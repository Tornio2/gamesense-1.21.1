package com.gamesense.api.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.*;
// TODO: Changed "import net.minecraft.client.renderer.GlStateManager;" to import com.mojang.blaze3d.systems.RenderSystem; if it makes problems
public class GSColor extends Color {

    private static final long serialVersionUID = 1L;

    public GSColor(int rgb) {
        super(rgb);
    }

    public GSColor(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    public GSColor(int r, int g, int b) {
        super(r, g, b);
    }

    public GSColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public GSColor(Color color) {
        super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public GSColor(GSColor color, int a) {
        super(color.getRed(), color.getGreen(), color.getBlue(), a);
    }

    public static GSColor fromHSB(float hue, float saturation, float brightness) {
        return new GSColor(Color.getHSBColor(hue, saturation, brightness));
    }

    public float getHue() {
        return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[0];
    }

    public float getSaturation() {
        return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[1];
    }

    public float getBrightness() {
        return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[2];
    }

    public void glColor() {
        // Replace GlStateManager.color with RenderSystem.setShaderColor
        RenderSystem.setShaderColor(
                getRed() / 255.0f,
                getGreen() / 255.0f,
                getBlue() / 255.0f,
                getAlpha() / 255.0f
        );
    }

    // You may want to add these convenience methods for the new rendering system
    public float[] getFloatComponents() {
        return new float[] {
                getRed() / 255.0f,
                getGreen() / 255.0f,
                getBlue() / 255.0f,
                getAlpha() / 255.0f
        };
    }

    public int getRGBA() {
        return (getRed() << 16) | (getGreen() << 8) | getBlue() | (getAlpha() << 24);
    }
}