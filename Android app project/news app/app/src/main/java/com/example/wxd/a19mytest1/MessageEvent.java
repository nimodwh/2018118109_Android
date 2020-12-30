package com.example.wxd.a19mytest1;

public class MessageEvent {
    private int i;

    public String getName() {
        return name;
    }

    private String name;
    public MessageEvent(int i){
        this.i=i;
        // this.name=name;

    }
    public int getI(){
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }


    public MessageEvent(int i,String name){
        this.i=i;
        this.name=name;
        // this.name=name;

    }

}

