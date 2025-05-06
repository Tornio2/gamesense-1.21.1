//package com.gamesense.api.util.player;
//
//import com.gamesense.client.module.ModuleManager;
//import com.gamesense.client.module.modules.combat.AutoCrystal;
//import com.gamesense.client.module.modules.combat.OffHand;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockObsidian;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.init.Enchantments;
//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemSkull;
//import net.minecraft.item.ItemStack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class InventoryUtil {
//
//    private static final MinecraftClient mc = MinecraftClient.getInstance();
//
//    public static int findObsidianSlot(boolean offHandActived, boolean activeBefore) {
//        int slot = -1;
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        if (offHandActived && ModuleManager.isModuleEnabled(OffHand.class)) {
//            if (!activeBefore) {
//                OffHand.requestItems(0);
//            }
//            return 9;
//        }
//
//        for (int i = 0; i < 9; i++) {
//            ItemStack stack = mainInventory.get(i);
//
//            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
//                continue;
//            }
//
//            Block block = ((ItemBlock) stack.getItem()).getBlock();
//            if (block instanceof BlockObsidian) {
//                slot = i;
//                break;
//            }
//        }
//        return slot;
//    }
//
//    public static int findSkullSlot(boolean offHandActived, boolean activeBefore) {
//        int slot = -1;
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        if (offHandActived) {
//            if (!activeBefore)
//                OffHand.requestItems(1);
//            return 9;
//        }
//
//        for (int i = 0; i < 9; i++) {
//            ItemStack stack = mainInventory.get(i);
//
//            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemSkull)
//                return i;
//        }
//        return slot;
//    }
//
//    public static int findTotemSlot(int lower, int upper) {
//        int slot = -1;
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//        for (int i = lower; i <= upper; i++) {
//            ItemStack stack = mainInventory.get(i);
//            if (stack == ItemStack.EMPTY || stack.getItem() != Items.TOTEM_OF_UNDYING)
//                continue;
//
//            slot = i;
//            break;
//        }
//        return slot;
//    }
//
//    public static int findFirstItemSlot(Class<? extends Item> itemToFind, int lower, int upper) {
//        int slot = -1;
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        for (int i = lower; i <= upper; i++) {
//            ItemStack stack = mainInventory.get(i);
//
//            if (stack == ItemStack.EMPTY || !(itemToFind.isInstance(stack.getItem()))) {
//                continue;
//            }
//
//            if (itemToFind.isInstance(stack.getItem())) {
//                slot = i;
//                break;
//            }
//        }
//        return slot;
//    }
//
//    public static int findFirstBlockSlot(Class<? extends Block> blockToFind, int lower, int upper) {
//        int slot = -1;
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        for (int i = lower; i <= upper; i++) {
//            ItemStack stack = mainInventory.get(i);
//
//            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
//                continue;
//            }
//
//            if (blockToFind.isInstance(((ItemBlock) stack.getItem()).getBlock())) {
//                slot = i;
//                break;
//            }
//        }
//        return slot;
//    }
//
//    public static List<Integer> findAllItemSlots(Class<? extends Item> itemToFind) {
//        List<Integer> slots = new ArrayList<>();
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        for (int i = 0; i < 36; i++) {
//            ItemStack stack = mainInventory.get(i);
//
//            if (stack == ItemStack.EMPTY || !(itemToFind.isInstance(stack.getItem()))) {
//                continue;
//            }
//
//            slots.add(i);
//        }
//        return slots;
//    }
//
//    public static List<Integer> findAllBlockSlots(Class<? extends Block> blockToFind) {
//        List<Integer> slots = new ArrayList<>();
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        for (int i = 0; i < 36; i++) {
//            ItemStack stack = mainInventory.get(i);
//
//            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
//                continue;
//            }
//
//            if (blockToFind.isInstance(((ItemBlock) stack.getItem()).getBlock())) {
//                slots.add(i);
//            }
//        }
//        return slots;
//    }
//
//    public static int findToolForBlockState(IBlockState iBlockState, int lower, int upper) {
//        int slot = -1;
//        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
//
//        double foundMaxSpeed = 0;
//        for (int i = lower; i <= upper; i++) {
//            ItemStack itemStack = mainInventory.get(i);
//
//            if (itemStack == ItemStack.EMPTY) continue;
//
//            float breakSpeed = itemStack.getDestroySpeed(iBlockState);
//
//            int efficiencySpeed = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
//
//            if (breakSpeed > 1.0F) {
//                breakSpeed += efficiencySpeed > 0 ? Math.pow(efficiencySpeed, 2) + 1 : 0;
//
//                if (breakSpeed > foundMaxSpeed) {
//                    foundMaxSpeed = breakSpeed;
//                    slot = i;
//                }
//            }
//        }
//
//        return slot;
//    }
//}

package com.gamesense.api.util.player;

import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.combat.OffHand;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static int findObsidianSlot(boolean offHandActived, boolean activeBefore) {
        int slot = -1;
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        if (offHandActived && ModuleManager.isModuleEnabled(OffHand.class)) {
            if (!activeBefore) {
                OffHand.requestItems(0);
            }
            return 9;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) {
                continue;
            }

            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (block == Blocks.OBSIDIAN) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int findSkullSlot(boolean offHandActived, boolean activeBefore) {
        int slot = -1;
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        if (offHandActived) {
            if (!activeBefore)
                OffHand.requestItems(1);
            return 9;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mainInventory.get(i);

//            if (!stack.isEmpty() && stack.getItem() instanceof SkullItem) return i;

            if (stack.isEmpty()) continue;


            // Alternative check if SkullItem doesn't work directly
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                Block block = ((BlockItem) stack.getItem()).getBlock();
                if (block == Blocks.PLAYER_HEAD || block == Blocks.PLAYER_WALL_HEAD ||
                        block == Blocks.ZOMBIE_HEAD || block == Blocks.ZOMBIE_WALL_HEAD ||
                        block == Blocks.SKELETON_SKULL || block == Blocks.SKELETON_WALL_SKULL ||
                        block == Blocks.WITHER_SKELETON_SKULL || block == Blocks.WITHER_SKELETON_WALL_SKULL ||
                        block == Blocks.CREEPER_HEAD || block == Blocks.CREEPER_WALL_HEAD ||
                        block == Blocks.DRAGON_HEAD || block == Blocks.DRAGON_WALL_HEAD) {
                    return i;
                }
            }
        }
        return slot;
    }

    public static int findTotemSlot(int lower, int upper) {
        int slot = -1;
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;
        for (int i = lower; i <= upper; i++) {
            ItemStack stack = mainInventory.get(i);
            if (stack.isEmpty() || stack.getItem() != Items.TOTEM_OF_UNDYING)
                continue;

            slot = i;
            break;
        }
        return slot;
    }

    public static int findFirstItemSlot(Class<? extends Item> itemToFind, int lower, int upper) {
        int slot = -1;
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        for (int i = lower; i <= upper; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack.isEmpty() || !(itemToFind.isInstance(stack.getItem()))) {
                continue;
            }

            if (itemToFind.isInstance(stack.getItem())) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int findFirstBlockSlot(Class<? extends Block> blockToFind, int lower, int upper) {
        int slot = -1;
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        for (int i = lower; i <= upper; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) {
                continue;
            }

            if (blockToFind.isInstance(((BlockItem) stack.getItem()).getBlock())) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static List<Integer> findAllItemSlots(Class<? extends Item> itemToFind) {
        List<Integer> slots = new ArrayList<>();
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack.isEmpty() || !(itemToFind.isInstance(stack.getItem()))) {
                continue;
            }

            slots.add(i);
        }
        return slots;
    }

    public static List<Integer> findAllBlockSlots(Class<? extends Block> blockToFind) {
        List<Integer> slots = new ArrayList<>();
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) {
                continue;
            }

            if (blockToFind.isInstance(((BlockItem) stack.getItem()).getBlock())) {
                slots.add(i);
            }
        }
        return slots;
    }

    public static int findToolForBlockState(BlockState blockState, int lower, int upper) {
        int slot = -1;
        DefaultedList<ItemStack> mainInventory = mc.player.getInventory().main;

        double foundMaxSpeed = 0;
        for (int i = lower; i <= upper; i++) {
            ItemStack itemStack = mainInventory.get(i);

            if (itemStack.isEmpty()) continue;

            float breakSpeed = itemStack.getMiningSpeedMultiplier(blockState);

//            int efficiencyLevel = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, itemStack);

            int efficiencyLevel = 0;
            if (mc.world != null) {
                efficiencyLevel = EnchantmentHelper.getLevel(
                        mc.world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(Enchantments.EFFICIENCY),
                        itemStack
                );
            }


            if (breakSpeed > 1.0F) {
                breakSpeed += efficiencyLevel > 0 ? Math.pow(efficiencyLevel, 2) + 1 : 0;

                if (breakSpeed > foundMaxSpeed) {
                    foundMaxSpeed = breakSpeed;
                    slot = i;
                }
            }
        }

        return slot;
    }
}