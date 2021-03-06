package com.ymangu.slidingmenu.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * .使用Fragment实现SlidingMenu
 * 1.首先Activity继承自SlidingMenu包下的SlidingFragmentActivity
 * 2. setContentView(R.layout.content_frame);//该layout为一个全屏的FrameLayout
 * 3. 设置SlidingMenu使用的布局，同样是一个全屏的FrameLayout
 * 4.设置SlidingMenu左侧菜单的Fragment
 * 
 **/
public class MainActivity extends SlidingFragmentActivity {
	private SlidingMenu sm;
	private Fragment leftFragment;  //默认显示的Menu
	private Fragment contentFragment; //默认显示的 content
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);
		
		//设置SlidingMenu的layout
		setBehindContentView(R.layout.menu_frame);
			
		//设置默认显示的content Fragment
		if (savedInstanceState == null) {
			contentFragment = new Fragment1();
		} else{
			//取出之前保存的contentFragment
			contentFragment = this.getSupportFragmentManager().getFragment(savedInstanceState, "contentFragment");
		}
		//设置当前content 的fragment
				this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, contentFragment)  //contentFragment
				.commit();			
		
		//设置SlidingMenu属性和layout
	   sm = getSlidingMenu();     //得到SlidingMenu的对象
	   sm.setMode(SlidingMenu.LEFT_RIGHT);//设置slidingmenu滑动的方式
	   sm.setShadowDrawable(R.drawable.shadow);//设置slidingmenu边界的阴影图片
	   sm.setShadowWidthRes(R.dimen.shadow_width);//设置阴影的宽度
	   sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//设置slidingmenu宽度
//		sm.setBehindWidth(400);//设置slidingmenu宽度
	   sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑出slidingmenu范围
	   sm.setMenu(R.layout.menu_frame);
	   sm.setBehindCanvasTransformer(canvasTransformer);//设置slidingmenu动画
	   
	     //设置menu的fragment
	 		leftFragment = new LeftFragment();
	 		this.getSupportFragmentManager()  //拿到fragment管理器
	 		.beginTransaction()				//fragment的事物管理
	 		.replace(R.id.menu_frame, leftFragment)  //参数1：layout的id,参数2：要显示的fragment的实例
	 		.commit();		//提交
	 		
	 		
	 		//设置右侧的slidingmenu
			sm.setSecondaryMenu(R.layout.menu_frame_right);
			sm.setSecondaryShadowDrawable(R.drawable.shadowright);
			
	 		//设置右侧滑出的slidingmenu
			RightFragment rightFragment = new RightFragment();
			this.getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.menu_frame_right, rightFragment)
			.commit();
	 		
			//设置在slidingmenu页显示ActionBar
			setSlidingActionBarEnabled(false);
			//设置ActionBar的图标可以被点击
			getSupportActionBar().setHomeButtonEnabled(true);
			//启用向左的图标
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
    // 保存当前的contentFragment
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 1.bundle
		// 2.存放的ID
		// 3.当前要保存的fragment的实例
		this.getSupportFragmentManager().putFragment(outState, "contentFragment", contentFragment);
		
		
		
	}
	
	/**
	 * 切换fragment
	 * @param f
	 */
	public void switchFragment(Fragment f) {
		contentFragment = f;
		this.getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, contentFragment)
		.commit();
		
		sm.toggle();   //让菜单弹回去
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/**
		 * .1. 这里导入的Menu的包是 com.actionbarsherlock.view.Menu
		 *  通过inflate 把xml转换成 menu
		 **/
		getSupportMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	/**
	 * . 捕捉 actionBar 向左的箭头的点击事件
	 **/
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // 这个ID是默认的，不用我们指定
			sm.toggle();// 动态打开或关闭slidingmenu
			break;
		case R.id.action_settings:
			Toast.makeText(this, "Settings", 0).show();
			break;
		case R.id.action_more:
			Toast.makeText(this, "更多", 0).show();
			break;
		default:
			break;

		}

		return true;
	}
	
	/**
	 * 缩放动画
	 */
	private CanvasTransformer canvasTransformer = new CanvasTransformer() {

		@Override
		public void transformCanvas(Canvas canvas, float percentOpen) {
			float scale = (float) (percentOpen*0.25 + 0.75);
			// 以中心点x,y都缩小scale
			canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
		}
	};
	
	/**
	 * 从下往上动画
	 */
private CanvasTransformer canvasTransformer2 = new CanvasTransformer() {
		
		@Override
		public void transformCanvas(Canvas canvas, float percentOpen) {
			//移动到哪个坐标点
			canvas.translate(0, canvas.getHeight()*(1-interp.getInterpolation(percentOpen)));
		}
		
	};
	
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}		
	};
	/**
	 * 拉伸动画
	 */
	private CanvasTransformer canvasTransformer3 = new CanvasTransformer() {
		
		@Override
		public void transformCanvas(Canvas canvas, float percentOpen) {
			canvas.scale(percentOpen, 1, 0, 0);

		}
		
	};
	
	
}
