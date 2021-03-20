package spn.ntb.mfrcrew.ui_siswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import spn.ntb.mfrcrew.MainUbahPassword;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.json.SessionManager;

public class Siswa_Profile extends AppCompatActivity {

Animation animAlpha;
SessionManager sessionManager;

TextView txt_nama, txt_hp, txt_email;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.siswa_profile);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	sessionManager = new SessionManager(getApplicationContext());
	
	//=============================== Tangkap Hasil SESSION ===================================//
	txt_nama = findViewById(R.id.prof_nama);
	txt_hp = findViewById(R.id.prof_hp);
	txt_email = findViewById(R.id.prof_email);
	HashMap<String, String> id_user = sessionManager.getUserDetails();
	String namax = id_user.get(SessionManager.nm_user);
	String hpx = id_user.get(SessionManager.hp_user);
	String emailx = id_user.get(SessionManager.email_user);
	txt_nama.setText(Html.fromHtml("<b>" + namax + "</b>"));
	txt_hp.setText(Html.fromHtml("<b>" + hpx + "</b>"));
	txt_email.setText(Html.fromHtml("<b>" + emailx + "</b>"));
	//=============================== End Tangkap Hasil SESSION ===================================//
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
}

public void beri_ratting(View view) {
	view.startAnimation(animAlpha);
	/*Intent intent = new Intent();
	intent.setAction(Intent.ACTION_VIEW);
	intent.addCategory(Intent.CATEGORY_BROWSABLE);
	intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=samsat.school.bappenda"));
	startActivity(intent);*/
}

public void btn_privacy(View view) {
	view.startAnimation(animAlpha);
	//Intent i = new Intent(getApplicationContext(), MainPrivacyPolice.class);
	//startActivity(i);
}

public void btn_ganti_pass(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), MainUbahPassword.class);
	finish();
	startActivity(i);
}

public void apk_lainnya(View view) {
	view.startAnimation(animAlpha);
	/*Intent intent = new Intent();
	intent.setAction(Intent.ACTION_VIEW);
	intent.addCategory(Intent.CATEGORY_BROWSABLE);
	intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Bappenda+Provinsi+NTB"));
	startActivity(intent);*/
}

public void btn_home(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
	finish();
	startActivity(i);
}

public void onBackPressed() {
	Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
	finish();
	startActivity(i);
}
@Override public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
	return true;
}

}