package net.awsikee.mcbangermod.entity.custom;

import net.awsikee.mcbangermod.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CurlHedgehogGoal extends Goal {
    public static final int WATER_CHECK_DISTANCE_VERTICAL = 1;
    protected final HedgehogEntity mob;
    protected double speedModifier;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;
    protected  boolean isCurled;


    public CurlHedgehogGoal(HedgehogEntity pMob, double pSpeedModifier) {
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    private void setSpeedModifier(double speedModifier){
        this.speedModifier = speedModifier;
    }
    private void curlIntoABall()
    {
     System.out.println("Called curl function");
     this.mob.getNavigation().stop();
     this.mob.setFreezeTicks(0);
     this.setSpeedModifier(0);
     this.isCurled = true;

    }
    private void uncurlFromABall()
    {
        System.out.println("Called uncurl function");
        this.mob.setNoAi(false);

    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (!this.shouldPanic()) {
            return false;
        } else {

            if (this.mob.isOnFire()) {
                System.out.println("Hedgehog on fire  ");
                BlockPos blockpos = this.lookForWater(this.mob.level(), this.mob, 5);
                if (blockpos != null) {
                    this.posX = (double)blockpos.getX();
                    this.posY = (double)blockpos.getY();
                    this.posZ = (double)blockpos.getZ();
                    return true;
                }
            }
            else if (this.isCurled) {
                System.out.println("tell to curl");
                 return true;

            }
            return this.findRandomPosition();
        }
    }

    protected boolean shouldPanic() {

        if (this.mob.getLastHurtByMob() != null)
        {
            System.out.println("Hedghehog got hit");
            this.isCurled = true;
            return true;
        }
        //System.out.println("Hedghehog is burning");
        return this.mob.isFreezing() || this.mob.isOnFire();
    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 5, 4);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public boolean isCurled() {
        return this.isCurled;
    }


    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        System.out.println("Called start function");
        if(!this.isCurled) {
            this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
            this.isRunning = true;
        }
        else{
            this.curlIntoABall();
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        System.out.println("Called stop function");
        this.isRunning = false;
        this.isCurled = false;
        this.setSpeedModifier(1);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        //System.out.println("Checking if continue");
        if(!this.isCurled) {
            return !this.mob.getNavigation().isDone();
        }

        return this.mob.getFreezeTicks() < mob.getMaxFreezeTicks();
    }

    @Override
    public void tick() {
        mob.setFreezeTicks(mob.getFreezeTicks()+1);
        if (mob.getFreezeTicks() >= mob.getMaxFreezeTicks()) {
            // Unfreeze the mob after maxFreezeTicks is reached
            this.uncurlFromABall();
        }
        //System.out.println(mob.getFreezeTicks());
    }
    @Nullable
    protected BlockPos lookForWater(BlockGetter pLevel, Entity pEntity, int pRange) {
        BlockPos blockpos = pEntity.blockPosition();
        return !pLevel.getBlockState(blockpos).getCollisionShape(pLevel, blockpos).isEmpty() ? null : BlockPos.findClosestMatch(pEntity.blockPosition(), pRange, 1, (p_196649_) -> {
            return pLevel.getFluidState(p_196649_).is(FluidTags.WATER);
        }).orElse((BlockPos)null);
    }
}
