package com.matrixxun.producttour;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;


public class ProductTourActivity extends AppCompatActivity {
 
    static final int NUM_PAGES = 5;
 
    ViewPager pager;
    PagerAdapter pagerAdapter;
    LinearLayout circles;
    Button skip;
    Button done;
    ImageButton next;
    boolean isOpaque = true;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
 
        setContentView(R.layout.activity_tutorial);
        skip = Button.class.cast(findViewById(R.id.skip));
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTutorial();
            }
        });

        next = ImageButton.class.cast(findViewById(R.id.next));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });
 
        done = Button.class.cast(findViewById(R.id.done));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTutorial();
            }
        });
 
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == NUM_PAGES - 2) {
                    skip.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                } else if (position < NUM_PAGES - 2) {
                    skip.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                } else if (position == NUM_PAGES - 1) {
                    endTutorial();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
 
        buildCircles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pager!=null){
            pager.clearOnPageChangeListeners();
        }
    }

    private void buildCircles(){
        circles = LinearLayout.class.cast(findViewById(R.id.circles));
 
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
 
        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.ic_swipe_indicator_white_18dp);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }
 
        setIndicator(0);
    }
 
    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                	circle.setColorFilter(getResources().getColor(R.color.text_selected));
                }else {
                	circle.setColorFilter(getResources().getColor(android.R.color.transparent));
                }
            }
        }
    }
 
    private void endTutorial(){
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
 
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
 
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
 
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public Fragment getItem(int position) {
            ProductTourFragment tp = null;
            switch(position){
                case 0:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment1);
                    break;
                case 1:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment2);
                    break;
                case 2:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment3);
                    break;
                case 3:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment4);
                    break;
                case 4:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment5);
                    break;
            }
 
            return tp;
        }
 
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

	public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
 
            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head= page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View object1 = page.findViewById(R.id.a000);
            View object2 = page.findViewById(R.id.a001);
 
            View object3 = page.findViewById(R.id.a002);
            View object4 = page.findViewById(R.id.a003);
            View object5 = page.findViewById(R.id.a004);
            View object6 = page.findViewById(R.id.a005);
            View object7 = page.findViewById(R.id.a006);
            View object8 = page.findViewById(R.id.a008);
            View object9 = page.findViewById(R.id.a010);
            View object10 = page.findViewById(R.id.a011);
            View object11 = page.findViewById(R.id.a007);
            View object12 = page.findViewById(R.id.a012);
            View object13 = page.findViewById(R.id.a013);

            if(0 <= position && position < 1){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }
            if(-1 < position && position < 0){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }
 
            if(position <= -1.0f || position >= 1.0f) {
            } else if( position == 0.0f ) {
            } else {
                if(backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView,1.0f - Math.abs(position));

                }
 
                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head,pageWidth * position);
                    ViewHelper.setAlpha(text_head,1.0f - Math.abs(position));
                }
                
                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content,pageWidth * position);
                    ViewHelper.setAlpha(text_content,1.0f - Math.abs(position));
                }
                
                if (object1 != null) {
                    ViewHelper.setTranslationX(object1,pageWidth * position);
                }

                // parallax effect
                if(object2 != null){
                    ViewHelper.setTranslationX(object2,pageWidth * position);
                }
                
                if(object4 != null){
                    ViewHelper.setTranslationX(object4,pageWidth/2 * position);
                }
                if(object5 != null){
                    ViewHelper.setTranslationX(object5,pageWidth/2 * position);
                }
                if(object6 != null){
                    ViewHelper.setTranslationX(object6,pageWidth/2 * position);
                }
                if(object7 != null){
                    ViewHelper.setTranslationX(object7,pageWidth/2 * position);
                }

                if(object8 != null){
                    ViewHelper.setTranslationX(object8,(float)(pageWidth/1.5 * position));
                }

                if(object9 != null){
                    ViewHelper.setTranslationX(object9,(float)(pageWidth/2 * position));
                }

                if(object10 != null){
                    ViewHelper.setTranslationX(object10,pageWidth/2 * position);
                }

                if(object11 != null){
                    ViewHelper.setTranslationX(object11,(float)(pageWidth/1.2 * position));
                }

                if(object12 != null){
                    ViewHelper.setTranslationX(object12,(float)(pageWidth/1.3 * position));
                }

                if(object13 != null){
                    ViewHelper.setTranslationX(object13,(float)(pageWidth/1.8 * position));
                }

                if(object3 != null){
                    ViewHelper.setTranslationX(object3,(float)(pageWidth/1.2 * position));
                }
            }
        }
    }
}