package spn.ntb.mfrcrew.ui_siswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

import spn.ntb.mfrcrew.MainHome;
import spn.ntb.mfrcrew.MainUbahPassword;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.Siswa_Ubah_Profil;
import spn.ntb.mfrcrew.json.SessionManager;

public class Siswa_Profile extends AppCompatActivity {

Animation animAlpha;
SessionManager sessionManager;

TextView txt_nama, ld_hambar;

RoundedImageView fotoprof;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.siswa_profile);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	sessionManager = new SessionManager(getApplicationContext());
	
	//=============================== Tangkap Hasil SESSION ===================================//
	txt_nama = findViewById(R.id.prof_nama);
	ld_hambar = findViewById(R.id.ld_hambar);
	HashMap<String, String> id_user = sessionManager.getUserDetails();
	String namax = id_user.get(SessionManager.nm_user);
	String fotox = id_user.get(SessionManager.foto_user);
	txt_nama.setText(Html.fromHtml("<b>" + namax + "</b>"));
	ld_hambar.setText(Html.fromHtml("<b>" + fotox + "</b>"));
	//=============================== End Tangkap Hasil SESSION ===================================//
	
	fotoprof = findViewById(R.id.fotoprof);
	
	Glide.with(getApplicationContext())
			  .load("http://spn.ntb.polri.go.id/admin/service_android/images/profile/"+ld_hambar.getText().toString())
			  .placeholder(R.drawable.placeholder)
			  .error(R.drawable.placeholder)
			  .fitCenter()
			  .into(fotoprof);
	
}

public void tulak(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
	finish();
	startActivity(i);
}

public void Keluar(View view) {
	view.startAnimation(animAlpha);
	showDialog();
}

private void showDialog(){
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	alertDialogBuilder.setTitle("Log Out");
	// set pesan dari dialog
	alertDialogBuilder
		  .setMessage("Apakah anda yakin ingin keluar?")
		  .setIcon(R.drawable.app_icon)
		  .setCancelable(false)
		  .setPositiveButton("Tidak",new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog,int id) {
				  dialog.cancel();
			  }
		  })
		  .setNegativeButton("Ya",new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int id) {
				  finish();
				  new Main_Dashboard().finish();
				  sessionManager.logout();
			  }
		  });
	AlertDialog alertDialog = alertDialogBuilder.create();
	alertDialog.show();
}

public void btn_edit(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Siswa_Ubah_Profil.class);
	finish();
	startActivity(i);
}

public void btn_home(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
	finish();
	startActivity(i);
}

public void onBackPressed() {
	Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
	finish();
	startActivity(i);
}
@Override public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
	return true;
}

public void btn_update_data(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainUbahPassword.class);
	finish();
	startActivity(i);
}

public void btn_web(View view) {
	view.startAnimation(animAlpha);
	Toast.makeText(getApplicationContext(), "FItur Belum Tersedia", Toast.LENGTH_SHORT).show();
}
}