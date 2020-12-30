package com.example.wxd.a19mytest1;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

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

import static com.example.wxd.a19mytest1.Frgment2.k;

public class seaech extends BaseActivity {

    String name;
    RecyclerView recyclerview1;
    Adapter adapters;
    private RefreshLayout refreshLayout;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event){
        if(event.getI()==0){
//
            adapters=new Adapter(xinxi,this);
            recyclerview1.setAdapter(adapters);

        }else if(event.getI()==1){
            adapters.notifyDataSetChanged();
        }
    }
    @NonNull

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaech);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        System.out.println("qwerrtrydr");
        name = getIntent().getStringExtra("aa");
        recyclerview1=findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview1.setLayoutManager(layoutManager);
        getHttpData();


        refreshLayout =findViewById(R.id.refreshLayout1);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                xinxi.clear();
                getHttpData();
                //adapters.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getHttpData();
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
    }

    void init() {
getHttpData();
    }
    List<New> xinxi=new ArrayList<>();
    public void getHttpData(){

        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.jisuapi.com/news/search?keyword="+name+"&appkey=68f29ab9601cb697").build();

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
                    for(int i=0;i<10;i++)
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

}