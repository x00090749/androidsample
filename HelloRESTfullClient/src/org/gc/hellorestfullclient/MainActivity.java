/**
 * An Activity which is a client for a RESTful web service 
 * i.e.  an ASP.Net Web API RESTful service running on VS development server
 * Service generates JSON output
 * Appropriate permissions to access Internet specified in manifest
 */

package org.gc.hellorestfullclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity 
{ 
	// URL for RESTful service which returns JSON
	private static String SERVICE_URL = "http://10.0.2.2:1764/api/Hello";			// 10.0.2.2 needed for emulator
	
	// tag for LogCat logging
	private static String TAG = "HelloRESTfullClient";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // get TextView for displaying result
        final TextView outputTextView = (TextView) findViewById(R.id.outputTextView); 
        
        final StringBuilder jsonData = new StringBuilder();

        // kick off a new thread which calls RESTful service
        // note: all networking calls in Android 3.0 must be in a separate thread from main thread
        // otherwise exception is thrown
		Thread t = new Thread()
		{ 
			public void run()														// anonymous class
			{
				try
				{
					URI uri = new URI(SERVICE_URL + "/Gary");					    // create URL
					Log.d(TAG, "Connecting to" + uri.toString());					// debug log message
					
					// issue a GET to uri
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet get = new HttpGet(uri);									// or HttpPost
					HttpResponse response = httpClient.execute(get);
					
					// get a reader to response content
					InputStream is = response.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					
					String line = null;
					Log.d(TAG, "Retreivng data");
					try
					{
						while ((line = reader.readLine()) != null)					// read line by line
						{
							Log.d(TAG, "appending" + line);
							jsonData.append(line + "\n");	
						}
					} 
					catch (IOException e)
					{
						Log.d(TAG, e.toString());
						outputTextView.setText(e.toString());
					} 
					finally
					{
						try
						{
							is.close();
						} 
						catch (IOException e)
						{
							Log.d(TAG, e.toString());
							outputTextView.setText(e.toString());
						}
					}  
					Log.d(TAG, "Data retrieved" + jsonData);
				} 
				catch (Exception e)
				{
					Log.d(TAG, e.toString());
					outputTextView.setText(e.toString());
				}
			}
		};
		try
		{
			t.start();						// kick off thread
			t.join();						// wait for thread to finish
			
			// create new JSON Object from JSON data 
			JSONObject jObject = new JSONObject(jsonData.toString());
			
			Log.d(TAG, "Displaying data" + jsonData);
			
			// display on TextView
			// Message and To are properties on Message model class in MVC application
			outputTextView.setText(jObject.getString("Message") + " " + jObject.getString("To") );
			
			// could parse JSON data directly to a custom class e.g. Greeting
			// e.g. gson api
		} 
		catch (Exception e)
		{
			Log.d(TAG, e.toString());
			outputTextView.setText(e.toString());
		}
    }
}
