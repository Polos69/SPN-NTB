package spn.ntb.mfrcrew.json;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.MainAksesMateri;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.SiswaLibrary_Detail;

public class LazyMateri extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;
Animation animAlpha;

public LazyMateri(Activity a, ArrayList<HashMap<String, String>> d) {
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
		vi = inflater.inflate(R.layout.item_materi, null);
	
	animAlpha = AnimationUtils.loadAnimation(vi.getContext(), R.anim.anim_hilang);
	
	TextView judul = (TextView) vi.findViewById(R.id.judul);
	TextView nm_gambar = (TextView) vi.findViewById(R.id.nm_gambar);
	ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar_galeri);
	
	CardView card_c = (CardView) vi.findViewById(R.id.card_c);
	
	HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
	daftar_elektronik = data.get(position);
	
	judul.setText(daftar_elektronik.get(MainAksesMateri.TAG_JUDUL));
	nm_gambar.setText(daftar_elektronik.get(MainAksesMateri.TAG_NM_GAMBAR));
	Glide.with(vi.getContext())
			  .load(daftar_elektronik.get(MainAksesMateri.TAG_GAMBAR))
			  .placeholder(R.drawable.placeholder)
			  .error(R.drawable.placeholder)
			  .fitCenter()
			  .into(thumb_image);
	
	card_c.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			Intent i = new Intent(v.getContext(), SiswaLibrary_Detail.class);
			i.putExtra("p_gambar", nm_gambar.getText().toString());
			v.getContext().startActivity(i);
		}
	});
	return vi;
}
}