package com.example.veterinerkullanici.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinerkullanici.Fragments.HomeFragment;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.Utils.ChangeFragments;
import com.example.veterinerkullanici.Utils.GetSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;      //anasayfalinearlayout
    // private Button anasayfaButon, aramaYapButon;
    private ChangeFragments changeFragments; //
    private ImageView cikisYap, aramaYap, anasayfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragment();
        tanimla();
        kontrol();
        action();

    }

    private void getFragment() {
        changeFragments = new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }

    public void action() {
        anasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });

        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSharedPreferences getSharedPreferences = new GetSharedPreferences(MainActivity.this);
                //  getSharedPreferences.getSession().edit().clear();
                //getSharedPreferences.getSession().edit().commit();
                getSharedPreferences.deleteToSession();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        aramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:00000000000"));
                startActivity(intent);
            }
        });

    }

    public void tanimla() {
        preferences = new GetSharedPreferences(MainActivity.this);
        getSharedPreferences = preferences.getSession();
        anasayfa = (ImageView) findViewById(R.id.anasayfa);
        aramaYap = (ImageView) findViewById(R.id.aramaYap);
        cikisYap = (ImageView) findViewById(R.id.cikisYap);
    }

    public void kontrol() {
        //Aslında ilk açılacak sayfayı mainActivity(Anasayfa) ayarladık.
        // Ama kullanıcı girişi yapılmadıysa yani boşsa giriş sayfasına yönlendiriyor.
        if (getSharedPreferences.getString("id", null) == null && getSharedPreferences.getString("mailAdres", null) == null
                && getSharedPreferences.getString("username", null) == null) {
            Intent intent = new Intent(MainActivity.this,GirisYapActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
