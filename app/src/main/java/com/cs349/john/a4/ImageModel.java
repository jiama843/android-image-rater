package com.cs349.john.a4;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


// Portions of this code are taken from CS349 mvc2:
public class ImageModel extends Observable {

    boolean canload = true;

    ArrayList<Bitmap> bm_arr = new ArrayList<>();
    ArrayList<Integer> ratings = new ArrayList<>();

    // Keep track of filtered arrays (will be populated with bm_arr and holds references to values that need to be changed)
    // Always filter ratings first
    ArrayList<Bitmap> filtered_bm_arr = new ArrayList<>();
    ArrayList<Integer> filtered_ratings = new ArrayList<>();

    int rating_threshold = 0;

    // Taken from CS349 mvc2 example:
    private static final ImageModel ourInstance = new ImageModel();
    static ImageModel getInstance() {
        return ourInstance;
    }

    public ImageModel(){
        loadItems();
        set_filtered_rating_arr();
        set_filtered_bm_arr();
        notifyObservers();
    }


    public void set_filtered_bm_arr(){
        filtered_bm_arr.clear();

        for(int i = 0; i < ratings.size(); i++){
            if(ratings.get(i) >= rating_threshold){
                filtered_bm_arr.add(bm_arr.get(i));
            }
        }
    }

    public void set_filtered_rating_arr() {
        filtered_ratings.clear();

        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i) >= rating_threshold) {
                filtered_ratings.add(ratings.get(i));
            }
        }
    }

    public ArrayList<Bitmap> get_filtered_bm_arr(){
        return filtered_bm_arr;
    }

    public ArrayList<Integer> get_filtered_rating_arr(){
        return filtered_ratings;
    }

    public void set_rating_arr(int id, int rating){
        for(int i = 0; i < bm_arr.size(); i++){
            if(bm_arr.get(i).equals(filtered_bm_arr.get(id))){
                ratings.set(i, rating);
            }
        }

        filtered_ratings.set(id, rating);
    }

    public void setRatingThreshold(int rating){
        rating_threshold = rating;
    }

    public void clearItems(){
        bm_arr.clear();
        ratings.clear();
        filtered_ratings.clear();
        filtered_bm_arr.clear();
    }

    public void loadItems(){

        if(!canload){ return; }
        canload = false;

        bm_arr.clear();
        ratings.clear();
        filtered_ratings.clear();
        filtered_bm_arr.clear();

        // Operating under the promise of 10 images
        URL[] urls= new URL[10];

        try {
            urls[0] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/bunny.jpg");
            urls[1] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/chinchilla.jpg");
            urls[2] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/doggo.jpg");
            urls[3] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/fox.jpg");
            urls[4] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/hamster.jpg");
            urls[5] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/husky.jpg");
            urls[6] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/kitten.png");
            urls[7] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/loris.jpg");
            urls[8] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/puppy.jpg");
            urls[9] = new URL("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/sleepy.png");

            for(int i = 0; i < bm_arr.size(); i++){
                ratings.add(0);
            }
        }
        catch(Exception exc){
            System.out.println(exc);
        }

        new DownloadImageTask().execute(urls);
    }

    // Taken from CS349 mvc2 example:
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    public void initObservers() {
        setChanged();
        notifyObservers();
    }

    @Override
    public void notifyObservers() {

        System.out.println("Notify activities");
        setChanged();
        super.notifyObservers();
    }

    private class DownloadImageTask extends AsyncTask<URL, Void, ArrayList<Bitmap>> {

        // Partially copied from the documentation on Async task
        @Override
        protected ArrayList<Bitmap> doInBackground(URL ... urls) {
            ArrayList<Bitmap> bm_arr = new ArrayList<>();

            for(int i = 0; i < urls.length; i++) {
                try {
                    bm_arr.add(BitmapFactory.decodeStream(urls[i].openConnection().getInputStream()));
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }

            for(int i = 0; i < bm_arr.size(); i++){
                ratings.add(0);
            }

            return bm_arr;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> b) {
            bm_arr = b;

            set_filtered_rating_arr();
            set_filtered_bm_arr();

            canload = true;
            System.out.println("Download finished");
            notifyObservers();
        }
    }
}
