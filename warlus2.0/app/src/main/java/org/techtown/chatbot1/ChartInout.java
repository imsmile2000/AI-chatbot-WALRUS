package org.techtown.chatbot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import me.relex.circleindicator.CircleIndicator;

public class ChartInout extends AppCompatActivity {

    boolean Change=true;
    ViewPager inout_pager;
    FloatingActionButton btn_change;
    CircleIndicator indicator_inout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_inout);

        JSONObject json_data = null;
        Intent intent = getIntent();
        String data = intent.getStringExtra("inout");

        Bundle bundle_inout = new Bundle();
        Bundle bundle_kind = new Bundle();
        Bundle bundle_country = new Bundle();

        try {
            json_data = new JSONObject(data);

            /*Inout데이터*/
            JSONObject inout = json_data.getJSONObject("inout");
            int in = inout.getInt("입항");
            int out = inout.getInt("출항");

            HashMap<String,Integer> inoutList = new HashMap<>();
            inoutList.put("입항",in);
            inoutList.put("출항",out);
            Parceldata inout_data = new Parceldata(inoutList);

            /*Kind데이터*/
            JSONObject kind = json_data.getJSONObject("kind");
            HashMap<String,Integer> kindList = new HashMap<>();
            Iterator i_kind = kind.keys();

            while(i_kind.hasNext()){
                String k = i_kind.next().toString();
                kindList.put(k,kind.getInt(k));
            }
            Parceldata kind_data = new Parceldata(kindList);

            /*Country데이터*/
            JSONObject country = json_data.getJSONObject("country");
            HashMap<String,Integer> countryList = new HashMap<>();
            Iterator i_country = country.keys();

            while(i_country.hasNext()){
                String k = i_country.next().toString();
                countryList.put(k,country.getInt(k));
            }
            Parceldata country_data = new Parceldata(countryList);

            bundle_inout.putParcelable("inout_data",inout_data);
            bundle_kind.putParcelable("kind_data",kind_data);
            bundle_country.putParcelable("country_data",country_data);



        } catch (Exception e) {
            e.printStackTrace();
        }


        inout_pager= (ViewPager)findViewById(R.id.inout_vp);
        inout_pager.setOffscreenPageLimit(3);


        InoutPageAdapter adapter = new InoutPageAdapter(getSupportFragmentManager());
        Inout_Fragment_Inout_barchart fragment_inout = new Inout_Fragment_Inout_barchart();
        fragment_inout.setArguments(bundle_inout);
        adapter.additem(fragment_inout);

        Inout_Fragment_kind_barchart fragment_kind = new Inout_Fragment_kind_barchart();
        fragment_kind.setArguments(bundle_kind);
        adapter.additem(fragment_kind);

        Inout_Fragment_country_barchart fragment_country = new Inout_Fragment_country_barchart();
        fragment_country.setArguments(bundle_country);
        adapter.additem(fragment_country);

        inout_pager.setAdapter(adapter);
        indicator_inout = findViewById(R.id.indicator_inout);
        indicator_inout.setViewPager(inout_pager);

        btn_change = findViewById(R.id.menu_chart_inout_btn);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Change==true){
                    InoutPageAdapter adapter = new InoutPageAdapter(getSupportFragmentManager());

                    Inout_Fragment_Inout_piechart fragment_inout = new Inout_Fragment_Inout_piechart();
                    fragment_inout.setArguments(bundle_inout);
                    adapter.additem(fragment_inout);

                    Inout_Fragment_kind_piechart fragment_kind = new  Inout_Fragment_kind_piechart();
                    fragment_kind.setArguments(bundle_kind);
                    adapter.additem(fragment_kind);

                    Inout_Fragment_country_piechart fragment_country = new  Inout_Fragment_country_piechart();
                    fragment_country.setArguments(bundle_country);
                    adapter.additem(fragment_country);

                    inout_pager.setAdapter(adapter);
                    indicator_inout.setViewPager(inout_pager);
                    btn_change.setImageResource(R.drawable.ic_baseline_pie_chart_24);
                    Change=false;
                }else{

                    InoutPageAdapter adapter = new InoutPageAdapter(getSupportFragmentManager());

                    Inout_Fragment_Inout_barchart fragment_inout = new Inout_Fragment_Inout_barchart();
                    fragment_inout.setArguments(bundle_inout);
                    adapter.additem(fragment_inout);

                    Inout_Fragment_kind_barchart fragment_kind = new Inout_Fragment_kind_barchart();
                    fragment_kind.setArguments(bundle_kind);
                    adapter.additem(fragment_kind);

                    Inout_Fragment_country_barchart fragment_country = new Inout_Fragment_country_barchart();
                    fragment_country.setArguments(bundle_country);
                    adapter.additem(fragment_country);

                    inout_pager.setAdapter(adapter);
                    indicator_inout.setViewPager(inout_pager);
                    btn_change.setImageResource(R.drawable.ic_baseline_bar_chart_24);
                    Change=true;
                }

            }
        });
    }
}