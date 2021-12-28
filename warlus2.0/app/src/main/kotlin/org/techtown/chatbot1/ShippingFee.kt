package org.techtown.chatbot1

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ShippingFee : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shipping_fee)


        var datePickerStart = findViewById<Button>(R.id.shipping_datepickerbtn_start)
        var datePickerEnd = findViewById<Button>(R.id.shipping_datepickerbtn_end)
        var timePickerStart = findViewById<Button>(R.id.shipping_timepickerbtn_start)
        var timePickerEnd = findViewById<Button>(R.id.shipping_timepickerbtn_end)
        var InOutrg = findViewById<RadioGroup>(R.id.shipping_inout_rg)
        var DCrg = findViewById<RadioGroup>(R.id.shipping_dc_rg)
        var CalBtn = findViewById<Button>(R.id.shipping_cal_btn)
        var ResetBtn = findViewById<Button>(R.id.shipping_reset_btn)
        var TonEt = findViewById<EditText>(R.id.shipping_ton_et)
        var fee1_tv = findViewById<TextView>(R.id.shipping_fee1_tv)
        var fee2_tv = findViewById<TextView>(R.id.shipping_fee2_tv)
        var fee3_tv = findViewById<TextView>(R.id.shipping_fee3_tv)
        var fee4_tv = findViewById<TextView>(R.id.shipping_fee4_tv)
        var fee5_tv = findViewById<TextView>(R.id.shipping_fee5_tv)
        var totalfee_tv = findViewById<TextView>(R.id.shipping_totalfee_tv)


        var result_fee1: Double
        var result_fee2: Double
        var result_fee3: Double
        var result_fee4: Double
        var result_fee5: Double
        var total_fee: Double


        var calendar = Calendar.getInstance();
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)

        datePickerStart.setOnClickListener {
            val dpd = DatePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    if (i2 < 9 && i3 < 10) {
                        datePickerStart.setText("${i}-0${i2 + 1}-0${i3}")
                    } else if (i3 < 10) {
                        datePickerStart.setText("${i}-${i2 + 1}-0${i3}")
                    } else if (i2 < 9) {
                        datePickerStart.setText("${i}-0${i2 + 1}-${i3}")
                    } else {
                        datePickerStart.setText("${i}-${i2 + 1}-${i3}")
                    }
                },
                year,
                month,
                day)
            dpd.show()
        }
        datePickerEnd.setOnClickListener {
            val dpd = DatePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    if (i2 < 9 && i3 < 10) {
                        datePickerEnd.setText("${i}-0${i2 + 1}-0${i3}")
                    } else if (i3 < 10) {
                        datePickerEnd.setText("${i}-${i2 + 1}-0${i3}")
                    } else if (i2 < 9) {
                        datePickerEnd.setText("${i}-0${i2 + 1}-${i3}")
                    } else {
                        datePickerEnd.setText("${i}-${i2 + 1}-${i3}")
                    }

                },
                year,
                month,
                day)
            dpd.show()
        }
        timePickerStart.setOnClickListener {
            val tpd = TimePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    if (i < 10 && i2 < 10) {
                        timePickerStart.setText("0${i}:0${i2}")
                    } else if (i < 10) {
                        timePickerStart.setText("0${i}:${i2}")
                    } else if (i2 < 10) {
                        timePickerStart.setText("${i}:0${i2}")
                    } else {
                        timePickerStart.setText("${i}:${i2}")
                    }
                },
                hour,
                minute,
                false)
            tpd.show()
        }
        timePickerEnd.setOnClickListener {
            val tpd = TimePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    if (i < 10 && i2 < 10) {
                        timePickerEnd.setText("0${i}:0${i2}")
                    } else if (i < 10) {
                        timePickerEnd.setText("0${i}:${i2}")
                    } else if (i2 < 10) {
                        timePickerEnd.setText("${i}:0${i2}")
                    } else {
                        timePickerEnd.setText("${i}:${i2}")
                    }
                },
                hour,
                minute,
                false)
            tpd.show()
        }
        fun CheckRadio(): String? {
            var id = InOutrg.getCheckedRadioButtonId()
            if (id == -1) {
                return "empty"
            } else {
                var rb = findViewById<RadioButton>(id)
                var result: String?
                result = rb.text as String?
                return result
            }
        }

        fun CheckRadio2(): String? {
            var id = DCrg.getCheckedRadioButtonId()
            if (id == -1) {
                return "empty"
            } else {
                var rb = findViewById<RadioButton>(id)
                var result: String?
                result = rb.text as String?
                if (result == "없음") {
                    return 0.0.toString()
                } else if (result == "10%") {
                    return 0.1.toString()
                } else if (result == "20%") {
                    return 0.2.toString()
                } else if (result == "50%") {
                    return 0.5.toString()
                } else {
                    return 0.7.toString()
                }

            }
        }

        fun GetTime(): Long {
            if (datePickerStart.getText().toString().equals("") || timePickerStart.getText()
                    .toString().equals("") ||
                datePickerEnd.getText().toString().equals("") || timePickerEnd.getText().toString()
                    .equals("")
            ) {
                return -1
            } else {
                var startdate = "${datePickerStart.getText()} ${timePickerStart.getText()}"
                var enddate = "${datePickerEnd.getText()} ${timePickerEnd.getText()}"
                var sf = SimpleDateFormat("yyyy-MM-dd HH:mm")

                var start = sf.parse(startdate)
                var end = sf.parse(enddate)
                var diff = (end.time - start.time) / (60 * 1000)
                var result: Long = 0
                if (diff % 60 > 0) {
                    result = diff / 60 + 1
                } else {
                    result = diff / 60
                }

                return result


            }
        }




        CalBtn.setOnClickListener {
            if (CheckRadio() == "empty" || CheckRadio2() == "empty" || TonEt.getText().toString()
                    .equals("") || GetTime().toInt() == -1
            ) {
                var builder = AlertDialog.Builder(this)
                builder.setTitle("정보")
                builder.setMessage("선택사항을 모두 선택하여 주십시오")
                builder.show()

            } else {
                if(GetTime().toInt() < 0){
                    var builder = AlertDialog.Builder(this)
                    builder.setTitle("정보")
                    builder.setMessage("시작시간과 종료시간을 올바르게 선택하여 주십시오.")
                    builder.show()

                }
                else if (CheckRadio() == "외항") {
                    var ton = TonEt.getText().toString().toInt()
                    var time = GetTime().toDouble()
                    var discount = CheckRadio2()!!.toDouble()
                    result_fee1 = Math.floor(ton * 135.0)
                    result_fee2 = ton * 24.0
                    if (time > 12) {
                        result_fee3 = time * ton * 29.892 / 10 * (1 - discount)
                        result_fee4 = time * ton * 15.686 / 10 * (1 - discount)
                    } else {
                        result_fee3 = ton * 358 / 10 * (1 - discount)
                        result_fee4 = ton * 187 / 10 * (1 - discount)
                    }


                    result_fee5 = ton * 3.0
                    total_fee = result_fee1 + result_fee2 + result_fee3 + result_fee4 + result_fee5

                    fee1_tv.text = result_fee1.toInt().toString() + "원"
                    fee2_tv.text = result_fee2.toInt().toString() + "원"
                    fee3_tv.text = result_fee3.toInt().toString() + "원"
                    fee4_tv.text = result_fee4.toInt().toString() + "원"
                    fee5_tv.text = result_fee5.toInt().toString() + "원"
                    totalfee_tv.text = total_fee.toInt().toString() + "원"


                } else if (CheckRadio() == "내항") {
                    var ton = TonEt.getText().toString().toInt()
                    var time = GetTime().toDouble()
                    var discount = CheckRadio2()!!.toDouble()
                    result_fee1 = ton * 0.0
                    result_fee2 = ton * 0.0
                    if (time > 12) {
                        result_fee3 = time * ton * (1 - discount)
                        result_fee4 = time * ton * 5.186 / 10 * (1 - discount)
                    } else {
                        result_fee3 = ton / 10 * 120 * (1 - discount)
                        result_fee4 = ton / 10 * 61 * (1 - discount)
                    }


                    result_fee5 = ton * 0.0
                    total_fee = result_fee1 + result_fee2 + result_fee3 + result_fee4 + result_fee5

                    fee1_tv.text = result_fee1.toInt().toString() + "원"
                    fee2_tv.text = result_fee2.toInt().toString() + "원"
                    fee3_tv.text = result_fee3.toInt().toString() + "원"
                    fee4_tv.text = result_fee4.toInt().toString() + "원"
                    fee5_tv.text = result_fee5.toInt().toString() + "원"
                    totalfee_tv.text = total_fee.toInt().toString() + "원"

                }
            }
        }

        ResetBtn.setOnClickListener {
            fee1_tv.text = "0원"
            fee2_tv.text = "0원"
            fee3_tv.text = "0원"
            fee4_tv.text = "0원"
            fee5_tv.text = "0원"
            totalfee_tv.text = "0원"
            TonEt.setText("")
            datePickerStart.setText("")
            datePickerEnd.setText("")
            timePickerStart.setText("")
            timePickerEnd.setText("")
            InOutrg.clearCheck()
            DCrg.clearCheck()

        }


    }


}


