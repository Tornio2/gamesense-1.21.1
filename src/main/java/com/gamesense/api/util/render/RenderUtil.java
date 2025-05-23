//package com.gamesense.api.util.render;
//import com.gamesense.api.util.font.FontUtil;
//import com.gamesense.api.util.world.EntityUtil;
//import com.gamesense.api.util.world.GeometryMasks;
//import com.gamesense.client.module.ModuleManager;
//import com.gamesense.client.module.modules.gui.ColorMain;
//import com.gamesense.client.module.modules.render.Nametags;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Vec3d;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL32;
//import org.lwjgl.util.glu.GLU;
//import org.lwjgl.util.glu.Sphere;
//
//import static org.lwjgl.opengl.GL11.glEnable;
//import static org.lwjgl.opengl.GL11.glHint;

//public class RenderUtil {
//
//    private static final MinecraftClient mc = MinecraftClient.getInstance();
//
//    public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, GSColor color) {
//        drawLine(posx, posy, posz, posx2, posy2, posz2, color, 1);
//    }
//
//    public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, GSColor color, float width) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        GlStateManager.glLineWidth(width);
//        color.glColor();
//        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
//        vertex(posx, posy, posz, bufferbuilder);
//        vertex(posx2, posy2, posz2, bufferbuilder);
//        tessellator.draw();
//    }
//
//    public static void draw2DRect(int posX, int posY, int width, int height, int zHeight, GSColor color) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        GlStateManager.enableBlend();
//        GlStateManager.disableTexture2D();
//        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        color.glColor();
//        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
//        bufferbuilder.pos(posX, posY + height, zHeight).endVertex();
//        bufferbuilder.pos(posX + width, posY + height, zHeight).endVertex();
//        bufferbuilder.pos(posX + width, posY, zHeight).endVertex();
//        bufferbuilder.pos(posX, posY, zHeight).endVertex();
//        tessellator.draw();
//        GlStateManager.enableTexture2D();
//        GlStateManager.disableBlend();
//    }
//
//    private static void drawBorderedRect(double x, double y, double x1, double y1, float lineWidth, GSColor inside, GSColor border) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        inside.glColor();
//        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
//        bufferbuilder.pos(x, y1, 0).endVertex();
//        bufferbuilder.pos(x1, y1, 0).endVertex();
//        bufferbuilder.pos(x1, y, 0).endVertex();
//        bufferbuilder.pos(x, y, 0).endVertex();
//        tessellator.draw();
//        border.glColor();
//        GlStateManager.glLineWidth(lineWidth);
//        bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
//        bufferbuilder.pos(x, y, 0).endVertex();
//        bufferbuilder.pos(x, y1, 0).endVertex();
//        bufferbuilder.pos(x1, y1, 0).endVertex();
//        bufferbuilder.pos(x1, y, 0).endVertex();
//        bufferbuilder.pos(x, y, 0).endVertex();
//        tessellator.draw();
//    }
//
//    public static void drawBox(BlockPos blockPos, double height, GSColor color, int sides) {
//        drawBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, height, 1, color, color.getAlpha(), sides);
//    }
//
//    public static void drawBox(AxisAlignedBB bb, boolean check, double height, GSColor color, int sides) {
//        drawBox(bb, check, height, color, color.getAlpha(), sides);
//    }
//
//    public static void drawBox(AxisAlignedBB bb, boolean check, double height, GSColor color, int alpha, int sides) {
//        if (check) {
//            drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, bb.maxY - bb.minY, bb.maxZ - bb.minZ, color, alpha, sides);
//        } else {
//            drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, height, bb.maxZ - bb.minZ, color, alpha, sides);
//        }
//    }
//
//    public static void drawBox(double x, double y, double z, double w, double h, double d, GSColor color, int alpha, int sides) {
//        GlStateManager.disableAlpha();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        color.glColor();
//        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
//        doVerticies(new AxisAlignedBB(x, y, z, x + w, y + h, z + d), color, alpha, bufferbuilder, sides, false);
//        tessellator.draw();
//        GlStateManager.enableAlpha();
//    }
//
//    public static void drawBoundingBox(BlockPos bp, double height, float width, GSColor color) {
//        drawBoundingBox(getBoundingBox(bp, 1, height, 1), width, color, color.getAlpha());
//    }
//
//    public static void drawBoundingBox(AxisAlignedBB bb, double width, GSColor color) {
//        drawBoundingBox(bb, width, color, color.getAlpha());
//    }
//
//    public static void drawBoundingBox(AxisAlignedBB bb, double width, GSColor color, int alpha) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        GlStateManager.glLineWidth((float) width);
//        color.glColor();
//        bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
//        colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
//        colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
//        colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
//        colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
//        colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
//        colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
//        colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
//        colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
//        colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
//        tessellator.draw();
//    }
//
//    public static void drawBoundingBoxWithSides(BlockPos blockPos, int width, GSColor color, int sides) {
//        drawBoundingBoxWithSides(getBoundingBox(blockPos, 1, 1, 1), width, color, color.getAlpha(), sides);
//    }
//
//    public static void drawBoundingBoxWithSides(BlockPos blockPos, int width, GSColor color, int alpha, int sides) {
//        drawBoundingBoxWithSides(getBoundingBox(blockPos, 1, 1, 1), width, color, alpha, sides);
//    }
//
//    public static void drawBoundingBoxWithSides(AxisAlignedBB axisAlignedBB, int width, GSColor color, int sides) {
//        drawBoundingBoxWithSides(axisAlignedBB, width, color, color.getAlpha(), sides);
//    }
//
//    public static void drawBoundingBoxWithSides(AxisAlignedBB axisAlignedBB, int width, GSColor color, int alpha, int sides) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        GlStateManager.glLineWidth(width);
//        bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
//        doVerticies(axisAlignedBB, color, alpha, bufferbuilder, sides, true);
//        tessellator.draw();
//    }
//
//    private static class Points {
//        double[][] point = new double[10][2];
//        private int count = 0;
//        private final double xCenter;
//        private final double zCenter;
//        public final double yMin;
//        public final double yMax;
//        private final float rotation;
//
//        public Points(double yMin, double yMax, double xCenter, double zCenter, float rotation) {
//            this.yMin = yMin;
//            this.yMax = yMax;
//            this.xCenter = xCenter;
//            this.zCenter = zCenter;
//            this.rotation = rotation;
//        }
//
//        public void addPoints(double x, double z) {
//            x -= xCenter;
//            z -= zCenter;
//            double rotateX = x * Math.cos(rotation) - z * Math.sin(rotation);
//            double rotateZ = x * Math.sin(rotation) + z * Math.cos(rotation);
//            rotateX += xCenter;
//            rotateZ += zCenter;
//            point[count++] = new double[]{rotateX, rotateZ};
//        }
//
//        public double[] getPoint(int index) {
//            return point[index];
//        }
//    }
//
//    public static void drawBoxWithDirection(AxisAlignedBB bb, GSColor color, float rotation, float width, int mode) {
//        double xCenter = bb.minX + (bb.maxX - bb.minX) / 2;
//        double zCenter = bb.minZ + (bb.maxZ - bb.minZ) / 2;
//
//        Points square = new Points(bb.minY, bb.maxY, xCenter, zCenter, rotation);
//
//        if (mode == 0) {
//            square.addPoints(bb.minX, bb.minZ);
//            square.addPoints(bb.minX, bb.maxZ);
//            square.addPoints(bb.maxX, bb.maxZ);
//            square.addPoints(bb.maxX, bb.minZ);
//        }
//
//        switch (mode) {
//            case 0: {
//                drawDirection(square, color, width);
//                break;
//            }
//            //can add different modes in the future
//        }
//    }
//
//    public static void drawDirection(Points square, GSColor color, float width) {
//        for (int i = 0; i < 4; i++) {
//            drawLine(square.getPoint(i)[0], square.yMin, square.getPoint(i)[1],
//                    square.getPoint((i + 1) % 4)[0], square.yMin, square.getPoint((i + 1) % 4)[1],
//                    color, width
//            );
//        }
//
//        for (int i = 0; i < 4; i++) {
//            drawLine(square.getPoint(i)[0], square.yMax, square.getPoint(i)[1],
//                    square.getPoint((i + 1) % 4)[0], square.yMax, square.getPoint((i + 1) % 4)[1],
//                    color, width
//            );
//        }
//
//        for (int i = 0; i < 4; i++) {
//            drawLine(square.getPoint(i)[0], square.yMin, square.getPoint(i)[1],
//                    square.getPoint(i)[0], square.yMax, square.getPoint(i)[1],
//                    color, width
//            );
//        }
//    }
//
//    public static void drawSphere(double x, double y, double z, float size, int slices, int stacks, float lineWidth, GSColor color) {
//        Sphere sphere = new Sphere();
//        GlStateManager.glLineWidth(lineWidth);
//        color.glColor();
//        sphere.setDrawStyle(GLU.GLU_SILHOUETTE);
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);
//        sphere.draw(size, slices, stacks);
//        GlStateManager.popMatrix();
//    }
//
//    public static void drawNametag(Entity entity, String[] text, GSColor color, int type) {
//        Vec3d pos = EntityUtil.getInterpolatedPos(entity, mc.getRenderPartialTicks());
//        drawNametag(pos.x, pos.y + entity.height, pos.z, text, color, type);
//    }
//
//    public static void drawNametag(double x, double y, double z, String[] text, GSColor color, int type) {
//        ColorMain colorMain = ModuleManager.getModule(ColorMain.class);
//        double dist = mc.player.getDistance(x, y, z);
//        double scale = 1, offset = 0;
//        int start = 0;
//        switch (type) {
//            case 0:
//                scale = dist / 20 * Math.pow(1.2589254, 0.1 / (dist < 25 ? 0.5 : 2));
//                scale = Math.min(Math.max(scale, .5), 5);
//                offset = scale > 2 ? scale / 2 : scale;
//                scale /= 40;
//                start = 10;
//                break;
//            case 1:
//                scale = -((int) dist) / 6.0;
//                if (scale < 1) scale = 1;
//                scale *= 2.0 / 75.0;
//                break;
//            case 2:
//                scale = 0.0018 + 0.003 * dist;
//                if (dist <= 8.0) scale = 0.0245;
//                start = -8;
//                break;
//        }
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y + offset - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);
//        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
//        GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1 : 1, 0, 0);
//        GlStateManager.scale(-scale, -scale, scale);
//        if (type == 2) {
//            double width = 0;
//            GSColor bcolor = new GSColor(0, 0, 0, 51);
//            Nametags nametags = ModuleManager.getModule(Nametags.class);
//
//            if (nametags.customColor.getValue()) {
//                bcolor = nametags.borderColor.getValue();
//            }
//            for (int i = 0; i < text.length; i++) {
//                double w = FontUtil.getStringWidth(colorMain.customFont.getValue(), text[i]) / 2;
//                if (w > width) {
//                    width = w;
//                }
//            }
//            drawBorderedRect(-width - 1, -mc.fontRenderer.FONT_HEIGHT, width + 2, 1, 1.8f, new GSColor(0, 4, 0, 85), bcolor);
//        }
//        GlStateManager.enableTexture2D();
//        for (int i = 0; i < text.length; i++) {
//            FontUtil.drawStringWithShadow(colorMain.customFont.getValue(), text[i], -FontUtil.getStringWidth(colorMain.customFont.getValue(), text[i]) / 2, i * (mc.fontRenderer.FONT_HEIGHT + 1) + start, color);
//        }
//        GlStateManager.disableTexture2D();
//        if (type != 2) {
//            GlStateManager.popMatrix();
//        }
//    }
//
//    private static void vertex(double x, double y, double z, BufferBuilder bufferbuilder) {
//        bufferbuilder.pos(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ).endVertex();
//    }
//
//    private static void colorVertex(double x, double y, double z, GSColor color, int alpha, BufferBuilder bufferbuilder) {
//        bufferbuilder.pos(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ).color(color.getRed(), color.getGreen(), color.getBlue(), alpha).endVertex();
//    }
//
//    private static AxisAlignedBB getBoundingBox(BlockPos bp, double width, double height, double depth) {
//        double x = bp.getX();
//        double y = bp.getY();
//        double z = bp.getZ();
//        return new AxisAlignedBB(x, y, z, x + width, y + height, z + depth);
//    }
//
//    private static void doVerticies(AxisAlignedBB axisAlignedBB, GSColor color, int alpha, BufferBuilder bufferbuilder, int sides, boolean five) {
//        if ((sides & GeometryMasks.Quad.EAST) != 0) {
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
//            if (five)
//                colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//        }
//        if ((sides & GeometryMasks.Quad.WEST) != 0) {
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//            if (five)
//                colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//        }
//        if ((sides & GeometryMasks.Quad.NORTH) != 0) {
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//            if (five)
//                colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//        }
//        if ((sides & GeometryMasks.Quad.SOUTH) != 0) {
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
//            if (five)
//                colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//        }
//        if ((sides & GeometryMasks.Quad.UP) != 0) {
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
//            if (five)
//                colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
//        }
//        if ((sides & GeometryMasks.Quad.DOWN) != 0) {
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
//            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//            if (five)
//                colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
//        }
//    }
//
//    public static void prepare() {
//        glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
//        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
//        GlStateManager.shadeModel(GL11.GL_SMOOTH);
//        GlStateManager.depthMask(false);
//        GlStateManager.enableBlend();
//        GlStateManager.disableDepth();
//        GlStateManager.disableTexture2D();
//        GlStateManager.disableLighting();
//        GlStateManager.disableCull();
//        GlStateManager.enableAlpha();
//        glEnable(GL11.GL_LINE_SMOOTH);
//        glEnable(GL32.GL_DEPTH_CLAMP);
//    }
//
//    public static void release() {
//        GL11.glDisable(GL32.GL_DEPTH_CLAMP);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GlStateManager.enableAlpha();
//        GlStateManager.enableCull();
//        GlStateManager.enableTexture2D();
//        GlStateManager.enableDepth();
//        GlStateManager.disableBlend();
//        GlStateManager.depthMask(true);
//        GlStateManager.glLineWidth(1.0f);
//        GlStateManager.shadeModel(GL11.GL_FLAT);
//        glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
//    }
//}

