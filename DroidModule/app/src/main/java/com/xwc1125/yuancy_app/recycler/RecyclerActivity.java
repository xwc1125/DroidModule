package com.xwc1125.yuancy_app.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xwc1125.droidmodule.R;
import com.xwc1125.yuancy_app.recycler.bean.NameBean;
import com.xwc1125.yuancy_app.recycler.bean.WaitMVBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class RecyclerActivity extends AppCompatActivity {
    private Context mContext;
    List<WaitMVBean.DataBean.ComingBean> comingslist;
    List<NameBean> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_recycler2);
        getDataFromNet();
    }

    private void setPullAction(List<WaitMVBean.DataBean.ComingBean> comingslist) {
        dataList = new ArrayList<NameBean>();

        for (int i = 0; i < comingslist.size(); i++) {
            NameBean nameBean = new NameBean();
            String name0 = comingslist.get(i).getComingTitle();
            nameBean.setName(name0);
            dataList.add(nameBean);
        }
    }

    /**
     * 联网请求所需的url
     */
    public String url = "http://api.meituan.com/mmdb/movie/v2/list/rt/order/coming.json?ci=1&limit=12&token=&__vhost=api.maoyan.com&utm_campaign=AmovieBmovieCD-1&movieBundleVersion=6801&utm_source=xiaomi&utm_medium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a375bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__skno=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D";

    /**
     * 使用okhttpUtils进行联网请求数据
     */
    private void getDataFromNet() {
        OkHttpUtils.
                get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);

                        //联网成功后使用fastjson解析
                        processData(response);
                    }
                });
    }

    /**
     * 使用fastjson进行解析
     *
     * @param json
     */
    private void processData(String json) {
        //这里使用GsonFormat生成对应的bean类
        JSONObject jsonObject = parseObject(json);

        String data = jsonObject.getString("data");
        JSONObject dataObj = JSON.parseObject(data);

        String coming = dataObj.getString("coming");
        comingslist = parseArray(coming, WaitMVBean.DataBean.ComingBean.class);
        //解析数据成功,设置适配器-->
        setPullAction(comingslist);


        RecyclerView list = (RecyclerView) findViewById(R.id.listview);
        //【xwc1125】所有的item放在GridLayout中
        GridLayoutManager mg = new GridLayoutManager(this, 1);//格子摆放
        //交错性的摆放，有点win8那种格子风格，最好使用CardView作为item，有边框和圆角
        //StaggeredGridLayoutManager mg = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(mg);
        list.addItemDecoration(new SectionDecoration(dataList, mContext, new SectionDecoration.DecorationCallback() {
            //返回标记id (即每一项对应的标志性的字符串)
            @Override
            public String getGroupId(int position) {
                if (dataList.get(position).getName() != null) {
                    return dataList.get(position).getName();
                }
                return "-1";
            }

            //获取同组中的第一个内容
            @Override
            public String getGroupFirstLine(int position) {
                if (dataList.get(position).getName() != null) {
                    return dataList.get(position).getName();
                }
                return "";
            }
        }));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, comingslist);
        list.setAdapter(adapter);

        //设置事件
        RecyclerView.ItemDecoration decoration = new SectionDecoration(dataList, mContext, new SectionDecoration.DecorationCallback() {

            @Override
            public String getGroupId(int position) {
                return null;
            }

            @Override
            public String getGroupFirstLine(int position) {
                return null;
            }
        });
        list.addItemDecoration(decoration);
    }

}
