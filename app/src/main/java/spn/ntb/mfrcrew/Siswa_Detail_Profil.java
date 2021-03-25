package spn.ntb.mfrcrew;

import androidx.appcompat.app.AppCompatActivity;
import spn.ntb.mfrcrew.json.SessionManager;
import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;
import spn.ntb.mfrcrew.ui_siswa.Siswa_Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Siswa_Detail_Profil extends AppCompatActivity {

    Animation animAlpha;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_detail_profil);

        animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_hilang);
        sessionManager = new SessionManager(getApplicationContext());

    }

    public void btn_ubah(View view) {
        view.startAnimation(animAlpha);
        Intent i = new Intent(getApplicationContext(), Siswa_Ubah_Profil.class);
        finish();
        startActivity(i);
    }

    public void tulak(View view) {
        view.startAnimation(animAlpha);
        Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
        finish();
        startActivity(i);
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Siswa_Profile.class);
        finish();
        startActivity(i);
    }
}