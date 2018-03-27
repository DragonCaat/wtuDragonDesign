package com.dragon.wtudragondesign.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtUserId;
    private Button mBtnAdd;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_add_frend);
        setTitle("添加好友");
        hideRightText();

        init();
        initData();
    }

    @Override
    public void init() {
        mEtUserId = fv(R.id.et_user_id);
        mBtnAdd = fv(R.id.btn_add);
    }

    @Override
    public void initData() {
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                checkUserInput();
                break;

            default:
                break;
        }
    }

    private void checkUserInput(){
        String userId = mEtUserId.getText().toString().trim();
        if (TextUtils.isEmpty(userId)){
            showToast("用户id不能为空");
        }else {
            addContact(userId);
        }
    }

    /**
     *  添加好友
     */
    public void addContact(String s){
        if(EMClient.getInstance().getCurrentUser().equals(s)){
            new EaseAlertDialog(this, "不能添加自己为好友").show();
            return;
        }

//        if(DemoHelper.getInstance().getContactList().containsKey(nameText.getText().toString())){
//            //let the user know the contact already in your contact list
//            if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(nameText.getText().toString())){
//                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
//                return;
//            }
//            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
//            return;
//        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact("加入", s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getAct(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getAct(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
