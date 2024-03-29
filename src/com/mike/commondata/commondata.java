package com.mike.commondata;

public class commondata {
	// 中国地图左上角经纬度
	public static final double lefttopposx = 73.55;
	public static final double lefttopposy = 53.55;
	// 中国地图右上角经纬度
	public static final double righttopposx = 135.083;
	public static final double righttopposy = 53.55;
	// 中国地图左下角经纬度
	public static final double leftbottomposx = 73.55;
	public static final double leftbottomposy = 3.85;
	// 中国地图右下角经纬度
	public static final double rightbottomposx = 135.083;
	public static final double rightbottomposy = 3.85;

	// 地球半径(千米)
	public final static double EARTH_RADIUS_KM = 6378.137;

	// 单位长度(千米)
	public final static double UNIT_LENGHT = 3;

	// 中国东西宽度
	public final static double WESTTOEAST = 3939.1562;
	// 中国南北长度
	public final static double SOUTHTONORTH = 5532.5787;
	// pi的值
	public final static double PI = 3.1415926;

	public static double getDistance(double lng1, double lat1, double lng2,
			double lat2) {
		
		double radLat1 = Math.toRadians(lat1);

		double radLat2 = Math.toRadians(lat2);

		double radLng1 = Math.toRadians(lng1);

		double radLng2 = Math.toRadians(lng2);

		double deltaLat = radLat1 - radLat2;

		double deltaLng = radLng1 - radLng2;

		double distance = 2 * Math.asin(Math.sqrt(Math.pow(

		Math.sin(deltaLat / 2), 2)

		+ Math.cos(radLat1)

		* Math.cos(radLat2)

		* Math.pow(Math.sin(deltaLng / 2), 2)));

		distance = distance * EARTH_RADIUS_KM;

		long nvalue = Math.round(distance * 10000);
		distance = (double) nvalue / 10000;

		return distance;

	}

	// 根据东西方向的距离差计算该方向上区域块的索引值(从1开始)
	public static int GetWestToEastIndex(double dDistance) {
		int nIndex = 1;
		int nTemp = (int) ((dDistance * 1000) % (UNIT_LENGHT * 1000));
		// 如果有余数
		if (nTemp > 0) {
			nIndex = (int) (dDistance / UNIT_LENGHT + 1);
		} else {
			nIndex = (int) (dDistance / UNIT_LENGHT);
		}
		return nIndex;
	}

	// 根据南北方向的距离差计算该方向上区域块的索引值(从1开始)
	public static int GetNorthToSouthIndex(double dDistance) {
		int nIndex = 1;
		int nTemp = (int) ((dDistance * 1000) % (UNIT_LENGHT * 1000));
		// 如果有余数
		if (nTemp > 0) {
			nIndex = (int) (dDistance / UNIT_LENGHT + 1);
		} else {
			nIndex = (int) (dDistance / UNIT_LENGHT);
		}
		return nIndex;
	}

	// 根据区域块索引号左下角与右上角的经纬度(其中nx表示x轴索引号,ny表示y轴索引号)
	public static DegreeDatas GetDegrees(double nx, double ny) {
		DegreeDatas data = new DegreeDatas(0, 0, 0, 0);
		DegreeDatas.letfbottomposlon = 180 * nx * UNIT_LENGHT
				/ (PI * EARTH_RADIUS_KM) + leftbottomposx;
		DegreeDatas.letfbottomposlat = 180 * ny * UNIT_LENGHT
				/ (PI * EARTH_RADIUS_KM) + leftbottomposy;
		DegreeDatas.righttopposlon = 180 * (nx + 1) * UNIT_LENGHT
				/ (PI * EARTH_RADIUS_KM) + leftbottomposx;
		DegreeDatas.righttopposlat = 180 * ny * UNIT_LENGHT
				/ (PI * EARTH_RADIUS_KM) + leftbottomposy;
		return data;
	}
}
