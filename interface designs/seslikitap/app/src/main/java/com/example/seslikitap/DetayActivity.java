package com.example.seslikitap;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
*/


import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class DetayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    Bundle bundle;
    SQLiteHandler veritabani;
    Cursor imlec;

    String gelenID, id, baslik, aciklama, resim, ses, favori, tarih, kategori;

    ImageView detayResim;
    SeekBar seekBar;
    TextView basYazi, sonYazi;
    Button playBtn, favoriBtn;

    private MediaPlayer mediaPlayer;
    private int toplamSure;
    private final Handler handler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);
/*
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

        final InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                interstitialAd.show();
            }
        });
*/

        veritabani = new SQLiteHandler(getApplicationContext());
        detayResim = findViewById(R.id.detayImage);
        seekBar = findViewById(R.id.seekbarPlay);
        basYazi = findViewById(R.id.textBas);
        sonYazi = findViewById(R.id.textSon);
        playBtn = findViewById(R.id.playButton);
        favoriBtn = findViewById(R.id.favoriButton);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    mediaPlayer.setDataSource(ses);
                    mediaPlayer.prepare();

                } catch (Exception e) {
                    e.printStackTrace();
                }


                toplamSure = mediaPlayer.getDuration();

                @SuppressLint("DefaultLocale") String toplamSureYazisi = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(toplamSure) -
                                TimeUnit.DAYS.toHours(TimeUnit.MICROSECONDS.toDays(toplamSure)),

                        TimeUnit.MILLISECONDS.toMinutes(toplamSure) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(toplamSure)),

                        TimeUnit.MILLISECONDS.toSeconds(toplamSure) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(toplamSure))
                );

                sonYazi.setText(toplamSureYazisi);

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playBtn.setBackgroundResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    playBtn.setBackgroundResource(R.drawable.pause);
                }

                seekbarGuncelle();
            }
        });

        bundle = getIntent().getExtras();
        if (bundle != null) {
            gelenID = bundle.getString("anahtarDegeri");
            imlec = veritabani.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE id = '" + gelenID + "'", null);
/*http://mistikyol.com/mistikmobil/thumbnails/
*http://mistikyol.com/mistikmobil/audios/
* "https://designofengineering.com/uygulama/kitap/" +*/
            while (imlec.moveToNext()) {
                id = imlec.getString(imlec.getColumnIndex("id"));
                baslik = imlec.getString(imlec.getColumnIndex("baslik"));
                aciklama = imlec.getString(imlec.getColumnIndex("aciklama"));
                resim ="https://designofengineering.com/uygulama/kitap/img/" +  imlec.getString(imlec.getColumnIndex("resim"));
                ses ="https://designofengineering.com/uygulama/kitap/ses/" +  imlec.getString(imlec.getColumnIndex("ses"));
                favori = imlec.getString(imlec.getColumnIndex("favori"));
                tarih = imlec.getString(imlec.getColumnIndex("tarih"));
                kategori = imlec.getString(imlec.getColumnIndex("kategori"));
            }
        }
        Picasso.with(this).load(resim).into(detayResim);

        if (favori.equals("1")) {
            favoriBtn.setBackgroundResource(R.drawable.cikar);
        } else {
            favoriBtn.setBackgroundResource(R.drawable.ekle);
        }

        favoriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favori.equals("1")) {
                    veritabani.favoriDurumu(id, "0");
                    favoriBtn.setBackgroundResource(R.drawable.ekle);
                    Toast.makeText(DetayActivity.this, "Favorilerden Çıkarıldı", Toast.LENGTH_SHORT).show();
                    favori = "0";
                } else {
                    veritabani.favoriDurumu(id, "1");
                    favoriBtn.setBackgroundResource(R.drawable.cikar);
                    Toast.makeText(DetayActivity.this, "Favorilere Eklendi", Toast.LENGTH_SHORT).show();
                    favori = "1";
                }
            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mediaPlayer.isPlaying()) {
                    int sure = (toplamSure / 100) * seekBar.getProgress();
                    mediaPlayer.seekTo(sure);
                }
                return false;
            }
        });
    }

    private void seekbarGuncelle() {

        final int anlikUzunSure = mediaPlayer.getCurrentPosition();

        seekBar.setProgress((int) (((float) anlikUzunSure / toplamSure * 100)));

        if (mediaPlayer.isPlaying()) {
            Runnable hareket = new Runnable() {
                @Override
                public void run() {
                    seekbarGuncelle();

                    @SuppressLint("DefaultLocale") String anlikSureYazisi = String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(anlikUzunSure) -
                                    TimeUnit.DAYS.toHours(TimeUnit.MICROSECONDS.toDays(anlikUzunSure)),

                            TimeUnit.MILLISECONDS.toMinutes(anlikUzunSure) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(anlikUzunSure)),

                            TimeUnit.MILLISECONDS.toSeconds(anlikUzunSure) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(anlikUzunSure))
                    );

                    basYazi.setText(anlikSureYazisi);
                }
            };
            handler.postDelayed(hareket, 1000);
        }

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        seekBar.setSecondaryProgress(i);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playBtn.setBackgroundResource(R.drawable.play);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
