package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.FriendsAdapter;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import static com.dragon.wtudragondesign.fragment.FragmentMessage.easeConversationList;

public class ActivityFriends extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRvFriends;

    private SwipeRefreshLayout swipeRefresh;

    private FriendsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_friends);

        setTitle("我的好友");
        hideRightText();
        init();
        initData();
    }

    @Override
    public void init() {
        GridLayoutManager manager = new GridLayoutManager(getAct(), 1);
        mRvFriends = fv(R.id.rv_friends);
        mRvFriends.setLayoutManager(manager);

        swipeRefresh = fv(R.id.refresh_friend);
    }

    @Override
    public void initData() {
//        if (easeContactListFragment == null) {
//            easeContactListFragment = new EaseContactListFragment();
//            easeContactListFragment.hideTitleBar();
//        }
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.contact_cont, easeContactListFragment).commit();

        getFriendsList();

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
    }

    private void getFriendsList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FriendsAdapter friendsAdapter = new FriendsAdapter(usernames,getAct());
                            mRvFriends.setAdapter(friendsAdapter);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public void onRefresh() {
        refreshFriends();
    }

    /**
     * 模拟刷新数据界面
     */
    private void refreshFriends() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getFriendsList();
                        showToast("就这几个好友，你还刷");
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
