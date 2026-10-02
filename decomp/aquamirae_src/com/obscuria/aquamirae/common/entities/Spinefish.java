package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class Spinefish extends AbstractSchoolingFish {
   public Spinefish(EntityType<? extends Spinefish> type, Level level) {
      super(type, level);
   }

   public Spinefish(SpawnEntity packet, Level level) {
      this((EntityType<? extends Spinefish>)AquamiraeEntities.SPINEFISH.get(), level);
   }

   @NotNull
   public ItemStack m_28282_() {
      return new ItemStack((ItemLike)AquamiraeItems.SPINEFISH_BUCKET.get());
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11758_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11759_;
   }

   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_11761_;
   }

   @NotNull
   protected SoundEvent m_5699_() {
      return SoundEvents.f_11760_;
   }

   public static SpawnPredicate<Spinefish> getSpawnRules() {
      return WaterAnimal::m_218282_;
   }
}
