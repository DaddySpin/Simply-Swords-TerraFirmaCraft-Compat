package net.simplyswordstfc.datagen;

import net.simplyswordstfc.SimplySwordsTFC;
import net.simplyswordstfc.SimplySwordsWeapons;
import net.simplyswordstfc.TFCWeaponMetals;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Generates item models for all TFC metal weapon variants.
 * Uses base template textures per weapon type with metal tinting applied dynamically.
 */
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SimplySwordsTFC.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();

                if (weapon.isGreathammer()) {
                    registerGreathammerModel(weaponName);
                } else {
                    registerStandardWeaponModel(weaponName, weapon);
                }

                if (weapon.shouldCreateBlade()) {
                    registerBladeModel(weaponName, weapon);
                }
            }
        }
    }

    /**
     * Registers a greathammer model using the custom tintable 3D template.
     * Uses a single base template texture that gets tinted by metal color.
     */
    private void registerGreathammerModel(String weaponName) {
        getBuilder(weaponName)
                .parent(getExistingFile(ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/template_reworked_greathammer_tintable")))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/greathammer"));
    }

    /**
     * Registers a standard layered weapon model.
     * Uses the existing blade template texture (layer0) that gets tinted by metal color.
     * Handle (layer1) remains untinted.
     */
    private void registerStandardWeaponModel(String weaponName, SimplySwordsWeapons weapon) {
        ResourceLocation parentLoc = resolveParentModel(weapon.getModelParent());
        
        // Use existing blade template texture - metal parts will be tinted via color handler
        // The blade_{weapon}.png textures serve as base templates that get tinted
        getBuilder(weaponName)
                .parent(new ModelFile.UncheckedModelFile(parentLoc))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/blade_" + weapon.getName()))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/handle_" + weapon.getName()));
    }

    /**
     * Registers a blade item model (crafting component).
     * Uses the existing blade template texture that gets tinted by metal color.
     */
    private void registerBladeModel(String weaponName, SimplySwordsWeapons weapon) {
        String bladeName = weaponName + "_blade";
        
        if (weapon.isGreathammer()) {
            // Greathammer blade uses a 3D head-only model
            getBuilder(bladeName)
                    .parent(getExistingFile(ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/template_greathammer_blade_tintable")))
                    .texture("texture", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/greathammer"));
        } else {
            // Standard 2D blade model - uses existing blade template that gets tinted
            getBuilder(bladeName)
                    .parent(getExistingFile(ResourceLocation.withDefaultNamespace("item/generated")))
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/blade_" + weapon.getName()));
        }
    }

    /**
     * Resolves the parent model location, handling legacy namespace conversions.
     */
    private ResourceLocation resolveParentModel(String parentPath) {
        // Handle legacy "simplymore" namespace - redirect to simplyswords
        if (parentPath.contains("simplymore")) {
            return ResourceLocation.fromNamespaceAndPath("simplyswords", "item/big_handheld");
        }

        if (parentPath.contains(":")) {
            String[] split = parentPath.split(":");
            return ResourceLocation.fromNamespaceAndPath(split[0], split[1]);
        }

        return ResourceLocation.withDefaultNamespace(parentPath);
    }
}



