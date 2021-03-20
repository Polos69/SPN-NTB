package spn.ntb.mfrcrew.json;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import spn.ntb.mfrcrew.MainHome;
import spn.ntb.mfrcrew.ui_siswa.Main_Dashboard;


public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;
    
    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String id_user = "id_user";
    public static final String nm_user = "nm_user";
    public static final String hp_user = "hp_user";
    public static final String email_user = "email_user";
    public static final String alamat_user = "alamat_user";
    public static final String akses_user = "akses_user";
    
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession1(String id){
        editor.putBoolean(is_login, true);
        editor.putString(id_user, id);
        editor.commit();
    }
    
    public void createSession2(String nama){
        editor.putBoolean(is_login, true);
        editor.putString(nm_user, nama);
        editor.commit();
    }
    
    public void createSession3(String hp){
        editor.putBoolean(is_login, true);
        editor.putString(hp_user, hp);
        editor.commit();
    }
    
    public void createSession4(String email){
        editor.putBoolean(is_login, true);
        editor.putString(email_user, email);
        editor.commit();
    }
    
    public void createSession5(String alamat){
        editor.putBoolean(is_login, true);
        editor.putString(alamat_user, alamat);
        editor.commit();
    }

    public void createSession6(String akses){
        editor.putBoolean(is_login, true);
        editor.putString(akses_user, akses);
        editor.commit();
    }
    
    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, MainHome.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, Main_Dashboard.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
    
    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }
    
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainHome.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
    
        user.put(id_user, pref.getString(id_user, null));
        user.put(nm_user, pref.getString(nm_user, null));
        user.put(hp_user, pref.getString(hp_user, null));
        user.put(email_user, pref.getString(email_user, null));
        user.put(alamat_user, pref.getString(alamat_user, null));
        user.put(akses_user, pref.getString(akses_user, null));
        return user;
    }
    
}