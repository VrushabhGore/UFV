package ufv.ca.abby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

public class add_event extends AppCompatActivity {

    private EditText mPostEventTitle, mEventFilename;
    private Button mSubmitEvent,mChooseFile;
    private Uri mImageUri,mFileUri;

    private ImageButton mEventImage;
    private StorageReference mStorage;
    private ProgressDialog mProgessDialog;
    private DatabaseReference mEventDatabase;
    private FirebaseUser mCurrentUser;
    private String current_uid;
    private static final int GALLERY_REQUEST = 1;
    private static final int FILE_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Event");

        mStorage = FirebaseStorage.getInstance().getReference();
        mEventFilename = (EditText) findViewById(R.id.event_file);
        mPostEventTitle = (EditText) findViewById(R.id.event_name);
        mSubmitEvent = (Button) findViewById(R.id.post_event_btn);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();
        mEventDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post Event");

        //mProgessDialog = new ProgressDialog(this);
        mSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();

            }
        });

        mEventImage = (ImageButton) findViewById(R.id.select_image_event);
        mEventImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_REQUEST);
            }
        });

        mChooseFile = (Button)findViewById(R.id.choose_file_event);
        mChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("pdf/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), FILE_REQUEST);

            }
        });
    }
    private void startPosting() {
        //mProgessDialog.setMessage("Uploading Event...");

        final String title_val = mPostEventTitle.getText().toString();
        final String desc_val = mEventFilename.getText().toString();

        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val)&& mImageUri !=null && mFileUri!=null ){
            //mProgessDialog.show();

            final StorageReference filepath = mStorage.child("Event").child(title_val).child(title_val+".jpg");
            final StorageReference filepath2 = mStorage.child("Event").child(title_val).child(desc_val+".jpg");
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    final DatabaseReference newPost = mEventDatabase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.child("uid").setValue(current_uid).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            filepath2.putFile(mFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    newPost.child("filename").setValue(desc_val);
                                    newPost.child("file").setValue(downloadUrl.toString());


                                }
                            });
                        }
                    });
                    //mProgessDialog.dismiss();

                    startActivity(new Intent(add_event.this,result.class));
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == add_event.RESULT_OK) {
            switch (requestCode){
                case GALLERY_REQUEST:
                    mImageUri = data.getData();
                CropImage.activity(mImageUri)
                        .setAspectRatio(1, 1)
                        .start(this);
                mEventImage.setImageURI(mImageUri);
                    break;

                case FILE_REQUEST:
                    mFileUri = data.getData();
                    String filename = mFileUri.getLastPathSegment();
                    mEventFilename.setText(filename);
                    break;
            }

        }
    }



    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(100);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(add_event.this,Events.class));
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
