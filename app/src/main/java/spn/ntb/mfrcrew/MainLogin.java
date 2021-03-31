package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.SessionManager;
import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;

public class MainLogin extends AppCompatActivity implements View.OnClickListener {
	Animation animAlpha;
	SessionManager session;
	EditText ed_user, ed_pass;
	Button btn_login;
	int success;
	private static final String LOGIN_URL = "http://spn.ntb.polri.go.id/admin/service_android/login.php";
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_login);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	session = new SessionManager(getApplicationContext());
	ed_user = findViewById(R.id.ed_user);
	ed_pass = findViewById(R.id.ed_pass);
	btn_login = findViewById(R.id.btnLogin);
	btn_login.setOnClickListener(this);
}

public void onClick(View view) {
	// TODO Auto-generated method stub
	//view.startAnimation(animAlpha);
	if (ed_user.getText().toString().equals("")){
		Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
	}else if (ed_pass.getText().toString().equals("")){
		Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
	}else{
		switch (view.getId()){
			case R.id.btnLogin:
				if (ed_user.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
				}else if(ed_pass.getText().toString().length() > 18){
					Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
				}else{
					view.startAnimation(animAlpha);
					new AttempLogin().execute();
				}
				break;
			default:
				break;
		}
	}
}

public void forgot_password(View view) {
	view.startAnimation(animAlpha);
	Toast.makeText(getApplicationContext(), "Maaf Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
}

public void privacy_police(View view) {
	view.startAnimation(animAlpha);
	Toast.makeText(getApplicationContext(), "Fitur Belum Tersedia", Toast.LENGTH_SHORT).show();
}

class AttempLogin extends AsyncTask<String, String, String> {
	boolean failure = false;
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(MainLogin.this);
		pDialog.setMessage("Login...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	@Override
	protected String doInBackground(String... args){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("p_user2", ed_user.getText().toString()));
		params.add(new BasicNameValuePair("p_pass2", ed_pass.getText().toString()));
		JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
		try {
			success = json.getInt("success");
			
			if (success == 1) {
				Log.d("Login Successful!", json.toString());
				
				session.createSession1(json.getString("id_user"));
				session.createSession2(json.getString("nm_user"));
				session.createSession3(json.getString("hp_user"));
				session.createSession4(json.getString("email_user"));
				session.createSession5(json.getString("alamat_user"));
				session.createSession6(json.getString("akses_user"));
				session.createSession7(json.getString("foto_user"));
				Intent in = new Intent(getApplicationContext(), Main_Dashboard.class);
				finish();
				startActivity(in);
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
		if (file_url != null){
			Toast.makeText(MainLogin.this, file_url, Toast.LENGTH_LONG).show();
		}
	}
}

public void onBackPressed() {
	Intent i = new Intent(getApplicationContext(), MainHome.class);
	finish();
	startActivity(i);
}
@Override public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
	return true;
}

}