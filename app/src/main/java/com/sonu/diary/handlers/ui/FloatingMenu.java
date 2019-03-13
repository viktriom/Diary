package com.sonu.diary.handlers.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.sonu.diary.R;
import com.sonu.diary.util.cartesian.CartesianCoordinate;
import com.sonu.diary.util.cartesian.CircularPlottingSystem;

import java.util.LinkedList;
import java.util.List;

public class FloatingMenu extends View {

    private Context context;
    private FloatingActionButton btnMenuHandler;
    private List<FloatingActionButton> menuOptions;
    private boolean floatingMenuShown = false;
    private CartesianCoordinate menuPosition;
    private CircularPlottingSystem circularPlottingSystem;
    private FloatingMenuHandler menuHandler;
    private List<String> menuOptionTags;
    private FrameLayout floatingMenuLayout;

    public FloatingMenu(Context context, int optionCount, CartesianCoordinate menuPosition, AttributeSet attributeSet, FloatingMenuHandler menuHandler) {
        super(context, attributeSet);
        menuOptionTags = new LinkedList<>();
        for(int i = 1;i == optionCount; i ++){
            menuOptionTags.add("MenuOption" + String.valueOf(i));
        }
        this.context = context;
        this.menuHandler = menuHandler;
        this.menuPosition = menuPosition;
        init();
    }

    public FloatingMenu(Context context, List<String> menuOptionTags, CartesianCoordinate menuPosition, AttributeSet attributeSet, FloatingMenuHandler menuHandler) {
        super(context, attributeSet);
        this.menuOptionTags = menuOptionTags;
        this.context = context;
        this.menuHandler = menuHandler;
        this.menuPosition = menuPosition;
        init();
    }

    private void init() {

        circularPlottingSystem = new CircularPlottingSystem(menuPosition,3,menuOptions.size(),90,180);
        this.floatingMenuLayout = new FrameLayout(context);
        menuOptions = new LinkedList<>();
        initMenuButtons();
    }

    private void initMenuButtons() {
        btnMenuHandler = createFloatingButton();
        btnMenuHandler.setTag("MenuController");
        btnMenuHandler.setOnClickListener(view -> toggleFloatingMenus());
        for(String tag: menuOptionTags){
            FloatingActionButton fab = createFloatingButton();
            fab.setTag(tag);
            menuOptions.add(fab);
            fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filter_none));
        }

    }

    private FloatingActionButton createFloatingButton(){
        FloatingActionButton btn = new FloatingActionButton(context);
        btn.setBackgroundColor(R.attr.colorPrimary);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM|Gravity.END);
        btn.setLayoutParams(layoutParams);
        btn.setOnClickListener(view -> menuHandler.floatingMenuTouched(view));
        return btn;
    }

    private AnimationSet createShowAnimation(float fromXDelta, float fromYDelta) {
        AnimationSet showAnimationSet = new AnimationSet(false);
        TranslateAnimation translateAnimShow = new TranslateAnimation(fromXDelta, 0,fromYDelta,0);
        translateAnimShow.setInterpolator(context, android.R.anim.linear_interpolator);
        translateAnimShow.setDuration(500);

        RotateAnimation rotateAnimShow = new RotateAnimation(30,0,50,50);
        rotateAnimShow.setInterpolator(context, android.R.anim.linear_interpolator);
        rotateAnimShow.setDuration(100);
        rotateAnimShow.setRepeatMode(2);
        rotateAnimShow.setRepeatMode(4);

        AlphaAnimation alphaAnimShow = new AlphaAnimation(0, 1);
        alphaAnimShow.setInterpolator(context, android.R.anim.decelerate_interpolator);
        alphaAnimShow.setDuration(1000);

        showAnimationSet.addAnimation(rotateAnimShow);
        showAnimationSet.addAnimation(translateAnimShow);
        showAnimationSet.addAnimation(alphaAnimShow);

        return showAnimationSet;
    }

    private AnimationSet createHideAnimationSet(float fromXDelta, float fromYDelta){
        AnimationSet hideAnimationSet =  new AnimationSet(false);
        TranslateAnimation translateAnimHide = new TranslateAnimation(fromXDelta, fromYDelta,0,0);
        translateAnimHide.setInterpolator(context, android.R.anim.linear_interpolator);
        translateAnimHide.setDuration(500);

        AlphaAnimation alphaAnimHide = new AlphaAnimation(0, 1);
        alphaAnimHide.setInterpolator(context, android.R.anim.decelerate_interpolator);
        alphaAnimHide.setDuration(1000);

        hideAnimationSet.addAnimation(translateAnimHide);
        hideAnimationSet.addAnimation(alphaAnimHide);
        return hideAnimationSet;
    }


    private void toggleFloatingMenus(){
        if(floatingMenuShown){
            for(int i =0; i< menuOptions.size(); i++){
                FloatingActionButton fab = menuOptions.get(i);
                CartesianCoordinate cc = circularPlottingSystem.getAbsNthPoint(i);
                showFloatingMenu(fab, cc.getY(), cc.getX(), createShowAnimation((int)cc.getX()*100,(int)cc.getY()*100));
            }
        } else {
            for(int i =0; i< menuOptions.size(); i++){
                FloatingActionButton fab = menuOptions.get(i);
                CartesianCoordinate cc = circularPlottingSystem.getAbsNthPoint(i);
                hideFloatingMenu(fab, cc.getY(), cc.getX(), createShowAnimation((int)cc.getX()*100,(int)cc.getY()*100));
            }
        }

    }

    private void showFloatingMenu(FloatingActionButton fab, double rightMargin, double leftMargin, Animation animation) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (fab.getHeight() * rightMargin);
        layoutParams.bottomMargin += (int) (fab.getHeight() * leftMargin);
        fab.setLayoutParams(layoutParams);
        fab.startAnimation(animation);
        fab.setClickable(true);
        floatingMenuShown = true;
    }

    private void hideFloatingMenu(FloatingActionButton fab, double rightMargin, double leftMargin, Animation animation) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab.getHeight() * rightMargin);
        layoutParams.bottomMargin -= (int) (fab.getWidth() * leftMargin);
        fab.setLayoutParams(layoutParams);
        fab.startAnimation(animation);
        fab.setClickable(false);
        floatingMenuShown = false;
    }


}
