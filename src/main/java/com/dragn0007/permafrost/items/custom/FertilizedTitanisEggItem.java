package com.dragn0007.permafrost.items.custom;

import com.dragn0007.permafrost.util.ThrownFertilizedTitanisEgg;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FertilizedTitanisEggItem extends Item {
   public FertilizedTitanisEggItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
      if (!level.isClientSide) {
         ThrownFertilizedTitanisEgg thrownegg = new ThrownFertilizedTitanisEgg(level, player);
         thrownegg.setItem(itemstack);
         thrownegg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
         level.addFreshEntity(thrownegg);
      }

      player.awardStat(Stats.ITEM_USED.get(this));
      if (!player.getAbilities().instabuild) {
         itemstack.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
   }
}