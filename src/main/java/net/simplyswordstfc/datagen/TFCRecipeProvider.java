package net.simplyswordstfc.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.simplyswordstfc.SimplySwordsTFC;
import net.simplyswordstfc.SimplySwordsWeapons;
import net.simplyswordstfc.TFCWeaponMetals;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generates TFC-specific recipes: anvil forging, heating, and assembly crafting.
 */
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

                if (weapon == SimplySwordsWeapons.CHAKRAM) {
                    // Chakram: no blade, anvil makes weapon directly from double ingot
                    futures.add(saveRecipe(cachedOutput,
                            createAnvilRecipeWithItem(weaponName, "tfc:metal/double_ingot/" + metal.getName(), metal.getAnvilTier(),
                                    "punch_last", "hit_not_last", "bend_any"),
                            "anvil/" + weaponName));

                    futures.add(saveRecipe(cachedOutput,
                            createHeatingRecipe(weaponName, metal.getMeltingPoint(), 200, metal.getName()),
                            "heating/" + weaponName));

                } else if (weapon.shouldCreateBlade() && RecipeDefinitions.RECIPES.containsKey(weapon.getName())) {
                    RecipeDefinitions.WeaponRecipeData data = RecipeDefinitions.RECIPES.get(weapon.getName());

                    // Resolve input item based on input type
                    String inputItem = resolveInputItem(data.inputType, metal.getName());
                    int metalAmount = RecipeDefinitions.METAL_VALUES.getOrDefault(data.inputType, 100);

                    // Anvil: Input -> Blade
                    futures.add(saveRecipe(cachedOutput,
                            createAnvilRecipeWithItem(bladeName, inputItem, metal.getAnvilTier(),
                                    "punch_last", "hit_not_last", "bend_any"),
                            "anvil/" + bladeName));

                    // Heating: Blade -> Liquid Metal
                    futures.add(saveRecipe(cachedOutput,
                            createHeatingRecipe(bladeName, metal.getMeltingPoint(), metalAmount, metal.getName()),
                            "heating/" + bladeName));

                    // Heating: Weapon -> Liquid Metal
                    futures.add(saveRecipe(cachedOutput,
                            createHeatingRecipe(weaponName, metal.getMeltingPoint(), metalAmount, metal.getName()),
                            "heating/" + weaponName));

                    // Crafting: Blade + Stick -> Weapon
                    futures.add(saveRecipe(cachedOutput,
                            createCraftingRecipe(weaponName, bladeName),
                            "crafting/" + weaponName));
                }
            }
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    /**
     * Resolves the input item ID for TFC metal items.
     * TFC uses tfc:metal/<type>/<metal> format for metal items.
     */
    private String resolveInputItem(String inputType, String metalName) {
        return switch (inputType) {
            case "ingot" -> "tfc:metal/ingot/" + metalName;
            case "double_ingot" -> "tfc:metal/double_ingot/" + metalName;
            case "sheet" -> "tfc:metal/sheet/" + metalName;
            case "double_sheet" -> "tfc:metal/double_sheet/" + metalName;
            default -> "tfc:metal/ingot/" + metalName;
        };
    }

    private CompletableFuture<?> saveRecipe(CachedOutput output, JsonObject json, String recipePath) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, recipePath);
        Path path = this.output.getOutputFolder().resolve("data/" + id.getNamespace() + "/recipe/" + id.getPath() + ".json");
        return DataProvider.saveStable(output, json, path);
    }

    private JsonObject createAnvilRecipeWithItem(String resultItem, String inputItemId, int tier, String... rules) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "tfc:anvil");
        json.addProperty("apply_bonus", true);

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", inputItemId);
        json.add("ingredient", ingredient);

        JsonObject result = new JsonObject();
        result.addProperty("id", SimplySwordsTFC.MOD_ID + ":" + resultItem);
        result.addProperty("count", 1);
        json.add("result", result);

        json.addProperty("tier", tier);

        JsonArray rulesArray = new JsonArray();
        for (String r : rules) {
            rulesArray.add(r);
        }
        json.add("rules", rulesArray);

        return json;
    }

    private JsonObject createHeatingRecipe(String inputItem, int temp, int amount, String metalName) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "tfc:heating");

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", SimplySwordsTFC.MOD_ID + ":" + inputItem);
        json.add("ingredient", ingredient);

        json.addProperty("temperature", (float) temp);

        JsonObject resultFluid = new JsonObject();
        resultFluid.addProperty("id", "tfc:metal/" + metalName);
        resultFluid.addProperty("amount", amount);
        json.add("result_fluid", resultFluid);

        return json;
    }

    private JsonObject createCraftingRecipe(String resultItem, String bladeItem) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");

        JsonArray pattern = new JsonArray();
        pattern.add("A");
        pattern.add("B");
        json.add("pattern", pattern);

        JsonObject key = new JsonObject();

        JsonObject bladeKey = new JsonObject();
        bladeKey.addProperty("item", SimplySwordsTFC.MOD_ID + ":" + bladeItem);
        key.add("A", bladeKey);

        JsonObject stickKey = new JsonObject();
        stickKey.addProperty("tag", "c:rods/wooden");
        key.add("B", stickKey);

        json.add("key", key);

        JsonObject result = new JsonObject();
        result.addProperty("id", SimplySwordsTFC.MOD_ID + ":" + resultItem);
        result.addProperty("count", 1);
        json.add("result", result);

        return json;
    }

    @Override
    public String getName() {
        return "TFC Recipes";
    }
}



