package com.example.veterinerkullanici.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Adapters.PetAdapter;
import com.example.veterinerkullanici.Adapters.SanalKarneAdapter;
import com.example.veterinerkullanici.Models.PetModel;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.ChangeFragments;
import com.example.veterinerkullanici.Utils.GetSharedPreferences;
import com.example.veterinerkullanici.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SanalKarne extends Fragment {

    private View view;
    private RecyclerView sanalKarne;
    private SanalKarneAdapter sanalKarneAdapter;
    private PetAdapter petAdapter;
    private List<PetModel> petList;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sanal_karne, container, false);
        tanimla();
        getPets(mus_id);//test amaçlı 34 vereceğiz...
        return view;
    }

    public void tanimla() {//Kullanıcı id’sinin hayvanlarının geçmiş aşılarını
        petList = new ArrayList<>();//yazdıracağız.
        sanalKarne = (RecyclerView) view.findViewById(R.id.sanalKarne);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 1);
        sanalKarne.setLayoutManager(mng); //Recyclerview temel yapıt.
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        mus_id = getSharedPreferences.getSession().getString("id", null);
    } //kullanıcı idsini kontrol ediyor.Ona ait hayvanların verisini çekecek.

    public void getPets(String mus_id) { //Burada sadece hayvan listeleme yapılıyor.
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {//gecmisasi.php json ile oluştuduğumuz
            @Override                               // Modeli çağırıyor.
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                // Log.i("listem",response.body().toString());//Console çıktı aldırma
                if (response.body().get(0).isTf()) {
                    petList = response.body();//birden fazla veri olabileceği için list model oldu.
                    sanalKarneAdapter = new SanalKarneAdapter(petList, getContext());
                    sanalKarne.setAdapter(sanalKarneAdapter);
                    //Toast.makeText(getContext(),"Sistemde kayıtlı"+petList.size()+"hayvanınız bulunmaktadır..",Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getContext(),"Sistemde kayıtlı hayvanınız bulunmamaktadır..", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }//Databasede istenen şartlara bağlı veri yoksa anasayfaya git.
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {//internet sorunu varsa..
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }
}