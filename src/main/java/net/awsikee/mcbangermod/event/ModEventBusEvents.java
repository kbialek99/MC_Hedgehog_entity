package net.awsikee.mcbangermod.event;

import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.entity.ModEntityTypes;
import net.awsikee.mcbangermod.entity.client.HedgehogModel;
import net.awsikee.mcbangermod.entity.client.ModModelLayers;
import net.awsikee.mcbangermod.entity.custom.HedgehogEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = McBangerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntityTypes.HEDGEHOG.get(),HedgehogEntity.createAttributes().build());
    }


}
