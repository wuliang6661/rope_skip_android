package com.habit.star.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.habit.star.R;
import com.habit.star.base.BaseActivity;
import com.habit.star.event.model.BlueEvent;
import com.habit.star.utils.blue.BlueUtils;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.inuker.bluetooth.library.search.SearchResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    List<SearchResult> results;


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_search_blue;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        results = new ArrayList<>();
        initBlue();
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


    /**
     * 蓝牙连接并获取数据
     */
    private void initBlue() {
        BlueUtils blueUtils = BlueUtils.getInstance();
        blueUtils.setListener(new BlueUtils.onBlueListener() {
            @Override
            public void onConnect(boolean isConnect) {
                stopProgress();
                if (isConnect) {
                    showToast("蓝牙连接成功！");
                    BlueEvent event = new BlueEvent();
                    event.isConnect = true;
                    EventBus.getDefault().post(event);
                } else {
                    showToast("蓝牙连接失败！");
                    BlueEvent event = new BlueEvent();
                    event.isConnect = false;
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void searchStart() {

            }

            @Override
            public void searchStop() {
               stopProgress();
            }

            @Override
            public void searchMacs(SearchResult devices) {
                for (SearchResult item : results) {
                    if (devices.getName().equals(item.getName())) {
                        return;
                    }
                }
                results.add(devices);
                setAdapter();
            }
        });
        blueUtils.searchMac();
        showProgress("蓝牙搜索中...");
    }

    private void setAdapter() {
        LGRecycleViewAdapter<SearchResult> adapter = new LGRecycleViewAdapter<SearchResult>(results) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blue;
            }

            @Override
            public void convert(LGViewHolder holder, SearchResult result, int position) {
                holder.setText(R.id.item_text, result.getName());
            }
        };
        adapter.setOnItemClickListener(R.id.connect, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                BlueUtils.getInstance().connectMac(results.get(position).getAddress());
            }
        });
        recycleView.setAdapter(adapter);
    }



    @OnClick(R.id.search_btn)
    public void search(){
        initBlue();
    }




}
