package spn.ntb.mfrcrew.ui_siswa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.json.SessionManager;

public class Main_ItemBawah extends AppCompatActivity {

	Animation animAlpha;
	SessionManager session;
	
	FrameLayout btn_notif, btn_home, btn_setting;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.item_btn_bawah);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	session = new SessionManager(getApplicationContext());
	
	btn_notif = findViewById(R.id.btn_notif);
	btn_home = findViewById(R.id.btn_home);
	btn_setting = findViewById(R.id.btn_setting);
	
	btn_notif.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
		}
	});
	
	btn_home.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
			finish();
			startActivity(i);
		}
	});
	
	btn_setting.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
			startActivity(i);
		}
	});
}
}
