package com.tohabit.skip.ui.train.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.BeatsBO;
import com.tohabit.skip.pojo.po.MusicBO;

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