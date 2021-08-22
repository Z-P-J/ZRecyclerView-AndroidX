package com.zpj.recycler.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zpj.recycler.demo.mutildata.MyDragAndSwipeMultiData;
import com.zpj.recycler.demo.mutildata.StringSingleTypeMultiData;
import com.zpj.recyclerview.BounceViewHolder;
import com.zpj.recyclerview.IRefresh;
import com.zpj.recyclerview.MultiData;
import com.zpj.recyclerview.MultiRecyclerViewWrapper;
import com.zpj.recyclerview.footer.SimpleFooterViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<MultiData<?>> list = new ArrayList<>();

        list.add(new StringSingleTypeMultiData());
        list.add(new MyDragAndSwipeMultiData());
        list.add(new StringSingleTypeMultiData());
        list.add(new MyDragAndSwipeMultiData() {

            @Override
            public int getColumnCount(int viewType) {
                return getMaxColumnCount();
            }

            @Override
            public int getMaxColumnCount() {
                return 2;
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MultiRecyclerViewWrapper.with(recyclerView)
                .setMultiData(list)
                .onRefresh(new BounceViewHolder(), new IRefresh.OnRefreshListener() {
                    @Override
                    public void onRefresh(IRefresh refresh) {
                        Toast.makeText(DragActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }, 500);
                    }
                })
                .setHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header, null, false))
                .setFooterViewBinder(new SimpleFooterViewHolder(R.layout.layout_loading_footer, R.layout.layout_error_footer) {

                    @Override
                    public void onShowHasNoMore() {
                        super.onShowHasNoMore();
                        showInfo("没有更多了！");
                    }

                    @Override
                    public void onShowError(String msg) {
                        super.onShowError(msg);
                        showInfo("出错了！" + msg);
                    }

                    private void showInfo(String msg) {
                        TextView tvInfo = textView.findViewById(R.id.tv_info);
                        tvInfo.setText(msg);
                    }

                })
                .build();

    }

}
