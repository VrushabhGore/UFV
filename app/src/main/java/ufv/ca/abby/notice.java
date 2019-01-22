package ufv.ca.abby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class notice extends AppCompatActivity {

    private RecyclerView mNoticeList;
    private DatabaseReference mDatabase;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        mNoticeList=(RecyclerView)findViewById(R.id.NoticeList);
        mNoticeList.setHasFixedSize(true);
        mNoticeList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notice");

        mLayoutManager = new LinearLayoutManager(notice.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mNoticeList.setLayoutManager(mLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notice");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<notice_notice,NoticeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<notice_notice, NoticeViewHolder>(
                notice_notice.class,
                R.layout.notice_row,
                NoticeViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(NoticeViewHolder viewHolder, notice_notice model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Fullnotice = new Intent(notice.this,full_notice.class);
                        Fullnotice.putExtra("Notice_id",post_key);
                        startActivity(Fullnotice);
                    }
                });


            }
        };
        mNoticeList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.notice_title);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.notice_text);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.notice_image);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.notice_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_notice){
            startActivity(new Intent(notice.this,post_notice.class));
        }else if(item.getItemId()== R.id.home){
            onBackPressed();

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(notice.this,homescreen.class));

    }

}






