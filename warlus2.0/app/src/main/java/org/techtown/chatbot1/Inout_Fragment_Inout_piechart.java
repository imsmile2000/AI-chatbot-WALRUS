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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Inout_Fragment_Inout_piechart extends Fragment {

    PieChart pieChart_inout;
    Map<String,Integer> datalist;
    HashMap<String,Integer> ex_datalist = new HashMap<>();
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_inout_inout_piechart, container, false);
        Bundle inout_bundle = getArguments();
        Parceldata inout_data = inout_bundle.getParcelable("inout_data");
        datalist = inout_data.datalist;

        pieChart_inout = (PieChart)rootView.findViewById(R.id.fragment_inout_inout_piechart);
        graphInitSetting();
        showPieChart();

        pieChart_inout.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try{
                    int index = pieChart_inout.getData().getDataSetForEntry(e).getEntryIndex((PieEntry)e);
                    Object[] data_key = ex_datalist.keySet().toArray();
                    Object[] data_value = ex_datalist.values().toArray();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("입/출항");
                    builder.setMessage(data_key[index].toString()+"\n"+"해당 개수: "+data_value[index]);
                    builder.setCancelable(true);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }catch (Exception error){
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
            pieEntries.add(new PieEntry(datalist.get(type).floatValue(), type));
            ex_datalist.put(type, datalist.get(type));
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
        pieData.setValueFormatter(new PercentFormatter(pieChart_inout)); //퍼센트표시

        //그래프생성
        Legend l = pieChart_inout.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChart_inout.setCenterText("입/출항");
        pieChart_inout.setCenterTextSize(24);
        pieChart_inout.setDrawHoleEnabled(true);
        pieChart_inout.setHoleColor(Color.parseColor("#f0ffff"));
        pieChart_inout.setUsePercentValues(true); //백분위로 계산
        pieChart_inout.setDescription(null);
        pieChart_inout.setEntryLabelColor(Color.BLACK);
        pieChart_inout.setData(pieData);
        pieChart_inout.invalidate();

        pieChart_inout.animateY(1400, Easing.EaseInOutQuad);
        pieChart_inout.setRotationEnabled(false);
    }
}