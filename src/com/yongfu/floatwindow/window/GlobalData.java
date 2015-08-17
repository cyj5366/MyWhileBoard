package com.yongfu.floatwindow.window;

import android.app.Application;

public class GlobalData extends Application {
	private int i;
	private Boolean bVisible = false;
	private Boolean bDownOrUp = false;	
	public int getValue(){
		return i;
	}
	
	public void setValue(int value){
		this.i = value;
	}		
	
	public Boolean getBoolValue(){
		return bVisible;
	}
	
	public void setBoolValue(Boolean value){
		this.bVisible = value;
	}
	
	
	public Boolean getDownOrUpValue(){
		return bDownOrUp;
	}
	
	public void setDownOrUpValue(Boolean value){
		this.bDownOrUp = value;
	}	
	
}
