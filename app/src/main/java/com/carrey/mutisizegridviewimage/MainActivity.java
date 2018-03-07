package com.carrey.mutisizegridviewimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.ll_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        mRecyclerView.setAdapter(new MuliSizeImageAdapter(getData()));

    }

    private List<ImageBean> getData() {
        List<ImageBean> imageBeans =new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            imageBeans.add(new ImageBean());
        }
        return imageBeans;
    }
}
