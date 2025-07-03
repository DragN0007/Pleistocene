package com.dragn0007.permafrost.entities.paraceratherium;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.ai.GroundTieGoal;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.dragnlivestock.entities.util.LOAnimations;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.gui.ParaceratheriumMenu;
import com.dragn0007.permafrost.util.PFTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class Paraceratherium extends AbstractOMount implements GeoEntity {

	public Paraceratherium(EntityType<? extends Paraceratherium> type, Level level) {
		super(type, level);
	}

	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(Permafrost.MODID, "entities/paraceratherium");
	private static final ResourceLocation TFC_LOOT_TABLE = new ResourceLocation(Permafrost.MODID, "entities/tfc/tfc_paraceratherium");
	@Override
	public @NotNull ResourceLocation getDefaultLootTable() {
		if (ModList.get().isLoaded("tfc")) {
			return TFC_LOOT_TABLE;
		}
		return LOOT_TABLE;
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0D, (double) this.getEyeHeight() * 0.8F, (double) (this.getBbWidth() * 1.2F));
		//              ^ Side offset                      ^ Height offset                   ^ Length offset
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 60.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.20F)
				.add(Attributes.ATTACK_DAMAGE, 3D);
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
		this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D, Paraceratherium.class));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
	}

	@Override
	public boolean canJump() {
		return false;
	}

	@Override
	public int getInventorySize() {
		return 26;
	}

	@Override
	public void openInventory(Player player) {
		if(player instanceof ServerPlayer serverPlayer && this.isTamed()) {
			NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((containerId, inventory, p) -> {
				return new ParaceratheriumMenu(containerId, inventory, this.inventory, this);
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
	}

	private <T extends GeoAnimatable> PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<T> tAnimationState) {
		double currentSpeed = this.getDeltaMovement().lengthSqr();
		double speedThreshold = 0.02;

		double x = this.getX() - this.xo;
		double z = this.getZ() - this.zo;

		boolean isMoving = (x * x + z * z) > 0.0001;

		double movementSpeed = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
		double animationSpeed = Math.max(0.1, movementSpeed);

		AnimationController<T> controller = tAnimationState.getController();

		if (isMoving) {
			if (this.isVehicle() && !this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPRINT_SPEED_MOD) && this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WALK_SPEED_MOD)) {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(Math.max(0.1, 0.85 * controller.getAnimationSpeed() + animationSpeed));

			} else if (this.isVehicle() && currentSpeed > speedThreshold) {
				controller.setAnimation(RawAnimation.begin().then("trot", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(Math.max(0.1, 0.86 * controller.getAnimationSpeed() + animationSpeed));

			} else {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(Math.max(0.1, 0.81 * controller.getAnimationSpeed() + animationSpeed));
			}

		} else {
			if (this.isSaddled() && !this.isVehicle() && LivestockOverhaulCommonConfig.GROUND_TIE.get()) {
				controller.setAnimation(RawAnimation.begin().then("idle3", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.0);
			} else {
				controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
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
	}

	@Override
	public SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.CAMEL_AMBIENT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.CAMEL_DEATH;
	}

	@Nullable
	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.CAMEL_EAT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource damageSource) {
		super.getHurtSound(damageSource);
		return SoundEvents.CAMEL_HURT;
	}

	@Override
	public SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.CAMEL_HURT;
	}

	public static final Ingredient FOOD_ITEMS = Ingredient.of(PFTags.Items.PARACERATHERIUM_FOOD);

	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Override
	public boolean handleEating(Player player, ItemStack itemStack) {
		int i = 0;
		int j = 0;
		float f = 0.0F;
		boolean flag = false;
		if (itemStack.is(PFTags.Items.PARACERATHERIUM_FOOD)) {
			i = 90;
			j = 6;
			f = 10.0F;
			if (this.isTamed() && this.getAge() == 0 && this.canFallInLove()) {
				flag = true;
				this.setInLove(player);
			}
		}

		if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
			this.heal(f);
			flag = true;
		}

		if (this.isBaby() && i > 0) {
			this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
			if (!this.level().isClientSide) {
				this.ageUp(i);
			}

			flag = true;
		}

		if (j > 0 && (flag || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
			flag = true;
			if (!this.level().isClientSide) {
				this.modifyTemper(j);
			}
		}

		if (flag) {
			this.gameEvent(GameEvent.ENTITY_INTERACT);
			if (!this.isSilent()) {
				SoundEvent soundevent = this.getEatingSound();
				if (soundevent != null) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), this.getEatingSound(), this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
				}
			}
		}

		return flag;
	}

	public Vec3 calcOffset ( double x, double y, double z){
		double rad = this.getYRot() * Math.PI / 180;

		double xOffset = this.position().x + (x * Math.cos(rad) - z * Math.sin(rad));
		double yOffset = this.position().y + y;
		double zOffset = this.position().z + (x * Math.sin(rad) + z * Math.cos(rad));

		return new Vec3(xOffset, yOffset, zOffset);
	}

	@Override
	public boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < 2;
	}

	@Override
	public void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
		int i = this.getPassengers().indexOf(entity);
		switch (i) {
			case 0:
				entity.setPos(this.calcOffset(0, 3.0, -0.4));
				break;
			case 1:
				entity.setPos(this.calcOffset(0, 2.9, -2));
				break;
		}
	}

	@Override
	public LivingEntity getControllingPassenger() {
		if (this.isTamed() && this.isSaddled()) {
			return (LivingEntity) this.getFirstPassenger();
		}
		return null;
	}

	@Override
	public boolean canWearArmor() {
		return false;
	}

	public static final EntityDataAccessor<ResourceLocation> VARIANT_TEXTURE = SynchedEntityData.defineId(Paraceratherium.class, LivestockOverhaul.RESOURCE_LOCATION);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Paraceratherium.class, EntityDataSerializers.INT);

	public ResourceLocation getTextureResource() {
		return this.entityData.get(VARIANT_TEXTURE);
	}

	public int getVariant() {
		return this.entityData.get(VARIANT);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT_TEXTURE, ParaceratheriumModel.Variant.variantFromOrdinal(variant).resourceLocation);
		this.entityData.set(VARIANT, variant);
	}

	public void setVariantTexture(String variant) {
		ResourceLocation resourceLocation = ResourceLocation.tryParse(variant);
		if (resourceLocation == null) {
			resourceLocation = ParaceratheriumModel.Variant.GREY.resourceLocation;
		}
		this.entityData.set(VARIANT_TEXTURE, resourceLocation);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("Variant")) {
			this.setVariant(tag.getInt("Variant"));
		}

		if (tag.contains("Variant_Texture")) {
			this.setVariantTexture(tag.getString("Variant_Texture"));
		}

		if (tag.contains("Gender")) {
			this.setGender(tag.getInt("Gender"));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
		tag.putString("Variant_Texture", this.getTextureResource().toString());
		tag.putInt("Gender", this.getGender());
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (data == null) {
			data = new AgeableMob.AgeableMobGroupData(0.2F);
		}
		Random random = new Random();
		this.setVariant(random.nextInt(ParaceratheriumModel.Variant.values().length));
		this.setGender(random.nextInt(Gender.values().length));

		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(VARIANT_TEXTURE, ParaceratheriumModel.Variant.GREY.resourceLocation);
		this.entityData.define(GENDER, 0);
	}

	@Override
	public boolean canParent() {
		return !this.isVehicle() && !this.isPassenger() && !this.isBaby() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
		if (animal == this) {
			return false;
		} else if (!(animal instanceof Paraceratherium)) {
			return false;
		} else {
			if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
				return this.canParent() && ((Paraceratherium) animal).canParent();
			} else {
				Paraceratherium partner = (Paraceratherium) animal;
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
		Paraceratherium paraceratherium1;
			Paraceratherium paraceratherium = (Paraceratherium) ageableMob;
			paraceratherium1 = EntityTypes.PARACERATHERIUM_ENTITY.get().create(serverLevel);

			int i = this.random.nextInt(9);
			int variant;
			if (i < 4) {
				variant = this.getVariant();
			} else if (i < 8) {
				variant = paraceratherium.getVariant();
			} else {
				variant = this.random.nextInt(ParaceratheriumModel.Variant.values().length);
			}

			int gender;
			gender = this.random.nextInt(Gender.values().length);

			paraceratherium1.setVariant(variant);
			paraceratherium1.setGender(gender);

		return paraceratherium1;
	}

}