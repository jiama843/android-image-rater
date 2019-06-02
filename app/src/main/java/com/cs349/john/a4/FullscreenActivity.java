package com.cs349.john.a4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class FullscreenActivity extends AppCompatActivity {

    ImageView full_img;
    ImageModel im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);

        im = ImageModel.getInstance();

        full_img = (ImageView) findViewById(R.id.img_fullscreen);
        //System.out.println(getIntent().getIntExtra("image", 0));
        //System.out.println(im.get_filtered_bm_arr());
        full_img.setImageBitmap(im.get_filtered_bm_arr().get(getIntent().getIntExtra("image", 0)));

        full_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
