package com.vihos.dlr.holder;

import android.view.View;
import android.widget.TextView;
import com.vihos.dlr.Utils;
import com.vihos.dlr.model.ViewMeta;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewsController {

	private final HashMap<View, ViewMeta> trackedViews = new HashMap<>();

	private static ViewsController viewsController = null;

	public static ViewsController getInstance() {
		if (viewsController == null)
			viewsController = new ViewsController();

		return viewsController;
	}

	public void updateLocale() {
		for (Map.Entry<View, ViewMeta> entry : trackedViews.entrySet()) {
			View view = entry.getKey();
			ViewMeta meta = entry.getValue();

			if (view instanceof TextView) {
				TextView textView = (TextView) view;

				if (meta.getOriginalText() != null) {
					String processedText = Utils.processString(view.getContext(), meta.getOriginalText());
					textView.setText(processedText);
				}
			}
		}
	}

	public ViewMeta get(View view) {
		return trackedViews.get(view);
	}

	public void put(View view, ViewMeta meta) {
		trackedViews.put(view, meta);
	}

	public ViewMeta remove(View view) {
		return trackedViews.remove(view);
	}
}
