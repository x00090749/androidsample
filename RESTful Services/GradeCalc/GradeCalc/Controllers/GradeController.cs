// RESTful service which calculates and returns a grade for a specified percentage mark (0 - 100)
// run on ASP.Net development server

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using GradeCalc.Models;

namespace GradeCalc.Controllers
{
    public class GradeController : ApiController
    {
        // GET /api/Grade/85
        public String GetGrade(int mark)
        {
            try
            {
                return Grade.CalcGrade(mark);
            }
            catch (ArgumentException)
            {
                throw new HttpResponseException(HttpStatusCode.BadRequest);
            }
        }
    }
}
