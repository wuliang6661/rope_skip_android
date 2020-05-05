package com.habit.star.anim;

import android.animation.Animator;
import android.animation.IntArrayEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TranslateTransition extends Transition {
    private static final String PROPNAME_TRANSLATE =
            "com.habit.adrkf.anim.TranslateTransition";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int[] location = new int[2];
        view.getLocationInWindow(location);
        transitionValues.values.put(PROPNAME_TRANSLATE, location);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        final View view = startValues.view;
        final int[] startLoc = (int[]) startValues.values.get(PROPNAME_TRANSLATE);
        final int[] endLoc = (int[]) endValues.values.get(PROPNAME_TRANSLATE);
        final IntArrayEvaluator evaluator = new IntArrayEvaluator();
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int[] loc = evaluator.evaluate(animation.getAnimatedFraction(), startLoc, endLoc);
                view.setX((float) loc[0]);
                view.setY((float) loc[1]);
            }
        });

        return animator;
    }
}