package com.vihos.dlr.function;

import android.view.View;

@FunctionalInterface
public interface ViewDetached {
	void consume(View parent, View view);
}
