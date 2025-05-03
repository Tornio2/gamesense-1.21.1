package com.gamesense.api.util.world;

import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
//import net.minecraft.init.Blocks;
import net.minecraft.block.Blocks;
//import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
//import net.minecraft.util.EnumFacing;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockUtil {

    public static final List blackList;
    public static final List shulkerList;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static BlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static boolean checkForNeighbours(BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {

            for (Direction side : Direction.values()) {
                BlockPos neighbour = blockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private static boolean hasNeighbour(BlockPos blockPos) {
        for (Direction side : Direction.values()) {
            BlockPos neighbour = blockPos.offset(side);
            // dont make fun of me i literally have no idea how to do this
//            if (!mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
//                return true;
//            }
            BlockState state = mc.world.getBlockState(neighbour);
            if (!state.isAir() && !state.getFluidState().isIn(FluidTags.WATER) && !state.getFluidState().isIn(FluidTags.LAVA)) {
                return true;
            }
        }
        return false;
    }


    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static Block getBlock(double x, double y, double z) {
        return mc.world.getBlockState(new BlockPos((int) x, (int) y, (int) z)).getBlock();
    }

    public static boolean canBeClicked(BlockPos pos) {
//        return getBlock(pos).canCollideCheck(getState(pos), false);
        BlockState state = getState(pos);
        return !state.isAir() && state.getOutlineShape(mc.world, pos).getBoundingBox().getAverageSideLength() > 0;


    }

    public static void faceVectorPacketInstant(Vec3d vec, Boolean roundAngles) {
        float[] rotations = getNeededRotations2(vec);

        // mc.player.connection.sendPacket -> mc.player.networkHandler.sendPacket
//        mc.player.connection.sendPacket(new PlayerMoveC2SPacket.Rotation(rotations[0], roundAngles ? MathHelper.normalizeAngle((int) rotations[1], 360) : rotations[1], mc.player.onGround));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                rotations[0],
                roundAngles ? MathHelper.wrapDegrees((int) rotations[1]) : rotations[1],
                mc.player.isOnGround())
        );
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

//        return new float[]{
//                mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
//                mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)
//        };
        return new float[]{
                mc.player.getYaw() + MathHelper.wrapDegrees(yaw - mc.player.getYaw()), // Updated from rotationYaw
                mc.player.getPitch() + MathHelper.wrapDegrees(pitch - mc.player.getPitch()) // Updated from rotationPitch
        };
    }

    public static Vec3d getEyesPos() {
//        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        return new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());

    }

    public static List<BlockPos> getCircle(final BlockPos loc, final int y, final float r, final boolean hollow) {
        final List<BlockPos> circleblocks = new ArrayList<>();
        final int cx = loc.getX();
        final int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
                if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                    final BlockPos l = new BlockPos(x, y, z);
                    circleblocks.add(l);
                }
            }
        }
        return circleblocks;
    }

    static {
        blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    }

    public static Direction getPlaceableSide(BlockPos pos) {

        for (Direction side : Direction.values()) {

            BlockPos neighbour = pos.offset(side);

//            if (!mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
//                continue;
//            }
            BlockState state = mc.world.getBlockState(neighbour);
            if (state.isAir() || state.getOutlineShape(mc.world, neighbour).isEmpty()) { continue;}

            BlockState blockState = mc.world.getBlockState(neighbour);
//            if (!blockState.getMaterial().isReplaceable()) {
//                return side;
//            }
            if (!state.isAir() && !state.getFluidState().isIn(FluidTags.WATER) && !state.getFluidState().isIn(FluidTags.LAVA)) {
                return side;
            }

        }

        return null;
    }

    public static Direction getPlaceableSideExlude(BlockPos pos, ArrayList<Direction> excluding) {

        for (Direction side : Direction.values()) {

            if (!excluding.contains(side)) {

                BlockPos neighbour = pos.offset(side);

//                if (!mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
//                    continue;
//                }
                BlockState state = mc.world.getBlockState(neighbour);
                if (state.isAir() || state.getOutlineShape(mc.world, neighbour).isEmpty()) { continue;}

                BlockState blockState = mc.world.getBlockState(neighbour);
//                if (!blockState.getMaterial().isReplaceable()) {
//                    return side;
//                }
                if (!state.isAir() && !state.getFluidState().isIn(FluidTags.WATER) && !state.getFluidState().isIn(FluidTags.LAVA)) {
                    return side;
                }
            }
        }

        return null;
    }

    public static Vec3d getCenterOfBlock(double playerX, double playerY, double playerZ) {

        double newX = Math.floor(playerX) + 0.5;
        double newY = Math.floor(playerY);
        double newZ = Math.floor(playerZ) + 0.5;

        return new Vec3d(newX, newY, newZ);
    }
}