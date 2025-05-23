package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.theme;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.Context;

/**
 * Interface abstracting the rendering of a panel.
 * @author lukflug
 * @param <T> type representing state of the panel
 */
public interface IPanelRenderer<T> extends IContainerRenderer {
	/**
	 * Render the outline of a panel.
	 * @param context the context to be used
	 * @param focus the focus state of the panel
	 * @param state the state of the panel
	 * @param open whether the panel is open or collapsed
	 */
	public void renderPanelOverlay (Context context, boolean focus, T state, boolean open);
	
	/**
	 * Render the overlay of a panel title bar.
	 * @param context the context to be used
	 * @param focus the focus state of the panel
	 * @param state the state of the panel
	 * @param open whether the panel is open or collapsed
	 */
	public void renderTitleOverlay (Context context, boolean focus, T state, boolean open);
}
