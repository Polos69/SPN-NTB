package spn.ntb.mfrcrew;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;

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

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.LazyJadwal;
import spn.ntb.mfrcrew.json.SessionManager;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

public class MainJadwal extends AppCompatActivity {

	Animation animAlpha;
	SessionManager session;
	TextView id, tgl_search, tanggal;

	private ProgressDialog pDialogx;
	JSONParser jParserx = new JSONParser();
	ArrayList<HashMap<String, String>> ListJadwal_A = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> ListJadwal_B = new ArrayList<HashMap<String, String>>();
	private static final String url_jadwal = "http://spn.ntb.polri.go.id/admin/service_android/jadwal.php";
	public static final String TAG_JAM = "jam";
	public static final String TAG_MATKUL = "matkul";
	public static final String TAG_TGL = "tgl";
	public static final String TAG_SP = "sp";
	public static final String TAG_LALU = "lalu";
	public static final String TAG_TUANG = "tuang";
	public static final String TAG_SISA = "sisa";

	JSONArray string_jsonx = null;
	ListView list, list_jadwal2;
	LazyJadwal adapter, adapter2;
	Calendar myCalendar;
	RoundedImageView btn_callendar;
	
	ImageView btn_search;
	String Tahun, Bulan, Hari;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_jadwal);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	session = new SessionManager(getApplicationContext());
	
	id = findViewById(R.id.id);
	
	//=============================== Tangkap Hasil SESSION ===================================//
	HashMap<String, String> id_userz = session.getUserDetails();
	String nox = id_userz.get(SessionManager.id_user);
	id.setText(Html.fromHtml("<b>" + nox + "</b>"));
	//=============================== End Tangkap Hasil SESSION ===================================//
	
	list = findViewById(R.id.list_jadwal);
	list_jadwal2 = findViewById(R.id.list_jadwal2);
	
	myCalendar = Calendar.getInstance();
	tgl_search = findViewById(R.id.tgl_search);
	btn_callendar = findViewById(R.id.btn_callendar);
	tanggal = findViewById(R.id.tanggal);
	btn_callendar.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new DatePickerDialog(MainJadwal.this, new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
							myCalendar.set(Calendar.YEAR, year);
							myCalendar.set(Calendar.MONTH, month);
							myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							
							String formatTanggal = "dd-MM-yyyy";
							SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
							tgl_search.setText(sdf.format(myCalendar.getTime()));
						}
					},
							  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				}
			});
		}
	});
	
	btn_search = findViewById(R.id.btn_search);
	
	Calendar now =  Calendar.getInstance();
	int Y1 = now.get(Calendar.YEAR);
	int m1 = now.get(Calendar.MONTH) + 1;
	int d1 = now.get(Calendar.DAY_OF_MONTH);
	Tahun = String.valueOf(Y1);
	if (m1 <= 9){
		Bulan = "0"+String.valueOf(m1);
	}else{
		Bulan = String.valueOf(m1);
	}
	
	if (d1 <= 9){
		Hari = "0"+String.valueOf(d1);
	}else{
		Hari = String.valueOf(d1);
	}
	
	
	
	
	
	tanggal.setText(Hari+'-'+Bulan+'-'+Tahun);
	
	
	btn_search.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			
			tanggal.setText(tgl_search.getText().toString());
			list.setVisibility(View.GONE);
			list_jadwal2.setVisibility(View.VISIBLE);
			new ListJadwal2().execute();
		}
	});
	
	new ListJadwal().execute();
	
}

public void SetListViewAdapter(ArrayList<HashMap<String, String>> verify) {
	adapter = new LazyJadwal(this, verify);
	adapter2 = new LazyJadwal(this, verify);
	
	list.setAdapter(adapter);
	list_jadwal2.setAdapter(adapter2);
}

class ListJadwal extends AsyncTask<String, String, String> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialogx = new ProgressDialog(MainJadwal.this);
		pDialogx.setMessage("Loading...");
		pDialogx.setIndeterminate(false);
		pDialogx.setCancelable(false);
		pDialogx.show();
	}
	
	protected String doInBackground(String... args) {
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("tgl_search", tgl_search.getText().toString()));
		JSONObject json = jParserx.makeHttpRequest(url_jadwal, "GET", params1);
		
		try {
			string_jsonx = json.getJSONArray("info_jadwal");
			ListJadwal_A.clear();
			for (int i = 0; i < string_jsonx.length(); i++) {
				JSONObject c = string_jsonx.getJSONObject(i);
				
					String jam_v = c.getString(TAG_JAM);
					String matkul_v = c.getString(TAG_MATKUL);
					String jp_v = c.getString(TAG_SP);
					String lalu_v = c.getString(TAG_LALU);
					String tuang_v = c.getString(TAG_TUANG);
					String sisa_v = c.getString(TAG_SISA);
					String tgl_v = c.getString(TAG_TGL);
					
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put(TAG_JAM, jam_v);
					map.put(TAG_MATKUL, matkul_v);
					map.put(TAG_SP, jp_v);
					map.put(TAG_LALU, lalu_v);
					map.put(TAG_TUANG, tuang_v);
					map.put(TAG_SISA, sisa_v);
					map.put(TAG_TGL, tgl_v);
				
					ListJadwal_A.add(map);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String file_url) {
		pDialogx.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				SetListViewAdapter(ListJadwal_A);
			}
		});
	}
}

class ListJadwal2 extends AsyncTask<String, String, String> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialogx = new ProgressDialog(MainJadwal.this);
		pDialogx.setMessage("Loading...");
		pDialogx.setIndeterminate(false);
		pDialogx.setCancelable(false);
		pDialogx.show();
	}
	
	protected String doInBackground(String... args) {
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("tgl_search", tgl_search.getText().toString()));
		JSONObject json = jParserx.makeHttpRequest(url_jadwal, "GET", params1);
		
		try {
			string_jsonx = json.getJSONArray("info_jadwal");
			ListJadwal_B.clear();
			for (int i = 0; i < string_jsonx.length(); i++) {
				JSONObject c = string_jsonx.getJSONObject(i);
				
				String jam_v = c.getString(TAG_JAM);
				String matkul_v = c.getString(TAG_MATKUL);
				String jp_v = c.getString(TAG_SP);
				String lalu_v = c.getString(TAG_LALU);
				String tuang_v = c.getString(TAG_TUANG);
				String sisa_v = c.getString(TAG_SISA);
				String tgl_v = c.getString(TAG_TGL);
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put(TAG_JAM, jam_v);
				map.put(TAG_MATKUL, matkul_v);
				map.put(TAG_SP, jp_v);
				map.put(TAG_LALU, lalu_v);
				map.put(TAG_TUANG, tuang_v);
				map.put(TAG_SISA, sisa_v);
				map.put(TAG_TGL, tgl_v);
				
				ListJadwal_B.add(map);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String file_url) {
		pDialogx.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				SetListViewAdapter(ListJadwal_B);
			}
		});
	}
}


public void tulak(View view) {
	view.startAnimation(animAlpha);
	finish();
}

public void btn_home(View view) {
	view.startAnimation(animAlpha);
	finish();
}

public void btn_setting(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
	finish();
	startActivity(i);
}

public void btn_pengumuman(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), EmptyState.class);
	startActivity(i);
}

public void onBackPressed() {
	finish();
}

@Override public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
	return true;
}

}