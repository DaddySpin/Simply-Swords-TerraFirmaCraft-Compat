package net.simplyswordstfc.datagen;

import net.simplyswordstfc.SimplySwordsTFC;
import net.simplyswordstfc.SimplySwordsWeapons;
import net.simplyswordstfc.TFCWeaponMetals;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Generates language files (en_us.json) for all items.
 */
public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, SimplySwordsTFC.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Creative tab
        add("itemGroup." + SimplySwordsTFC.MOD_ID, "Simply Swords TFC");

        // Generate translations for all weapons and blades
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();
                String displayName = formatDisplayName(metal.getName(), weapon.getName());

                // Weapon item
                add("item." + SimplySwordsTFC.MOD_ID + "." + weaponName, displayName);

                // Blade item (if applicable)
                if (weapon.shouldCreateBlade()) {
                    String bladeName = weaponName + "_blade";
                    add("item." + SimplySwordsTFC.MOD_ID + "." + bladeName, displayName + " Blade");
                }
            }
        }
    }

    /**
     * Converts snake_case names to Title Case display names.
     * e.g., "black_bronze" + "great_katana" -> "Black Bronze Great Katana"
     */
    private String formatDisplayName(String metalName, String weaponName) {
        return toTitleCase(metalName) + " " + toTitleCase(weaponName);
    }

    private String toTitleCase(String input) {
        StringBuilder result = new StringBuilder();
        for (String word : input.split("_")) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            result.append(Character.toUpperCase(word.charAt(0)));
            result.append(word.substring(1).toLowerCase());
        }
        return result.toString();
    }
}



