package com.example.veterinerkullanici.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinerkullanici.Models.RegisterPojo;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {
    private Button kayitOlButon;
    private EditText registerPassword, registerUserName, registerMailAdres;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        registerToUser();
        changeActivity();
    }

    public void tanimla() {
        kayitOlButon = (Button) findViewById(R.id.kayitOlButon);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerUserName = (EditText) findViewById(R.id.registerUserName);
        registerMailAdres = (EditText) findViewById(R.id.registerMailAdres);
        registerText = (TextView) findViewById(R.id.registerText);
    }

    public void registerToUser() {
        kayitOlButon.setOnClickListener(new View.OnClickListener() {
            @Override //clicklediğinde input alma işlemi
            public void onClick(View view) {
                String mail = registerMailAdres.getText().toString();
                String userN = registerUserName.getText().toString();
                String pass = registerPassword.getText().toString();
                register(mail, userN, pass);
                delete();
            }
        });
    }


    public void changeActivity() {
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override//bu butona tıklandığında diğer activitye geçiş yapılacak...
            public void onClick(View view) { //"Hesabım var.." yazısına tıklandığında git..
                Intent intent = new Intent(KayitOlActivity.this, GirisYapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void delete() {
        registerMailAdres.setText("");
        registerPassword.setText("");
        registerUserName.setText("");
    }

    public void register(String userMailAdres, String userName, String userPass) {
        //  Call<RegisterPojo> req = ManagerAll.getInstance().kayitOl(userMailAdres, userName, userPass);
        Call<RegisterPojo> req = ManagerAll.getInstance().kayitOl(userMailAdres, userName, userPass);
        req.enqueue(new Callback<RegisterPojo>() {
            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(KayitOlActivity.this, GirisYapActivity.class);
                    startActivity(intent); //Kayit olduktan sonra giriş paneline yönlendirme
                    finish();
                } else { //phpde text mesajlarını buradan çıktı aldırıyoruz.
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }//$user->text="Girmiş olduğunuz bilgilere ait kullanıcı bulunmaktadır.
            }    // Lütfen bilgileriniz değiştirin."; gibi..


            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
