package net.awsikee.mcbangermod.item;

import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, McBangerMod.MOD_ID);
    public static  final RegistryObject<CreativeModeTab> MCBANGER_TAB = CREATIVE_MODE_TABS.register("mcbanger_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY.get()))
                    .title(Component.translatable("creativetab.mcbanger_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.RUBY.get());
                        pOutput.accept(ModItems.ROUGH_RUBY.get());
                        pOutput.accept(ModBlocks.RUBY_ORE.get());
                        pOutput.accept(ModBlocks.RUBY_BLOCK.get());
                        pOutput.accept(ModItems.HEDGEHOG_SPAWN_EGG.get());

                    }))
                    .build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
