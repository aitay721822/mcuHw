package com.app.myapp;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

import com.app.myapp.R;

public class Animation {

	public interface AnimationListener {
		/**
		 * 當動畫開始時，調用的方法。
		 */
		void onAnimationStart();
		/**
		 * 當動畫結束時，調用的方法。
		 */
		void onAnimationEnd();
	}

	private Handler handler = new Handler(Looper.getMainLooper());

	private AnimationDrawable animDraw;
	private AnimationListener listener;

	public Animation(Context context,ImageView view,@DrawableRes int resoucesId) {
		Drawable drawable = context.getResources().getDrawable(resoucesId);
		if(drawable instanceof AnimationDrawable){
			animDraw = (AnimationDrawable)drawable;
			view.setImageDrawable(animDraw);
		}
	}

	public void start(final int duration){
		if(animDraw == null) return;
		if(listener != null) listener.onAnimationStart();
		new Thread(new Runnable() {
			@Override
			public void run() {
				animDraw.start();
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				animDraw.stop();
				handler.post(new Runnable() {
					@Override
					public void run() {
						if(listener != null) listener.onAnimationEnd();
					}
				});
			}
		}).start();
	}

	public void setListener(AnimationListener listener) {
		this.listener = listener;
	}
}
