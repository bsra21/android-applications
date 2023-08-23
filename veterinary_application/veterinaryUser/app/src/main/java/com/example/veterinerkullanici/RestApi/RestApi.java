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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @FormUrlEncoded
    @POST("/uygulama/kontrol.php") //kayit olma
    Call<RegisterPojo> registerUser(@Field("mailAdres") String mailAdres, @Field("kadi") String kadi, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("/uygulama/girisyap.php")
    Call<LoginModel> loginUser(@Field("mailadres") String mailAdres, @Field("sifre") String pass);

    @FormUrlEncoded
    @POST("/uygulama/hayvanlarim.php")
    Call<List<PetModel>> getPets(@Field("musid") String mus_id);

    @FormUrlEncoded
    @POST("/uygulama/sorusor.php")
    Call<AskQuestionPojo> soruSor(@Field("id") String id, @Field("soru") String soru);

    @FormUrlEncoded
    @POST("/uygulama/cevaplar.php")
    Call<List<AnswerModel>> getAnswers(@Field("mus_id") String mus_id);

    @FormUrlEncoded
    @POST("/uygulama/cevapsil.php")
    Call<DeleteAnswerModel> deleteAnswer(@Field("cevap") String cevapid, @Field("soru") String soruid);


    @GET("/uygulama/kampanya.php")
    Call<List<KampanyaModel>> getKampanya();


    @FormUrlEncoded
    @POST("/uygulama/asitakip.php")
    Call<List<AsiModel>> getAsi(@Field("id") String id);

    @FormUrlEncoded
    @POST("/uygulama/gecmisasi.php")
    Call<List<AsiModel>> getGecmisAsi(@Field("id") String id, @Field("petid") String pet_id);

}

