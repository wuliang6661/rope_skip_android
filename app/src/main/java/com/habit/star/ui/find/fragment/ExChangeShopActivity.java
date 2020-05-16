package com.habit.star.ui.find.fragment;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.AddressBO;
import com.habit.star.pojo.po.ShopDetailsBO;
import com.habit.star.ui.mine.fragment.MyAddressListFragment;
import com.habit.star.utils.AppManager;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 兑换商品界面
 */
public class ExChangeShopActivity extends BaseActivity {

    @BindView(R.id.add_address)
    LilayItemClickableWithHeadImageTopDivider addAddress;
    @BindView(R.id.name)
    AppCompatTextView name;
    @BindView(R.id.phone)
    AppCompatTextView phone;
    @BindView(R.id.moren_img)
    AppCompatTextView morenImg;
    @BindView(R.id.address_details)
    AppCompatTextView addressDetails;
    @BindView(R.id.address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.shop_img)
    RoundedImageView shopImg;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_price)
    TextView shopPrice;
    @BindView(R.id.shop_nengliang)
    TextView shopNengliang;
    @BindView(R.id.dikou_nengliang)
    TextView dikouNengliang;
    @BindView(R.id.duihuan_button)
    RelativeLayout duihuanButton;

    private ShopDetailsBO detailsBO;
    private AddressBO address;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_change_shop;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("兑换商品");

        detailsBO = (ShopDetailsBO) getIntent().getExtras().getSerializable("data");
        showData();
        getAddress();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError(int errorCode) {

    }

    private void showData() {
        Glide.with(this).load(detailsBO.getImage()).into(shopImg);
        shopName.setText(detailsBO.getName());
        shopPrice.setText("市场价 ¥ " + detailsBO.getPrice());
        shopNengliang.setText("能量  " + detailsBO.getExchangeEnergy());
        dikouNengliang.setText("- 能量 " + detailsBO.getExchangeEnergy());
    }


    /**
     * 获取收货地址
     */
    private void getAddress() {
        HttpServerImpl.getDefaultAddress().subscribe(new HttpResultSubscriber<AddressBO>() {
            @Override
            public void onSuccess(AddressBO addressBO) {
                if (addressBO == null) {
                    addressLayout.setVisibility(View.GONE);
                    addAddress.setVisibility(View.VISIBLE);
                } else {
                    addressLayout.setVisibility(View.VISIBLE);
                    addAddress.setVisibility(View.GONE);
                    address = addressBO;
                    showAddress();
                }
            }

            @Override
            public void onFiled(String message) {
                addressLayout.setVisibility(View.GONE);
                addAddress.setVisibility(View.VISIBLE);
                showToast(message);
            }
        });
    }


    private void showAddress() {
        name.setText(address.getName());
        phone.setText(address.getPhone());
        addressDetails.setText(address.getAddress());
        if (address.getIsDefault() == 1) {
            morenImg.setVisibility(View.VISIBLE);
        } else {
            morenImg.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.add_address, R.id.address_layout})
    public void goAddress() {
        Intent intent = new Intent(this, MyAddressListFragment.class);
        intent.putExtra("isSelect", 1);
        startActivity(intent);
    }


    @OnClick(R.id.duihuan_button)
    public void duihuan() {
        if(address == null){
            showToast("请选择收货地址！");
            return;
        }
        HttpServerImpl.exchangeGood(address.getId(),detailsBO.getExchangeEnergy(),detailsBO.getId(),detailsBO.getPrice() + "")
                .subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("兑换成功！");
                AppManager.getAppManager().goHome();
            }

            @Override
            public void onFiled(String message) {
               showToast(message);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddressBO addressBO) {
        address = addressBO;
        showAddress();
    }
}
