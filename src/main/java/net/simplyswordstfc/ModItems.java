package net.simplyswordstfc;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for all TFC metal weapon variants and their blade components.
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimplySwordsTFC.MOD_ID);

    public static final Map<String, DeferredItem<SwordItem>> WEAPONS = new HashMap<>();
    public static final Map<String, DeferredItem<Item>> BLADES = new HashMap<>();

    static {
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                registerWeapon(metal, weapon);
            }
        }
    }

    private static void registerWeapon(TFCWeaponMetals metal, SimplySwordsWeapons weapon) {
                String weaponName = metal.getName() + "_" + weapon.getName();
                
                int damageModifier = (int) (weapon.getAttackDamage() - 3);
                float speedModifier = weapon.getAttackSpeed() - 4.0f;

        WEAPONS.put(weaponName, ITEMS.register(weaponName,
                () -> new TFCSwordItem(metal, damageModifier, speedModifier,
                        new Item.Properties().rarity(metal.getRarity()))));

                if (weapon.shouldCreateBlade()) {
                    String bladeName = weaponName + "_blade";
            BLADES.put(bladeName, ITEMS.register(bladeName,
                    () -> new Item(new Item.Properties().rarity(metal.getRarity()))));
        }
    }
}

