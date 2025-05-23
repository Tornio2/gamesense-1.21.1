package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget;

import java.util.function.Supplier;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.Context;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.component.FocusableComponent;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.ILabeled;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.theme.IButtonRenderer;

/**
 * Button widget class.
 * @author lukflug
 */
public class Button<T> extends FocusableComponent {
	/**
	 * The button state supplier.
	 */
	protected Supplier<T> state;
	/**
	 * Renderer for this component.
	 */
	protected IButtonRenderer<T> renderer;

	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param state the button state supplier
	 * @param renderer the renderer for this component
	 */
	public Button (ILabeled label, Supplier<T> state, IButtonRenderer<T> renderer) {
		super(label);
		this.renderer=renderer;
		this.state=state;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),hasFocus(context),state.get());
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
