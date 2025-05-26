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
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;
import net.minecraft.world.RaycastContext;


import static net.minecraft.enchantment.Enchantments.BLAST_PROTECTION;
import static net.minecraft.enchantment.Enchantments.PROTECTION;

public class DamageUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float finalDamage = 1.0f;
        try {
            float doubleExplosionSize = 12.0F;
            Vec3d explosionPos = new Vec3d(posX, posY, posZ);

            // Calculate distance
            double distancedSize = entity.squaredDistanceTo(explosionPos);
            distancedSize = Math.sqrt(distancedSize) / doubleExplosionSize;

            // Get block density
//            double blockDensity = entity.getWorld().getBlockDensity(explosionPos, entity.getBoundingBox());
            double blockDensity = getBlockDensity(entity.getWorld(), explosionPos, entity.getBoundingBox());

            double v = (1.0D - distancedSize) * blockDensity;
            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D));

            if (entity instanceof LivingEntity) {
                // Create a dummy explosion for calculation
                Explosion explosion = new Explosion(
                        mc.world,
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
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Get explosion damage source
//            var damageSource = entity.getWorld().getDamageSources().explosion(explosion, null);
            var damageSource = entity.getWorld().getDamageSources().explosion(explosion);

            // Apply armor reduction
            float armor = player.getArmor();
            float armorToughness = (float) player.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            damage = getDamageAfterArmorAbsorb(damage, armor, armorToughness);

            // Calculate enchantment protection
            int protection = calculateProtection(player);
            float f = MathHelper.clamp(protection, 0.0F, 20.0F);
            damage *= 1.0F - f / 25.0F;

            // Apply resistance effect
            if (entity.hasStatusEffect(StatusEffects.RESISTANCE)) {
                damage = damage - (damage / 4);
            }
            damage = Math.max(damage, 0.0F);
            return damage;
        }

        // For non-player living entities
        float armor = entity.getArmor();
        float armorToughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        damage = getDamageAfterArmorAbsorb(damage, armor, armorToughness);
        return damage;
    }

    public static float calculateDamageThreaded(double posX, double posY, double posZ, PlayerInfo playerInfo) {
        float finalDamage = 1.0f;
        try {
            float doubleExplosionSize = 12.0F;
            Vec3d explosionPos = new Vec3d(posX, posY, posZ);

            // Calculate distance
            double distancedSize = playerInfo.entity.squaredDistanceTo(explosionPos);
            distancedSize = Math.sqrt(distancedSize) / doubleExplosionSize;

            // Get block density
//            double blockDensity = playerInfo.entity.getWorld().getBlockDensity(explosionPos, playerInfo.entity.getBoundingBox());
            double blockDensity = getBlockDensity(playerInfo.entity.getWorld(), explosionPos, playerInfo.entity.getBoundingBox());


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
        float f = MathHelper.clamp(playerInfo.enchantModifier, 0.0F, 20.0F);
        damage *= 1.0F - f / 25.0F;

        // Apply resistance effect
        if (playerInfo.hasResistance) {
            damage = damage - (damage / 4);
        }
        damage = Math.max(damage, 0.0F);
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        Difficulty difficulty = mc.world.getDifficulty();

        switch (difficulty) {
            case PEACEFUL:
                return 0;
            case EASY:
                return damage * 0.5f;
            case NORMAL:
                return damage;
            case HARD:
            default:
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

    /**
     * Calculate protection value based on armor enchantments
     */
    private static int calculateProtection(PlayerEntity player) {
        int protection = 0;

        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;

            // Get protection levels
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
    private static int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
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

    /**
     * Custom implementation of getBlockDensity that works in 1.21.1
     * This performs ray-casting from the explosion to points on the entity's bounding box
     * to determine how much of the explosion is blocked by terrain.
     */
    public static float getBlockDensity(World world, Vec3d explosionPos, Box boundingBox) {
        // Number of sample points to check - more means more precise but slower
        final int numSteps = 16;
        double stepX = (boundingBox.maxX - boundingBox.minX) / numSteps;
        double stepY = (boundingBox.maxY - boundingBox.minY) / numSteps;
        double stepZ = (boundingBox.maxZ - boundingBox.minZ) / numSteps;

        // Get bounding box center
        double centerX = (boundingBox.minX + boundingBox.maxX) / 2.0;
        double centerY = (boundingBox.minY + boundingBox.maxY) / 2.0;
        double centerZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0;

        // Count hits and sample points
        int totalSamples = 0;
        int clearedSamples = 0;

        // Sample points on the bounding box faces (6 faces)
        // For each face, we'll check a grid of points
        for (int faceX = 0; faceX < 2; faceX++) {
            for (int faceY = 0; faceY < 2; faceY++) {
                for (int faceZ = 0; faceZ < 2; faceZ++) {
                    // Skip center samples - only include faces
                    if (faceX == 1 && faceY == 1 && faceZ == 1) continue;

                    // Get face corner position
                    double posX = faceX == 0 ? boundingBox.minX : boundingBox.maxX;
                    double posY = faceY == 0 ? boundingBox.minY : boundingBox.maxY;
                    double posZ = faceZ == 0 ? boundingBox.minZ : boundingBox.maxZ;

                    // Create a ray from explosion to this point
                    Vec3d targetPos = new Vec3d(posX, posY, posZ);

                    // Check if ray is obstructed by blocks
                    if (canSeePoint(world, explosionPos, targetPos)) {
                        clearedSamples++;
                    }
                    totalSamples++;
                }
            }
        }

        // Create more sample points between corners for better precision
        for (double x = boundingBox.minX; x <= boundingBox.maxX; x += stepX) {
            for (double y = boundingBox.minY; y <= boundingBox.maxY; y += stepY) {
                for (double z = boundingBox.minZ; z <= boundingBox.maxZ; z += stepZ) {
                    // Only consider points on the bounding box surface
                    if (isPointOnBoxSurface(x, y, z, boundingBox)) {
                        Vec3d targetPos = new Vec3d(x, y, z);

                        // Check if ray is obstructed by blocks
                        if (canSeePoint(world, explosionPos, targetPos)) {
                            clearedSamples++;
                        }
                        totalSamples++;
                    }
                }
            }
        }

        // Add the center of each face as important sample points
        double[][] faceCenters = {
                {boundingBox.minX, centerY, centerZ}, // -X face
                {boundingBox.maxX, centerY, centerZ}, // +X face
                {centerX, boundingBox.minY, centerZ}, // -Y face
                {centerX, boundingBox.maxY, centerZ}, // +Y face
                {centerX, centerY, boundingBox.minZ}, // -Z face
                {centerX, centerY, boundingBox.maxZ}  // +Z face
        };

        for (double[] point : faceCenters) {
            Vec3d targetPos = new Vec3d(point[0], point[1], point[2]);

            // Check if ray is obstructed by blocks
            if (canSeePoint(world, explosionPos, targetPos)) {
                clearedSamples++;
            }
            totalSamples++;
        }

        return totalSamples > 0 ? (float) clearedSamples / totalSamples : 0f;
    }

    /**
     * Check if a ray from point A to point B is not obstructed by solid blocks
     */
    private static boolean canSeePoint(World world, Vec3d startPos, Vec3d endPos) {
        // Create a ray-casting context (VISUAL = can see through water, etc.)
        RaycastContext context = new RaycastContext(
                startPos,
                endPos,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.ANY,
                (Entity) null
        );

        // Cast the ray and check if it hits a block
        BlockHitResult result = world.raycast(context);

        // If it hits a block before reaching the end point, check if the block is solid
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = result.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);

            // Check if block is solid (non-passable)
            // Some blocks like glass would reduce damage but not completely block it
            float blockResistance = blockState.getHardness(world, blockPos);

            // Return false (ray is blocked) if the block has significant blast resistance
            return blockResistance < 4.0f; // Low resistance blocks allow more explosion through
        }

        // No solid block hit, ray is clear
        return true;
    }

    /**
     * Checks if a point lies on the surface of a bounding box
     */
    private static boolean isPointOnBoxSurface(double x, double y, double z, Box box) {
//        double epsilon = 0.001; // Small tolerance value
//
//        // Check if the point is on any of the six faces
//        return (Math.abs(x - box.minX) < epsilon) ||
//                (Math.abs(x - box.maxX) < epsilon) ||
//                (Math.abs(y - box.minY) < epsilon) ||
//                (Math.abs(y - box.maxY) < epsilon) ||
//                (Math.abs(z - box.minZ) < epsilon) ||
//                (Math.abs(z - box.maxZ) < epsilon);
//

        boolean onX = x <= box.minX || x >= box.maxX;
        boolean onY = y <= box.minY || y >= box.maxY;
        boolean onZ = z <= box.minZ || z >= box.maxZ;
        return onX || onY || onZ;

    }
}