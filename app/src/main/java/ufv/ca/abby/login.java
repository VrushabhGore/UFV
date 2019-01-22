package ufv.ca.abby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private EditText mEmailField;
    private EditText mPassword;
    private Button mloginBtn;

    private ProgressDialog mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText)findViewById(R.id.login_email);
        mPassword = (EditText)findViewById(R.id.login_password);
        mloginBtn = (Button)findViewById(R.id.loginbtn);
        mLoginProgress = new ProgressDialog(this);




        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailField.getText().toString();
                String password = mPassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Please wait while we check your creds");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email, password);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
                    HashMap<String,String> user_map = new HashMap<>();
                    user_map.put("name","Default_name");
                    user_map.put("Status","I am using UFV APP");
                    user_map.put("Image","default");
                    user_map.put("thumb_image","default");

                    mDatabase.setValue(user_map);
                    mLoginProgress.dismiss();
                    Intent homeIntent = new Intent(login.this,homescreen.class);
                    startActivity(homeIntent);
                    finish();
                }else {

                    mLoginProgress.hide();
                    Toast.makeText(login.this,"Login Failed please check your credentials",Toast.LENGTH_LONG).show();

                }
            }
        });

    }


}
