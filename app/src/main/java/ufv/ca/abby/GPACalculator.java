package ufv.ca.abby;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GPACalculator extends AppCompatActivity implements OnClickListener
{
    private String[] creditHourItem = {"1","2","3","4","5","6"};
    private String[] gradeItem = {"A+","A","A-","B+","B","B-","C+","C","C-","D+","D","E","F"};
    private String finalresult;

    private Button[] subj = new Button[7];
    private Button[] grade = new Button[7];
    private Button calculate,sendmail;
    private EditText emailedit;

    private int z = 0;
    private View temp;

    private TextView result;

    Subject[] sub = new Subject[7];

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);



        subj[0] = (Button)findViewById(R.id.subject1);
        subj[1] = (Button)findViewById(R.id.subject2);
        subj[2] = (Button)findViewById(R.id.subject3);
        subj[3] = (Button)findViewById(R.id.subject4);
        subj[4] = (Button)findViewById(R.id.subject5);
        subj[5] = (Button)findViewById(R.id.subject6);
        subj[6] = (Button)findViewById(R.id.subject7);

        grade[0] = (Button)findViewById(R.id.grade1);
        grade[1] = (Button)findViewById(R.id.grade2);
        grade[2] = (Button)findViewById(R.id.grade3);
        grade[3] = (Button)findViewById(R.id.grade4);
        grade[4] = (Button)findViewById(R.id.grade5);
        grade[5] = (Button)findViewById(R.id.grade6);
        grade[6] = (Button)findViewById(R.id.grade7);

        calculate = (Button)findViewById(R.id.ok);
        calculate.setOnClickListener(this);

        sendmail = (Button)findViewById(R.id.sendmail);
        sendmail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();
            }
        });

        result = (TextView)findViewById(R.id.result);
        emailedit = (EditText)findViewById(R.id.emailedittext);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("GPA CALCULATOR");


        int i = 0;
        while (i<7)
        {

            sub[i] = new Subject();

            subj[i].setOnClickListener(this);
            grade[i].setOnClickListener(this);
            i++;
        }
    }

    public void onClick(View v)
    {
        for(int s = 0; s < 7; s++)
        {
            if(v == subj[s])
            {
                temp = v;

                AlertDialog.Builder chList = new AlertDialog.Builder(this);
                chList.setTitle("Set credit hour");

                chList.setItems(creditHourItem, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        Toast.makeText(getApplicationContext(), creditHourItem[item], Toast.LENGTH_SHORT).show();
                        z = item;
                        for(int j = 0; j < subj.length; j++ )
                        {
                            if(temp == subj[j])
                            {
                                subj[j].setText(creditHourItem[z] + " Credit Hour");
                                double ch = 0;
                                if(creditHourItem[z] == "1")
                                    ch = 1;
                                else if(creditHourItem[z] == "2")
                                    ch = 2;
                                else if(creditHourItem[z] == "3")
                                    ch = 3;
                                else if(creditHourItem[z] == "4")
                                    ch = 4;
                                else if(creditHourItem[z] == "5")
                                    ch = 5;
                                else if(creditHourItem[z] == "6")
                                    ch = 6;

                                sub[j].setCreditHour(ch);

                            }
                        }
                    }

                });

                chList.show();
            }

            else if(v == grade[s])
            {
                temp = v;

                AlertDialog.Builder gradeList = new AlertDialog.Builder(this);
                gradeList.setTitle("Set grade");

                gradeList.setItems(gradeItem, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        Toast.makeText(getApplicationContext(), gradeItem[item], Toast.LENGTH_SHORT).show();
                        z = item;
                        for(int j = 0; j < grade.length; j++ )
                        {
                            if(temp == grade[j])
                            {
                                grade[j].setText("      Grade : " + gradeItem[z] + "      ");

                                sub[j].setGrade(gradeItem[item]);
                            }
                        }
                    }

                });
                gradeList.show();
            }
        }

        if(v == calculate)
        {
            Semester sem  = new Semester();
            for(int i = 0; i < 7; i++)
            {
                sem.setCreditHour(sub[i].getCreditHour());
                sem.setGrade(sub[i].getGrade());
                sem.calcTotalCreditHour();
                sem.calcTotalGradePoint();
            }
            sem.calculateGpa();
            double res = sem.getGpa();
            final String convertedResultValue = Double.toString(res);
            finalresult = convertedResultValue;
            result.setText(convertedResultValue);
        }

    }

    private void sendEmail() {
        //Getting content for email
        final String email = emailedit.getText().toString();
        String mail = email;
        String subject = "GPA RESULTS FROM UFV APP";
        String message = "Thank you for using the UFV Application. Your GPA is : "+ finalresult;

        //Creating SendMail object
        SendMail sm = new SendMail(this, mail, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GPACalculator.this,homescreen.class));
        this.overridePendingTransition(R.anim.swipe_to_right,R.anim.swipe_to_left);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}