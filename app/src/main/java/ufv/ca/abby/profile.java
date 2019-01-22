package ufv.ca.abby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mDisplayName;
    private TextView mDisplayStatus;
    private Button mChangeImage, mChangeStatus;
    private StorageReference mProfileImageStorage;
    private ProgressDialog mProgressDialog;
    private CircleImageView mProfileImage;
    private static final int GALLERY_PICK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mDisplayName = (TextView) findViewById(R.id.profile_displayname);
        mDisplayStatus = (TextView) findViewById(R.id.profile_Status);
        mChangeStatus = (Button) findViewById(R.id.profile_status_btn);
        mChangeImage = (Button) findViewById(R.id.profile_image_btn);
        mProfileImageStorage = FirebaseStorage.getInstance().getReference();
        mProfileImage=(CircleImageView)findViewById(R.id.profile_picture);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("Status").getValue().toString();
                String image = dataSnapshot.child("Image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mDisplayStatus.setText(status);
                mDisplayName.setText(name);
                Picasso.with(profile.this).load(image).into(mProfileImage);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile.this, statusactivity.class);
                startActivity(i);
                finish();
            }
        });
        mChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri ImageURI = data.getData();
            CropImage.activity(ImageURI)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                mProgressDialog= new ProgressDialog(profile.this);
                mProgressDialog.setTitle("Uploading Image");
                mProgressDialog.setMessage("Please wait while we process the Image");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                String current_user_id = mCurrentUser.getUid();
                Uri resultUri = result.getUri();
                StorageReference filepath = mProfileImageStorage.child("profile_pictures").child(current_user_id+".jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                        String downloadURL = task.getResult().getDownloadUrl().toString();
                        mUserDatabase.child("Image").setValue(downloadURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                }
                            }
                        });

                        }
                        else {
                            Toast.makeText(profile.this,"Failed uploading",Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(profile.this,homescreen.class));
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


