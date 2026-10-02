package com.hollingsworth.arsnouveau.api.spell;

import java.util.HashMap;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public abstract class AbstractCastMethod extends AbstractSpellPart {
   public AbstractCastMethod(String tag, String description) {
      super(tag, description);
   }

   public AbstractCastMethod(ResourceLocation tag, String description) {
      super(tag, description);
   }

   @Override
   public Integer getTypeIndex() {
      return 1;
   }

   public abstract CastResolveType onCast(@Nullable ItemStack var1, LivingEntity var2, Level var3, SpellStats var4, SpellContext var5, SpellResolver var6);

   public abstract CastResolveType onCastOnBlock(UseOnContext var1, SpellStats var2, SpellContext var3, SpellResolver var4);

   public abstract CastResolveType onCastOnBlock(BlockHitResult var1, LivingEntity var2, SpellStats var3, SpellContext var4, SpellResolver var5);

   public abstract CastResolveType onCastOnEntity(
      @Nullable ItemStack var1, LivingEntity var2, Entity var3, InteractionHand var4, SpellStats var5, SpellContext var6, SpellResolver var7
   );

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      super.buildAugmentLimitsConfig(builder, this.getDefaultAugmentLimits(new HashMap<>()));
   }
}
