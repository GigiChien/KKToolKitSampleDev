package com.example.kktoolkitdemo.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kktoolkitdemo.R;
import com.kkbox.toolkit.api.KKAPIBase;
import com.kkbox.toolkit.api.KKAPIListener;
import com.kkbox.toolkit.api.KKAPIRequest;
import com.kkbox.toolkit.ui.KKActivity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by gigichien on 13/10/22.
 */
public class KKAPIActivity extends KKActivity {
    private TextView mTextView;
    private ExampleWeatherAPI mAPI;
    private EditText mInput;
    private KKAPIRequest mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apibase);
        Button btnSubmit = (Button) findViewById(R.id.submit);
        mTextView = (TextView) findViewById(R.id.output);
        mInput = (EditText) findViewById(R.id.input_url);
        setStatusText(" Status : Initialize");
        mAPI = new ExampleWeatherAPI();
        mAPI.setAPIListener(exampleAPIListener);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatusText(" Status : Wait for response...");
                if(mInput != null){
                    if(mRequest != null){
                        mRequest.cancel();
                    }
                    String inputURL = "http://api.openweathermap.org/data/2.5/weather?q="+mInput.getText();
                    mRequest = new KKAPIRequest(inputURL, null);
                }
                mAPI.start(mRequest);
            }
        });
    }

    private void setStatusText(String status){
        if(mTextView != null){
            mTextView.setText(status);
        }
    }

    private KKAPIListener exampleAPIListener = new KKAPIListener() {
        @Override
        public void onAPIComplete() {
            ExampleWeatherAPI.WeatherData data = mAPI.getWeatherData();
            setStatusText(" Status : onAPIComplete \n [response] \n"+
                    mAPI.getResponseData() +
                    "\n [parse] \n"+
                    " Condition : " + data.main +
                    "\n Description : " + data.description +
                    "\n Temp : " + data.temp +
                    "\n Min temp : " + data.min_temp +
                    "\n Max temp : " + data.max_temp);
        }

        @Override
        public void onAPIError(int errorCode) {
            setStatusText(" Status : onAPIError");
        }
    };
}
