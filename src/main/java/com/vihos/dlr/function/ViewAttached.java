package com.vihos.dlr.function;

import android.view.View;

@FunctionalInterface
public interface ViewAttached {
	void consume(View parent, View view);
}
