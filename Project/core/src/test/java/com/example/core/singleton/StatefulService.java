package com.example.core.singleton;

public class StatefulService {

//    private int price; // 상태를 유지하는 필드 -> 무상태를 해침

//    public void order(String name, int price) {
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
