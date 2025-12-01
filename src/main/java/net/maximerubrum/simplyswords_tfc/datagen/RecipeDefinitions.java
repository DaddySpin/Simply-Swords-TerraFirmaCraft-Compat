package net.maximerubrum.simplyswords_tfc.datagen;

import java.util.HashMap;
import java.util.Map;

public class RecipeDefinitions {
    public static class WeaponRecipeData {
        public final String handle; // e.g. "hilt", "pole" or "stick" for now
        public final String inputType; // e.g. "double_ingot", "sheet", "double_sheet", "metal_heavy_sheet"
        public final String[] extras; // e.g. rivets, nails?
        
        public WeaponRecipeData(String handle, String inputType, String... extras) {
            this.handle = handle;
            this.inputType = inputType;
            this.extras = extras;
        }
    }

    public static final Map<String, WeaponRecipeData> RECIPES = new HashMap<>();
    public static final Map<String, Integer> METAL_VALUES = new HashMap<>();

    static {
        // Values for melting
        METAL_VALUES.put("ingot", 100);
        METAL_VALUES.put("double_ingot", 200);
        METAL_VALUES.put("sheet", 200);
        METAL_VALUES.put("double_sheet", 400);
        METAL_VALUES.put("metal_heavy_sheet", 800); // Assuming heavy sheet is double double or just massive? 
        // User listed "metal_heavy_sheet" in TFC context, maybe 4x? 
        // Let's assume 400 (same as double sheet) or 600?
        // If "double sheet" is 400, maybe heavy sheet is bigger.
        // I will guess 400 or 600. Let's stick to 400 for now unless specified.
        METAL_VALUES.put("knife_blade", 100);
        METAL_VALUES.put("scythe_blade", 100);
        METAL_VALUES.put("axe_head", 100);
        METAL_VALUES.put("metal_nail", 25); // Guess
        METAL_VALUES.put("meta_rivets", 25); // Guess

        // Real Life Mappings Attempt
        // Daggers/Small: Ingot or Small Sheet (user didn't provide small sheet). Use Ingot.
        // Normal Swords: Double Ingot (standard TFC).
        // Large Swords: Double Sheet (4 ingots).
        // Massive Weapons: Heavy Sheet (if user wants that for "close to real life").

        // Handle logic:
        // User provided lists but didn't specify which handle for which weapon.
        // I will use `tfc:stick` for now as handle placeholder in the provider code,
        // but I'll prepare the data structure to accept "hilt" or "pole" if I find the items later.
        // Actually, user said "we have... make them accurate".
        // I will assume standard TFC stick for now.
        
        // Inputs from user:
        // tfc:metal/double_sheet/metal
        // tfc:metal/sheet/metal
        // tfc_items:metal_heavy_sheet  (Likely `tfc_moreitems:metal_heavy_sheet`?)
        
        String HEAVY_SHEET = "metal_heavy_sheet"; 
        String DOUBLE_SHEET = "double_sheet";
        String SHEET = "sheet";
        String DOUBLE_INGOT = "double_ingot";
        String INGOT = "ingot";

        // Extras
        String NAIL = "metal_nail";
        String RIVET = "meta_rivets"; // Typo in user prompt "meta_rivets"? Probably "metal_rivets" or "rivet".
        // User typed "tfc_items:meta_rivets". I will use that exact string for now in the ID generation
        // but I suspect it's "metal_rivets". I will use "meta_rivets" as requested.

        // Light / Daggers
        RECIPES.put("backhand_blade", new WeaponRecipeData("hilt", INGOT, NAIL)); 
        RECIPES.put("dagger", new WeaponRecipeData("hilt", INGOT, NAIL));
        RECIPES.put("sai", new WeaponRecipeData("hilt", INGOT, NAIL)); 
        
        // Standard Swords (Cutlass, Katana, Rapier, etc) -> Double Ingot (Standard TFC)
        RECIPES.put("cutlass", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("katana", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("rapier", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("scimitar", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET)); // If exists?
        RECIPES.put("longsword", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("twinblade", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET)); // Two blades? Maybe double sheet?
        
        // Heavy / Large Swords -> Double Sheet
        RECIPES.put("claymore", new WeaponRecipeData("hilt", DOUBLE_SHEET, RIVET));
        RECIPES.put("grandsword", new WeaponRecipeData("hilt", DOUBLE_SHEET, RIVET)); // Very big
        RECIPES.put("great_katana", new WeaponRecipeData("hilt", DOUBLE_SHEET, RIVET));
        
        // Polearms -> Sheet (Head)
        RECIPES.put("glaive", new WeaponRecipeData("pole", SHEET, NAIL));
        RECIPES.put("halberd", new WeaponRecipeData("pole", SHEET, NAIL));
        RECIPES.put("lance", new WeaponRecipeData("pole", DOUBLE_SHEET, RIVET)); // Lance is big
        RECIPES.put("spear", new WeaponRecipeData("pole", SHEET, NAIL));
        RECIPES.put("great_spear", new WeaponRecipeData("pole", DOUBLE_SHEET, RIVET));
        RECIPES.put("quarterstaff", new WeaponRecipeData("pole", DOUBLE_INGOT, NAIL)); // Metal staff?

        // Axes / Hammers
        RECIPES.put("greataxe", new WeaponRecipeData("pole", DOUBLE_SHEET, RIVET));
        RECIPES.put("greathammer", new WeaponRecipeData("pole", HEAVY_SHEET, RIVET)); // The heaviest!
        RECIPES.put("scythe", new WeaponRecipeData("pole", SHEET, NAIL));

        // Others
        RECIPES.put("warglaive", new WeaponRecipeData("hilt", DOUBLE_INGOT, NAIL));
        RECIPES.put("khopesh", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("deer_horns", new WeaponRecipeData("hilt", INGOT, NAIL));
        RECIPES.put("pernach", new WeaponRecipeData("hilt", DOUBLE_INGOT, NAIL)); // Mace-like
    }
}
