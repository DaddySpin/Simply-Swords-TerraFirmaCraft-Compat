package net.simplyswordstfc.datagen;

import net.simplyswordstfc.ModItems;
import net.simplyswordstfc.SimplySwordsTFC;
import net.simplyswordstfc.SimplySwordsWeapons;
import net.simplyswordstfc.TFCWeaponMetals;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Generates item tags for weapons including:
 * - c:tools (common tools tag)
 * - c:tools/swords (sword-type tools)
 * - tfc:deals_slashing_damage, tfc:deals_piercing_damage, tfc:deals_crushing_damage
 * - simplymore:weapon_types/* (weapon type tags)
 */
public class ModItemTagsProvider extends ItemTagsProvider {

    // TFC damage type tags
    private static final TagKey<Item> TFC_SLASHING = ItemTags.create(ResourceLocation.fromNamespaceAndPath("tfc", "deals_slashing_damage"));
    private static final TagKey<Item> TFC_PIERCING = ItemTags.create(ResourceLocation.fromNamespaceAndPath("tfc", "deals_piercing_damage"));
    private static final TagKey<Item> TFC_CRUSHING = ItemTags.create(ResourceLocation.fromNamespaceAndPath("tfc", "deals_crushing_damage"));

    // Common tags
    private static final TagKey<Item> C_TOOLS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools"));
    private static final TagKey<Item> C_TOOLS_SWORDS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/swords"));
    private static final TagKey<Item> C_TOOLS_MELEE = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/melee_weapons"));

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, 
                               CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SimplySwordsTFC.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();

                if (!ModItems.WEAPONS.containsKey(weaponName)) {
                    continue;
                }

                Item weaponItem = ModItems.WEAPONS.get(weaponName).get();

                // Add to common tool tags
                tag(C_TOOLS).add(weaponItem);
                tag(C_TOOLS_SWORDS).add(weaponItem);
                tag(C_TOOLS_MELEE).add(weaponItem);

                // Add to TFC damage type tags
                TagKey<Item> damageTag = switch (weapon.getDamageType()) {
                    case SLASHING -> TFC_SLASHING;
                    case PIERCING -> TFC_PIERCING;
                    case CRUSHING -> TFC_CRUSHING;
                };
                tag(damageTag).add(weaponItem);

                // Add to Simply Swords weapon type tags
                String pluralSuffix = getPluralSuffix(weapon.getName());
                String tagName = weapon.getName() + pluralSuffix;
                TagKey<Item> typeTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("simplymore", "weapon_types/" + tagName));
                tag(typeTag).add(weaponItem);
            }
        }
    }

    private String getPluralSuffix(String name) {
        if (name.endsWith("ss")) {
            return "es";
        } else if (name.endsWith("s")) {
            return "";
        } else {
            return "s";
        }
    }
}
