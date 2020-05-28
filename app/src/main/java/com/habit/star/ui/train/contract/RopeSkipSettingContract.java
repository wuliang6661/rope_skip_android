package com.habit.star.ui.train.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.pojo.po.BeatsBO;
import com.habit.star.pojo.po.MusicBO;

import java.util.List;


/**
 * @version V1.0
 * @date: 2020-02-11 12:22
 * @ClassName: TrainPlanContract.java
 * @Description:
 * @author: sundongdong
 */
public interface RopeSkipSettingContract {
    interface View extends BaseView {

        void getBeats(List<BeatsBO> beatsBOS);

        void getMusics(List<MusicBO> musicBOS);
    }

    interface Presenter extends BasePresenter<View> {
    }

}