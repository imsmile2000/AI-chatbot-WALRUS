package org.techtown.chatbot1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.chatbot1.Chatbot;
import org.techtown.chatbot1.R;
import org.techtown.chatbot1.SettingActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    TextView rain_probability;
    TextView wind;
    TextView air_temperature;
    TextView humidity;
    static RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); getWindow().setStatusBarColor(Color.TRANSPARENT); }



        final TextView textView = (TextView) findViewById(R.id.dateView);
        final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);

        rain_probability = (TextView) findViewById(R.id.rain_probability);
        wind = (TextView) findViewById(R.id.wind);
        air_temperature = (TextView) findViewById(R.id.air_temperature);
        humidity=(TextView)findViewById(R.id.humidity);


        //?????? ??????
        (new Thread(new Runnable() {

            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run() {
                                textView.setText(getCurrentTime());
                            }
                        });
                    } catch (InterruptedException e) {
                    }
            }
        })).start();


        //??? ?????? ????????? ?????? ???????????? ??????
        ImageButton imageButton1 = findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chatbot.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        //????????? ??????
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        if (rg.getCheckedRadioButtonId() == R.id.radio0){
            makeRequest("??????");
        }
        //??? ??????????????? ????????? ?????? ?????? ?????? ??????
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio0)
                {
                    makeRequest("??????"); //?????????
                }else if(checkedId==R.id.radio1)
                {
                    makeRequest("??????"); //?????????
                }else if(checkedId==R.id.radio2)
                {
                    makeRequest("??????"); //?????????
                }else if(checkedId==R.id.radio3)
                {
                    makeRequest("??????"); //?????????
                }

            }
        });

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},0);
        }
    }
    
    //?????????????????? ?????????
    public String getCurrentTime() {
        TimeZone tz;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
        tz = TimeZone.getTimeZone("Asia/Seoul");
        dateFormat.setTimeZone(tz);

        Date date = new Date();
        String str = dateFormat.format(date);

        /*
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = dayTime.format(new Date(time));

         */
        return str;
    }
    
    //???????????? ?????????
    public void makeRequest(String kind){
        String url = "http://13.125.187.57:3001/weather/weather?kind="+kind; //(10.0.2.2:3001 local)
        ImageView sun=null;
        ImageView cloud=null;
        ImageView rain=null;
        ImageView hwangsa=null;
        ImageView manycloud=null;
        ImageView snow=null;
        ImageView ange=null;
        ImageView sonagi=null;
        ImageView thunder=null;

        sun = (ImageView)findViewById(R.id.sun);
        cloud = (ImageView)findViewById(R.id.cloud);
        rain = (ImageView)findViewById(R.id.rain);
        hwangsa = (ImageView)findViewById(R.id.hwangsa);
        manycloud = (ImageView)findViewById(R.id.manycloud);
        snow = (ImageView)findViewById(R.id.snow);
        ange = (ImageView)findViewById(R.id.ange);
        sonagi = (ImageView)findViewById(R.id.sonagi);
        thunder = (ImageView)findViewById(R.id.thunder);


        sun.setImageResource(R.drawable.sun);
        cloud.setImageResource(R.drawable.cloud);
        rain.setImageResource(R.drawable.rain);
        hwangsa.setImageResource(R.drawable.hwangsa);
        manycloud.setImageResource(R.drawable.manycloud);
        snow.setImageResource(R.drawable.snow);
        ange.setImageResource(R.drawable.ange);
        sonagi.setImageResource(R.drawable.sonagi);
        thunder.setImageResource(R.drawable.thunder);

        ImageView finalSun = sun;
        ImageView finalRain = rain;
        ImageView finalCloud = cloud;
        ImageView finalHwangsa = hwangsa;
        ImageView finalManycloud = manycloud;
        ImageView finalSnow = snow;
        ImageView finalAnge = ange;
        ImageView finalSonagi = sonagi;
        ImageView finalThunder = thunder;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                wind.setText("");
                rain_probability.setText("");
                air_temperature.setText("");
                humidity.setText("");
                try {
                    jsonObject = new JSONObject(response);
                    String air_temperature1 = jsonObject.getString("air_temperature");
                    air_temperature.append(air_temperature1+"??C");
                    String rain_probability1 = jsonObject.getString("rain_probability");
                    rain_probability.setText("?????? ??????: " + rain_probability1);

                    String humidity1 = jsonObject.getString("humidity");
                    humidity.setText("??????: " + humidity1);

                    String wind_speed1 = jsonObject.getString("wind_speed");
                    String[] arr_wind = wind_speed1.split(" ");
                    wind.setText(arr_wind[0]+"\n"+arr_wind[1]);

                    String weather = jsonObject.getString("weather");



                    // ????????? ???????????? ????????? ?????? ?????? ?????????????????? ??????????????? ??????????????? ?????????
                    // ???????????? ???????????? ??? ??????????????? ???????????? ????????? ?????? ??????????????? ???????????????(???????????? ???????????? ???????)
                    // main layout?????? frame ???????????? ?????? ?????? imageview ?????? ????????? ?????? ????????????
                    // mainActivity ???????????? ??????????????? ?????????
                    // ?????? ???????????? ??? ????????? ?????? ?????????????????? ??????/???/???/????????????.. ??????
                    // ??????????????? ???????????? imageview??? ?????? ????????? ??????????????????
                    if(weather.equals("??????")){
                        finalSun.setVisibility(View.VISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("???")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.VISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("??????")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.VISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("??????")){
                       finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.VISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);


                    }else if(weather.equals("????????????")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.VISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("???")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.VISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("??????")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.VISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("?????????")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.VISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);

                    }else if(weather.equals("??????, ??????")){
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.INVISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.VISIBLE);

                    }else{
                        finalSun.setVisibility(View.INVISIBLE);
                        finalRain.setVisibility(View.INVISIBLE);
                        finalCloud.setVisibility(View.VISIBLE);
                        finalHwangsa.setVisibility(View.INVISIBLE);
                        finalManycloud.setVisibility(View.INVISIBLE);
                        finalSnow.setVisibility(View.INVISIBLE);
                        finalAnge.setVisibility(View.INVISIBLE);
                        finalSonagi.setVisibility(View.INVISIBLE);
                        finalThunder.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wind.setText("????????????");
                rain_probability.setText("????????????");
                air_temperature.setText("????????????");
                humidity.setText("????????????");
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                return  params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setShouldCache(false);
        requestQueue.add(request);
    }


}