package org.techtown.chatbot1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> implements OnNoticeItemClickListener{

    private int lastPosition = -1; //애니메이션에서 사용
    ArrayList<Notice> items = new ArrayList<>();
    OnNoticeItemClickListener listener;


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView notice_textview, notice_title, notice_date;

        public ViewHolder(@NonNull View itemView, final OnNoticeItemClickListener listener) {
            super(itemView);

            notice_textview = itemView.findViewById(R.id.notice_textview);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.notice_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.notice_item, viewGroup, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Notice item = items.get(position);

        viewHolder.notice_textview.setText(item.getKind());
        viewHolder.notice_title.setText(item.getTitle());
        viewHolder.notice_date.setText(item.getDate());

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //NoticeActivity에서 Item선택 이벤트 처리시 사용
    public void setOnItemClickListener(OnNoticeItemClickListener listener){
        this.listener = listener;
    }

    //OnNoticeItemClickListener인터페이스 메소드 재정의
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

    //NoticeActivity에서 adpeter에 데이터 추가시 사용
    public void setItems(ArrayList<Notice> items){
        this.items = items;
    }

    //NoticeActivity에서 Item선택 이벤트 처리시 사용
    public Notice getItem(int position){
        return items.get(position);
    }
}