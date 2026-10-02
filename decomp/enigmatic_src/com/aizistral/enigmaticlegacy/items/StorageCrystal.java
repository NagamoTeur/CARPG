package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.IPermanentCrystal;
import com.aizistral.enigmaticlegacy.helpers.ExperienceHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class StorageCrystal extends ItemBase implements IPermanentCrystal, Vanishable {
   public StorageCrystal() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41486_().m_41491_(null));
   }

   public ItemStack storeDropsOnCrystal(Collection<ItemEntity> drops, Player player, @Nullable ItemStack embeddedSoulCrystal) {
      ItemStack crystal = new ItemStack(this);
      CompoundTag crystalNBT = ItemNBTHelper.getNBT(crystal);
      int counter = 0;

      for (ItemEntity drop : drops) {
         ItemStack dropStack = drop.m_32055_();
         CompoundTag nbt = dropStack.serializeNBT();
         crystalNBT.m_128365_("storedStack" + counter, nbt);
         counter++;
      }

      if (embeddedSoulCrystal != null) {
         CompoundTag deserializedCrystal = new CompoundTag();
         embeddedSoulCrystal.deserializeNBT(deserializedCrystal);
         crystalNBT.m_128365_("embeddedSoul", deserializedCrystal);
      }

      ItemNBTHelper.setInt(crystal, "storedStacks", counter);
      int exp = ExperienceHelper.getPlayerXP(player);
      ExperienceHelper.drainPlayerXP(player, exp);
      ItemNBTHelper.setInt(crystal, "storedXP", (int)((double)exp * EnigmaticAmulet.savedXPFraction.getValue()));
      ItemNBTHelper.setBoolean(crystal, "isStored", true);
      return crystal;
   }

   public ItemStack retrieveDropsFromCrystal(ItemStack crystal, Player player, ItemStack retrieveSoul) {
      CompoundTag crystalNBT = ItemNBTHelper.getNBT(crystal);
      int counter = crystalNBT.m_128451_("storedStacks") - 1;
      int exp = crystalNBT.m_128451_("storedXP");

      for (int c = counter; c >= 0; c--) {
         CompoundTag nbt = crystalNBT.m_128469_("storedStack" + c);
         ItemStack stack = ItemStack.m_41712_(nbt);
         if (!player.m_150109_().m_36054_(stack)) {
            ItemEntity drop = new ItemEntity(player.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), stack);
            player.f_19853_.m_7967_(drop);
         }

         crystalNBT.m_128473_("storedStack" + c);
      }

      ExperienceHelper.addPlayerXP(player, exp);
      if (retrieveSoul != null) {
         EnigmaticItems.SOUL_CRYSTAL.retrieveSoulFromCrystal(player, retrieveSoul);
      } else {
         player.f_19853_.m_5594_(null, new BlockPos(player.m_20182_()), SoundEvents.f_11736_, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      ItemNBTHelper.setBoolean(crystal, "isStored", false);
      ItemNBTHelper.setInt(crystal, "storedStacks", 0);
      ItemNBTHelper.setInt(crystal, "storedXP", 0);
      return crystal;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      playerIn.m_6672_(handIn);
      if (!worldIn.f_46443_) {
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
   }
}
