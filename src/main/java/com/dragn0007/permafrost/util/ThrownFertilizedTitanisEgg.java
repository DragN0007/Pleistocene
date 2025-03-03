package com.dragn0007.permafrost.util;

import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.permafrost.entities.titanis.Titanis;
import com.dragn0007.permafrost.entities.titanis.TitanisModel;
import com.dragn0007.permafrost.items.PFItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownFertilizedTitanisEgg extends ThrowableItemProjectile {

   public ThrownFertilizedTitanisEgg(Level p_37481_, LivingEntity p_37482_) {
      super(EntityType.EGG, p_37482_, p_37481_);
   }

   public void handleEntityEvent(byte p_37484_) {
      if (p_37484_ == 3) {
         double d0 = 0.08D;

         for(int i = 0; i < 8; ++i) {
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
         }
      }

   }

   protected void onHitEntity(EntityHitResult p_37486_) {
      super.onHitEntity(p_37486_);
      p_37486_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
   }

   @Override
   protected void onHit(HitResult p_37488_) {
      super.onHit(p_37488_);
      if (!this.level().isClientSide) {
         int i = 1;
         if (this.random.nextInt(64) == 0) {
            i = 4;
         }

         for (int j = 0; j < i; ++j) {
            if (getItem().is(PFItems.FERTILIZED_TITANIS_EGG.get())) {
               Titanis titanis = com.dragn0007.permafrost.entities.EntityTypes.TITANIS_ENTITY.get().create(this.level());
               titanis.setAge(-24000);
               titanis.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);

               int randomVariant = this.level().getRandom().nextInt(TitanisModel.Variant.values().length);
               titanis.setVariant(randomVariant);

               int randomGender = this.level().getRandom().nextInt(AbstractOMount.Gender.values().length);
               titanis.setGender(randomGender);

               this.level().addFreshEntity(titanis);
            }
         }

         this.level().broadcastEntityEvent(this, (byte) 3);
         this.discard();
      }
   }

   protected Item getDefaultItem() {
      return PFItems.FERTILIZED_TITANIS_EGG.get();
   }
}