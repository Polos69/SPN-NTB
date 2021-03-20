package spn.ntb.mfrcrew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import spn.ntb.mfrcrew.json.SessionManager;

public class MainActivity extends AppCompatActivity {
	SessionManager sessionManager;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
	
	if(networkInfo != null && networkInfo.isConnected()){
		
		sessionManager = new SessionManager(getApplicationContext());
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				sessionManager.checkLogin();
				finish();
			}
		},1000);
		
	}else{
		AlertDialog.Builder alert = new AlertDialog.Builder(this)
				  .setTitle("Tidak Ada Koneksi Internet")
				  .setMessage("Silakan periksa koneksi internet anda dan coba lagi")
				  .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
					  @Override
					  public void onClick(DialogInterface dialogInterface, int i) {
						  finish();
					  }
				  });
		alert.setCancelable(false);
		alert.show();
	}
}
@Override
protected void onDestroy() {
	super.onDestroy();
}
}