package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.hud;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.Animation;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IBoolean;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IInterface;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.IToggleable;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.component.IFixedComponent;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.container.GUI;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.popup.IPopupPositioner;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.theme.IDescriptionRenderer;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.theme.ITheme;

/**
 * Extension of {@link GUI} to support HUD components.
 * @author lukflug
 */
public class HUDGUI extends GUI {
	/**
	 * Whether the ClickGUI is visible.
	 */
	protected IToggleable guiVisibility;
	/**
	 * Whether the HUD panels are visible.
	 */
	protected IToggleable hudVisibility;
	
	/**
	 * Constructor.
	 * @param inter the {@link IInterface} to be used by the GUI
	 * @param descriptionRenderer the {@link IDescriptionRenderer} used by the GUI
	 * @param descriptionPosition the static {@link IPopupPositioner} to be used to position the descriptions
	 * @param guiVisibility predicate for ClickGUI visibility
	 * @param hudVisibility predicate for HUDEdior visibility
	 */
	public HUDGUI(IInterface inter, IDescriptionRenderer descriptionRenderer, IPopupPositioner descriptionPosition, IToggleable guiVisibility, IToggleable hudVisibility) {
		super(inter,descriptionRenderer,descriptionPosition);
		this.guiVisibility=guiVisibility;
		this.hudVisibility=hudVisibility;
	}
	
	@Override
	public boolean addComponent (IFixedComponent component) {
		return container.addComponent(component,guiVisibility);
	}

	@Override
	public boolean addComponent (IFixedComponent component, IBoolean visible) {
		return container.addComponent(component,()->guiVisibility.isOn()&&visible.isOn());
	}
	
	/**
	 * Add a component as HUD component.
	 * @param component the component to be added
	 * @param visible the external visibility of the component
	 * @return whether the component was added
	 */
	public boolean addHUDComponent (IFixedComponent component, IBoolean visible) {
		return container.addComponent(component,visible);
	}
	
	/**
	 * Add a component wrapped in a {@link HUDPanel} as HUD component.
	 * @param component the component to be added
	 * @param state the boolean state to be passed to the theme
	 * @param animation the animation to be used for opening and closing
	 * @param theme the theme for the panel
	 * @param border the component border
	 * @return whether the component was added
	 */
	public boolean addHUDComponent (IFixedComponent component, IToggleable state, Animation animation, ITheme theme, int border) {
		return container.addComponent(new HUDPanel<IFixedComponent>(component,state,animation,theme,hudVisibility,border),()->true);
	}
	
	/**
	 * Get ClickGUI visibility predicate.
	 * @return whether the ClickGUI is visible
	 */
	public IToggleable getGUIVisibility() {
		return guiVisibility;
	}
	
	/**
	 * Get HUDEditor visibility predicate.
	 * @return whether the ClickGUI is visible
	 */
	public IToggleable getHUDVisibility() {
		return hudVisibility;
	}
}
