package spn.ntb.mfrcrew;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import spn.ntb.mfrcrew.json.JSONParser;

public class SiswaLibrary_Detail extends AppCompatActivity {

    Animation animAlpha;
    TextView nm_gambar;

    PDFView pdfView;
    
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static final String url_pdf = "http://spn.ntb.polri.go.id/admin/service_android/detail_library.php";
    JSONArray string_json = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_library_detail);
    
        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
        nm_gambar = findViewById(R.id.nm_gambar);
    
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            nm_gambar.setText(bundle.getString("p_gambar"));
        }else{
            nm_gambar.setText(getIntent().getStringExtra("p_gambar"));
        }
    
        pdfView = findViewById(R.id.pdfView);
        
        new TampilPDF().execute();
    }

    public void tulak(View view) {
        view.startAnimation(animAlpha);
        finish();
    }


    class TampilPDF extends AsyncTask<String, String, String> {
       @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(SiswaLibrary_Detail.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        protected String doInBackground(String... args) {
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("nm_gambar",nm_gambar.getText().toString()));
                JSONObject json = jParser.makeHttpRequest(url_pdf, "GET", params1);
                string_json = json.getJSONArray("detail_library");
                
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            //ambil objek pertama dari JSON Array
                            JSONObject c = string_json.getJSONObject(0);
    
                            new RetrivePDFfromUrl().execute(c.getString("lokasi"));
                            
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
                
            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }
        
        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }
    
}