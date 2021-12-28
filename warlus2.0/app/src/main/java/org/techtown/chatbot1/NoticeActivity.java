package org.techtown.chatbot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoticeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoticeAdapter adapter;
    static RequestQueue requestQueue;
    ArrayList<Notice> arr_notice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoticeAdapter();
        recyclerView.setAdapter(adapter);

        //리사이클러뷰(어댑터) Item선택 이벤트처리
        adapter.setOnItemClickListener(new OnNoticeItemClickListener() {
            @Override
            public void onItemClick(NoticeAdapter.ViewHolder holder, View view, int position) {
                Notice item = adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra("url", item.url);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        makeRequest("busan");
        makeRequest("ulsan");
        makeRequest("incheon");
        makeRequest("yeosu");
    }

    public void makeRequest(String kind){

        String urlstr = "http://13.125.187.57:3001/notice/"+ kind; //(domain=10.0.2.2:3001)(domain=ngrok)(domain=ec2-3-34-43-179.ap-northeast-2.compute.amazonaws.com)
        StringRequest request = new StringRequest(Request.Method.GET, urlstr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String kind = jsonObject.getString("kind");
                            JSONArray arr_title = jsonObject.getJSONArray("title");
                            JSONArray arr_url = jsonObject.getJSONArray("url");
                            JSONArray arr_date = jsonObject.getJSONArray("date");


                            for(int i =0; i<arr_title.length(); i++) {
                                arr_notice.add(new Notice(kind,arr_title.getString(i),arr_url.getString(i), arr_date.getString(i)));

                            }
                            //어댑터에 데이터 추가!!!

                            adapter.setItems(arr_notice);
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                return params;
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