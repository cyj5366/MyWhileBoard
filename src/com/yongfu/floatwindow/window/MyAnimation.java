package com.yongfu.floatwindow.window;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;;
public class MyAnimation {
	//�붯��
	public static void startAnimationIN(ViewGroup viewGroup, int duration){
		for(int i = 0; i < viewGroup.getChildCount(); i++ ){
			viewGroup.getChildAt(i).setVisibility(View.VISIBLE);//������ʾ
			viewGroup.getChildAt(i).setFocusable(true);//��ý���
			viewGroup.getChildAt(i).setClickable(true);//���Ե��
		}
		
		Animation animation;
		/**
		 * 旋转动画
		 * RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue)
		 * fromDegrees 开始旋转角度
		 * toDegrees 旋转到的角度
		 * pivotXType X轴 参照物
		 * pivotXValue x轴 旋转的参考点
		 * pivotYType Y轴 参照物
		 * pivotYValue Y轴 旋转的参考点
		 */
		animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setFillAfter(true);//ͣ���ڶ�������λ��
		animation.setDuration(duration);
		
		viewGroup.startAnimation(animation);
		
	}
	
	//������
	public static void startAnimationOUT(final ViewGroup viewGroup, int duration , int startOffSet){
		Animation animation;
		//animation = new ScaleAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
			
		animation.setFillAfter(true);//ͣ���ڶ�������λ��
		animation.setDuration(duration);
		animation.setStartOffset(startOffSet);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				for(int i = 0; i < viewGroup.getChildCount(); i++ ){
					viewGroup.getChildAt(i).setVisibility(View.GONE);//������ʾ
					viewGroup.getChildAt(i).setFocusable(false);//��ý���
					viewGroup.getChildAt(i).setClickable(false);//���Ե��
				}
				
			}
		});
		
		viewGroup.startAnimation(animation);
	}
}
