package com.habit.star.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.BeatsBO;
import com.habit.star.pojo.po.MusicBO;
import com.habit.star.ui.train.contract.RopeSkipSettingContract;
import com.habit.star.ui.train.presenter.RoseSkipSettingPresenter;
import com.habit.star.utils.ToastUtil;
import com.habit.star.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @version V1.0
 * @date: 2020-02-12 10:45
 * @ClassName: RopeSkipSettingFragment.java
 * @Description:跳绳配置界面
 * @author: sundongdong
 */
public class RopeSkipSettingFragment extends BaseFragment<RoseSkipSettingPresenter> implements RopeSkipSettingContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.tv_bg_music_name_fragment_rope_skip_setting)
    AppCompatTextView tvBgMusicName;
    @BindView(R.id.ll_bg_music_fragment_rope_skip_setting)
    LinearLayout llBgMusic;
    @BindView(R.id.tv_jz_name_fragment_rope_skip_setting)
    AppCompatTextView tvJzName;
    @BindView(R.id.ll_jz_fragment_rope_skip_setting)
    LinearLayout llJz;
    @BindView(R.id.btn_save_fragment_rope_skip_setting)
    AppCompatButton btnSave;


    private List<BeatsBO> beatsBOS;
    private List<MusicBO> musicBOS;

    private int selectBeat = 0;
    private int selectMusic = 0;

    public static RopeSkipSettingFragment newInstance(Bundle bundle) {
        RopeSkipSettingFragment fragment = new RopeSkipSettingFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rope_skip_setting;
    }

    @Override
    protected String getLogTag() {
        return "RopeSkipSettingFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });

        mPresenter.getBeats();
        mPresenter.getMusicList();
    }


    private void initDialog() {

    }

    @Override
    public void showProgress() {

        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    @SingleClick
    @OnClick({R.id.ll_bg_music_fragment_rope_skip_setting,
            R.id.ll_jz_fragment_rope_skip_setting,
            R.id.btn_save_fragment_rope_skip_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_bg_music_fragment_rope_skip_setting:
                showMusic();
                break;
            case R.id.ll_jz_fragment_rope_skip_setting:
                showBeats();
                break;
            case R.id.btn_save_fragment_rope_skip_setting:
                TrainPlanFragment.musicBO = musicBOS.get(selectMusic);
                TrainPlanFragment.beat = beatsBOS.get(selectBeat).getBeat() + "";
                ToastUtil.show("保存成功");
                _mActivity.onBackPressedSupport();
                break;
        }
    }

    @Override
    public void getBeats(List<BeatsBO> beatsBOS) {
        this.beatsBOS = beatsBOS;
        if (!beatsBOS.isEmpty()) {
            tvJzName.setText(beatsBOS.get(0).getBeat() + "下/秒");
        }
    }

    @Override
    public void getMusics(List<MusicBO> musicBOS) {
        this.musicBOS = musicBOS;
        if (!musicBOS.isEmpty()) {
            tvBgMusicName.setText(musicBOS.get(0).getName());
        }
    }


    private void showBeats() {
        List<String> beat = new ArrayList<>();
        for (BeatsBO beatsBO : beatsBOS) {
            beat.add(beatsBO.getBeat() + "下/秒");
        }
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", beat);
        popXingZhi.setListener(new PopXingZhi.onSelectListener() {
            @Override
            public void commit(int position, String item) {
                selectBeat = position;
                tvJzName.setText(item);
            }
        });
        popXingZhi.setSelectPosition(selectBeat);
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }


    private void showMusic() {
        List<String> beat = new ArrayList<>();
        for (MusicBO beatsBO : musicBOS) {
            beat.add(beatsBO.getName());
        }
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", beat);
        popXingZhi.setListener(new PopXingZhi.onSelectListener() {
            @Override
            public void commit(int position, String item) {
                tvBgMusicName.setText(item);
                selectMusic = position;
            }
        });
        popXingZhi.setSelectPosition(selectMusic);
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }
}
