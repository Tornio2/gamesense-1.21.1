package com.gamesense.api.util.world;

import com.gamesense.api.util.player.social.SocialManager;
import com.gamesense.mixin.mixins.accessors.TimerAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 086
 * @author Crystallinqq/Auto
 */

public class EntityUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Block isColliding(double posX, double posY, double posZ) {
        Block block = null;
        if (mc.player != null) {
            final Box bb = mc.player.getVehicle() != null ?
                    mc.player.getVehicle().getBoundingBox().contract(0.0d, 0.0d, 0.0d).offset(posX, posY, posZ) :
                    mc.player.getBoundingBox().contract(0.0d, 0.0d, 0.0d).offset(posX, posY, posZ);
            int y = (int) bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
                    block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                }
            }
        }
        return block;
    }

    public static boolean isInLiquid() {
        if (mc.player != null) {
            if (mc.player.fallDistance >= 3.0f) {
                return false;
            }
            boolean inLiquid = false;
            final Box bb = mc.player.getVehicle() != null ?
                    mc.player.getVehicle().getBoundingBox() :
                    mc.player.getBoundingBox();
            int y = (int) bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    FluidState fluidState = mc.world.getFluidState(pos);
                    Block block = mc.world.getBlockState(pos).getBlock();

                    if (!block.equals(Blocks.AIR)) {
                        if (!fluidState.isEmpty()) {
                            inLiquid = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return inLiquid;
        }
        return false;
    }

    public static void setTimer(float speed) {
//        TimerAccessor.setTickLength(50.0f / speed);
        TimerAccessor.setTimerSpeed(speed);

    }

    public static void resetTimer() {
//        TimerAccessor.setTickLength(50.0f);
        TimerAccessor.resetTimer();
    }


    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

//    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
//        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
//    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(
                entity.lastRenderX,
                entity.lastRenderY,
                entity.lastRenderZ
        ).add(getInterpolatedAmount(entity, ticks));
    }

//    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
//        return new Vec3d(
//                (entity.posX - entity.lastTickPosX) * x,
//                (entity.posY - entity.lastTickPosY) * y,
//                (entity.posZ - entity.lastTickPosZ) * z
//        );
//    }
    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.getX() - entity.lastRenderX) * x,
                (entity.getY() - entity.lastRenderY) * y,
                (entity.getZ() - entity.lastRenderZ) * z
        );
    }

    public static float clamp(float val, float min, float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }

    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleBlocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }

    public static List<BlockPos> getSquare(BlockPos pos1, BlockPos pos2) {
        List<BlockPos> squareBlocks = new ArrayList<>();
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int z1 = pos1.getZ();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        int z2 = pos2.getZ();
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x += 1) {
            for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z += 1) {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y += 1) {
                    squareBlocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return squareBlocks;
    }

    public static double[] calculateLookAt(double px, double py, double pz, Entity me) {
        double dirx = me.getX() - px;
        double diry = me.getY() - py;
        double dirz = me.getZ() - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw, pitch};
    }

    public static boolean basicChecksEntity(Entity pl) {
        return pl.getName().equals(mc.player.getName()) || SocialManager.isFriend(pl.getName()) || !pl.isAlive();
    }

    public static BlockPos getPosition(Entity pl) {
        return new BlockPos((int)Math.floor(pl.getX()), (int)Math.floor(pl.getY()), (int)Math.floor(pl.getZ()));
    }

    public static List<BlockPos> getBlocksIn(Entity pl) {
        List<BlockPos> blocks = new ArrayList<>();
        Box bb = pl.getBoundingBox();
        for (double x = Math.floor(bb.minX); x < Math.ceil(bb.maxX); x++) {
            for (double y = Math.floor(bb.minY); y < Math.ceil(bb.maxY); y++) {
                for (double z = Math.floor(bb.minZ); z < Math.ceil(bb.maxZ); z++) {
                    blocks.add(new BlockPos((int)x, (int)y, (int)z));
                }
            }
        }
        return blocks;
    }
}