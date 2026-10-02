package com.hollingsworth.arsnouveau.api.spell.wrapped_caster;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;

public class LivingCaster implements IWrappedCaster {
   public LivingEntity livingEntity;

   public LivingCaster(LivingEntity livingEntity) {
      this.livingEntity = livingEntity;
   }

   public static LivingCaster from(LivingEntity livingEntity) {
      if (livingEntity instanceof Player player && !(player instanceof FakePlayer)) {
         return new PlayerCaster(player);
      }

      return new LivingCaster(livingEntity);
   }

   @Override
   public SpellContext.CasterType getCasterType() {
      return SpellContext.CasterType.LIVING_ENTITY;
   }

   @NotNull
   @Override
   public List<FilterableItemHandler> getInventory() {
      List<FilterableItemHandler> filterableItemHandlers = new ArrayList<>();
      this.livingEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> filterableItemHandlers.add(new FilterableItemHandler(cap)));
      return filterableItemHandlers;
   }

   @Override
   public Direction getFacingDirection() {
      return this.livingEntity.m_6350_();
   }

   @Override
   public Vec3 getPosition() {
      return this.livingEntity.m_20182_();
   }
}
