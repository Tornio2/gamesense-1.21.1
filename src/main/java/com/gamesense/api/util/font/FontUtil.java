package com.gamesense.api.util.font;

import com.gamesense.api.util.render.GSColor;
import com.gamesense.client.GameSense;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;

public class FontUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, GSColor color) {
        if (customFont) {
            return GameSense.INSTANCE.cFontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            // Create a DrawContext (required in 1.21.1)
            DrawContext drawContext = new DrawContext(mc, mc.getBufferBuilders().getEntityVertexConsumers());

            // Use the DrawContext to draw text with shadow
            drawContext.drawTextWithShadow(
                    mc.textRenderer, // fontRenderer ->textRenderer
                    Text.literal(text),
                    x,
                    y,
                    color.getRGB()
            );
//            return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());

            // Return the width of the drawn text
            return x + mc.textRenderer.getWidth(text);
        }
    }

    public static int getStringWidth(boolean customFont, String string) {
        if (customFont) {
            return GameSense.INSTANCE.cFontRenderer.getStringWidth(string);
        } else {
            return mc.textRenderer.getWidth(string);
        }
    }

    public static int getFontHeight(boolean customFont) {
        if (customFont) {
            return GameSense.INSTANCE.cFontRenderer.getHeight();
        } else {
            return mc.textRenderer.fontHeight;
        }
    }
}