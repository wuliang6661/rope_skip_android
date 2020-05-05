package com.habit.star.anim;

import android.os.Parcel;
import android.os.Parcelable;

import com.habit.star.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by YoKeyword on 16/2/5.
 */
public class FadeAnimator extends FragmentAnimator implements Parcelable {

    public FadeAnimator() {
        enter = R.anim.fade_in;
        exit = R.anim.fade_out;
        popEnter = R.anim.fade_in;
        popExit = R.anim.fade_out;
    }

    protected FadeAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FadeAnimator> CREATOR = new Creator<FadeAnimator>() {
        @Override
        public FadeAnimator createFromParcel(Parcel in) {
            return new FadeAnimator(in);
        }

        @Override
        public FadeAnimator[] newArray(int size) {
            return new FadeAnimator[size];
        }
    };
}
