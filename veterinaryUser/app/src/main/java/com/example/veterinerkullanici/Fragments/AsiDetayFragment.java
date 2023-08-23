package com.example.veterinerkullanici.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Adapters.SanalKarneGecmisAsiAdapter;
import com.example.veterinerkullanici.Models.AsiModel;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.ChangeFragments;
import com.example.veterinerkullanici.Utils.GetSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiDetayFragment extends Fragment {

    private View view;
    private String musid;
    private String petid;
    private GetSharedPreferences getSharedPreferences;
    private RecyclerView asiDetayRecView;
    private SanalKarneGecmisAsiAdapter adapter;
    private List<AsiModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_detay, container, false);
        tanimla();
        getGecmisAsi();
        return view;
    }

    public void tanimla() {
        petid = getArguments().getString("petid").toString();
        getSharedPreferences = new GetSharedPreferences(getActivity());
        musid = getSharedPreferences.getSession().getString("id", null);
        asiDetayRecView = (RecyclerView) view.findViewById(R.id.asiDetayRecView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        asiDetayRecView.setLayoutManager(layoutManager);
        list = new ArrayList<>();

    }


    public void getGecmisAsi() {
        Call<List<AsiModel>> req = ManagerAll.getInstance().getGecmisAsi(musid, petid);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.body().get(0).isTf()) {
                    list = response.body();
                    adapter = new SanalKarneGecmisAsiAdapter(list, getContext());
                    asiDetayRecView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Hayvana ait " + list.size() + " adet aşı vardır.",
                            Toast.LENGTH_LONG).show();

                } else {
                    ChangeFragments changeFragments = new ChangeFragments(getContext());
                    changeFragments.change(new SanalKarne());
                    Toast.makeText(getContext(), "hayvana ait aşı bulunmamaktadır..", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });

    }
}