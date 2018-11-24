package com.vihos.dlr.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vihos.dlr.HierarchyWatcher;
import com.vihos.dlr.TextChangeProcessor;
import com.vihos.dlr.holder.ViewsController;
import com.vihos.dlr.model.ViewMeta;
import java.util.Locale;

public abstract class LocaleAppCompatActivity extends AppCompatActivity {

	private ViewsController viewsController = ViewsController.getInstance();

	protected final void setLanguage(String language) {
		Locale locale = new Locale(language);
		Locale.setDefault(locale);

		Configuration config = new Configuration();
		config.locale = locale;

		Resources resources = getResources();
		resources.updateConfiguration(config, resources.getDisplayMetrics());

		viewsController.updateLocale();
	}

	protected void onTranslationStart() {

	}

	protected void onTranslationEnd() {

	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		onTranslationStart();

		// Get window root ViewGroup
		ViewGroup windowRootViewGroup = (ViewGroup) getWindow().getDecorView().getRootView();

		// Get activity root ViewGroup to attach recursive history change event
		ViewGroup rootActivityViewGroup = findViewById(android.R.id.content);

		HierarchyWatcher hierarchyWatcher = new HierarchyWatcher();
		hierarchyWatcher.addOnViewAttached((parent, view) -> {
			Log.d("TAG", String.format("Child `%s` attached to `%s`", view, parent));

			ViewMeta viewMeta = viewsController.get(view);

			if (viewMeta == null) {
				viewMeta = new ViewMeta();
				viewsController.put(view, viewMeta);
			}

			if (view instanceof TextView) {
				TextView textView = (TextView) view;

				viewMeta.setOriginalText(textView.getText().toString());
				textView.addTextChangedListener(new TextChangeProcessor(textView, viewMeta));

				// Initialize text processing event
				textView.setText(textView.getText());
				textView.setHint(textView.getHint());
			}
		});

		hierarchyWatcher.addOnViewDetached((parent, view) -> {
			Log.d("TAG", String.format("Child `%s` detached from `%s`", view, parent));

			viewsController.remove(view);
		});

		windowRootViewGroup.setOnHierarchyChangeListener(hierarchyWatcher);
		rootActivityViewGroup.setOnHierarchyChangeListener(hierarchyWatcher);

		onTranslationEnd();
	}
}
