package com.gamesense.api.util.world.combat;

import com.gamesense.api.util.player.PlayerUtil;
import com.gamesense.api.util.world.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

import java.util.List;
import java.util.stream.Collectors;

public class CrystalUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean canPlaceCrystal(BlockPos blockPos, boolean newPlacement) {
        if (notValidBlock(mc.world.getBlockState(blockPos).getBlock())) return false;

        BlockPos posUp = blockPos.up();

        if (newPlacement) {
            if (!mc.world.getBlockState(posUp).isAir()) return false;
        } else {
            if (isInvalidForCrystal(posUp) || isInvalidForCrystal(posUp.up())) return false;
        }

        Box box = new Box(
                posUp.getX(), posUp.getY(), posUp.getZ(),
                posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0
        );

        return mc.world.getOtherEntities(null, box, Entity::isAlive).isEmpty();
    }

    public static boolean canPlaceCrystalExcludingCrystals(BlockPos blockPos, boolean newPlacement) {
        if (notValidBlock(mc.world.getBlockState(blockPos).getBlock())) return false;

        BlockPos posUp = blockPos.up();

        if (newPlacement) {
            if (!mc.world.getBlockState(posUp).isAir()) return false;
        } else {
            if (isInvalidForCrystal(posUp) || isInvalidForCrystal(posUp.up())) return false;
        }

        Box box = new Box(
                posUp.getX(), posUp.getY(), posUp.getZ(),
                posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0
        );

        return mc.world.getOtherEntities(null, box, entity -> entity.isAlive() && !(entity instanceof EndCrystalEntity)).isEmpty();
    }

    private static boolean isInvalidForCrystal(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
//        return state.getMaterial().isLiquid() || !state.isReplaceable(mc.world, pos);
        return !state.getFluidState().isEmpty() || !state.isReplaceable();
    }

    public static boolean notValidBlock(Block block) {
        return block != Blocks.BEDROCK && block != Blocks.OBSIDIAN;
    }

    // Remove the old material method since we now use isInvalidForCrystal
    // public static boolean notValidMaterial(Material material) {
    //    return material.isLiquid() || !material.isReplaceable();
    // }

    public static List<BlockPos> findCrystalBlocks(float placeRange, boolean mode) {
        return EntityUtil.getSphere(PlayerUtil.getPlayerPos(), placeRange, (int) placeRange, false, true, 0).stream().filter(pos -> CrystalUtil.canPlaceCrystal(pos, mode)).collect(Collectors.toList());
    }

    public static List<BlockPos> findCrystalBlocksExcludingCrystals(float placeRange, boolean mode) {
        return EntityUtil.getSphere(PlayerUtil.getPlayerPos(), placeRange, (int) placeRange, false, true, 0).stream().filter(pos -> CrystalUtil.canPlaceCrystalExcludingCrystals(pos, mode)).collect(Collectors.toList());
    }

    public static void breakCrystal(Entity crystal) {
        mc.interactionManager.attackEntity(mc.player, crystal);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    public static void breakCrystalPacket(Entity crystal) {
        mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(crystal, false));
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}