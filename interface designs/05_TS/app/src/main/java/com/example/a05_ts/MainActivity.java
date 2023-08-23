package com.example.a05_ts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView driverManager;
    private CardView parkingManager;
    private CardView history;
    private CardView feedback;
    private CardView support;
    private CardView setting;
    private CardView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if (getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }
        initObjects();

    }

    private void initObjects(){

        driverManager=findViewById(R.id.driverManager);
        parkingManager=findViewById(R.id.parkingManager);
        history=findViewById(R.id.history);
        feedback=findViewById(R.id.feedback);
        support=findViewById(R.id.support);
        setting=findViewById(R.id.setting);
        logout=findViewById(R.id.logout);
        driverManager.setOnClickListener(this);
        parkingManager.setOnClickListener(this);
        history.setOnClickListener(this);
        feedback.setOnClickListener(this);
        support.setOnClickListener(this);
        setting.setOnClickListener(this);
        logout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.driverManager:

                Toast.makeText(this, "Driver Manager", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainMenu.this,DriverManager.class));

                break;
            case R.id.parkingManager:

                Toast.makeText(this, "Parking Manager", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history:

                Toast.makeText(this, "History Manager", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feedback:

                Toast.makeText(this, "Feedback Manager", Toast.LENGTH_SHORT).show();
                break;
            case R.id.support:

                Toast.makeText(this, "support Manager", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:

                Toast.makeText(this, "setting Manager", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:

                Toast.makeText(this, "logout Manager", Toast.LENGTH_SHORT).show();
                break;

        }


    }
}
