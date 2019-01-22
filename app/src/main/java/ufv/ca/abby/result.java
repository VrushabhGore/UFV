package ufv.ca.abby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class result extends AppCompatActivity {

    private Button mResultBscit,mResultBES,mResultBMM,mResultBMS,mResultBA,mResultBFM,mResultBBI,mResultBIM,mResultBCOM;
    private Button mResultBFTM,mResultBEM,mResultBAF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");

        mResultBscit = (Button)findViewById(R.id.result_bscit);
        mResultBscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bscit = new Intent(result.this,result_bscit.class);
                startActivity(bscit);
                finish();
            }
        });

        mResultBA = (Button)findViewById(R.id.result_ba);
        mResultBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ba = new Intent(result.this,result_ba.class);
                startActivity(ba);
                finish();
            }
        });

        mResultBAF = (Button)findViewById(R.id.result_baf);
        mResultBAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baf = new Intent(result.this,result_baf.class);
                startActivity(baf);
                finish();
            }
        });

        mResultBBI = (Button)findViewById(R.id.result_bbi);
        mResultBBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bbi = new Intent(result.this,result_bbi.class);
                startActivity(bbi);
                finish();
            }
        });

        mResultBCOM = (Button)findViewById(R.id.result_bcom);
        mResultBCOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bcom = new Intent(result.this,result_bcom.class);
                startActivity(bcom);
                finish();
            }
        });

        mResultBEM = (Button)findViewById(R.id.result_bem);
        mResultBEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bem = new Intent(result.this,result_bem.class);
                startActivity(bem);
                finish();
            }
        });

        mResultBES = (Button)findViewById(R.id.result_bes);
        mResultBES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bes = new Intent(result.this,result_bes.class);
                startActivity(bes);
                finish();
            }
        });

        mResultBFM = (Button)findViewById(R.id.result_bfm);
        mResultBFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bfm = new Intent(result.this,result_bfm.class);
                startActivity(bfm);
                finish();
            }
        });

        mResultBFTM=(Button)findViewById(R.id.result_bftm);
        mResultBFTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bftm = new Intent(result.this,result_bftm.class);
                startActivity(bftm);
                finish();
            }
        });

        mResultBMM = (Button)findViewById(R.id.result_bmm);
        mResultBMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bmm = new Intent(result.this,result_bmm.class);
                startActivity(bmm);
                finish();
            }
        });

        mResultBMS = (Button)findViewById(R.id.result_bms);
        mResultBMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bms = new Intent(result.this,result_bms.class);
                startActivity(bms);
                finish();
            }
        });

        mResultBIM = (Button)findViewById(R.id.result_bim);
        mResultBIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bim = new Intent(result.this,result_bim.class);
                startActivity(bim);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(result.this,homescreen.class));
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
