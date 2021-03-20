package spn.ntb.mfrcrew.ui_siswa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import spn.ntb.mfrcrew.MainGaleri;
import spn.ntb.mfrcrew.MainHasilUjian;
import spn.ntb.mfrcrew.MainHome;
import spn.ntb.mfrcrew.MainJadwalSementara;
import spn.ntb.mfrcrew.MainProfil;
import spn.ntb.mfrcrew.MainSejarah;
import spn.ntb.mfrcrew.MainSiapUjian;
import spn.ntb.mfrcrew.MainStandar;
import spn.ntb.mfrcrew.MainVisi;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.SessionManager;

public class Main_Dashboard extends AppCompatActivity {

	Animation animAlpha;
	SessionManager session;

	TextView txt_user, txt_akses, txt_hari;

	//============ DIALOG ================//
		AlertDialog.Builder dialog;
		LayoutInflater inflater;
		View dialogView;
		EditText ed_nama, ed_email, ed_saran;
		ImageButton btn_kritik_saran;
	//============ END DIALOG ================//
	Spinner sp_kategori;
	
	int success;
	private static final String LOGIN_URL = "http://spn.ntb.polri.go.id/admin/service_android/kritik_saran.php";
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	
	LinearLayout lin_ujian, lin_ujian2;
	String Tahun, Bulan, Hari;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.siswa_dashboard);
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	session = new SessionManager(getApplicationContext());
	
	txt_user = findViewById(R.id.txt_user);
	txt_akses = findViewById(R.id.txt_akses);
	lin_ujian = findViewById(R.id.lin_ujian);
	lin_ujian2 = findViewById(R.id.lin_ujian2);
	txt_hari = findViewById(R.id.txt_hari);
	//=============================== Tangkap Hasil SESSION ===================================//
		HashMap<String, String> id_user = session.getUserDetails();
		String namax = id_user.get(SessionManager.nm_user);
		String aksesx = id_user.get(SessionManager.akses_user);
		txt_user.setText(Html.fromHtml("<b>" + namax + "</b>"));
		txt_akses.setText(Html.fromHtml("<b>" + aksesx + "</b>"));
	//=============================== End Tangkap Hasil SESSION ===================================//
	
	Calendar now =  Calendar.getInstance();
	SimpleDateFormat tgl_skarang = new SimpleDateFormat("EEEE, d MMMM yyyy");
	String V_tgl_skarang = tgl_skarang.format(now.getTime());
	txt_hari.setText(V_tgl_skarang);
	
	if (txt_akses.getText().toString().equals("1")){
		lin_ujian.setVisibility(View.VISIBLE);
		lin_ujian2.setVisibility(View.GONE);
	}else{
		lin_ujian.setVisibility(View.GONE);
		lin_ujian2.setVisibility(View.VISIBLE);
	}
	
	btn_kritik_saran = findViewById(R.id.btn_kritik_saran);
	btn_kritik_saran.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			DialogForm();
		}
	});
	
}

private void DialogForm() {
	dialog = new AlertDialog.Builder(this);
	inflater = getLayoutInflater();
	dialogView = inflater.inflate(R.layout.item_kritik_saran, null);
	dialog.setView(dialogView);
	dialog.setCancelable(true);
	dialog.setIcon(R.drawable.app_icon);
	dialog.setTitle("Saran dan Masukkan");
	
	sp_kategori = dialogView.findViewById(R.id.sp_kategori);
	ed_nama = dialogView.findViewById(R.id.ed_nama);
	ed_email = dialogView.findViewById(R.id.ed_email);
	ed_saran = dialogView.findViewById(R.id.ed_pesan);
	
	dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	});
	
	dialog.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			if (ed_nama.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
			}else if (ed_email.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
			}else if (ed_saran.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "Silahkan Masukkan Kritik, Saran dan Masukkan Anda", Toast.LENGTH_SHORT).show();
			}else{
				new SimpanKritikSaran().execute();
				dialog.dismiss();
			}
		}
	});
	dialog.show();
}

public void btn_jadwal(View view) {
	view.startAnimation(animAlpha);
	//DialogForm3();
	Intent i = new Intent(getApplicationContext(), MainJadwalSementara.class);
	startActivity(i);
}

private void DialogForm3() {
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	
	alertDialogBuilder.setTitle("INFO JADWAL");
	
	// set pesan dari dialog
	alertDialogBuilder
			  .setMessage("Jadwal Belum Ada")
			  .setIcon(R.drawable.app_icon)
			  .setCancelable(false)
			  .setPositiveButton("OK",new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog,int id) {
					  dialog.cancel();
				  }
			  });
	
	AlertDialog alertDialog = alertDialogBuilder.create();
	alertDialog.show();
}

public void btn_standar(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainStandar.class);
	startActivity(i);
}


class SimpanKritikSaran extends AsyncTask<String, String, String> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(Main_Dashboard.this);
		pDialog.setMessage("Login...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	@Override
	protected String doInBackground(String... args){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("p_kategori", sp_kategori.getSelectedItem().toString()));
		params.add(new BasicNameValuePair("p_nama", ed_nama.getText().toString()));
		params.add(new BasicNameValuePair("p_email", ed_email.getText().toString()));
		params.add(new BasicNameValuePair("p_saran", ed_saran.getText().toString()));
		JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
		try {
			success = json.getInt("success");
			
			if (success == 1) {
				Log.d("Login Failure!", json.getString("message"));
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
		Toast.makeText(getApplicationContext(), "Terimakasih Atas Saran dan Masukkan Anda", Toast.LENGTH_SHORT).show();
	}
}

public void btn_ujian(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainSiapUjian.class);
	startActivity(i);
}


private void DialogForm2() {
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	
	alertDialogBuilder.setTitle("INFO UJIAN");
	
	// set pesan dari dialog
	alertDialogBuilder
			  .setMessage("Ujian Belum di Mulai")
			  .setIcon(R.drawable.app_icon)
			  .setCancelable(false)
			  
			  .setNegativeButton("OK",new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int id) {
					  dialog.cancel();
				  }
			  });
	
	AlertDialog alertDialog = alertDialogBuilder.create();
	alertDialog.show();
}

public void btn_setting(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
	finish();
	startActivity(i);
}

public void btn_home(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
	finish();
	startActivity(i);
}

public void btn_library(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), SiswaLibrary.class);
	finish();
	startActivity(i);
}

public void btn_profilex(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainProfil.class);
	//finish();
	startActivity(i);
}

public void btn_sejarah(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainSejarah.class);
	startActivity(i);
}

public void btn_visi_misi(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainVisi.class);
	startActivity(i);
}

public void btn_galeri(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainGaleri.class);
	startActivity(i);
}

public void onBackPressed() {
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	alertDialogBuilder.setTitle("SPN POLDA NTB");
	
	alertDialogBuilder
		  .setMessage("Apakah anda yakin keluar aplikasi?")
		  .setIcon(R.drawable.app_icon)
		  .setCancelable(false)
		  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int id) {
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

}
