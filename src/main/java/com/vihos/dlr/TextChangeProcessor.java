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

	private String prevText = "";

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		long current = System.nanoTime();

		String newText = s.toString();

		if (prevText.equals(newText))
			return;

		prevText = newText;
		viewMeta.setOriginalText(newText);

		String processedString = Utils.processString(textView.getContext(), newText);
		textView.setText(processedString);

		long result = System.nanoTime() - current;
		System.out.println(result / 1000 + "ms " + result + "ns");
	}
}
