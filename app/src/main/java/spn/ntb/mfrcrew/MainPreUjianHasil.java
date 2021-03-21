package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.SessionManager;

public class MainPreUjianHasil extends AppCompatActivity {

    Animation animAlpha;
    SessionManager session;
    
    TextView txt_score, txt_jwb_salah, txt_jwb_benar;
    CardView akhir_ujian;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static final String url_simpan = "http://spn.ntb.polri.go.id/admin/service_android/simpan_ujian.php";

    int success;
    
    TextView txt_nosis, kd_soal, p_mapel, p_tgl, p_pengampu;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pre_ujian_hasil);
    
        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
        session = new SessionManager(getApplicationContext());
    
        txt_score = findViewById(R.id.txt_score);
        txt_jwb_salah = findViewById(R.id.txt_jwb_salah);
        txt_jwb_benar = findViewById(R.id.txt_jwb_benar);
        kd_soal = findViewById(R.id.kd_soal);
        p_mapel = findViewById(R.id.p_mapel);
        p_tgl = findViewById(R.id.p_tgl);
        p_pengampu = findViewById(R.id.p_pengampu);
        
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            txt_jwb_salah.setText(bundle.getString("salah"));
            txt_jwb_benar.setText(bundle.getString("benar"));
            txt_score.setText(bundle.getString("score"));
            kd_soal.setText(bundle.getString("p_kd_soal"));
            p_mapel.setText(bundle.getString("p_mapel"));
            p_tgl.setText(bundle.getString("p_tgl"));
            p_pengampu.setText(bundle.getString("p_pengampu"));
        }else{
            txt_jwb_salah.setText(getIntent().getStringExtra("salah"));
            txt_jwb_benar.setText(getIntent().getStringExtra("benar"));
            txt_score.setText(getIntent().getStringExtra("score"));
            kd_soal.setText(getIntent().getStringExtra("p_kd_soal"));
            p_mapel.setText(getIntent().getStringExtra("p_mapel"));
            p_tgl.setText(getIntent().getStringExtra("p_tgl"));
            p_pengampu.setText(getIntent().getStringExtra("p_pengampu"));
        }
    
        txt_nosis = findViewById(R.id.txt_nosis);
        //=============================== Tangkap Hasil SESSION ===================================//
            HashMap<String, String> id_userz = session.getUserDetails();
            String nox = id_userz.get(SessionManager.id_user);
            txt_nosis.setText(Html.fromHtml("<b>" + nox + "</b>"));
        //=============================== End Tangkap Hasil SESSION ===================================//
    
        akhir_ujian = findViewById(R.id.akhir_ujian);
        akhir_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                showDialog();
            }
        });
    }

    private void showDialog(){
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("SPN POLDA NTB");
        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Terimakasih Sudah Mengikuti Ujian Hari Ini.")
                .setIcon(R.drawable.app_icon)
                .setCancelable(false)
                .setPositiveButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SimpanHasilUjian().execute();
                        finish();
                    }
                });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("SPN POLDA NTB");
        alertDialogBuilder
                .setMessage("Terimakasih Sudah Mengikuti Ujian Hari Ini.")
                .setIcon(R.drawable.app_icon)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SimpanHasilUjian().execute();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    class SimpanHasilUjian extends AsyncTask<String, String, String> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainPreUjianHasil.this);
            pDialog.setMessage("Loading ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("p_nosis",txt_nosis.getText().toString()));
                params3.add(new BasicNameValuePair("p_soal",kd_soal.getText().toString()));
                params3.add(new BasicNameValuePair("p_mapel",p_mapel.getText().toString()));
                params3.add(new BasicNameValuePair("p_salah",txt_jwb_salah.getText().toString()));
                params3.add(new BasicNameValuePair("p_benar",txt_jwb_benar.getText().toString()));
                params3.add(new BasicNameValuePair("p_score",txt_score.getText().toString()));
                
                JSONObject json = jParser.makeHttpRequest(url_simpan, "GET", params3);
                string_json = json.getJSONArray("simpan_ujian");
                
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            //ambil objek pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            
                            if (ar.getString("status_simpan").equals("1")){
                                Toast.makeText(getApplicationContext(), "Terimakasih Sudah Mengikuti Ujian Hari Ini", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            }
                            
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
}