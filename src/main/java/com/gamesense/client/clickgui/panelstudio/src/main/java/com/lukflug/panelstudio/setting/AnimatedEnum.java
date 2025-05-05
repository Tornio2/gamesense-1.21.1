package com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.setting;

import com.gamesense.client.clickgui.panelstudio.src.main.java.com.lukflug.panelstudio.base.Animation;

public final class AnimatedEnum {
	private final IEnumSetting setting;
	private final Animation animation;

	public AnimatedEnum (IEnumSetting setting, Animation animation) {
		this.setting=setting;
		this.animation=animation;
	}
	
	public double getValue() {
		int index=setting.getValueIndex();
		if (animation.getTarget()!=index) animation.setValue(index);
		return animation.getValue();
	}
}
