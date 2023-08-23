package com.example.veterinerkullanici.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.veterinerkullanici.R;

public class ChangeFragments {
    private Context context;

    public ChangeFragments(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment) {
        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, fragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void changeWithParameters(Fragment fragment, String petid) {

        Bundle bundle = new Bundle();
        bundle.putString("petid", petid);
        fragment.setArguments(bundle);

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, fragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}
