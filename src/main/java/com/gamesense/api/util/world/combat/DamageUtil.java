//package com.gamesense.api.util.world.combat;
//
//import com.gamesense.api.util.world.combat.ac.PlayerInfo;
//import net.minecraft.client.Minecraft;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.potion.Potion;
//import net.minecraft.util.CombatRules;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.Explosion;
//
//public class DamageUtil {
//
//    private static final Minecraft mc = Minecraft.getMinecraft();
//
//    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
//        float finalDamage = 1.0f;
//        try {
//            float doubleExplosionSize = 12.0F;
//            double distancedSize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
//            double blockDensity = entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());
//            double v = (1.0D - distancedSize) * blockDensity;
//            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
//
//            if (entity instanceof EntityLivingBase) {
//                finalDamage = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
//            }
//        } catch (NullPointerException ignored) {
//        }
//
//        return finalDamage;
//    }
//
//    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
//        if (entity instanceof EntityPlayer) {
//            EntityPlayer ep = (EntityPlayer) entity;
//            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
//            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
//
//            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
//            float f = MathHelper.clamp(k, 0.0F, 20.0F);
//            damage *= 1.0F - f / 25.0F;
//
//            if (entity.isPotionActive(Potion.getPotionById(11))) {
//                damage = damage - (damage / 4);
//            }
//            damage = Math.max(damage, 0.0F);
//            return damage;
//        }
//        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
//        return damage;
//    }
//
//    public static float calculateDamageThreaded(double posX, double posY, double posZ, PlayerInfo playerInfo) {
//        float finalDamage = 1.0f;
//        try {
//            float doubleExplosionSize = 12.0F;
//            double distancedSize = playerInfo.entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
//            double blockDensity = playerInfo.entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), playerInfo.entity.getEntityBoundingBox());
//            double v = (1.0D - distancedSize) * blockDensity;
//            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
//
//            finalDamage = getBlastReductionThreaded(playerInfo, getDamageMultiplied(damage));
//        } catch (NullPointerException ignored) {
//        }
//
//        return finalDamage;
//    }
//
//    public static float getBlastReductionThreaded(PlayerInfo playerInfo, float damage) {
//        damage = CombatRules.getDamageAfterAbsorb(damage, playerInfo.totalArmourValue, playerInfo.armourToughness);
//
//        float f = MathHelper.clamp(playerInfo.enchantModifier, 0.0F, 20.0F);
//        damage *= 1.0F - f / 25.0F;
//
//        if (playerInfo.hasResistance) {
//            damage = damage - (damage / 4);
//        }
//        damage = Math.max(damage, 0.0F);
//        return damage;
//    }
//
//    private static float getDamageMultiplied(float damage) {
//        int diff = mc.world.getDifficulty().getId();
//        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
//    }
//}


package com.gamesense.api.util.world.combat;

import com.gamesense.api.util.world.combat.ac.PlayerInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.explosion.Explosion;

public class DamageUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float finalDamage = 1.0f;
        try {
            float doubleExplosionSize = 12.0F;
            Vec3d explosionPos = new Vec3d(posX, posY, posZ);
            double distancedSize = entity.getPos().distanceTo(explosionPos) / doubleExplosionSize;
            double blockDensity = entity.getWorld().getBlockDensity(explosionPos, entity.getBoundingBox());
            double v = (1.0D - distancedSize) * blockDensity;
            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D));

            if (entity instanceof LivingEntity) {
                // Create a dummy explosion for damage calculation
                Explosion explosion = new Explosion(
                        mc.world,
                        null,
                        null,
                        posX, posY, posZ,
                        6F, false,
                        Explosion.DestructionType.DESTROY
                );
                finalDamage = getBlastReduction((LivingEntity) entity, getDamageMultiplied(damage), explosion);
            }
        } catch (NullPointerException ignored) {
        }
        return finalDamage;
    }

    public static float getBlastReduction(LivingEntity entity, float damage, Explosion explosion) {
        DamageSource ds = DamageSource.explosion(explosion);

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Apply armor reduction
            float armor = player.getArmor();
            float armorToughness = (float) player.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            damage = getDamageAfterArmorAbsorb(damage, armor, armorToughness);

            // Apply enchantment protection
            int protection = EnchantmentHelper.getProtectionAmount(player.getArmorItems(), ds);
            float protectionFactor = MathHelper.clamp(protection, 0.0F, 20.0F);
            damage *= 1.0F - protectionFactor / 25.0F;

            // Apply resistance effect
            if (entity.hasStatusEffect(StatusEffects.RESISTANCE)) {
                damage = damage - (damage / 4);  // 25% reduction from resistance
            }

            return Math.max(damage, 0.0F);
        }

        // For non-player living entities
        float armor = entity.getArmor();
        float armorToughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        damage = getDamageAfterArmorAbsorb(damage, armor, armorToughness);

        return Math.max(damage, 0.0F);
    }

    public static float calculateDamageThreaded(double posX, double posY, double posZ, PlayerInfo playerInfo) {
        float finalDamage = 1.0f;
        try {
            float doubleExplosionSize = 12.0F;
            Vec3d explosionPos = new Vec3d(posX, posY, posZ);
            double distancedSize = playerInfo.entity.getPos().distanceTo(explosionPos) / doubleExplosionSize;
            double blockDensity = playerInfo.entity.getWorld().getBlockDensity(
                    explosionPos,
                    playerInfo.entity.getBoundingBox()
            );
            double v = (1.0D - distancedSize) * blockDensity;
            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D));

            finalDamage = getBlastReductionThreaded(playerInfo, getDamageMultiplied(damage));
        } catch (NullPointerException ignored) {
        }
        return finalDamage;
    }

    public static float getBlastReductionThreaded(PlayerInfo playerInfo, float damage) {
        // Apply armor reduction
        damage = getDamageAfterArmorAbsorb(damage, playerInfo.totalArmourValue, playerInfo.armourToughness);

        // Apply enchantment protection
        float protectionFactor = MathHelper.clamp(playerInfo.enchantModifier, 0.0F, 20.0F);
        damage *= 1.0F - protectionFactor / 25.0F;

        // Apply resistance effect
        if (playerInfo.hasResistance) {
            damage = damage - (damage / 4);
        }

        return Math.max(damage, 0.0F);
    }

    private static float getDamageMultiplied(float damage) {
        Difficulty difficulty = mc.world.getDifficulty();

        if (difficulty == Difficulty.PEACEFUL) {
            return 0;
        } else if (difficulty == Difficulty.EASY) {
            return damage * 0.5f;
        } else if (difficulty == Difficulty.NORMAL) {
            return damage;
        } else { // HARD
            return damage * 1.5f;
        }
    }

    /**
     * Implements armor damage reduction formula from vanilla Minecraft
     */
    private static float getDamageAfterArmorAbsorb(float damage, float armor, float armorToughness) {
        float f = 2.0F + armorToughness / 4.0F;
        float f1 = MathHelper.clamp(armor - damage / f, armor * 0.2F, 20.0F);
        return damage * (1.0F - f1 / 25.0F);
    }
}