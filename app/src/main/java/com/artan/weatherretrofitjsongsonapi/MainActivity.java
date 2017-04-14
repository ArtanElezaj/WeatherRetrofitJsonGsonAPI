package com.artan.weatherretrofitjsongsonapi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.artan.weatherretrofitjsongsonapi.models.Weather;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_city) TextView mTvCity;
    @Bind(R.id.tv_temperature)TextView mTvTemperature;
    @Bind(R.id.tv_lastupdated)TextView mTvLastUpdated;
    @Bind(R.id.tv_condition)TextView  mTvCondition;
    @Bind(R.id.btn_refresh)Button mBtnRefresh;
    //@Bind(R.id.image)Image mImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},   //request specific permission from user
                    10);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},   //request specific permission from user
                    10);

            return;
        }

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_refresh) public void onClickRefresh() {
        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                mTvTemperature.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp());
                mTvCity.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity());
                mTvCondition.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());
                mTvLastUpdated.setText(response.body().getQuery().getResults().getChannel().getLastBuildDate());
              //  mImage.setUrl(response.body().getQuery().getResults().getChannel().getImage().getUrl());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("test","Failed");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClickRefresh();
    }
}
