package com.carrey.mutisizegridviewimage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pccai on 2018/3/7.
 */

public class ImageBean {

    public String name;

    public List<Integer> images;


    public ImageBean() {
        Random random = new Random();
        this.images = new ArrayList<>();
        for (int i = 0; i < random.nextInt(9); i++) {
            images.add(R.mipmap.ic_launcher);
        }
        if (images != null) {
            name = images.size() + "";
        } else {
            name = "0";
        }
    }
}
