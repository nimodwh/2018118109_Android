package com.example.wxd.a19mytest1;

public class UserEvent {
    private String name;
    private String password;
    private int code;
    private int code1;
    public UserEvent(){}
    public UserEvent(String name,int code,int code1){
        this.name=name;
        this.code=code;
        this.code1=code1;
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public int getCode(){return code;}
    public void setCode(int code){this.code=code;}
    public int getCode1(){return code1;}
    public void setCode1(int code1){this.code1=code1;}

    public String toString(){
        return "UserEvent{"+"name='"+name+'\''+",password='"+password+'\''+'}';
    }
}
