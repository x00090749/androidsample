// a RESTful service which returns a greeting

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using Hello.Models;                     // Greeting

namespace Hello.Controllers
{
    public class HelloController : ApiController
    {
        // Get /api/Hello/Gary
        public Greeting GetHello(String name)
        {
            return new Greeting() { Message = "Hello Word", To = name };        // as JSON or XML
            // 200 OK
        }
    }
}
