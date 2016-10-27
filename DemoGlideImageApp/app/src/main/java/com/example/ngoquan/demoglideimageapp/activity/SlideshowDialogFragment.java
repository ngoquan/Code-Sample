package com.example.ngoquan.demoglideimageapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ngoquan.demoglideimageapp.R;
import com.example.ngoquan.demoglideimageapp.model.Image;

import java.util.ArrayList;

/**
 * Created by NGOQUAN on 10/27/2016.
 */
public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Image> images;
    private ViewPager viewPager;

    private MyViewPagerAdapter myViewPagerAdpter;
    private TextView tvCount, tvTitle, tvDate;
    private int selectedPosition = 0;

    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tvCount = (TextView) view.findViewById(R.id.lbl_count);
        tvTitle = (TextView) view.findViewById(R.id.title);
        tvDate = (TextView) view.findViewById(R.id.date);

        images = (ArrayList<Image>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.d(TAG, "position: " + selectedPosition);
        Log.d(TAG, "images size: " + images.size());
        myViewPagerAdpter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdpter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);

        return view;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(position);
    }

    //    page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void displayMetaInfo(int position) {
        tvCount.setText((position + 1) + " of " + images.size());
        Image image = images.get(position);
        tvTitle.setText(image.getName());
        tvDate.setText(image.getTimestamp());
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            ImageView imgPreview = (ImageView) view.findViewById(R.id.img_preview);
            Image image = images.get(position);
            Glide.with(getActivity()).load(image.getLarge())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .crossFade()
                    .into(imgPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
