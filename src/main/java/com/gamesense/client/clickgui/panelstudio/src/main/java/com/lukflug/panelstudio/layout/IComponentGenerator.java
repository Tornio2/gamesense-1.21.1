package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.layout;

import java.util.function.Supplier;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.Animation;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.SimpleToggleable;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.component.IComponent;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.IBooleanSetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.IColorSetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.IEnumSetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.IKeybindSetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.INumberSetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.ISetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting.IStringSetting;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.theme.ThemeTuple;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.Button;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.ColorSliderComponent;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.CycleButton;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.ITextFieldKeys;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.KeybindComponent;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.NumberSlider;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.TextField;
import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.widget.ToggleButton;

/**
 * Interface defining what components to use for settings.
 * @author lukflug
 */
public interface IComponentGenerator {
	/**
	 * Get component from a given setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getComponent (ISetting<?> setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		if (setting instanceof IBooleanSetting) {
			return getBooleanComponent((IBooleanSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof INumberSetting) {
			return getNumberComponent((INumberSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IEnumSetting) {
			return getEnumComponent((IEnumSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IColorSetting) {
			return getColorComponent((IColorSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IKeybindSetting) {
			return getKeybindComponent((IKeybindSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IStringSetting) {
			return getStringComponent((IStringSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else {
			return new Button<Void>(setting,()->null,theme.getButtonRenderer(Void.class,isContainer));
		}
	}
	
	/**
	 * Get component from a given boolean setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getBooleanComponent (IBooleanSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new ToggleButton(setting,theme.getButtonRenderer(Boolean.class,isContainer));
	}
	
	/**
	 * Get component from a given number setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getNumberComponent (INumberSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new NumberSlider(setting,theme.getSliderRenderer(isContainer));
	}
	
	/**
	 * Get component from a given enum setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getEnumComponent (IEnumSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new CycleButton(setting,theme.getButtonRenderer(String.class,isContainer));
	}
	
	/**
	 * Get component from a given color setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getColorComponent (IColorSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new ColorSliderComponent((IColorSetting)setting,new ThemeTuple(theme.theme,theme.logicalLevel,colorLevel));
	}
	
	/**
	 * Get component from a given keybind setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getKeybindComponent (IKeybindSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new KeybindComponent(setting,theme.getKeybindRenderer(isContainer));
	}
	
	/**
	 * Get component from a given string setting object.
	 * @param setting the setting object.
	 * @param animation the animation supplier
	 * @param adder the component adder for any pop-ups
	 * @param theme the theme to be used
	 * @param colorLevel the panel nesting level, in case the component is a container (e.g. color components)
	 * @param isContainer whether this component is a title bar
	 * @return the component to be used
	 */
	public default IComponent getStringComponent (IStringSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new TextField(setting, new ITextFieldKeys() {
			@Override
			public boolean isBackspaceKey (int scancode) {
				return false;
			}

			@Override
			public boolean isDeleteKey (int scancode) {
				return false;
			}

			@Override
			public boolean isInsertKey (int scancode) {
				return false;
			}

			@Override
			public boolean isLeftKey (int scancode) {
				return false;
			}

			@Override
			public boolean isRightKey (int scancode) {
				return false;
			}

			@Override
			public boolean isHomeKey (int scancode) {
				return false;
			}

			@Override
			public boolean isEndKey (int scancode) {
				return false;
			}

			@Override
			public boolean isCopyKey (int scancode) {
				return false;
			}

			@Override
			public boolean isPasteKey (int scancode) {
				return false;
			}

			@Override
			public boolean isCutKey (int scancode) {
				return false;
			}

			@Override
			public boolean isAllKey (int scancode) {
				return false;
			}
		},0,new SimpleToggleable(false),theme.getTextRenderer(false,isContainer)) {
			@Override
			public boolean allowCharacter (char character) {
				return false;
			}
		};
	}
}
