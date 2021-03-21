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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.SessionManager;

public class MainPreUjian extends AppCompatActivity {
    Animation animAlpha;
    SessionManager session;
    TextView txt_matkul, txt_pengampu, txt_kd_soal, txt_tgl, txt_jam1, txt_jam2, hh_mulai, hh_akhir, mm_mulai, mm_akhir, txt_nosis, txt_status_ikut;
    CardView start_ujian;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static final String url_info_ujian = "http://spn.ntb.polri.go.id/admin/service_android/info_ujian.php";

    JSONArray string_json1 = null;
    private ProgressDialog pDialog1;
    JSONParser jParser1 = new JSONParser();
    private static final String url_info_ujian1 = "http://spn.ntb.polri.go.id/admin/service_android/info_ujian2.php";

    String Tahun, Bulan, Hari, Jam, Menit;
    
    Integer JamSQL1, MenitSQL1, JamSQL2, MenitSQL2, JamKomp1, MenitKompK2;
    Integer H_waktu_mulai, H_waktu_akhir, H_komp;
    
    
    LinearLayout line_u1, line_u2, line_info;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pre_ujian);
    
        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
        session = new SessionManager(getApplicationContext());
    
        txt_kd_soal = findViewById(R.id.kd_soal);
        txt_matkul = findViewById(R.id.txt_matkul);
        txt_tgl = findViewById(R.id.txt_tgl);
        txt_jam1 = findViewById(R.id.txt_jam1);
        txt_jam2 = findViewById(R.id.txt_jam2);
        hh_mulai = findViewById(R.id.hh_mulai);
        hh_akhir = findViewById(R.id.hh_akhir);
        mm_mulai = findViewById(R.id.mm_mulai);
        mm_akhir = findViewById(R.id.mm_akhir);
        txt_nosis = findViewById(R.id.txt_nosis);
        txt_pengampu = findViewById(R.id.txt_pengajar);
        txt_status_ikut = findViewById(R.id.txt_status_ikut);
    
        line_u1  = findViewById(R.id.lin_pre_ujian1);
        line_u2  = findViewById(R.id.bottt);
        line_info  = findViewById(R.id.line_info);
        
        //=============================== Tangkap Hasil SESSION ===================================//
            HashMap<String, String> id_userz = session.getUserDetails();
            String nox = id_userz.get(SessionManager.id_user);
            txt_nosis.setText(Html.fromHtml("<b>" + nox + "</b>"));
        //=============================== End Tangkap Hasil SESSION ===================================//
        
        new InfoUjian().execute();
        
        start_ujian = findViewById(R.id.start_ujian);
        start_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                Calendar now =  Calendar.getInstance();
                int Y1 = now.get(Calendar.YEAR);
                int m1 = now.get(Calendar.MONTH) + 1;
                int d1 = now.get(Calendar.DAY_OF_MONTH);
                int j1 = now.get(Calendar.HOUR_OF_DAY);
                int mm1 = now.get(Calendar.MINUTE);
                Tahun = String.valueOf(Y1);
                Hari = String.valueOf(d1);
                Jam = String.valueOf(j1);
                Menit =  String.valueOf(mm1);
                if (m1 <= 9){
                    Bulan = "0"+String.valueOf(m1);
                }else{
                    Bulan = String.valueOf(m1);
                }
    
                JamSQL1 = (Integer.parseInt(hh_mulai.getText().toString()) * 60) * 60;
                MenitSQL1 = Integer.parseInt(mm_mulai.getText().toString()) * 60;
                JamSQL2 = (Integer.parseInt(hh_akhir.getText().toString()) * 60) * 60;
                MenitSQL2 = Integer.parseInt(mm_akhir.getText().toString()) * 60;
                JamKomp1 = (Integer.parseInt(Jam) * 60) * 60;
                MenitKompK2 = Integer.parseInt(Menit) * 60;
                
                H_waktu_mulai = JamSQL1 + MenitSQL1;
                H_waktu_akhir = JamSQL2 + MenitSQL2;
                H_komp = JamKomp1 + MenitKompK2;
                
                if (txt_status_ikut.getText().toString().equals("0")) {
                    if (H_komp >= H_waktu_mulai) {
                        if (H_komp <= H_waktu_akhir) {
                            Intent i = new Intent(getApplicationContext(), MainPreUjianLanjutan.class);
                            i.putExtra("p_kd_soal", txt_kd_soal.getText().toString());
                            i.putExtra("p_matkul", txt_matkul.getText().toString());
                            i.putExtra("p_tgl", txt_tgl.getText().toString());
                            i.putExtra("p_jam1", txt_jam1.getText().toString());
                            i.putExtra("p_jam2", txt_jam2.getText().toString());
                            i.putExtra("p_nosis", txt_nosis.getText().toString());
                            i.putExtra("p_pengampu", txt_pengampu.getText().toString());
                            i.putExtra("p_hh_mulai", hh_mulai.getText().toString());
                            i.putExtra("p_hh_akhir", hh_akhir.getText().toString());
                            i.putExtra("p_mm_mulai", mm_mulai.getText().toString());
                            i.putExtra("p_mm_akhir", mm_akhir.getText().toString());
                            finish();
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Ujian3 " + txt_matkul.getText() + " Sudah Selesai", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Ujian4 " + txt_matkul.getText() + " Belum di Mulai", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Maaf Ujian Tidak Bisa Diikuti 2 Kali", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void btn_info_ujian(View view) {
        view.startAnimation(animAlpha);
        Intent i = new Intent(getApplicationContext(), EmptyState.class);
        finish();
        startActivity(i);
    }

    class InfoUjian extends AsyncTask<String, String, String> {
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainPreUjian.this);
            pDialog.setMessage("Loading ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        protected String doInBackground(String... params) {
            
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("nopol",txt_nosis.getText().toString()));
                JSONObject json1 = jParser.makeHttpRequest(url_info_ujian, "GET", params1);
                string_json = json1.getJSONArray("info_ujian");
                
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            //ambil objek pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            
                            if (ar.getString("status").equals("0")){
                                line_u1.setVisibility(View.GONE);
                                line_u2.setVisibility(View.GONE);
                                line_info.setVisibility(View.VISIBLE);
                                //Toast.makeText(getApplicationContext(), "Tidak Ada Ujian Hari Ini", Toast.LENGTH_LONG).show();
                            }else{
    
                                line_u1.setVisibility(View.VISIBLE);
                                line_u2.setVisibility(View.VISIBLE);
                                line_info.setVisibility(View.GONE);
                                
                                String kd_soal_d = ar.getString("kd_soal");
                                String matkul_d = ar.getString("matkul");
                                String tgl_ujian_d = ar.getString("tgl_ujian");
                                String jam_mulai_d = ar.getString("jam_mulai");
                                String jam_berakhir_d = ar.getString("jam_berakhir");
                                String hh_mulai_d = ar.getString("hh_mulai");
                                String hh_akhir_d = ar.getString("hh_akhir");
                                String mm_mulai_d = ar.getString("mm_mulai");
                                String mm_akhir_d = ar.getString("mm_akhir");
                                String pengampu_d = ar.getString("pengampu");
        
                                txt_kd_soal.setText(kd_soal_d);
                                txt_matkul.setText(matkul_d);
                                txt_tgl.setText(tgl_ujian_d);
                                txt_jam1.setText(jam_mulai_d);
                                txt_jam2.setText(jam_berakhir_d);
                                hh_mulai.setText(hh_mulai_d);
                                hh_akhir.setText(hh_akhir_d);
                                mm_mulai.setText(mm_mulai_d);
                                mm_akhir.setText(mm_akhir_d);
                                txt_pengampu.setText(pengampu_d);
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
            new InfoUjian2().execute();
        }
    }

    class InfoUjian2 extends AsyncTask<String, String, String> {
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(MainPreUjian.this);
            pDialog1.setMessage("Loading ... !");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(true);
            pDialog1.show();
        }
        
        protected String doInBackground(String... params) {
            
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("nosis",txt_nosis.getText().toString()));
                params1.add(new BasicNameValuePair("kd_soal",txt_kd_soal.getText().toString()));
                params1.add(new BasicNameValuePair("mapel",txt_matkul.getText().toString()));
                
                JSONObject json1 = jParser1.makeHttpRequest(url_info_ujian1, "GET", params1);
                string_json1 = json1.getJSONArray("info_ujian_x");
                
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            //ambil objek pertama dari JSON Array
                            JSONObject ar1 = string_json1.getJSONObject(0);
        
                            String status_d = ar1.getString("status_ikut");
                            txt_status_ikut.setText(status_d);
                            
                            
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
            pDialog1.dismiss();
        }
    }
    
    public void tulak(View view) {
        view.startAnimation(animAlpha);
        finish();
    }
}