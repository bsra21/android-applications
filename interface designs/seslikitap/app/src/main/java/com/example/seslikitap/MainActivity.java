package com.example.seslikitap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Meditasyon> liste = new ArrayList<>();
    private RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    DrawerLayout drawer;
    Button menuButton, yenileButton;
    ListViewAdapter adapter;

    String url = "https://designofengineering.com/uygulama/kitap/kitap.php";
    RequestQueue queue; //  https://designofengineering.com/uygulama/kitap/kitap.php
    SQLiteHandler veritabani;//http://mistikyol.com/mistikmobil/mobiljson.php

    ListView listView;
    String[] adlar = new String[]{
            "Son Eklenenler",
            "Favorilerim",
            "Aksiyon",
            "Türk Klasikleri",
            "Dünya Klasikleri",
            "Fantastik",
            "Romantik",
            "Tarihi",
            "Dram",
            "Psikolojik"
    };

    String[] linkler = new String[]{
            "0",
            "00",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
        queue = NetworkController.getInstance(this).getRequestQueue();
        queue.add(new JsonObjectRequest(0, url, null, new listener(), new error()));

        veritabani = new SQLiteHandler(getApplicationContext());

        drawer = findViewById(R.id.drawer_layout);
        menuButton = findViewById(R.id.menuBtn);
        yenileButton = findViewById(R.id.refreshBtn);
        listView = findViewById(R.id.left_drawer_child);

        yenileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veritabani.verileriSil();
                queue.add(new JsonObjectRequest(0, url, null, new listener(), new error()));
                Toast.makeText(MainActivity.this, "Veriler sunucuyla senkronize ediliyor", Toast.LENGTH_SHORT).show();
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                menuButton.setBackgroundResource(R.drawable.menu); //menus butondu bn değiştirdim. Eğer
            } //menü açıldığında buton ikonu değişsin istersen buaryı düzenleyebilirsin.

            @Override
            public void onDrawerClosed(@NonNull View view) {
                menuButton.setBackgroundResource(R.drawable.menu);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        adapter = new ListViewAdapter(this, adlar, linkler);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                drawer.closeDrawer(GravityCompat.START);

                verileriGoster(linkler[position]);

            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        recyclerView = findViewById(R.id.recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        verileriGoster("0");

    }

    private class listener implements Response.Listener<JSONObject> {
        public void onResponse(JSONObject response) {

            try {

                JSONArray meditasyonlar = response.getJSONArray("kitaplar");
                int length = meditasyonlar.length();

                for (int i = 0; i < length; i++) {
                    try {

                        JSONObject meditasyon = meditasyonlar.getJSONObject(i);

                        Cursor kayitlar = veritabani.getWritableDatabase().rawQuery("SELECT count(*) FROM veriler WHERE anahtar = '"
                                + meditasyon.getString("id") + "'", null);
                        kayitlar.moveToFirst();
                        int sayi = kayitlar.getInt(0);

                        if (sayi == 0) {
                            veritabani.veriEkle(meditasyon.getString("baslik"), meditasyon.getString("aciklama"),
                                    meditasyon.getString("thumbnail"), meditasyon.getString("sesdosyasi"),
                                    meditasyon.getString("tarih"), meditasyon.getString("kategori"), meditasyon.getString("id"));
                        }

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                verileriGoster("0");

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            Log.i("Gelen Cevap", response.toString());
        }
    }

    public void verileriGoster(String kate) {

        liste.clear();

        Cursor kayitlar;

        switch (kate) {
            case "0":
                kayitlar = veritabani.getWritableDatabase().rawQuery("SELECT * FROM veriler ORDER BY anahtar DESC", null);
                break;
            case "00":
                kayitlar = veritabani.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE favori = 1 ORDER BY anahtar DESC", null);
                break;
            default:
                kayitlar = veritabani.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE kategori = " + kate + " ORDER BY anahtar DESC", null);
                break;
        }
/*
* "https://designofengineering.com/uygulama/kitap/" +http://mistikyol.com/mistikmobil/thumbnails/
 * */
        while (kayitlar.moveToNext()) {
            String id = kayitlar.getString(kayitlar.getColumnIndex("id"));
            String baslik = kayitlar.getString(kayitlar.getColumnIndex("baslik"));
            String aciklama = kayitlar.getString(kayitlar.getColumnIndex("aciklama"));
            String resim ="https://designofengineering.com/uygulama/kitap/img/" + kayitlar.getString(kayitlar.getColumnIndex("resim"));
            String ses ="https://designofengineering.com/uygulama/kitap/ses/" +  kayitlar.getString(kayitlar.getColumnIndex("ses"));
            String favori = kayitlar.getString(kayitlar.getColumnIndex("favori"));
            String tarih = kayitlar.getString(kayitlar.getColumnIndex("tarih"));
            String kategori = kayitlar.getString(kayitlar.getColumnIndex("kategori"));

            liste.add(new Meditasyon(id,baslik,aciklama,resim,ses,favori,tarih,kategori));
        }

        recyclerAdapter = new RecyclerAdapter(liste);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view1, int position) {

                Intent gecis = new Intent(MainActivity.this, DetayActivity.class);
                gecis.putExtra("anahtarDegeri", liste.get(position).getId());
                startActivity(gecis);

            }
        }));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private class error implements Response.ErrorListener {
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Hata Oluştu: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
