package com.ymangu.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * . 继承自v4 包下的Fragment，不然只能在4.0以上用
 **/
public class LeftFragment extends BaseFragment implements OnItemClickListener   {

	@Override
	protected View initView(LayoutInflater inflater) {
		//设置fragment的layout
		rootView = inflater.inflate(R.layout.left_frag_lay, null);
		
		return rootView;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		ListView listView = (ListView) rootView.findViewById(R.id.listview);
		List<String> list = initListData();
		 
		//使用 ArrayAdapter 为ListView 适配数据
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), 
				android.R.layout.simple_list_item_1, 
				android.R.id.text1, 
				list);
		listView.setAdapter(adapter);  //设置适配器
		listView.setOnItemClickListener(this);   //设置listView 的item 点击监听事件
		
		
	}
	
	//初始化数据
	private List<String> initListData() {
		List<String> list=new ArrayList<String>();
		// 得到 class 的字节码
		list.add(Fragment1.class.getSimpleName());  
		list.add(Fragment2.class.getSimpleName());  
		list.add(Fragment3.class.getSimpleName());  
		list.add(Fragment4.class.getSimpleName());  
		list.add(Fragment5.class.getSimpleName());  
		
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment f = null;
		switch (position) {  //点击的是哪个
		case 0:
			f = new Fragment1();
			break;
		case 1:
			f = new Fragment2();
			break;
		case 2:
			f = new Fragment3();
			break;
		case 3:
			f = new Fragment4();
			break;
		case 4:
			f = new Fragment5();
			break;
		default:
			f = new Fragment1();
			break;
		
		}
		switchFragment(f);   //跳转到相应的Fragment
		
		
	}
	/**
	 * listview item 点击时切换fragment
	 * @param f
	 */
	private void switchFragment(Fragment f) {
		if (getActivity() instanceof MainActivity) {
			MainActivity activity = (MainActivity) getActivity();   //强转成 MainActivity
			activity.switchFragment(f);     //通知 MainActivity去改变当前要显示的fragment
		}		
		
	}
	
}
