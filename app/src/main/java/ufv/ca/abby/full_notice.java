package ufv.ca.abby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class full_notice extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private ImageView mFullNoticeImage;
    private TextView mFulltitle,mFullDesc;
    private Button mRemoveNotice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_notice);

        mPost_key = getIntent().getExtras().getString("Notice_id");
        mFulltitle=(TextView)findViewById(R.id.full_notice_title);
        mFullDesc=findViewById(R.id.full_notice_text);
        mFullNoticeImage=(ImageView)findViewById(R.id.full_notice_image);

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid= mCurrentUser.getUid();

        mAuth = FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Notice");

        mRemoveNotice=(Button)findViewById(R.id.remove_notice);
        mRemoveNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPost_key).removeValue();
                Intent noticeIntent = new Intent(full_notice.this,notice.class);
                startActivity(noticeIntent);
            }
        });

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String)dataSnapshot.child("image").getValue();

                mFulltitle.setText(post_title);
                mFullDesc.setText(post_desc);
                Picasso.with(full_notice.this).load(post_image).into(mFullNoticeImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Full Notice");

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(full_notice.this, notice.class));

    }
}
