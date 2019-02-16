package com.vdian.hybridstackmanager.hybridstackmanagerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vdian.hybridstackmanager.hybridstackmanager.FlutterWrapActivity;

import org.voiddog.android.lib.base.recycler.adapter.ListMultiTypeBindAdapter;
import org.voiddog.android.lib.base.recycler.adapter.MultiTypeBindAdapter;
import org.voiddog.android.lib.base.recycler.viewholder.BindViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static class MenuItem {
        private String title;
        private View.OnClickListener clickListener;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public View.OnClickListener getClickListener() {
            return clickListener;
        }

        public void setClickListener(View.OnClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public MenuItem() {}

        public MenuItem(String title, View.OnClickListener clickListener) {
            this.title = title;
            this.clickListener = clickListener;
        }
    }

    public static class MenuVH extends BindViewHolder<MenuItem> {

        private TextView txtContent;

        public MenuVH(@NonNull ViewGroup parent) {
            super(parent, R.layout.rec_main_item);
            txtContent = itemView.findViewById(R.id.txt_content);
        }

        @Override
        public void onBindData(@NonNull MenuItem data) {
            txtContent.setText(data.title);
            itemView.setOnClickListener(data.clickListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListMultiTypeBindAdapter<MenuItem> adapter = new ListMultiTypeBindAdapter<>();
        adapter.registerItemProvider(new MultiTypeBindAdapter.ItemProvider<MenuItem>() {
            @Override
            public BindViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
                return new MenuVH(parent);
            }
        });
        RecyclerView recList = findViewById(R.id.rec_list);
        // data
        List<MenuItem> dataList = new ArrayList<>();
        dataList.add(new MenuItem(
                "跳转到 flutter 页面", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> args = new HashMap<>();
                args.put("title", "jump from main");
                FlutterWrapActivity.start(MainActivity.this, "example", args);
            }
        }));
        dataList.add(new MenuItem(
                "跳转到 native 页面", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NativeExampleActivity.class);
                intent.putExtra("title", "Jump from main");
                startActivity(intent);
            }
        }
        ));
        adapter.set(dataList);
        recList.setLayoutManager(new LinearLayoutManager(this));
        recList.setAdapter(adapter);
    }
}
