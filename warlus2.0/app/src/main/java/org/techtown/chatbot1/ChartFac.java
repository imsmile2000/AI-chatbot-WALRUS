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

public class ChartFac extends AppCompatActivity {

    boolean Change=true;
    FloatingActionButton btn_change;
    ViewPager fac_pager;
    CircleIndicator indicator_fac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_fac);

        JSONObject json_data = null;
        Intent getintent = getIntent();
        String data = getintent.getStringExtra("fac");

        Bundle bundle_permission= new Bundle();
        Bundle bundle_location= new Bundle();
        Bundle bundle_ton = new Bundle();

        try {
            json_data = new JSONObject(data);
            
            /*Permssion데이터*/
            HashMap<String,Integer> permissionList = new HashMap<>();
            JSONObject permission = json_data.getJSONObject("permission");

            int yes_permission = permission.getInt("허가완료");
            int no_permission = permission.getInt("미허가");
            int nes_permission = permission.getInt("미정");
            permissionList.put("허가",yes_permission);
            permissionList.put("미허가",no_permission);
            permissionList.put("미정",nes_permission);
            Parceldata permission_data = new Parceldata(permissionList);
            
            /*Location데이터*/
            HashMap<String,Integer> locationList = new HashMap<>();
            JSONObject location = json_data.getJSONObject("location");

            Iterator i_location = location.keys();
            while(i_location.hasNext()){
                String k = i_location.next().toString();
                locationList.put(k,location.getInt(k));
            }
            Parceldata location_data = new Parceldata(locationList);

            /*Ton데이터*/
            HashMap<String,Integer> tonList = new HashMap<>();
            JSONObject ton = json_data.getJSONObject("ton");

            Iterator i_ton = ton.keys();
            while(i_ton.hasNext()){
                String k = i_ton.next().toString();
                tonList.put(k,ton.getInt(k));
            }
            Parceldata ton_data = new Parceldata(tonList);

            bundle_permission.putParcelable("permission_data",permission_data);
            bundle_location.putParcelable("location_data",location_data);
            bundle_ton.putParcelable("ton_data",ton_data);


        } catch (Exception e) {
            e.printStackTrace();
        }


        fac_pager = (ViewPager)findViewById(R.id.fac_vp);
        fac_pager.setOffscreenPageLimit(3);

        FacPageAdapter adapter = new FacPageAdapter(getSupportFragmentManager());

        Fac_Fragment_permission_barchart fragment_permission = new Fac_Fragment_permission_barchart();
        fragment_permission.setArguments(bundle_permission);
        adapter.additem(fragment_permission);

        Fac_Fragment_location_barchart fragment_location = new Fac_Fragment_location_barchart();
        fragment_location.setArguments(bundle_location);
        adapter.additem(fragment_location);

        Fac_Fragment_ton_barchart fragment_ton = new Fac_Fragment_ton_barchart();
        fragment_ton.setArguments(bundle_ton);
        adapter.additem(fragment_ton);

        fac_pager.setAdapter(adapter);
        indicator_fac = findViewById(R.id.indicator_fac);
        indicator_fac.setViewPager(fac_pager);

        btn_change = findViewById(R.id.menu_chart_fac_btn);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Change==true){
                    FacPageAdapter adapter = new FacPageAdapter(getSupportFragmentManager());

                    Fac_Fragment_permission_piechart fragment_permission = new Fac_Fragment_permission_piechart();
                    fragment_permission.setArguments(bundle_permission);
                    adapter.additem(fragment_permission);

                    Fac_Fragment_location_piechart fragment_location = new Fac_Fragment_location_piechart();
                    fragment_location.setArguments(bundle_location);
                    adapter.additem(fragment_location);

                    Fac_Fragment_ton_piechart fragment_ton = new Fac_Fragment_ton_piechart();
                    fragment_ton.setArguments(bundle_ton);
                    adapter.additem(fragment_ton);

                    fac_pager.setAdapter(adapter);
                    indicator_fac.setViewPager(fac_pager);
                    btn_change.setImageResource(R.drawable.ic_baseline_pie_chart_24);
                    Change=false;
                }else{

                    FacPageAdapter adapter = new FacPageAdapter(getSupportFragmentManager());

                    Fac_Fragment_permission_barchart fragment_permission = new Fac_Fragment_permission_barchart();
                    fragment_permission.setArguments(bundle_permission);
                    adapter.additem(fragment_permission);

                    Fac_Fragment_location_barchart fragment_location = new Fac_Fragment_location_barchart();
                    fragment_location.setArguments(bundle_location);
                    adapter.additem(fragment_location);

                    Fac_Fragment_ton_barchart fragment_ton = new Fac_Fragment_ton_barchart();
                    fragment_ton.setArguments(bundle_ton);
                    adapter.additem(fragment_ton);

                    fac_pager.setAdapter(adapter);
                    indicator_fac.setViewPager(fac_pager);
                    btn_change.setImageResource(R.drawable.ic_baseline_bar_chart_24);
                    Change=true;
                }

            }
        });

    }
}