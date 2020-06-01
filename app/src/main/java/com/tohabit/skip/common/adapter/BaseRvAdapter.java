package com.tohabit.skip.common.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 创建日期：2018/6/4 15:17
 *
 * @author linzhiliang
 * @version 1.0
 * @since 文件名称： BaseRvAdapter.java
 * 类说明：带有自定义加载更多标志量的基类适配器
 */
public abstract class BaseRvAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    /**
     * 是否正在加载更多，这里单独定义这个属性是因为BaseQuickAdapter中mLoading这个变量在onLoadMoreRequested之前就已经设置为true了
     * 无法使用mLoading这个变量来进行上拉加载更多的时候进行保护
     */
    private boolean isLoadingMore = false;

    public BaseRvAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    @Override
    public void loadMoreEnd() {
        setLoadingMore(false);
        super.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        setLoadingMore(false);
        super.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        setLoadingMore(false);
        super.loadMoreFail();
    }
}
