package org.techtown.chatbot1;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;


        import java.util.ArrayList;

public class FreightFee extends AppCompatActivity {

    RadioButton radio_busan,radio_incheon,radio_etc;
    RadioButton radio_outport, radio_inport;
    RadioButton radio_income, radio_outcome;
    RadioGroup radioGroup_1,radioGroup_2,radioGroup_3;
    ImageButton btn_plus;
    Spinner spinner1_kind,spinner2_kind,spinner3_kind,spinner4_kind,spinner5_kind,spinner6_kind,spinner7_kind,spinner8_kind,spinner9_kind;
    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7,editText8,editText9;
    TextView textView1_fee,textView2_fee,textView3_fee,textView4_fee,textView5_fee,textView6_fee,textView7_fee,textView8_fee,textView9_fee;
    TextView textView1_info,textView2_info,textView3_info,textView4_info,textView5_info,textView6_info,textView7_info,textView8_info,textView9_info;
    CheckBox check1_discount,check2_discount,check3_discount,check4_discount,check5_discount,check6_discount,check7_discount,check8_discount,check9_discount;
    TextView textview_freight,textview_security,textview_sum;
    Button btn_calculate,btn_reset;

    String[] items_kind= {"","일반화물","기계하역","무연탄","송유관하역","10ft","20ft","35ft","40ft","45ft"};
    double[] items_security ={0,4,4,4,5,43,86,146.2,172,197.8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freight_fee);

        radioGroup_1 = (RadioGroup)findViewById(R.id.freight_radiogroup_1);
        radioGroup_2 = (RadioGroup)findViewById(R.id.freight_radiogroup_2);
        radioGroup_3 = (RadioGroup)findViewById(R.id.freight_radiogroup_3);
        radio_busan = (RadioButton) findViewById(R.id.freight_radio_busan);
        radio_incheon = (RadioButton)findViewById(R.id.freight_radio_incheon);
        radio_etc = (RadioButton)findViewById(R.id.freight_radio_etc);
        radio_outport = (RadioButton)findViewById(R.id.freight_radio_outport);
        radio_inport = (RadioButton)findViewById(R.id.freight_radio_inport);
        radio_income = (RadioButton)findViewById(R.id.freight_radio_income);
        radio_outcome = (RadioButton)findViewById(R.id.freight_radio_outcome);
        textview_freight = (TextView) findViewById(R.id.freight_textview_freight);
        textview_security = (TextView) findViewById(R.id.freight_textview_security);
        textview_sum = (TextView) findViewById(R.id.freight_textview_sum);
        btn_calculate = (Button)findViewById(R.id.freight_btn_calculate);
        btn_reset = (Button)findViewById(R.id.freight_btn_reset);


