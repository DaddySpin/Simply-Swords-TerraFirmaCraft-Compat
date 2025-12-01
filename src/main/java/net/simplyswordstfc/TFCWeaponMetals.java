package net.simplyswordstfc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * TFC metal tiers for weapon crafting.
 * Implements Minecraft's Tier interface for use with SwordItem.
 */
public enum TFCWeaponMetals implements Tier {
    // Format: (name, qualityBonus, hexColor, durability, anvilTier, meltingPoint, rarity, attackDamage)
    COPPER("copper", 0, 0xc3725e, 600, 1, 1976, Rarity.COMMON, 0.25f),
    BRONZE("bronze", 0, 0xcaa281, 1300, 2, 1742, Rarity.COMMON, 1.0f),
    BISMUTH_BRONZE("bismuth_bronze", 0, 0x84af94, 1200, 2, 1805, Rarity.COMMON, 1.0f),
    BLACK_BRONZE("black_bronze", 0, 0x825e82, 1460, 2, 1958, Rarity.COMMON, 1.25f),
    WROUGHT_IRON("wrought_iron", 0, 0xd7d7d7, 2200, 3, 2795, Rarity.COMMON, 1.75f),
    STEEL("steel", 1, 0x829296, 3300, 4, 2804, Rarity.UNCOMMON, 2.75f),
    BLACK_STEEL("black_steel", 2, 0x505050, 4200, 5, 2705, Rarity.RARE, 4.0f),
    BLUE_STEEL("blue_steel", 3, 0x7a87b7, 6500, 6, 2804, Rarity.EPIC, 6.0f),
    RED_STEEL("red_steel", 3, 0xc8575d, 6500, 6, 2804, Rarity.EPIC, 6.0f);

    private final String name;
    private final int qualityBonus;
    private final int hexColor;
    private final int durability;
    private final int anvilTier;
    private final int meltingPoint;
    private final Rarity rarity;
    private final float attackDamage;
    private final Supplier<Ingredient> repairIngredient;

    TFCWeaponMetals(String name, int qualityBonus, int hexColor, int durability, int anvilTier, int meltingPoint, Rarity rarity, float attackDamage) {
        this.name = name;
        this.qualityBonus = qualityBonus;
        this.hexColor = hexColor;
        this.durability = durability;
        this.anvilTier = anvilTier;
        this.meltingPoint = meltingPoint;
        this.rarity = rarity;
        this.attackDamage = attackDamage;
        this.repairIngredient = () -> Ingredient.of(ItemTags.create(ResourceLocation.fromNamespaceAndPath("tfc", "metal/ingot/" + name)));
    }

    // Getters
    public String getName() { return name; }
    public int getQualityBonus() { return qualityBonus; }
    public int getHexColor() { return hexColor; }
    public int getDurability() { return durability; }
    public int getAnvilTier() { return anvilTier; }
    public int getMeltingPoint() { return meltingPoint; }
    public Rarity getRarity() { return rarity; }

    // Tier implementation
    @Override
    public int getUses() { return durability; }

    @Override
    public float getSpeed() { return 6.0f + anvilTier; }

    @Override
    public float getAttackDamageBonus() { return attackDamage; }
    
    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        // Use appropriate mining level tag based on anvil tier
        return switch (anvilTier) {
            case 1 -> BlockTags.INCORRECT_FOR_STONE_TOOL;
            case 2 -> BlockTags.INCORRECT_FOR_IRON_TOOL;
            case 3, 4 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            default -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        };
    }

    @Override
    public int getEnchantmentValue() { return 10 + anvilTier * 2; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient.get(); }
}


