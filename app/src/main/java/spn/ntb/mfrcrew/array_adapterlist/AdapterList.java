package spn.ntb.mfrcrew.array_adapterlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import spn.ntb.mfrcrew.R;
import spn.ntb.mfrcrew.SiswaLibrary_Detail;
import spn.ntb.mfrcrew.ui_siswa.SiswaLibrary;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder>{

Context context;
ArrayList<HashMap<String, String>> list_data;

public AdapterList(SiswaLibrary mainActivity, ArrayList<HashMap<String, String>> list_data) {
	this.context = mainActivity;
	this.list_data = list_data;
}

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library, null);
	return new ViewHolder(view);
}

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
	Glide.with(context)
			  .load("http://spn.ntb.polri.go.id/admin/service_android/images/" + list_data.get(position).get("gambar"))
			  .placeholder(R.mipmap.ic_launcher)
			  .into(holder.imghape);
	holder.txthape.setText(list_data.get(position).get("judul"));
	
	holder.imghape.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			//Memanggil Gambar ketika di klik
			//Toast.makeText(context, list_data.get(position).get("gambar"), Toast.LENGTH_SHORT).show();
			
			Intent i = new Intent(context, SiswaLibrary_Detail.class);
			i.putExtra("p_gambar", list_data.get(position).get("gambar"));
			context.startActivity(i);
		}
	});
	
}

@Override
public int getItemCount() {
	return list_data.size();
}

public class ViewHolder extends RecyclerView.ViewHolder {
	TextView txthape;
	ImageView imghape;
	
	public ViewHolder(View itemView) {
		super(itemView);
		
		txthape = (TextView) itemView.findViewById(R.id.judul);
		imghape = (ImageView) itemView.findViewById(R.id.image);
	}
}
}