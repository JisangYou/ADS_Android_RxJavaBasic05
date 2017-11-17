package com.practice.rxbasic05_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.practice.rxbasic05_retrofit.domain.Row;
import com.practice.rxbasic05_retrofit.domain.Weather;


import io.reactivex.Observable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(iweather.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
        //2. 서비스 만들기< 인터페이스로부터
        iweather service = retrofit.create(iweather.class);

        //3. 옵져버(Emitter) 생성
        Observable<Weather> observable = service.getData(iweather.SERVER_KEY, 1, 10, "동작");

        //4. 발행시작
        observable
                //5. 구독
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weather -> {
                            String result="";
                            for (Row row : weather.getRealtimeWeatherStation().getRow()) {
                                result += "지역명"+row.getNAME()+"\n";
                                result += "온도"+row.getSAWS_TA_AVG()+"\n";
                                result += "지역명"+row.getSAWS_HD()+"\n";
                            }
                            ((TextView) findViewById(R.id.result)).setText(result);

                        }
                );

    }
}


interface iweather {

    public static final String SERVER_URL = "http://openAPI.seoul.go.kr:8088/";
    public static final String SERVER_KEY = "47516265416a697337374e7872556a";

    @GET("{key}/json/RealtimeWeatherStation/{skip}/{count}/{gu}")
    Observable<Weather> getData(
            @Path("key") String server_key,
            @Path("skip") int skip,
            @Path("count") int count,
            @Path("gu") String gu);
}