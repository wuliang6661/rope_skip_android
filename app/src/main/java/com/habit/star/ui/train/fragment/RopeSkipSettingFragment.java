package com.habit.star.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.train.contract.RopeSkipSettingContract;
import com.habit.star.ui.train.presenter.RoseSkipSettingPresenter;
import com.habit.star.utils.ToastUtil;

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


    private OptionsPickerView<String> jiezhouOptions;
    private List<String> jiezhouList = new ArrayList<>();
    private String jpStr = "3下/秒";

    private OptionsPickerView<String> musicOptions;
    private List<String> musicList = new ArrayList<>();
    private String musicStr = "小苹果";


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
        jiezhouList.add("1下/秒");
        jiezhouList.add("2下/秒");
        jiezhouList.add("3下/秒");
        jiezhouList.add("4下/秒");
        jiezhouList.add("5下/秒");

        musicList.add("小苹果");
        musicList.add("小苹果1");
        musicList.add("小苹果2");
        musicList.add("小苹果3");
        musicList.add("小苹果4");
        musicList.add("小苹果5");
        musicList.add("小苹果6");
        initCustomOptionPicker();
    }


    private void initCustomOptionPicker() {

        jiezhouOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                jpStr = jiezhouList.get(options1);
                tvJzName.setText(jpStr);
            }
        }).setLayoutRes(R.layout.custom_zhiye_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancle);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jiezhouOptions.returnData();
                                jiezhouOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jiezhouOptions.dismiss();
                            }
                        });

                    }
                })
//                .isDialog(true)
                .build();

        jiezhouOptions.setPicker(jiezhouList);

        musicOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                musicStr = musicList.get(options1);
                tvBgMusicName.setText(musicStr);
            }
        }).setLayoutRes(R.layout.custom_zhiye_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancle);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                musicOptions.returnData();
                                musicOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                musicOptions.dismiss();
                            }
                        });

                    }
                })
//                .isDialog(true)
                .build();

        musicOptions.setPicker(musicList);//添加数据
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
                int p = jiezhouList.indexOf(musicStr);
                if (p >= 0) {
                    musicOptions.setSelectOptions(p);
                }
                musicOptions.show();
                break;
            case R.id.ll_jz_fragment_rope_skip_setting:
                int position = jiezhouList.indexOf(jpStr);
                if (position >= 0) {
                    jiezhouOptions.setSelectOptions(position);
                }
                jiezhouOptions.show();
                break;
            case R.id.btn_save_fragment_rope_skip_setting:
                ToastUtil.show("保存成功");
                _mActivity.onBackPressedSupport();
                break;
        }
    }
}
