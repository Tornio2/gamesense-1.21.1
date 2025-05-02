package com.gamesense.api.util.player;

import com.gamesense.api.util.world.BlockUtil;
import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.combat.AutoCrystal;

import com.gamesense.mixin.mixins.accessors.MinecraftClientAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.BlockHitResult;

import java.util.ArrayList;

public class PlacementUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static int placementConnections = 0;
    private static boolean isSneaking = false;

    public static void onEnable() {
        placementConnections++;
    }

    public static void onDisable() {
        placementConnections--;
        if (placementConnections == 0) {
            if (isSneaking) {
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(
                        mc.player,
                        ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY
                ));
                isSneaking = false;
            }
        }
    }

    public static boolean placeBlock(BlockPos blockPos, Hand hand, boolean rotate, Class<? extends Block> blockToPlace) {
        int oldSlot = mc.player.getInventory().selectedSlot;
        int newSlot = InventoryUtil.findFirstBlockSlot(blockToPlace, 0, 8);

        if (newSlot == -1) {
            return false;
        }

        mc.player.getInventory().selectedSlot = newSlot;
        boolean output = place(blockPos, hand, rotate);
        mc.player.getInventory().selectedSlot = oldSlot;

        return output;
    }

    public static boolean placeItem(BlockPos blockPos, Hand hand, boolean rotate, Class<? extends Item> itemToPlace) {
        int oldSlot = mc.player.getInventory().selectedSlot;
        int newSlot = InventoryUtil.findFirstItemSlot(itemToPlace, 0, 8);

        if (newSlot == -1) {
            return false;
        }

        mc.player.getInventory().selectedSlot = newSlot;
        boolean output = place(blockPos, hand, rotate);
        mc.player.getInventory().selectedSlot = oldSlot;

        return output;
    }

    public static boolean place(BlockPos blockPos, Hand hand, boolean rotate) {
        return placeBlock(blockPos, hand, rotate, true, null);
    }

    public static boolean place(BlockPos blockPos, Hand hand, boolean rotate, ArrayList<Direction> forceSide) {
        return placeBlock(blockPos, hand, rotate, true, forceSide);
    }

    public static boolean place(BlockPos blockPos, Hand hand, boolean rotate, boolean checkAction) {
        return placeBlock(blockPos, hand, rotate, checkAction, null);
    }

    public static boolean placeBlock(BlockPos blockPos, Hand hand, boolean rotate, boolean checkAction, ArrayList<Direction> forceSide) {
        ClientPlayerEntity player = mc.player;
        ClientWorld world = mc.world;
        ClientPlayerInteractionManager interactionManager = mc.interactionManager;

        if (player == null || world == null || interactionManager == null) return false;

        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }

        Direction side = forceSide != null ? BlockUtil.getPlaceableSideExlude(blockPos, forceSide) : BlockUtil.getPlaceableSide(blockPos);

        if (side == null) {
            return false;
        }

        BlockPos neighbour = blockPos.offset(side);
        Direction opposite = side.getOpposite();

        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }

        Vec3d hitVec = Vec3d.ofCenter(neighbour).add(Vec3d.of(opposite.getVector()).multiply(0.5));
        Block neighbourBlock = world.getBlockState(neighbour).getBlock();

        if (!isSneaking && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
            player.networkHandler.sendPacket(new ClientCommandC2SPacket(
                    player,
                    ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY
            ));
            isSneaking = true;
        }

        boolean stoppedAC = false;

        if (ModuleManager.isModuleEnabled(AutoCrystal.class)) {
            AutoCrystal.stopAC = true;
            stoppedAC = true;
        }

        if (rotate) {
            BlockUtil.faceVectorPacketInstant(hitVec, true);
        }

        BlockHitResult hitResult = new BlockHitResult(hitVec, opposite, neighbour, false);
        ActionResult action = interactionManager.interactBlock(player, world, hand, hitResult);

        if (!checkAction || action == ActionResult.SUCCESS) {
            player.swingHand(hand);
            ((MinecraftClientAccessor)mc).setItemUseCooldown(4);


        }

        if (stoppedAC) {
            AutoCrystal.stopAC = false;
        }

        return action == ActionResult.SUCCESS;
    }

    public static boolean placePrecise(BlockPos blockPos, Hand hand, boolean rotate, Vec3d precise, Direction forceSide, boolean onlyRotation, boolean support) {
        ClientPlayerEntity player = mc.player;
        ClientWorld world = mc.world;
        ClientPlayerInteractionManager interactionManager = mc.interactionManager;

        if (player == null || world == null || interactionManager == null) return false;

        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }

        Direction side = forceSide == null ? BlockUtil.getPlaceableSide(blockPos) : forceSide;

        if (side == null) {
            return false;
        }

        BlockPos neighbour = blockPos.offset(side);
        Direction opposite = side.getOpposite();

        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }

        Vec3d hitVec = precise == null ?
                Vec3d.ofCenter(neighbour).add(Vec3d.of(opposite.getVector()).multiply(0.5)) :
                precise;

        Block neighbourBlock = world.getBlockState(neighbour).getBlock();

        if (!isSneaking && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
            player.networkHandler.sendPacket(new ClientCommandC2SPacket(
                    player,
                    ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY
            ));
            isSneaking = true;
        }

        boolean stoppedAC = false;

        if (ModuleManager.isModuleEnabled(AutoCrystal.class)) {
            AutoCrystal.stopAC = true;
            stoppedAC = true;
        }

        if (rotate && !support) {
            BlockUtil.faceVectorPacketInstant(hitVec, true);
        }

        if (!onlyRotation) {
            BlockHitResult hitResult = new BlockHitResult(hitVec, opposite, neighbour, false);
            ActionResult action = interactionManager.interactBlock(player, world, hand, hitResult);

            if (action == ActionResult.SUCCESS) {
                player.swingHand(hand);
                ((MinecraftClientAccessor)mc).setItemUseCooldown(4);
            }

            if (stoppedAC) {
                AutoCrystal.stopAC = false;
            }

            return action == ActionResult.SUCCESS;
        }

        return true;
    }
}





