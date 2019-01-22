package ufv.ca.abby;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class attendance_bmm extends AppCompatActivity {

    private Button mUploadBmmAttendance;
    private ImageButton mSubmitBmmAttendance;
    private EditText mEditAttendanceBmm;
    private RecyclerView mBmmAttendanceList;
    private static final int GALLERY_PICK = 1;
    private Uri FileUri;
    private ProgressDialog mProgessDialog;
    private String current_uid;
    private StorageReference mStorage;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_bmm);


        mEditAttendanceBmm = (EditText) findViewById(R.id.bmm_att_name);
        mStorage = FirebaseStorage.getInstance().getReference().child("Attendance").child("BMM");
        mBmmAttendanceList = (RecyclerView) findViewById(R.id.att_bmm_list_view);
        mUploadBmmAttendance = (Button) findViewById(R.id.upload_att_bmm_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Attendance").child("BMM");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();


        mProgessDialog = new ProgressDialog(this);
        mUploadBmmAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadattbmm();
            }
        });


        mSubmitBmmAttendance = (ImageButton) findViewById(R.id.choose_att_bmm);
        mSubmitBmmAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallerypick = new Intent();
                gallerypick.setType("pdf/*");
                gallerypick.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallerypick, "Choose a file"), GALLERY_PICK);
            }
        });

        mBmmAttendanceList.setHasFixedSize(true);
        mBmmAttendanceList.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance BMM");
    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<attendance_bmm_recycle,attendance_bmm.AttendanceBmmViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<attendance_bmm_recycle,attendance_bmm.AttendanceBmmViewHolder>(
                attendance_bmm_recycle.class,
                R.layout.list_view,
                attendance_bmm.AttendanceBmmViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(final attendance_bmm.AttendanceBmmViewHolder viewHolder, attendance_bmm_recycle model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());

                mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String url = (String) dataSnapshot.child("file").getValue();
                        final String title = (String) dataSnapshot.child("title").getValue();
                        mDatabase.keepSynced(true);
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Toast.makeText(attendance_bmm.this,"Downloading the file, please check the notification tab",Toast.LENGTH_LONG).show();
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                request.setDescription("Download Complete");
                                request.setTitle(title);

                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename()+".pdf");

// get download service and enqueue file
                                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                manager.enqueue(request);
                            }

                            private String filename() {
                                String filename = title;
                                return filename;
                            }
                        });

                        viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                mDatabase.child(post_key).removeValue();


                                return false;
                            }


                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        mBmmAttendanceList.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FileUri = data.getData();
        String filename = FileUri.getLastPathSegment();
        mEditAttendanceBmm.setText(filename);
    }

    private void uploadattbmm() {
        mProgessDialog.setMessage("Uploading File...");

        final String title_val = mEditAttendanceBmm.getText().toString();

        if (!TextUtils.isEmpty(title_val) && FileUri != null) {
            mProgessDialog.show();
            mProgessDialog.setCanceledOnTouchOutside(false);

            StorageReference filepath = mStorage.child(title_val+ ".pdf");
            filepath.putFile(FileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(title_val+".pdf");
                    newPost.child("file").setValue(downloadUrl.toString());
                    newPost.child("uid").setValue(current_uid);
                    mProgessDialog.dismiss();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(attendance_bmm.this,"Upload Complete",Toast.LENGTH_LONG).show();
                    mEditAttendanceBmm.getText().clear();
                }
            });
        }else {
            Toast.makeText(attendance_bmm.this,"Error occured Try again",Toast.LENGTH_LONG).show();
        }

    }

    public static class AttendanceBmmViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public AttendanceBmmViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }
        public  void setFile(String file){


        }
        public void setTitle(String title){
            TextView Title = (TextView)mView.findViewById(R.id.study_bscit_filename);
            Title.setText(title);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(attendance_bmm.this,attendance.class));
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
