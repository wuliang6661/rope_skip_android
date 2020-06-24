package com.tohabit.skip.ui.train.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.BeatsBO;
import com.tohabit.skip.pojo.po.MusicBO;
import com.tohabit.skip.pojo.po.MusicBeatBO;
import com.tohabit.skip.ui.train.contract.RopeSkipSettingContract;
import com.tohabit.skip.ui.train.presenter.RoseSkipSettingPresenter;
import com.tohabit.skip.utils.ToastUtil;

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

    private BeatsBO selectBeat;
    private MusicBO selectMusic;

    private int musicId;
    private int beatId;

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

        if (App.musicBeatBO == null) {
            mPresenter.getBeats();
            mPresenter.getMusicList();
        } else {
            musicId = App.musicBeatBO.getMusicId();
            beatId = App.musicBeatBO.getBeatId();
            tvBgMusicName.setText(App.musicBeatBO.getMusicName());
            tvJzName.setText(App.musicBeatBO.getBeat() + "下/秒");
        }
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
                Intent intent = new Intent(getActivity(), SettingMusicActivity.class);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0x11);
                break;
            case R.id.ll_jz_fragment_rope_skip_setting:
                Intent intent1 = new Intent(getActivity(), SettingMusicActivity.class);
                intent1.putExtra("type", 1);
                startActivityForResult(intent1, 0x22);
                break;
            case R.id.btn_save_fragment_rope_skip_setting:
                saveMusic();
                break;
        }
    }

    @Override
    public void getBeats(List<BeatsBO> beatsBOS) {
        if (!beatsBOS.isEmpty()) {
            tvJzName.setText(beatsBOS.get(0).getBeat() + "下/秒");
            selectBeat = beatsBOS.get(0);
            beatId = selectBeat.getId();
        }
    }

    @Override
    public void getMusics(List<MusicBO> musicBOS) {
        if (!musicBOS.isEmpty()) {
            tvBgMusicName.setText(musicBOS.get(0).getName());
            selectMusic = musicBOS.get(0);
            musicId = selectMusic.getId();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0x11:
                MusicBO musicBO = (MusicBO) data.getSerializableExtra("music");
                if (musicBO != null) {
                    selectMusic = musicBO;
                    tvBgMusicName.setText(selectMusic.getName());
                    musicId = selectMusic.getId();
                }
                break;
            case 0x22:
                BeatsBO beatsBO = (BeatsBO) data.getSerializableExtra("beat");
                if (beatsBO != null) {
                    selectBeat = beatsBO;
                    tvJzName.setText(selectBeat.getBeat() + "下/秒");
                    beatId = selectBeat.getId();
                }
                break;
        }
    }


    /**
     * 保存音乐
     */
    private void saveMusic() {
        HttpServerImpl.saveMusicAndBeat(beatId + "", musicId + "")
                .subscribe(new HttpResultSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getMusic();
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }


    /**
     * 获取保存的音乐节拍
     */
    private void getMusic() {
        HttpServerImpl.getMusicAndBeat().subscribe(new HttpResultSubscriber<MusicBeatBO>() {
            @Override
            public void onSuccess(MusicBeatBO s) {
                App.musicBeatBO = s;
                ToastUtil.show("保存成功");
                _mActivity.onBackPressedSupport();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
