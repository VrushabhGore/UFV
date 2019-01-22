package ufv.ca.abby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class study_material extends AppCompatActivity {

    private Button mstudyBscit;
    private Button mstudyBES,mStudyBMM,mStudyBMS,mStudyBA,mstudyBBI,mstudyBFM,mStudyBBI,mStudyBIM,mStudyBCOM;
    private Button mStudyBFTM,mStudyBEM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);

        mstudyBscit = (Button) findViewById(R.id.study_bscit);
        mstudyBscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bscit = new Intent(study_material.this, study_bscit.class);
                startActivity(study_bscit);
                finish();
            }
        });

        mstudyBFM = (Button)findViewById(R.id.study_bfm);
        mstudyBFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bfm = new Intent(study_material.this, ufv.ca.abby.study_bfm.class);
                startActivity(study_bfm);
                finish();
            }
        });

        mStudyBBI = (Button)findViewById(R.id.study_bbi);
        mStudyBBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bbi = new Intent(study_material.this, ufv.ca.abby.study_bbi.class);
                startActivity(study_bbi);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Study Material");


        mstudyBES = (Button)findViewById(R.id.study_bes);
        mstudyBES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bes = new Intent(study_material.this,study_BES.class);
                startActivity(study_bes);
                finish();
            }
        });

        mStudyBCOM = (Button)findViewById(R.id.study_bcom);
        mStudyBCOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bcom = new Intent(study_material.this, ufv.ca.abby.study_bcom.class);
                startActivity(study_bcom);
                finish();
            }
        });

        mStudyBA = (Button)findViewById(R.id.study_ba);
        mStudyBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_ba = new Intent(study_material.this, ufv.ca.abby.study_ba.class);
                startActivity(study_ba);
                finish();
            }
        });

        mStudyBBI=(Button)findViewById(R.id.study_bbi);
        mStudyBBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bbi = new Intent(study_material.this, ufv.ca.abby.study_bbi.class);
                startActivity(study_bbi);
                finish();
            }
        });
        mStudyBEM = (Button)findViewById(R.id.study_bem);
        mStudyBEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bem = new Intent(study_material.this, ufv.ca.abby.study_bem.class);
                startActivity(study_bem);
                finish();
            }
        });

        mStudyBMS = (Button)findViewById(R.id.study_bms);
        mStudyBMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bms = new Intent(study_material.this, ufv.ca.abby.study_bms.class);
                startActivity(study_bms);
                finish();
            }
        });

        mStudyBMM = (Button)findViewById(R.id.study_bmm);
        mStudyBMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bmm = new Intent(study_material.this, ufv.ca.abby.study_bmm.class);
                startActivity(study_bmm);
                finish();
            }
        });

        mStudyBIM = (Button)findViewById(R.id.study_bim);
        mStudyBIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bim = new Intent(study_material.this, ufv.ca.abby.study_bim.class);
                startActivity(study_bim);
                finish();
            }
        });

        mStudyBFTM = (Button)findViewById(R.id.study_bftm);
        mStudyBFTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent study_bftm = new Intent(study_material.this, ufv.ca.abby.study_bftm.class);
                startActivity(study_bftm);
                finish();
            }
        });

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
        startActivity(new Intent(study_material.this, homescreen.class));
        this.overridePendingTransition(R.anim.swipe_to_right,R.anim.swipe_to_left);

    }
}
