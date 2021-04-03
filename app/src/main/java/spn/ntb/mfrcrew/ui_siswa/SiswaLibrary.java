package spn.ntb.mfrcrew.ui_siswa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.array_adapterlist.AdapterList;

public class SiswaLibrary extends AppCompatActivity {

	Animation animAlpha;
	private RecyclerView rc_recently, rc_kategori;
	
	private RequestQueue requestQueue;
	private StringRequest stringRequest;
	
	ArrayList<HashMap<String, String>> list_data;
	
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.siswa_library);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	
	String url = "http://spn.ntb.polri.go.id/admin/service_android/library.php";
	
	rc_recently = (RecyclerView) findViewById(R.id.rc_recently);
	rc_kategori = (RecyclerView) findViewById(R.id.rc_kategori);
	LinearLayoutManager llm = new LinearLayoutManager(this);
	llm.setOrientation(LinearLayoutManager.HORIZONTAL);
	LinearLayoutManager llm2 = new LinearLayoutManager(this);
	llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
	rc_recently.setLayoutManager(llm);
	rc_kategori.setLayoutManager(llm2);
	
	requestQueue = Volley.newRequestQueue(SiswaLibrary.this);
	
	list_data = new ArrayList<HashMap<String, String>>();
	
	stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.d("response ", response);
			try {
				JSONObject jsonObject = new JSONObject(response);
				JSONArray jsonArray = jsonObject.getJSONArray("data_librari");
				for (int a = 0; a < jsonArray.length(); a++) {
					JSONObject json = jsonArray.getJSONObject(a);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("judul", json.getString("judul"));
					map.put("gambar", json.getString("gambar"));
					
					list_data.add(map);
					AdapterList adapter = new AdapterList(SiswaLibrary.this, list_data);
					rc_recently.setAdapter(adapter);
					rc_kategori.setAdapter(adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}, new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(SiswaLibrary.this, error.getMessage(), Toast.LENGTH_SHORT).show();
		}
	});
	
	requestQueue.add(stringRequest);
	
}

public void tulak(View view) {
	view.startAnimation(animAlpha);
	finish();
}

public void btn_home(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
	finish();
	startActivity(i);
}

public void btn_setting(View view) {
	view.startAnimation(animAlpha);
	Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
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

}
