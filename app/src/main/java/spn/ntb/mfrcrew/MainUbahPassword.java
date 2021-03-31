package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.SessionManager;
import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainUbahPassword extends AppCompatActivity {

    SessionManager sessionManager;
    Animation animAlpha;
    
    EditText ed_pass_lama, ed_pass_baru, ed_pass_baru_lagi;
    CardView tombol_ganti;

    private static final String url_update = "http://spn.ntb.polri.go.id/admin/service_android/ubah_password.php";
    JSONParser jsonParser = new JSONParser();
    JSONArray string_json = null;
    private ProgressDialog pDialog;
    int success;
    
    TextView id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ubah_password);
    
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_hilang);
        sessionManager = new SessionManager(getApplicationContext());
    
        //=============================== Tangkap Hasil SESSION ===================================//
        id = findViewById(R.id.id);
        HashMap<String, String> id_user = sessionManager.getUserDetails();
        String idx = id_user.get(SessionManager.id_user);
        id.setText(Html.fromHtml("<b>" + idx + "</b>"));
        //=============================== End Tangkap Hasil SESSION ===================================//
        
        ed_pass_lama = findViewById(R.id.ed_pass_lama);
        ed_pass_baru = findViewById(R.id.ed_pass_baru);
        ed_pass_baru_lagi = findViewById(R.id.ed_pass_baru_lagi);
        tombol_ganti = findViewById(R.id.tombol_ganti);
        tombol_ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                new UpdateData().execute();
            }
        });
        
        
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
        finish();
        startActivity(i);
    }

    public void tulak(View view) {
        view.startAnimation(animAlpha);
        Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
        finish();
        startActivity(i);
    }

    class UpdateData extends AsyncTask<String, String, String> {
    boolean failure = false;
    
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(MainUbahPassword.this);
        pDialog.setMessage("Login...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    
    @Override
    protected String doInBackground(String... args){
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("p_nosis", id.getText().toString()));
        params.add(new BasicNameValuePair("p_lama", ed_pass_lama.getText().toString()));
        params.add(new BasicNameValuePair("p_baru1", ed_pass_baru.getText().toString()));
        params.add(new BasicNameValuePair("p_baru2", ed_pass_baru_lagi.getText().toString()));
        
        JSONObject json = jsonParser.makeHttpRequest(url_update, "POST", params);
        
        try {
            success = json.getInt("success");
            
            if (success == 1) {
                Log.d("Lengkapi Data Diri Anda", json.toString());
                finish();
                sessionManager.logout();
                return json.getString("message");
            }else if(success == 0){
                Log.d("Inputan Masih Kosong", json.toString());
                return json.getString("message");
            }else{
                Log.d("Login Failure!", json.getString("message"));
                return json.getString("message");
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return "error";
    }
    
    protected void onPostExecute(String file_url){
        // dismiss the dialog once product deleted
        pDialog.dismiss();
    }
}
}