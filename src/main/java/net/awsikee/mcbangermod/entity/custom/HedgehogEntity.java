package net.awsikee.mcbangermod.entity.custom;

import net.awsikee.mcbangermod.entity.ModEntityTypes;
import net.awsikee.mcbangermod.entity.ai.CurlHedgehogGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class HedgehogEntity extends Animal {

    private static final EntityDataAccessor<Boolean> ATTACKED =
            SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.BOOLEAN);
    private int freezeTicks;
    private final int maxFreezeTicks;

    public HedgehogEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxFreezeTicks = 100;
    }

    public final  AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final  AnimationState walkingAnimationState = new AnimationState();
    private int isAttackedTimeout = 0;
    public final  AnimationState attackedAnimationState = new AnimationState();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKED, false);
    }
    public void setAttacked(boolean attacked){
        this.entityData.set(ATTACKED,attacked);
    }
    public boolean isAttacked(){
        return this.entityData.get(ATTACKED);
    }
    @Override
    public void tick()
    {
        super.tick();
        if(this.level().isClientSide())
        {
            setupAnimationStates();
        }
    }
    private void  setupAnimationStates(){

        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;

        }else{
            --this.idleAnimationTimeout;
        }

        if(this.isAttacked() && isAttackedTimeout <= 0)
        {
           isAttackedTimeout = this.maxFreezeTicks*2;
           attackedAnimationState.start(this.tickCount);
        }else {
            --this.isAttackedTimeout;

        }

        if(!this.isAttacked())
        {
            attackedAnimationState.stop();
        }

    }
    @Override
    protected void updateWalkAnimation(float pPartialTick)
    {
        float f;
        if(this.getPose() == Pose.STANDING)
        {
            f = Math.min(pPartialTick * 6F, 1f);
        }else{
            f = 0f;
        }
        this.walkAnimation.update(f, 0.2f);
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

    public static AttributeSupplier.Builder  createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3D)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED,0.2f );
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



//    protected <T extends HedgehogEntity> PlayState changeEntityState(AnimationState<T> event) {
//        Stream<WrappedGoal> runningGoals = this.goalSelector.getRunningGoals();
//        runningGoals.forEach(wrappedGoal -> {
//            System.out.println(" checking goals");
//            Goal goal = wrappedGoal.getGoal();
//            if (goal instanceof CurlHedgehogGoal) {
//                CurlHedgehogGoal customGoal = (CurlHedgehogGoal) goal;
//                if (customGoal.isCurled()) {
//                    // Take action when the goal is triggered
//                    System.out.println(" change anim curl function called.");
//                    event.getController().setAnimation(RawAnimation.begin().then("curl", Animation.LoopType.HOLD_ON_LAST_FRAME));
//                    return PlayState.CONTINUE;
//                }
//                else
//                {
//                    System.out.println(" change anim uncurl function called.");
//
//                    event.getController().setAnimation(RawAnimation.begin().then("uncurl", Animation.LoopType.PLAY_ONCE));
//                    return PlayState.CONTINUE;
//                }
//            }
//        });
//        if (event.isMoving()) {
//                event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
//            }
//            event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
//
//        return PlayState.CONTINUE;
//
//    }


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
