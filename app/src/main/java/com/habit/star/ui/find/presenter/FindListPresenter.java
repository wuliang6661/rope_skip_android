package com.habit.star.ui.find.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.find.bean.FindModel;
import com.habit.star.ui.find.contract.FindListContract;
import com.habit.star.ui.train.bean.ImprovePlanModel;
import com.habit.star.ui.train.contract.TrainPlanListContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * @version V1.0
 * @date: 2020-02-26 23:19
 * @ClassName: FindListPresenter.java
 * @Description:
 * @author: sundongdong
 */
public class FindListPresenter extends RxPresenter<FindListContract.View> implements FindListContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public FindListPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getList(String type) {
//        mView.showProgress();
//        addSubscrebe(mRetrofitHelper.getRecodList().subscribe(new Action1<UserInfoMode>() {
//            @Override
//            public void call(UserInfoMode userInfoMode) {
//                mView.hideProgress();
//                mView.getUserInfo(userInfoMode);
//            }
//        }, new ActionError(App.getStringResource(R.string.getUserInfoError)) {
//            @Override
//            public void onError(String msg, int code) {
//                mView.hideProgress();
//                mView.showError(msg);
//            }
//        }));


        ///添加测试数据
        List<FindModel> testData = new ArrayList<>();
        FindModel model1 = new FindModel();
        model1.title = "大国风采  开启体育强身健体新时代之少年组跳绳比赛";
        model1.startTime = "2019-10-21";
        model1.endTime = "2019-12-01";
        model1.img = "https://b-ssl.duitang.com/uploads/item/201707/20/20170720222547_kHazm.thumb.700_0.jpeg";
        testData.add(model1);
        FindModel model2 = new FindModel();
        model2.title = "运动小将  浙江省少儿杯跑步竞技大赛";
        model2.startTime = "2019-10-21";
        model2.endTime = "2019-11-04";
        model2.img = "http://img.jk51.com/img_jk51/376945853.jpeg";
        testData.add(model2);
        FindModel model3 = new FindModel();
        model3.title = "国际儿童运动会  3个国家5个城市少儿大型拉力运动赛";
        model3.startTime = "2019-10-21";
        model3.endTime = "2019-12-11";
        model3.img = "http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20190420/96ef90a4ab4942c0b9445486e835a8cd.jpeg";
        testData.add(model3);
        FindModel model4 = new FindModel();
        model4.title = "杭州运动杯  人小志气大比赛顶呱呱 你行我行争取战胜他";
        model4.startTime = "2019-10-21";
        model4.endTime = "2019-11-21";
        model4.img = "https://ss2.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3201219442,1409891636&fm=26&gp=0.jpg";
        testData.add(model4);
        FindModel model5 = new FindModel();
        model5.title = "杭州运动杯  人小志气大比赛顶呱呱 你行我行争取战胜他";
        model5.startTime = "2019-10-21";
        model5.endTime = "2019-11-21";
        model5.img = "https://ss0.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2655434241,3112091437&fm=26&gp=0.jpg";
        testData.add(model5);
        FindModel model6 = new FindModel();
        model6.title = "杭州运动杯  人小志气大比赛顶呱呱 你行我行争取战胜他";
        model6.startTime = "2019-10-21";
        model6.endTime = "2019-11-21";
        model6.img = "https://ss0.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2655434241,3112091437&fm=26&gp=0.jpg";
        testData.add(model6);
        mView.setList(testData);
    }
}