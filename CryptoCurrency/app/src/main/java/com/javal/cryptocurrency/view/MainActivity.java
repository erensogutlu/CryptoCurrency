package com.javal.cryptocurrency.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javal.cryptocurrency.R;
import com.javal.cryptocurrency.adapter.RecyclerViewAdapter;
import com.javal.cryptocurrency.model.CryptoModel;
import com.javal.cryptocurrency.service.CryptoAPI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       recyclerView =findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();

             retrofit = new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create(gson))
                     .build();

             loadData();
    }

    private void loadData() {

       final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

       /*


          compositeDisposable = new CompositeDisposable();
          compositeDisposable.add(cryptoAPI.getData()
               .subscribeOn(Scheduler.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(this::handlerResponse));


       */

       Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });




       /*


            private void handlerResponse(List<CryptoModel> cryptoModelList) {

            cryptoModels = new ArrayList<>(cryptoModelList);

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
            recyclerView.setAdapter(recyclerViewAdapter);



        }
              */



    }

}