package net.awsikee.mcbangermod.entity.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.awsikee.mcbangermod.entity.animations.ModAnimationDefinition;
import net.awsikee.mcbangermod.entity.custom.HedgehogEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;



public class HedgehogModel<T extends Entity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "unknown"), "main");
    private final ModelPart hedgehog;
    private final ModelPart head;

    public HedgehogModel(ModelPart root) {
        this.hedgehog = root.getChild("hedgehog");
        this.head = hedgehog.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hedgehog = partdefinition.addOrReplaceChild("hedgehog", CubeListBuilder.create(), PartPose.offset(-1.5F, 20.5F, 1.5F));

        PartDefinition body = hedgehog.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -2.5F, -3.5F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = hedgehog.addOrReplaceChild("head", CubeListBuilder.create().texOffs(7, 12).addBox(-3.5F, -0.5F, -5.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 14).addBox(-4.5F, -4.5F, -4.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(17, 13).addBox(-2.875F, -0.375F, -5.75F, 1.0F, 0.75F, 0.75F, new CubeDeformation(0.0F)), PartPose.offset(0.375F, -0.375F, 0.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(-1.5046F, -4.4981F, 0.0F));

        PartDefinition ears_r1 = ears.addOrReplaceChild("ears_r1", CubeListBuilder.create().texOffs(18, 2).addBox(-0.5F, 0.5F, 2.0F, 1.0F, 1.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0046F, -1.0019F, -5.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition ears_r2 = ears.addOrReplaceChild("ears_r2", CubeListBuilder.create().texOffs(18, 2).addBox(-0.5F, 0.5F, 2.0F, 1.0F, 1.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9954F, -1.0019F, -5.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition legs = hedgehog.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(1.5F, 3.5F, -1.5F));

        PartDefinition Legfront1 = legs.addOrReplaceChild("Legfront1", CubeListBuilder.create().texOffs(17, 4).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.0F, -1.5F));

        PartDefinition Legfront2 = legs.addOrReplaceChild("Legfront2", CubeListBuilder.create().texOffs(17, 4).addBox(-0.5F, -1.5563F, -0.4747F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -0.4437F, -1.5253F));

        PartDefinition legback1 = legs.addOrReplaceChild("legback1", CubeListBuilder.create().texOffs(17, 4).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.0F, 2.5F));

        PartDefinition legback2 = legs.addOrReplaceChild("legback2", CubeListBuilder.create().texOffs(17, 4).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.0F, 2.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(ModAnimationDefinition.WALK, limbSwing, limbSwingAmount, 2f,2.5f);
        this.animate(((HedgehogEntity) entity).idleAnimationState, ModAnimationDefinition.IDLE, ageInTicks,1f);
        this.animate(((HedgehogEntity) entity).attackedAnimationState, ModAnimationDefinition.CURL, ageInTicks,1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        hedgehog.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return hedgehog;
    }
}