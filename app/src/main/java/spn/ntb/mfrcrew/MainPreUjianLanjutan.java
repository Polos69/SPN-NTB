package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import spn.ntb.mfrcrew.json.SessionManager;
import spn.ntb.mfrcrew.service_ujian.ImageLoader;
import spn.ntb.mfrcrew.service_ujian.ServiceHandler;
import spn.ntb.mfrcrew.service_ujian.Soal;

public class MainPreUjianLanjutan extends AppCompatActivity {

    Animation animAlpha;
    SessionManager session;
    
    TextView txtNama, txtNo, txtWaktu, txtSoal, kd_soal, p_mapel, p_tgl, p_pengampu;
    CardView btnPrev, btnSelesai, btnNext;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4;
    ImageView img;
    EditText inputNama;
    int jawabanYgDiPilih[] = null;
    int jawabanYgBenar[] = null;
    boolean cekPertanyaan = false;
    int urutanPertanyaan = 0;
    List<Soal> listSoal;
    JSONArray soal = null;
    CounterClass mCountDownTimer;
    private ProgressDialog pDialog;
    private static String url = "http://spn.ntb.polri.go.id/admin/service_android/soal.php";
    private static final String TAG_DAFTAR = "daftar_soal";
    private static final String TAG_ID = "soal_id";
    private static final String TAG_SOAL = "soal";
    private static final String TAG_A = "a";
    private static final String TAG_B = "b";
    private static final String TAG_C = "c";
    private static final String TAG_D = "d";
    private static final String TAG_JWB = "jawaban";
    private static final String TAG_GAMBAR = "gambar";
    public ImageLoader imageLoader;
    
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20;
    Button btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29, btn30, btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38, btn39, btn40;
    Button btn41, btn42, btn43, btn44, btn45, btn46, btn47, btn48, btn49, btn50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pre_ujian_lanjutan);
    
        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
        session = new SessionManager(getApplicationContext());
    
        listSoal = new ArrayList<Soal>();
        imageLoader = new ImageLoader(getApplicationContext());
        img = findViewById(R.id.gambar_ujian);
        txtNama = findViewById(R.id.textViewNama);
        txtNo = findViewById(R.id.textViewNo);
        txtWaktu = findViewById(R.id.textViewWaktu);
        txtSoal = findViewById(R.id.textViewSoal);
        kd_soal = findViewById(R.id.kd_soal);
        p_mapel = findViewById(R.id.p_mapel);
        p_tgl = findViewById(R.id.p_tgl);
        p_pengampu = findViewById(R.id.p_pengampu);
    
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            kd_soal.setText(bundle.getString("p_kd_soal"));
            p_mapel.setText(bundle.getString("p_matkul"));
            p_tgl.setText(bundle.getString("p_tgl"));
            p_pengampu.setText(bundle.getString("p_pengampu"));
        }else{
            kd_soal.setText(getIntent().getStringExtra("p_kd_soal"));
            p_mapel.setText(getIntent().getStringExtra("p_matkul"));
            p_tgl.setText(getIntent().getStringExtra("p_tgl"));
            p_pengampu.setText(getIntent().getStringExtra("p_pengampu"));
        }
        
        btnPrev = findViewById(R.id.buttonPrev);
        btnSelesai = findViewById(R.id.buttonSelesai);
        btnNext = findViewById(R.id.buttonNext);
        rg = findViewById(R.id.radioGroup1);
        rb1 = findViewById(R.id.radio0);
        rb2 = findViewById(R.id.radio1);
        rb3 = findViewById(R.id.radio2);
        rb4 = findViewById(R.id.radio3);
    
        btnSelesai.setVisibility(View.INVISIBLE);
    
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
    
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
        btn19 = findViewById(R.id.btn19);
        btn20 = findViewById(R.id.btn20);
    
        btn21 = findViewById(R.id.btn21);
        btn22 = findViewById(R.id.btn22);
        btn23 = findViewById(R.id.btn23);
        btn24 = findViewById(R.id.btn24);
        btn25 = findViewById(R.id.btn25);
        btn26 = findViewById(R.id.btn26);
        btn27 = findViewById(R.id.btn27);
        btn28 = findViewById(R.id.btn28);
        btn29 = findViewById(R.id.btn29);
        btn30 = findViewById(R.id.btn30);
    
        btn31 = findViewById(R.id.btn31);
        btn32 = findViewById(R.id.btn32);
        btn33 = findViewById(R.id.btn33);
        btn34 = findViewById(R.id.btn34);
        btn35 = findViewById(R.id.btn35);
        btn36 = findViewById(R.id.btn36);
        btn37 = findViewById(R.id.btn37);
        btn38 = findViewById(R.id.btn38);
        btn39 = findViewById(R.id.btn39);
        btn40 = findViewById(R.id.btn40);
    
        btn41 = findViewById(R.id.btn41);
        btn42 = findViewById(R.id.btn42);
        btn43 = findViewById(R.id.btn43);
        btn44 = findViewById(R.id.btn44);
        btn45 = findViewById(R.id.btn45);
        btn46 = findViewById(R.id.btn46);
        btn47 = findViewById(R.id.btn47);
        btn48 = findViewById(R.id.btn48);
        btn49 = findViewById(R.id.btn49);
        btn50 = findViewById(R.id.btn50);
    
        btn1.setOnClickListener(btn_no1);
        btn2.setOnClickListener(btn_no2);
        btn3.setOnClickListener(btn_no3);
        btn4.setOnClickListener(btn_no4);
        btn5.setOnClickListener(btn_no5);
        btn6.setOnClickListener(btn_no6);
        btn7.setOnClickListener(btn_no7);
        btn8.setOnClickListener(btn_no8);
        btn9.setOnClickListener(btn_no9);
        btn10.setOnClickListener(btn_no10);
    
        btn11.setOnClickListener(btn_no11);
        btn12.setOnClickListener(btn_no12);
        btn13.setOnClickListener(btn_no13);
        btn14.setOnClickListener(btn_no14);
        btn15.setOnClickListener(btn_no15);
        btn16.setOnClickListener(btn_no16);
        btn17.setOnClickListener(btn_no17);
        btn18.setOnClickListener(btn_no18);
        btn19.setOnClickListener(btn_no19);
        btn20.setOnClickListener(btn_no20);
    
        btn21.setOnClickListener(btn_no21);
        btn22.setOnClickListener(btn_no22);
        btn23.setOnClickListener(btn_no23);
        btn24.setOnClickListener(btn_no24);
        btn25.setOnClickListener(btn_no25);
        btn26.setOnClickListener(btn_no26);
        btn27.setOnClickListener(btn_no27);
        btn28.setOnClickListener(btn_no28);
        btn29.setOnClickListener(btn_no29);
        btn30.setOnClickListener(btn_no30);
    
        btn31.setOnClickListener(btn_no31);
        btn32.setOnClickListener(btn_no32);
        btn33.setOnClickListener(btn_no33);
        btn34.setOnClickListener(btn_no34);
        btn35.setOnClickListener(btn_no35);
        btn36.setOnClickListener(btn_no36);
        btn37.setOnClickListener(btn_no37);
        btn38.setOnClickListener(btn_no38);
        btn39.setOnClickListener(btn_no39);
        btn40.setOnClickListener(btn_no40);
    
        btn41.setOnClickListener(btn_no41);
        btn42.setOnClickListener(btn_no42);
        btn43.setOnClickListener(btn_no43);
        btn44.setOnClickListener(btn_no44);
        btn45.setOnClickListener(btn_no45);
        btn46.setOnClickListener(btn_no46);
        btn47.setOnClickListener(btn_no47);
        btn48.setOnClickListener(btn_no48);
        btn49.setOnClickListener(btn_no49);
        btn50.setOnClickListener(btn_no50);
    
        btnSelesai.setOnClickListener(klikSelesai);
        btnPrev.setOnClickListener(klikSebelum);
        btnNext.setOnClickListener(klikBerikut);
        new GetSoal().execute();
    }

