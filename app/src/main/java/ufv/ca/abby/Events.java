package ufv.ca.abby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Events extends AppCompatActivity {

    private Button mAddEventbtn;
    private RecyclerView mEventList;
    private DatabaseReference mDatabase;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mAddEventbtn = (Button)findViewById(R.id.add_event);
        mAddEventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_event= new Intent(Events.this, add_event.class);
                startActivity(add_event);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");

        mEventList = (RecyclerView)findViewById(R.id.EventList);
        mEventList.setHasFixedSize(true);
        mEventList.setLayoutManager(new LinearLayoutManager(this));

        mLayoutManager = new LinearLayoutManager(Events.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mEventList.setLayoutManager(mLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<event_recycle,Events.EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<event_recycle, Events.EventViewHolder>(
                event_recycle.class,
                R.layout.event_view,
                Events.EventViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(Events.EventViewHolder viewHolder, event_recycle model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Fullnotice = new Intent(Events.this,full_event.class);
                        Fullnotice.putExtra("Event_id",post_key);
                        startActivity(Fullnotice);
                    }
                });


            }
        };
        mEventList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public EventViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.event_title);
            post_title.setText(title);
        }


        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.event_image);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Events.this,homescreen.class));
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
