package ufv.ca.abby;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class contact_us extends AppCompatActivity {

    TextView mContact_one,mContact_two,mEmailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        mContact_one=(TextView)findViewById(R.id.contact_1);
        mContact_two=(TextView)findViewById(R.id.contact_2);
        mEmailID=(TextView)findViewById(R.id.email_contact);

        mContact_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactone = new Intent(Intent.ACTION_DIAL);
                contactone.setData(Uri.parse("tel:022-28072477"));
                startActivity(contactone);
            }
        });

        mContact_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contacttwo = new Intent(Intent.ACTION_DIAL);
                contacttwo.setData(Uri.parse("tel:022-28090185"));
                startActivity(contacttwo);
            }
        });

        mEmailID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EmailIntent = new Intent(contact_us.this,Feedback.class);
                startActivity(EmailIntent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(contact_us.this,homescreen.class));
        finish();
        this.overridePendingTransition(R.anim.swipe_to_right,
                R.anim.swipe_to_left);
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