private class GetSoal extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(MainPreUjianLanjutan.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    
    @Override
    protected Void doInBackground(Void... arg0) {
        ServiceHandler sh = new ServiceHandler();
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("kd_soal",kd_soal.getText().toString()));
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, params1);
        Log.d("Response: ", "> " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                soal = jsonObj.getJSONArray(TAG_DAFTAR);
                Soal s = null;
                for (int i = 0; i < soal.length(); i++) {
                    JSONObject c = soal.getJSONObject(i);
                    s = new Soal();
                    
                    String id = c.getString(TAG_ID);
                    String soal = c.getString(TAG_SOAL);
                    String a = c.getString(TAG_A);
                    String b = c.getString(TAG_B);
                    String cc = c.getString(TAG_C);
                    String d = c.getString(TAG_D);
                    String jwb = c.getString(TAG_JWB);
                    String gambar = c.getString(TAG_GAMBAR);
                    
                    s.setId(id);
                    s.setSoal(soal);
                    s.setA(a);
                    s.setB(b);
                    s.setC(cc);
                    s.setD(d);
                    s.setJawban(jwb);
                    s.setGambar(gambar);
                    
                    listSoal.add(s);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        
        jawabanYgDiPilih = new int[listSoal.size()];
        java.util.Arrays.fill(jawabanYgDiPilih, -1);
        jawabanYgBenar = new int[listSoal.size()];
        java.util.Arrays.fill(jawabanYgBenar, -1);
        setUpSoal();
    }
}

