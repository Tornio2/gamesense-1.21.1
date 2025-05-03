package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.component;


import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IInterface;

/**
 * Combination of {@link IComponentProxy} and {@link IHorizontalComponent}.
 * @author lukflug
 * @param <T> the component type
 */
@FunctionalInterface
public interface IHorizontalComponentProxy<T extends IHorizontalComponent> extends IComponentProxy<T>,IHorizontalComponent {
	@Override
	public default int getWidth (IInterface inter) {
		return getComponent().getWidth(inter);
	}
	
	@Override
	public default int getWeight() {
		return getComponent().getWeight();
	}
}
