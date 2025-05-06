package com.gamesense.api.util.world;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MotionUtil {

    public static boolean isMoving(LivingEntity entity) {
//        return entity.moveForward != 0 || entity.moveStrafing != 0;
        return entity.forwardSpeed !=0 || entity.sidewaysSpeed != 0;
    }

    public static void setSpeed(final LivingEntity entity, final double speed) {
        double[] dir = forward(speed);
//        entity.motionX = dir[0];
//        entity.motionZ = dir[1];
        Vec3d velocity = entity.getVelocity();
        entity.setVelocity(dir[0], velocity.y, dir[1]);
    }

//    public static double getBaseMoveSpeed() {
//        double baseSpeed = 0.2873;
//        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.isPotionActive(Potion.getPotionById(1))) {
//            final int amplifier = MinecraftClient.getInstance().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
//            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
//        }
//        return baseSpeed;
//    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MinecraftClient.getInstance().player != null &&
                MinecraftClient.getInstance().player.hasStatusEffect(StatusEffects.SPEED)) {
            final int amplifier = MinecraftClient.getInstance().player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

//    public static double[] forward(final double speed) {
//        float forward = MinecraftClient.getInstance().player.movementInput.moveForward;
//        float side = MinecraftClient.getInstance().player.movementInput.moveStrafe;
//        float yaw = MinecraftClient.getInstance().player.prevRotationYaw + (MinecraftClient.getInstance().player.rotationYaw - MinecraftClient.getInstance().player.prevRotationYaw) * MinecraftClient.getInstance().getRenderPartialTicks();
//        if (forward != 0.0f) {
//            if (side > 0.0f) {
//                yaw += ((forward > 0.0f) ? -45 : 45);
//            } else if (side < 0.0f) {
//                yaw += ((forward > 0.0f) ? 45 : -45);
//            }
//            side = 0.0f;
//            if (forward > 0.0f) {
//                forward = 1.0f;
//            } else if (forward < 0.0f) {
//                forward = -1.0f;
//            }
//        }
//        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
//        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
//        final double posX = forward * speed * cos + side * speed * sin;
//        final double posZ = forward * speed * sin - side * speed * cos;
//        return new double[]{posX, posZ};
//    }

    public static double[] forward(final double speed) {
        MinecraftClient mc = MinecraftClient.getInstance();
        float forward = mc.player.input.movementForward;
        float side = mc.player.input.movementSideways;
        float yaw = mc.player.prevYaw +
                (mc.player.getYaw() - mc.player.prevYaw) *
                        mc.getRenderTickCounter().getLastFrameDuration();


        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }

        final double sin = MathHelper.sin(yaw * 0.017453292F); // Math.toRadians equivalent
        final double cos = MathHelper.cos(yaw * 0.017453292F);
        final double posX = forward * speed * -sin + side * speed * cos;
        final double posZ = forward * speed * cos - side * speed * -sin;

        return new double[]{posX, posZ};
    }
}