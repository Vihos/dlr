package com.vihos.dlr;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import com.vihos.dlr.function.ViewAttached;
import com.vihos.dlr.function.ViewDetached;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class HierarchyWatcher implements OnHierarchyChangeListener {

	@Getter
	private List<ViewAttached> viewAttachedHandlers = new ArrayList<>();

	@Getter
	private List<ViewDetached> viewDetachedHandlers = new ArrayList<>();

	@Override
	public void onChildViewAdded(View parent, View child) {
		// Call event for new attached view
		callViewAttachedHandlers(parent, child);

		// If there is ViewGroup call recursive parsing for it
		if (child instanceof ViewGroup) {
			// Attach events and recursively walk through all objects in group
			recursiveEventsAttach((ViewGroup) child);
		}
	}

	@Override
	public void onChildViewRemoved(View parent, View child) {
		callViewDetachedHandlers(parent, child);

		if (child instanceof ViewGroup)
			recursiveEventsDetach((ViewGroup) child);
	}

	private void recursiveEventsAttach(ViewGroup viewGroup) {
		// Attach watcher to this ViewGroup
		// Watcher will watch ViewGroup only for depth 1. That's why changes in child ViewGroup
		// will be lost iw we will not attach watcher to them.
		viewGroup.setOnHierarchyChangeListener(this);

		// Go through all child's and check for another ViewGroup as child.
		// Also we will call attachView events because this method will be called only for not parsed viewGroups.
		// That means that all child's in new viewGroup not processed.
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View childView = viewGroup.getChildAt(i);

			// Call event handlers
			callViewAttachedHandlers(viewGroup, childView);

			// If there are another ViewGroup call recursive parsing for it
			if (childView instanceof ViewGroup)
				recursiveEventsAttach((ViewGroup) childView);
		}
	}

	private void recursiveEventsDetach(ViewGroup viewGroup) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View childView = viewGroup.getChildAt(i);

			// Call event handlers
			callViewDetachedHandlers(viewGroup, childView);

			// If there are another ViewGroup call recursive parsing for it
			if (childView instanceof ViewGroup)
				recursiveEventsDetach((ViewGroup) childView);
		}
	}

	/**
	 * Handlers controls
	 */

	public void callViewAttachedHandlers(View parent, View view) {
		for (ViewAttached handler : viewAttachedHandlers) {
			try {
				handler.consume(parent, view);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void callViewDetachedHandlers(View parent, View view) {
		for (ViewDetached handler : viewDetachedHandlers) {
			try {
				handler.consume(parent, view);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public HierarchyWatcher addOnViewAttached(ViewAttached handler) {
		viewAttachedHandlers.add(handler);

		return this;
	}

	public HierarchyWatcher addOnViewAttached(int index, ViewAttached handler) {
		viewAttachedHandlers.add(index, handler);

		return this;
	}

	public HierarchyWatcher removeOnViewAttached(ViewAttached handler) {
		viewAttachedHandlers.remove(handler);

		return this;
	}

	public HierarchyWatcher addOnViewDetached(ViewDetached handler) {
		viewDetachedHandlers.add(handler);

		return this;
	}

	public HierarchyWatcher addOnViewDetached(int index, ViewDetached handler) {
		viewDetachedHandlers.add(index, handler);

		return this;
	}

	public HierarchyWatcher removeOnViewDetached(ViewDetached handler) {
		viewDetachedHandlers.remove(handler);

		return this;
	}
}
