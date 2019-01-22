package ufv.ca.abby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class attendance extends AppCompatActivity {

    private Button mAttBscit,mAttBES,mAttBMM,mAttBMS,mAttBA,mAttBFM,mAttBBI,mAttBIM,mAttBCOM;
    private Button mAttBFTM,mAttBEM,mAttBAF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance");

        mAttBscit = (Button)findViewById(R.id.attendance_bscit);
        mAttBscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bscit = new Intent(attendance.this,attendance_bscit.class);
                startActivity(bscit);
                finish();
            }
        });

        mAttBA = (Button)findViewById(R.id.attendance_ba);
        mAttBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ba = new Intent(attendance.this,attendance_ba.class);
                startActivity(ba);
                finish();
            }
        });

        mAttBAF = (Button)findViewById(R.id.attendance_baf);
        mAttBAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baf = new Intent(attendance.this,attendance_baf.class);
                startActivity(baf);
                finish();
            }
        });

        mAttBBI = (Button)findViewById(R.id.attendance_bbi);
        mAttBBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bbi = new Intent(attendance.this,attendance_bbi.class);
                startActivity(bbi);
                finish();
            }
        });

        mAttBCOM = (Button)findViewById(R.id.attendance_bcom);
        mAttBCOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bcom = new Intent(attendance.this,attendance_bcom.class);
                startActivity(bcom);
                finish();
            }
        });

        mAttBEM = (Button)findViewById(R.id.attendance_bem);
        mAttBEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bem = new Intent(attendance.this,attendance_bem.class);
                startActivity(bem);
                finish();
            }
        });

        mAttBES = (Button)findViewById(R.id.attendance_bes);
        mAttBES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bes = new Intent(attendance.this,attendance_bes.class);
                startActivity(bes);
                finish();
            }
        });

        mAttBFM = (Button)findViewById(R.id.attendance_bfm);
        mAttBFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bfm = new Intent(attendance.this,attendance_bfm.class);
                startActivity(bfm);
                finish();
            }
        });

        mAttBFTM=(Button)findViewById(R.id.attendance_bftm);
        mAttBFTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bftm = new Intent(attendance.this,attendance_bftm.class);
                startActivity(bftm);
                finish();
            }
        });

        mAttBMM = (Button)findViewById(R.id.attendance_bmm);
        mAttBMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bmm = new Intent(attendance.this,attendance_bmm.class);
                startActivity(bmm);
                finish();
            }
        });

        mAttBMS = (Button)findViewById(R.id.attendance_bms);
        mAttBMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bms = new Intent(attendance.this,attendance_bms.class);
                startActivity(bms);
                finish();
            }
        });

        mAttBIM = (Button)findViewById(R.id.attendance_bim);
        mAttBIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bim = new Intent(attendance.this,attendance_bim.class);
                startActivity(bim);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(attendance.this,homescreen.class));
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
