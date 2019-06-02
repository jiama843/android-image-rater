package com.cs349.john.a4;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    RecyclerView imagegroup;
    ImageModelAdapter im_adapt;
    ImageModel im;

    ImageView load;
    ImageView clear;

    RatingBar filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = ImageModel.getInstance();
        im.addObserver(this);

        load = (ImageView) findViewById(R.id.load_button);
        clear = (ImageView) findViewById(R.id.clear_button);

        filter = (RatingBar) findViewById(R.id.filter);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im.loadItems();
                im_adapt.notifyDataSetChanged();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im.clearItems();
                im_adapt.notifyDataSetChanged();
            }
        });

        filter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                im.setRatingThreshold((int) rating);
                im.set_filtered_rating_arr();
                im.set_filtered_bm_arr();
                im_adapt.notifyDataSetChanged();
            }
        });

        Toolbar fotag_toolbar = (Toolbar) findViewById(R.id.fotag_toolbar);
        setSupportActionBar(fotag_toolbar);
        getSupportActionBar().setTitle("");
        //fotag_toolbar.setTitleTextColor(Color.WHITE);

        ArrayList<Bitmap> currbm = im.get_filtered_bm_arr();

        im.initObservers();
        im_adapt = new ImageModelAdapter(im, this);
        im_adapt.notifyDataSetChanged();
        System.out.println("im_adapt created");

        imagegroup = findViewById(R.id.image_col);
        imagegroup.setAdapter(im_adapt);
        imagegroup.post(new Runnable()
        {
            @Override
            public void run() {
                im_adapt.notifyDataSetChanged();
            }
        });
        //imagegroup.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("Update Called");

        if(im_adapt != null) {
            im_adapt.notifyDataSetChanged();
            System.out.println(im_adapt.getItemCount());
        }
    }
}
