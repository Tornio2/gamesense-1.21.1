package com.gamesense.api.util.player;

import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.ClientPlayerEntity;
//import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

// Sponsored by KAMI Blue
// https://github.com/kami-blue/client/blob/master/src/main/kotlin/org/kamiblue/client/util/math/RotationUtils.kt

public class RotationUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    /**
     * Get rotation from player position to the closest hit vector in a `AxisAlignedBB`
     *
     * @param box Calculate rotation to this AABB
     */
    public static Vec2f getRotationTo(Box box) {
        ClientPlayerEntity player = mc.player;
        if (player == null) {
            return Vec2f.ZERO;
        }

//        Vec3d eyePos = player.getPositionEyes(1.0f);
        Vec3d eyePos = player.getEyePos();

        if (player.getBoundingBox().intersects(box)) { // getEntityBoundingBox() -> getBoundingBox()
            return getRotationTo(eyePos, box.getCenter());
        }

        double x = MathHelper.clamp(eyePos.x, box.minX, box.maxX);
        double y = MathHelper.clamp(eyePos.y, box.minY, box.maxY);
        double z = MathHelper.clamp(eyePos.z, box.minZ, box.maxZ);

        return getRotationTo(eyePos, new Vec3d(x, y, z));
    }

    /**
     * Get rotation from player position to another position vector
     *
     * @param posTo Calculate rotation to this position vector
     */
    public static Vec2f getRotationTo(Vec3d posTo) {
        ClientPlayerEntity player = mc.player;
        return player != null ? getRotationTo(player.getEyePos(), posTo) : Vec2f.ZERO;
    }

    /**
     * Get rotation from a position vector to another position vector
     *
     * @param posFrom Calculate rotation from this position vector
     * @param posTo   Calculate rotation to this position vector
     */
    public static Vec2f getRotationTo(Vec3d posFrom, Vec3d posTo) {
        return getRotationFromVec(posTo.subtract(posFrom));
    }

    /**
     * Get rotation from a vector
     *
     * @param vec Calculate rotation from this vector
     */
    public static Vec2f getRotationFromVec(Vec3d vec) {
        double lengthXZ = Math.hypot(vec.x, vec.z);
        double yaw = normalizeAngle(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0);
        double pitch = normalizeAngle(Math.toDegrees(-Math.atan2(vec.y, lengthXZ)));

        return new Vec2f((float) yaw, (float) pitch);
    }

    public static double normalizeAngle(double angle) {
        angle %= 360.0;

        if (angle >= 180.0) {
            angle -= 360.0;
        }

        if (angle < -180.0) {
            angle += 360.0;
        }

        return angle;
    }

    public static float normalizeAngle(float angle) {
        angle %= 360.0f;

        if (angle >= 180.0f) {
            angle -= 360.0f;
        }

        if (angle < -180.0f) {
            angle += 360.0f;
        }

        return angle;
    }
}