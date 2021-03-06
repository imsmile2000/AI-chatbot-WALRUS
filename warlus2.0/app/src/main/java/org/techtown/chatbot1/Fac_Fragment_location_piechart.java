package org.techtown.chatbot1;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fac_Fragment_location_piechart extends Fragment {

    PieChart pieChart_location;
    HashMap<String,Integer> datalist = new HashMap<>();
    HashMap<String,Integer> ex_datalist = new HashMap<>();
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fac_location_piechart, container, false);
        Bundle location_bundle = getArguments();
        Parceldata location_data = location_bundle.getParcelable("location_data");
        datalist = location_data.datalist;



        pieChart_location = (PieChart) rootView.findViewById(R.id.fragment_fac_location_piechart);
        graphInitSetting();
        showPieChart();

        pieChart_location.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try{
                    pieChart_location.setDrawMarkers(true);
                    int index = pieChart_location.getData().getDataSetForEntry(e).getEntryIndex((PieEntry)e);
                    Object[] data_key = ex_datalist.keySet().toArray();
                    Object[] data_value = ex_datalist.values().toArray();

                    System.out.println(index);
                    for(int i =0; i<ex_datalist.size(); i++){
                        System.out.println(i+"???"+ex_datalist.keySet().toArray()[i] + ":" +ex_datalist.values().toArray()[i]);

                    }


                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("?????? ??????");
                    builder.setMessage(data_key[index].toString()+"\n"+"?????? ??????: "+data_value[index]);
                    builder.setCancelable(true);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();}
                catch (Exception error){
                    error.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });

        return rootView;
    }

    public void graphInitSetting(){



        /*colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));*/

    }

    private void showPieChart(){

        //????????? ???/??? ??????
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for(String type : datalist.keySet()){
            pieEntries.add(new PieEntry(datalist.get(type).floatValue(), type));
            ex_datalist.put(type, datalist.get(type));
        }

        //????????? ??????, ???????????? ??????, ?????? ??????
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        //data??? ?????????
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true); //value?????? ??????
        pieData.setValueFormatter(new PercentFormatter(pieChart_location)); //???????????????

        //???????????????
        Legend l = pieChart_location.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChart_location.setDrawMarkers(false);
        pieChart_location.setCenterText("?????? ??????");
        pieChart_location.setCenterTextSize(24);
        pieChart_location.setDrawHoleEnabled(true);
        pieChart_location.setHoleColor(Color.parseColor("#f0ffff"));
        pieChart_location.setUsePercentValues(true); //???????????? ??????
        pieChart_location.setDescription(null);
        pieChart_location.setEntryLabelColor(Color.BLACK);
        pieChart_location.setData(pieData);
        pieChart_location.invalidate();

        pieChart_location.animateY(1400, Easing.EaseInOutQuad);
        pieChart_location.setRotationEnabled(false);
    }
}