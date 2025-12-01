package net.simplyswordstfc.datagen;

import net.simplyswordstfc.ModItems;
import net.simplyswordstfc.SimplySwordsTFC;
import net.simplyswordstfc.SimplySwordsWeapons;
import net.simplyswordstfc.TFCWeaponMetals;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SimplySwordsTFC.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();
                String pluralSuffix;
                String wName = weapon.getName();
                if (wName.endsWith("ss")) {
                    pluralSuffix = "es";
                } else if (wName.endsWith("s")) {
                    pluralSuffix = "";
                } else {
                    pluralSuffix = "s";
                }
                String tagName = wName + pluralSuffix;
                
                TagKey<Item> typeTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("simplymore", "weapon_types/" + tagName));
                
                if (ModItems.WEAPONS.containsKey(weaponName)) {
                    tag(typeTag).add(ModItems.WEAPONS.get(weaponName).get());
                }
            }
        }
    }
}


