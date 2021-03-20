package spn.ntb.mfrcrew.json;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.ui_siswa.Siswa_List_ujian;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Ujian;

public class LazyListUjian extends BaseAdapter {

private Activity activity;
private ArrayList<HashMap<String, String>> data;
private static LayoutInflater inflater = null;
public ImageLoader imageLoader;

String Tahun, Bulan, Hari, Jam, Menit;

public LazyListUjian(Activity a, ArrayList<HashMap<String, String>> d) {
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
		vi = inflater.inflate(R.layout.item_list_ujian, null);
	
	TextView txt_kd_soal = (TextView) vi.findViewById(R.id.kd_soal);
	TextView txt_matkul = (TextView) vi.findViewById(R.id.txt_matkul);
	TextView txt_tgl = (TextView) vi.findViewById(R.id.txt_tgl);
	TextView txt_jam1 = (TextView) vi.findViewById(R.id.txt_jam1);
	TextView txt_jam2 = (TextView) vi.findViewById(R.id.txt_jam2);
	Button btn_mulai = (Button) vi.findViewById(R.id.btn_mulai);
	CardView card_ujian = (CardView) vi.findViewById(R.id.card_ujian);
	
	
	TextView hh_mulai = (TextView) vi.findViewById(R.id.hh_mulai);
	TextView hh_akhir = (TextView) vi.findViewById(R.id.hh_akhir);
	TextView mm_mulai = (TextView) vi.findViewById(R.id.mm_mulai);
	TextView mm_akhir = (TextView) vi.findViewById(R.id.mm_akhir);
	
	HashMap<String, String> daftar_elektronik = new HashMap<String, String>();
	daftar_elektronik = data.get(position);
	
	txt_kd_soal.setText(daftar_elektronik.get(Siswa_List_ujian.TAG_KD_SOAL));
	txt_matkul.setText(daftar_elektronik.get(Siswa_List_ujian.TAG_MATKUL));
	txt_tgl.setText(daftar_elektronik.get(Siswa_List_ujian.TAG_TGL));
	txt_jam1.setText(daftar_elektronik.get(Siswa_List_ujian.TAG_JAM1));
	txt_jam2.setText(daftar_elektronik.get(Siswa_List_ujian.TAG_JAM2));
	
	hh_mulai.setText(daftar_elektronik.get(Siswa_List_ujian.HH_MULAI));
	hh_akhir.setText(daftar_elektronik.get(Siswa_List_ujian.HH_AKHIR));
	mm_mulai.setText(daftar_elektronik.get(Siswa_List_ujian.MM_MULAI));
	mm_akhir.setText(daftar_elektronik.get(Siswa_List_ujian.MM_AKHIR));
	
	
	
	btn_mulai.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			Calendar now =  Calendar.getInstance();
			int Y1 = now.get(Calendar.YEAR);
			int m1 = now.get(Calendar.MONTH) + 1;
			int d1 = now.get(Calendar.DAY_OF_MONTH);
			int j1 = now.get(Calendar.HOUR_OF_DAY);
			int mm1 = now.get(Calendar.MINUTE);
			Tahun = String.valueOf(Y1);
			Hari = String.valueOf(d1);
			Jam = String.valueOf(j1);
			Menit =  String.valueOf(mm1);
			if (m1 <= 9){
				Bulan = "0"+String.valueOf(m1);
			}else{
				Bulan = String.valueOf(m1);
			}
			
			if (Integer.parseInt(Jam) >= Integer.parseInt(hh_mulai.getText().toString())){ //Jam Mulai
				if (Integer.parseInt(Jam) <= Integer.parseInt(hh_akhir.getText().toString())){ //Jam Berakhir
					if (Integer.parseInt(Menit) >= Integer.parseInt(mm_mulai.getText().toString())){ //Menit Mulai
						if (Integer.parseInt(Menit) <= Integer.parseInt(mm_akhir.getText().toString())) { //Menit Berakhir
							Intent i = new Intent(v.getContext(), Siswa_Ujian.class);
							i.putExtra("kd_soal", txt_kd_soal.getText().toString());
							i.putExtra("kd_soal", txt_matkul.getText().toString());
							i.putExtra("kd_soal", txt_tgl.getText().toString());
							i.putExtra("kd_soal", txt_jam1.getText().toString());
							i.putExtra("kd_soal", txt_jam2.getText().toString());
							v.getContext().startActivity(i);
						}else{
							Toast.makeText(v.getContext(), "Ujian1 "+txt_matkul.getText()+" Sudah Selesai", Toast.LENGTH_LONG).show();
						}
					}else{
						Toast.makeText(v.getContext(), "Ujian2 "+txt_matkul.getText()+" Belum di Mulai", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(v.getContext(), "Ujian3 "+txt_matkul.getText()+" Sudah Selesai", Toast.LENGTH_LONG).show();
				}
				
			}else{
				Toast.makeText(v.getContext(), "Ujian4 "+txt_matkul.getText()+" Belum di Mulai", Toast.LENGTH_LONG).show();
			}
			
			
		}
	});
	
	return vi;
}
}