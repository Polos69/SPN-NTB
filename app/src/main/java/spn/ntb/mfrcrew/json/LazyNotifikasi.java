package spn.ntb.mfrcrew.json;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.MainSiswaLibrary2;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.SiswaLibrary_Detail;

public class LazyNotifikasi extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;

public LazyNotifikasi(Activity a, ArrayList<HashMap<String, String>> d) {
	activity = a;
	data = d;
	inflater = (LayoutInflater) this.activity
			  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	imageLoader = new ImageLoader(this.activity.getApplicationContext());
}

public int getCount() {
	return data.size();
}
public Object getItem(int position) {
	return position;
}
public long getItemId(int position) {
	return position;
}
public View getView(int position, View convertView, ViewGroup parent) {
	View vi = convertView;
	if (convertView == null)
		vi = inflater.inflate(R.layout.item_notifikasi, null);
	
	FrameLayout fr_check = (FrameLayout) vi.findViewById(R.id.fr_check);
	TextView txt_mapel = (TextView) vi.findViewById(R.id.txt_mapel);
	TextView judul2 = (TextView) vi.findViewById(R.id.judul2);
	
	HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
	daftar_elektronik = data.get(position);
	
	fr_check.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent i = new Intent(view.getContext(), SiswaLibrary_Detail.class);
			i.putExtra("p_gambar", judul2.getText().toString());
			//activity.finish();
			view.getContext().startActivity(i);
		}
	});
	
	return vi;
}
}