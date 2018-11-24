package com.vihos.dlr.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import com.vihos.dlr.Localizable;
import com.vihos.dlr.Utils;

public class LocalizableTextInputLayout extends TextInputLayout implements Localizable {
	private CharSequence originalHint;

	public LocalizableTextInputLayout(Context context) {
		super(context);

		originalHint = getHint();
	}

	public LocalizableTextInputLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		originalHint = getHint();
	}

	public LocalizableTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		originalHint = getHint();
	}

	@Override
	public void setHint(@Nullable CharSequence hint) {
		if (hint != null) {
			CharSequence originalHint = hint;
			hint = Utils.processString(getContext(), hint);

			if (!originalHint.equals(hint))
				this.originalHint = originalHint;
		}

		super.setHint(hint);
	}

	@Override
	public void updateLocales() {
		setHint(originalHint);
	}
}
