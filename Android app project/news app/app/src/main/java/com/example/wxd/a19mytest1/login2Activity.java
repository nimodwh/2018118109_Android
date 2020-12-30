package com.example.wxd.a19mytest1;

import android.content.Intent;
import android.os.Bundle;
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

public class login2Activity extends AppCompatActivity {

    TextView tvStuNumber;
    EditText etOriginPwd,etNewPwd,etConfirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //取消事件
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStuNumber = findViewById(R.id.tv_stu_number);
        tvStuNumber.setText(this.getIntent().getStringExtra("stu_number"));
        etOriginPwd = findViewById(R.id.et_original_pwd);
        etNewPwd = findViewById(R.id.et_new_pwd);
        etConfirmPwd = findViewById(R.id.et_confirm_new_pwd);
        Button btnModify = findViewById(R.id.btn_modify_pwd);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先保证输入合法
                if(CheckInput()) {
                    String stuNumber = tvStuNumber.getText().toString();
                    UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(),UserDbHelper.DB_NAME,null,1);
                    LinkedList<User> users = dbHelper.readUsers();
                    for(User user : users) {
                        //首先找到用户名
                        if(user.getUsername().equals(stuNumber)) {
                            if(!etOriginPwd.getText().toString().equals(user.getPassword())) {
                                //提示初始密码输入错误
                                Toast.makeText(getApplicationContext(),"初始密码输入错误!",Toast.LENGTH_SHORT).show();
                            }else {
                                //执行修改密码操作
                                user.setPassword(etNewPwd.getText().toString());
                                boolean flag = dbHelper.updateUser(tvStuNumber.getText().toString(),etNewPwd.getText().toString());
                                if(flag) {
                                    Toast.makeText(getApplicationContext(),"修改密码成功!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"修改密码失败!",Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }

    //判断输入的合法性
    public boolean CheckInput() {
        String OriginalPwd = etOriginPwd.getText().toString();
        String NewPwd = etNewPwd.getText().toString();
        String NewConfirmPwd = etConfirmPwd.getText().toString();
        if(OriginalPwd.trim().equals("")) {
            Toast.makeText(this,"原始密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(NewPwd.trim().equals("")) {
            Toast.makeText(this,"新密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(NewConfirmPwd.trim().equals("")) {
            Toast.makeText(this,"确认新密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!NewPwd.trim().equals(NewConfirmPwd.trim())) {
            Toast.makeText(this,"两次密码输入不一致!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

