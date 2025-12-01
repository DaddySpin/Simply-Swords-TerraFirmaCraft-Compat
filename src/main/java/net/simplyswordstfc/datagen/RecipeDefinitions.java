package net.simplyswordstfc.datagen;

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
        METAL_VALUES.put("metal_heavy_sheet", 800);
        METAL_VALUES.put("knife_blade", 100);
        METAL_VALUES.put("scythe_blade", 100);
        METAL_VALUES.put("axe_head", 100);
        METAL_VALUES.put("metal_nail", 25);
        METAL_VALUES.put("meta_rivets", 25);

        String HEAVY_SHEET = "metal_heavy_sheet"; 
        String DOUBLE_SHEET = "double_sheet";
        String SHEET = "sheet";
        String DOUBLE_INGOT = "double_ingot";
        String INGOT = "ingot";

        String NAIL = "metal_nail";
        String RIVET = "meta_rivets";

        // Light / Daggers
        RECIPES.put("backhand_blade", new WeaponRecipeData("hilt", INGOT, NAIL)); 
        RECIPES.put("dagger", new WeaponRecipeData("hilt", INGOT, NAIL));
        RECIPES.put("sai", new WeaponRecipeData("hilt", INGOT, NAIL)); 
        
        // Standard Swords
        RECIPES.put("cutlass", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("katana", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("rapier", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("scimitar", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("longsword", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("twinblade", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        
        // Heavy / Large Swords
        RECIPES.put("claymore", new WeaponRecipeData("hilt", DOUBLE_SHEET, RIVET));
        RECIPES.put("grandsword", new WeaponRecipeData("hilt", DOUBLE_SHEET, RIVET));
        RECIPES.put("great_katana", new WeaponRecipeData("hilt", DOUBLE_SHEET, RIVET));
        
        // Polearms
        RECIPES.put("glaive", new WeaponRecipeData("pole", SHEET, NAIL));
        RECIPES.put("halberd", new WeaponRecipeData("pole", SHEET, NAIL));
        RECIPES.put("lance", new WeaponRecipeData("pole", DOUBLE_SHEET, RIVET));
        RECIPES.put("spear", new WeaponRecipeData("pole", SHEET, NAIL));
        RECIPES.put("great_spear", new WeaponRecipeData("pole", DOUBLE_SHEET, RIVET));
        RECIPES.put("quarterstaff", new WeaponRecipeData("pole", DOUBLE_INGOT, NAIL));

        // Axes / Hammers
        RECIPES.put("greataxe", new WeaponRecipeData("pole", DOUBLE_SHEET, RIVET));
        RECIPES.put("greathammer", new WeaponRecipeData("pole", HEAVY_SHEET, RIVET));
        RECIPES.put("scythe", new WeaponRecipeData("pole", SHEET, NAIL));

        // Others
        RECIPES.put("warglaive", new WeaponRecipeData("hilt", DOUBLE_INGOT, NAIL));
        RECIPES.put("khopesh", new WeaponRecipeData("hilt", DOUBLE_INGOT, RIVET));
        RECIPES.put("deer_horns", new WeaponRecipeData("hilt", INGOT, NAIL));
        RECIPES.put("pernach", new WeaponRecipeData("hilt", DOUBLE_INGOT, NAIL));
    }
}



