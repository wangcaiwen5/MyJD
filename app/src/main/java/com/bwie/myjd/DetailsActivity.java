package com.bwie.myjd;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bwie.myjd.api.Api;
import com.bwie.myjd.detailsfragment.DetailsFragment;
import com.bwie.myjd.detailsfragment.PIngjiaFragment;
import com.bwie.myjd.detailsfragment.ShoppFragment;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailsActivity extends AppCompatActivity{


    @InjectView(R.id.iv_fx)
    ImageView ivFx;


    @InjectView(R.id.toolbar_details)
    Toolbar toolbarDetails;
    @InjectView(R.id.tl_item)
    TabLayout tlItem;
    @InjectView(R.id.vp_details_page)
    ViewPager vpDetailsPage;
   private UMShareListener umShareListener;


    private List<String> itemlist = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.inject(this);


        getCid();
        initToolbar();
        initTab();
        initView();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void initView() {
        ivFx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareAction(DetailsActivity.this)
                        .withMedia(new UMWeb(Api.PRODUCT_DETAIL + "?" + "pid=" + id))
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();

            }
        });

        umShareListener = new UMShareListener() {
            /**
             * @descrption 分享开始的回调
             * @param platform 平台类型
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            /**
             * @descrption 分享成功的回调
             * @param platform 平台类型
             */
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(DetailsActivity.this,"成功了",Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享失败的回调
             * @param platform 平台类型
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(DetailsActivity.this,"失败"+t.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享取消的回调
             * @param platform 平台类型
             */
            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(DetailsActivity.this,"取消了",Toast.LENGTH_LONG).show();

            }
        };
    }

    private void getCid() {
        id = getIntent().getIntExtra("pid", -1);
    }

    /**
     * 初始化tablayout
     */
    private void initTab() {
        itemlist.add("商品");
        itemlist.add("详情");
        itemlist.add("评论");
        System.out.println("id=="+id);
        fragmentList.add(new ShoppFragment());
        fragmentList.add(new DetailsFragment());
        fragmentList.add(new PIngjiaFragment());



        MyTabLayoutAdapter tabLayoutAdapter = new MyTabLayoutAdapter(getSupportFragmentManager());

        tlItem.setSelected(true);
        tlItem.setTabsFromPagerAdapter(tabLayoutAdapter);
        vpDetailsPage.setAdapter(tabLayoutAdapter);
        tlItem.setupWithViewPager(vpDetailsPage);
    }


    private void initToolbar() {
        setActionBar(toolbarDetails);

        //toolbar的menu点击事件的监听
        toolbarDetails.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);//加载menu文件到布局
        return true;
    }




    class MyTabLayoutAdapter extends FragmentPagerAdapter

    {


    public MyTabLayoutAdapter(FragmentManager fm) {
        super(fm);

    }



        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putString("pid",id+"");
            fragmentList.get(position).setArguments(bundle);

        return fragmentList.get(position);
    }

        @Override
        public int getCount() {
        return itemlist.size();
    }

        @Override
        public CharSequence getPageTitle(int position) {
        return itemlist.get(position);
    }
    }

}
