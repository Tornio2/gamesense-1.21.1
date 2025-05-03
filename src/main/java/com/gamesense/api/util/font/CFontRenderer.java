//package com.gamesense.api.util.font;
//import com.gamesense.api.util.render.GSColor;
// //import net.minecraft.client.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.texture.DynamicTexture;
//import org.lwjgl.opengl.GL11;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CFontRenderer extends CFont {
//
//    protected CharData[] boldChars = new CharData[256];
//    protected CharData[] italicChars = new CharData[256];
//    protected CharData[] boldItalicChars = new CharData[256];
//
//    private final int[] colorCode = new int[32];
//    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
//
//    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
//        super(font, antiAlias, fractionalMetrics);
//        setupMinecraftColorcodes();
//        setupBoldItalicIDs();
//    }
//
//    String fontName = "Arial";
//    int fontSize = 18;
//
//    public String getFontName() {
//        return this.fontName;
//    }
//
//    public int getFontSize() {
//        return this.fontSize;
//    }
//
//    public void setFontName(String newName) {
//        fontName = newName;
//    }
//
//    public void setFontSize(int newSize) {
//        fontSize = newSize;
//    }
//
//    public float drawStringWithShadow(String text, double x, double y, GSColor color) {
//        float shadowWidth = drawString(text, x + 1D, y + 1D, color, true);
//        return Math.max(shadowWidth, drawString(text, x, y, color, false));
//    }
//
//    public float drawString(String text, float x, float y, GSColor color) {
//        return drawString(text, x, y, color, false);
//    }
//
//    public float drawCenteredStringWithShadow(String text, float x, float y, GSColor color) {
//        return drawStringWithShadow(text, x - getStringWidth(text) / 2, y, color);
//    }
//
//    public float drawCenteredString(String text, float x, float y, GSColor color) {
//        return drawString(text, x - getStringWidth(text) / 2, y, color);
//    }
//
//    public float drawString(String text, double x, double y, GSColor gsColor, boolean shadow) {
//        x -= 1;
//        y -= 2;
//        GSColor color = new GSColor(gsColor);
//        if (text == null) return 0.0F;
//        if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255 && color.getAlpha() == 32)
//            color = new GSColor(255, 255, 255);
//        if (color.getAlpha() < 4) color = new GSColor(color, 255);
//        if (shadow)
//            color = new GSColor(color.getRed() / 4, color.getGreen() / 4, color.getBlue() / 4, color.getAlpha());
//
//        CharData[] currentData = this.charData;
//        boolean randomCase = false;
//        boolean bold = false;
//        boolean italic = false;
//        boolean strikethrough = false;
//        boolean underline = false;
//        boolean render = true;
//        x *= 2.0D;
//        y *= 2.0D;
//        if (render) {
//            RenderSystem.pushMatrix();
//            RenderSystem.scale(0.5D, 0.5D, 0.5D);
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            RenderSystem.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
//            int size = text.length();
//            RenderSystem.enableTexture2D();
//            RenderSystem.bindTexture(tex.getGlTextureId());
//            //GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());
//            for (int i = 0; i < size; i++) {
//                char character = text.charAt(i);
//                if ((character == '\u00A7') && (i < size)) {
//                    int colorIndex = 21;
//                    try {
//                        colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
//                    } catch (Exception e) {
//
//                    }
//                    if (colorIndex < 16) {
//                        bold = false;
//                        italic = false;
//                        randomCase = false;
//                        underline = false;
//                        strikethrough = false;
//                        RenderSystem.bindTexture(tex.getGlTextureId());
//                        // GL11.glBindTexture(GL11.GL_TEXTURE_2D,
//                        // tex.getGlTextureId());
//                        currentData = this.charData;
//                        if ((colorIndex < 0) || (colorIndex > 15)) colorIndex = 15;
//                        if (shadow) colorIndex += 16;
//                        int colorcode = this.colorCode[colorIndex];
//                        RenderSystem.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, color.getAlpha());
//                    } else if (colorIndex == 16) {
//                        randomCase = true;
//                    } else if (colorIndex == 17) {
//                        bold = true;
//                        if (italic) {
//                            RenderSystem.bindTexture(texItalicBold.getGlTextureId());
//                            // GL11.glBindTexture(GL11.GL_TEXTURE_2D,
//                            // texItalicBold.getGlTextureId());
//                            currentData = this.boldItalicChars;
//                        } else {
//                            RenderSystem.bindTexture(texBold.getGlTextureId());
//                            // GL11.glBindTexture(GL11.GL_TEXTURE_2D,
//                            // texBold.getGlTextureId());
//                            currentData = this.boldChars;
//                        }
//                    } else if (colorIndex == 18) {
//                        strikethrough = true;
//                    } else if (colorIndex == 19) {
//                        underline = true;
//                    } else if (colorIndex == 20) {
//                        italic = true;
//                        if (bold) {
//                            RenderSystem.bindTexture(texItalicBold.getGlTextureId());
//                            // GL11.glBindTexture(GL11.GL_TEXTURE_2D,
//                            // texItalicBold.getGlTextureId());
//                            currentData = this.boldItalicChars;
//                        } else {
//                            RenderSystem.bindTexture(texItalic.getGlTextureId());
//                            // GL11.glBindTexture(GL11.GL_TEXTURE_2D,
//                            // texItalic.getGlTextureId());
//                            currentData = this.italicChars;
//                        }
//                    } else if (colorIndex == 21) {
//                        bold = false;
//                        italic = false;
//                        randomCase = false;
//                        underline = false;
//                        strikethrough = false;
//                        RenderSystem.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
//                        RenderSystem.bindTexture(tex.getGlTextureId());
//                        // GL11.glBindTexture(GL11.GL_TEXTURE_2D,
//                        // tex.getGlTextureId());
//                        currentData = this.charData;
//                    }
//                    i++;
//                } else if ((character < currentData.length) && (character >= 0)) {
//                    RenderSystem.glBegin(GL11.GL_TRIANGLES);
//                    drawChar(currentData, character, (float) x, (float) y);
//                    RenderSystem.glEnd();
//                    if (strikethrough)
//                        drawLine(x, y + currentData[character].height / 2, x + currentData[character].width - 8.0D, y + currentData[character].height / 2, 1.0F);
//                    if (underline)
//                        drawLine(x, y + currentData[character].height - 2.0D, x + currentData[character].width - 8.0D, y + currentData[character].height - 2.0D, 1.0F);
//                    x += currentData[character].width - 8 + this.charOffset;
//                }
//            }
//            GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
//            RenderSystem.popMatrix();
//        }
//        return (float) x / 2.0F;
//    }
//
//    @Override
//    public int getStringWidth(String text) {
//        if (text == null) {
//            return 0;
//        }
//        int width = 0;
//        CharData[] currentData = this.charData;
//        boolean bold = false;
//        boolean italic = false;
//        int size = text.length();
//
//        for (int i = 0; i < size; i++) {
//            char character = text.charAt(i);
//            if ((character == '\u00A7') && (i < size)) {
//                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
//                if (colorIndex < 16) {
//                    bold = false;
//                    italic = false;
//                } else if (colorIndex == 17) {
//                    bold = true;
//                    if (italic) {
//                        currentData = this.boldItalicChars;
//                    } else {
//                        currentData = this.boldChars;
//                    }
//                } else if (colorIndex == 20) {
//                    italic = true;
//                    if (bold) {
//                        currentData = this.boldItalicChars;
//                    } else {
//                        currentData = this.italicChars;
//                    }
//                } else if (colorIndex == 21) {
//                    bold = false;
//                    italic = false;
//                    currentData = this.charData;
//                }
//                i++;
//            } else if ((character < currentData.length) && (character >= 0)) {
//                width += currentData[character].width - 8 + this.charOffset;
//            }
//        }
//        return width / 2;
//    }
//
//    public void setFont(Font font) {
//        super.setFont(font);
//        setupBoldItalicIDs();
//    }
//
//    public void setAntiAlias(boolean antiAlias) {
//        super.setAntiAlias(antiAlias);
//        setupBoldItalicIDs();
//    }
//
//    public void setFractionalMetrics(boolean fractionalMetrics) {
//        super.setFractionalMetrics(fractionalMetrics);
//        setupBoldItalicIDs();
//    }
//
//    protected DynamicTexture texBold;
//    protected DynamicTexture texItalic;
//    protected DynamicTexture texItalicBold;
//
//    private void setupBoldItalicIDs() {
//        this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
//        this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
//        this.texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
//    }
//
//    private void drawLine(double x, double y, double x1, double y1, float width) {
//        RenderSystem.disableTexture2D();
//        RenderSystem.glLineWidth(width);
//        RenderSystem.glBegin(GL11.GL_LINES);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x1, y1);
//        RenderSystem.glEnd();
//        RenderSystem.enableTexture2D();
//    }
//
//    public List<String> wrapWords(String text, double width) {
//        List finalWords = new ArrayList();
//        if (getStringWidth(text) > width) {
//            String[] words = text.split(" ");
//            String currentWord = "";
//            char lastColorCode = 65535;
//
//            for (String word : words) {
//                for (int i = 0; i < word.toCharArray().length; i++) {
//                    char c = word.toCharArray()[i];
//
//                    if ((c == '\u00A7') && (i < word.toCharArray().length - 1)) {
//                        lastColorCode = word.toCharArray()[(i + 1)];
//                    }
//                }
//                if (getStringWidth(currentWord + word + " ") < width) {
//                    currentWord = currentWord + word + " ";
//                } else {
//                    finalWords.add(currentWord);
//                    currentWord = "\u00A7" + lastColorCode + word + " ";
//                }
//            }
//            if (currentWord.length() > 0) if (getStringWidth(currentWord) < width) {
//                finalWords.add("\u00A7" + lastColorCode + currentWord + " ");
//                currentWord = "";
//            } else {
//                for (String s : formatString(currentWord, width)) {
//                    finalWords.add(s);
//                }
//            }
//        } else {
//            finalWords.add(text);
//        }
//        return finalWords;
//    }
//
//    public List<String> formatString(String string, double width) {
//        List finalWords = new ArrayList();
//        String currentWord = "";
//        char lastColorCode = 65535;
//        char[] chars = string.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            char c = chars[i];
//
//            if ((c == '\u00A7') && (i < chars.length - 1)) {
//                lastColorCode = chars[(i + 1)];
//            }
//
//            if (getStringWidth(currentWord + c) < width) {
//                currentWord = currentWord + c;
//            } else {
//                finalWords.add(currentWord);
//                currentWord = "\u00A7" + lastColorCode + c;
//            }
//        }
//
//        if (currentWord.length() > 0) {
//            finalWords.add(currentWord);
//        }
//        return finalWords;
//    }
//
//    private void setupMinecraftColorcodes() {
//        for (int index = 0; index < 32; index++) {
//            int noClue = (index >> 3 & 0x1) * 85;
//            int red = (index >> 2 & 0x1) * 170 + noClue;
//            int green = (index >> 1 & 0x1) * 170 + noClue;
//            int blue = (index >> 0 & 0x1) * 170 + noClue;
//
//            if (index == 6) {
//                red += 85;
//            }
//            if (index >= 16) {
//                red /= 4;
//                green /= 4;
//                blue /= 4;
//            }
//            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
//        }
//    }
//}
package com.gamesense.api.util.font;

