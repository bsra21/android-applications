package com.example.veterinerkullanici.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Utils.ChangeFragments;
import com.example.veterinerkullanici.Adapters.AnswersAdapter;
import com.example.veterinerkullanici.Models.AnswerModel;
import com.example.veterinerkullanici.Models.AskQuestionPojo;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.GetSharedPreferences;
import com.example.veterinerkullanici.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    public View view;
    private LinearLayout petlerimLayout, sorusorlinearlayout, cevapLayout, kampanyaLinearLayout, asiTakipLayout, sanalKarneLayout;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;
    private String id;
    private AnswersAdapter answersAdapter;
    private List<AnswerModel> answerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();

        return view;
    }

    public void tanimla() {
        petlerimLayout = (LinearLayout) view.findViewById(R.id.petlerimLayout);
        sorusorlinearlayout = (LinearLayout) view.findViewById(R.id.sorusorlinearlayout);
        kampanyaLinearLayout = (LinearLayout) view.findViewById(R.id.kampanyaLinearLayout);
        asiTakipLayout = (LinearLayout) view.findViewById(R.id.asiTakipLayout);
        sanalKarneLayout = (LinearLayout) view.findViewById(R.id.sanalKarneLayout);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id", null);
        cevapLayout = (LinearLayout) view.findViewById(R.id.cevapLayout);
        answerList = new ArrayList<>();
    }

    public void action() {
        //Hayvanlarım butonuna tıklandığında yapılacak işlemler
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new UserPetsFragment());
            }
        });
        //Sorular butonuna tıklandığında yapılacak işlemler
        sorusorlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuestionAlert();
            }
        });
        //cevap butonuna tıklandığında yapılacak işlemler
        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnswers(id);
            }
        });
        kampanyaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new KampanyaFragment());
            }
        });//Kampanya sayfasına git.

        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new AsiFragment());
            }//Aşı sayfasına git.
        });
        sanalKarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new SanalKarne());
            }
        });//Geçmiş Aşılar sayfasına git.
    }

    /////////////////////////////////
    public void openQuestionAlert() { //Soru yazma ekranı..
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sorusoralertlayout, null);
        final EditText sorusoredittext = (EditText) view.findViewById(R.id.sorusoredittext);
        Button sorusorbuton = (Button) view.findViewById(R.id.sorusorbuton);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);//Sorular butonuna tıklandığında başka bir tam sayfaya geçmiyor,
        alert.setCancelable(true);//küçük bir pencere oluştuğu için buradan işlemi yaptık.
        final AlertDialog alertDialog = alert.create();
        sorusorbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soru = sorusoredittext.getText().toString();
                sorusoredittext.setText("");
                alertDialog.cancel();
                askQuestion(id, soru, alertDialog);
            }
        });
        alertDialog.show();

    }

    public void askQuestion(String mus_id, String text, final AlertDialog alr) {
        Call<AskQuestionPojo> req = ManagerAll.getInstance().soruSor(mus_id, text);
        req.enqueue(new Callback<AskQuestionPojo>() {
            @Override
            public void onResponse(Call<AskQuestionPojo> call, Response<AskQuestionPojo> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    alr.cancel();
                } else {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AskQuestionPojo> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAnswers(String mus_id) {
        Call<List<AnswerModel>> req = ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswerModel>>() {
            @Override
            public void onResponse(Call<List<AnswerModel>> call, Response<List<AnswerModel>> response) {
                if (response.body().get(0).isTf()) {
                    //  Log.i("cevaplarım",response.body().toString());
                    if (response.isSuccessful()) {
                        answerList = response.body();
                        answersAdapter = new AnswersAdapter(answerList, getContext());
                        openAnswersAlert();
                    }


                } else {
                    Toast.makeText(getContext(), "Herhangi bir cevap yok..", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnswerModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });

    }
    //
    public void openAnswersAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevapalertlayout, null);
        RecyclerView cevapRecyclerView = (RecyclerView) view.findViewById(R.id.cevapRecyclerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        cevapRecyclerView.setLayoutManager(layoutManager);
        cevapRecyclerView.setAdapter(answersAdapter);
        alertDialog.show();

    }


}

