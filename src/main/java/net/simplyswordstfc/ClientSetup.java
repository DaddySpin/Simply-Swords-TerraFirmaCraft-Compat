package net.simplyswordstfc;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

/**
 * Client-side setup for registering item color handlers.
 * Applies TFC metal colors to weapon blades via tinting.
 */
@EventBusSubscriber(modid = SimplySwordsTFC.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (TFCWeaponMetals metal : TFCWeaponMetals.values()) {
            // Ensure color has full alpha (0xFF) for opacity - Minecraft uses ARGB format
            final int color = metal.getHexColor() | 0xFF000000;

            for (SimplySwordsWeapons weapon : SimplySwordsWeapons.values()) {
                String weaponName = metal.getName() + "_" + weapon.getName();

                // Register weapon color handler
                if (ModItems.WEAPONS.containsKey(weaponName)) {
                    event.register((stack, tintIndex) -> tintIndex == 0 ? color : -1,
                            ModItems.WEAPONS.get(weaponName).get());
                }

                // Register blade item color handler
                String bladeName = weaponName + "_blade";
                if (ModItems.BLADES.containsKey(bladeName)) {
                    event.register((stack, tintIndex) -> tintIndex == 0 ? color : -1,
                            ModItems.BLADES.get(bladeName).get());
                }
            }
        }
    }
}



