package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.LazyGaleri;
import spn.ntb.mfrcrew.json.LazyLibrary2;
import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

public class MainSiswaLibrary2 extends AppCompatActivity {

	Animation animAlpha;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> DaftarElektronik = new ArrayList<HashMap<String, String>>();
	private static String url_galeri = "https://spn.ntb.polri.go.id/admin/service_android/library2.php";
	public static final String TAG_JUDUL = "judul";
	public static final String TAG_GAMBAR = "gambar";
	public static final String TAG_GAMBAR2 = "gambar2";
	
	JSONArray string_json = null;
	GridView list;
	LazyLibrary2 adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_siswa_library2);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	DaftarElektronik = new ArrayList<HashMap<String, String>>();
	new AmbilData().execute();
	list = (GridView) findViewById(R.id.list_galeri);
}

public void SetListViewAdapter(ArrayList<HashMap<String, String>> elektronik) {
	adapter = new LazyLibrary2(this, elektronik);
	list.setAdapter(adapter);
}

class AmbilData extends AsyncTask<String, String, String> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(MainSiswaLibrary2.this);
		pDialog.setMessage("Loading...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject json = jParser.makeHttpRequest(url_galeri, "GET",params);
		
		try {
			string_json = json.getJSONArray("data_librari");
			for (int i = 0; i < string_json.length(); i++) {
				JSONObject c = string_json.getJSONObject(i);
				
				String d_judul =  c.getString(TAG_JUDUL);
				String link_image =  c.getString(TAG_GAMBAR);
				String link_image2 =  c.getString(TAG_GAMBAR2);
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put(TAG_JUDUL, d_judul);
				map.put(TAG_GAMBAR, link_image);
				map.put(TAG_GAMBAR2, link_image2);
				
				DaftarElektronik.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String file_url) {
		pDialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				SetListViewAdapter(DaftarElektronik);
			}
		});
	}
}

public void tulak(View view) {
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
}