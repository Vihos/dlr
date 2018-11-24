package com.vihos.dlr;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.vihos.dlr.model.ViewMeta;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextChangeProcessor implements TextWatcher {
	@NonNull
	private final TextView textView;

	@NonNull
	private final ViewMeta viewMeta;

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		String string = s.toString().trim();

		if (Utils.isLocalizable(string)) {
			String processedString = Utils.processString(textView.getContext(), string);

			if (processedString.equals(string))
				return;

			viewMeta.setOriginalText(string);
			s.replace(0, s.length(), processedString);
		}
	}
}
