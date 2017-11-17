package com.practice.rxbasic05_retrofit.domain;

/**
 * Created by JisangYou on 2017-11-16.
 */

public class Weather
{
    private RealtimeWeatherStation RealtimeWeatherStation;

    public RealtimeWeatherStation getRealtimeWeatherStation ()
    {
        return RealtimeWeatherStation;
    }

    public void setRealtimeWeatherStation (RealtimeWeatherStation RealtimeWeatherStation)
    {
        this.RealtimeWeatherStation = RealtimeWeatherStation;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RealtimeWeatherStation = "+RealtimeWeatherStation+"]";
    }
}
