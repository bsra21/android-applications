package com.example.veterinerkullanici.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Fragments.AsiDetayFragment;
import com.example.veterinerkullanici.Models.PetModel;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneAdapter extends RecyclerView.Adapter<SanalKarneAdapter.ViewHolder> {
    List<PetModel> list;
    Context context;

    public SanalKarneAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanalkarnelayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.sanalkarneText.setText(list.get(position).getPetisim().toString());
        holder.sanalkarneBilgiText.setText(list.get(position).getPetisim().toString() + " isimli " + list.get(position).getPettur().toString() + " türlü " + list.get(position).getPetcins().toString() + " cinsli");
        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalkarneImage);
        holder.sanalLayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeFragments changeFragments = new ChangeFragments(context);
                changeFragments.changeWithParameters(new AsiDetayFragment(), list.get(position).getPetid().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sanalkarneBilgiText, sanalkarneText;
        CircleImageView sanalkarneImage;
        CardView sanalLayoutCardView;

        //itemview ile listviewin her elemanı için layout ile oluştuduğumuz view tanımlaması işlemi gerçekleşecek.
        public ViewHolder(View itemView) {
            super(itemView);
            sanalkarneBilgiText = (TextView) itemView.findViewById(R.id.sanalkarneBilgiText);
            sanalkarneText = (TextView) itemView.findViewById(R.id.sanalkarneText);
            sanalkarneImage = (CircleImageView) itemView.findViewById(R.id.sanalkarneImage);
            sanalLayoutCardView = (CardView) itemView.findViewById(R.id.sanalLayoutCardView);
        }
    }


}

