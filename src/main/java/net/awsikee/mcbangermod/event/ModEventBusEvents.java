package net.awsikee.mcbangermod.event;

import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.entity.ModEntityTypes;
import net.awsikee.mcbangermod.entity.custom.HedgehogEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = McBangerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.HEDGEHOG.get(), HedgehogEntity.setAttributes());
    }
}