        btn_plus = (ImageButton) findViewById(R.id.freight_btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FreightFee.this);
                builder.setTitle("추가 정보");
                builder.setMessage("→  화물료정보(톤/개/배럴당)\n"
                        +"일반화물: 4원\n"+"기계하역: 4원\n"+"무연탄: 4원\n"+"송유관하역: 5원\n"
                        +"10ft: 43원\n"+"20ft: 86원\n"+"35ft: 146.2원\n"+"40ft: 172원\n"+"45ft: 197.8원\n"
                        +"\n\n→   모든 면제사유는 화물료 할인률 100%입니다.");
                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }
        });

        editText1 = (EditText) findViewById(R.id.freight_editText1);
        spinner1_kind = (Spinner) findViewById(R.id.freight_spinner1_kind);
        textView1_info = (TextView) findViewById(R.id.freight_textview1_info);
        textView1_fee = (TextView) findViewById(R.id.freight_textview1_fee);
        check1_discount = (CheckBox)findViewById(R.id.freight_check1_discount);

        editText2 = (EditText) findViewById(R.id.freight_editText2);
        spinner2_kind = (Spinner) findViewById(R.id.freight_spinner2_kind);
        textView2_info = (TextView) findViewById(R.id.freight_textview2_info);
        textView2_fee = (TextView) findViewById(R.id.freight_textview2_fee);
        check2_discount = (CheckBox)findViewById(R.id.freight_check2_discount);


        editText3 = (EditText) findViewById(R.id.freight_editText3);
        spinner3_kind = (Spinner) findViewById(R.id.freight_spinner3_kind);
        textView3_info = (TextView) findViewById(R.id.freight_textview3_info);
        textView3_fee = (TextView) findViewById(R.id.freight_textview3_fee);
        check3_discount = (CheckBox)findViewById(R.id.freight_check3_discount);

        editText4 = (EditText) findViewById(R.id.freight_editText4);
        spinner4_kind = (Spinner) findViewById(R.id.freight_spinner4_kind);
        textView4_info = (TextView) findViewById(R.id.freight_textview4_info);
        textView4_fee = (TextView) findViewById(R.id.freight_textview4_fee);
        check4_discount = (CheckBox)findViewById(R.id.freight_check4_discount);

        editText5 = (EditText) findViewById(R.id.freight_editText5);
        spinner5_kind = (Spinner) findViewById(R.id.freight_spinner5_kind);
        textView5_info = (TextView) findViewById(R.id.freight_textview5_info);
        textView5_fee = (TextView) findViewById(R.id.freight_textview5_fee);
        check5_discount = (CheckBox)findViewById(R.id.freight_check5_discount);

        editText6 = (EditText) findViewById(R.id.freight_editText6);
        spinner6_kind = (Spinner) findViewById(R.id.freight_spinner6_kind);
        textView6_info = (TextView) findViewById(R.id.freight_textview6_info);
        textView6_fee = (TextView) findViewById(R.id.freight_textview6_fee);
        check6_discount = (CheckBox)findViewById(R.id.freight_check6_discount);

        editText7 = (EditText) findViewById(R.id.freight_editText7);
        spinner7_kind = (Spinner) findViewById(R.id.freight_spinner7_kind);
        textView7_info = (TextView) findViewById(R.id.freight_textview7_info);
        textView7_fee = (TextView) findViewById(R.id.freight_textview7_fee);
        check7_discount = (CheckBox)findViewById(R.id.freight_check7_discount);

        editText8 = (EditText) findViewById(R.id.freight_editText8);
        spinner8_kind = (Spinner) findViewById(R.id.freight_spinner8_kind);
        textView8_info = (TextView) findViewById(R.id.freight_textview8_info);
        textView8_fee = (TextView) findViewById(R.id.freight_textview8_fee);
        check8_discount = (CheckBox)findViewById(R.id.freight_check8_discount);

        editText9 = (EditText) findViewById(R.id.freight_editText9);
        spinner9_kind = (Spinner) findViewById(R.id.freight_spinner9_kind);
        textView9_info = (TextView) findViewById(R.id.freight_textview9_info);
        textView9_fee = (TextView) findViewById(R.id.freight_textview9_fee);
        check9_discount = (CheckBox)findViewById(R.id.freight_check9_discount);

        ArrayAdapter<String> adapter1_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter1_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1_kind.setAdapter(adapter1_kind);
        spinner1_kind.setSelection(0);
        spinner1_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner1_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter2_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter2_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2_kind.setAdapter(adapter2_kind);
        spinner2_kind.setSelection(0);
        spinner2_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner2_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter3_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter3_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3_kind.setAdapter(adapter3_kind);
        spinner3_kind.setSelection(0);
        spinner3_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner3_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter4_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter4_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4_kind.setAdapter(adapter4_kind);
        spinner4_kind.setSelection(0);
        spinner4_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner4_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter5_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter5_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5_kind.setAdapter(adapter5_kind);
        spinner5_kind.setSelection(0);
        spinner5_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner5_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter6_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter6_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6_kind.setAdapter(adapter6_kind);
        spinner6_kind.setSelection(0);
        spinner6_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner6_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter7_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter7_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7_kind.setAdapter(adapter7_kind);
        spinner7_kind.setSelection(0);
        spinner7_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner7_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter8_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter8_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner8_kind.setAdapter(adapter8_kind);
        spinner8_kind.setSelection(0);
        spinner8_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner8_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter9_kind = new ArrayAdapter<String>(
                FreightFee.this, android.R.layout.simple_spinner_dropdown_item, items_kind);
        adapter9_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner9_kind.setAdapter(adapter9_kind);
        spinner9_kind.setSelection(0);
        spinner9_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner9_check(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        radioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.freight_radio_busan:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }
                    case R.id.freight_radio_incheon:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }
                    case R.id.freight_radio_etc:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }

                }
            }
        });

        radioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.freight_radio_outport:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }
                    case R.id.freight_radio_inport:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }
                }
            }
        });

        radioGroup_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.freight_radio_income:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }
                    case R.id.freight_radio_outcome:{
                        for(int k=0; k<10; k++){
                            if(spinner1_kind.getSelectedItemPosition()==k){ spinner1_check(k); }
                            if(spinner2_kind.getSelectedItemPosition()==k){ spinner2_check(k); }
                            if(spinner3_kind.getSelectedItemPosition()==k){ spinner3_check(k); }
                            if(spinner4_kind.getSelectedItemPosition()==k){ spinner4_check(k); }
                            if(spinner5_kind.getSelectedItemPosition()==k){ spinner5_check(k); }
                            if(spinner6_kind.getSelectedItemPosition()==k){ spinner6_check(k); }
                            if(spinner7_kind.getSelectedItemPosition()==k){ spinner7_check(k); }
                            if(spinner8_kind.getSelectedItemPosition()==k){ spinner8_check(k); }
                            if(spinner9_kind.getSelectedItemPosition()==k){ spinner9_check(k); }
                        }
                        break;
                    }
                }
            }
        });

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double freight_fee = cal_freight();
                textview_freight.setText(freight_fee+"원");
                double security_fee = cal_security();
                textview_security.setText(security_fee+"원");
                double sum = freight_fee + security_fee;
                textview_sum.setText(sum +"원");
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_busan.setChecked(true);
                radio_outport.setChecked(true);
                radio_income.setChecked(true);
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                editText5.setText("");
                editText6.setText("");
                editText7.setText("");
                editText8.setText("");
                editText9.setText("");
                check1_discount.setChecked(false);
                check2_discount.setChecked(false);
                check3_discount.setChecked(false);
                check4_discount.setChecked(false);
                check5_discount.setChecked(false);
                check6_discount.setChecked(false);
                check7_discount.setChecked(false);
                check8_discount.setChecked(false);
                check9_discount.setChecked(false);
                spinner1_kind.setSelection(0);
                spinner2_kind.setSelection(0);
                spinner3_kind.setSelection(0);
                spinner4_kind.setSelection(0);
                spinner5_kind.setSelection(0);
                spinner6_kind.setSelection(0);
                spinner7_kind.setSelection(0);
                spinner8_kind.setSelection(0);
                spinner9_kind.setSelection(0);
                textview_freight.setText("0원");
                textview_security.setText("0원");
                textview_sum.setText("0원");
            }
        });

    }

    /*Calculate freight_fee*/
    public double cal_freight(){
        double freight_fee=0;

        double num1_amount=0;
        if (editText1.getText().toString().length() != 0) {
            num1_amount = Double.parseDouble(editText1.getText().toString());
        }
        double num1_fee=0;
        if(!check1_discount.isChecked()) {
            String str1_fee = textView1_fee.getText().toString();
            num1_fee = Double.parseDouble(str1_fee.substring(0, str1_fee.length() - 1));
        }

        double num2_amount=0;
        if (editText2.getText().toString().length() != 0) {
            num2_amount = Double.parseDouble(editText2.getText().toString());
        }
        double num2_fee=0;
        if(!check2_discount.isChecked()) {
            String str2_fee = textView2_fee.getText().toString();
            num2_fee = Double.parseDouble(str2_fee.substring(0, str2_fee.length() - 1));
        }

        double num3_amount=0;
        if (editText3.getText().toString().length() != 0) {
            num3_amount = Double.parseDouble(editText3.getText().toString());
        }
        double num3_fee=0;
        if(!check3_discount.isChecked()) {
            String str3_fee = textView3_fee.getText().toString();
            num3_fee = Double.parseDouble(str3_fee.substring(0, str3_fee.length() - 1));
        }

        double num4_amount=0;
        if (editText4.getText().toString().length() != 0) {
            num4_amount = Double.parseDouble(editText4.getText().toString());
        }
        double num4_fee=0;
        if(!check4_discount.isChecked()) {
            String str4_fee = textView4_fee.getText().toString();
            num4_fee = Double.parseDouble(str4_fee.substring(0, str4_fee.length() - 1));
        }

        double num5_amount=0;
        if (editText5.getText().toString().length() != 0) {
            num5_amount = Double.parseDouble(editText5.getText().toString());
        }
        double num5_fee=0;
        if(!check5_discount.isChecked()) {
            String str5_fee = textView5_fee.getText().toString();
            num5_fee = Double.parseDouble(str5_fee.substring(0, str5_fee.length() - 1));
        }

        double num6_amount=0;
        if (editText6.getText().toString().length() != 0) {
            num6_amount = Double.parseDouble(editText6.getText().toString());
        }
        double num6_fee=0;
        if(!check6_discount.isChecked()) {
            String str6_fee = textView6_fee.getText().toString();
            num6_fee = Double.parseDouble(str6_fee.substring(0, str6_fee.length() - 1));
        }

        double num7_amount=0;
        if (editText7.getText().toString().length() != 0) {
            num7_amount = Double.parseDouble(editText7.getText().toString());
        }
        double num7_fee=0;
        if(!check7_discount.isChecked()) {
            String str7_fee = textView7_fee.getText().toString();
            num7_fee = Double.parseDouble(str7_fee.substring(0, str7_fee.length() - 1));
        }

        double num8_amount=0;
        if (editText8.getText().toString().length() != 0) {
            num8_amount = Double.parseDouble(editText8.getText().toString());
        }
        double num8_fee=0;
        if(!check8_discount.isChecked()) {
            String str8_fee = textView8_fee.getText().toString();
            num8_fee = Double.parseDouble(str8_fee.substring(0, str8_fee.length() - 1));
        }

        double num9_amount=0;
        if (editText9.getText().toString().length() != 0) {
            num9_amount = Double.parseDouble(editText9.getText().toString());
        }
        double num9_fee=0;
        if(!check9_discount.isChecked()) {
            String str9_fee = textView9_fee.getText().toString();
            num9_fee = Double.parseDouble(str9_fee.substring(0, str9_fee.length() - 1));
        }

        freight_fee = num1_amount * num1_fee + num2_amount * num2_fee +  num3_amount * num3_fee +  num4_amount * num4_fee +  num5_amount * num5_fee+ num6_amount * num6_fee +  num7_amount * num7_fee +  num8_amount * num8_fee +  num9_amount * num9_fee;

        return freight_fee;
    }

    /*Calculate security_fee*/
    public double cal_security(){
        double security_fee=0.0;

        double num1_amount= 0;
        if (editText1.getText().toString().length() != 0) {
            num1_amount = Double.parseDouble(editText1.getText().toString());
        }
        double num2_amount=0;
        if (editText2.getText().toString().length() != 0) {
            num2_amount = Double.parseDouble(editText2.getText().toString());
        }
        double num3_amount=0;
        if (editText3.getText().toString().length() != 0) {
            num3_amount = Double.parseDouble(editText3.getText().toString());
        }
        double num4_amount=0;
        if (editText4.getText().toString().length() != 0) {
            num4_amount = Double.parseDouble(editText4.getText().toString());
        }
        double num5_amount=0;
        if (editText5.getText().toString().length() != 0) {
            num5_amount = Double.parseDouble(editText5.getText().toString());
        }
        double num6_amount=0;
        if (editText6.getText().toString().length() != 0) {
            num6_amount = Double.parseDouble(editText6.getText().toString());
        }
        double num7_amount=0;
        if (editText7.getText().toString().length() != 0) {
            num7_amount = Double.parseDouble(editText7.getText().toString());
        }
        double num8_amount=0;
        if (editText8.getText().toString().length() != 0) {
            num8_amount = Double.parseDouble(editText8.getText().toString());
        }
        double num9_amount=0;
        if (editText9.getText().toString().length() != 0) {
            num9_amount = Double.parseDouble(editText9.getText().toString());
        }

        for(int k=0; k<10; k++){
            if(spinner1_kind.getSelectedItemPosition()==k){
                if(spinner1_kind.getSelectedItemPosition()==4){
                    if(num1_amount%10==0){
                        num1_amount = (double)((int)(num1_amount/10) * items_security[4]);
                    }else{
                        num1_amount= (double)((int)(num1_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num1_amount = num1_amount * items_security[k];
                }
            }
            if(spinner2_kind.getSelectedItemPosition()==k){
                if(spinner2_kind.getSelectedItemPosition()==4){
                    if(num2_amount%10==0){
                        num2_amount = (double)((int)(num2_amount/10) * items_security[4]);
                    }else{
                        num2_amount= (double)((int)(num2_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num2_amount = num2_amount * items_security[k];
                }
            }
            if(spinner3_kind.getSelectedItemPosition()==k){
                if(spinner3_kind.getSelectedItemPosition()==4){
                    if(num3_amount%10==0){
                        num3_amount = (double)((int)(num3_amount/10) * items_security[4]);
                    }else{
                        num3_amount= (double)((int)(num3_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num3_amount = num3_amount * items_security[k];
                }
            }
            if(spinner4_kind.getSelectedItemPosition()==k){
                if(spinner4_kind.getSelectedItemPosition()==4){
                    if(num4_amount%10==0){
                        num4_amount = (double)((int)(num4_amount/10) * items_security[4]);
                    }else{
                        num4_amount= (double)((int)(num4_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num4_amount = num4_amount * items_security[k];
                }
            }
            if(spinner5_kind.getSelectedItemPosition()==k){
                if(spinner5_kind.getSelectedItemPosition()==4){
                    if(num5_amount%10==0){
                        num5_amount = (double)((int)(num5_amount/10) * items_security[4]);
                    }else{
                        num5_amount= (double)((int)(num5_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num5_amount = num5_amount * items_security[k];
                }
            }
            if(spinner6_kind.getSelectedItemPosition()==k){
                if(spinner6_kind.getSelectedItemPosition()==4){
                    if(num6_amount%10==0){
                        num6_amount = (double)((int)(num6_amount/10) * items_security[4]);
                    }else{
                        num6_amount= (double)((int)(num6_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num6_amount = num6_amount * items_security[k];
                }
            }
            if(spinner7_kind.getSelectedItemPosition()==k){
                if(spinner7_kind.getSelectedItemPosition()==4){
                    if(num7_amount%10==0){
                        num7_amount = (double)((int)(num7_amount/10) * items_security[4]);
                    }else{
                        num7_amount= (double)((int)(num7_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num7_amount = num7_amount * items_security[k];
                }
            }
            if(spinner8_kind.getSelectedItemPosition()==k){
                if(spinner8_kind.getSelectedItemPosition()==4){
                    if(num8_amount%10==0){
                        num8_amount = (double)((int)(num8_amount/10) * items_security[4]);
                    }else{
                        num8_amount= (double)((int)(num8_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num8_amount = num8_amount * items_security[k];
                }
            }
            if(spinner9_kind.getSelectedItemPosition()==k){
                if(spinner9_kind.getSelectedItemPosition()==4){
                    if(num9_amount%10==0){
                        num9_amount = (double)((int)(num9_amount/10) * items_security[4]);
                    }else{
                        num9_amount= (double)((int)(num9_amount/10+1) * items_security[4]);
                    }
                }
                else{
                    num9_amount = num9_amount * items_security[k];
                }
            }
        }

        security_fee = num1_amount + num2_amount +num3_amount + num4_amount + num5_amount + num6_amount + num7_amount + num8_amount + num9_amount;

        return security_fee;
    }

    /*Spinner9*/
    public void spinner9_check(int position){
        switch (position){
            case 0:{
                textView9_info.setText("톤");
                textView9_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView9_info.setText("톤");
                            textView9_fee.setText("341원");
                        }else{
                            textView9_info.setText("톤");
                            textView9_fee.setText("203원");
                        }
                    }
                    else{
                        textView9_info.setText("톤");
                        textView9_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("톤");
                        textView9_fee.setText("306원");
                    }
                    else{
                        textView9_info.setText("톤");
                        textView9_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView9_info.setText("톤");
                            textView9_fee.setText("194원");
                        }else{
                            textView9_info.setText("톤");
                            textView9_fee.setText("120원");
                        }
                    }
                    else{
                        textView9_info.setText("톤");
                        textView9_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("톤");
                        textView9_fee.setText("203원");
                    }else{
                        textView9_info.setText("톤");
                        textView9_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("톤");
                        textView9_fee.setText("192원");
                    }else{
                        textView9_info.setText("톤");
                        textView9_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("톤");
                        textView9_fee.setText("120원");
                    }else{
                        textView9_info.setText("톤");
                        textView9_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView9_info.setText("톤");
                textView9_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("배럴");
                        textView9_fee.setText("11.1원");
                    }else{
                        textView9_info.setText("배럴");
                        textView9_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("배럴");
                        textView9_fee.setText("11.1원");
                    }else{
                        textView9_info.setText("배럴");
                        textView9_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("배럴");
                        textView9_fee.setText("11.1원");
                    }else{
                        textView9_info.setText("배럴");
                        textView9_fee.setText("7.5원");
                    }
                }
                break;
            }case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("2210원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("2100원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("1371원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("4420원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("4200원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("2742원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("7510원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("7140원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("4659원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("8840원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("8400원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("5484원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("10160원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("9660원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView9_info.setText("개");
                        textView9_fee.setText("6306원");
                    }else{
                        textView9_info.setText("개");
                        textView9_fee.setText("2670원");
                    }
                }
                break;
            }
        }
    }

    /*Spinner8*/
    public void spinner8_check(int position){
        switch (position){
            case 0:{
                textView8_info.setText("톤");
                textView8_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView8_info.setText("톤");
                            textView8_fee.setText("341원");
                        }else{
                            textView8_info.setText("톤");
                            textView8_fee.setText("203원");
                        }
                    }
                    else{
                        textView8_info.setText("톤");
                        textView8_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("톤");
                        textView8_fee.setText("306원");
                    }
                    else{
                        textView8_info.setText("톤");
                        textView8_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView8_info.setText("톤");
                            textView8_fee.setText("194원");
                        }else{
                            textView8_info.setText("톤");
                            textView8_fee.setText("120원");
                        }
                    }
                    else{
                        textView8_info.setText("톤");
                        textView8_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("톤");
                        textView8_fee.setText("203원");
                    }else{
                        textView8_info.setText("톤");
                        textView8_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("톤");
                        textView8_fee.setText("192원");
                    }else{
                        textView8_info.setText("톤");
                        textView8_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("톤");
                        textView8_fee.setText("120원");
                    }else{
                        textView8_info.setText("톤");
                        textView8_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView8_info.setText("톤");
                textView8_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("배럴");
                        textView8_fee.setText("11.1원");
                    }else{
                        textView8_info.setText("배럴");
                        textView8_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("배럴");
                        textView8_fee.setText("11.1원");
                    }else{
                        textView8_info.setText("배럴");
                        textView8_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("배럴");
                        textView8_fee.setText("11.1원");
                    }else{
                        textView8_info.setText("배럴");
                        textView8_fee.setText("7.5원");
                    }
                }
                break;
            }case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("2210원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("2100원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("1371원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("4420원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("4200원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("2742원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("7510원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("7140원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("4659원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("8840원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("8400원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("5484원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("10160원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("9660원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView8_info.setText("개");
                        textView8_fee.setText("6306원");
                    }else{
                        textView8_info.setText("개");
                        textView8_fee.setText("2670원");
                    }
                }
                break;
            }

        }
    }

    /*Spinner7*/
    public void spinner7_check(int position){
        switch (position){
            case 0:{
                textView7_info.setText("톤");
                textView7_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView7_info.setText("톤");
                            textView7_fee.setText("341원");
                        }else{
                            textView7_info.setText("톤");
                            textView7_fee.setText("203원");
                        }
                    }
                    else{
                        textView7_info.setText("톤");
                        textView7_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("톤");
                        textView7_fee.setText("306원");
                    }
                    else{
                        textView7_info.setText("톤");
                        textView7_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView7_info.setText("톤");
                            textView7_fee.setText("194원");
                        }else{
                            textView7_info.setText("톤");
                            textView7_fee.setText("120원");
                        }
                    }
                    else{
                        textView7_info.setText("톤");
                        textView7_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("톤");
                        textView7_fee.setText("203원");
                    }else{
                        textView7_info.setText("톤");
                        textView7_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("톤");
                        textView7_fee.setText("192원");
                    }else{
                        textView7_info.setText("톤");
                        textView7_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("톤");
                        textView7_fee.setText("120원");
                    }else{
                        textView7_info.setText("톤");
                        textView7_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView7_info.setText("톤");
                textView7_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("배럴");
                        textView7_fee.setText("11.1원");
                    }else{
                        textView7_info.setText("배럴");
                        textView7_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("배럴");
                        textView7_fee.setText("11.1원");
                    }else{
                        textView7_info.setText("배럴");
                        textView7_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("배럴");
                        textView7_fee.setText("11.1원");
                    }else{
                        textView7_info.setText("배럴");
                        textView7_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("2210원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("2100원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("1371원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("4420원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("4200원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("2742원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("7510원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("7140원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("4659원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("8840원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("8400원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("5484원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("10160원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("9660원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView7_info.setText("개");
                        textView7_fee.setText("6306원");
                    }else{
                        textView7_info.setText("개");
                        textView7_fee.setText("2670원");
                    }
                }
                break;
            }

        }
    }

    /*Spinner6*/
    public void spinner6_check(int position){
        switch (position){
            case 0:{
                textView6_info.setText("톤");
                textView6_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView6_info.setText("톤");
                            textView6_fee.setText("341원");
                        }else{
                            textView6_info.setText("톤");
                            textView6_fee.setText("203원");
                        }
                    }
                    else{
                        textView6_info.setText("톤");
                        textView6_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("톤");
                        textView6_fee.setText("306원");
                    }
                    else{
                        textView6_info.setText("톤");
                        textView6_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView6_info.setText("톤");
                            textView6_fee.setText("194원");
                        }else{
                            textView6_info.setText("톤");
                            textView6_fee.setText("120원");
                        }
                    }
                    else{
                        textView6_info.setText("톤");
                        textView6_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("톤");
                        textView6_fee.setText("203원");
                    }else{
                        textView6_info.setText("톤");
                        textView6_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("톤");
                        textView6_fee.setText("192원");
                    }else{
                        textView6_info.setText("톤");
                        textView6_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("톤");
                        textView6_fee.setText("120원");
                    }else{
                        textView6_info.setText("톤");
                        textView6_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView6_info.setText("톤");
                textView6_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("배럴");
                        textView6_fee.setText("11.1원");
                    }else{
                        textView6_info.setText("배럴");
                        textView6_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("배럴");
                        textView6_fee.setText("11.1원");
                    }else{
                        textView6_info.setText("배럴");
                        textView6_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("배럴");
                        textView6_fee.setText("11.1원");
                    }else{
                        textView6_info.setText("배럴");
                        textView6_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("2210원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("2100원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("1371원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("4420원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("4200원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("2742원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("7510원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("7140원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("4659원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("8840원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("8400원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("5484원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("10160원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("9660원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView6_info.setText("개");
                        textView6_fee.setText("6306원");
                    }else{
                        textView6_info.setText("개");
                        textView6_fee.setText("2670원");
                    }
                }
                break;
            }
        }
    }

    /*Spinner5*/
    public void spinner5_check(int position){
        switch (position){
            case 0:{
                textView5_info.setText("톤");
                textView5_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView5_info.setText("톤");
                            textView5_fee.setText("341원");
                        }else{
                            textView5_info.setText("톤");
                            textView5_fee.setText("203원");
                        }
                    }
                    else{
                        textView5_info.setText("톤");
                        textView5_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("톤");
                        textView5_fee.setText("306원");
                    }
                    else{
                        textView5_info.setText("톤");
                        textView5_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView5_info.setText("톤");
                            textView5_fee.setText("194원");
                        }else{
                            textView5_info.setText("톤");
                            textView5_fee.setText("120원");
                        }
                    }
                    else{
                        textView5_info.setText("톤");
                        textView5_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("톤");
                        textView5_fee.setText("203원");
                    }else{
                        textView5_info.setText("톤");
                        textView5_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("톤");
                        textView5_fee.setText("192원");
                    }else{
                        textView5_info.setText("톤");
                        textView5_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("톤");
                        textView5_fee.setText("120원");
                    }else{
                        textView5_info.setText("톤");
                        textView5_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView5_info.setText("톤");
                textView5_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("배럴");
                        textView5_fee.setText("11.1원");
                    }else{
                        textView5_info.setText("배럴");
                        textView5_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("배럴");
                        textView5_fee.setText("11.1원");
                    }else{
                        textView5_info.setText("배럴");
                        textView5_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("배럴");
                        textView5_fee.setText("11.1원");
                    }else{
                        textView5_info.setText("배럴");
                        textView5_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("2210원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("2100원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("1371원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("4420원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("4200원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("2742원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("7510원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("7140원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("4659원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("8840원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("8400원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("5484원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9: {
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("10160원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("9660원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView5_info.setText("개");
                        textView5_fee.setText("6306원");
                    }else{
                        textView5_info.setText("개");
                        textView5_fee.setText("2670원");
                    }
                }
                break;
            }
        }
    }

    /*Spinner4*/
    public void spinner4_check(int position){
        switch (position){
            case 0:{
                textView4_info.setText("톤");
                textView4_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView4_info.setText("톤");
                            textView4_fee.setText("341원");
                        }else{
                            textView4_info.setText("톤");
                            textView4_fee.setText("203원");
                        }
                    }
                    else{
                        textView4_info.setText("톤");
                        textView4_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("톤");
                        textView4_fee.setText("306원");
                    }
                    else{
                        textView4_info.setText("톤");
                        textView4_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView4_info.setText("톤");
                            textView4_fee.setText("194원");
                        }else{
                            textView4_info.setText("톤");
                            textView4_fee.setText("120원");
                        }
                    }
                    else{
                        textView4_info.setText("톤");
                        textView4_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("톤");
                        textView4_fee.setText("203원");
                    }else{
                        textView4_info.setText("톤");
                        textView4_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("톤");
                        textView4_fee.setText("192원");
                    }else{
                        textView4_info.setText("톤");
                        textView4_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("톤");
                        textView4_fee.setText("120원");
                    }else{
                        textView4_info.setText("톤");
                        textView4_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView4_info.setText("톤");
                textView4_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("배럴");
                        textView4_fee.setText("11.1원");
                    }else{
                        textView4_info.setText("배럴");
                        textView4_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("배럴");
                        textView4_fee.setText("11.1원");
                    }else{
                        textView4_info.setText("배럴");
                        textView4_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("배럴");
                        textView4_fee.setText("11.1원");
                    }else{
                        textView4_info.setText("배럴");
                        textView4_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("2210원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("2100원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("1371원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("4420원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("4200원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("2742원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("7510원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("7140원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("4659원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("8840원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("8400원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("5484원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("10160원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("9660원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView4_info.setText("개");
                        textView4_fee.setText("6306원");
                    }else{
                        textView4_info.setText("개");
                        textView4_fee.setText("2670원");
                    }
                }
                break;
            }


        }
    }

    /*Spinner3*/
    public void spinner3_check(int position){
        switch (position){
            case 0:{
                textView3_info.setText("톤");
                textView3_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView3_info.setText("톤");
                            textView3_fee.setText("341원");
                        }else{
                            textView3_info.setText("톤");
                            textView3_fee.setText("203원");
                        }
                    }
                    else{
                        textView3_info.setText("톤");
                        textView3_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("톤");
                        textView3_fee.setText("306원");
                    }
                    else{
                        textView3_info.setText("톤");
                        textView3_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView3_info.setText("톤");
                            textView3_fee.setText("194원");
                        }else{
                            textView3_info.setText("톤");
                            textView3_fee.setText("120원");
                        }
                    }
                    else{
                        textView3_info.setText("톤");
                        textView3_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("톤");
                        textView3_fee.setText("203원");
                    }else{
                        textView3_info.setText("톤");
                        textView3_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("톤");
                        textView3_fee.setText("192원");
                    }else{
                        textView3_info.setText("톤");
                        textView3_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("톤");
                        textView3_fee.setText("120원");
                    }else{
                        textView3_info.setText("톤");
                        textView3_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView3_info.setText("톤");
                textView3_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("배럴");
                        textView3_fee.setText("11.1원");
                    }else{
                        textView3_info.setText("배럴");
                        textView3_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("배럴");
                        textView3_fee.setText("11.1원");
                    }else{
                        textView3_info.setText("배럴");
                        textView3_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("배럴");
                        textView3_fee.setText("11.1원");
                    }else{
                        textView3_info.setText("배럴");
                        textView3_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("2210원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("2100원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("1371원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("4420원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("4200원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("2742원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("7510원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("7140원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("4659원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("8840원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("8400원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("5484원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("10160원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("9660원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView3_info.setText("개");
                        textView3_fee.setText("6306원");
                    }else{
                        textView3_info.setText("개");
                        textView3_fee.setText("2670원");
                    }
                }
                break;
            }
        }
    }

    /*Spinner2*/
    public void spinner2_check(int position){
        switch (position){
            case 0:{
                textView2_info.setText("톤");
                textView2_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView2_info.setText("톤");
                            textView2_fee.setText("341원");
                        }else{
                            textView2_info.setText("톤");
                            textView2_fee.setText("203원");
                        }
                    }
                    else{
                        textView2_info.setText("톤");
                        textView2_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("톤");
                        textView2_fee.setText("306원");
                    }
                    else{
                        textView2_info.setText("톤");
                        textView2_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView2_info.setText("톤");
                            textView2_fee.setText("194원");
                        }else{
                            textView2_info.setText("톤");
                            textView2_fee.setText("120원");
                        }
                    }
                    else{
                        textView2_info.setText("톤");
                        textView2_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("톤");
                        textView2_fee.setText("203원");
                    }else{
                        textView2_info.setText("톤");
                        textView2_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("톤");
                        textView2_fee.setText("192원");
                    }else{
                        textView2_info.setText("톤");
                        textView2_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("톤");
                        textView2_fee.setText("120원");
                    }else{
                        textView2_info.setText("톤");
                        textView2_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView2_info.setText("톤");
                textView2_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("배럴");
                        textView2_fee.setText("11.1원");
                    }else{
                        textView2_info.setText("배럴");
                        textView2_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("배럴");
                        textView2_fee.setText("11.1원");
                    }else{
                        textView2_info.setText("배럴");
                        textView2_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("배럴");
                        textView2_fee.setText("11.1원");
                    }else{
                        textView2_info.setText("배럴");
                        textView2_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("2210원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("2100원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("1371원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("4420원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("4200원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("2742원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("7510원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("7140원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("4659원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("8840원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("8400원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("5484원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("10160원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("9660원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView2_info.setText("개");
                        textView2_fee.setText("6306원");
                    }else{
                        textView2_info.setText("개");
                        textView2_fee.setText("2670원");
                    }
                }
                break;
            }

        }
    }

    /*Spinner1*/
    public void spinner1_check(int position){
        switch (position){
            case 0:{
                textView1_info.setText("톤");
                textView1_fee.setText("0원");
                break;
            }
            case 1:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView1_info.setText("톤");
                            textView1_fee.setText("341원");
                        }else{
                            textView1_info.setText("톤");
                            textView1_fee.setText("203원");
                        }
                    }
                    else{
                        textView1_info.setText("톤");
                        textView1_fee.setText("90원");
                    }

                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("톤");
                        textView1_fee.setText("306원");
                    }
                    else{
                        textView1_info.setText("톤");
                        textView1_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        if(radio_income.isChecked()){
                            textView1_info.setText("톤");
                            textView1_fee.setText("194원");
                        }else{
                            textView1_info.setText("톤");
                            textView1_fee.setText("120원");
                        }
                    }
                    else{
                        textView1_info.setText("톤");
                        textView1_fee.setText("54원");
                    }
                }
                break;
            }
            case 2:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("톤");
                        textView1_fee.setText("203원");
                    }else{
                        textView1_info.setText("톤");
                        textView1_fee.setText("51원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("톤");
                        textView1_fee.setText("192원");
                    }else{
                        textView1_info.setText("톤");
                        textView1_fee.setText("85원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("톤");
                        textView1_fee.setText("120원");
                    }else{
                        textView1_info.setText("톤");
                        textView1_fee.setText("51원");
                    }
                }
                break;
            }
            case 3:{
                textView1_info.setText("톤");
                textView1_fee.setText("27원");
                break;
            }
            case 4:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("배럴");
                        textView1_fee.setText("11.1원");
                    }else{
                        textView1_info.setText("배럴");
                        textView1_fee.setText("7.5원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("배럴");
                        textView1_fee.setText("11.1원");
                    }else{
                        textView1_info.setText("배럴");
                        textView1_fee.setText("7.5원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("배럴");
                        textView1_fee.setText("11.1원");
                    }else{
                        textView1_info.setText("배럴");
                        textView1_fee.setText("7.5원");
                    }
                }
                break;
            }
            case 5:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("2210원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("580원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("2100원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("580원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("1371원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("580원");
                    }
                }
                break;
            }
            case 6:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("4420원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("1161원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("4200원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("1161원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("2742원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("1161원");
                    }
                }
                break;
            }
            case 7:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("7510원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("1973원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("7140원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("1973원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("4659원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("1973원");
                    }
                }
                break;
            }
            case 8:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("8840원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("2322원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("8400원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("2322원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("5484원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("2322원");
                    }
                }
                break;
            }
            case 9:{
                if(radio_busan.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("10160원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("2670원");
                    }
                }else if(radio_incheon.isChecked()){
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("9660원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("2670원");
                    }
                }else{
                    if(radio_outport.isChecked()){
                        textView1_info.setText("개");
                        textView1_fee.setText("6306원");
                    }else{
                        textView1_info.setText("개");
                        textView1_fee.setText("2670원");
                    }
                }
                break;
            }
        }
    }
}