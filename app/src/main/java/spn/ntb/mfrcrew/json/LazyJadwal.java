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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import spn.ntb.mfrcrew.MainJadwal;
import spn.ntb.mfrcrew.MainJadwalDetail;
import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.ui_siswa.Siswa_List_ujian;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Ujian;

public class LazyJadwal extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;
Animation animAlpha;
String Tahun, Bulan, Hari, Jam, Menit;

public LazyJadwal(Activity a, ArrayList<HashMap<String, String>> d) {
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
		vi = inflater.inflate(R.layout.item_jadwal, null);
	
	animAlpha = AnimationUtils.loadAnimation(vi.getContext(), R.anim.anim_hilang);
	
	TextView txt_jam = (TextView) vi.findViewById(R.id.txt_jam);
	TextView txt_mapel = (TextView) vi.findViewById(R.id.txt_mapel);
	TextView txt_jp = (TextView) vi.findViewById(R.id.txt_jp);
	TextView txt_lalu = (TextView) vi.findViewById(R.id.txt_lalu);
	TextView txt_tuang = (TextView) vi.findViewById(R.id.txt_tuang);
	TextView txt_sisa = (TextView) vi.findViewById(R.id.txt_sisa);
	
	TextView txt_tgl = (TextView) vi.findViewById(R.id.txt_tgl);
	CardView btn_card = (CardView) vi.findViewById(R.id.btn_card);
	
	HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
	daftar_elektronik = data.get(position);
	
	txt_jam.setText(daftar_elektronik.get(MainJadwal.TAG_JAM));
	txt_mapel.setText(daftar_elektronik.get(MainJadwal.TAG_MATKUL));
	txt_tgl.setText(daftar_elektronik.get(MainJadwal.TAG_TGL));
	txt_jp.setText(daftar_elektronik.get(MainJadwal.TAG_SP));
	txt_lalu.setText(daftar_elektronik.get(MainJadwal.TAG_LALU));
	txt_tuang.setText(daftar_elektronik.get(MainJadwal.TAG_TUANG));
	txt_sisa.setText(daftar_elektronik.get(MainJadwal.TAG_SISA));
	
	btn_card.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			v.startAnimation(animAlpha);
			Intent i = new Intent(v.getContext(), MainJadwalDetail.class);
			i.putExtra("txt_jam", txt_jam.getText().toString());
			i.putExtra("txt_mapel", txt_mapel.getText().toString());
			i.putExtra("txt_tgl", txt_tgl.getText().toString());
			i.putExtra("txt_jp", txt_jp.getText().toString());
			i.putExtra("txt_lalu", txt_lalu.getText().toString());
			i.putExtra("txt_tuang", txt_tuang.getText().toString());
			i.putExtra("txt_sisa", txt_sisa.getText().toString());
			v.getContext().startActivity(i);
		}
	});
	
	return vi;
}
}