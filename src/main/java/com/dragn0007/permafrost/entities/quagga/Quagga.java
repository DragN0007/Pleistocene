package com.dragn0007.permafrost.entities.quagga;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.ai.GroundTieGoal;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.horse.OHorseModel;
import com.dragn0007.dragnlivestock.entities.marking_layer.EquineEyeColorOverlay;
import com.dragn0007.dragnlivestock.entities.marking_layer.EquineMarkingOverlay;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.dragnlivestock.entities.util.LOAnimations;
import com.dragn0007.dragnlivestock.event.LivestockOverhaulClientEvent;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.gui.QuaggaMenu;
import com.dragn0007.permafrost.util.PFTags;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class Quagga extends OHorse implements GeoEntity {

	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(LivestockOverhaul.MODID, "entities/o_horse");
	private static final ResourceLocation VANILLA_LOOT_TABLE = new ResourceLocation("minecraft", "entities/horse");
	private static final ResourceLocation TFC_LOOT_TABLE = new ResourceLocation("tfc", "entities/horse");
	@Override
	public @NotNull ResourceLocation getDefaultLootTable() {
		if (LivestockOverhaulCommonConfig.USE_VANILLA_LOOT.get()) {
			return VANILLA_LOOT_TABLE;
		}
		if (ModList.get().isLoaded("tfc")) {
			return TFC_LOOT_TABLE;
		}
		return LOOT_TABLE;
	}

	@Override
	public boolean hasGrowableHair() {
		return true;
	}

	public Quagga(EntityType<? extends Quagga> type, Level level) {
		super(type, level);
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0D, (double) this.getEyeHeight() * 0.6F, (double) (this.getBbWidth() * 1F));
		//              ^ Side offset                      ^ Height offset                   ^ Length offset
	}

	public static AttributeSupplier.Builder createBaseHorseAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.JUMP_STRENGTH)
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.255F)
				.add(Attributes.ATTACK_DAMAGE, 1D);
	}

	public void randomizeAttributes() {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
	}

	@Override
	public void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new GroundTieGoal(this));

		this.goalSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.7D));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 0.0F));

		this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D, AbstractOMount.class));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, entity ->
				(entity.getType().is(LOTags.Entity_Types.WOLVES) && !this.isTamed()) ||
						(entity.getType().is(LOTags.Entity_Types.WOLVES) && (entity instanceof TamableAnimal && !((TamableAnimal) entity).isTame())) && this.isTamed()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, entity ->
				(entity.getType().is(LOTags.Entity_Types.HUNTING_DOGS) && !this.isTamed()) ||
						(entity.getType().is(LOTags.Entity_Types.HUNTING_DOGS) && (entity instanceof TamableAnimal && !((TamableAnimal) entity).isTame())) && this.isTamed()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, entity ->
				(entity.getType().is(PFTags.Entity_Types.PREDATORS) && !this.isTamed()) ||
						(entity.getType().is(PFTags.Entity_Types.PREDATORS) && (entity instanceof TamableAnimal && !((TamableAnimal) entity).isTame())) && this.isTamed()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, entity ->
				entity instanceof Player && !this.isTamed()
		));
	}

	public float generateRandomMaxHealth() {
		float baseHealth;
		baseHealth = 16.0F;
		return baseHealth + this.random.nextInt(3) + this.random.nextInt(5);
	}

	public double generateRandomJumpStrength() {
		double baseStrength = 0.4F;
		double multiplier = this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.25D;
		baseStrength = 0.2F;
		return baseStrength + multiplier;
	}

	public double generateRandomSpeed() {
		double baseSpeed = 0.0F;
		double multiplier = (this.random.nextDouble() * 0.1D + this.random.nextDouble() * 0.1D + this.random.nextDouble() * 0.1D) * 0.30D;
		baseSpeed = 0.2F;
		return baseSpeed + multiplier;
	}

	public int getInventorySize() {
		if (this.hasChest()) {
			return 17;
		}
		return super.getInventorySize();
	}

	@Override
	public void positionRider(Entity entity, MoveFunction moveFunction) {
		if (this.hasPassenger(entity)) {

			double offsetX = 0;
			double offsetY = 0.6;
			double offsetZ = -0.1;

			if (this.isJumping()) {
				offsetZ = -0.4;
			}

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

	@Override
	public void openInventory(Player player) {
		if(player instanceof ServerPlayer serverPlayer && this.isTamed()) {
			NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((containerId, inventory, p) -> {
				return new QuaggaMenu(containerId, inventory, this.inventory, this);
			}, this.getDisplayName()), (data) -> {
				data.writeInt(this.getInventorySize());
				data.writeInt(this.getId());
			});
		}
	}

	@Override
	public boolean canPerformRearing() {
		return false;
	}

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 2, this::predicate));
		controllers.add(LOAnimations.genericAttackAnimation(this, LOAnimations.ATTACK));
		controllers.add(new AnimationController<>(this, "emoteController", 5, this::emotePredicate));
	}

	private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
		double x = this.getX() - this.xo;
		double z = this.getZ() - this.zo;
		double currentSpeed = this.getDeltaMovement().lengthSqr();
		double speedThreshold = 0.015;

		boolean isMoving = (x * x + z * z) > 0.0001;

		double movementSpeed = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
		double animationSpeed = Math.max(0.1, movementSpeed);

		AnimationController<T> controller = tAnimationState.getController();

		if ((!this.isTamed() || this.isWearingHarness()) && this.isVehicle() && !this.isJumping()) {
			controller.setAnimation(RawAnimation.begin().then("buck", Animation.LoopType.LOOP));
			controller.setAnimationSpeed(1.3);
		} else if (this.isJumping()) {
			controller.setAnimation(RawAnimation.begin().then("jump", Animation.LoopType.PLAY_ONCE));
			controller.setAnimationSpeed(1.0);
		} else {
			if (isMoving) {
				if (!LivestockOverhaulClientEvent.HORSE_WALK_BACKWARDS.isDown()) {
					if (this.isAggressive() || (this.isVehicle() && this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPRINT_SPEED_MOD)) || (!this.isVehicle() && currentSpeed > speedThreshold)) {
						controller.setAnimation(RawAnimation.begin().then("sprint", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.82 * controller.getAnimationSpeed() + animationSpeed));

					} else if (this.isVehicle() && !this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WALK_SPEED_MOD) && !this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPRINT_SPEED_MOD)) {
						controller.setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.78 * controller.getAnimationSpeed() + animationSpeed));

					} else if (this.isOnSand() && this.isVehicle() && !this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WALK_SPEED_MOD) && !this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPRINT_SPEED_MOD)) {
						controller.setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.78 * controller.getAnimationSpeed() + animationSpeed));

					} else if (this.isVehicle() && this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WALK_SPEED_MOD)) {
						controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.83 * controller.getAnimationSpeed() + animationSpeed));

					} else if (this.isVehicle() && LivestockOverhaulClientEvent.HORSE_SPANISH_WALK_TOGGLE.isDown() && this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WALK_SPEED_MOD)) {
						controller.setAnimation(RawAnimation.begin().then("spanish_walk", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.78 * controller.getAnimationSpeed() + animationSpeed));
					} else {
						controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.80 * controller.getAnimationSpeed() + animationSpeed));
					}
				} else if (this.isVehicle() && LivestockOverhaulClientEvent.HORSE_WALK_BACKWARDS.isDown()) {
					if (this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WALK_SPEED_MOD)) {
						controller.setAnimation(RawAnimation.begin().then("walk_back", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.76 * controller.getAnimationSpeed() + animationSpeed));
					} else {
						controller.setAnimation(RawAnimation.begin().then("walk_back", Animation.LoopType.LOOP));
						controller.setAnimationSpeed(Math.max(0.1, 0.83 * controller.getAnimationSpeed() + animationSpeed));
					}
				}
			} else {
				if (this.isVehicle() || !LivestockOverhaulCommonConfig.GROUND_TIE.get()) {
					controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
				} else if (this.isSleeping()) {
					controller.setAnimation(RawAnimation.begin().then("idle_sleep", Animation.LoopType.LOOP));
				} else if (this.isSleeping() && !this.isVehicle() && this.isFollower()) {
					controller.setAnimation(RawAnimation.begin().then("sleep", Animation.LoopType.LOOP));
				} else {
					controller.setAnimation(RawAnimation.begin().then("ground_tie", Animation.LoopType.LOOP));
				}
				controller.setAnimationSpeed(1.0);
			}

		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}

	@Override
	public void playEmote(String emoteName, String loopType) {
		AnimationController<?> controller = this.getAnimatableInstanceCache().getManagerForId(this.getId()).getAnimationControllers().get("emoteController");
		controller.forceAnimationReset();
		controller.stop();
		controller.setAnimation(RawAnimation.begin().then(emoteName, Animation.LoopType.fromString(loopType)));
		this.shouldEmote = true;
	}

	private <T extends GeoAnimatable> PlayState emotePredicate(AnimationState<T> tAnimationState) {
		AnimationController<T> controller = tAnimationState.getController();

		if (tAnimationState.isMoving() || !this.shouldEmote) {
			controller.forceAnimationReset();
			controller.stop();
			this.shouldEmote = false;
			return PlayState.STOP;
		}

		return PlayState.CONTINUE;
	}

	public int maxSprint = 20 * LivestockOverhaulCommonConfig.BASE_HORSE_SPRINT_TIME.get();
	public int sprintTick = maxSprint;

	@Override
	public void tick() {
		super.tick();
		if (this.isOnSand()) {
			if (!this.hasSlownessEffect()) {
				this.applySlownessEffect();
			}
		} else {
			if (this.hasSlownessEffect()) {
				this.removeSlownessEffect();
			}
		}

		if (!this.isBaby()) {
			maneGrowthTick++;
			tailGrowthTick++;

			if (maneGrowthTick >= LivestockOverhaulCommonConfig.HORSE_HAIR_GROWTH_TIME.get() && this.getManeType() > 2) {
				this.setManeType(this.getManeType() - 1);
				maneGrowthTick = 0;
			}

			if (tailGrowthTick >= LivestockOverhaulCommonConfig.HORSE_HAIR_GROWTH_TIME.get() && this.getTailType() > 1) {
				this.setTailType(this.getTailType() - 1);
				tailGrowthTick = 0;
			}
		}

		Entity controllingPassenger = this.getControllingPassenger();
		Entity entity = controllingPassenger;
		int sprintLeftInSeconds = sprintTick / 20;
		double x = this.getX() - this.xo;
		double z = this.getZ() - this.zo;
		boolean isMoving = (x * x + z * z) > 0.0001;

		if (this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPRINT_SPEED_MOD) && !(sprintTick <= 0) && this.hasControllingPassenger() && isMoving) {
			sprintTick--;
			if (controllingPassenger != null && !(sprintTick <= 0)) {
				if (controllingPassenger instanceof Player player && LivestockOverhaulClientConfig.HORSE_SPRINT_TIMER.get()) {
					player.displayClientMessage(Component.translatable("Sprint Left: " + sprintLeftInSeconds + "s").withStyle(ChatFormatting.GOLD), true);
				}
			}
		}

		if ((!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPRINT_SPEED_MOD) || !isMoving)) {
			if (sprintTick < maxSprint && isMoving) {
				sprintTick++;
			} else if (sprintTick < maxSprint && !isMoving) {
				sprintTick++;
				sprintTick++;
			}
		}

		if (sprintTick <= 0 && controllingPassenger != null) {
			AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
			this.handleSpeedRequest(-1);
			movementSpeed.removeModifier(SPRINT_SPEED_MOD);
			if (controllingPassenger != null) {
				if (controllingPassenger instanceof Player player && LivestockOverhaulClientConfig.HORSE_SPRINT_TIMER.get()) {
					player.displayClientMessage(Component.translatable("Sprint Depleted").withStyle(ChatFormatting.DARK_RED), true);
				}
			}
		} else if (entity == null || !this.hasControllingPassenger()) {
			return;
		}
	}

	@Override
	public boolean canWearArmor() {
		return false;
	}

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public int getVariant() {
		return this.entityData.get(VARIANT);
	}
	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
		this.entityData.set(VARIANT_TEXTURE, OHorseModel.Variant.variantFromOrdinal(variant).resourceLocation);
	}
	public static final EntityDataAccessor<ResourceLocation> VARIANT_TEXTURE = SynchedEntityData.defineId(Quagga.class, LivestockOverhaul.RESOURCE_LOCATION);
	public ResourceLocation getTextureResource() {
		return this.entityData.get(VARIANT_TEXTURE);
	}
	public void setVariantTexture(String variant) {
		ResourceLocation resourceLocation = ResourceLocation.tryParse(variant);
		if (resourceLocation == null) {
			resourceLocation = OHorseModel.Variant.CREAM.resourceLocation;
		}
		this.entityData.set(VARIANT_TEXTURE, resourceLocation);
	}

	public static final EntityDataAccessor<Integer> OVERLAY = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public int getOverlayVariant() {
		return this.entityData.get(OVERLAY);
	}
	public void setOverlayVariant(int variant) {
		this.entityData.set(OVERLAY, variant);
		this.entityData.set(OVERLAY_TEXTURE, EquineMarkingOverlay.overlayFromOrdinal(variant).resourceLocation);
	}
	public static final EntityDataAccessor<ResourceLocation> OVERLAY_TEXTURE = SynchedEntityData.defineId(Quagga.class, LivestockOverhaul.RESOURCE_LOCATION);
	public ResourceLocation getOverlayLocation() {
		return this.entityData.get(OVERLAY_TEXTURE);
	}
	public void setOverlayVariantTexture(String variant) {
		ResourceLocation resourceLocation = ResourceLocation.tryParse(variant);
		if (resourceLocation == null) {
			resourceLocation = EquineMarkingOverlay.NONE.resourceLocation;
		}
		this.entityData.set(OVERLAY_TEXTURE, resourceLocation);
	}

	public static final EntityDataAccessor<Integer> STRIPES = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public ResourceLocation getStripeTextureResource() {
		return QuaggaStripeLayer.Overlay.overlayFromOrdinal(getEyeVariant()).resourceLocation;
	}
	public int getStripeVariant() {
		return this.entityData.get(STRIPES);
	}
	public void setStripeVariant(int variant) {
		this.entityData.set(STRIPES, variant);
	}

	public static final EntityDataAccessor<Integer> EYES = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public ResourceLocation getEyeTextureResource() {
		return EquineEyeColorOverlay.eyesFromOrdinal(getEyeVariant()).resourceLocation;
	}
	public int getEyeVariant() {
		return this.entityData.get(EYES);
	}
	public void setEyeVariant(int eyeVariant) {
		this.entityData.set(EYES, eyeVariant);
	}

	public static final EntityDataAccessor<Integer> MANE_TYPE = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public int getManeType() {
		return this.entityData.get(MANE_TYPE);
	}
	public void setManeType(int mane) {
		this.entityData.set(MANE_TYPE, mane);
	}

	public static final EntityDataAccessor<Integer> TAIL_TYPE = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public int getTailType() {
		return this.entityData.get(TAIL_TYPE);
	}
	public void setTailType(int tail) {
		this.entityData.set(TAIL_TYPE, tail);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("Variant")) {
			this.setVariant(tag.getInt("Variant"));
		}

		if (tag.contains("Overlay")) {
			this.setOverlayVariant(tag.getInt("Overlay"));
		}

		if (tag.contains("Stripes")) {
			this.setStripeVariant(tag.getInt("Stripes"));
		}

		if (tag.contains("Variant_Texture")) {
			this.setVariantTexture(tag.getString("Variant_Texture"));
		}

		if (tag.contains("Overlay_Texture")) {
			this.setOverlayVariantTexture(tag.getString("Overlay_Texture"));
		}

		if (tag.contains("Gender")) {
			this.setGender(tag.getInt("Gender"));
		}

		if (tag.contains("Eyes")) {
			this.setEyeVariant(tag.getInt("Eyes"));
		}

		if (tag.contains("SprintTime")) {
			this.sprintTick = tag.getInt("SprintTime");
		}

		if (tag.contains("ManeGrowthTime")) {
			this.maneGrowthTick = tag.getInt("ManeGrowthTime");
		}

		if (tag.contains("TailGrowthTime")) {
			this.tailGrowthTick = tag.getInt("TailGrowthTime");
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
		tag.putInt("Overlay", this.getOverlayVariant());
		tag.putInt("Stripes", this.getStripeVariant());
		tag.putString("Variant_Texture", this.getTextureResource().toString());
		tag.putString("Overlay_Texture", this.getOverlayLocation().toString());
		tag.putInt("Gender", this.getGender());
		tag.putInt("Eyes", this.getEyeVariant());
		tag.putInt("SprintTime", this.sprintTick);
		tag.putInt("ManeGrowthTime", this.maneGrowthTick);
		tag.putInt("TailGrowthTime", this.tailGrowthTick);
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (data == null) {
			data = new AgeableMobGroupData(0.2F);
		}
		Random random = new Random();
		this.setGender(random.nextInt(Gender.values().length));
		this.setStripeVariant(random.nextInt(QuaggaStripeLayer.Overlay.values().length));

		if (LivestockOverhaulCommonConfig.SPAWN_BY_BREED.get()) {
			this.setColor();
			this.setMarking();
		} else {
			this.setVariant(random.nextInt(OHorseModel.Variant.values().length));
			this.setOverlayVariant(random.nextInt(EquineMarkingOverlay.values().length));
		}

		if (LivestockOverhaulCommonConfig.EYES_BY_COLOR.get()) {
			this.setEyeColorByChance();
		} else {
			this.setEyeVariant(random.nextInt(EquineEyeColorOverlay.values().length));
		}

		this.randomizeAttributes();
		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
	}

	public void setColor() {

		if (random.nextDouble() < 0.15) {
			this.setOverlayVariant(random.nextInt(OHorseModel.Variant.values().length));
		} else if (random.nextDouble() > 0.15) {
			int[] variants = {0, 2, 6, 7, 10, 11, 13, 15, 18, 22, 25, 26};
			int randomIndex = new Random().nextInt(variants.length);
			this.setVariant(variants[randomIndex]);
		}

	}

	public void setMarking() {

		if (random.nextDouble() < 0.02) {
			this.setOverlayVariant(random.nextInt(EquineMarkingOverlay.values().length));
		} else if (random.nextDouble() > 0.02) {
			this.setVariant(0);
		}
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(OVERLAY, 0);
		this.entityData.define(STRIPES, 0);
		this.entityData.define(GENDER, 0);
		this.entityData.define(VARIANT_TEXTURE, OHorseModel.Variant.CREAM.resourceLocation);
		this.entityData.define(OVERLAY_TEXTURE, EquineMarkingOverlay.NONE.resourceLocation);
		this.entityData.define(MANE_TYPE, 0);
		this.entityData.define(TAIL_TYPE, 0);
		this.entityData.define(EYES, 0);
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
	public static final EntityDataAccessor<Integer> GENDER = SynchedEntityData.defineId(Quagga.class, EntityDataSerializers.INT);
	public int getGender() {
		return this.entityData.get(GENDER);
	}
	public void setGender(int gender) {
		this.entityData.set(GENDER, gender);
	}

	@Override
	public boolean canParent() {
		return !this.isVehicle() && !this.isPassenger() && !this.isBaby() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
		if (animal == this) {
			return false;
		} else if (!(animal instanceof OHorse) && !(animal instanceof Quagga)) {
			return false;
		} else {
			if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
				return this.canParent() && ((AbstractOMount) animal).canParent();
			} else {
				AbstractOMount partner = (AbstractOMount) animal;
				if (this.canParent() && partner.canParent() && this.getGender() != partner.getGender()) {
					return this.isFemale();
				}
			}
		}
		return false;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		Quagga foal;

		if (ageableMob instanceof OHorse horse && ageableMob.getClass() == OHorse.class) {
			foal = EntityTypes.QORSE_ENTITY.get().create(serverLevel);

			int variantChance = this.random.nextInt(14);
			int variant;
			if (variantChance < 6) {
				variant = this.getVariant();
			} else if (variantChance < 12) {
				variant = horse.getVariant();
			} else {
				variant = this.random.nextInt(OHorseModel.Variant.values().length);
			}
			foal.setVariant(variant);

			int overlayChance = this.random.nextInt(10);
			int overlay;
			if (overlayChance < 4) {
				overlay = this.getOverlayVariant();
			} else if (overlayChance < 8) {
				overlay = horse.getOverlayVariant();
			} else {
				overlay = this.random.nextInt(EquineMarkingOverlay.values().length);
			}
			foal.setOverlayVariant(overlay);

			int eyeColorChance = this.random.nextInt(11);
			int eyes;
			if (eyeColorChance < 5) {
				eyes = this.getEyeVariant();
			} else if (eyeColorChance < 10) {
				eyes = horse.getEyeVariant();
			} else {
				eyes = this.random.nextInt(EquineEyeColorOverlay.values().length);
			}
			foal.setEyeVariant(eyes);

			int gender;
			gender = this.random.nextInt(OHorse.Gender.values().length);
			foal.setGender(gender);

			foal.setFeatheringByBreed();
			foal.setManeType(3);
			foal.setTailType(2);


			if (this.random.nextInt(3) >= 1) {
				foal.generateRandomOHorseJumpStrength();

				int betterSpeed = (int) Math.max(horse.getSpeed(), this.random.nextInt(10) + 20);
				foal.setSpeed(betterSpeed);

				int betterHealth = (int) Math.max(horse.getHealth(), this.random.nextInt(20) + 40);
				foal.setHealth(betterHealth);
			}
		} else {
			Quagga partner = (Quagga) ageableMob;
			foal = EntityTypes.QUAGGA_ENTITY.get().create(serverLevel);

			int variantChance = this.random.nextInt(14);
			int variant;
			if (variantChance < 6) {
				variant = this.getVariant();
			} else if (variantChance < 12) {
				variant = partner.getVariant();
			} else {
				variant = this.random.nextInt(OHorseModel.Variant.values().length);
			}
			foal.setVariant(variant);

			int overlayChance = this.random.nextInt(10);
			int overlay;
			if (overlayChance < 4) {
				overlay = this.getOverlayVariant();
			} else if (overlayChance < 8) {
				overlay = partner.getOverlayVariant();
			} else {
				overlay = this.random.nextInt(EquineMarkingOverlay.values().length);
			}
			foal.setOverlayVariant(overlay);

			int eyeColorChance = this.random.nextInt(11);
			int eyes;
			if (eyeColorChance < 5) {
				eyes = this.getEyeVariant();
			} else if (eyeColorChance < 10) {
				eyes = partner.getEyeVariant();
			} else {
				eyes = this.random.nextInt(EquineEyeColorOverlay.values().length);
			}
			foal.setEyeVariant(eyes);

			int gender;
			gender = this.random.nextInt(OHorse.Gender.values().length);
			foal.setGender(gender);

			foal.setManeType(3);
			foal.setTailType(2);

			if (this.random.nextInt(3) >= 1) {
				foal.generateRandomOHorseJumpStrength();

				int betterSpeed = (int) Math.max(partner.getSpeed(), this.random.nextInt(10) + 20);
				foal.setSpeed(betterSpeed);

				int betterHealth = (int) Math.max(partner.getHealth(), this.random.nextInt(20) + 40);
				foal.setHealth(betterHealth);
			}
		}

		this.setOffspringAttributes(ageableMob, foal);
		return foal;
	}

}