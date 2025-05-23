package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.popup;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IInterface;

/**
 * Static pop-up positioner, that positions the pop-up on the side of the panel.
 * @author lukflug
 */
public class PanelPositioner implements IPopupPositioner {
	/**
	 * The offset.
	 */
	protected Point offset;
	
	/**
	 * Constructor.
	 * @param offset the offset relative to the current cursor position
	 */
	public PanelPositioner (Point offset) {
		this.offset=offset;
	}

	@Override
	public Point getPosition (IInterface inter, Dimension popup, Rectangle component, Rectangle panel) {
		return new Point(panel.x+panel.width+offset.x,component.y+offset.y);
	}
}