private void setUpSoal() {
    Collections.shuffle(listSoal);
    tunjukanPertanyaan(0, cekPertanyaan);
}

private void tunjukanPertanyaan(int urutan_soal_soal, boolean review) {
    btnSelesai.setEnabled(false);
    if(urutan_soal_soal == 0) {
        setUpWaktu();
    }
    
    try {
        rg.clearCheck();
        Soal soal = new Soal();
        soal = listSoal.get(urutan_soal_soal);
        if (jawabanYgBenar[urutan_soal_soal] == -1) {
            jawabanYgBenar[urutan_soal_soal] = Integer.parseInt(soal.getJawban());
        }
        
        String soalnya = soal.getSoal();
    
        if (soal.getGambar().equals("http://spn.ntb.polri.go.id/admin/service_android/images/")){
            img.setVisibility(View.GONE);
        }else{
            img.setVisibility(View.VISIBLE);
        }
        
        txtSoal.setText(soalnya);
        rg.check(-1);
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);
        //imageLoader.DisplayImage(soal.getGambar(), img);
    
        Glide.with(getApplicationContext())
                .load(soal.getGambar())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fitCenter()
                .into(img);
        
        rb1.setText(soal.getA());
        rb2.setText(soal.getB());
        rb3.setText(soal.getC());
        rb4.setText(soal.getD());
        
        Log.d("", jawabanYgDiPilih[urutan_soal_soal] + "");
        if (jawabanYgDiPilih[urutan_soal_soal] == 1)
            rg.check(R.id.radio0);
        if (jawabanYgDiPilih[urutan_soal_soal] == 2)
            rg.check(R.id.radio1);
        if (jawabanYgDiPilih[urutan_soal_soal] == 3)
            rg.check(R.id.radio2);
        if (jawabanYgDiPilih[urutan_soal_soal] == 4)
            rg.check(R.id.radio3);
        
        pasangLabelDanNomorUrut();
        
        if (urutan_soal_soal == (listSoal.size() - 1)) {
            btnNext.setEnabled(false);
            btnSelesai.setEnabled(true);
        }
        
        if (urutan_soal_soal == 0) {
            btnPrev.setEnabled(false);
        }
        
        if (urutan_soal_soal > 0) {
            btnPrev.setEnabled(true);
        }
        
        if (urutan_soal_soal < (listSoal.size() - 1)) {
            btnNext.setEnabled(true);
        }
        
        if (review) {
            mCountDownTimer.cancel();
            Log.d("priksa", jawabanYgDiPilih[urutan_soal_soal] + "" + jawabanYgBenar[urutan_soal_soal]);
            if (jawabanYgDiPilih[urutan_soal_soal] != jawabanYgBenar[urutan_soal_soal]) {
                if (jawabanYgDiPilih[urutan_soal_soal] == 1)
                    rb1.setTextColor(Color.RED);
                if (jawabanYgDiPilih[urutan_soal_soal] == 2)
                    rb2.setTextColor(Color.RED);
                if (jawabanYgDiPilih[urutan_soal_soal] == 3)
                    rb3.setTextColor(Color.RED);
                if (jawabanYgDiPilih[urutan_soal_soal] == 4)
                    rb4.setTextColor(Color.RED);
            }
            if (jawabanYgBenar[urutan_soal_soal] == 1)
                rb1.setTextColor(Color.GREEN);
            if (jawabanYgBenar[urutan_soal_soal] == 2)
                rb2.setTextColor(Color.GREEN);
            if (jawabanYgBenar[urutan_soal_soal] == 3)
                rb3.setTextColor(Color.GREEN);
            if (jawabanYgBenar[urutan_soal_soal] == 4)
                rb4.setTextColor(Color.GREEN);
        }
        
    } catch (Exception e) {
        Log.e(this.getClass().toString(), e.getMessage(), e.getCause());
    }
}

