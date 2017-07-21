package com.xwc1125.yuancy_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xwc1125.droidapp.R;
import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidui.ToolBar.ToolbarHelper;
import com.xwc1125.yuancy_app.gesturelock.GettureLockActivity;
import com.xwc1125.yuancy_app.javascript.JavascriptFragment;
import com.xwc1125.yuancy_app.javascript.JavascriptFragment2;
import com.xwc1125.yuancy_app.material_design.cardview.CardViewFragment;
import com.xwc1125.yuancy_app.qr.QRFragment;
import com.xwc1125.yuancy_app.recycler.RecyclerFragment;
import com.xwc1125.yuancy_app.recycler.dummy.DummyContent;
import com.xwc1125.yuancy_app.toolbar.ToolbarActivity;
import com.xwc1125.yuancy_app.utils.Install_APK_Fragment;
import com.xwc1125.yuancy_app.webview.WebViewFragment;

/**
 * home activity
 */
public class HomeActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecyclerFragment.OnListFragmentInteractionListener {

    private static final String DATA_FLAG = "intent_data_flag";

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void getBundleExtra() {
    }

    @Override
    protected void initViews() {
        ToolbarHelper toolbarHelper = new ToolbarHelper(activity);
        /**
         * 悬浮按钮
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *
                 提示信息:Snackbar基本上继承了和Toast一样的方法和属性，例如LENGTH_LONG 和 LENGTH_SHORT用于设置显示时长。
                 * Snackbar.make(view, message, duration)
                 .setAction(action message, click listener)
                 .show();
                 .setActionTextColor(R.color.material_blue)//设置文字颜色
                 .setDuration(4000).show();//显示时间
                 */
                Snackbar.make(view, "显示提示", Snackbar.LENGTH_LONG)
                        .setAction("点击", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //点击事件
                                Toast.makeText(activity, "点击事件", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        /**
         * 侧滑菜单
         *
         * drawerLayout其实是一个布局控件，跟LinearLayout等控件是一种东西，但是drawerLayout带有滑动的功能。只要按照drawerLayout的规定布局方式写完布局，就能有侧滑的效果。
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /**
         * ActionBarDrawerToggle  是 DrawerLayout.DrawerListener实现。
         和 NavigationDrawer 搭配使用，推荐用这个方法，符合Android design规范。
         作用：
         1.改变android.R.id.home返回图标。
         2.Drawer拉出、隐藏，带有android.R.id.home动画效果。
         3.监听Drawer拉出、隐藏；
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarHelper.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //左边滑出菜单的布局用一个NavigationView来代替
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void initListeners() {
        replaceFragment(R.id.content_frame, RecyclerFragment.newInstance(3));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 设置菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_setting, menu);
        return true;
    }

    /**
     * 菜单item被选中的时候
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑中的item被选中事件
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            replaceFragment(R.id.content_frame, RecyclerFragment.newInstance(3));
        } else if (id == R.id.nav_webview) {
            // Handle the camera action
            replaceFragment(R.id.content_frame, WebViewFragment.newInstance());
        } else if (id == R.id.nav_MaterialDesgin) {
            replaceFragment(R.id.content_frame, CardViewFragment.newInstance());
        } else if (id == R.id.nav_tools) {
            replaceFragment(R.id.content_frame, Install_APK_Fragment.newInstance());
        } else if (id == R.id.nav_toolbar) {
            startActivity(activity,ToolbarActivity.class);
        } else if (id == R.id.nav_qr) {
            replaceFragment(R.id.content_frame, QRFragment.newInstance("二维码"));
        } else if (id == R.id.nav_send) {
            startActivity(activity,GettureLockActivity.class);
        }else if (id==R.id.nav_javascript){
            replaceFragment(R.id.content_frame, JavascriptFragment2.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
