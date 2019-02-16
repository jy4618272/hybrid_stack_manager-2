package com.vdian.hybridstackmanager.hybridstackmanagerexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vdian.hybridstackmanager.hybridstackmanager.FlutterWrapActivity;

import java.util.HashMap;

public class NativeExampleActivity extends AppCompatActivity {

    private static final int REQUEST_FLUTTER = 10;
    private static final int REQUEST_NATIVE = 11;

    private TextView txtRet;
    private Button btnJumpToFlutter;
    private Button btnJumpToNative;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_example);
        toolbar = findViewById(R.id.tool_bar);
        txtRet = findViewById(R.id.txt_ret);
        btnJumpToFlutter = findViewById(R.id.btn_jump_to_flutter);
        btnJumpToNative = findViewById(R.id.btn_jump_to_native);

        Intent data = getIntent();
        if (data != null) {
            String title = data.getStringExtra("title");
            if (!TextUtils.isEmpty(title)) {
                toolbar.setTitle(title);
            }
        }

        btnJumpToFlutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> args = new HashMap<>();
                args.put("title", "Jump from native example");
                Intent intent = FlutterWrapActivity.startIntent(v.getContext(), "example", args);
                startActivityForResult(intent, REQUEST_FLUTTER);
            }
        });

        btnJumpToNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NativeExampleActivity.class);
                intent.putExtra("title", "Jump from native example");
                startActivityForResult(intent, REQUEST_NATIVE);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("message", "Return message from native example");
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_NATIVE: {
                String message = null;
                if (data != null && data.hasExtra("message")) {
                    message = data.getStringExtra("message");
                }
                message = message == null ? "No message from native example" : message;
                txtRet.setText(message);
                break;
            }
            case REQUEST_FLUTTER: {
                String message = null;
                if (data != null && data.hasExtra(FlutterWrapActivity.EXTRA_RESULT_KEY)) {
                    message = data.getStringExtra(FlutterWrapActivity.EXTRA_RESULT_KEY);
                }
                message = message == null ? "No message from flutter example" : message;
                txtRet.setText(message);
                break;
            }
        }
    }
}
