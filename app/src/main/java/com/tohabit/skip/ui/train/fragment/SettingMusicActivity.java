package com.tohabit.skip.ui.train.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sak.ultilviewlib.UltimateRefreshView;
import com.sak.ultilviewlib.adapter.InitFooterAdapter;
import com.sak.ultilviewlib.interfaces.OnFooterRefreshListener;
import com.sak.ultilviewlib.interfaces.OnHeaderRefreshListener;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.BeatsBO;
import com.tohabit.skip.pojo.po.MusicBO;
import com.tohabit.skip.widget.TraditionHeaderAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;

public class SettingMusicActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.refresh_view)
    UltimateRefreshView mSwipeRefreshLayout;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_setting_music;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            setTitleText("背景音乐");
            getMusicList();
        } else {
            setTitleText("节拍");
            getBeats();
        }
        mSwipeRefreshLayout.setBaseHeaderAdapter(new TraditionHeaderAdapter(this));
        mSwipeRefreshLayout.setBaseFooterAdapter(new InitFooterAdapter(this));
        mSwipeRefreshLayout.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(UltimateRefreshView view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0) {
                            getMusicList();
                        } else {
                            getBeats();
                        }
                    }
                }, 1000);
            }
        });
        mSwipeRefreshLayout.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(UltimateRefreshView view) {
                if (type == 0) {
                    getMusicList();
                } else {
                    getBeats();
                }
            }
        });
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
     * 查询所有节拍
     */
    public void getBeats() {
        HttpServerImpl.getBeats().subscribe(new HttpResultSubscriber<List<BeatsBO>>() {
            @Override
            public void onSuccess(List<BeatsBO> s) {
                setBeatsAdapter(s);
                mSwipeRefreshLayout.onHeaderRefreshComplete();
                mSwipeRefreshLayout.onFooterRefreshComplete();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                mSwipeRefreshLayout.onHeaderRefreshComplete();
                mSwipeRefreshLayout.onFooterRefreshComplete();
            }
        });
    }


    /**
     * 查询所有系统音乐
     */
    public void getMusicList() {
        HttpServerImpl.getMusicList().subscribe(new HttpResultSubscriber<List<MusicBO>>() {
            @Override
            public void onSuccess(List<MusicBO> s) {
                setMusicAdapter(s);
                mSwipeRefreshLayout.onHeaderRefreshComplete();
                mSwipeRefreshLayout.onFooterRefreshComplete();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                mSwipeRefreshLayout.onHeaderRefreshComplete();
                mSwipeRefreshLayout.onFooterRefreshComplete();
            }
        });
    }


    private void setMusicAdapter(List<MusicBO> musics) {
        LGRecycleViewAdapter<MusicBO> adapter = new LGRecycleViewAdapter<MusicBO>(musics) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_select_music;
            }

            @Override
            public void convert(LGViewHolder holder, MusicBO musicBO, int position) {
                holder.setImageResurce(R.id.item_img, R.mipmap.yiliao);
                holder.setText(R.id.item_title, "背景音乐");
                holder.setText(R.id.item_name, musicBO.getName());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("music", musics.get(position));
                setResult(0x11, intent);
                finish();
            }
        });
        recycleView.setAdapter(adapter);
    }


    private void setBeatsAdapter(List<BeatsBO> musics) {
        LGRecycleViewAdapter<BeatsBO> adapter = new LGRecycleViewAdapter<BeatsBO>(musics) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_select_music;
            }

            @Override
            public void convert(LGViewHolder holder, BeatsBO musicBO, int position) {
                holder.setImageResurce(R.id.item_img, R.mipmap.jiepai);
                holder.setText(R.id.item_title, "节拍");
                holder.setText(R.id.item_name, musicBO.getBeat() + "下/秒");
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("beat", musics.get(position));
                setResult(0x22, intent);
                finish();
            }
        });
        recycleView.setAdapter(adapter);
    }

}
