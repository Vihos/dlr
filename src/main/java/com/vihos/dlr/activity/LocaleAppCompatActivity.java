package com.vihos.dlr.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vihos.dlr.HierarchyWatcher;
import com.vihos.dlr.TextChangeProcessor;
import com.vihos.dlr.model.ViewMeta;
import java.util.HashMap;

public abstract class LocaleAppCompatActivity extends AppCompatActivity {
	private HashMap<View, ViewMeta> trackedViews = new HashMap<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get window root ViewGroup
		ViewGroup windowRootViewGroup = (ViewGroup) getWindow().getDecorView().getRootView();

		// Get activity root ViewGroup to attach recursive history change event
		ViewGroup rootActivityViewGroup = findViewById(android.R.id.content);

		HierarchyWatcher hierarchyWatcher = new HierarchyWatcher();
		hierarchyWatcher.addOnViewAttached((parent, view) -> {
			Log.d("TAG", String.format("Child `%s` attached to `%s`", view, parent));

			ViewMeta viewMeta = trackedViews.get(view);

			if (viewMeta == null) {
				viewMeta = new ViewMeta();
				trackedViews.put(view, viewMeta);
			}

			if (view instanceof TextView) {
				TextView textView = (TextView) view;

				viewMeta.setOriginalText(textView.getText().toString());
				textView.addTextChangedListener(new TextChangeProcessor(textView, viewMeta));
//				textView.setTransformationMethod(new TextTransformer());

				// Initialize text processing event
				textView.setText(textView.getText());
			}

			System.out.println(trackedViews);
//			Log.d("TAG", String.format("Current tracked objects count: %d", trackedViews.size()));
		});

		hierarchyWatcher.addOnViewDetached((parent, view) -> {
			Log.d("TAG", String.format("Child `%s` detached from `%s`", view, parent));

			trackedViews.remove(view);
		});

//		windowRootViewGroup.setOnHierarchyChangeListener(hierarchyWatcher);
		rootActivityViewGroup.setOnHierarchyChangeListener(hierarchyWatcher);

		// Attach new child's listener
//		viewGroup.setOnHierarchyChangeListener(new HierarchyWatcher((text) -> {
////			System.out.println(text);
//
//			if (text.startsWith("#")) {
//				int slashIndex = text.indexOf("/");
//
//				if (slashIndex == -1)
//					return text;
//
//				String type = text.substring(1, slashIndex);
//				String resourceName = text.substring(slashIndex + 1);
//
//				int id = getResources().getIdentifier(resourceName, type, getPackageName());
//
//				if (id != 0) {
//					return getResources().getString(id);
////					System.out.println(type);
////					System.out.println(resourceName);
////					System.out.println(text);
////					System.out.println(id);
//				}
//			}
//
//			return text;
//		}));
	}
}
