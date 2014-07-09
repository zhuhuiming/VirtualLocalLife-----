package com.mike.commondata;

public class DegreeDatas{
	
	public DegreeDatas(double x1,double y1,double x2,double y2){
		letfbottomposlon = x1;
		letfbottomposlat = y1;
		righttopposlon = x2;
		righttopposlat = y2;
	}
	//左下角经纬度
	public static double letfbottomposlon = 0;//经度
	public static double letfbottomposlat = 0;//纬度
	
	//右上角经纬度
	public static double righttopposlon = 0;//经度
	public static double righttopposlat = 0;//纬度
}
