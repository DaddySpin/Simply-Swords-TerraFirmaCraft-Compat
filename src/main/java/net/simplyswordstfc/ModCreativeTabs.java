package net.simplyswordstfc;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SimplySwordsTFC.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SIMPLY_SWORDS_TFC_TAB = CREATIVE_MODE_TABS.register("simplyswords_tfc_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + SimplySwordsTFC.MOD_ID))
            .icon(() -> new ItemStack(ModItems.WEAPONS.get("steel_claymore").get()))
            .displayItems((parameters, output) -> {
                ModItems.WEAPONS.values().forEach(item -> output.accept(item.get()));
                ModItems.BLADES.values().forEach(item -> output.accept(item.get()));
            }).build());
}



