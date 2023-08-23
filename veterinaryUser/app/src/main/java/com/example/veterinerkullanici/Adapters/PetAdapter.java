package com.example.veterinerkullanici.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Models.PetModel;
import com.example.veterinerkullanici.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {
    List<PetModel> list;
    Context context;

    public PetAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.petlistitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.petlayoutcinsname.setText("Pet Cinsi: " + list.get(position).getPetcins().toString());
        holder.petlayoutpetname.setText("Pet İsmi: " + list.get(position).getPetisim().toString());
        holder.petlayoutturname.setText("Pet Türü: " + list.get(position).getPettur().toString());
        Picasso.get().load(list.get(position).getPetresim()).into(holder.petlayoutpetimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView petlayoutpetname, petlayoutcinsname, petlayoutturname;
        CircleImageView petlayoutpetimage;
        //itemview ile listviewin her elemanı için layout ile oluştuduğumuz view tanımlaması işlemi gerçekleşecek.
        public ViewHolder(View itemView) {
            super(itemView);
            petlayoutpetname = (TextView) itemView.findViewById(R.id.petlayoutpetname);
            petlayoutcinsname = (TextView) itemView.findViewById(R.id.petlayoutcinsname);
            petlayoutturname = (TextView) itemView.findViewById(R.id.petlayoutturname);
            petlayoutpetimage = (CircleImageView) itemView.findViewById(R.id.petlayoutpetimage);
        }
    }


}

