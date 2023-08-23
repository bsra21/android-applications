package com.example.veterinerkullanici.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinerkullanici.Models.AnswerModel;
import com.example.veterinerkullanici.Models.DeleteAnswerModel;
import com.example.veterinerkullanici.R;
import com.example.veterinerkullanici.RestApi.ManagerAll;
import com.example.veterinerkullanici.Utils.Warnings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    List<AnswerModel> list;
    Context context;

    public AnswersAdapter(List<AnswerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cevapitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.cevapSoruText.setText("Soru:" + list.get(position).getSoru().toString());
        holder.cevapCevapText.setText("Cevap:" + list.get(position).getCevap().toString());
        holder.cevapSilbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteToService(list.get(position).getCevapid().toString(), list.get(position).getSoruid().toString(), position);
            }
        });
    }

    public void deleteToList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public void deleteToService(String cevapid, String soruid, final int pos) {
        Call<DeleteAnswerModel> req = ManagerAll.getInstance().deleteAnswer(cevapid, soruid);
        req.enqueue(new Callback<DeleteAnswerModel>() {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {
                if (response.body().isTf()) {
                    if (response.isSuccessful()) {
                        deleteToList(pos);
                        Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cevapCevapText, cevapSoruText;
        Button cevapSilbuton;

        //itemview ile listviewin her elemanı için layout ile oluştuduğumuz view tanımlaması işlemi gerçekleşecek.
        public ViewHolder(View itemView) {
            super(itemView);
            cevapCevapText = (TextView) itemView.findViewById(R.id.cevapCevapText);
            cevapSoruText = (TextView) itemView.findViewById(R.id.cevapSoruText);
            cevapSilbuton = (Button) itemView.findViewById(R.id.cevapSilbuton);

        }
    }


}