private View.OnClickListener klikSelesai = new View.OnClickListener() {
    public void onClick(View v) {
        
        aturJawaban_nya();
        // hitung berapa yg benar
        int jumlahJawabanYgBenar = 0;
        for (int i = 0; i < jawabanYgBenar.length; i++) {
            if ((jawabanYgBenar[i] != -1) && (jawabanYgBenar[i] == jawabanYgDiPilih[i])) {
                jumlahJawabanYgBenar++;
            }
        }
        
        AlertDialog tampilKotakAlert;
        tampilKotakAlert = new AlertDialog.Builder(MainPreUjianLanjutan.this).create();
        tampilKotakAlert.setTitle("SPN POLDA NTB");
        tampilKotakAlert.setIcon(R.drawable.logo_app);
        tampilKotakAlert.setCancelable(true);
        tampilKotakAlert.setMessage("Apakah anda yakin ingin mengakhiri ujian ini?");
        tampilKotakAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                aturJawaban_nya();
                // hitung berapa yg benar
                int jumlahJawabanYgBenar = 0;
                for (int i = 0; i < jawabanYgBenar.length; i++) {
                    if ((jawabanYgBenar[i] != -1) && (jawabanYgBenar[i] == jawabanYgDiPilih[i])) {
                        jumlahJawabanYgBenar++;
                    }
                }
                Intent i = new Intent(getApplicationContext(), MainPreUjianHasil.class);
                i.putExtra("score", ""+(jumlahJawabanYgBenar * 2));
                i.putExtra("benar", ""+jumlahJawabanYgBenar);
                i.putExtra("salah", ""+(50 - jumlahJawabanYgBenar));
                i.putExtra("p_kd_soal", kd_soal.getText().toString());
                i.putExtra("p_mapel", p_mapel.getText().toString());
                i.putExtra("p_tgl", p_tgl.getText().toString());
                i.putExtra("p_pengampu", p_pengampu.getText().toString());
                mCountDownTimer.cancel();
                startActivity(i);
            }
        });
        tampilKotakAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        tampilKotakAlert.show();
    }
};

