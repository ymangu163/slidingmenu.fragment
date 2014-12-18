package com.ymangu.slidingmenu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Fragment4 extends BaseFragment {

	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.freg_text, null);//设置fragment的layout
		TextView tv = (TextView) view.findViewById(R.id.text);
		tv.setText(Fragment4.class.getSimpleName());
		
		return view;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}

}
