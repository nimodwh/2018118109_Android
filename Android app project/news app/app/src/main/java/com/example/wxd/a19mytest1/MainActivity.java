package com.example.wxd.a19mytest1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    TextView tvUserName;
    @ViewById(R.id.my_viewpager)
    ViewPager my_viewpager;
    //private ViewPager my_viewpager;
    @ViewById(R.id.my_tab)
    TabLayout my_tab;
    @ViewById(R.id.searchView)
    SearchView searchView;

    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private class MyAdapter extends FragmentPagerAdapter{     //适配器
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);  //返回碎片集合的第几项
        }

        @Override
        public int getCount() {
            return fragments.size();    //返回碎片集合大小
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);    //返回标题的第几项
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == 1){
            if(resultCode == 1){
                if(data != null){
                    tvUserName.setText("用户名"+data.getStringExtra("username"));
                }
            }else {
                tvUserName.setText("用户名：xxxxxx");
            }
        }
    }

    @AfterViews
    void initViews() {

        final NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_normal);
        View view = navigationView.getHeaderView(0);
        ImageButton imageButton=view.findViewById(R.id.bt_login);
        tvUserName = view.findViewById(R.id.tvUsername);
        toolbar.setNavigationIcon(R.mipmap.cc);
        toolbar.inflateMenu(R.menu.menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.groupfirst:
                        Intent intent = new Intent(MainActivity.this,login2Activity.class);
                        startActivityForResult(intent,1);
                        break;
                    case R.id.grouptwo:
                        Intent intent2 = new Intent(MainActivity.this,About_.class);
                        startActivityForResult(intent2,1);
                        break;
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent2 = new Intent(MainActivity.this, seaech.class);
                String str=query;
                intent2.putExtra("aa",str);
                startActivity(intent2);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        my_tab=(TabLayout) findViewById(R.id.my_tab);
        my_viewpager=(ViewPager) findViewById(R.id.my_viewpager);
        fragments=new ArrayList<>();       //碎片的集合
        fragments.add(new Frgment1());
        fragments.add(new Frgment2());
        fragments.add(new Frgment3());
        fragments.add(new Frgment4());
        fragments.add(new Frgment5());
        fragments.add(new Frgment6());

        titles=new ArrayList<>();     //标题的集合
        titles.add("要闻");
        titles.add("财经");
        titles.add("NBA");
        titles.add("科技");
        titles.add("体育");
        titles.add("军事");

        MyAdapter adapter=new MyAdapter(getSupportFragmentManager());
        my_viewpager.setAdapter(adapter);
        my_tab.setupWithViewPager(my_viewpager);    //关联TabLayout和ViewPager

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
                drawerLayout.openDrawer(navigationView);
            }

        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivityForResult(intent,1);
            }
        });

    }

    void displayToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}
