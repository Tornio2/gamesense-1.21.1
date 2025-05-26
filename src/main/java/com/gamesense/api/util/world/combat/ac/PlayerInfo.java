//package com.gamesense.api.util.world.combat.ac;
//
//import net.minecraft.enchantment.EnchantmentHelper;
////import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.attribute.EntityAttributes;
//import net.minecraft.entity.damage.DamageSource;
//import net.minecraft.entity.effect.StatusEffects;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.Potion;
//
//public class PlayerInfo {
////    private static final Potion RESISTANCE = Potion.getPotionById(11); - entity.hasStatusEffect(StatusEffects.RESISTANCE);
//    private static final DamageSource EXPLOSION_SOURCE = (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
//    private static final
//
//
//    public final PlayerEntity entity;
//
//    public final float totalArmourValue;
//    public final float armourToughness;
//    public final float health;
//    public final int enchantModifier;
//
//    public final boolean hasResistance;
//    public final boolean lowArmour;
//
//    public PlayerInfo(PlayerEntity entity, float armorPercent) {
//        this.entity = entity;
//
////        this.totalArmourValue = entity.getTotalArmorValue();
//        this.totalArmourValue = entity.getArmor();
////        this.armourToughness = (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
//        this.armourToughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
//        this.health = entity.getHealth() + entity.getAbsorptionAmount();
////        this.enchantModifier = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), EXPLOSION_SOURCE);
//        this.enchantModifier = EnchantmentHelper.getProtectionAmount(entity.getArmorItems(), entity, EXPLOSION_SOURCE);
//
//
//        this.hasResistance = entity.hasStatusEffect(StatusEffects.RESISTANCE);
//
//        boolean i = false;
//        for (ItemStack stack : entity.getArmorInventoryList()) {
//            if ((1.0f - ((float) stack.getItemDamage() / (float) stack.getMaxDamage())) < armorPercent) {
//                i = true;
//                break;
//            }
//        }
//        this.lowArmour = i;
//    }
//
//    public PlayerInfo(PlayerEntity entity, boolean lowArmour) {
//        this.entity = entity;
//
////        this.totalArmourValue = entity.getTotalArmorValue();
//        this.totalArmourValue = entity.getArmor();
////        this.armourToughness = (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
//        this.armourToughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
//        this.health = entity.getHealth() + entity.getAbsorptionAmount();
//        this.enchantModifier = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), EXPLOSION_SOURCE);
//
//        this.hasResistance = entity.hasStatusEffect(StatusEffects.RESISTANCE);
//
//        this.lowArmour = lowArmour;
//    }
//}


package com.gamesense.api.util.world.combat.ac;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

import static net.minecraft.enchantment.Enchantments.BLAST_PROTECTION;
import static net.minecraft.enchantment.Enchantments.PROTECTION;

public class PlayerInfo {
    // Modern way to get explosion damage source
    private static DamageSource getExplosionSource(PlayerEntity entity) {
        return entity.getWorld().getDamageSources().explosion(null, null);
    }

    public final PlayerEntity entity;

    public final float totalArmourValue;
    public final float armourToughness;
    public final float health;
    public final int enchantModifier;

    public final boolean hasResistance;
    public final boolean lowArmour;

    public PlayerInfo(PlayerEntity entity, float armorPercent) {
        this.entity = entity;

        this.totalArmourValue = entity.getArmor();
        this.armourToughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        this.health = entity.getHealth() + entity.getAbsorptionAmount();

        // Get explosion damage source and calculate protection
        DamageSource explosionSource = getExplosionSource(entity);
//        this.enchantModifier = calculateProtection(entity, explosionSource);
        this.enchantModifier = calculateProtection(entity);

        this.hasResistance = entity.hasStatusEffect(StatusEffects.RESISTANCE);

        boolean i = false;
        for (ItemStack stack : entity.getArmorItems()) {
            if (stack.isEmpty()) continue;
            if ((1.0f - ((float) stack.getDamage() / (float) stack.getMaxDamage())) < armorPercent) {
                i = true;
                break;
            }
        }
        this.lowArmour = i;
    }

    public PlayerInfo(PlayerEntity entity, boolean lowArmour) {
        this.entity = entity;

        this.totalArmourValue = entity.getArmor();
        this.armourToughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        this.health = entity.getHealth() + entity.getAbsorptionAmount();

        // Get explosion damage source and calculate protection
        DamageSource explosionSource = getExplosionSource(entity);
//        this.enchantModifier = calculateProtection(entity, explosionSource);
        this.enchantModifier = calculateProtection(entity);

        this.hasResistance = entity.hasStatusEffect(StatusEffects.RESISTANCE);

        this.lowArmour = lowArmour;
    }

    /**
     * Calculates protection value against explosions
     * This mimics the functionality of the original EnchantmentHelper.getEnchantmentModifierDamage
     */
//    private int calculateProtection(PlayerEntity player, DamageSource source) {
//        int protection = 0;
//
//        for (ItemStack stack : player.getArmorItems()) {
//            if (!stack.isEmpty()) {
//                // Get protection level with emphasis on blast protection
//                int blastProt = EnchantmentHelper.getLevel(net.minecraft.enchantment.Enchantments.BLAST_PROTECTION, stack);
//                int regularProt = EnchantmentHelper.getLevel(net.minecraft.enchantment.Enchantments.PROTECTION, stack);
//
//                // Blast protection is more effective against explosions (weight × 2)
//                // This matches the vanilla calculation but implemented directly
//                protection += blastProt * 2 + regularProt;
//            }
//        }
//
//        return Math.min(protection, 20); // Cap at maximum protection value (vanilla limit)
//    }

    private int calculateProtection(PlayerEntity player) {
        int protection = 0;

        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;

            // Get protection levels using the new registry-based approach
            int blastProt = getEnchantmentLevel(stack, BLAST_PROTECTION);
            int regularProt = getEnchantmentLevel(stack, PROTECTION);

            // Blast protection counts double for explosions (matching vanilla behavior)
            protection += blastProt * 2 + regularProt;
        }

        return Math.min(protection, 20); // Cap at maximum protection value (vanilla limit)
    }

    /**
     * Get the level of a specific enchantment on an item
     */
    private int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        // Check if the stack has enchantments
        if (stack.isEmpty() || !stack.hasEnchantments()) return 0;

        // Go through each enchantment on the item and check for a match
        for (var entry : stack.getEnchantments().getEnchantments()) {
            if (entry.toString().contains(enchantment.getValue().toString())) {
                return stack.getEnchantments().getLevel(entry);
            }
        }

        return 0;
    }

}