package com.gamesense.api.util.render;

import com.gamesense.api.util.font.FontUtil;
import com.gamesense.api.util.world.EntityUtil;
import com.gamesense.api.util.world.GeometryMasks;
import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.gui.ColorMain;
import com.gamesense.client.module.modules.render.Nametags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;

/**
 * @author 086
 * @author Hoosiers
 * @author lukflug
 * @author TechAle
 * @updated for Fabric 1.21.1
 */

public class RenderUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, GSColor color) {
        drawLine(posx, posy, posz, posx2, posy2, posz2, color, 1);
    }

    public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, GSColor color, float width) {
        MatrixStack matrixStack = new MatrixStack();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.lineWidth(width);
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferbuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION);
        vertex(posx, posy, posz, bufferbuilder);
        vertex(posx2, posy2, posz2, bufferbuilder);
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
    }

    public static void draw2DRect(int posX, int posY, int width, int height, int zHeight, GSColor color) {
        MatrixStack matrixStack = new MatrixStack();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferbuilder.vertex(matrix, posX, posY + height, zHeight).next();
        bufferbuilder.vertex(matrix, posX + width, posY + height, zHeight).next();
        bufferbuilder.vertex(matrix, posX + width, posY, zHeight).next();
        bufferbuilder.vertex(matrix, posX, posY, zHeight).next();
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private static void drawBorderedRect(double x, double y, double x1, double y1, float lineWidth, GSColor inside, GSColor border) {
        MatrixStack matrixStack = new MatrixStack();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        // Draw inside
        RenderSystem.setShaderColor(inside.getRed() / 255f, inside.getGreen() / 255f, inside.getBlue() / 255f, inside.getAlpha() / 255f);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferbuilder.vertex(matrix, (float)x, (float)y1, 0).next();
        bufferbuilder.vertex(matrix, (float)x1, (float)y1, 0).next();
        bufferbuilder.vertex(matrix, (float)x1, (float)y, 0).next();
        bufferbuilder.vertex(matrix, (float)x, (float)y, 0).next();
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());

        // Draw border
        RenderSystem.setShaderColor(border.getRed() / 255f, border.getGreen() / 255f, border.getBlue() / 255f, border.getAlpha() / 255f);
        RenderSystem.lineWidth(lineWidth);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferbuilder.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION);
        bufferbuilder.vertex(matrix, (float)x, (float)y, 0).next();
        bufferbuilder.vertex(matrix, (float)x, (float)y1, 0).next();
        bufferbuilder.vertex(matrix, (float)x1, (float)y1, 0).next();
        bufferbuilder.vertex(matrix, (float)x1, (float)y, 0).next();
        bufferbuilder.vertex(matrix, (float)x, (float)y, 0).next();
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
    }

    public static void drawBox(BlockPos blockPos, double height, GSColor color, int sides) {
        drawBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, height, 1, color, color.getAlpha(), sides);
    }

    public static void drawBox(Box bb, boolean check, double height, GSColor color, int sides) {
        drawBox(bb, check, height, color, color.getAlpha(), sides);
    }

    public static void drawBox(Box bb, boolean check, double height, GSColor color, int alpha, int sides) {
        if (check) {
            drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, bb.maxY - bb.minY, bb.maxZ - bb.minZ, color, alpha, sides);
        } else {
            drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, height, bb.maxZ - bb.minZ, color, alpha, sides);
        }
    }

    public static void drawBox(double x, double y, double z, double w, double h, double d, GSColor color, int alpha, int sides) {
        MatrixStack matrixStack = new MatrixStack();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        doVerticies(new Box(x, y, z, x + w, y + h, z + d), color, alpha, bufferbuilder, sides, false);
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
        RenderSystem.enableDepthTest();
    }

    public static void drawBoundingBox(BlockPos bp, double height, float width, GSColor color) {
        drawBoundingBox(getBoundingBox(bp, 1, height, 1), width, color, color.getAlpha());
    }

    public static void drawBoundingBox(Box bb, double width, GSColor color) {
        drawBoundingBox(bb, width, color, color.getAlpha());
    }

    public static void drawBoundingBox(Box bb, double width, GSColor color, int alpha) {
        MatrixStack matrixStack = new MatrixStack();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.lineWidth((float) width);
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferbuilder.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
    }

    public static void drawBoundingBoxWithSides(BlockPos blockPos, int width, GSColor color, int sides) {
        drawBoundingBoxWithSides(getBoundingBox(blockPos, 1, 1, 1), width, color, color.getAlpha(), sides);
    }

    public static void drawBoundingBoxWithSides(BlockPos blockPos, int width, GSColor color, int alpha, int sides) {
        drawBoundingBoxWithSides(getBoundingBox(blockPos, 1, 1, 1), width, color, alpha, sides);
    }

    public static void drawBoundingBoxWithSides(Box box, int width, GSColor color, int sides) {
        drawBoundingBoxWithSides(box, width, color, color.getAlpha(), sides);
    }

    public static void drawBoundingBoxWithSides(Box box, int width, GSColor color, int alpha, int sides) {
        MatrixStack matrixStack = new MatrixStack();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.lineWidth(width);
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferbuilder.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        doVerticies(box, color, alpha, bufferbuilder, sides, true);
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
    }

    private static class Points {
        double[][] point = new double[10][2];
        private int count = 0;
        private final double xCenter;
        private final double zCenter;
        public final double yMin;
        public final double yMax;
        private final float rotation;

        public Points(double yMin, double yMax, double xCenter, double zCenter, float rotation) {
            this.yMin = yMin;
            this.yMax = yMax;
            this.xCenter = xCenter;
            this.zCenter = zCenter;
            this.rotation = rotation;
        }

        public void addPoints(double x, double z) {
            x -= xCenter;
            z -= zCenter;
            double rotateX = x * Math.cos(rotation) - z * Math.sin(rotation);
            double rotateZ = x * Math.sin(rotation) + z * Math.cos(rotation);
            rotateX += xCenter;
            rotateZ += zCenter;
            point[count++] = new double[]{rotateX, rotateZ};
        }

        public double[] getPoint(int index) {
            return point[index];
        }
    }

    public static void drawBoxWithDirection(Box bb, GSColor color, float rotation, float width, int mode) {
        double xCenter = bb.minX + (bb.maxX - bb.minX) / 2;
        double zCenter = bb.minZ + (bb.maxZ - bb.minZ) / 2;

        Points square = new Points(bb.minY, bb.maxY, xCenter, zCenter, rotation);

        if (mode == 0) {
            square.addPoints(bb.minX, bb.minZ);
            square.addPoints(bb.minX, bb.maxZ);
            square.addPoints(bb.maxX, bb.maxZ);
            square.addPoints(bb.maxX, bb.minZ);
        }

        switch (mode) {
            case 0: {
                drawDirection(square, color, width);
                break;
            }
            //can add different modes in the future
        }
    }

    public static void drawDirection(Points square, GSColor color, float width) {
        for (int i = 0; i < 4; i++) {
            drawLine(square.getPoint(i)[0], square.yMin, square.getPoint(i)[1],
                    square.getPoint((i + 1) % 4)[0], square.yMin, square.getPoint((i + 1) % 4)[1],
                    color, width
            );
        }

        for (int i = 0; i < 4; i++) {
            drawLine(square.getPoint(i)[0], square.yMax, square.getPoint(i)[1],
                    square.getPoint((i + 1) % 4)[0], square.yMax, square.getPoint((i + 1) % 4)[1],
                    color, width
            );
        }

        for (int i = 0; i < 4; i++) {
            drawLine(square.getPoint(i)[0], square.yMin, square.getPoint(i)[1],
                    square.getPoint(i)[0], square.yMax, square.getPoint(i)[1],
                    color, width
            );
        }
    }

    public static void drawSphere(double x, double y, double z, float size, int slices, int stacks, float lineWidth, GSColor color) {
        Sphere sphere = new Sphere();
        MatrixStack matrixStack = new MatrixStack();

        RenderSystem.lineWidth(lineWidth);
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        matrixStack.push();
        Camera camera = mc.gameRenderer.getCamera();
        matrixStack.translate(
                x - camera.getPos().x,
                y - camera.getPos().y,
                z - camera.getPos().z
        );

        sphere.setDrawStyle(GLU.GLU_SILHOUETTE);
        sphere.draw(size, slices, stacks);

        matrixStack.pop();
    }

    public static void drawNametag(Entity entity, String[] text, GSColor color, int type) {
        Vec3d pos = EntityUtil.getInterpolatedPos(entity, mc.getTickDelta());
        drawNametag(pos.x, pos.y + entity.getHeight(), pos.z, text, color, type);
    }

    public static void drawNametag(double x, double y, double z, String[] text, GSColor color, int type) {
        ColorMain colorMain = ModuleManager.getModule(ColorMain.class);
        double dist = mc.player.squaredDistanceTo(x, y, z);
        dist = Math.sqrt(dist);
        double scale = 1, offset = 0;
        int start = 0;
        switch (type) {
            case 0:
                scale = dist / 20 * Math.pow(1.2589254, 0.1 / (dist < 25 ? 0.5 : 2));
                scale = Math.min(Math.max(scale, .5), 5);
                offset = scale > 2 ? scale / 2 : scale;
                scale /= 40;
                start = 10;
                break;
            case 1:
                scale = -((int) dist) / 6.0;
                if (scale < 1) scale = 1;
                scale *= 2.0 / 75.0;
                break;
            case 2:
                scale = 0.0018 + 0.003 * dist;
                if (dist <= 8.0) scale = 0.0245;
                start = -8;
                break;
        }

        MatrixStack matrixStack = new MatrixStack();
        Camera camera = mc.gameRenderer.getCamera();

        matrixStack.push();
        matrixStack.translate(
                x - camera.getPos().x,
                y + offset - camera.getPos().y,
                z - camera.getPos().z
        );

        // Adjust rotation to face the player
        matrixStack.multiply(camera.getRotation());
        matrixStack.scale((float)-scale, (float)-scale, (float)scale);

        if (type == 2) {
            double width = 0;
            GSColor bcolor = new GSColor(0, 0, 0, 51);
            Nametags nametags = ModuleManager.getModule(Nametags.class);

            if (nametags.customColor.getValue()) {
                bcolor = nametags.borderColor.getValue();
            }
            for (int i = 0; i < text.length; i++) {
                double w = FontUtil.getStringWidth(colorMain.customFont.getValue(), text[i]) / 2;
                if (w > width) {
                    width = w;
                }
            }
            drawBorderedRect(-width - 1, -mc.textRenderer.fontHeight, width + 2, 1, 1.8f, new GSColor(0, 4, 0, 85), bcolor);
        }

        RenderSystem.enableTexture();
        for (int i = 0; i < text.length; i++) {
            FontUtil.drawStringWithShadow(colorMain.customFont.getValue(), text[i],
                    -FontUtil.getStringWidth(colorMain.customFont.getValue(), text[i]) / 2,
                    i * (mc.textRenderer.fontHeight + 1) + start, color);
        }
        RenderSystem.disableTexture();

        if (type != 2) {
            matrixStack.pop();
        }
    }

    private static void vertex(double x, double y, double z, BufferBuilder bufferbuilder) {
        Camera camera = mc.gameRenderer.getCamera();
        bufferbuilder.vertex(
                (float) (x - camera.getPos().x),
                (float) (y - camera.getPos().y),
                (float) (z - camera.getPos().z)
        ).next();
    }

    private static void colorVertex(double x, double y, double z, GSColor color, int alpha, BufferBuilder bufferbuilder) {
        Camera camera = mc.gameRenderer.getCamera();
        bufferbuilder.vertex(
                (float) (x - camera.getPos().x),
                (float) (y - camera.getPos().y),
                (float) (z - camera.getPos().z)
        ).color(color.getRed(), color.getGreen(), color.getBlue(), alpha).next();
    }

    private static Box getBoundingBox(BlockPos bp, double width, double height, double depth) {
        double x = bp.getX();
        double y = bp.getY();
        double z = bp.getZ();
        return new Box(x, y, z, x + width, y + height, z + depth);
    }

    private static void doVerticies(Box box, GSColor color, int alpha, BufferBuilder bufferbuilder, int sides, boolean five) {
        if ((sides & GeometryMasks.Quad.EAST) != 0) {
            colorVertex(box.maxX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.maxX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.maxX, box.maxY, box.minZ, color, alpha, bufferbuilder);
            colorVertex(box.maxX, box.maxY, box.maxZ, color, alpha, bufferbuilder);
            if (five)
                colorVertex(box.maxX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
        }
        if ((sides & GeometryMasks.Quad.WEST) != 0) {
            colorVertex(box.minX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.minX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.minX, box.maxY, box.maxZ, color, alpha, bufferbuilder);
            colorVertex(box.minX, box.maxY, box.minZ, color, alpha, bufferbuilder);
            if (five)
                colorVertex(box.minX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
        }
        if ((sides & GeometryMasks.Quad.NORTH) != 0) {
            colorVertex(box.maxX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.minX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.minX, box.maxY, box.minZ, color, alpha, bufferbuilder);
            colorVertex(box.maxX, box.maxY, box.minZ, color, alpha, bufferbuilder);
            if (five)
                colorVertex(box.maxX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
        }
        if ((sides & GeometryMasks.Quad.SOUTH) != 0) {
            colorVertex(box.minX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.maxX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.maxX, box.maxY, box.maxZ, color, alpha, bufferbuilder);
            colorVertex(box.minX, box.maxY, box.maxZ, color, alpha, bufferbuilder);
            if (five)
                colorVertex(box.minX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
        }
        if ((sides & GeometryMasks.Quad.UP) != 0) {
            colorVertex(box.maxX, box.maxY, box.minZ, color, alpha, bufferbuilder);
            colorVertex(box.minX, box.maxY, box.minZ, color, alpha, bufferbuilder);
            colorVertex(box.minX, box.maxY, box.maxZ, color, alpha, bufferbuilder);
            colorVertex(box.maxX, box.maxY, box.maxZ, color, alpha, bufferbuilder);
            if (five)
                colorVertex(box.maxX, box.maxY, box.minZ, color, alpha, bufferbuilder);
        }
        if ((sides & GeometryMasks.Quad.DOWN) != 0) {
            colorVertex(box.maxX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.maxX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.minX, box.minY, box.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(box.minX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
            if (five)
                colorVertex(box.maxX, box.minY, box.minZ, color, color.getAlpha(), bufferbuilder);
        }
    }

    public static void prepare() {
        glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        glEnable(GL11.GL_LINE_SMOOTH);
        glEnable(GL32.GL_DEPTH_CLAMP);
    }

    public static void release() {
        GL11.glDisable(GL32.GL_DEPTH_CLAMP);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        RenderSystem.enableCull();
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(1.0f);
        glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
    }
}