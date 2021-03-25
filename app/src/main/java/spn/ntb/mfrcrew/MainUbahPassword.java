package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

import android.content.Intent;
import android.os.Bundle;

public class MainUbahPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ubah_password);
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
        finish();
        startActivity(i);
    }
}