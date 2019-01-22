package ufv.ca.abby;

public class Subject {

        protected String grade;
        protected double creditHour;
        protected double gradePoint;


    public Subject()
        {
            grade = "";
            creditHour = 0;
            gradePoint = 0;

        }

    public Subject(String g, double ch)
        {
            grade = g;
            creditHour = ch;
        }

        public void setGrade(String g)
        {
            grade = g;
        }

        public void setCreditHour(double ch)
        {
            creditHour = ch;
        }

        public void setGradePoint()
        {
            if (grade.equals("A+"))
            {
                gradePoint = 4.00;
            }
            else if (grade.equals("A"))
            {
                gradePoint = 4.00;
            }
            else if (grade.equals("A-"))
            {
                gradePoint = 3.67;
            }
            else if (grade.equals("B+"))
            {
                gradePoint = 3.33;
            }
            else if (grade.equals("B"))
            {
                gradePoint = 3;
            }
            else if (grade.equals("B-"))
            {
                gradePoint = 2.67;
            }
            else if (grade.equals("C+"))
            {
                gradePoint = 2.33;
            }
            else if (grade.equals("C"))
            {
                gradePoint = 2;
            }
            else if (grade.equals("C-"))
            {
                gradePoint = 1.67;
            }
            else if (grade.equals("D+"))
            {
                gradePoint = 1.33;
            }
            else if (grade.equals("D"))
            {
                gradePoint = 1;
            }
            else if (grade.equals("E"))
            {
                gradePoint = 0.67;
            }
            else if (grade.equals("F"))
            {
                gradePoint = 0.0;
            }
        }

        public String getGrade()
        {
            return grade;
        }

        public double getCreditHour()
        {
            return creditHour;
        }

        public double getGradePoint()
        {
            return gradePoint;
        }
    }

