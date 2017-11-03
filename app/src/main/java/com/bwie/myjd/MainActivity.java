package com.bwie.myjd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bwie.myjd.fragment.ClassifyFragment;
import com.bwie.myjd.fragment.FindFragment;
import com.bwie.myjd.fragment.FristFragment;
import com.bwie.myjd.fragment.MyFragment;
import com.bwie.myjd.fragment.ShopingCartFragment;
import com.yzs.yzsbaseactivitylib.activity.YzsBaseActivity;
import com.yzs.yzsbaseactivitylib.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends YzsBaseActivity{


    @InjectView(R.id.fl_Container)
    FrameLayout flContainer;
    @InjectView(R.id.bottomBar)
    BottomNavigationBar bottomBar;
    private List<Fragment> fragments = new ArrayList<>();
    private ImageView ivTab;
    private int curFragment=-1;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        manager = getSupportFragmentManager();
        initData();
        initTab();

    }

    @Override
    protected void initContentView(Bundle bundle) {

    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    private void initData() {
        fragments.add(new FristFragment());
        fragments.add(new ClassifyFragment());
        fragments.add(new FindFragment());
        fragments.add(new ShopingCartFragment());
        fragments.add(new MyFragment());
    }

    /**
     * 初始化fragmenttabhost
     */
    private void initTab() {
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomBar.addItem(new BottomNavigationItem(R.drawable.frist,"首页")).setActiveColor(R.color.red_text_color)
                  .addItem(new BottomNavigationItem(R.drawable.classify,"分类"))
                  .addItem(new BottomNavigationItem(R.drawable.find_page,"发现"))
                  .addItem(new BottomNavigationItem(R.drawable.shopping_cart,"购物车"))
                  .addItem(new BottomNavigationItem(R.drawable.my_page,"我的")).initialise();


        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switchTab(position);

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        switchTab(0);

    }

    private void switchTab(int position) {

        /*switch (position){
            case 0:
                manager.beginTransaction().replace(R.id.fl_Container,fragments.get(0),"f1").commit();
                break;
            case 1:
                manager.beginTransaction().replace(R.id.fl_Container,fragments.get(1),"f2").commit();
                break;
            case 2:
                manager.beginTransaction().replace(R.id.fl_Container,fragments.get(2),"f3").commit();
                break;
            case 3:
                manager.beginTransaction().replace(R.id.fl_Container,fragments.get(3),"f4").commit();
                break;
            case 4:
                manager.beginTransaction().replace(R.id.fl_Container,fragments.get(4),"f5").commit();
                break;
        }*/

        Fragment fragment = manager.findFragmentByTag("" + position);
        FragmentTransaction beginTransaction = manager.beginTransaction();
        if(fragment==null){
            if(manager.findFragmentByTag("" + curFragment)!=null){
                beginTransaction.hide(fragments.get(curFragment));
            }
            beginTransaction.add(R.id.fl_Container,fragments.get(position),""+position)
                    .show(fragments.get(position))
                    .commit();


        }else if(curFragment!=position){
            beginTransaction.hide(fragments.get(curFragment))
                    .show(fragments.get(position))
                    .commit();
        }
        curFragment=position;
    }


}
