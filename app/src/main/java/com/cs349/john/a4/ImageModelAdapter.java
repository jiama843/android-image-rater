package com.cs349.john.a4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.ArrayList;

// Tutorial followed: https://guides.codepath.com/android/using-the-recyclerview
public class ImageModelAdapter extends RecyclerView.Adapter<ImageModelAdapter.ViewHolder> {

    private boolean onBind = false;

    Context context;
    ImageModel im;

    public ImageModelAdapter(ImageModel i, Context c) {
        im = i;
        context = c;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public LinearLayout cardViewLayout;
        public ImageView cardImage;
        public RatingBar cardRating;
        public ViewHolder(View v) {
            super(v);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardViewLayout = (LinearLayout) itemView.findViewById(R.id.card_view_layout);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
            cardRating = (RatingBar) itemView.findViewById(R.id.card_rating);

            cardView.setContentPadding(15, 15, 15, 15);

            cardImage.setAdjustViewBounds(true);
            cardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullscreenActivity.class);

                    // Send the bitmap position
                    intent.putExtra("image", getAdapterPosition());
                    context.startActivity(intent);
                }
            });

            cardRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(onBind){ return; }

                    System.out.println("Adapter (image) position: "+getAdapterPosition());
                    im.set_rating_arr(getAdapterPosition(), (int) rating);
                    im.set_filtered_rating_arr();
                    im.set_filtered_bm_arr();
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public ImageModelAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        System.out.println("Creating ViewHolder");
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ImageModelAdapter.ViewHolder viewHolder, int i) {

        ArrayList<Bitmap> bitmaps = im.get_filtered_bm_arr();
        ArrayList<Integer> ratings = im.get_filtered_rating_arr();

        /*System.out.println("Populating data");
        System.out.println(i);
        System.out.println(bitmaps.get(i));*/

        ImageView iv = viewHolder.cardImage;
        iv.setImageBitmap(bitmaps.get(i));

        RatingBar rb = viewHolder.cardRating;
        onBind = true;
        rb.setRating((float) ratings.get(i));
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return im.get_filtered_bm_arr().size();
    }
}