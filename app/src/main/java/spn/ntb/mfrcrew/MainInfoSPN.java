package spn.ntb.mfrcrew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import spn.ntb.mfrcrew.app.AppController;
import spn.ntb.mfrcrew.json.JSONParser;

public class MainInfoSPN extends AppCompatActivity implements OnMapReadyCallback {

	Animation animAlpha;

	//================ Maps ================//
	MapFragment mapFragment;
	GoogleMap gMap;
	MarkerOptions markerOptions = new MarkerOptions();
	CameraPosition cameraPosition;
	LatLng center, latLng;
	String title;
	public static final String ID = "id";
	public static final String TITLE = "nama";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	String tag_json_obj = "json_obj_req";
	//================ END Maps ================//

	int success;
	private static final String LOGIN_URL = "http://spn.ntb.polri.go.id/admin/service_android/kritik_saran.php";
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	//============ DIALOG ================//
	AlertDialog.Builder dialog;
	LayoutInflater inflater;
	View dialogView;
	EditText ed_nama, ed_email, ed_saran;
	CardView btn_kritik_saran;
	//============ END DIALOG ================//

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_info);

	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);

	mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
	mapFragment.getMapAsync(this);

	btn_kritik_saran = findViewById(R.id.btn_kritik_saran);
	btn_kritik_saran.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			DialogForm();
		}
	});

}

	//=================================== MAPS ===================================//
	@Override
	public void onMapReady(GoogleMap googleMap) {
		gMap = googleMap;
		// Mengarahkan ke alun-alun Demak
		center = new LatLng(-8.2962209, 116.6408263);
		cameraPosition = new CameraPosition.Builder().target(center).zoom(14).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		getMarkers();
	}
	private void addMarker(LatLng latlng, final String title) {
		markerOptions.position(latlng);
		markerOptions.title(title);
		gMap.addMarker(markerOptions);

		gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void getMarkers() {
		String url = "http://spn.ntb.polri.go.id/admin/service_android/maps.php";
		StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("Response: ", response.toString());

				try {
					JSONObject jObj = new JSONObject(response);
					String getObject = jObj.getString("wisata");
					JSONArray jsonArray = new JSONArray(getObject);

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						title = jsonObject.getString(TITLE);

						latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
						// Menambah data marker untuk di tampilkan ke google map
						addMarker(latLng, title);
					}
				} catch (JSONException e) {
					// JSON error
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Error: ", error.getMessage());
				Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
		AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
	}
	//=================================== END MAPS ===================================//

	private void DialogForm() {
		dialog = new AlertDialog.Builder(this);
		inflater = getLayoutInflater();
		dialogView = inflater.inflate(R.layout.item_kritik_saran, null);
		dialog.setView(dialogView);
		dialog.setCancelable(true);
		dialog.setIcon(R.drawable.placeholder);
		dialog.setTitle("SPN NTB");

		ed_nama = dialogView.findViewById(R.id.ed_nama);
		ed_email = dialogView.findViewById(R.id.ed_email);
		ed_saran = dialogView.findViewById(R.id.ed_pesan);

		dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		dialog.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (ed_nama.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
				}else if (ed_email.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
				}else if (ed_saran.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Silahkan Masukkan Kritik, Saran dan Masukkan Anda", Toast.LENGTH_SHORT).show();
				}else{
					new MainInfoSPN.SimpanKritikSaran().execute();
					dialog.dismiss();
				}


			}
		});
		dialog.show();
	}

	class SimpanKritikSaran extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(MainInfoSPN.this);
			pDialog.setMessage("Login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args){

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("p_nama", ed_nama.getText().toString()));
			params.add(new BasicNameValuePair("p_email", ed_email.getText().toString()));
			params.add(new BasicNameValuePair("p_saran", ed_saran.getText().toString()));
			JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
			try {
				success = json.getInt("success");

				if (success == 1) {
					Log.d("Login Failure!", json.getString("message"));
					return json.getString("message");
				}else{
					Log.d("Login Failure!", json.getString("message"));
					return json.getString("message");
				}
			}catch (JSONException e) {
				e.printStackTrace();
			}
			return "error";
		}

		protected void onPostExecute(String file_url){
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), "Terimakasih Atas Kritik, Saran dan Masukkan Anda", Toast.LENGTH_SHORT).show();
		}
	}

}
