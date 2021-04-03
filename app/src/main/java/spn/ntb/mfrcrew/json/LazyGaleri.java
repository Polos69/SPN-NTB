package spn.ntb.mfrcrew.json;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.MainGaleri;
import spn.ntb.mfrcrew.MainJadwal;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Ujian;

public class LazyGaleri extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;

AlertDialog.Builder dialog;
LayoutInflater inflater2;
View dialogView;

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
	
	thumb_image.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			inflater2 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
			daftar_elektronik = data.get(position);
			dialog = new AlertDialog.Builder(v.getContext());
			dialogView = inflater2.inflate(R.layout.pop_up_galery, null);
			dialog.setView(dialogView);
			dialog.setCancelable(true);
			dialog.setIcon(R.drawable.app_icon);
			dialog.setTitle("View Gallery");
			
			ImageView img_pop_up = dialogView.findViewById(R.id.img_pop_up);
			TextView txt_pop_up = dialogView.findViewById(R.id.txt_pop_up);
			
			Glide.with(v.getContext())
					  .load(daftar_elektronik.get(MainGaleri.TAG_GAMBAR))
					  .placeholder(R.drawable.placeholder)
					  .error(R.drawable.placeholder)
					  .fitCenter()
					  .into(img_pop_up);
			
			txt_pop_up.setText(daftar_elektronik.get(MainGaleri.TAG_JUDUL));
			
			dialog.show();
		}
	});
	
	
	
	
	return vi;
}

}