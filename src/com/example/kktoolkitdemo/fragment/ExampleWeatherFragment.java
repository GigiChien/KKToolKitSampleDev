package com.example.kktoolkitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kktoolkitdemo.R;
import com.example.kktoolkitdemo.SampleUtil;
import com.example.kktoolkitdemo.api.ExampleWeatherAPI;
import com.kkbox.toolkit.api.KKAPIListener;
import com.kkbox.toolkit.api.KKAPIRequest;
import com.kkbox.toolkit.ui.KKFragment;

/**
 * Created by gigichien on 13/10/23.
 */
public class ExampleWeatherFragment extends KKFragment {

    TextView mCity;
    TextView mCondition;
    TextView mDescription;
    TextView mTemperature;
    TextView mTempH;
    TextView mTempL;
    private ExampleWeatherAPI mAPI;
    private KKAPIRequest mRequest;
    private ExampleWeatherAPI.WeatherData mWeatherData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initView(view);
        mCity = (TextView) view.findViewById(R.id.city_name);
        mCondition = (TextView) view.findViewById(R.id.condition);
        mDescription = (TextView) view.findViewById(R.id.description);
        mTemperature = (TextView) view.findViewById(R.id.temperature);
        mTempH = (TextView) view.findViewById(R.id.max_temp);
        mTempL = (TextView) view.findViewById(R.id.min_temp);
        return view;

    }

    @Override
    protected void onLoadUI() {
        super.onLoadUI();

        mCity.setText(getArguments().getString("City"));
        mCondition.setText(mWeatherData.main);
        mDescription.setText(mWeatherData.description);
        mTemperature.setText(Float.toString(mWeatherData.temp));
        mTempH.setText(Float.toString(mWeatherData.max_temp));
        mTempL.setText(Float.toString(mWeatherData.min_temp));
    }

    @Override
    public void onLoadData() {
        super.onLoadData();
        startFetchData(); //Just show message
        Bundle bundle = getArguments();
        String city = null;
        if(bundle != null){
            city = bundle.getString("City");
        }
        mAPI = new ExampleWeatherAPI();
        mAPI.setAPIListener(exampleAPIListener);
        String inputURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city;
        mRequest = new KKAPIRequest(inputURL, null);
        mAPI.start(mRequest);
    }

    private final KKAPIListener exampleAPIListener = new KKAPIListener() {
        @Override
        public void onAPIComplete() {
            ExampleWeatherAPI.WeatherData data = mAPI.getWeatherData();
            mWeatherData = mAPI.getWeatherData();
            finishFetchData();
        }

        @Override
        public void onAPIError(int errorCode) {
            fetchDataFailed();
        }
    };
}