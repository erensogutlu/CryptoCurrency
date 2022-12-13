package com.javal.cryptocurrency.service;

import com.javal.cryptocurrency.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    // GET ==> price?key=xxx
    // https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

    //Observable<List<CryptoModel>> getData();
     Call<List<CryptoModel>> getData();

}
