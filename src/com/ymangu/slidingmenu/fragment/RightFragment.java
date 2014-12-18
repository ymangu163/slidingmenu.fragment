package com.ymangu.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RightFragment extends BaseFragment {

	@Override
	protected View initView(LayoutInflater inflater) {
		rootView = inflater.inflate(R.layout.left_frag_lay, null);//设置fragment的layout
		
		return rootView;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		ListView lv = (ListView) rootView.findViewById(R.id.listview);
		List<String> list = initListData();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), 
				android.R.layout.simple_list_item_1, 
				android.R.id.text1, 
				list);
		lv.setAdapter(adapter);
	}
	
	/**
	 * 初始化数据
	 * @return
	 */
	private List<String> initListData() {
		List<String> list = new ArrayList<String>();
		list.add(Fragment1.class.getSimpleName());
		list.add(Fragment2.class.getSimpleName());
		list.add(Fragment3.class.getSimpleName());
		list.add(Fragment4.class.getSimpleName());
		list.add(Fragment5.class.getSimpleName());
		return list;
		
		
	}

}
