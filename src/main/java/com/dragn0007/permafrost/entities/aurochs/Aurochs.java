package com.dragn0007.permafrost.entities.aurochs;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.dragnlivestock.entities.util.LOAnimations;
import com.dragn0007.dragnlivestock.gui.OxMenu;
import com.dragn0007.dragnlivestock.items.LOItems;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.entities.titanis.Titanis;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Aurochs extends AbstractOMount implements GeoEntity {

	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(Permafrost.MODID, "entities/aurochs");
	private static final ResourceLocation TFC_LOOT_TABLE = new ResourceLocation(Permafrost.MODID, "entities/tfc/tfc_aurochs");
	@Override
	public @NotNull ResourceLocation getDefaultLootTable() {
		if (ModList.get().isLoaded("tfc")) {
			return TFC_LOOT_TABLE;
		}
		return LOOT_TABLE;
	}

	public Aurochs(EntityType<? extends Aurochs> type, Level level) {
		super(type, level);
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0D, (double) this.getEyeHeight() * 0.6F, (double) (this.getBbWidth() * 1.4F));
		//              ^ Side offset                      ^ Height offset                   ^ Length offset
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 45.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.20F)
				.add(Attributes.ATTACK_DAMAGE, 4D);
	}

	@Override
	public void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.7D));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.9, true));
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 0.0F));

		this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D, Aurochs.class));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));

		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 6, true, false,
				entity -> entity.getType().is(LOTags.Entity_Types.WOLVES) && (entity instanceof TamableAnimal && !((TamableAnimal) entity).isTame()))  {
		});
		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 6, true, false,
				entity -> entity.getType().is(PFTags.Entity_Types.PREDATORS) && (entity instanceof TamableAnimal && !((TamableAnimal) entity).isTame()))  {
		});

		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 2, true, false,
				entity -> entity instanceof Player && !this.isTamed() && !entity.isCrouching()
		));
	}

	public int getInventorySize() {
		return this.hasChest() ? 25 : super.getInventorySize();
	}

	@Override
	public void openInventory(Player player) {
		if(player instanceof ServerPlayer serverPlayer && this.isTamed()) {
			NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((containerId, inventory, p) -> {
				return new OxMenu(containerId, inventory, this.inventory, this);
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
		double speedThreshold = 0.01;

		AnimationController<T> controller = tAnimationState.getController();

		if (tAnimationState.isMoving()) {
			if (currentSpeed > speedThreshold) {
				controller.setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.2);
			} else {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.0);
			}
		} else {
			controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
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

	public Aurochs leader;
	public int herdSize = 1;

	public boolean isFollower() {
		return this.leader != null && this.leader.isAlive();
	}

	public Aurochs startFollowing(Aurochs cow) {
		this.leader = cow;
		cow.addFollower();
		return cow;
	}

	public void stopFollowing() {
		this.leader.removeFollower();
		this.leader = null;
	}

	public void addFollower() {
		++this.herdSize;
	}

	public void removeFollower() {
		--this.herdSize;
	}

	public boolean canBeFollowed() {
		return this.hasFollowers() && this.herdSize < this.getMaxHerdSize();
	}

	public int getMaxHerdSize() {
		return LivestockOverhaulCommonConfig.COW_HERD_MAX.get();
	}

	public boolean hasFollowers() {
		return this.herdSize > 1;
	}

	public boolean inRangeOfLeader() {
		return this.distanceToSqr(this.leader) <= 120.0D;
	}

	public void pathToLeader() {
		if (this.isFollower()) {
			this.getNavigation().moveTo(this.leader, 1.0D);
		}

	}

	public void addFollowers(Stream<? extends Aurochs> p_27534_) {
		p_27534_.limit((long)(this.getMaxHerdSize() - this.herdSize)).filter((cow) -> {
			return cow != this;
		}).forEach((cow) -> {
			cow.startFollowing(this);
		});
	}

	public void tick() {
		super.tick();
		if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
			List<? extends Aurochs> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(20.0D, 20.0D, 20.0D));
			if (list.size() <= 1) {
				this.herdSize = 1;
			}
		}

		replenishMilkCounter++;

		if (replenishMilkCounter >= LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get()) {
			this.setMilked(false);
		}
	}

	public int replenishMilkCounter = 0;
	private boolean milked = false;
	public boolean wasMilked() {
		return this.milked;
	}
	public boolean getMilked() {
		return this.milked;
	}
	public void setMilked(boolean milked) {
		this.milked = milked;
	}

	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);

		if (itemstack.is(Items.SHEARS) && player.isShiftKeyDown()) {
			if (this.hasChest()) {
				this.dropEquipment();
				this.inventory.removeAllItems();

				this.setChest(false);
				this.playChestEquipsSound();

				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		if(!this.hasChest() && itemstack.is(Blocks.CHEST.asItem())) {
			this.setChest(true);
			this.playChestEquipsSound();
			if(!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			this.createInventory();
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}

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

		if (itemstack.is(Items.BUCKET) && !this.isBaby() && (!wasMilked() || replenishMilkCounter >= LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get())
				&& (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BIPRODUCTS.get() ||
				(LivestockOverhaulCommonConfig.GENDERS_AFFECT_BIPRODUCTS.get() && this.isFemale()))) {

			player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, Items.MILK_BUCKET.getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			replenishMilkCounter = 0;
			setMilked(true);

			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public void doPlayerRide(Player player) {
	}

	@Override
	public boolean isSaddleable() {
		return false;
	}

	public static final Ingredient FOOD_ITEMS = Ingredient.of(PFTags.Items.AUROCHS_FOOD);

	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Override
	public boolean handleEating(Player player, ItemStack itemStack) {
		int i = 0;
		int j = 0;
		float f = 0.0F;
		boolean flag = false;
		if (itemStack.is(PFTags.Items.AUROCHS_FOOD)) {
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
			setTamed(true);
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

	@Override
	public SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.COW_AMBIENT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.COW_DEATH;
	}

	@Nullable
	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource damageSource) {
		super.getHurtSound(damageSource);
		return SoundEvents.COW_HURT;
	}

	@Override
	public SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.COW_HURT;
	}

	@Override
	public boolean canWearArmor() {
		return false;
	}

	public static final EntityDataAccessor<ResourceLocation> VARIANT_TEXTURE = SynchedEntityData.defineId(Aurochs.class, LivestockOverhaul.RESOURCE_LOCATION);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Aurochs.class, EntityDataSerializers.INT);

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

	public static final EntityDataAccessor<Integer> GENDER = SynchedEntityData.defineId(Aurochs.class, EntityDataSerializers.INT);

	public int getGender() {
		return this.entityData.get(GENDER);
	}

	public void setGender(int gender) {
		this.entityData.set(GENDER, gender);
	}

	public ResourceLocation getTextureResource() {
		return this.entityData.get(VARIANT_TEXTURE);
	}

	public int getVariant() {
		return this.entityData.get(VARIANT);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT_TEXTURE, AurochsModel.Variant.variantFromOrdinal(variant).resourceLocation);
		this.entityData.set(VARIANT, variant);
	}

	public void setVariantTexture(String variant) {
		ResourceLocation resourceLocation = ResourceLocation.tryParse(variant);
		if (resourceLocation == null) {
			resourceLocation = AurochsModel.Variant.BROWN.resourceLocation;
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

		if (tag.contains("Milked")) {
			setMilked(tag.getBoolean("Milked"));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
		tag.putString("Variant_Texture", this.getTextureResource().toString());
		tag.putInt("Gender", this.getGender());
		tag.putBoolean("Milked", getMilked());
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (data == null) {
			data = new AgeableMobGroupData(0.2F);
		}

		Random random = new Random();
		this.setVariant(random.nextInt(AurochsModel.Variant.values().length));
		this.setGender(random.nextInt(Gender.values().length));

		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(VARIANT_TEXTURE, AurochsModel.Variant.BROWN.resourceLocation);
		this.entityData.define(GENDER, 0);
	}

	@Override
	public boolean canParent() {
		return !this.isPassenger() && !this.isBaby() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
		if (animal == this) {
			return false;
		} else if (!(animal instanceof Aurochs)) {
			return false;
		} else {
			if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
				return this.canParent() && ((Aurochs) animal).canParent();
			} else {
				Aurochs partner = (Aurochs) animal;
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
		Aurochs abstracthorse;
			Aurochs horse = (Aurochs) ageableMob;
			abstracthorse = EntityTypes.AUROCHS_ENTITY.get().create(serverLevel);

			int i = this.random.nextInt(9);
			int variant;
			if (i < 4) {
				variant = this.getVariant();
			} else if (i < 8) {
				variant = horse.getVariant();
			} else {
				variant = this.random.nextInt(AurochsModel.Variant.values().length);
			}

			int gender;
			gender = this.random.nextInt(Gender.values().length);

			((Aurochs) abstracthorse).setVariant(variant);
			((Aurochs) abstracthorse).setGender(gender);

		return abstracthorse;
	}

}