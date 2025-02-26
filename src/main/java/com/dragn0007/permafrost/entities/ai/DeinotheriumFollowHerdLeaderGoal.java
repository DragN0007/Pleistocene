package com.dragn0007.permafrost.entities.ai;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class DeinotheriumFollowHerdLeaderGoal extends Goal {
   public static final int INTERVAL_TICKS = 200;
   public final Deinotherium mob;
   public int timeToRecalcPath;
   public int nextStartTick;

   public DeinotheriumFollowHerdLeaderGoal(Deinotherium deinotherium) {
      this.mob = deinotherium;
      this.nextStartTick = this.nextStartTick(deinotherium);
   }

   public int nextStartTick(Deinotherium deinotherium) {
      return reducedTickDelay(200 + deinotherium.getRandom().nextInt(200) % 20);
   }

   public boolean canUse() {
      if (this.mob.hasFollowers() && (!mob.isSaddled() && !mob.isLeashed() && !this.mob.isGroundTied()) || !LivestockOverhaulCommonConfig.ANIMALS_HERDING_ENABLED.get()) {
         return false;
      } else if (this.mob.isFollower() && (!this.mob.isGroundTied() && !mob.isSaddled() && !mob.isLeashed())) {
         return true;
      } else if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      } else {
         this.nextStartTick = this.nextStartTick(this.mob);
         Predicate<Deinotherium> predicate = (follower) -> {
            return follower.canBeFollowed() || !follower.isFollower();
         };
         List<? extends Deinotherium> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
         Deinotherium Deinotherium = DataFixUtils.orElse(list.stream().filter(com.dragn0007.permafrost.entities.deinotherium.Deinotherium::canBeFollowed).findAny(), this.mob);
         Deinotherium.addFollowers(list.stream().filter((deinotherium) -> {
            return !deinotherium.isFollower();
         }));
         return this.mob.isFollower();
      }
   }

   public boolean canContinueToUse() {
      return this.mob.isFollower() && this.mob.inRangeOfLeader() && (!mob.isSaddled() && !mob.isLeashed() && !this.mob.isGroundTied()) && LivestockOverhaulCommonConfig.ANIMALS_HERDING_ENABLED.get();
   }

   public void start() {
      this.timeToRecalcPath = 0;
   }

   public void stop() {
      this.mob.stopFollowing();
   }

   public void tick() {
      if (--this.timeToRecalcPath <= 0 && (!mob.isSaddled() && !mob.isLeashed() && !this.mob.isGroundTied())) {
         this.timeToRecalcPath = this.adjustedTickDelay(10);
         this.mob.pathToLeader();
      }
   }
}