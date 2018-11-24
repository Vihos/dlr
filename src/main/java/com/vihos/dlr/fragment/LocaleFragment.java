package com.vihos.dlr.fragment;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import com.vihos.dlr.holder.ViewsController;
import java.util.Locale;

public abstract class LocaleFragment extends Fragment {
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
}