//package com.gamesense.api.util.player;
//
//import com.gamesense.api.util.world.BlockUtil;
//import com.gamesense.client.module.ModuleManager;
//import com.gamesense.client.module.modules.combat.AutoCrystal;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.network.ClientPlayerEntity;
//import net.minecraft.client.network.ClientPlayerInteractionManager;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.item.Item;
//import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
//import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
//
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.math.Direction;
//import net.minecraft.util.Hand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Vec3d;
//
//import java.util.ArrayList;
//
//public class PlacementUtil {
//
//    private static final MinecraftClient mc = MinecraftClient.getInstance();
//
//
//    private static int placementConnections = 0;
//    private static boolean isSneaking = false;
//
//    public static void onEnable() {
//        placementConnections++;
//    }
//
//    public static void onDisable() {
//        placementConnections--;
//        if (placementConnections == 0) {
//            if (isSneaking) {
//                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(
//                        mc.player,
//                        ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY
//                ));
//                isSneaking = false;
//            }
//        }
//    }
//
//    public static boolean placeBlock(BlockPos blockPos, Hand hand, boolean rotate, Class<? extends Block> blockToPlace) {
//        int oldSlot = mc.player.getInventory().selectedSlot;
//        int newSlot = InventoryUtil.findFirstBlockSlot(blockToPlace, 0, 8);
//
//        if (newSlot == -1) {
//            return false;
//        }
//
//        mc.player.getInventory().selectedSlot = newSlot;
//        boolean output = place(blockPos, hand, rotate);
//        mc.player.getInventory().selectedSlot = oldSlot;
//
//        return output;
//    }
//
//    public static boolean placeItem(BlockPos blockPos, Hand hand, boolean rotate, Class<? extends Item> itemToPlace) {
//        int oldSlot = mc.player.getInventory().selectedSlot;
//        int newSlot = InventoryUtil.findFirstItemSlot(itemToPlace, 0, 8);
//
//        if (newSlot == -1) {
//            return false;
//        }
//
//        mc.player.getInventory().selectedSlot = newSlot;
//        boolean output = place(blockPos, hand, rotate);
//        mc.player.getInventory().selectedSlot = oldSlot;
//
//        return output;
//    }
//
//    public static boolean place(BlockPos blockPos, Hand hand, boolean rotate) {
//        return placeBlock(blockPos, hand, rotate, true, null);
//    }
//
//    public static boolean place(BlockPos blockPos, Hand hand, boolean rotate, ArrayList<Direction> forceSide) {
//        return placeBlock(blockPos, hand, rotate, true, forceSide);
//    }
//
//    public static boolean place(BlockPos blockPos, Hand hand, boolean rotate, boolean checkAction) {
//        return placeBlock(blockPos, hand, rotate, checkAction, null);
//    }
//
//    public static boolean placeBlock(BlockPos blockPos, Hand hand, boolean rotate, boolean checkAction, ArrayList<Direction> forceSide) {
//        ClientPlayerEntity player = mc.player;
//        ClientWorld world = mc.world;
//        ClientPlayerInteractionManager interactionManager = mc.interactionManager;
//
//        if (player == null || world == null || interactionManager == null) return false;
//
//        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
//            return false;
//        }
//
//        Direction side = forceSide != null ? BlockUtil.getPlaceableSideExlude(blockPos, forceSide) : BlockUtil.getPlaceableSide(blockPos);
//
//        if (side == null) {
//            return false;
//        }
//
//        BlockPos neighbour = blockPos.offset(side);
//        Direction opposite = side.getOpposite();
//
//        if (!BlockUtil.canBeClicked(neighbour)) {
//            return false;
//        }
//
//        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
//        Block neighbourBlock = world.getBlockState(neighbour).getBlock();
//
//        if (!isSneaking && BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock)) {
//            player.connection.sendPacket(new PlayerActionC2SPacket(player, PlayerActionC2SPacket.Action.START_SNEAKING));
//            isSneaking = true;
//        }
//
//        boolean stoppedAC = false;
//
//        if (ModuleManager.isModuleEnabled(AutoCrystal.class)) {
//            AutoCrystal.stopAC = true;
//            stoppedAC = true;
//        }
//
//        if (rotate) {
//            BlockUtil.faceVectorPacketInstant(hitVec, true);
//        }
//
//        ActionResult action = interactionManager.interactBlock(player, world, neighbour, opposite, hitVec, hand);
//        if (!checkAction || action == ActionResult.SUCCESS) {
//            player.swingArm(hand);
//            mc.rightClickDelayTimer = 4;
//        }
//
//        if (stoppedAC) {
//            AutoCrystal.stopAC = false;
//        }
//
//        return action == ActionResult.SUCCESS;
//    }
//
//    public static boolean placePrecise(BlockPos blockPos, Hand hand, boolean rotate, Vec3d precise, Direction forceSide, boolean onlyRotation, boolean support) {
//        ClientPlayerEntity player = mc.player;
//        ClientWorld world = mc.world;
//        ClientPlayerInteractionManager interactionManager = mc.interactionManager;
//
//        if (player == null || world == null || interactionManager == null) return false;
//
//        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
//            return false;
//        }
//
//        Direction side = forceSide == null ? BlockUtil.getPlaceableSide(blockPos) : forceSide;
//
//        if (side == null) {
//            return false;
//        }
//
//        BlockPos neighbour = blockPos.offset(side);
//        Direction opposite = side.getOpposite();
//
//        if (!BlockUtil.canBeClicked(neighbour)) {
//            return false;
//        }
//
//        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
//        Block neighbourBlock = world.getBlockState(neighbour).getBlock();
//
//        if (!isSneaking && BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock)) {
//            player.connection.sendPacket(new PlayerActionC2SPacket(player, PlayerActionC2SPacket.Action.START_SNEAKING));
//            isSneaking = true;
//        }
//
//        boolean stoppedAC = false;
//
//        if (ModuleManager.isModuleEnabled(AutoCrystal.class)) {
//            AutoCrystal.stopAC = true;
//            stoppedAC = true;
//        }
//
//        if (rotate && !support) {
//            BlockUtil.faceVectorPacketInstant(precise == null ? hitVec : precise, true);
//        }
//
//        if (!onlyRotation) {
//            ActionResult action = interactionManager.interactBlock(player, world, neighbour, opposite, precise == null ? hitVec : precise, hand);
//            if (action == ActionResult.SUCCESS) {
//                player.swingArm(hand);
//                mc.rightClickDelayTimer = 4;
//            }
//
//            if (stoppedAC) {
//                AutoCrystal.stopAC = false;
//            }
//
//            return action == ActionResult.SUCCESS;
//        }
//
//        return true;
//    }
//}