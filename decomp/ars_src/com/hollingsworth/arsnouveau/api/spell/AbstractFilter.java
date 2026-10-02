package com.hollingsworth.arsnouveau.api.spell;

import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFilter extends AbstractEffect implements IFilter {
   public AbstractFilter(ResourceLocation registryName, String name) {
      super(registryName, name);
   }

   @Override
   public int getDefaultManaCost() {
      return 0;
   }

   @NotNull
   @Override
   protected Set<AbstractAugment> getCompatibleAugments() {
      return Set.of();
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!this.shouldResolveOnEntity(rayTraceResult)) {
         spellContext.setCanceled(true);
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!this.shouldResolveOnBlock(rayTraceResult)) {
         spellContext.setCanceled(true);
      }
   }
}
