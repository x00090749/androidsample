using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GradeCalc.Models
{
    public class Grade
    {
        // calculate the grade for a mark i.e. F, D, C, C+, B-, B+ or A
        public static String CalcGrade(int mark)
        {
            if ((mark < 0) || (mark > 100))                 // validate input
            {
                throw new ArgumentException("Mark: " + mark + " must be in range 0..100");
            }
            else
            {
                if (mark <= 34)
                {
                    return "F";
                }
                else if (mark <= 39)
                {
                    return "D";
                }
                else if (mark <= 49)
                {
                    return "C";
                }
                else if (mark <= 54)
                {
                    return "C+";
                }
                else if (mark <= 59)
                {
                    return "B-";
                }
                else if (mark <= 69)
                {
                    return "B";
                }
                else if (mark <= 79)
                {
                    return "B+";
                }
                else
                {
                    return "A";
                }
            }
        }
    }
}