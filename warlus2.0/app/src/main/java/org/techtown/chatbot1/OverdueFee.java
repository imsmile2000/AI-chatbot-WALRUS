package org.techtown.chatbot1;


import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.app.DatePickerDialog;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;

public class OverdueFee extends AppCompatActivity {

    int year_start,month_start,day_start;
    int year_end,month_end,day_end;

    EditText edt_input;
    ImageButton btn_start, btn_end;
    Button btn_calculate, btn_reset;
    TextView textView_start, textView_end, textView_output, textView_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overdue_fee);

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str_sdf = sdf.format(today);
        String[] arr = str_sdf.split("-");
        year_start = Integer.parseInt(arr[0]);
        month_start = Integer.parseInt(arr[1])-1;
        day_start = Integer.parseInt(arr[2]);
        year_end = Integer.parseInt(arr[0]);
        month_end = Integer.parseInt(arr[1])-1;
        day_end = Integer.parseInt(arr[2]);

        edt_input = (EditText)findViewById(R.id.overdue_edt_input);
        textView_start = (TextView)findViewById(R.id.overdue_textview_start);
        textView_end = (TextView)findViewById(R.id.overdue_textView_end);
        textView_output = (TextView)findViewById(R.id.overdue_textView_output);
        textView_info = (TextView)findViewById(R.id.overdue_textView_info);

        textView_info.setText("→ 2018년 6월 25일 이전\n\n" +
                "1개월 미만: 12% (원금x0.12x연체일/365)\n"+ "1개월 이상 3개월 미만: 13% (원금x0.13x연체일/365)\n"+
                "3개월 이상 6개월 미만: 14% (원금x0.14x연체일/365)\n"+ "6개월 이상: 15% (원금x0.15x연체일/365)\n\n\n"+
                "→ 2018년 6월 25일 이후\n\n"+
                "1개월 미만: 7% (원금x0.07x연체일/365)\n"+ "1개월 이상 3개월 미만: 8% (원금x0.08x연체일/365)\n"+
                "3개월 이상 6개월 미만: 9% (원금x0.09x연체일/365)\n"+ "6개월 이상: 10% (원금x0.10x연체일/365)\n");

        btn_start = (ImageButton)findViewById(R.id.overdue_btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OverdueFee.this, AlertDialog.THEME_HOLO_LIGHT ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1++;
                        String str_month="";
                        if(i1 / 10 ==0 ){ str_month = "0"+i1; }
                        else{ str_month = ""+i1; }

                        String str_day="";
                        if(i2 / 10 == 0){ str_day="0"+i2;}
                        else{str_day =""+i2;}
                        textView_start.setText(i+"-"+str_month+"-"+str_day);

                        year_start = i;
                        month_start = i1-1;
                        day_start = i2;
                    }
                },year_start,month_start,day_start);

                datePickerDialog.show();

            }
        });

        btn_end = (ImageButton)findViewById(R.id.overdue_btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OverdueFee.this,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1++;
                        String str_month="";
                        if(i1 / 10 ==0 ){ str_month = "0"+i1; }
                        else{ str_month = ""+i1; }

                        String str_day="";
                        if(i2 / 10 == 0){ str_day="0"+i2;}
                        else{str_day =""+i2;}
                        textView_end.setText(i+"-"+str_month+"-"+str_day);

                        year_end = i;
                        month_end = i1-1;
                        day_end = i2;
                    }
                },year_end,month_end,day_end);

                datePickerDialog.show();
            }
        });

        btn_calculate = (Button)findViewById(R.id.overdue_btn_calculate);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String str_input = edt_input.getText().toString();

                    String str_start = textView_start.getText().toString();
                    String str_end = textView_end.getText().toString();
                    String str_basis ="2018-06-25";

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date s_date = format.parse(str_start);
                    Date e_date = format.parse(str_end);
                    Date basis_date = format.parse(str_basis);

                    double difftime = e_date.getTime() - s_date.getTime();
                    double diffDay=0.0;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(e_date);

                    switch (cal.get(Calendar.DAY_OF_WEEK)){
                        case 7:{ /*토요일*/
                            diffDay =  (difftime / (24*60*60*1000))-1;
                            break;
                        }
                        case 1: { /*일요일*/
                            diffDay =  (difftime / (24*60*60*1000))-2;
                            break;
                        }
                        default:{
                            diffDay =  difftime / (24*60*60*1000);
                            break;
                        }
                    }

                    double result= 0;
                    if(diffDay> 1){

                        int num_input = Integer.parseInt(str_input);

                        if(basis_date.getTime()>= s_date.getTime()){ /*2018년 6월 25일 이전*/
                            if(year_start==year_end) {/*년도 같을때*/
                                if(month_end - month_start>=6) {
                                    if(day_end-day_start>=0) {/*6개월이상*/
                                        result = num_input*0.15*diffDay/365;
                                    }else {/*3개월이상~ 6개월미만*/
                                        result = num_input*0.14*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start>=3) {
                                    if(day_end-day_start>=0) {/*3개월이상~ 6개월미만*/
                                        result = num_input*0.14*diffDay/365;
                                    }else {/*1개월이상~ 3개월미만*/
                                        result = num_input*0.13*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start>=1) {

                                    if(day_end-day_start>=0) {/*1개월이상~ 3개월미만*/
                                        result = num_input*0.13*diffDay/365;
                                    }else {/*1개월미만*/
                                        result = num_input*0.12*diffDay/365;
                                    }
                                }
                                else {
                                    if(day_end-day_start>=0) {/*1개월미만*/
                                        result = num_input*0.12*diffDay/365;
                                    }else { /*x*/

                                    }
                                }
                            }
                            else if(year_end-year_start==1) { /*년도 1년차*/
                                if(month_end - month_start> -6) { /*6개월이상*/
                                    result = num_input*0.15*diffDay/365;
                                }
                                else if(month_end - month_start == -6) {

                                    if(day_end-day_start>=0) {/*6개월이상*/
                                        result = num_input*0.15*diffDay/365;
                                    }else {/*3개월이상 6개월미만*/
                                        result = num_input*0.14*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start> -9) {
                                    result = num_input*0.14*diffDay/365; /*3개월이상 6개월미만*/
                                }
                                else if(month_end - month_start == -9) {

                                    if(day_end-day_start>=0) {/*3개월이상 6개월 미만*/
                                        result = num_input*0.14*diffDay/365;
                                    }else {/*1개월이상 3개월미만*/
                                        result = num_input*0.13*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start> -11) {
                                    result = num_input*0.13*diffDay/365; /*1개월이상 3개월미만*/
                                }
                                else if(month_end - month_start == -11) {
                                    if(day_end-day_start>=0) {/*1개월이상 3개월미만*/
                                        result = num_input*0.13*diffDay/365;
                                    }else {/*1개월미만*/
                                        result = num_input*0.12*diffDay/365;
                                    }
                                }
                            }
                            else {/*년도 2년이상차*/
                                result = num_input*0.15*diffDay/365;
                            }

                        }else { /*2018년 6월 26일 이후*/
                            if(year_start==year_end) {/*년도 같을때*/
                                if(month_end - month_start>=6) {
                                    if(day_end-day_start>=0) {/*6개월이상*/
                                        result = num_input*0.10*diffDay/365;
                                    }else {/*3개월이상~ 6개월미만*/
                                        result = num_input*0.09*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start>=3) {
                                    if(day_end-day_start>=0) {/*3개월이상~ 6개월미만*/
                                        result = num_input*0.09*diffDay/365;
                                    }else {/*1개월이상~ 3개월미만*/
                                        result = num_input*0.08*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start>=1) {

                                    if(day_end-day_start>=0) {/*1개월이상~ 3개월미만*/
                                        result = num_input*0.08*diffDay/365;
                                    }else {/*1개월미만*/
                                        result = num_input*0.07*diffDay/365;
                                    }
                                }
                                else {
                                    if(day_end-day_start>=0) {/*1개월미만*/
                                        result = num_input*0.07*diffDay/365;
                                    }else { /*x*/

                                    }
                                }
                            }
                            else if(year_end-year_start==1) { /*년도 1년차*/
                                if(month_end - month_start> -6) { /*6개월이상*/
                                    result = num_input*0.10*diffDay/365;
                                }
                                else if(month_end - month_start == -6) {

                                    if(day_end-day_start>=0) {/*6개월이상*/
                                        result = num_input*0.10*diffDay/365;
                                    }else {/*3개월이상 6개월미만*/
                                        result = num_input*0.09*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start> -9) {
                                    result = num_input*0.09*diffDay/365; /*3개월이상 6개월미만*/
                                }
                                else if(month_end - month_start == -9) {

                                    if(day_end-day_start>=0) {/*3개월이상 6개월 미만*/
                                        result = num_input*0.09*diffDay/365;
                                    }else {/*1개월이상 3개월미만*/
                                        result = num_input*0.08*diffDay/365;
                                    }
                                }
                                else if(month_end - month_start> -11) {
                                    result = num_input*0.08*diffDay/365; /*1개월이상 3개월미만*/
                                }
                                else if(month_end - month_start == -11) {
                                    if(day_end-day_start>=0) {/*1개월이상 3개월미만*/
                                        result = num_input*0.08*diffDay/365;
                                    }else {/*1개월미만*/
                                        result = num_input*0.07*diffDay/365;
                                    }
                                }
                            }
                            else {/*년도 2년이상차*/
                                result = num_input*0.10*diffDay/365;
                            }
                        }

                        System.out.println(result);
                        System.out.println(diffDay);
                        System.out.println(num_input);
                        result = Math.floor(result/10) * 10;
                        int result_int = (int) result;
                        textView_output.setText(""+result_int);

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(OverdueFee.this);
                        builder.setTitle("정보");
                        builder.setMessage("연체대상이 아닙니다. 납부기한을 올바르게 입력해주세요.");
                        AlertDialog alertDialog = builder.create();

                        alertDialog.show();
                    }

                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(OverdueFee.this);
                    builder.setTitle("정보");
                    builder.setMessage("연체대상이 아닙니다. 고지금액과 납부기한을 올바르게 입력해주세요.");
                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                    e.printStackTrace();
                }
            }
        });

        btn_reset = (Button) findViewById(R.id.overdue_btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_input.setText("");
                textView_start.setText("날짜 선택");
                textView_end.setText("날짜 선택");
                textView_output.setText("0");

                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str_sdf = sdf.format(today);
                String[] arr = str_sdf.split("-");
                year_start = Integer.parseInt(arr[0]);
                month_start = Integer.parseInt(arr[1])-1;
                day_start = Integer.parseInt(arr[2]);
                year_end = Integer.parseInt(arr[0]);
                month_end = Integer.parseInt(arr[1])-1;
                day_end = Integer.parseInt(arr[2]);
            }
        });

    }
}