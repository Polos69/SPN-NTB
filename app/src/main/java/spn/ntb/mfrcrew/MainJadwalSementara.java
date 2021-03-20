package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainJadwalSementara extends AppCompatActivity {

	Animation animAlpha;
	
	PDFView pdfView;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_jadwal_sementara);
	
	animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
	pdfView = findViewById(R.id.pdfView);
	
	new RetrivePDFfromUrl().execute("http://spn.ntb.polri.go.id/admin/service_android/file/jadwal.pdf");
}

public void tulak(View view) {
	view.startAnimation(animAlpha);
	finish();
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