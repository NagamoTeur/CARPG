package com.hollingsworth.arsnouveau.common.potions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class ShockedEffect extends MobEffect {
   public ShockedEffect() {
      super(MobEffectCategory.HARMFUL, 2039587);
   }

   public boolean m_6584_(int duration, int amp) {
      int j = 25 >> amp;
      return j > 0 ? duration % j == 0 : true;
   }

   public void m_6742_(LivingEntity entity, int amp) {
      int multiplier = 0;

      for (ItemStack i : entity.m_6168_()) {
         IEnergyStorage energyStorage = (IEnergyStorage)i.getCapability(ForgeCapabilities.ENERGY).orElse(null);
         if (energyStorage != null) {
            multiplier++;
         }
      }

      IEnergyStorage energyStorage = (IEnergyStorage)entity.m_21205_().getCapability(ForgeCapabilities.ENERGY).orElse(null);
      if (energyStorage != null) {
         multiplier++;
      }

      energyStorage = (IEnergyStorage)entity.m_21206_().getCapability(ForgeCapabilities.ENERGY).orElse(null);
      if (energyStorage != null) {
         multiplier++;
      }

      if (multiplier > 0) {
         int numTicks = 0;
         if (entity instanceof Player) {
            CompoundTag var10 = entity.getPersistentData().m_128469_("PlayerPersisted");
         }

         entity.m_6469_(DamageSource.f_19306_, (float)(20 * multiplier * (amp + 1)));
      }
   }
}
