package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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

public class MainJadwalDetail extends AppCompatActivity {
    
    Animation animAlpha;
    SessionManager session;
    
    TextView id, jam, mapel, nm_gadik, jp, lalu, tuang, sisa, tgl, txt_akses;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static final String url_detail_ujian = "http://spn.ntb.polri.go.id/admin/service_android/detail_jadwal.php";
    
    JSONArray string_json2 = null;
    private ProgressDialog pDialog2;
    JSONParser jParser2 = new JSONParser();
    private static final String url_absen = "http://spn.ntb.polri.go.id/admin/service_android/absen.php";

    JSONArray string_json3 = null;
    private ProgressDialog pDialog3;
    JSONParser jParser3 = new JSONParser();
    private static final String url_stts_absen = "http://spn.ntb.polri.go.id/admin/service_android/stts_absen.php";
    
    int success;
    
    CardView btn_absen, btn_materi;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jadwal_detail);
    
        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
        session = new SessionManager(getApplicationContext());
    
        id = findViewById(R.id.id);
        jam = findViewById(R.id.jam);
        mapel = findViewById(R.id.mapel);
        nm_gadik = findViewById(R.id.nm_gadik);
        jp = findViewById(R.id.jp);
        lalu = findViewById(R.id.lalu);
        tuang = findViewById(R.id.tuang);
        sisa = findViewById(R.id.sisa);
        tgl = findViewById(R.id.tgl);
        btn_absen = findViewById(R.id.btn_absen);
        btn_materi = findViewById(R.id.btn_materi);
        txt_akses = findViewById(R.id.txt_akses);
        btn_absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                new SimpanAbsen().execute();
            }
        });
        
        btn_materi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent i = new Intent(getApplicationContext(), MainAksesMateri.class);
                i.putExtra("mapel", mapel.getText().toString());
                startActivity(i);
            }
        });
        
        //=============================== Tangkap Hasil SESSION ===================================//
            HashMap<String, String> id_userz = session.getUserDetails();
            String nox = id_userz.get(SessionManager.id_user);
            String aksesx = id_userz.get(SessionManager.akses_user);
            id.setText(Html.fromHtml("<b>" + nox + "</b>"));
            txt_akses.setText(Html.fromHtml("<b>" + aksesx + "</b>"));
        //=============================== End Tangkap Hasil SESSION ===================================//
    
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            jam.setText(bundle.getString("txt_jam"));
            mapel.setText(bundle.getString("txt_mapel"));
            jp.setText(bundle.getString("txt_jp"));
            lalu.setText(bundle.getString("txt_lalu"));
            tuang.setText(bundle.getString("txt_tuang"));
            sisa.setText(bundle.getString("txt_sisa"));
            tgl.setText(getIntent().getStringExtra("txt_tgl"));
        }else{
            jam.setText(getIntent().getStringExtra("txt_jam"));
            mapel.setText(getIntent().getStringExtra("txt_mapel"));
            jp.setText(getIntent().getStringExtra("txt_jp"));
            lalu.setText(getIntent().getStringExtra("txt_lalu"));
            tuang.setText(getIntent().getStringExtra("txt_tuang"));
            sisa.setText(getIntent().getStringExtra("txt_sisa"));
            tgl.setText(getIntent().getStringExtra("txt_tgl"));
        }
        
        new NamaGadik().execute();
    
        if (txt_akses.getText().toString().equals("1")){
            new StatusAbsen().execute();
        }else{
            btn_absen.setVisibility(View.GONE);
            btn_materi.setVisibility(View.VISIBLE);
        }
        
        
    }

    class NamaGadik extends AsyncTask<String, String, String> {
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(MainJadwalDetail.this);
        pDialog.setMessage("Loading ... !");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    
    protected String doInBackground(String... params) {
        
        try {
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("mapel",mapel.getText().toString()));
            JSONObject json1 = jParser.makeHttpRequest(url_detail_ujian, "GET", params1);
            string_json = json1.getJSONArray("info_jadwal_detail");
            
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        
                        JSONObject ar = string_json.getJSONObject(0);
                        
                        String pengampu_d = ar.getString("pengampu");
                        nm_gadik.setText(pengampu_d);
                        
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

    class StatusAbsen extends AsyncTask<String, String, String> {
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog3 = new ProgressDialog(MainJadwalDetail.this);
            pDialog3.setMessage("Loading ... !");
            pDialog3.setIndeterminate(false);
            pDialog3.setCancelable(true);
            pDialog3.show();
        }
        
        protected String doInBackground(String... params) {
            
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("nosis",id.getText().toString()));
                params1.add(new BasicNameValuePair("mapel",mapel.getText().toString()));
                JSONObject json3 = jParser3.makeHttpRequest(url_stts_absen, "GET", params1);
                string_json3 = json3.getJSONArray("info_stts_absen");
                
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            JSONObject ar = string_json3.getJSONObject(0);
                            
                            if (ar.getString("status_absen_x").equals("1")){
                                btn_absen.setVisibility(View.GONE);
                                btn_materi.setVisibility(View.VISIBLE);
                            }else{
                                btn_absen.setVisibility(View.VISIBLE);
                                btn_materi.setVisibility(View.GONE);
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
            pDialog3.dismiss();
        }
    }
    
    public void tulak(View view) {
        view.startAnimation(animAlpha);
        finish();
    }

    class SimpanAbsen extends AsyncTask<String, String, String> {
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(MainJadwalDetail.this);
            pDialog2.setMessage("Loading ... !");
            pDialog2.setIndeterminate(false);
            pDialog2.setCancelable(true);
            pDialog2.show();
        }
        
        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("p_nosis",id.getText().toString()));
                params3.add(new BasicNameValuePair("p_mapel",mapel.getText().toString()));
                params3.add(new BasicNameValuePair("p_gadik",nm_gadik.getText().toString()));
                params3.add(new BasicNameValuePair("p_tgl",tgl.getText().toString()));
                
                JSONObject json2 = jParser2.makeHttpRequest(url_absen, "GET", params3);
                string_json2 = json2.getJSONArray("simpan_absen");
                
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            //ambil objek pertama dari JSON Array
                            JSONObject ar = string_json2.getJSONObject(0);
                            
                            if (ar.getString("status").equals("1")){
                                Toast.makeText(getApplicationContext(), "Terimakasih Sudah Melakukan Absensi", Toast.LENGTH_SHORT).show();
                                btn_absen.setVisibility(View.GONE);
                                btn_materi.setVisibility(View.VISIBLE);
                            }else{
                                Toast.makeText(getApplicationContext(), "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                btn_absen.setVisibility(View.VISIBLE);
                                btn_materi.setVisibility(View.GONE);
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
            pDialog2.dismiss();
        }
    }
}