package com.example.rxobservable;

public class Boat {
    private int length;
    private String brand;

    public Boat(int length, String brand) {
        this.length = length;
        this.brand = brand;
    }

    public int getLength() {
        return length;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "length=" + length +
                ", brand='" + brand + '\'' +
                '}';
    }
}
