package com.mike.virtuallocallife;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.mike.commondata.DegreeDatas;
import com.mike.commondata.commondata;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;
	
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.main_bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(99999999);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener(){

			@Override
			public void onMapStatusChange(MapStatus arg0) {
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				//在同一纬度下,该点离中国区域左下角区域的距离
				double dx = commondata.getDistance(arg0.target.longitude, 
						commondata.leftbottomposy, commondata.leftbottomposx,
						commondata.leftbottomposy);
				//在同一经度下,该点离中国区域左下角区域的距离
				double dy = commondata.getDistance(arg0.target.longitude, 
						arg0.target.latitude, arg0.target.longitude,
						commondata.leftbottomposy);
				
				//根据dx获取x轴方向的索引
				int nIndexX = commondata.GetWestToEastIndex(dx);
				//根据dy获取y轴方向的索引
				int nIndexY = commondata.GetNorthToSouthIndex(dy);
				
				//根据nIndexX与nIndexY计算区域左下角和右边经纬度
				DegreeDatas data1 = commondata.GetDegrees(nIndexX, nIndexY);
				//将左下角的经纬度转换成屏幕像素坐标
				LatLng ln = new LatLng(data1.letfbottomposlat,data1.letfbottomposlon);
				//将右上角的经纬度转换成屏幕像素坐标
				LatLng ln1 = new LatLng(data1.righttopposlat,data1.righttopposlon);
				Point pt = mBaiduMap.getProjection().toScreenLocation(ln);
				Point pt1 = mBaiduMap.getProjection().toScreenLocation(ln1);
				//计算单位区域边框的像素大小
				int nPixel = pt1.x - pt.x;
				Point pttemp = pt;
				//获取要画的直线坐标(总共要画12条直线),从左上角开始
				pt.x = pttemp.x - nPixel;
				pt.y = pttemp.y - 2*nPixel;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y - 2*nPixel;
				//画第一条直线
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第二条直线
				pt.x = pt.x;
				pt.y = pt.y + nPixel;
				pt1.x = pt1.x;
				pt1.y = pt1.y + nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第三条直线
				pt.x = pt.x;
				pt.y = pt.y + nPixel;
				pt1.x = pt1.x;
				pt1.y = pt1.y + nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第四条直线
				pt.x = pt.x;
				pt.y = pt.y + nPixel;
				pt1.x = pt1.x;
				pt1.y = pt1.y + nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第五条直线
				pt.x = pt.x;
				pt.y = pt.y;
				pt1.x = pt.x;
				pt1.y = pt.y - 3*nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第六条直线
				pt.x = pt.x + nPixel;
				pt.y = pt.y;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第七条直线
				pt.x = pt.x + nPixel;
				pt.y = pt.y;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//开始画第八条直线
				pt.x = pt.x + nPixel;
				pt.y = pt.y;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
			}

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				if(mBaiduMap != null){
					mBaiduMap.clear();	
				}
			}
			
		});
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void DrawLine(LatLng ln1,LatLng ln2){
		List<LatLng> points = new ArrayList<LatLng>();
		points.add(ln1);
		points.add(ln2);
		OverlayOptions ooPolyline = new PolylineOptions().width(10)
				.color(0xAAFF0000).points(points);
		mBaiduMap.addOverlay(ooPolyline);
	}
	
	@Override
	protected void onPause() {
		//mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		//mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
