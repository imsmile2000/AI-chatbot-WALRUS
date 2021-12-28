package org.techtown.chatbot1;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fac_Fragment_ton_piechart extends Fragment {

    PieChart pieChart_ton;
    Map<String,Integer> datalist;
    HashMap<String,Integer> ex_datalist = new HashMap<>();
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fac_ton_piechart, container, false);
        Bundle ton_bundle = getArguments();
        Parceldata ton_data = ton_bundle.getParcelable("ton_data");
        datalist = ton_data.datalist;

        pieChart_ton = (PieChart)rootView.findViewById(R.id.fragment_fac_ton_piechart);
        graphInitSetting();
        showPieChart();

        pieChart_ton.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                try{
                    int index = pieChart_ton.getData().getDataSetForEntry(e).getEntryIndex((PieEntry)e);
                    Object[] data_key = ex_datalist.keySet().toArray();
                    Object[] data_value = ex_datalist.values().toArray();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("신고 톤수");
                    builder.setMessage(data_key[index].toString()+"\n"+"해당 개수: "+data_value[index]);
                    builder.setCancelable(true);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
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

        //데이터 키/값 추가
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for(String type : datalist.keySet()){
            if(datalist.get(type).floatValue()>0f){
                pieEntries.add(new PieEntry(datalist.get(type).floatValue(), type));
                ex_datalist.put(type, datalist.get(type));
            }
        }

        //그래프 설명, 글씨크기 설정, 색깔 설정
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        //data로 만들기
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true); //value값도 표시
        pieData.setValueFormatter(new ValueFormatter() {
            private DecimalFormat mformat;

            @Override
            public String getFormattedValue(float value) {
                mformat = new DecimalFormat("###,###,##0.0");

                if(value == 0){
                    return "";
                }
                else{
                    return mformat.format(value) + "%";
                }

            }
        }); //퍼센트표시

        //그래프생성
        Legend l = pieChart_ton.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChart_ton.setCenterText("신고 톤수");
        pieChart_ton.setCenterTextSize(24);
        pieChart_ton.setDrawHoleEnabled(true);
        pieChart_ton.setHoleColor(Color.parseColor("#f0ffff"));
        pieChart_ton.setUsePercentValues(true); //백분위로 계산
        pieChart_ton.setDescription(null);
        pieChart_ton.setEntryLabelColor(Color.BLACK);
        pieChart_ton.setData(pieData);
        pieChart_ton.invalidate();

        pieChart_ton.animateY(1400, Easing.EaseInOutQuad);
        pieChart_ton.setRotationEnabled(false);
    }
}