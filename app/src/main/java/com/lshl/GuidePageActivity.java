package com.lshl;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lshl.utils.SharedPreferencesUtils;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * 引导页Activity
 */
public class GuidePageActivity extends AppCompatActivity {

    private static final boolean DEBUG = false;
    private static final String SHARE_TAG = "guide_page_load";

    private ImageView aImage;
    private ImageView bImage;
    private ImageView cImage;
    private ImageView dImage;
    private ImageView eImage;

    private int mWindowHeight;

    private ValueAnimator mAnimatorMoveYInto;
    private ValueAnimator mAnimatorMoveYOutOf;
    private ObjectAnimator mAnimatorAlphaInto;
    private ObjectAnimator mAnimatorAlphaOutOf;

    private ObjectAnimator mAnimatorUpstep;

    private boolean isBImageOver = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide_page);

        if (DEBUG) {
            findViewById(R.id.btn_start).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_content).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_end).setVisibility(View.VISIBLE);
        } else {
            boolean isLoadOver = (boolean) SharedPreferencesUtils.getParam(this, SHARE_TAG, false);
            if (isLoadOver) {
                onClickGo(null);
                return;
            }
            SharedPreferencesUtils.setParam(this, SHARE_TAG, true);
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        mWindowHeight = metrics.heightPixels;

        aImage = (ImageView) findViewById(R.id.iv_a);
        bImage = (ImageView) findViewById(R.id.iv_b);
        cImage = (ImageView) findViewById(R.id.iv_c);
        dImage = (ImageView) findViewById(R.id.iv_d);
        eImage = (ImageView) findViewById(R.id.iv_e);

        animatorAlphaInto(aImage);
        animatorMoveYInto(aImage);

        mAnimatorMoveYInto.start();
        mAnimatorAlphaInto.start();

    }

    public void onClickGo(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickStart(View view) {

        aImage.setVisibility(View.VISIBLE);
        bImage.setVisibility(View.GONE);
        cImage.setVisibility(View.GONE);
        dImage.setVisibility(View.GONE);
        eImage.setVisibility(View.GONE);

        animatorAlphaInto(aImage);
        animatorMoveYInto(aImage);

        isBImageOver = false;
        mAnimatorMoveYInto.start();
        mAnimatorAlphaInto.start();
    }

    public void onClickLast(View view) {
        animatorUpstep(cImage);
        mAnimatorUpstep.start();
        dImage.setVisibility(View.GONE);
        eImage.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        rotateyAnimRun();
    }

    private void rotateyAnimRun() {

       /* dImage.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(dImage, "rotationY", 90.0F, -34.0F, 26.0F, -18.0F, 9.0F, 0.0F)//
                .setDuration(1200)//
                .start();*/

        eImage.setVisibility(View.VISIBLE);
        ObjectAnimator//
                .ofFloat(eImage, "rotationY", 90.0F, -34.0F, 26.0F, -18.0F, 9.0F, 0.0F)//
                .setDuration(1200)//
                .start();
    }

    private void animatorMoveYOutOf(final View view) {

        mAnimatorMoveYOutOf = ValueAnimator.ofFloat(mWindowHeight / 2 - 210, mWindowHeight / 2 - 420);
        mAnimatorMoveYOutOf.setTarget(view);
        mAnimatorMoveYOutOf.setDuration(2500);

        mAnimatorMoveYOutOf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                Log.d("animation", "value = " + cVal);
                view.setY(cVal);
            }
        });


        mAnimatorMoveYOutOf.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isBImageOver = true;
                if (!isBImageOver) {
                    aImage.setVisibility(View.GONE);
                    bImage.setVisibility(View.VISIBLE);
                    animatorAlphaInto(bImage);
                    animatorMoveYInto(bImage);
                    mAnimatorAlphaInto.start();
                    mAnimatorMoveYInto.start();
                    isBImageOver = true;
                } else {
                    cImage.setVisibility(View.VISIBLE);
                    animatorUpstep(cImage);
                    mAnimatorUpstep.start();

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void animatorAlphaOutOf(final View view) {
        mAnimatorAlphaOutOf = //
                ofFloat(view, "zhy", 1.0F, 0.0F)//
                        .setDuration(2500);//
        mAnimatorAlphaOutOf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                //Log.guide_page_d("animation", "value = " + cVal);
                view.setAlpha(cVal);
            }
        });
    }

    private void animatorMoveYInto(final View view) {

        mAnimatorMoveYInto = ValueAnimator.ofFloat(mWindowHeight / 2, mWindowHeight / 2 - 210);
        mAnimatorMoveYInto.setTarget(view);
        mAnimatorMoveYInto.setDuration(2500);

        mAnimatorMoveYInto.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                Log.d("animation", "value = " + cVal);
                view.setY(cVal);
            }
        });

        mAnimatorMoveYInto.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorAlphaOutOf(view);
                animatorMoveYOutOf(view);
                mAnimatorMoveYOutOf.start();
                mAnimatorAlphaOutOf.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void animatorAlphaInto(final View view) {
        mAnimatorAlphaInto = //
                ofFloat(view, "zhy", 0.0F, 1.0F)//
                        .setDuration(2500);//

        mAnimatorAlphaInto.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                //Log.guide_page_d("animation", "value = " + cVal);
                view.setAlpha(cVal);
            }
        });
    }

    private void animatorUpstep(final View view) {
        mAnimatorUpstep = //
                ofFloat(view, "zhy", 0.3F, 1.0F)//
                        .setDuration(900);//

        mAnimatorUpstep.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                //Log.guide_page_d("animation", "value = " + cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
        mAnimatorUpstep.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rotateyAnimRun();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
