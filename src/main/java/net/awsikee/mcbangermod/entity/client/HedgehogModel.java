package net.awsikee.mcbangermod.entity.client;
import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.entity.custom.HedgehogEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class HedgehogModel extends GeoModel<HedgehogEntity> {
    @Override
    public ResourceLocation getModelResource(HedgehogEntity animatable) {
        return new ResourceLocation(McBangerMod.MOD_ID, "geo/hedgehog.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HedgehogEntity animatable) {
        return new ResourceLocation(McBangerMod.MOD_ID, "textures/entity/hedgehog_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HedgehogEntity animatable) {
        return new ResourceLocation(McBangerMod.MOD_ID, "animations/hedgehog.animation.json");
    }
    @Override
    public void setCustomAnimations(HedgehogEntity animatable, long instanceId, AnimationState<HedgehogEntity> animationState) {

    }
}
