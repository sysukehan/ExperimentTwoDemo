package com.study.android.kehan.experimenttwo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.study.android.kehan.experimenttwo.AppInfo.AppInfo;
import com.study.android.kehan.experimenttwo.R;

/**
 * Created by kehan on 16-9-12.
 */
public class ChooseModeActivity extends AppCompatActivity {

    private TextView base;
    private TextView expand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);
        findView();
        setListener();
    }

    /**
     * 获得控件
     */
    private void findView() {
        base = (TextView) findViewById(R.id.base);
        expand = (TextView) findViewById(R.id.expand);
    }

    /**
     * 绑定自定义监听器类
     */
    private void setListener() {
        base.setOnClickListener(new ChooseModeListener(AppInfo.BASE_MODE));
        expand.setOnClickListener(new ChooseModeListener(AppInfo.EXPAND_MODE));
    }

    /**
     * 自定义监听器类
     */
    private class ChooseModeListener implements View.OnClickListener {

        private int mode;

        public ChooseModeListener(int mode) {
            this.mode = mode;
        }

        @Override
        public void onClick(View view) {
            Intent newIntent = new Intent(ChooseModeActivity.this, MainActivity.class);
            newIntent.putExtra(AppInfo.SIGN, mode);  //  将启动的模式传入下一个Activity，指定加载基础部分或扩展部分
            startActivity(newIntent);
        }
    }
}
