package com.example.springbootdemo.schemas;

import java.util.ArrayList;

public record Category(int id, String name, ArrayList<Item> items) {
    public ArrayList<Item> getItems() {
        return items;
    }
}
