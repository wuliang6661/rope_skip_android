package com.tohabit.skip.common.fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.common.bean.EditBusEventBean;
import com.tohabit.skip.presenter.EditContentPresenter;
import com.tohabit.skip.presenter.contract.EditContentContract;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：
 * @author sundongdong6
 * @version 1.0 
 * @since  
 * 文件名称：  EditContentFragment.class
 * 类说明：文字内容输入
 */
public class EditContentFragment extends BaseFragment<EditContentPresenter> implements EditContentContract.View {
    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;

    @BindView(R.id.et_content_fragment_edit_content)
    EditText contentEt;
    @BindView(R.id.btn_clear_fragment_resource_attribute_edit)
    Button clearBtn;
    @BindView(R.id.btn_submit)
    AppCompatButton submitBtn;

    private String titleString = "";
    private String contentString = "";
    private String backContent = "";
    private EditBusEventBean eventBean;
    public static EditContentFragment newInstance(Bundle bundle) {
        EditContentFragment fragment = new EditContentFragment();
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
        return R.layout.fragment_edit_content;
    }

    @Override
    protected String getLogTag() {
        return "EditContentFragment %s";
    }

    @Override
    protected void initEventAndData() {
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleString = bundle.getString(RouterConstants.KEY_STRING, getStringResource(R.string.edit_content));
            contentString = bundle.getString(RouterConstants.ARG_STRING, "");
            backContent = bundle.getString(RouterConstants.MESSAGE, "");
            eventBean = (EditBusEventBean) bundle.getSerializable(RouterConstants.ARG_BEAN);
            if (eventBean!=null){
                contentString = eventBean.content;
            }
        }
        toolbar.setTitle(titleString);
        contentEt.setText(contentString);
        if (!TextUtils.isEmpty(backContent)){
            toolbar.setLeftText(backContent);
        }

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEt.setText("");
            }
        });

    }

    @OnClick(R.id.btn_submit)
    public void setResultAndFinish() {
        Bundle bundle = new Bundle();
        bundle.putString(RouterConstants.KEY_RESULT, contentEt.getText().toString());
        setFragmentResult(RESULT_OK, bundle);
        if (eventBean!=null){
            eventBean.content = contentEt.getText().toString();
            EventBus.getDefault().post(eventBean);
        }
        _mActivity.onBackPressedSupport();
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

}
