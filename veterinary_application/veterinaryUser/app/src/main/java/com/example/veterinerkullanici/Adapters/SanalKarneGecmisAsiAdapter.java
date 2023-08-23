package com.example.veterinerkullanici.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Models.AsiModel;
import com.example.veterinerkullanici.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAsiAdapter extends RecyclerView.Adapter<SanalKarneGecmisAsiAdapter.ViewHolder> {
    List<AsiModel> list;
    Context context;

    public SanalKarneGecmisAsiAdapter(List<AsiModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanalkarnegecmislayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.sanalkarneGecmisAsiText.setText(list.get(position).getAsiisim().toString() + " Aşısı ");

        holder.sanalKarneGecmisAsiBilgi.setText(list.get(position).getHayvanisim().toString() + "isimli hayvanınıza "
                + list.get(position).getAsitarih().toString()
                + " tarihinde " + list.get(position).getAsiisim().toString() + " aşısı yapılmıştır.");
        Picasso.get().load(list.get(position).getHayvanresim().toString()).into(holder.sanalkarneGecmisAsiImage);
    }//Tıklanan hayvanın aşı bilgileri yazdırılıyor.  SanalKarne.javadan geldik buraya

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sanalKarneGecmisAsiBilgi, sanalkarneGecmisAsiText;
        CircleImageView sanalkarneGecmisAsiImage;
        //itemview ile listviewin her elemanı için layout ile oluştuduğumuz view tanımlaması işlemi gerçekleşecek.
        public ViewHolder(View itemView) {
            super(itemView); //id tanımlaması..
            sanalkarneGecmisAsiText = (TextView) itemView.findViewById(R.id.sanalkarneGecmisAsiText);
            sanalKarneGecmisAsiBilgi = (TextView) itemView.findViewById(R.id.sanalKarneGecmisAsiBilgi);
            sanalkarneGecmisAsiImage = (CircleImageView) itemView.findViewById(R.id.sanalkarneGecmisAsiImage);
        }
    }


}

