package net.maximerubrum.simplyswords_tfc.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.maximerubrum.simplyswords_tfc.SimplySwordsTFC;
import net.maximerubrum.simplyswords_tfc.SimplySwordsWeapons;
import net.maximerubrum.simplyswords_tfc.TFCWeaponMetals;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TFCRecipeProvider implements DataProvider {
    private final PackOutput output;

    public TFCRecipeProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();
                String bladeName = weaponName + "_blade";
                
                if (RecipeDefinitions.RECIPES.containsKey(weapon.getName())) {
                    RecipeDefinitions.WeaponRecipeData data = RecipeDefinitions.RECIPES.get(weapon.getName());
                    
                    if (weapon.shouldCreateBlade()) {
                        // Resolve Input Item for Anvil
                        // If input is "metal_heavy_sheet", user said "tfc_items:metal_heavy_sheet".
                        // If input is "double_sheet", user said "tfc:metal/double_sheet/metal".
                        
                        String inputItem;
                        if (data.inputType.equals("metal_heavy_sheet")) {
                            // Assuming namespace tfc_items (or whatever user meant, likely an addon)
                            // User provided "tfc_items:metal_heavy_sheet"
                            // Does it have metal suffix? "tfc_items:metal_heavy_sheet" might be generic or "tfc_items:metal_heavy_sheet/copper"?
                            // Usually TFC addon items follow tfc pattern: "namespace:metal/item/metal_name"
                            // Or "namespace:item/metal_name_heavy_sheet".
                            // User prompt: "tfc_items:metal_heavy_sheet". This looks like a single item ID?
                            // But we need it for EACH metal.
                            // So it's probably "tfc_items:metal/metal_heavy_sheet/" + metal ??
                            // Or "tfc_items:metal_heavy_sheet_" + metal?
                            // I will assume "tfc_items:metal/heavy_sheet/" + metal based on standard TFC paths
                            // OR "tfc_items:" + metal + "_heavy_sheet".
                            // Let's try "tfc_items:metal/heavy_sheet/" + metal.
                            inputItem = "tfc_items:metal/heavy_sheet/" + metal.getName();
                        } else if (data.inputType.equals("metal_nail") || data.inputType.equals("meta_rivets")) {
                             inputItem = "tfc_items:metal/" + data.inputType + "/" + metal.getName(); // guessing path
                        } else {
                            // Standard TFC
                            inputItem = "tfc:metal/" + data.inputType + "/" + metal.getName();
                        }

                        // Anvil Recipe (Blade)
                        futures.add(saveRecipe(cachedOutput, 
                            createAnvilJson(bladeName, inputItem, metal.getAnvilTier(), "punch_last", "hit_not_last", "bend_any"),
                            ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "tfc/anvil/" + metal.getName() + "_" + weapon.getName() + "_blade")));

                        // Heating Blade
                        int amount = RecipeDefinitions.METAL_VALUES.getOrDefault(data.inputType, 100);
                        futures.add(saveRecipe(cachedOutput,
                            createHeatingJson(bladeName, metal.getMeltingPoint(), amount, "tfc:metal/" + metal.getName()),
                            ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "tfc/heating/" + metal.getName() + "_" + weapon.getName() + "_blade")));

                        // Heating Weapon
                        futures.add(saveRecipe(cachedOutput,
                            createHeatingJson(weaponName, metal.getMeltingPoint(), amount, "tfc:metal/" + metal.getName()),
                            ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "tfc/heating/" + metal.getName() + "_" + weapon.getName())));

                        // Advanced Shaped (Assembly)
                        // Uses Blade + Handle + Extras (Nails/Rivets)
                        String handleItem = "tfc:stick"; // Placeholder as per user
                        
                        // Extras
                        List<String> extraItems = new ArrayList<>();
                        if (data.extras != null) {
                            for (String extra : data.extras) {
                                // Resolve extra item ID
                                // "metal_nail" -> "tfc_items:metal/nail/copper"?
                                // User said "tfc_items:metal_nail".
                                // Again, assuming it varies by metal? Or generic?
                                // If it's metal specific:
                                extraItems.add("tfc_items:metal/" + extra.replace("metal_", "").replace("meta_", "") + "/" + metal.getName());
                                // This is a guess on the path structure for the addon.
                            }
                        }
                        
                        futures.add(saveRecipe(cachedOutput,
                            createAdvancedShapedJson(weaponName, bladeName, handleItem, extraItems),
                            ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "tfc/crafting/" + metal.getName() + "_" + weapon.getName())));
                    }
                } else if (weapon == SimplySwordsWeapons.CHAKRAM) {
                    // Chakram logic...
                     futures.add(saveRecipe(cachedOutput, 
                        createAnvilJson(weaponName, "tfc:metal/double_ingot/" + metal.getName(), metal.getAnvilTier(), "punch_last", "hit_not_last", "bend_any"),
                        ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "tfc/anvil/" + metal.getName() + "_chakram")));

                    futures.add(saveRecipe(cachedOutput,
                        createHeatingJson(weaponName, metal.getMeltingPoint(), 200, "tfc:metal/" + metal.getName()),
                        ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "tfc/heating/" + metal.getName() + "_chakram")));
                }
            }
        }
        
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private CompletableFuture<?> saveRecipe(CachedOutput output, JsonObject json, ResourceLocation id) {
        Path path = this.output.getOutputFolder().resolve("data/" + id.getNamespace() + "/recipes/" + id.getPath() + ".json");
        return DataProvider.saveStable(output, json, path);
    }

    private JsonObject createAnvilJson(String resultItem, String inputTag, int tier, String... rules) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "tfc:anvil");
        
        JsonObject input = new JsonObject();
        if (inputTag.contains("tfc:metal/") || inputTag.contains("tfc_items:")) {
             input.addProperty("item", inputTag); 
        } else {
            input.addProperty("tag", inputTag);
        }
        json.add("input", input);

        JsonObject result = new JsonObject();
        result.addProperty("item", SimplySwordsTFC.MOD_ID + ":" + resultItem);
        json.add("result", result);

        json.addProperty("tier", tier);
        
        JsonArray rulesArray = new JsonArray();
        for (String r : rules) {
            rulesArray.add(r);
        }
        json.add("rules", rulesArray);
        
        return json;
    }

    private JsonObject createHeatingJson(String inputItem, int temp, int amount, String fluid) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "tfc:heating");
        
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", SimplySwordsTFC.MOD_ID + ":" + inputItem);
        json.add("ingredient", ingredient);
        
        json.addProperty("temperature", temp);
        
        JsonObject result = new JsonObject();
        result.addProperty("fluid", fluid);
        result.addProperty("amount", amount);
        json.add("result_fluid", result);
        
        return json;
    }

    private JsonObject createAdvancedShapedJson(String resultItem, String bladeItem, String handleItem, List<String> extras) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "tfc:advanced_shaped_crafting");
        
        JsonArray pattern = new JsonArray();
        // Pattern depends on extras.
        // Basic: 
        //  A 
        // B
        
        // With Rivet/Nail (C):
        //  A
        // CB
        
        // Or:
        //  A
        // C
        // B
        
        // Let's try a consistent vertical pattern.
        // A: Blade
        // B: Handle
        // C: Extra
        
        if (extras != null && !extras.isEmpty()) {
            pattern.add(" A ");
            pattern.add(" C "); // Rivet/Nail in middle?
            pattern.add(" B ");
        } else {
            pattern.add(" A ");
            pattern.add(" B ");
            pattern.add("   ");
        }
        
        json.add("pattern", pattern);
        
        JsonObject key = new JsonObject();
        
        JsonObject A = new JsonObject();
        A.addProperty("item", SimplySwordsTFC.MOD_ID + ":" + bladeItem);
        key.add("A", A);
        
        JsonObject B = new JsonObject();
        B.addProperty("item", handleItem);
        key.add("B", B);
        
        if (extras != null && !extras.isEmpty()) {
            JsonObject C = new JsonObject();
            C.addProperty("item", extras.get(0)); // Use first extra
            key.add("C", C);
        }
        
        json.add("key", key);
        
        JsonObject result = new JsonObject();
        result.addProperty("item", SimplySwordsTFC.MOD_ID + ":" + resultItem);
        json.add("result", result);
        
        return json;
    }

    @Override
    public String getName() {
        return "TFC Recipes";
    }
}
