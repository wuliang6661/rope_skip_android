package com.tohabit.skip.di.component;

import android.app.Activity;

import com.tohabit.skip.common.fragment.EditContentFragment;
import com.tohabit.skip.di.FragmentScope;
import com.tohabit.skip.di.module.FragmentModule;
import com.tohabit.skip.ui.common.CommonFragment;
import com.tohabit.skip.ui.find.fragment.FindListFragment;
import com.tohabit.skip.ui.find.fragment.FindMainFragment;
import com.tohabit.skip.ui.login.fragment.PerfectInformationFragment;
import com.tohabit.skip.ui.mine.fragment.FamilyMemberDetailFragment;
import com.tohabit.skip.ui.mine.fragment.FamilyMemberFragment;
import com.tohabit.skip.ui.login.fragment.LoginFragment;
import com.tohabit.skip.ui.login.fragment.RegisterFragment;
import com.tohabit.skip.ui.login.fragment.RetrievePasswordFragment;
import com.tohabit.skip.ui.login.fragment.SplashFragment;
import com.tohabit.skip.ui.mine.fragment.AddAddressFragment;
import com.tohabit.skip.ui.mine.fragment.FeedbackFragment;
import com.tohabit.skip.ui.mine.fragment.HelpCenterFragment;
import com.tohabit.skip.ui.mine.fragment.MessageListFragment;
import com.tohabit.skip.ui.mine.fragment.MineFragment;
import com.tohabit.skip.ui.mine.fragment.ModifyNickNameFragment;
import com.tohabit.skip.ui.mine.fragment.ModifyTelephoneFragment;
import com.tohabit.skip.ui.mine.fragment.MyAchievementFragment;
import com.tohabit.skip.ui.mine.fragment.MyHonorCertificateFragment;
import com.tohabit.skip.ui.mine.fragment.MyMedalAchievementFragment;
import com.tohabit.skip.ui.mine.fragment.MyPkFragment;
import com.tohabit.skip.ui.mine.fragment.PersonalDataFragment;
import com.tohabit.skip.ui.train.fragment.RopeSkipResultFragment;
import com.tohabit.skip.ui.mine.fragment.SystemSettingFragment;
import com.tohabit.skip.ui.train.fragment.BaseMsgInputFragment;
import com.tohabit.skip.ui.train.fragment.EnergyDetailFragment;
import com.tohabit.skip.ui.train.fragment.EnergyValueFragment;
import com.tohabit.skip.ui.train.fragment.RopeSkipSettingFragment;
import com.tohabit.skip.ui.train.fragment.TestResultFragment;
import com.tohabit.skip.ui.train.fragment.TestResultShareSuccessFragment;
import com.tohabit.skip.ui.train.fragment.TrainPlanFragment;
import com.tohabit.skip.ui.train.fragment.TrainPlanListFragment;
import com.tohabit.skip.ui.train.fragment.TrainingPlanMainFragment;
import com.tohabit.skip.ui.train.fragment.TranHomeFragment;
import com.tohabit.skip.ui.young.fragment.YoungHomeFragment;

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
