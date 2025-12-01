package net.maximerubrum.simplyswords_tfc;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SimplySwordsTFC.MOD_ID)
public class SimplySwordsTFC {
    public static final String MOD_ID = "simplyswords_tfc";

    public SimplySwordsTFC(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }
}
