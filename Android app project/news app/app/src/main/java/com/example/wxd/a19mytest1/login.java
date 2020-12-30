package com.example.wxd.a19mytest1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wxd.a19mytest1.bean.User;
import com.example.wxd.a19mytest1.util.UserDbHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.androidannotations.annotations.EActivity;



public class login extends AppCompatActivity {

    EditText EtStuNumber,EtStuPwd;
    private String username;

    LinkedList<User> users = new LinkedList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvRegister = findViewById(R.id.tv_register);
        //跳转到注册界面
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
            }
        });
        EtStuNumber = findViewById(R.id.et_username);
        EtStuPwd = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if(CheckInput()) {
                    UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(),UserDbHelper.DB_NAME,null,1);
                    users = dbHelper.readUsers();
                    for(User user : users) {
                        //如果可以找到,则输出登录成功,并跳转到主界面
                        if(user.getUsername().equals(EtStuNumber.getText().toString()) && user.getPassword().equals(EtStuPwd.getText().toString()) ) {
                            flag = true;
                            Toast.makeText(login.this,"恭喜你登录成功!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this,MainActivity.class);
                            Bundle bundle = new Bundle();
                            username = EtStuNumber.getText().toString();
                            bundle.putString("username",username);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                    //否则提示登录失败,需要重新输入
                    if (!flag) {
                        Toast.makeText(login.this,"学号或密码输入错误!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //检查输入是否符合要求
    public boolean CheckInput() {
        String StuNumber = EtStuNumber.getText().toString();
        String StuPwd = EtStuPwd.getText().toString();
        if(StuNumber.trim().equals("")) {
            Toast.makeText(login.this,"学号不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuPwd.trim().equals("")) {
            Toast.makeText(login.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
