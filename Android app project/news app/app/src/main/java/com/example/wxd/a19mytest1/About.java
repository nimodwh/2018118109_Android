package com.example.wxd.a19mytest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;

@EActivity(R.layout.about)
public class About extends AppCompatActivity {
   @ViewById(R.id.button_right4)
    Button button_right4;
    @Click({R.id.button_right4})
    void buttonClicked(Button button){
        switch (button.getId()) {
            case R.id.button_right4:
                Intent intent1 = new Intent(About.this,MainActivity_.class);
                startActivityForResult(intent1,1);
                break;
        }
        }


//    @AfterViews
//    void init()
//    {
//
//    }
}
