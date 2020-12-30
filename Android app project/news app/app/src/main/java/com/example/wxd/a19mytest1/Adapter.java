package com.example.wxd.a19mytest1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.VH> {

    private final List<New> data;
    private Context context;

//    public MyAdapter(List<Music>data, Handler handler){
//        this.data=data;
//        this.handler=handler;
//    }
//    public void ChangeIndex(int index){
//        lastIndex = index;
//        this.notifyDataSetChanged();
//    }

    public static class VH extends RecyclerView.ViewHolder{

        public final TextView title;
        public final TextView time;
        public final TextView src;
        public final ImageView pic;
        //        public String url;
//        private List<News> data;
//        private  Context context;
        public VH(View v){
            super(v);
            this.title = v.findViewById(R.id.txtTitle);
            this.time = v.findViewById(R.id.txtTime);
            this.src = v.findViewById(R.id.txtSrc);
            this.pic = v.findViewById(R.id.imgPic);
        }

    }

    public Adapter(List<New> data, Context activity){
        this.data = data;
        this.context=activity;
    }

    @NonNull
    @Override
    public Adapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new VH(view);
    }





    @Override
    public void onBindViewHolder(@NonNull final VH viewHolder, final int position) {
//        if (lastIndex==position)
//        {
//            viewHolder.itemView.setBackgroundColor(0xff666666);
//        }else {
//            viewHolder.itemView.setBackgroundColor(0x00666666);
//        }

        viewHolder.title.setText(data.get(position).title);
        viewHolder.time.setText(data.get(position).time);
        viewHolder.src.setText(data.get(position).src);
        Picasso.with(context).load(data.get(position).pic).into(viewHolder.pic);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 点击事件
              //  context = (Activity)arg0.getContext();
                Intent intent = new Intent(context,Webnewxqq.class);
                String str=data.get(position).weburl;
                intent.putExtra("test",str);
                context.startActivity(intent);

                //context.startActivityForResult(intent, REQUEST_CODE);

                //finsh();
                //startActivityForResult(intent,1);



            }
        });

        //viewHolder.url = (data.get(position).url);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewHolder.itemView.setBackgroundColor(0xff666666);
//                int oldIndex=lastIndex;
//                lastIndex = position;
//                notifyItemChanged(oldIndex);
//                if(oldIndex>=0)
//                    notifyItemChanged(oldIndex);
//                lastIndex=position;
//                Message msg=new Message();
//                msg.what=1;
//                msg.obj=position;
//                handler.sendMessage(msg);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}


