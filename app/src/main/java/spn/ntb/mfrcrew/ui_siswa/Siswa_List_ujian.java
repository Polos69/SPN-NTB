package spn.ntb.mfrcrew.ui_siswa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.LazyListUjian;

public class Siswa_List_ujian extends AppCompatActivity {
	Animation animAlpha;
	private ProgressDialog pDialogx;
	JSONParser jParserx = new JSONParser();
	ArrayList<HashMap<String, String>> ListUjian_A = new ArrayList<HashMap<String, String>>();
	private static final String url_list_ujian = "http://spn.ntb.polri.go.id/admin/service_android/info_ujian.php";
	public static final String TAG_KD_SOAL = "kd_soal";
	public static final String TAG_MATKUL = "matkul";
	public static final String TAG_TGL = "tgl_ujian";
	public static final String TAG_JAM1 = "jam_mulai";
	public static final String TAG_JAM2 = "jam_berakhir";

	public static final String HH_MULAI = "hh_mulai";
	public static final String HH_AKHIR = "hh_akhir";
	public static final String MM_MULAI = "mm_mulai";
	public static final String MM_AKHIR = "mm_akhir";
	
	JSONArray string_jsonx = null;
	ListView list;
	LazyListUjian adapter;
	TextView id;
	ImageView  info_ujian;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.siswa_list_ujian);
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	id = findViewById(R.id.post_text);
	list = findViewById(R.id.list);
	info_ujian = findViewById(R.id.info_ujian);
	new ListUjian().execute();
	
	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			HashMap<String, String> map = ListUjian_A.get(position);
			
			/*Intent in = new Intent(getApplicationContext(), MainDetailElektronik.class);
			in.putExtra(TAG_ID, map.get(TAG_ID));
			in.putExtra(TAG_JUDUL, map.get(TAG_JUDUL));
			startActivity(in);*/
		}
	});
}

public void SetListViewAdapter(ArrayList<HashMap<String, String>> verify) {
	adapter = new LazyListUjian(this, verify);
	list.setAdapter(adapter);
}

public void tulak(View view) {
	view.startAnimation(animAlpha);
	finish();
}


class ListUjian extends AsyncTask<String, String, String> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialogx = new ProgressDialog(Siswa_List_ujian.this);
		pDialogx.setMessage("Loading...");
		pDialogx.setIndeterminate(false);
		pDialogx.setCancelable(false);
		pDialogx.show();
	}
	
	protected String doInBackground(String... args) {
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("id",id.getText().toString()));
		JSONObject json = jParserx.makeHttpRequest(url_list_ujian, "GET", params1);
		
		try {
			string_jsonx = json.getJSONArray("info_ujian");
			
			for (int i = 0; i < string_jsonx.length(); i++) {
				JSONObject c = string_jsonx.getJSONObject(i);
				
				if (c.getString("status").equals("0")){
					runOnUiThread(new Runnable() {
						public void run() {
							info_ujian.setVisibility(View.VISIBLE);
							list.setVisibility(View.GONE);
						}
					});
				}else{
					info_ujian.setVisibility(View.GONE);
					list.setVisibility(View.VISIBLE);
					String kd_v = c.getString(TAG_KD_SOAL);
					String matkul_v = c.getString(TAG_MATKUL);
					String tgl_v = c.getString(TAG_TGL);
					String jam1_v = c.getString(TAG_JAM1);
					String jam2_v = c.getString(TAG_JAM2);
					
					String hh_mulai_v = c.getString(HH_MULAI);
					String hh_akhir_v = c.getString(HH_AKHIR);
					String mm_mulai_v = c.getString(MM_MULAI);
					String mm_akhir_v = c.getString(MM_AKHIR);
					
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put(TAG_KD_SOAL, kd_v);
					map.put(TAG_MATKUL, matkul_v);
					map.put(TAG_TGL, tgl_v);
					map.put(TAG_JAM1, jam1_v);
					map.put(TAG_JAM2, jam2_v);
					
					map.put(HH_MULAI, hh_mulai_v);
					map.put(HH_AKHIR, hh_akhir_v);
					map.put(MM_MULAI, mm_mulai_v);
					map.put(MM_AKHIR, mm_akhir_v);
					
					ListUjian_A.add(map);
				}
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
				SetListViewAdapter(ListUjian_A);
			}
		});
	}
}


}
