package com.vihos.dlr;

import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.view.View;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//public class TextTransformer extends PasswordTransformationMethod {
public class TextTransformer implements TransformationMethod, TextWatcher {

//	private final HashMap<View, ViewMeta> trackedViews;


	@Override
	public CharSequence getTransformation(CharSequence source, View view) {
		System.out.println(source);

		return source;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		System.out.println(s);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		System.out.println(s);
	}

	@Override
	public void afterTextChanged(Editable s) {
		System.out.println(s);
	}

	@Override
	public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction,
		Rect previouslyFocusedRect) {
		System.out.println(sourceText);
	}
}
