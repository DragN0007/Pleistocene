package com.dragn0007.permafrost.entities.direwolf;

import com.dragn0007.dragnlivestock.items.LOItems;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.entities.ai.DirewolfFollowOwnerGoal;
import com.dragn0007.permafrost.entities.ai.DirewolfFollowPackLeaderGoal;
import com.dragn0007.permafrost.util.PFTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class Direwolf extends TamableAnimal implements NeutralMob, GeoEntity {

   private static final ResourceLocation LOOT_TABLE = new ResourceLocation(Permafrost.MODID, "entities/direwolf");
   private static final ResourceLocation TFC_LOOT_TABLE = new ResourceLocation(Permafrost.MODID, "entities/tfc/tfc_direwolf");
   @Override
   public @NotNull ResourceLocation getDefaultLootTable() {
      if (ModList.get().isLoaded("tfc")) {
         return TFC_LOOT_TABLE;
      }
      return LOOT_TABLE;
   }

   public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Direwolf.class, EntityDataSerializers.INT);

   public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   @Nullable
   public UUID persistentAngerTarget;

   public Direwolf(EntityType<? extends Direwolf> entityType, Level level) {
      super(entityType, level);
      this.setTame(false);
      this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
   }

   public void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Direwolf.WolfPanicGoal(1.4D));
      this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
      this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
      this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.6D, true));
      this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
      this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
      this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
      this.goalSelector.addGoal(8, new DirewolfFollowPackLeaderGoal(this));
      this.goalSelector.addGoal(6, new DirewolfFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));

      this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 2, true, false,
              entity -> entity.getType().is(PFTags.Entity_Types.MEDIUM_PREDATOR_PREY) && ((!this.isTame() && (this.isFollower() || this.hasFollowers())) || (this.isTame() && this.wasToldToWander())))  {
      });

      this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 2, true, false,
              entity -> entity instanceof Player && (!this.isTame() && !this.isBaby())
      ));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
              .add(Attributes.MOVEMENT_SPEED, 0.28F)
              .add(Attributes.MAX_HEALTH, 24.0D)
              .add(Attributes.ATTACK_DAMAGE, 3.0D);
   }

   public final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

   public <T extends GeoAnimatable> PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<T> tAnimationState) {
      double currentSpeed = this.getDeltaMovement().lengthSqr();
      double speedThreshold = 0.015;
      double x = this.getX() - this.xo;
      double z = this.getZ() - this.zo;

      boolean isMoving = (x * x + z * z) > 0.0001;

      AnimationController<T> controller = tAnimationState.getController();

      if (isMoving) {
         if (currentSpeed > speedThreshold) {
            controller.setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
            controller.setAnimationSpeed(2.2);
         } else {
            controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            controller.setAnimationSpeed(1.0);
         }
      } else {
         if (isInSittingPose()) {
            controller.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            controller.setAnimationSpeed(1.0);
         } else {
            controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            controller.setAnimationSpeed(1.0);
         }
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
      controllers.add(new AnimationController<>(this, "controller", 2, this::predicate));
   }

   @Override
   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }


   public Direwolf leader;
   public int packSize = 1;

   public boolean isFollower() {
      return this.leader != null && this.leader.isAlive();
   }

   public Direwolf startFollowing(Direwolf wolf) {
      this.leader = wolf;
      wolf.addFollower();
      return wolf;
   }

   public void stopFollowing() {
      this.leader.removeFollower();
      this.leader = null;
   }

   public void addFollower() {
      ++this.packSize;
   }

   public void removeFollower() {
      --this.packSize;
   }

   public boolean canBeFollowed() {
      return this.hasFollowers() && this.packSize < this.getMaxHerdSize();
   }

   public int regenHealthCounter = 0;

   @Override
   public void tick() {
      super.tick();
      if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
         List<? extends Direwolf> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(20.0D, 20.0D, 20.0D));
         if (list.size() <= 1) {
            this.packSize = 1;
         }
      }

      regenHealthCounter++;

      if (this.getHealth() < this.getMaxHealth() && regenHealthCounter >= 150 && this.isTame() && this.isAlive()) {
         this.setHealth(this.getHealth() + 2);
         regenHealthCounter = 0;
         this.level().addParticle(ParticleTypes.HEART, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.7D, 0.7D, 0.7D);
      }

      if (this.hasFollowers()) {
         if (!this.hasStrengthEffect()) {
            this.applyStrengthEffect();
         }
         if (!this.hasResistanceEffect()) {
            this.applyResistanceEffect();
         }
      } else {
         if (this.hasStrengthEffect()) {
            this.removeStrengthEffect();
         }
         if (this.hasResistanceEffect()) {
            this.removeResistanceEffect();
         }
      }


   }

   public void applyStrengthEffect() {
      MobEffectInstance effectInstance = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0, false, false);
      this.addEffect(effectInstance);
   }
   public boolean hasStrengthEffect() {
      return this.hasEffect(MobEffects.DAMAGE_BOOST);
   }
   public void removeStrengthEffect() {
      this.removeEffect(MobEffects.DAMAGE_BOOST);
   }

   public void applyResistanceEffect() {
      MobEffectInstance effectInstance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, false, false);
      this.addEffect(effectInstance);
   }
   public boolean hasResistanceEffect() {
      return this.hasEffect(MobEffects.DAMAGE_RESISTANCE);
   }
   public void removeResistanceEffect() {
      this.removeEffect(MobEffects.DAMAGE_RESISTANCE);
   }

   public int getMaxHerdSize() {
      return 3;
   }

   public boolean hasFollowers() {
      return this.packSize > 1;
   }

   public boolean inRangeOfLeader() {
      return this.distanceToSqr(this.leader) <= 121.0D;
   }

   public void pathToLeader() {
      if (this.isFollower()) {
         this.getNavigation().moveTo(this.leader, 1.0D);
      }

   }

   public void addFollowers(Stream<? extends Direwolf> p_27534_) {
      p_27534_.limit((long)(this.getMaxHerdSize() - this.packSize)).filter((wolf) -> {
         return wolf != this;
      }).forEach((wolf) -> {
         wolf.startFollowing(this);
      });
   }

   @Override
   public float getStepHeight() {
      return 1.6F;
   }

   public void playStepSound(BlockPos p_30415_, BlockState p_30416_) {
      this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
   }

   public SoundEvent getAmbientSound() {
      if (this.isAngry()) {
         return SoundEvents.WOLF_GROWL;
      } else if (this.random.nextInt(3) == 0) {
         return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
      } else {
         return SoundEvents.WOLF_AMBIENT;
      }
   }

   public SoundEvent getHurtSound(DamageSource p_30424_) {
      return SoundEvents.WOLF_GROWL;
   }

   public SoundEvent getDeathSound() {
      return SoundEvents.WOLF_DEATH;
   }

   public float getSoundVolume() {
      return 0.4F;
   }

   public boolean doHurtTarget(Entity p_30372_) {
      boolean flag = p_30372_.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
      if (flag) {
         this.doEnchantDamageEffects(this, p_30372_);
      }

      return flag;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (damageSource.getEntity() instanceof Player player && player.isShiftKeyDown()) {
         if (!this.level().isClientSide && this.isTame()) {
            if (this.isOwnedBy(player) && player.isShiftKeyDown() && !this.isVehicle()) {
               this.setOrderedToSit(!this.isOrderedToSit());
               this.jumping = false;
               this.navigation.stop();
               this.setTarget(null);
            }
            return false;
         }

         if (this.isInvulnerableTo(damageSource)) {
            return false;
         } else {
            Entity entity = damageSource.getEntity();
            if (!this.level().isClientSide) {
               this.setOrderedToSit(false);
            }

            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
               amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(damageSource, amount);
         }
      }
      return super.hurt(damageSource, amount);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      Item item = itemstack.getItem();

      if (itemstack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isFemale()) {
         player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
         ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, LOItems.FEMALE_GENDER_TEST_STRIP.get().getDefaultInstance());
         player.setItemInHand(hand, itemstack1);
         return InteractionResult.SUCCESS;
      }

      if (itemstack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isMale()) {
         player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
         ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, LOItems.MALE_GENDER_TEST_STRIP.get().getDefaultInstance());
         player.setItemInHand(hand, itemstack1);
         return InteractionResult.SUCCESS;
      }

      if (player.isShiftKeyDown() && !this.isFood(itemstack) && !this.isOrderedToSit() && !this.wasToldToWander() && this.isOwnedBy(player)) {
         this.setToldToWander(true);
         player.displayClientMessage(Component.translatable("tooltip.permafrost.wandering.tooltip").withStyle(ChatFormatting.GOLD), true);
         return InteractionResult.SUCCESS;
      }

      if (player.isShiftKeyDown() && !this.isFood(itemstack) && !this.isOrderedToSit() && this.wasToldToWander() && this.isOwnedBy(player)) {
         this.setToldToWander(false);
         player.displayClientMessage(Component.translatable("tooltip.permafrost.following.tooltip").withStyle(ChatFormatting.GOLD), true);
         return InteractionResult.SUCCESS;
      }

      if (this.level().isClientSide) {
         boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame() && !this.isAngry();
         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else if (this.isTame()) {
         if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            this.heal((float)itemstack.getFoodProperties(this).getNutrition());
            if (!player.getAbilities().instabuild) {
               itemstack.shrink(1);
            }

            this.gameEvent(GameEvent.EAT, this);
            return InteractionResult.SUCCESS;
         } else {

            if (!this.isOrderedToSit() && this.isOwnedBy(player) && !this.isFood(itemstack)) {
               this.doPlayerRide(player);
               return InteractionResult.SUCCESS;
            } else {
               InteractionResult interactionresult = super.mobInteract(player, hand);
               return interactionresult;
            }
         }
      } else if (itemstack.is(Items.BONE) && !this.isAngry()) {
         if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
         }

         if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
         } else {
            this.level().broadcastEntityEvent(this, (byte)6);
         }

         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   @Override
   public void updateControlFlags() {
      super.updateControlFlags();
   }

   public int calculateFallDamage(float v, float v1) {
      return Mth.ceil((v * 0.5F - 3.0F) * v1);
   }

   public void doPlayerRide(Player player) {
      if (!this.level().isClientSide) {
         player.setYRot(this.getYRot());
         player.setXRot(this.getXRot());
         player.startRiding(this);
      }
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return (LivingEntity) this.getFirstPassenger();
   }

   @Override
   public void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
      if (this.hasPassenger(entity)) {
         double offsetX = 0;
         double offsetY = 0.55;
         double offsetZ = -0.2;

         double radYaw = Math.toRadians(this.getYRot());

         double offsetXRotated = offsetX * Math.cos(radYaw) - offsetZ * Math.sin(radYaw);
         double offsetYRotated = offsetY;
         double offsetZRotated = offsetX * Math.sin(radYaw) + offsetZ * Math.cos(radYaw);

         double x = this.getX() + offsetXRotated;
         double y = this.getY() + offsetYRotated;
         double z = this.getZ() + offsetZRotated;

         entity.setPos(x, y, z);
      }
   }

   public Vec2 getRiddenRotation(LivingEntity livingEntity) {
      return new Vec2(livingEntity.getXRot() * 0.5F, livingEntity.getYRot());
   }

   public void tickRidden(Player p_278233_, Vec3 p_275693_) {
      super.tickRidden(p_278233_, p_275693_);
      Vec2 vec2 = this.getRiddenRotation(p_278233_);
      this.setRot(vec2.y, vec2.x);
      this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
   }

   @Override
   public boolean isImmobile() {
      return super.isImmobile() && this.isVehicle();
   }

   public Vec3 getRiddenInput(Player p_278278_, Vec3 p_275506_) {
      float f = p_278278_.xxa * 0.5F;
      float f1 = p_278278_.zza;
      if (f1 <= 0.0F) {
         f1 *= 0.25F;
      }

      return new Vec3((double)f, 0.0D, (double)f1);
   }

   @Override
   public float getRiddenSpeed(Player player) {
      return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
   }

   public boolean toldToWander = false;

   public boolean wasToldToWander() {
      return this.toldToWander;
   }

   public boolean getToldToWander() {
      return this.toldToWander;
   }

   public void setToldToWander(boolean toldToWander) {
      this.toldToWander = toldToWander;
   }

   public static final Ingredient FOOD_ITEMS = Ingredient.of(PFTags.Items.DIREWOLF_FOOD);

   @Override
   public boolean isFood(ItemStack itemStack) {
      return FOOD_ITEMS.test(itemStack);
   }

   public int getRemainingPersistentAngerTime() {
      return this.entityData.get(DATA_REMAINING_ANGER_TIME);
   }

   public void setRemainingPersistentAngerTime(int p_30404_) {
      this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
      this.persistentAngerTarget = p_30400_;
   }

   public ResourceLocation getTextureLocation() {
      return DirewolfModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
   }

   public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Direwolf.class, EntityDataSerializers.INT);

   public int getVariant() {
      return this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
      this.entityData.define(GENDER, 0);
      this.entityData.define(VARIANT, 0);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("Gender", this.getGender());
      tag.putBoolean("Wandering", this.getToldToWander());
      tag.putBoolean("Panicking", this.getPanicking());
      tag.putInt("Variant", getVariant());
      this.addPersistentAngerSaveData(tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("CollarColor", 99)) {
      }

      if (tag.contains("Gender")) {
         this.setGender(tag.getInt("Gender"));
      }

      if (tag.contains("Wandering")) {
         this.setToldToWander(tag.getBoolean("Wandering"));
      }

      if (tag.contains("Panicking")) {
         this.setPanicking(tag.getBoolean("Panicking"));
      }

      if (tag.contains("Variant")) {
         setVariant(tag.getInt("Variant"));
      }

      this.readPersistentAngerSaveData(this.level(), tag);
   }

   @Override
   @javax.annotation.Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData data, @javax.annotation.Nullable CompoundTag tag) {
      if (data == null) {
         data = new AgeableMobGroupData(0.2F);
      }
      Random random = new Random();
      setGender(random.nextInt(Direwolf.Gender.values().length));
      setVariant(random.nextInt(DirewolfModel.Variant.values().length));

      return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
   }

   public enum Gender {
      FEMALE,
      MALE
   }

   public boolean isFemale() {
      return this.getGender() == 0;
   }

   public boolean isMale() {
      return this.getGender() == 1;
   }

   public static final EntityDataAccessor<Integer> GENDER = SynchedEntityData.defineId(Direwolf.class, EntityDataSerializers.INT);

   public int getGender() {
      return this.entityData.get(GENDER);
   }

   public void setGender(int gender) {
      this.entityData.set(GENDER, gender);
   }

   public boolean canParent() {
      return !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
   }

   public boolean canMate(Animal animal) {
      if (animal == this) {
         return false;
      } else if (!(animal instanceof Direwolf)) {
         return false;
      } else {
         if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
            return this.canParent() && ((Direwolf) animal).canParent();
         } else {
            Direwolf partner = (Direwolf) animal;
            if (this.canParent() && partner.canParent() && this.getGender() != partner.getGender()) {
               return true;
            }

            boolean partnerIsFemale = partner.isFemale();
            boolean partnerIsMale = partner.isMale();
            if (LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get() && this.canParent() && partner.canParent()
                    && ((isFemale() && partnerIsMale) || (isMale() && partnerIsFemale))) {
               return isFemale();
            }
         }
      }
      return false;
   }

   @Override
   public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
      Direwolf oWolf1 = (Direwolf) ageableMob;
      if (ageableMob instanceof Direwolf) {
         Direwolf oWolf = (Direwolf) ageableMob;
         oWolf1 = EntityTypes.DIREWOLF_ENTITY.get().create(serverLevel);

         int i = this.random.nextInt(9);
         int variant;
         if (i < 4) {
            variant = this.getVariant();
         } else if (i < 8) {
            variant = oWolf.getVariant();
         } else {
            variant = this.random.nextInt(com.dragn0007.permafrost.entities.direwolf.DirewolfModel.Variant.values().length);
         }

         int gender;
         gender = this.random.nextInt(Direwolf.Gender.values().length);

         oWolf1.setVariant(variant);
         oWolf1.setGender(gender);
      }

      return oWolf1;
   }

   public boolean wantsToAttack(LivingEntity entity, LivingEntity p_30390_) {
      if (!(entity instanceof Creeper) && !(entity instanceof Ghast)) {
         if (entity instanceof Direwolf) {
            Direwolf wolf = (Direwolf)entity;
            return !wolf.isTame() || wolf.getOwner() != p_30390_;
         } else if (entity instanceof Player && p_30390_ instanceof Player && !((Player)p_30390_).canHarmPlayer((Player)entity)) {
            return false;
         } else if (entity instanceof AbstractHorse && ((AbstractHorse)entity).isTamed()) {
            return false;
         } else {
            return !(entity instanceof TamableAnimal) || !((TamableAnimal)entity).isTame();
         }
      } else {
         return false;
      }
   }

   public boolean canBeLeashed(Player p_30396_) {
      return !this.isAngry() && super.canBeLeashed(p_30396_);
   }

   public Vec3 getLeashOffset() {
      return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }

   public boolean isPanicking = false;

   public boolean isPanicking() {
      return this.getHealth() < this.getMaxHealth() / 3 && this.isAlive();
   }

   public boolean getPanicking() {
      return this.isPanicking;
   }

   public void setPanicking(boolean panicking) {
      this.isPanicking = panicking;
   }

   class WolfPanicGoal extends PanicGoal {
      public WolfPanicGoal(double v) {
         super(Direwolf.this, v);
      }

      public boolean shouldPanic() {
         return this.mob.isFreezing() || this.mob.isOnFire() || this.mob.getHealth() < this.mob.getMaxHealth() / 3 && this.mob.isAlive();
      }
   }
}