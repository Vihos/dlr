package com.vihos.dlr.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LocaleFragment extends Fragment {

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

//		System.out.println("========LocaleAppCompatActivity========");
//		System.out.println(view);
//		System.out.println(savedInstanceState);
//		System.out.println("=======================================");
	}




	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {

//		System.out.println("=======LocaleFragment=======");
//		System.out.println(inflater);
//		System.out.println(container);
//		System.out.println(savedInstanceState);
//		System.out.println("============================");

		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
