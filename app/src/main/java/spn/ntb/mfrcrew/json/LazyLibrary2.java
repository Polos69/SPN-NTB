package spn.ntb.mfrcrew.json;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.MainGaleri;
import spn.ntb.mfrcrew.MainSiswaLibrary2;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.SiswaLibrary_Detail;
import spn.ntb.mfrcrew.ui_siswa.Siswa_List_ujian;

public class LazyLibrary2 extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;

public LazyLibrary2(Activity a, ArrayList<HashMap<String, String>> d) {
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
		vi = inflater.inflate(R.layout.item_library2, null);
	
	CardView card = (CardView) vi.findViewById(R.id.card);
	ImageView thumb_image = (ImageView) vi.findViewById(R.id.image);
	TextView judul = (TextView) vi.findViewById(R.id.judul);
	TextView judul2 = (TextView) vi.findViewById(R.id.judul2);
	
	HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
	daftar_elektronik = data.get(position);
	
	judul.setText(daftar_elektronik.get(MainSiswaLibrary2.TAG_JUDUL));
	judul2.setText(daftar_elektronik.get(MainSiswaLibrary2.TAG_GAMBAR2));
	Glide.with(vi.getContext())
			  .load(daftar_elektronik.get(MainSiswaLibrary2.TAG_GAMBAR))
			  .placeholder(R.drawable.placeholder)
			  .error(R.drawable.placeholder)
			  .fitCenter()
			  .into(thumb_image);
	
	card.setOnClickListener(new View.OnClickListener() {
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