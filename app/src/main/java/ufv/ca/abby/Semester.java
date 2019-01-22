package ufv.ca.abby;

public class Semester extends Subject
{
    protected double gpa;
    protected double totalGradePoint;
    protected double totalCreditHour;

    public Semester()
    {
        gpa = 0;
        totalGradePoint = 0;
        totalCreditHour = 0;
    }

    public Semester(String g, double ch)
    {
        super(g,ch);
    }

    public void setCreditHour(double ch)
    {
        creditHour = ch;
    }

    public void setGrade(String g)
    {
        grade = g;
    }

    public void calcTotalGradePoint()
    {
        super.setGradePoint();

        double sumOfGradePoint = creditHour * gradePoint;
        totalGradePoint = totalGradePoint + sumOfGradePoint;
    }

    public void calcTotalCreditHour()
    {
        totalCreditHour = totalCreditHour + creditHour;
    }

    public void calculateGpa()
    {
        gpa = totalGradePoint / totalCreditHour;
    }

    public double getGpa()
    {
        return gpa;
    }

}
