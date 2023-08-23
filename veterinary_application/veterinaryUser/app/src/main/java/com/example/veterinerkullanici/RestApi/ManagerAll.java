package com.example.veterinerkullanici.RestApi;

import com.example.veterinerkullanici.Models.AnswerModel;
import com.example.veterinerkullanici.Models.AsiModel;
import com.example.veterinerkullanici.Models.AskQuestionPojo;
import com.example.veterinerkullanici.Models.DeleteAnswerModel;
import com.example.veterinerkullanici.Models.KampanyaModel;
import com.example.veterinerkullanici.Models.LoginModel;
import com.example.veterinerkullanici.Models.PetModel;
import com.example.veterinerkullanici.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<RegisterPojo> kayitOl(String mailAdres, String kadi, String pass) {
        Call<RegisterPojo> x = getRestApi().registerUser(mailAdres, kadi, pass);
        return x;
    }

    public Call<LoginModel> girisYap(String mail, String parola) {
        Call<LoginModel> x = getRestApi().loginUser(mail, parola);
        return x;
    }

    public Call<List<PetModel>> getPets(String id) {
        Call<List<PetModel>> x = getRestApi().getPets(id);
        return x;
    }

    public Call<AskQuestionPojo> soruSor(String id, String soru) {
        Call<AskQuestionPojo> x = getRestApi().soruSor(id, soru);
        return x;
    }

    public Call<List<AnswerModel>> getAnswers(String id) {
        Call<List<AnswerModel>> x = getRestApi().getAnswers(id);
        return x;
    }


    public Call<DeleteAnswerModel> deleteAnswer(String cevap, String soru) {
        Call<DeleteAnswerModel> x = getRestApi().deleteAnswer(cevap, soru);
        return x;
    }

    public Call<List<KampanyaModel>> getKampanya() {
        Call<List<KampanyaModel>> x = getRestApi().getKampanya();
        return x;
    }

    public Call<List<AsiModel>> getAsi(String id) {
        Call<List<AsiModel>> x = getRestApi().getAsi(id);
        return x;
    }

    public Call<List<AsiModel>> getGecmisAsi(String id, String pet_id) {
        Call<List<AsiModel>> x = getRestApi().getGecmisAsi(id, pet_id);
        return x;
    }

}
