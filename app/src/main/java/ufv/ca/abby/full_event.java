package ufv.ca.abby;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class full_event extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private ImageView mFullEventImage;
    private TextView mFullEventtitle;
    private Button mRemoveNotice,mDownloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event");

        mPost_key = getIntent().getExtras().getString("Event_id");
        mFullEventtitle=(TextView)findViewById(R.id.full_event_title);
        mFullEventImage=(ImageView)findViewById(R.id.full_event_image);

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid= mCurrentUser.getUid();

        mAuth = FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Event");

        mRemoveNotice=(Button)findViewById(R.id.remove_notice);
        mRemoveNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPost_key).removeValue();
                Intent noticeIntent = new Intent(full_event.this, Events.class);
                startActivity(noticeIntent);
            }
        });

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("title").getValue();
                final String fileurl = (String) dataSnapshot.child("file").getValue();
                String post_image = (String)dataSnapshot.child("image").getValue();

                mFullEventtitle.setText(post_title);
                Picasso.with(full_event.this).load(post_image).into(mFullEventImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDownloadButton = (Button)findViewById(R.id.download_file);
        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileurl = mDatabase.child(mPost_key).child("file").toString();
                String filename = mDatabase.child(mPost_key).child("title").toString();
                Toast.makeText(full_event.this,"Downloading the file, please check the notification tab",Toast.LENGTH_LONG).show();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileurl));
                request.setDescription("Download Complete");
                request.setTitle("Downloading Brouchure");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename+".pdf");

// get download service and enqueue file
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(full_event.this,Events.class));
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
