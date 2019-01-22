package ufv.ca.abby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Feedback extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button buttonSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
        getSupportActionBar().setTitle("FeedBack");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Feedback.this,homescreen.class));
        finish();
        this.overridePendingTransition(R.anim.swipe_to_right,
                R.anim.swipe_to_left);
    }
    private void sendEmail() {
        //Getting content for email
        String email = "testufv1234@gmail.com";
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();
        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        SendMail vm = new SendMail(this, subject, "COPY OF THE FEEDBACK FORM YOU SUBMITTED", "Thank you for using the UFV Feedback System. Here is the message you sent us. \n"+message);
        //Executing sendmail to send email
        sm.execute();
        vm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
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
