package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.theme;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 * @param <T> type representing state of scroll bar
 */
@FunctionalInterface
public interface IScrollBarRendererProxy<T> extends IScrollBarRenderer<T>,IContainerRendererProxy {
	@Override
	public default int renderScrollBar (Context context, boolean focus, T state, boolean horizontal, int height, int position) {
		return getRenderer().renderScrollBar(context,focus,state,horizontal,height,position);
	}

	@Override
	public default int getThickness() {
		return getRenderer().getThickness();
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IScrollBarRenderer<T> getRenderer();
}
