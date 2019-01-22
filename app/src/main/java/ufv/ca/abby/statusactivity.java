package ufv.ca.abby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class statusactivity extends AppCompatActivity {

    private EditText mStatus, mName;
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;
    private Button mSaveChanges;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusactivity);

        mStatus = (EditText) findViewById(R.id.new_status);
        mName = (EditText) findViewById(R.id.new_name);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(current_uid);

        mSaveChanges = (Button) findViewById(R.id.save_changes_status_btn);

        mSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress = new ProgressDialog(statusactivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();

                String status = mStatus.getText().toString();
                mStatusDatabase.child("Status").setValue(status);
                String name = mName.getText().toString();
                mStatusDatabase.child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            mProgress.dismiss();
                        } else {
                            Toast.makeText(statusactivity.this, "Something went wrong while changing the details", Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }
        });




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(statusactivity.this,profile.class));
        finish();
    }
}
