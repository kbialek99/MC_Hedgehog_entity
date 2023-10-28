package net.awsikee.mcbangermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.entity.custom.HedgehogEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HedgehogRenderer extends MobRenderer<HedgehogEntity, HedgehogModel<HedgehogEntity>> {
    public HedgehogRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HedgehogModel<>(renderManager.bakeLayer(ModModelLayers.HEDGEHOG_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(HedgehogEntity animatable) {
        return new ResourceLocation(McBangerMod.MOD_ID, "textures/entity/hedgehog_texture.png");
    }
    @Override
    public void render(HedgehogEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.5f,0.5f,0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }
}
