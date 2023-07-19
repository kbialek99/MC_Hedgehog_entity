package net.awsikee.mcbangermod.entity.client;

import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.entity.custom.HedgehogEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HedgehogRenderer extends GeoEntityRenderer<HedgehogEntity> {
    public HedgehogRenderer(EntityRendererProvider.Context renderManager) {

        super(renderManager, new HedgehogModel());
    }

    @Override
    public ResourceLocation getTextureLocation(HedgehogEntity animatable) {
        return new ResourceLocation(McBangerMod.MOD_ID, "textures/entity/hedgehog_texture.png");
    }
}
