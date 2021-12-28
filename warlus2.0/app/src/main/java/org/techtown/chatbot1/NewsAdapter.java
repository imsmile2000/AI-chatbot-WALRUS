package org.techtown.chatbot1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements OnNewsItemClickListener{

    private int lastPosition = -1; //애니메이션에서 사용
    ArrayList<News> items = new ArrayList<>();
    OnNewsItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView news_image;
        TextView news_title, news_date;
        
        //생성자
        public ViewHolder(View itemView, final OnNewsItemClickListener listener) {
            super(itemView);

            news_image = itemView.findViewById(R.id.news_image);
            news_title = itemView.findViewById(R.id.news_title);
            news_date = itemView.findViewById(R.id.news_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener !=null){
                        listener.onItemClick(ViewHolder.this , view, position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.news_item, viewGroup, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        News item = items.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.image_url)
                .error(R.drawable.ic_baseline_wallpaper_24)
                .into(viewHolder.news_image);
        viewHolder.news_title.setText(item.getTitle());
        viewHolder.news_date.setText(item.getDate());

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //NewsActivity에서 Item선택 이벤트 처리시 사용
    public void setOnItemClickListener(OnNewsItemClickListener listener){
        this.listener = listener;
    }

    //OnNewsItemClickListener인터페이스 메소드 재정의
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener !=null){
            listener.onItemClick(holder,view, position);
        }
    }

    //BindViewHolder에서 애니메이션 사용
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    //NewsActivity에서 adpeter에 데이터 추가시 사용
    public void setItems(ArrayList<News> items){
        this.items = items;
    }

    //NewsActivity에서 Item선택 이벤트 처리시 사용
    public News getItem(int position){
        return items.get(position);
    }

}