import com.gamesense.api.util.render.GSColor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.DynamicTexture;
import net.minecraft.client.render.GameRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CFontRenderer extends CFont {

    protected CharData[] boldChars = new CharData[256];
    protected CharData[] italicChars = new CharData[256];
    protected CharData[] boldItalicChars = new CharData[256];

    private final int[] colorCode = new int[32];
    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";

    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        setupMinecraftColorcodes();
        setupBoldItalicIDs();
    }

    String fontName = "Arial";
    int fontSize = 18;

    public String getFontName() {
        return this.fontName;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontName(String newName) {
        fontName = newName;
    }

    public void setFontSize(int newSize) {
        fontSize = newSize;
    }

    public float drawStringWithShadow(String text, double x, double y, GSColor color) {
        float shadowWidth = drawString(text, x + 1D, y + 1D, color, true);
        return Math.max(shadowWidth, drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, GSColor color) {
        return drawString(text, x, y, color, false);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, GSColor color) {
        return drawStringWithShadow(text, x - getStringWidth(text) / 2, y, color);
    }

    public float drawCenteredString(String text, float x, float y, GSColor color) {
        return drawString(text, x - getStringWidth(text) / 2, y, color);
    }

    public float drawString(String text, double x, double y, GSColor gsColor, boolean shadow) {
        x -= 1;
        y -= 2;
        GSColor color = new GSColor(gsColor);
        if (text == null) return 0.0F;
        if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255 && color.getAlpha() == 32)
            color = new GSColor(255, 255, 255);
        if (color.getAlpha() < 4) color = new GSColor(color, 255);
        if (shadow)
            color = new GSColor(color.getRed() / 4, color.getGreen() / 4, color.getBlue() / 4, color.getAlpha());

        CharData[] currentData = this.charData;
        boolean randomCase = false;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        boolean render = true;
        x *= 2.0D;
        y *= 2.0D;
        if (render) {
            // Create a new MatrixStack for our rendering operations
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.scale(0.5F, 0.5F, 0.5F);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

            int size = text.length();
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, tex.getGlId());

            for (int i = 0; i < size; i++) {
                char character = text.charAt(i);
                if ((character == '\u00A7') && (i < size)) {
                    int colorIndex = 21;
                    try {
                        colorIndex = colorcodeIdentifiers.indexOf(text.charAt(i + 1));
                    } catch (Exception e) {
                        // Ignore
                    }
                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikethrough = false;
                        RenderSystem.setShaderTexture(0, tex.getGlId());
                        currentData = this.charData;
                        if ((colorIndex < 0) || (colorIndex > 15)) colorIndex = 15;
                        if (shadow) colorIndex += 16;
                        int colorcode = this.colorCode[colorIndex];
                        RenderSystem.setShaderColor((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, color.getAlpha() / 255.0F);
                    } else if (colorIndex == 16) {
                        randomCase = true;
                    } else if (colorIndex == 17) {
                        bold = true;
                        if (italic) {
                            RenderSystem.setShaderTexture(0, texItalicBold.getGlId());
                            currentData = this.boldItalicChars;
                        } else {
                            RenderSystem.setShaderTexture(0, texBold.getGlId());
                            currentData = this.boldChars;
                        }
                    } else if (colorIndex == 18) {
                        strikethrough = true;
                    } else if (colorIndex == 19) {
                        underline = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                        if (bold) {
                            RenderSystem.setShaderTexture(0, texItalicBold.getGlId());
                            currentData = this.boldItalicChars;
                        } else {
                            RenderSystem.setShaderTexture(0, texItalic.getGlId()); // textItalic.getGLid() -> tex.getGlId()
                            currentData = this.italicChars;
                        }
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikethrough = false;
                        RenderSystem.setShaderColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                        RenderSystem.setShaderTexture(0, tex.getGlId());
                        currentData = this.charData;
                    }
                    i++;
                } else if ((character < currentData.length) && (character >= 0)) {
                    drawChar(matrixStack, currentData, character, (float) x, (float) y);

                    if (strikethrough)
                        drawLine(matrixStack, x, y + currentData[character].height / 2, x + currentData[character].width - 8.0D, y + currentData[character].height / 2, 1.0F);
                    if (underline)
                        drawLine(matrixStack, x, y + currentData[character].height - 2.0D, x + currentData[character].width - 8.0D, y + currentData[character].height - 2.0D, 1.0F);
                    x += currentData[character].width - 8 + this.charOffset;
                }
            }
        }
        return (float) x / 2.0F;
    }

    // You'll need to update this method in your CFont class
    protected void drawChar(MatrixStack matrixStack, CharData[] currentData, char character, float x, float y) {
        // This will need to be implemented using modern rendering methods
        // For now, we'll use the old method as a fallback
        drawChar(currentData, character, x, y);
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);
            if ((character == '\u00A7') && (i < size)) {
                int colorIndex = colorcodeIdentifiers.indexOf(character);
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        currentData = this.boldItalicChars;
                    } else {
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        currentData = this.boldItalicChars;
                    } else {
                        currentData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                i++;
            } else if ((character < currentData.length) && (character >= 0)) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }
        return width / 2;
    }

    public void setFont(Font font) {
        super.setFont(font);
        setupBoldItalicIDs();
    }

    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        setupBoldItalicIDs();
    }

    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        setupBoldItalicIDs();
    }

    protected AbstractTexture texBold; // DynamicTexture -> AbstractTexture
    protected AbstractTexture texItalic;
    protected AbstractTexture texItalicBold;

    private void setupBoldItalicIDs() {
        this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    private void drawLine(MatrixStack matrixStack, double x, double y, double x1, double y1, float width) {
        // Save current texture state
        RenderSystem.setShaderTexture(0, 0);
        GL11.glLineWidth(width);

        // Drawing with immediate mode - will need to be modernized in a full rewrite
        // This is a temporary solution to maintain compatibility
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();

        // Restore texture state
        RenderSystem.setShaderTexture(0, tex.getGlId());
    }

    public List<String> wrapWords(String text, double width) {
        List<String> finalWords = new ArrayList<>();
        if (getStringWidth(text) > width) {
            String[] words = text.split(" ");
            String currentWord = "";
            char lastColorCode = 65535;

            for (String word : words) {
                for (int i = 0; i < word.toCharArray().length; i++) {
                    char c = word.toCharArray()[i];

                    if ((c == '\u00A7') && (i < word.toCharArray().length - 1)) {
                        lastColorCode = word.toCharArray()[(i + 1)];
                    }
                }
                if (getStringWidth(currentWord + word + " ") < width) {
                    currentWord = currentWord + word + " ";
                } else {
                    finalWords.add(currentWord);
                    currentWord = "\u00A7" + lastColorCode + word + " ";
                }
            }
            if (currentWord.length() > 0) if (getStringWidth(currentWord) < width) {
                finalWords.add("\u00A7" + lastColorCode + currentWord + " ");
                currentWord = "";
            } else {
                for (String s : formatString(currentWord, width)) {
                    finalWords.add(s);
                }
            }
        } else {
            finalWords.add(text);
        }
        return finalWords;
    }

    public List<String> formatString(String string, double width) {
        List<String> finalWords = new ArrayList<>();
        String currentWord = "";
        char lastColorCode = 65535;
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if ((c == '\u00A7') && (i < chars.length - 1)) {
                lastColorCode = chars[(i + 1)];
            }

            if (getStringWidth(currentWord + c) < width) {
                currentWord = currentWord + c;
            } else {
                finalWords.add(currentWord);
                currentWord = "\u00A7" + lastColorCode + c;
            }
        }

        if (currentWord.length() > 0) {
            finalWords.add(currentWord);
        }
        return finalWords;
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; index++) {
            int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index >> 0 & 0x1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }
}