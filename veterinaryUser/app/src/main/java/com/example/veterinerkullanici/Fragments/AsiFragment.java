package com.example.veterinerkullanici.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.veterinerkullanici.Models.AsiModel;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.GetSharedPreferences;
import com.example.veterinerkullanici.Utils.Warnings;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiFragment extends Fragment {

    private View view;
    private CalendarPickerView calenderPickerView;
    private DateFormat format;
    private Calendar nextYear;
    private Date today;
    private List<AsiModel> asiList;
    private List<Date> dateList;
    private String id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi, container, false);
        tanimla();
        getAsi();
        clickToCalender();
        return view;
    }

    public void tanimla() {
        calenderPickerView = (CalendarPickerView) view.findViewById(R.id.calenderPickerView);
        format = new SimpleDateFormat("dd/MM/yyyy"); //tarih yazım biçimi
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);//bir yıl sonrasına kadar göstersin.
        today = new Date();
        calenderPickerView.init(today, nextYear.getTime()); //bugünden bir yıl sonraya kadar
        asiList = new ArrayList<>();
        dateList = new ArrayList<>();
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id", null);
    }//kullanıcı id

    public void getAsi() {//takvimde işaretleme fonksiyonu
        Call<List<AsiModel>> req = ManagerAll.getInstance().getAsi(id);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.isSuccessful()) {
                    asiList = response.body();
                    for (int i = 0; i < asiList.size(); i++) {
                        String dataString = response.body().get(i).getAsitarih().toString();
                        try {
                            Date date = format.parse(dataString);
                            dateList.add(date);//parse olduğu için try-catch kullandık..
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }//bir tane için date, birden fazla date istediğimiz için -s takısını unutma!!!
                     calenderPickerView.init(today,nextYear.getTime()).withHighlightedDates(dateList);
                }                                                   //withHighlightedDates ssss
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void clickToCalender() {
        calenderPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                for (int i = 0; i < dateList.size(); i++) {
                    if (date.toString().equals(dateList.get(i).toString())) {
                        //Toast.makeText(getContext(), asiList.get(i).getHayvanisim().toString(), Toast.LENGTH_LONG).show();
                        openQuestionAlert(asiList.get(i).getHayvanisim().toString(), asiList.get(i).getAsitarih().toString(),
                                asiList.get(i).getAsiisim().toString(), asiList.get(i).getHayvanresim().toString());
                    }//tıklandığında bilgiyi openQuestionAlert fonksiyonuna gönderiyor.
                }//aynı tarihte birden fazla hayvanın aşı olabilir.
            }
            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    public void openQuestionAlert(String petismi, String tarih, String asiismi, String resimUrl) {
        LayoutInflater layoutInflater = this.getLayoutInflater();//tarihe tıklandıktan sonra cardview
        View view = layoutInflater.inflate(R.layout.asitakiplayout, null);//içine yazdırılacak
        TextView petisimText = (TextView) view.findViewById(R.id.petisimText);//bilgilendirme metni hazırlığı
        TextView asiTakipBilgiText = (TextView) view.findViewById(R.id.asiTakipBilgiText);
        CircleImageView asiTakipCircleImageView = (CircleImageView) view.findViewById(R.id.asiTakipCircleImageView);
        petisimText.setText(petismi);//Öncelikle CardViw içindeki tanımlamaları,atamaları yapıyor
        asiTakipBilgiText.setText(petismi + " isimli hayvanınıza " + tarih + " tarihinde " + asiismi + " aşısı yapılacaktır.");
        Picasso.get().load(resimUrl).into(asiTakipCircleImageView); //imageler için Picasso kütüphanesi kullanıyoruz.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);//AlertDialog ile ekranda küçük bilgi mesajı şeklinde gözükmesini sağlıyoruz.
        alert.setCancelable(true); //Yani yeni tam bir sayfaya yönlendirmesin diye.
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

   // final EditText sorusoredittext = (EditText) view.findViewById(R.id.sorusoredittext);
 //   Button sorusorbuton = (Button) view.findViewById(R.id.sorusorbuton);
}