package org.techtown.chatbot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lakue.pagingbutton.LakuePagingButton;
import com.lakue.pagingbutton.OnPageSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsActivity extends AppCompatActivity {
    LakuePagingButton lpb_buttonlist;
    RecyclerView recyclerView;
    NewsAdapter adapter;
    static RequestQueue requestQueue;
    ArrayList<News> arr_news = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false); //리사이클러뷰 레이아웃설정
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter();    //리사이클러뷰 어댑터설정
        recyclerView.setAdapter(adapter);

        //리사이클러뷰(어댑터) Item선택 이벤트처리
        adapter.setOnItemClickListener(new OnNewsItemClickListener() {
            @Override
            public void onItemClick(NewsAdapter.ViewHolder holder, View view, int position) {
                News item = adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra("url",item.url);
                startActivity(intent);
            }
        });

        lpb_buttonlist = findViewById(R.id.lpb_buttonlist);
        lpb_buttonlist.setPageItemCount(5); //한 번에 표시되는 버튼 수 (기본값 : 5)
        lpb_buttonlist.addBottomPageButton(50,1); //총 페이지 버튼 수와 현재 페이지 설정

        lpb_buttonlist.setOnPageSelectListener(new OnPageSelectListener() { //페이지 리스너를 클릭했을 때의 이벤트
            @Override
            public void onPageBefore(int now_page) {
                lpb_buttonlist.addBottomPageButton(50,now_page); //총 페이지 버튼 수와 현재 페이지 설정
                arr_news = new ArrayList<>();
                makeRequest((now_page* 10 )- 9);
            }

            @Override
            public void onPageCenter(int now_page) {
                arr_news = new ArrayList<>();
                makeRequest((now_page* 10 )- 9);
            }

            @Override
            public void onPageNext(int now_page) {
                lpb_buttonlist.addBottomPageButton(50,now_page); //총 페이지 버튼 수와 현재 페이지 설정
                arr_news = new ArrayList<>();
                makeRequest((now_page* 10 )- 9);
            }
        });


        requestQueue = Volley.newRequestQueue(this); //서버에 요청
        makeRequest(1);
    }

    public void makeRequest(int i){
        //(domain=10.0.2.2:3001)(domain=ngrok)(domain=ec2-3-34-43-179.ap-northeast-2.compute.amazonaws.com:3001)
        String urlstr = "http://13.125.187.57:3001/news/news?start="+ i;
        StringRequest request = new StringRequest(Request.Method.GET, urlstr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray arr_image = jsonObject.getJSONArray("image");
                            JSONArray arr_title = jsonObject.getJSONArray("title");
                            JSONArray arr_url = jsonObject.getJSONArray("url");
                            JSONArray arr_date = jsonObject.getJSONArray("date");


                            for(int i =0; i<arr_image.length(); i++) {
                                arr_news.add(new News(arr_image.getString(i),arr_title.getString(i),arr_url.getString(i),arr_date.getString(i)));
                            }
                            //어댑터에 데이터 추가!!!
                            adapter.setItems(arr_news);
                            adapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(0);

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