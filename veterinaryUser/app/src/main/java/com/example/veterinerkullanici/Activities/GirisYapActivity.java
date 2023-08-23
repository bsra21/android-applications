package com.example.veterinerkullanici.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinerkullanici.Models.LoginModel;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.GetSharedPreferences;
import com.example.veterinerkullanici.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {

    private EditText loginMailAdres, loginPassword;
    private TextView loginText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);
        tanimla();
        click();
    }

    public void tanimla() { //activity_giris sayfa tasarımında kullandığımız
        // id'leri burada tanımlıyoruz,atama yapıyoruz.
        loginMailAdres = (EditText) findViewById(R.id.loginMailAdres);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginText = (TextView) findViewById(R.id.loginText);
        loginButton = (Button) findViewById(R.id.loginButton);
    }

    public void click() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override  //Giriş Yap butonuna tıklandığında ...
            public void onClick(View view) {
                String mail = loginMailAdres.getText().toString();
                String pass = loginPassword.getText().toString();
                login(mail, pass); //Inputları fonksiyona gönder.
                delete();//Daha sonra edittextlerin içini boşaltır.
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override //"Sisteme kayıtlı hesabım yok.." tıklandığında...
            public void onClick(View view) {
                Intent intent = new Intent(GirisYapActivity.this, KayitOlActivity.class);
                startActivity(intent); //Kayıt Ol sayfasına yönlendirir.
                finish();
            }
        });
    }

    public void delete(){ //Edittext temizle
        loginMailAdres.setText("");
        loginPassword.setText("");
    }

    public void login(String mailAdres, String parola) {
        Call<LoginModel> req = ManagerAll.getInstance().girisYap(mailAdres, parola);
        req.enqueue(new Callback<LoginModel>() { //ManagerAll'dan php sorgumuzu kontrol ediyoruz.
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GirisYapActivity.this, MainActivity.class);
                    GetSharedPreferences getSharedPreferences = new GetSharedPreferences(GirisYapActivity.this);
                    getSharedPreferences.setSession(response.body().getId(), response.body().getUserName(), response.body().getMailAdres());
                    startActivity(intent);//Eğer bilgiler doğruysa 'mainActivity' sayfasına yönlendiriiyor.
                    finish(); //Session(oturum) kullanıcı bilgilerini de mainActivity'e taşıyor.
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) { //internet problemi olduğunu yazdır.
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }
}

