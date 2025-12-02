package net.simplyswordstfc;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

/**
 * Client-side setup for registering item color handlers.
 * Applies TFC metal colors to weapon base templates via tinting.
 * Uses a single base template texture per weapon that gets tinted with metal color for a shiny effect.
 */
@EventBusSubscriber(modid = SimplySwordsTFC.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            // Apply metal color with full alpha (0xFF) for opacity - Minecraft uses ARGB format
            // For a shiny metal effect, we can slightly brighten the color
            final int baseColor = metal.getHexColor();
            final int color = applyMetalShine(baseColor) | 0xFF000000;

            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();

                // Register weapon color handler - tints layer0 (blade/metal parts) with metal color
                // layer1 (handle) remains untinted (returns -1)
                if (ModItems.WEAPONS.containsKey(weaponName)) {
                    event.register((stack, tintIndex) -> tintIndex == 0 ? color : -1,
                            ModItems.WEAPONS.get(weaponName).get());
                }

                // Register blade item color handler - tints the blade template
                String bladeName = weaponName + "_blade";
                if (ModItems.BLADES.containsKey(bladeName)) {
                    event.register((stack, tintIndex) -> tintIndex == 0 ? color : -1,
                            ModItems.BLADES.get(bladeName).get());
                }
            }
        }
    }

    /**
     * Applies a subtle shine effect to the metal color by slightly brightening it.
     * This gives the metal a more polished, shiny appearance.
     */
    private static int applyMetalShine(int color) {
        // Extract RGB components
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        // Brighten by ~10% for shine effect (clamped to 255)
        r = Math.min(255, (int) (r * 1.1f));
        g = Math.min(255, (int) (g * 1.1f));
        b = Math.min(255, (int) (b * 1.1f));

        return (r << 16) | (g << 8) | b;
    }
}




