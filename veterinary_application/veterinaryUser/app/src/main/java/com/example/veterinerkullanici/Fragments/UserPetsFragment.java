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


public class UserPetsFragment extends Fragment {


    View view;
    private RecyclerView petlistrecyclerview;
    private PetAdapter petAdapter;
    private List<PetModel> petList;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_pets, container, false);


        tanimla();
        getPets(mus_id);//test amaçlı 34 vereceğiz...
        return view;
    }

    public void tanimla() {
        petList = new ArrayList<>();
        petlistrecyclerview = (RecyclerView) view.findViewById(R.id.petlistrecyclerview);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 1);
        petlistrecyclerview.setLayoutManager(mng);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        mus_id = getSharedPreferences.getSession().getString("id", null);
    }

    public void getPets(String mus_id) {
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                // Log.i("listem",response.body().toString());
                if (response.body().get(0).isTf()) {
                    petList = response.body();
                    petAdapter = new PetAdapter(petList, getContext());
                    petlistrecyclerview.setAdapter(petAdapter);
                    Toast.makeText(getContext(), "Sistemde kayıtlı" + petList.size() + " hayvanınız bulunmaktadır..", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Sistemde kayıtlı hayvanınız bulunmamaktadır..", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }


}