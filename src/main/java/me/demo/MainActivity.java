package me.demo;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.github.ipcjs.coordtransform.CoordinateTransformUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by ipcjs on 2017/9/29.
 */

public class MainActivity extends AppCompatActivity {

    public static final CoordinateTransformUtil.Rectangle[] RECTANGLES = {
            new CoordinateTransformUtil.Rectangle(72.004, 0.8293, 137.8347, 55.8271)
    };

    public static final String TAG_MAP = "map";

    private BaiduMap mBaiduMap;

    private SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.GCJ02);

        mMapFragment = (SupportMapFragment) fm.findFragmentByTag(TAG_MAP);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance(new BaiduMapOptions()
                    .mapStatus(new MapStatus.Builder().zoom(2).build())
            );
            fm.beginTransaction()
                    .add(android.R.id.content, mMapFragment, TAG_MAP)
                    .commitNow(); // 立即提交
        }
        getWindow().getDecorView().post(this::initMap); // 下个事件循环中才能读取到MapView
    }

    private void initMap() {
        mBaiduMap = mMapFragment.getBaiduMap();
        drawRectangle(CoordinateTransformUtil.REGION, Color.BLUE); // 属于中国的范围
        drawRectangle(CoordinateTransformUtil.EXCLUDE, Color.RED); // 不属于中国的范围
        drawRectangle(RECTANGLES, Color.YELLOW); // 属于中国的范围
    }

    private void drawRectangle(CoordinateTransformUtil.Rectangle[] rectangles, int color) {
        for (CoordinateTransformUtil.Rectangle r : rectangles) {
            ArrayList<LatLng> points = new ArrayList<>();
            points.add(new LatLng(r.north, r.west));
            points.add(new LatLng(r.north, r.east));
            points.add(new LatLng(r.south, r.east));
            points.add(new LatLng(r.south, r.west));
            points.add(points.get(0)); // 闭合 线
            mBaiduMap.addOverlay(new PolylineOptions().points(points).color(color));
        }
    }
}
