package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.LazyGaleri;
import spn.ntb.mfrcrew.json.LazyMateri;
import spn.ntb.mfrcrew.json.SessionManager;

public class MainAksesMateri extends AppCompatActivity {

	Animation animAlpha;
	SessionManager session;
	
	TextView txt_mapel;
	GridView list_materi;

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> DaftarMateri = new ArrayList<HashMap<String, String>>();
	private static String url_materi = "http://spn.ntb.polri.go.id/admin/service_android/materi.php";
	public static final String TAG_GAMBAR = "gambar";
	public static final String TAG_JUDUL = "judul";
	public static final String TAG_NM_GAMBAR = "nm_gambar";
	
	JSONArray string_json = null;
	LazyMateri adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_akses_materi);
		
		animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
		session = new SessionManager(getApplicationContext());
		
		txt_mapel = findViewById(R.id.txt_mapel);
		list_materi = findViewById(R.id.list_materi);
		
		if (getIntent().getExtras() != null) {
			Bundle bundle = getIntent().getExtras();
			txt_mapel.setText(bundle.getString("mapel"));
		}else{
			txt_mapel.setText(getIntent().getStringExtra("mapel"));
		}
		
		new AmbilData().execute();
		
	}

	public void SetListViewAdapter(ArrayList<HashMap<String, String>> elektronik) {
		adapter = new LazyMateri(this, elektronik);
		list_materi.setAdapter(adapter);
	}
	
	public void tulak(View view) {
		view.startAnimation(animAlpha);
		finish();
	}

	class AmbilData extends AsyncTask<String, String, String> {
	
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(MainAksesMateri.this);
			pDialog.setMessage("Login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mapel",txt_mapel.getText().toString()));
			JSONObject json = jParser.makeHttpRequest(url_materi, "GET",params);
			
			try {
				string_json = json.getJSONArray("daftar_materi");
				for (int i = 0; i < string_json.length(); i++) {
					JSONObject c = string_json.getJSONObject(i);
					
					String p_judul =  c.getString(TAG_JUDUL);
					String p_nm_gambar =  c.getString(TAG_NM_GAMBAR);
					String link_image =  c.getString(TAG_GAMBAR);
					
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put(TAG_JUDUL, p_judul);
					map.put(TAG_NM_GAMBAR, p_nm_gambar);
					map.put(TAG_GAMBAR, link_image);
					
					DaftarMateri.add(map);
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
					SetListViewAdapter(DaftarMateri);
				}
			});
		}
	}
	
}