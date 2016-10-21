package com.study.android.kehan.experimenttwo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.study.android.kehan.experimenttwo.AppInfo.AppInfo;
import com.study.android.kehan.experimenttwo.R;

/**
 * Created by kehan on 16-9-8.
 */
public class MainActivity extends AppCompatActivity {

    //  字符串常量 START

    private final String correctUsername = "Android";
    private final String correctPassword = "123456";
    private final String emptyUsername = "用户名不能为空";
    private final String emptyPassword = "密码不能为空";
    private final String loginSuccess = "登录成功";
    private final String loginFail = "登录失败";
    private final String unableRegister = "注册功能尚未开启";
    private final String studentIdentity = "学生身份";
    private final String teacherIdentity = "教师身份";
    private final String groupIdentity = "社团身份";
    private final String managerIdentity = "管理者身份";
    private final String identityIsSelected = "被选中";

    //  字符串常量 END

    //  基础版控件 START

    private EditText baseUsername;
    private EditText basePassword;

    //  基础版控件 END

    //  拓展版控件 START

    private TextInputLayout usernameText;
    private TextInputLayout passwordText;
    private EditText usernameEdit;
    private EditText passwordEdit;

    //  拓展版控件 END

    //  通用控件 START

    private LinearLayout rootView;
    private RadioGroup rg;
    private Button login;
    private Button register;

    private ViewStub base;
    private ViewStub extend;

    private AlertDialog.Builder builder;

    //  通用控件 END

    private int sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        sign = getIntent().getIntExtra(AppInfo.SIGN, AppInfo.BASE_MODE);
        findView();
        initialData();
        setListener();
    }

    private void findView() {

        rootView = (LinearLayout) findViewById(R.id.root);
        base = (ViewStub) findViewById(R.id.base);
        extend = (ViewStub) findViewById(R.id.extend);

        if (sign == AppInfo.BASE_MODE) {  //  如果是基础模式，则加载基础布局
            base.inflate();
            baseUsername = (EditText) findViewById(R.id.base_username);
            basePassword = (EditText) findViewById(R.id.base_password);
        } else {  //  否则，加载扩展布局
            extend.inflate();
            usernameText = (TextInputLayout) findViewById(R.id.more_username);
            passwordText = (TextInputLayout) findViewById(R.id.more_password);
            usernameEdit = usernameText.getEditText();
            passwordEdit = passwordText.getEditText();
        }

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        rg = (RadioGroup) findViewById(R.id.parent);

        builder = new AlertDialog.Builder(MainActivity.this);  //  实例化对话框
    }

    /**
     * 设置对话框的内容
     */
    private void initialData() {
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListener() {

        if (sign == AppInfo.BASE_MODE) {
            login.setOnClickListener(new BaseLoginListener());
        } else {
            login.setOnClickListener(new ExpandLoginListener());
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.student:
                        showMessage(studentIdentity + unableRegister);
                        break;
                    case R.id.teacher:
                        showMessage(teacherIdentity + unableRegister);
                        break;
                    case R.id.group:
                        showMessage(groupIdentity + unableRegister);
                        break;
                    case R.id.manager:
                        showMessage(managerIdentity + unableRegister);
                        break;
                    default:
                        break;
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.student:
                        showMessage(studentIdentity + identityIsSelected);
                        break;
                    case R.id.teacher:
                        showMessage(teacherIdentity + identityIsSelected);
                        break;
                    case R.id.group:
                        showMessage(groupIdentity + identityIsSelected);
                        break;
                    case R.id.manager:
                        showMessage(managerIdentity + identityIsSelected);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void showMessage(String message) {
        if (sign == AppInfo.BASE_MODE) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
                    .setAction("按钮", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                    .setDuration(5000)
                    .show();
        }
    }

    /**
     * 基础版本登录按钮监听器类
     */
    private class BaseLoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String username = baseUsername.getText().toString();
            String password = basePassword.getText().toString();

            if (TextUtils.isEmpty(username)) {
                showMessage(emptyUsername);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                showMessage(emptyPassword);
                return;
            }

            if (correctUsername.equals(username) && correctPassword.equals(password)) {
                builder.setMessage(loginSuccess);
                builder.create().show();
            } else {
                builder.setMessage(loginFail);
                builder.create().show();
            }
        }
    }

    /**
     * 扩展版本登录按钮监听器
     */
    private class ExpandLoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if (TextUtils.isEmpty(username)) {
                usernameText.setErrorEnabled(true);
                usernameText.setError(emptyUsername);
                return;
            } else {
                usernameText.setErrorEnabled(false);
            }
            if (TextUtils.isEmpty(password)) {
                passwordText.setErrorEnabled(true);
                passwordText.setError(emptyPassword);
                return;
            } else {
                passwordText.setErrorEnabled(false);
            }
            if (correctUsername.equals(username) && correctPassword.equals(password)) {
                showMessage(loginSuccess);
            } else {
                showMessage(loginFail);
            }
        }
    }
}
