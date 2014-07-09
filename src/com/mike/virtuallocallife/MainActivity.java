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

	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;
	
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);

		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.main_bmapView);
		mBaiduMap = mMapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(99999999);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener(){

			@Override
			public void onMapStatusChange(MapStatus arg0) {
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				//��ͬһγ����,�õ����й��������½�����ľ���
				double dx = commondata.getDistance(arg0.target.longitude, 
						commondata.leftbottomposy, commondata.leftbottomposx,
						commondata.leftbottomposy);
				//��ͬһ������,�õ����й��������½�����ľ���
				double dy = commondata.getDistance(arg0.target.longitude, 
						arg0.target.latitude, arg0.target.longitude,
						commondata.leftbottomposy);
				
				//����dx��ȡx�᷽�������
				int nIndexX = commondata.GetWestToEastIndex(dx);
				//����dy��ȡy�᷽�������
				int nIndexY = commondata.GetNorthToSouthIndex(dy);
				
				//����nIndexX��nIndexY�����������½Ǻ��ұ߾�γ��
				DegreeDatas data1 = commondata.GetDegrees(nIndexX, nIndexY);
				//�����½ǵľ�γ��ת������Ļ��������
				LatLng ln = new LatLng(data1.letfbottomposlat,data1.letfbottomposlon);
				//�����Ͻǵľ�γ��ת������Ļ��������
				LatLng ln1 = new LatLng(data1.righttopposlat,data1.righttopposlon);
				Point pt = mBaiduMap.getProjection().toScreenLocation(ln);
				Point pt1 = mBaiduMap.getProjection().toScreenLocation(ln1);
				//���㵥λ����߿�����ش�С
				int nPixel = pt1.x - pt.x;
				Point pttemp = pt;
				//��ȡҪ����ֱ������(�ܹ�Ҫ��12��ֱ��),�����Ͻǿ�ʼ
				pt.x = pttemp.x - nPixel;
				pt.y = pttemp.y - 2*nPixel;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y - 2*nPixel;
				//����һ��ֱ��
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ���ڶ���ֱ��
				pt.x = pt.x;
				pt.y = pt.y + nPixel;
				pt1.x = pt1.x;
				pt1.y = pt1.y + nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ��������ֱ��
				pt.x = pt.x;
				pt.y = pt.y + nPixel;
				pt1.x = pt1.x;
				pt1.y = pt1.y + nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ��������ֱ��
				pt.x = pt.x;
				pt.y = pt.y + nPixel;
				pt1.x = pt1.x;
				pt1.y = pt1.y + nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ��������ֱ��
				pt.x = pt.x;
				pt.y = pt.y;
				pt1.x = pt.x;
				pt1.y = pt.y - 3*nPixel;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ��������ֱ��
				pt.x = pt.x + nPixel;
				pt.y = pt.y;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ��������ֱ��
				pt.x = pt.x + nPixel;
				pt.y = pt.y;
				pt1.x = pt1.x + nPixel;
				pt1.y = pt1.y;
				DrawLine(mBaiduMap.getProjection().fromScreenLocation(pt),
						mBaiduMap.getProjection().fromScreenLocation(pt1));
				
				//��ʼ���ڰ���ֱ��
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
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
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
		// �˳�ʱ���ٶ�λ
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
