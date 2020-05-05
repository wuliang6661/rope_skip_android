package com.habit.star.di.component;

import android.app.Activity;

import com.habit.star.common.fragment.EditContentFragment;
import com.habit.star.di.FragmentScope;
import com.habit.star.di.module.FragmentModule;
import com.habit.star.ui.common.CommonFragment;
import com.habit.star.ui.find.fragment.FindListFragment;
import com.habit.star.ui.find.fragment.FindMainFragment;
import com.habit.star.ui.login.fragment.PerfectInformationFragment;
import com.habit.star.ui.mine.fragment.FamilyMemberDetailFragment;
import com.habit.star.ui.mine.fragment.FamilyMemberFragment;
import com.habit.star.ui.login.fragment.LoginFragment;
import com.habit.star.ui.login.fragment.RegisterFragment;
import com.habit.star.ui.login.fragment.RetrievePasswordFragment;
import com.habit.star.ui.login.fragment.SplashFragment;
import com.habit.star.ui.mine.fragment.AddAddressFragment;
import com.habit.star.ui.mine.fragment.FeedbackFragment;
import com.habit.star.ui.mine.fragment.HelpCenterFragment;
import com.habit.star.ui.mine.fragment.MessageListFragment;
import com.habit.star.ui.mine.fragment.MineFragment;
import com.habit.star.ui.mine.fragment.ModifyNickNameFragment;
import com.habit.star.ui.mine.fragment.ModifyTelephoneFragment;
import com.habit.star.ui.mine.fragment.MyAchievementFragment;
import com.habit.star.ui.mine.fragment.MyAddressListFragment;
import com.habit.star.ui.mine.fragment.MyHonorCertificateFragment;
import com.habit.star.ui.mine.fragment.MyMedalAchievementFragment;
import com.habit.star.ui.mine.fragment.MyPkFragment;
import com.habit.star.ui.mine.fragment.PersonalDataFragment;
import com.habit.star.ui.train.fragment.RopeSkipResultFragment;
import com.habit.star.ui.mine.fragment.SystemSettingFragment;
import com.habit.star.ui.train.fragment.BaseMsgInputFragment;
import com.habit.star.ui.train.fragment.EnergyDetailFragment;
import com.habit.star.ui.train.fragment.EnergyValueFragment;
import com.habit.star.ui.train.fragment.RopeSkipSettingFragment;
import com.habit.star.ui.train.fragment.TestResultFragment;
import com.habit.star.ui.train.fragment.TestResultShareSuccessFragment;
import com.habit.star.ui.train.fragment.TrainPlanFragment;
import com.habit.star.ui.train.fragment.TrainPlanListFragment;
import com.habit.star.ui.train.fragment.TrainingPlanMainFragment;
import com.habit.star.ui.train.fragment.TranHomeFragment;
import com.habit.star.ui.young.fragment.YoungHomeFragment;

import dagger.Component;

/*
 * 创建日期：2019-07-29 15:54
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：FragmentComponent.java
 * 类说明：
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();



    void inject(CommonFragment fragment);
    void inject(RegisterFragment fragment);
    void inject(LoginFragment fragment);
    void inject(SplashFragment fragment);
    void inject(MineFragment fragment);
    void inject(EditContentFragment fragment);
    void inject(RetrievePasswordFragment fragment);
    void inject(HelpCenterFragment fragment);
    void inject(FeedbackFragment fragment);
    void inject(SystemSettingFragment fragment);
    void inject(AddAddressFragment fragment);
    void inject(FamilyMemberFragment fragment);
    void inject(FamilyMemberDetailFragment fragment);
    void inject(PerfectInformationFragment fragment);
    void inject(MyAchievementFragment fragment);
    void inject(MyMedalAchievementFragment fragment);
    void inject(MyHonorCertificateFragment fragment);
    void inject(MyPkFragment fragment);
    void inject(MyAddressListFragment fragment);
    void inject(TranHomeFragment fragment);
    void inject(BaseMsgInputFragment fragment);
    void inject(TrainPlanFragment fragment);
    void inject(TestResultFragment fragment);
    void inject(RopeSkipSettingFragment fragment);
    void inject(TrainingPlanMainFragment fragment);
    void inject(TrainPlanListFragment fragment);
    void inject(EnergyValueFragment fragment);
    void inject(EnergyDetailFragment fragment);
    void inject(RopeSkipResultFragment fragment);
    void inject(YoungHomeFragment fragment);
    void inject(TestResultShareSuccessFragment fragment);
    void inject(FindMainFragment fragment);
    void inject(FindListFragment fragment);
    void inject(PersonalDataFragment fragment);
    void inject(MessageListFragment fragment);
    void inject(ModifyTelephoneFragment fragment);
    void inject(ModifyNickNameFragment fragment);

}
