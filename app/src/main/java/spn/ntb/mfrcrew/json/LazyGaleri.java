package spn.ntb.mfrcrew.json;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.MainGaleri;
import spn.ntb.mfrcrew.R;

public class LazyGaleri extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;

public LazyGaleri(Activity a, ArrayList<HashMap<String, String>> d) {
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
		vi = inflater.inflate(R.layout.item_galeri, null);
	
	ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar_galeri);
	
	HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
	daftar_elektronik = data.get(position);
	
	Glide.with(vi.getContext())
			  .load(daftar_elektronik.get(MainGaleri.TAG_GAMBAR))
			  .placeholder(R.drawable.placeholder)
			  .error(R.drawable.placeholder)
			  .fitCenter()
			  .into(thumb_image);
	
	return vi;
}
}