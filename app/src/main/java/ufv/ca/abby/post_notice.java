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

public class post_notice extends AppCompatActivity {

    private EditText mPostNoticeTitle, mPostNoticeDesc;
    private Button mSubmitNotice;
    private Uri mImageUri;

    private ImageButton mNoticeImage;
    private StorageReference mStorage;
    private ProgressDialog mProgessDialog;
    private DatabaseReference mNoticeDatabase;
    private FirebaseUser mCurrentUser;
    private String current_uid;
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_notice);

        mStorage = FirebaseStorage.getInstance().getReference();
        mPostNoticeDesc = (EditText) findViewById(R.id.notice_text);
        mPostNoticeTitle = (EditText) findViewById(R.id.notice_title);
        mSubmitNotice = (Button) findViewById(R.id.post_notice_btn);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();
        mNoticeDatabase = FirebaseDatabase.getInstance().getReference().child("Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post Notice");

        mProgessDialog = new ProgressDialog(this);
        mSubmitNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();

            }
        });

        mNoticeImage = (ImageButton) findViewById(R.id.select_image_notice);
        mNoticeImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_REQUEST);
            }
        });

    }

    private void startPosting() {
        mProgessDialog.setMessage("Uploading Notice...");

        final String title_val = mPostNoticeTitle.getText().toString();
        final String desc_val = mPostNoticeDesc.getText().toString();

        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val)&& mImageUri !=null){
            mProgessDialog.show();

            StorageReference filepath = mStorage.child("Notice").child(random()+".jpg");
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mNoticeDatabase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.child("uid").setValue(current_uid);
                    mProgessDialog.dismiss();

                    startActivity(new Intent(post_notice.this,notice.class));
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            CropImage.activity(mImageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
            mNoticeImage.setImageURI(mImageUri);
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
        startActivity(new Intent(post_notice.this,notice.class));
        finish();
        this.overridePendingTransition(R.anim.swipe_to_left,
                R.anim.swipe_to_right);
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
