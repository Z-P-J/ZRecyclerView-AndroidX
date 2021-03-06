package com.zpj.recyclerview;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.zpj.recyclerview.footer.AbsFooterViewHolder;
import com.zpj.recyclerview.footer.DefaultFooterViewHolder;
import com.zpj.recyclerview.footer.IFooterViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EasyRecyclerView<T> extends EasyStateConfig<EasyRecyclerView<T>>
        implements IEasy.OnLoadMoreListener {

    protected final RecyclerView recyclerView;

    protected RecyclerView.LayoutManager layoutManager;

    protected EasyStateAdapter<T> easyAdapter;

    protected IRefresh mRefresh;

    protected IEasy.AdapterInjector adapterInjector;

    protected List<T> list;

    protected int itemRes = -1;

    protected View headerView;
    protected IEasy.OnBindHeaderListener onBindHeaderListener;
//    protected IEasy.OnBindFooterListener onBindFooterListener;
//    protected View footerView;
    protected IFooterViewHolder footerViewBinder;

    protected boolean enableLoadMore = false;

    protected IEasy.OnGetChildViewTypeListener<T> onGetChildViewTypeListener;
    protected IEasy.OnGetChildLayoutIdListener onGetChildLayoutIdListener;
    protected IEasy.OnBindViewHolderListener<T> onBindViewHolderListener;
    protected IEasy.OnCreateViewHolderListener<T> onCreateViewHolder;
    protected IEasy.OnLoadMoreListener onLoadMoreListener;

    protected final SparseArray<IEasy.OnClickListener<T>> onClickListeners = new SparseArray<>();
    protected final SparseArray<IEasy.OnLongClickListener<T>> onLongClickListeners = new SparseArray<>();
    protected IEasy.OnItemClickListener<T> onItemClickListener;
    protected IEasy.OnItemLongClickListener<T> onItemLongClickListener;

    public EasyRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

//    @Override
//    public IViewHolder getViewHolder(State state) {
//        IViewHolder viewHolder = super.getViewHolder(state);
//        if (viewHolder instanceof BaseViewHolder) {
//            ((BaseViewHolder) viewHolder).setOnRetry(new Runnable() {
//                @Override
//                public void run() {
//                    if (onLoadRetryListener != null) {
//                        onLoadRetryListener.onLoadRetry();
//                    }
//                }
//            });
//        }
//        return viewHolder;
//    }

    public EasyRecyclerView<T> setItemAnimator(RecyclerView.ItemAnimator animator) {
        recyclerView.setItemAnimator(animator);
        return this;
    }

    public EasyRecyclerView<T> setItemRes(int res) {
        this.itemRes = res;
        return this;
    }

    public EasyRecyclerView<T> setData(List<T> list) {
        this.list = list;
        return this;
    }

    public EasyRecyclerView<T> setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public EasyRecyclerView<T> addItemDecoration(RecyclerView.ItemDecoration decor) {
        this.recyclerView.addItemDecoration(decor);
        return this;
    }

    public EasyRecyclerView<T> addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        this.recyclerView.addItemDecoration(decor, index);
        return this;
    }

    public EasyRecyclerView<T> setItemViewCacheSize(int size) {
        recyclerView.setItemViewCacheSize(size);
        return this;
    }

    public EasyRecyclerView<T> setHasFixedSize(boolean hasFixedSize) {
        recyclerView.setHasFixedSize(hasFixedSize);
        return this;
    }

    public EasyRecyclerView<T> setLayoutFrozen(boolean layoutFrozen) {
        recyclerView.setLayoutFrozen(layoutFrozen);
        return this;
    }

    public EasyRecyclerView<T> setOnFlingListener(RecyclerView.OnFlingListener listener) {
        recyclerView.setOnFlingListener(listener);
        return this;
    }

    public EasyRecyclerView<T> setRecyclerListener(RecyclerView.RecyclerListener listener) {
        recyclerView.setRecyclerListener(listener);
        return this;
    }

    public EasyRecyclerView<T> setScrollingTouchSlop(int slop) {
        recyclerView.setScrollingTouchSlop(slop);
        return this;
    }

    public EasyRecyclerView<T> setEdgeEffectFactory(RecyclerView.EdgeEffectFactory factory) {
        recyclerView.setEdgeEffectFactory(factory);
        return this;
    }

    public EasyRecyclerView<T> setRecycledViewPool(RecyclerView.RecycledViewPool pool) {
        recyclerView.setRecycledViewPool(pool);
        return this;
    }

    public EasyRecyclerView<T> setNestedScrollingEnabled(boolean enabled) {
        recyclerView.setNestedScrollingEnabled(enabled);
        return this;
    }

    public EasyRecyclerView<T> setPreserveFocusAfterLayout(boolean preserveFocusAfterLayout) {
        recyclerView.setPreserveFocusAfterLayout(preserveFocusAfterLayout);
        return this;
    }

    public EasyRecyclerView<T> setViewCacheExtension(RecyclerView.ViewCacheExtension extension) {
        recyclerView.setViewCacheExtension(extension);
        return this;
    }

    public EasyRecyclerView<T> setChildDrawingOrderCallback(RecyclerView.ChildDrawingOrderCallback callback) {
        recyclerView.setChildDrawingOrderCallback(callback);
        return this;
    }

    public EasyRecyclerView<T> setHeaderView(View headerView) {
        this.headerView = headerView;
        return this;
    }

    @SuppressLint("ResourceType")
    public EasyRecyclerView<T> setHeaderView(@LayoutRes int layoutRes, IEasy.OnBindHeaderListener l) {
        if (layoutRes > 0 && l != null) {
            this.headerView = LayoutInflater.from(recyclerView.getContext()).inflate(layoutRes, null, false);
            onBindHeaderListener = l;
        }
        return this;
    }

    public EasyRecyclerView<T> setFooterViewBinder(IFooterViewHolder footerViewBinder) {
        this.footerViewBinder = footerViewBinder;
        return this;
    }

    public EasyRecyclerView<T> setFooterView(final View footerView) {
//        this.footerView = headerView;
        this.footerViewBinder = new AbsFooterViewHolder() {
            @Override
            public View onCreateFooterView(ViewGroup root) {
                return footerView;
            }
        };
        return this;
    }

    public EasyRecyclerView<T> setFooterView(@LayoutRes final int layoutRes, final IEasy.OnBindFooterListener listener) {
//        this.footerView = LayoutInflater.from(recyclerView.getContext()).inflate(layoutRes, null, false);
//        onBindFooterListener = listener;
        this.footerViewBinder = new AbsFooterViewHolder() {
            @Override
            public View onCreateFooterView(ViewGroup root) {
                return LayoutInflater.from(root.getContext()).inflate(layoutRes, null, false);
            }

            @Override
            public void onBindFooter(EasyViewHolder holder) {
                if (listener != null) {
                    listener.onBindFooter(holder);
                }
            }
        };
        return this;
    }

    public EasyRecyclerView<T> onLoadMore(IEasy.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        this.enableLoadMore = true;
        return this;
    }

    public EasyRecyclerView<T> onGetChildViewType(IEasy.OnGetChildViewTypeListener<T> listener) {
        this.onGetChildViewTypeListener = listener;
        return this;
    }

    public EasyRecyclerView<T> onGetChildLayoutId(IEasy.OnGetChildLayoutIdListener listener) {
        this.onGetChildLayoutIdListener = listener;
        return this;
    }

    public EasyRecyclerView<T> onBindViewHolder(IEasy.OnBindViewHolderListener<T> callback) {
        this.onBindViewHolderListener = callback;
        return this;
    }

    public EasyRecyclerView<T> onCreateViewHolder(IEasy.OnCreateViewHolderListener<T> callback) {
        this.onCreateViewHolder = callback;
        return this;
    }

    public IEasy.OnCreateViewHolderListener<T> getOnCreateViewHolder() {
        return onCreateViewHolder;
    }

    public EasyRecyclerView<T> addOnScrollListener(final RecyclerView.OnScrollListener onScrollListener) {
        recyclerView.addOnScrollListener(onScrollListener);
        return this;
    }

    public EasyRecyclerView<T> onViewClick(@IdRes int id, IEasy.OnClickListener<T> listener) {
        onClickListeners.put(id, listener);
        return this;
    }

    public EasyRecyclerView<T> onViewClick(IEasy.OnClickListener<T> listener, int... ids) {
        for (int id : ids) {
            onClickListeners.put(id, listener);
        }
        return this;
    }

    public EasyRecyclerView<T> onViewLongClick(@IdRes int id, IEasy.OnLongClickListener<T> listener) {
        onLongClickListeners.put(id, listener);
        return this;
    }

    public EasyRecyclerView<T> onViewLongClick(IEasy.OnLongClickListener<T> listener, int... ids) {
        for (int id : ids) {
            onLongClickListeners.put(id, listener);
        }
        return this;
    }

    public EasyRecyclerView<T> onItemClick(IEasy.OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
        return this;
    }

    public EasyRecyclerView<T> onItemLongClick(IEasy.OnItemLongClickListener<T> listener) {
        this.onItemLongClickListener = listener;
        return this;
    }

    public EasyRecyclerView<T> setLoadMoreEnabled(boolean enabled) {
        this.enableLoadMore = enabled;
        return this;
    }

    public EasyRecyclerView<T> setAdapterInjector(IEasy.AdapterInjector adapterInjector) {
        this.adapterInjector = adapterInjector;
        if (easyAdapter != null) {
            easyAdapter.setAdapterInjector(adapterInjector);
        }
        return this;
    }

    public EasyRecyclerView<T> onRefresh(IRefresh.OnRefreshListener listener) {
        mRefresh = new RefreshViewHolder();
        mRefresh.setOnRefreshListener(listener);
        return this;
    }

    public EasyRecyclerView<T> onRefresh(IRefresh refresh) {
        mRefresh = refresh;
        return this;
    }

    public EasyRecyclerView<T> onRefresh(IRefresh refresh, IRefresh.OnRefreshListener listener) {
        mRefresh = refresh;
        if (refresh != null) {
            refresh.setOnRefreshListener(listener);
        }
        return this;
    }

    public void build() {
        if (list == null) {
            list = new ArrayList<>(0);
        }
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
        }

        easyAdapter = new EasyStateAdapter<>(
                recyclerView.getContext(), list,
                itemRes, onGetChildViewTypeListener,
                onGetChildLayoutIdListener, onCreateViewHolder,
                onBindViewHolderListener, onItemClickListener,
                onItemLongClickListener, onClickListeners,
                onLongClickListeners, mRefresh, this
        );
        easyAdapter.setAdapterInjector(adapterInjector);
        if (headerView != null) {
            easyAdapter.setHeaderView(headerView);
            easyAdapter.setOnBindHeaderListener(onBindHeaderListener);
        }
        if (footerViewBinder != null) {
            easyAdapter.setFooterViewHolder(footerViewBinder);
//            easyAdapter.setFooterView(footerView);
//            easyAdapter.setOnBindFooterListener(onBindFooterListener);
        } else if (onLoadMoreListener != null && enableLoadMore) {
//            footerView = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.easy_base_footer, null, false);
//            easyAdapter.setFooterView(footerView);
//            easyAdapter.setOnBindFooterListener(onBindFooterListener);
            easyAdapter.setFooterViewHolder(new DefaultFooterViewHolder());
        }
        easyAdapter.setOnLoadMoreListener(this);
        easyAdapter.setLoadMoreEnabled(onLoadMoreListener != null && enableLoadMore);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(easyAdapter);
    }

    /**
     * ???????????????
     */
    public final void showEmpty() {
        easyAdapter.showEmpty();
    }

    public void showEmptyView(int msgId) {
        easyAdapter.showEmptyView(msgId);
    }

    public void showEmptyView(String msg) {
        easyAdapter.showEmptyView(msg);
    }

    public void showEmptyView(int msgId, int imgId) {
        easyAdapter.showEmptyView(msgId, imgId);
    }

    public void showEmptyView(String msg, int imgId) {
        easyAdapter.showEmptyView(msg, imgId);
    }

    /**
     * ??????????????????
     */
    public final void showError() {
        easyAdapter.showError();
    }

    public void showErrorView(int msgId) {
        easyAdapter.showErrorView(msgId);
    }

    public void showErrorView(String msg) {
        easyAdapter.showErrorView(msg);
    }

    public void showErrorView(int msgId, int imgId) {
        easyAdapter.showErrorView(msgId, imgId);
    }

    public void showErrorView(String msg, int imgId) {
        easyAdapter.showErrorView(msg, imgId);
    }

    /**
     * ?????????????????????
     */
    public final void showLoading() {
        easyAdapter.showLoading();
    }

    public void showLoadingView(View view) {
        easyAdapter.showLoadingView(view);
    }

    public void showLoadingView(View view, boolean showTip) {
        easyAdapter.showLoadingView(view, showTip);
    }

    public void showLoadingView(int msgId) {
        easyAdapter.showLoadingView(msgId);
    }

    public void showLoadingView(String msg) {
        easyAdapter.showLoadingView(msg);
    }

    /**
     * ?????????????????????
     */
    public final void showNoNetwork() {
        easyAdapter.showNoNetwork();
    }

    public void showNoNetworkView(int msgId) {
        easyAdapter.showNoNetworkView(msgId);
    }

    public void showNoNetworkView(String msg) {
        easyAdapter.showNoNetworkView(msg);
    }

    public void showNoNetworkView(int msgId, int imgId) {
        easyAdapter.showNoNetworkView(msgId, imgId);
    }

    /**
     * ??????????????????
     */
    public final void showContent() {
        easyAdapter.showContent();
    }

    public void notifyDataSetChanged() {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyDataSetChanged();
    }

    public void notifyItemChanged(int position) {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyItemChanged(position);
    }

    public void notifyItemChanged(int position, Object payload) {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyItemChanged(position, payload);
    }

    public void notifyVisibleItemChanged() {
        notifyVisibleItemChanged(null);
    }

    public void notifyVisibleItemChanged(Object payload) {
        RecyclerView.LayoutManager manager = getLayoutManager();
        int first = -1;
        int last = -1;
        if (manager instanceof LinearLayoutManager) {
            first = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
            last = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
//            if (payload == null) {
//                notifyItemRangeChanged(first, last - first + 1);
//            } else {
//                notifyItemRangeChanged(first, last - first + 1, payload);
//            }
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;
            int[] firsts = new int[layoutManager.getSpanCount()];
            int[] into = new int[layoutManager.getSpanCount()];
            layoutManager.findFirstVisibleItemPositions(firsts);
            layoutManager.findLastVisibleItemPositions(into);
            first = first(firsts);
            last = last(into);
        }

        if (getAdapter().getHeaderView() != null) {
            first += 1;
        }

        if (last > first) {
            int count = last - first;
            if (last + 1 <= getData().size()) {
                count += 1;
            }
            if (payload == null) {
                notifyItemRangeChanged(first, count);
            } else {
                notifyItemRangeChanged(first, count, payload);
            }
        }
    }

    private int first(int[] firstPositions) {
        int first = firstPositions[0];
        for (int value : firstPositions) {
            if (value < first) {
                first = value;
            }
        }
        return first;
    }

    private int last(int[] lastPositions) {
        int last = lastPositions[0];
        for (int value : lastPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }

    public void notifyItemInserted(int position) {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyItemInserted(position);
    }

    public void notifyItemRangeChanged(int start, int itemCount) {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyItemRangeChanged(start, itemCount);
    }

    public void notifyItemRangeChanged(int start, int itemCount, Object payload) {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyItemRangeChanged(start, itemCount, payload);
    }

    public void notifyItemRemoved(int position) {
        if (easyAdapter == null) {
            return;
        }
        easyAdapter.notifyItemRemoved(position);
    }

    public void smoothScrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    public EasyStateAdapter<T> getAdapter() {
        return easyAdapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void post(Runnable runnable) {
        recyclerView.post(runnable);
    }

    public List<T> getData() {
        return list;
    }

    @Override
    public boolean onLoadMore(EasyAdapter.Enabled enabled, int currentPage) {
        if (onLoadMoreListener != null) {
            return onLoadMoreListener.onLoadMore(enabled, currentPage);
        }
        return false;
    }
}
