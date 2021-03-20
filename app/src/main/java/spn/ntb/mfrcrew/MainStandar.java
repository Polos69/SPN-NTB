package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

public class MainStandar extends AppCompatActivity {
    Animation animAlpha;

    PDFView pdfView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_standar);
        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
    
        pdfView = findViewById(R.id.pdfView);
        new RetrivePDFfromUrl().execute("http://spn.ntb.polri.go.id/admin/service_android/file/8_standar_pendidikan.pdf");
    }

    public void tulak(View view) {
        view.startAnimation(animAlpha);
        finish();
    }

public void btn_home(View view) {
    view.startAnimation(animAlpha);
    Intent i = new Intent(getApplicationContext(), Main_Dashboard.class);
    finish();
    startActivity(i);
}

public void btn_setting(View view) {
    view.startAnimation(animAlpha);
    Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
    finish();
    startActivity(i);
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