package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.popup;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IInterface;

/**
 * Static pop-up positioner that positions the pop-up at a fixed position relative to the mouse pointer.
 * @author lukflug
 */
public class MousePositioner implements IPopupPositioner {
	/**
	 * The offset.
	 */
	protected Point offset;
	
	/**
	 * Constructor.
	 * @param offset the offset relative to the current cursor position
	 */
	public MousePositioner (Point offset) {
		this.offset=offset;
	}

	@Override
	public Point getPosition (IInterface inter, Dimension popup, Rectangle component, Rectangle panel) {
		Point pos=inter.getMouse();
		pos.translate(offset.x,offset.y);
		return pos;
	}
}
