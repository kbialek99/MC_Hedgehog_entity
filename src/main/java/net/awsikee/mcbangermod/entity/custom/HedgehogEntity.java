package net.awsikee.mcbangermod.entity.custom;

import net.awsikee.mcbangermod.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.stream.Stream;

public class HedgehogEntity extends Animal implements GeoEntity {

    private final AnimatableInstanceCache geoCache = new SingletonAnimatableInstanceCache(this);
    private int freezeTicks;
    private final int maxFreezeTicks;

    public HedgehogEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxFreezeTicks = 100;
    }

    public void setFreezeTicks(int freezeTicks) {
        this.freezeTicks = freezeTicks;
    }
    public int getFreezeTicks()
    {
        return this.freezeTicks;
    }
    public int getMaxFreezeTicks()
    {
        return this.maxFreezeTicks;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3D)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED,0.2f ).build();

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, (new CurlHedgehogGoal(this, 1.50)));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob OtherParent) {
        return ModEntityTypes.HEDGEHOG.get().create(level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::changeEntityState));
        //controllers.add(new AnimationController<>(this, "onEventTrigger", 0, this::changeEntityState));

    }

    protected <T extends HedgehogEntity> PlayState changeEntityState(AnimationState<T> event) {
        Stream<WrappedGoal> runningGoals = this.goalSelector.getRunningGoals();
        runningGoals.forEach(wrappedGoal -> {
            System.out.println(" checking goals");
            Goal goal = wrappedGoal.getGoal();
            if (goal instanceof CurlHedgehogGoal) {
                CurlHedgehogGoal customGoal = (CurlHedgehogGoal) goal;
                if (customGoal.isCurled()) {
                    // Take action when the goal is triggered
                    System.out.println(" change anim curl function called.");
                    event.getController().setAnimation(RawAnimation.begin().then("curl", Animation.LoopType.HOLD_ON_LAST_FRAME));
                    return PlayState.CONTINUE;
                }
                else
                {
                    System.out.println(" change anim uncurl function called.");

                    event.getController().setAnimation(RawAnimation.begin().then("uncurl", Animation.LoopType.PLAY_ONCE));
                    return PlayState.CONTINUE;
                }
            }
        });
        if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            }
            event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }
//    protected <T extends HedgehogEntity> PlayState predicate(AnimationState<T> event) {
//        if (event.isMoving()) {
//            event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
//            return PlayState.CONTINUE;
//        }
//        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
//        return PlayState.CONTINUE;
//
//    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }



    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

}
