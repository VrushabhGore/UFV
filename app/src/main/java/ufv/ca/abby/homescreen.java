package ufv.ca.abby;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class homescreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int STORAGEREF = 1;
    private FirebaseAuth mAuth;
    //Android Activitiy content
    ViewPager viewPager;
    private Button about_us,mNotice,mStudy_Material,mAttendance,mEvent,mResult,mAcademics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mAttendance = (Button)findViewById(R.id.attendance);
        mAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance = new Intent(homescreen.this, ufv.ca.abby.attendance.class);
                startActivity(attendance);
                finish();
            }
        });
        getSupportActionBar().setTitle("Welcome to UFV App");
        mStudy_Material=(Button)findViewById(R.id.study_material);
        mStudy_Material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_material = new Intent(homescreen.this,study_material.class);
                startActivity(study_material);
                finish();
            }
        });

        mResult = (Button)findViewById(R.id.result);
        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent(homescreen.this, ufv.ca.abby.result.class);
                startActivity(result);
                finish();
            }
        });

        mEvent = (Button)findViewById(R.id.events);
        mEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event = new Intent(homescreen.this,Events.class);
                startActivity(event);
                finish();
            }
        });

        mAcademics=(Button)findViewById(R.id.academics);
        mAcademics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent academics = new Intent (homescreen.this,GPACalculator.class);
                startActivity(academics);
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        about_us=(Button)findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutusIntent = new Intent(homescreen.this,FeeCalculator.class);
                startActivity(aboutusIntent);
                finish();
            }
        });
        mNotice = (Button)findViewById(R.id.notice);
        mNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noticeIntent = new Intent(homescreen.this,notice.class);
                startActivity(noticeIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_kesonline) {
            Intent i = new Intent(homescreen.this,ufvonline.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_feedback) {

            Intent i = new Intent(homescreen.this,Feedback.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent (Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "Hey I found our college app! You can download it from the below link";
            String sharesubject="Kandivali Education Society App";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,sharesubject);
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(shareIntent,"Share Using"));

        } else if (id == R.id.nav_contact_us) {
            Intent i = new Intent(homescreen.this,contact_us.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_find_us) {
            Intent i = new Intent(homescreen.this,find_us.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            sentToStart();
        } else if (id == R.id.nav_profile){
            Intent i = new Intent(homescreen.this,profile.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sentToStart();
        }
        requestStoragePermission();



    }

    private void sentToStart() {

        Intent startIntent = new Intent(homescreen.this, login.class);
        startActivity(startIntent);
        finish();
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            homescreen.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode  == STORAGEREF){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("This permission is needed").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGEREF);
        }


    }



}





