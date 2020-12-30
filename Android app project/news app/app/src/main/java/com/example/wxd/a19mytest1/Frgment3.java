package com.example.wxd.a19mytest1;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wxd.a19mytest1.Adapter;
import com.example.wxd.a19mytest1.MessageEvent;
import com.example.wxd.a19mytest1.New;
import com.example.wxd.a19mytest1.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Frgment3 extends Fragment {
    RecyclerView recyclerview3;
    Adapter adapters;
    private RefreshLayout refreshLayout;
    static  int k =0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event){
        if(event.getI()==0){
//
            adapters=new Adapter(xinxi,getActivity());
            recyclerview3.setAdapter(adapters);

        }else if(event.getI()==1){
            adapters.notifyDataSetChanged();
    }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.activity_frgment1, null);
        View view = inflater.inflate(R.layout.activity_frgment3, container, false);
        //return view;

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        recyclerview3=view.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview3.setLayoutManager(layoutManager);
        getHttpData(k);


        refreshLayout =view.findViewById(R.id.refreshLayout3);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                xinxi.clear();
                getHttpData(k);
                //adapters.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                k=k+10;
                getHttpData1(k);
                //adapters.notifyDataSetChanged();
                //adapters.notifyItemMoved(k,k+10);
                refreshlayout.finishLoadmore();
                //recyclerview1.scrollToPosition(k+10);
            }


        });

        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        return view;
    }
    List<New> xinxi=new ArrayList<>();
    public void getHttpData(int key){

        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.jisuapi.com/news/get?channel=NBA&start=0&num=10&appkey=4516133fbf6e29f5").build();

        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.print("rrr");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())throw new IOException("Unexpected code"+response);

                try {
                    JSONObject responseJson=new JSONObject(response.body().string());
                    System.out.print(responseJson);
                    JSONObject result=responseJson.getJSONObject("result");
                    JSONArray array=result.getJSONArray("list");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject json=array.getJSONObject(i);
                        New xw = new New();
                        xw.title=json.getString("title");
                        xw.time=json.getString("time");
                        xw.src=json.getString("src");
                        xw.pic=json.getString("pic");
                        xw.weburl=json.getString("weburl");
                        xinxi.add(xw);
                    }
                    EventBus.getDefault().post(new MessageEvent(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void getHttpData1(int k) {
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.jisuapi.com/news/get?channel=NBA&start=0&num=10&appkey=4516133fbf6e29f5").build();

        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.print("rrr");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())throw new IOException("Unexpected code"+response);

                try {
                    JSONObject responseJson=new JSONObject(response.body().string());
                    System.out.print(responseJson);
                    JSONObject result=responseJson.getJSONObject("result");
                    JSONArray array=result.getJSONArray("list");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject json=array.getJSONObject(i);
                        New xw = new New();
                        xw.title=json.getString("title");
                        xw.time=json.getString("time");
                        xw.src=json.getString("src");
                        xw.pic=json.getString("pic");
                        xw.weburl=json.getString("weburl");
                        xinxi.add(xw);
                    }
                    EventBus.getDefault().post(new MessageEvent(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }





}
