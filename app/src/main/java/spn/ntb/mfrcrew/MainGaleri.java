package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.LazyGaleri;
import spn.ntb.mfrcrew.json.SessionManager;

public class MainGaleri extends AppCompatActivity {
	Animation animAlpha;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> DaftarElektronik = new ArrayList<HashMap<String, String>>();
	private static String url_galeri = "https://spn.ntb.polri.go.id/admin/service_android/galeri.php";
	public static final String TAG_JUDUL = "judul";
	public static final String TAG_GAMBAR = "gambar_nama";
	
	JSONArray string_json = null;
	GridView list;
	LazyGaleri adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_galeri);
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	DaftarElektronik = new ArrayList<HashMap<String, String>>();
	new AmbilData().execute();
	
	list = (GridView) findViewById(R.id.list_galeri);
}

public void SetListViewAdapter(ArrayList<HashMap<String, String>> elektronik) {
	adapter = new LazyGaleri(this, elektronik);
	list.setAdapter(adapter);
}

public void tulak(View view) {
	view.startAnimation(animAlpha);
	finish();
}

class AmbilData extends AsyncTask<String, String, String> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(MainGaleri.this);
		pDialog.setMessage("Loading...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject json = jParser.makeHttpRequest(url_galeri, "GET",params);
		
		try {
			string_json = json.getJSONArray("daftar_galeri");
			for (int i = 0; i < string_json.length(); i++) {
				JSONObject c = string_json.getJSONObject(i);
				
				String jdul_d =  c.getString(TAG_JUDUL);
				String link_image =  c.getString(TAG_GAMBAR);
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put(TAG_JUDUL, jdul_d);
				map.put(TAG_GAMBAR, link_image);
				
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

}