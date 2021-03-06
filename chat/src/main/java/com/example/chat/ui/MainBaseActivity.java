package com.example.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.chat.listener.OnEditDataCompletedListener;
import com.example.chat.manager.UserManager;
import com.example.chat.util.LogUtil;
import com.example.commonlibrary.BaseActivity;
import com.example.commonlibrary.mvp.presenter.BasePresenter;
import com.example.commonlibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称:    HappyChat
 * 创建人:        陈锦军
 * 创建时间:    2016/9/13      11:46
 * QQ:             1981367757
 */
public abstract class MainBaseActivity<T,P extends BasePresenter> extends BaseActivity<T,P> {


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }


        @Override
        protected void onResume() {
                super.onResume();
                checkLogin();
        }

        private void checkLogin() {
                if (UserManager.getInstance().getCurrentUser() == null) {
                        ToastUtils.showShortToast("你的帐号已在其他设备登陆,请重新登录!");
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                }
        }


        public void showEditDialog(String title, List<String> names, final OnEditDataCompletedListener listener) {
                if (names != null && names.size() > 0) {
                        mBaseDialog.setTitle(title).setEditViewsName(names).setLeftButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        cancelBaseDialog();
                                }
                        }).setRightButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        if (listener != null) {
                                                int size = mBaseDialog.getMiddleLayout().getChildCount();
                                                List<String> data = new ArrayList<>();
                                                for (int i = 0; i < size; i++) {
                                                        String result = ((EditText) ((LinearLayout) mBaseDialog.getMiddleLayout().getChildAt(i)).getChildAt(1)).getText().toString().trim();
                                                        if (result.equals("")) {
                                                                ToastUtils.showShortToast("1输入内容不能为空");
                                                                LogUtil.e("输入框不能为空");
                                                                return;
                                                        }
                                                        data.add(result);
                                                }
                                                listener.onDataInputCompleted(data);
                                                dismissBaseDialog();
                                        }
                                }
                        }).show();
                }
        }

}