//Pengaturan warna tombol lompat ke nomor yang di tuju ada di sini ya guys,
//syarat awal bahwa nomor itu belum terjawab sama sekali alyas warna merah
//Toast ganti pakai set color
private void aturJawaban_nya() {
    if (rb1.isChecked()) {
        jawabanYgDiPilih[urutanPertanyaan] = 1;
    }
    if (rb2.isChecked()) {
        jawabanYgDiPilih[urutanPertanyaan] = 2;
    }
    if (rb3.isChecked()) {
        jawabanYgDiPilih[urutanPertanyaan] = 3;
    }
    if (rb4.isChecked()) {
        jawabanYgDiPilih[urutanPertanyaan] = 4;
    }
    
    Log.d("", Arrays.toString(jawabanYgDiPilih));
    Log.d("", Arrays.toString(jawabanYgBenar));
    
    if (urutanPertanyaan == 0){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn1.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 1){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn2.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 2){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn3.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 3){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn4.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 4){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn5.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 5){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn6.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 6){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn7.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 7){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn8.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 8){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn9.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 9){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn10.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    
    if (urutanPertanyaan == 10){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn11.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 11){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn12.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 12){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn13.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 13){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn14.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 14){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn15.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 15){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn16.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 16){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn17.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 17){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn18.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 18){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn19.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 19){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn20.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    
    if (urutanPertanyaan == 20){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn21.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 21){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn22.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 22){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn23.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 23){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn24.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 24){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn25.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 25){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn26.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 26){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn27.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 27){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn28.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 28){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn29.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 29){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn30.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    
    if (urutanPertanyaan == 30){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn31.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 31){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn32.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 32){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn33.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 33){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn34.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 34){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn35.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 35){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn36.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 36){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn37.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 37){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn38.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 38){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn39.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 39){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn40.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    
    if (urutanPertanyaan == 40){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn41.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 41){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn42.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 42){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn43.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 43){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn44.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 44){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn45.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 45){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn46.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 46){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn47.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 47){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn48.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 48){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn49.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
    if (urutanPertanyaan == 49){
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
            btn50.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
}

private View.OnClickListener klikBerikut = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan++;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        
        if (urutanPertanyaan == 49){
            btnSelesai.setVisibility(View.VISIBLE);
        }
    }
};

//Digunakan untuk menuju ke nomor selanjutnya
private View.OnClickListener btn_no1 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=0; //Loginya dia menuju ke nomor 1
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no2 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=1; //Loginya dia menuju ke nomor 2
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no3 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=2; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no4 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=3; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no5 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=4; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no6 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=5; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no7 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=6; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no8 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=7; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no9 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=8; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no10 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=9; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no11 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=10; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no12 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=11; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no13 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=12; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no14 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=13; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no15 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=14; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no16 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=15; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no17 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=16; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no18 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=17; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no19 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=18; //Loginya dia menuju ke nomor 4
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no20 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=19;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no21 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=20;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no22 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=21;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no23 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=22;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no24 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=23;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no25 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=24;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no26 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=25;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no27 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=26;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no28 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=27;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no29 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=28;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no30 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=29;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no31 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=30;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no32 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=31;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no33 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=32;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no34 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=33;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no35 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=34;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no36 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=35;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no37 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=36;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no38 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=37;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no39 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=38;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no40 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=39;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no41 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=40;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no42 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=41;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no43 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=42;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no44 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=43;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no45 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=44;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no46 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=45;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no47 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=46;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no48 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=47;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no49 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=48;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};
private View.OnClickListener btn_no50 = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan=49;
        if (urutanPertanyaan >= listSoal.size()) {
            urutanPertanyaan = listSoal.size() - 1;
        }
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        
        btnSelesai.setVisibility(View.VISIBLE);
        
    }
};

private View.OnClickListener klikSebelum = new View.OnClickListener() {
    public void onClick(View v) {
        aturJawaban_nya();
        urutanPertanyaan--;
        if (urutanPertanyaan < 0)
            urutanPertanyaan = 0;
        
        tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        
        btnSelesai.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
};

private void pasangLabelDanNomorUrut() {
    txtNo.setText("No. " + (urutanPertanyaan + 1)+ " dari " + listSoal.size() + " soal");
}

private void setUpWaktu() {
    mCountDownTimer = new CounterClass(5401000, 1000);
    //mCountDownTimer = new CounterClass(3900, 1000);
    mCountDownTimer.start();
}

@SuppressLint("DefaultLocale")
public class CounterClass extends CountDownTimer {
    public CounterClass(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    
    //Waktu Berakhir Memunculkan Hasil Ujian
    @Override
    public void onFinish() {
        
        aturJawaban_nya();
        // hitung berapa yg benar
        int jumlahJawabanYgBenar = 0;
        for (int i = 0; i < jawabanYgBenar.length; i++) {
            if ((jawabanYgBenar[i] != -1) && (jawabanYgBenar[i] == jawabanYgDiPilih[i])) {
                jumlahJawabanYgBenar++;
            }
        }
    
        Intent i = new Intent(getApplicationContext(), MainPreUjianHasil.class);
        i.putExtra("score", ""+(jumlahJawabanYgBenar * 2));
        i.putExtra("benar", ""+jumlahJawabanYgBenar);
        i.putExtra("salah", ""+(50 - jumlahJawabanYgBenar));
        i.putExtra("p_kd_soal", kd_soal.getText().toString());
        i.putExtra("p_mapel", p_mapel.getText().toString());
        i.putExtra("p_tgl", p_tgl.getText().toString());
        i.putExtra("p_pengampu", p_pengampu.getText().toString());
        mCountDownTimer.cancel();
        finish();
        startActivity(i);
        
    }
    
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onTick(long millisUntilFinished) {
        long millis = millisUntilFinished;
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
        txtWaktu.setText(hms);
    }
}

public void onBackPressed() {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setTitle("SPN POLDA NTB");
    alertDialogBuilder
        .setMessage("Apakah anda yakin ingin mengakhiri ujian ini?")
        .setIcon(R.drawable.app_icon)
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
    
                aturJawaban_nya();
                // hitung berapa yg benar
                int jumlahJawabanYgBenar = 0;
                for (int i = 0; i < jawabanYgBenar.length; i++) {
                    if ((jawabanYgBenar[i] != -1) && (jawabanYgBenar[i] == jawabanYgDiPilih[i])) {
                        jumlahJawabanYgBenar++;
                    }
                }
    
                Intent i = new Intent(getApplicationContext(), MainPreUjianHasil.class);
                i.putExtra("score", ""+(jumlahJawabanYgBenar * 2));
                i.putExtra("benar", ""+jumlahJawabanYgBenar);
                i.putExtra("salah", ""+(50 - jumlahJawabanYgBenar));
                i.putExtra("p_kd_soal", kd_soal.getText().toString());
                i.putExtra("p_mapel", p_mapel.getText().toString());
                i.putExtra("p_tgl", p_tgl.getText().toString());
                i.putExtra("p_pengampu", p_pengampu.getText().toString());
                mCountDownTimer.cancel();
                startActivity(i);
                
            }
        })
        .setNegativeButton("No", null)
        .show();
}

@Override public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
}


}