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
 * Uses Simply Swords model templates as parents with custom blade/handle textures.
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
     */
    private void registerGreathammerModel(String weaponName) {
        getBuilder(weaponName)
                .parent(getExistingFile(ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/template_reworked_greathammer_tintable")))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/greathammer"));
    }

    /**
     * Registers a standard layered weapon model with blade (layer0) and handle (layer1).
     */
    private void registerStandardWeaponModel(String weaponName, SimplySwordsWeapons weapon) {
        ResourceLocation parentLoc = resolveParentModel(weapon.getModelParent());
                    
                    getBuilder(weaponName)
                .parent(new ModelFile.UncheckedModelFile(parentLoc))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/blade_" + weapon.getName()))
                            .texture("layer1", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_templates/handle_" + weapon.getName()));
                }

    /**
     * Registers a blade item model (crafting component).
     */
    private void registerBladeModel(String weaponName, SimplySwordsWeapons weapon) {
        String bladeName = weaponName + "_blade";
                    getBuilder(bladeName)
                            .parent(getExistingFile(ResourceLocation.withDefaultNamespace("item/generated")))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(SimplySwordsTFC.MOD_ID, "item/simply_swords_blades/blade_" + weapon.getName()));
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



