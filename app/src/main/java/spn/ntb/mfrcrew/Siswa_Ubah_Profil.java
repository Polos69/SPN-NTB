package spn.ntb.mfrcrew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;
import spn.ntb.mfrcrew.json.JSONParser;
import spn.ntb.mfrcrew.json.SessionManager;
import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

public class Siswa_Ubah_Profil extends AppCompatActivity {

    SessionManager sessionManager;
    Animation animAlpha;
    
    //Request Code Digunakan Untuk Menentukan Permintaan dari User
    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;
    
    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    String tag_json_obj = "json_obj_req";
    
    TextView id;
    ImageView setImage;
    Button btn_kamera;
    EditText prof_nama, prof_hp, prof_alamat, prof_email;
    CardView tombol_ganti;

    private static final String url_update = "http://spn.ntb.polri.go.id/admin/service_android/ubah_data.php";
    JSONParser jsonParser = new JSONParser();
    JSONArray string_json = null;
    private ProgressDialog pDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_ubah_profil);
    
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_hilang);
        sessionManager = new SessionManager(getApplicationContext());
        setImage = findViewById(R.id.showImg);
        
        //=============================== Tangkap Hasil SESSION ===================================//
        id = findViewById(R.id.id);
        prof_nama = findViewById(R.id.prof_nama);
        prof_hp = findViewById(R.id.prof_hp);
        prof_alamat = findViewById(R.id.prof_alamat);
        prof_email = findViewById(R.id.prof_email);
        HashMap<String, String> id_user = sessionManager.getUserDetails();
        String idx = id_user.get(SessionManager.id_user);
        String namax = id_user.get(SessionManager.nm_user);
        String hpx = id_user.get(SessionManager.hp_user);
        String alamatx = id_user.get(SessionManager.alamat_user);
        String emailx = id_user.get(SessionManager.email_user);
        id.setText(Html.fromHtml("<b>" + idx + "</b>"));
        prof_nama.setText(Html.fromHtml("<b>" + namax + "</b>"));
        prof_hp.setText(Html.fromHtml("<b>" + hpx + "</b>"));
        prof_alamat.setText(Html.fromHtml("<b>" + alamatx + "</b>"));
        prof_email.setText(Html.fromHtml("<b>" + emailx + "</b>"));
        //=============================== End Tangkap Hasil SESSION ===================================//
        btn_kamera = findViewById(R.id.open_image);
        btn_kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
            }
        });
    
        tombol_ganti = findViewById(R.id.tombol_ganti);
        tombol_ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                
                if (prof_nama.getText().toString().equals("")){
                    prof_nama.setError("Inputan Tidak Boleh Kosong");
                }else{
                    if (prof_hp.getText().toString().equals("")){
                        prof_hp.setError("Inputan Tidak Boleh Kosong");
                    }else{
                        if (prof_alamat.getText().toString().equals("")){
                            prof_alamat.setError("Inputan Tidak Boleh Kosong");
                        }else{
                            if (prof_email.getText().toString().equals("")){
                                prof_email.setError("Inputan Tidak Boleh Kosong");
                            }else{
                                new UpdateData().execute();
                            }
                        }
                    }
                }
            }
        });
    }


//kamera
private void setRequestImage(){
    CharSequence[] item = {"Galeri"};
    AlertDialog.Builder request = new AlertDialog.Builder(this)
            .setTitle("Jasa Raharja")
            .setIcon(R.drawable.logo_app)
            .setItems(item, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case 1:
                            //Membuka Kamera Untuk Mengambil Gambar
                            EasyImage.openCamera(Siswa_Ubah_Profil.this, REQUEST_CODE_CAMERA);
                            break;
                        case 0:
                            //Membuaka Galeri Untuk Mengambil Gambar
                            EasyImage.openGallery(Siswa_Ubah_Profil.this, REQUEST_CODE_GALLERY);
                            break;
                    }
                }
            });
    request.create();
    request.show();
}

public String getStringImage(Bitmap bmp) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
    byte[] imageBytes = baos.toByteArray();
    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    return encodedImage;
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    
    EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
        
        @Override
        public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            //Method Ini Digunakan Untuk Menghandle Error pada Image
        }
        
        @Override
        public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
            //Method Ini Digunakan Untuk Menghandle Image
            switch (type){
                case REQUEST_CODE_CAMERA:
                    //card_img.setVisibility(View.VISIBLE);
                    Glide.with(Siswa_Ubah_Profil.this)
                            .load(imageFile)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(setImage);
                    break;
                
                case REQUEST_CODE_GALLERY:
                    //card_img.setVisibility(View.VISIBLE);
                    Glide.with(Siswa_Ubah_Profil.this)
                            .load(imageFile)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(setImage);
                    break;
            }
        }
        
        @Override
        public void onCanceled(EasyImage.ImageSource source, int type) {
            //Batalkan penanganan, Anda mungkin ingin menghapus foto yang diambil jika dibatalkan
        }
    });
}

public void tulak(View view) {
    view.startAnimation(animAlpha);
    Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
    finish();
    startActivity(i);
}


class UpdateData extends AsyncTask<String, String, String> {
    boolean failure = false;
    
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(Siswa_Ubah_Profil.this);
        pDialog.setMessage("Login...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    
    @Override
    protected String doInBackground(String... args){
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("prof_nosis", id.getText().toString()));
        params.add(new BasicNameValuePair("prof_nama", prof_nama.getText().toString()));
        params.add(new BasicNameValuePair("prof_hp", prof_hp.getText().toString()));
        params.add(new BasicNameValuePair("prof_alamat", prof_alamat.getText().toString()));
        params.add(new BasicNameValuePair("prof_email", prof_email.getText().toString()));
        params.add(new BasicNameValuePair(KEY_IMAGE, getStringImage(((BitmapDrawable) setImage.getDrawable()).getBitmap())));
        params.add(new BasicNameValuePair(KEY_NAME, id.getText().toString().trim()));
        
        JSONObject json = jsonParser.makeHttpRequest(url_update, "POST", params);
        
        try {
            success = json.getInt("success");
            
            if (success == 1) {
                Log.d("Lengkapi Data Diri Anda", json.toString());
                sessionManager.createSession7(id.getText().toString().trim()+".png");
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(i);
                return json.getString("message");
            }else if(success == 0){
                Log.d("Inputan Masih Kosong", json.toString());
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
    }
}

public void onBackPressed() {
    Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
    finish();
    startActivity(i);
}
@Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
}
}