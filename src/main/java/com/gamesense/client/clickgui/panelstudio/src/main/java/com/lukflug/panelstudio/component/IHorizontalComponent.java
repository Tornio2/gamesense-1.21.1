package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.component;


import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IInterface;

/**
 * Component to be included in horizontal containers.
 * @author lukflug
 */
public interface IHorizontalComponent extends IComponent {
	/**
	 * Get the component width.
	 * @param inter current interface
	 * @return component width
	 */
	public int getWidth (IInterface inter);
	
	/**
	 * Get the component weight.
	 * @return component weight
	 */
	public int getWeight();
}
