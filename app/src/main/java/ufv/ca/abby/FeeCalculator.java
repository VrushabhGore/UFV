package ufv.ca.abby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FeeCalculator extends AppCompatActivity {

    private Button calculate,sendemail;
    private RadioGroup radioGroup;
    private EditText editText;
    private TextView totalfees,emailedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feecalculator);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fee Calculator");
        emailedit = findViewById(R.id.emailedittext);


        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            totalfee();
            }
        });
        sendemail = findViewById(R.id.sendmail);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    public int totalfee(){
        int fee = 0;
        radioGroup = findViewById(R.id.radio_group_1);
        int radioId1 = radioGroup.getCheckedRadioButtonId();
        if (radioId1 == R.id.domestic){
            editText = findViewById(R.id.numberofcredit);
            String credits = editText.getText().toString();
            int credit = Integer.parseInt(credits);
            fee = (int) (credit * 145.04);
            String totalfee = Integer.toString(fee);
            totalfees = findViewById(R.id.totalfee);
            totalfees.setText(totalfee);

        }else if(radioId1 == R.id.international){

            editText = findViewById(R.id.numberofcredit);
            String credits = editText.getText().toString();
            int credit = Integer.parseInt(credits);
            fee = (int) (credit * 545);
            String totalfee = Integer.toString(fee);
            totalfees = findViewById(R.id.totalfee);
            totalfees.setText(totalfee);
        }
        else {
            Toast.makeText(this,"Please fill in all the details",Toast.LENGTH_LONG).show();
            return fee;
        }
        return fee;
    }

    private void sendEmail() {
        //Getting content for email
        totalfee();
        totalfees = findViewById(R.id.totalfee);
        String total_fee = totalfees.getText().toString();
        final String email = emailedit.getText().toString();
        String mail = email;
        String subject = "Fee Estimation FROM UFV APP";
        String message = "Thank you for using the UFV Application. Your estimated fee  is : "+ total_fee;

        //Creating SendMail object
        SendMail sm = new SendMail(this, mail, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FeeCalculator.this,homescreen.class));
        super.onBackPressed();
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
