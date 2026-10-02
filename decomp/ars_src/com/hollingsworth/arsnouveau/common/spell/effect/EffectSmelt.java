package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EffectSmelt extends AbstractEffect {
   public static EffectSmelt INSTANCE = new EffectSmelt();

   private EffectSmelt() {
      super(GlyphLib.EffectSmeltID, "Smelt");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      double aoeBuff = spellStats.getAoeMultiplier();
      int pierceBuff = spellStats.getBuffCount(AugmentPierce.INSTANCE);
      int maxItemSmelt = (int)Math.round(4.0 * (1.0 + aoeBuff + (double)pierceBuff));
      List<ItemEntity> itemEntities = world.m_45976_(ItemEntity.class, new AABB(rayTraceResult.m_82443_().m_20183_()).m_82400_(aoeBuff + 1.0));
      this.smeltItems(world, itemEntities, maxItemSmelt, spellStats);
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      double aoeBuff = spellStats.getAoeMultiplier();
      int pierceBuff = spellStats.getBuffCount(AugmentPierce.INSTANCE);
      int maxItemSmelt = (int)Math.round(4.0 * (1.0 + aoeBuff + (double)pierceBuff));
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats);
      List<ItemEntity> itemEntities = world.m_45976_(ItemEntity.class, new AABB(rayTraceResult.m_82425_()).m_82400_(aoeBuff + 1.0));
      this.smeltItems(world, itemEntities, maxItemSmelt, spellStats);

      for (BlockPos pos : posList) {
         this.smeltBlock(world, pos, shooter, rayTraceResult, spellStats, spellContext, resolver);
      }
   }

   public void smeltBlock(
      Level world, BlockPos pos, LivingEntity shooter, BlockHitResult hitResult, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (this.canBlockBeHarvested(spellStats, world, pos)) {
         BlockState state = world.m_8055_(pos);
         if (BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos)) {
            Optional<SmeltingRecipe> optional = world.m_7465_()
               .m_44015_(RecipeType.f_44108_, new SimpleContainer(new ItemStack[]{new ItemStack(state.m_60734_().m_5456_(), 1)}), world);
            if (optional.isPresent()) {
               ItemStack itemstack = optional.get().m_8043_();
               if (!itemstack.m_41619_()) {
                  if (itemstack.m_41720_() instanceof BlockItem) {
                     world.m_46597_(pos, ((BlockItem)itemstack.m_41720_()).m_40614_().m_49966_());
                  } else {
                     BlockUtil.destroyBlockSafely(world, pos, false, shooter);
                     world.m_7967_(new ItemEntity(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), itemstack.m_41777_()));
                     BlockUtil.safelyUpdateState(world, pos);
                  }

                  ShapersFocus.tryPropagateBlockSpell(
                     new BlockHitResult(new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), hitResult.m_82434_(), pos, false),
                     world,
                     shooter,
                     spellContext,
                     resolver
                  );
               }
            }
         }
      }
   }

   public void smeltItems(Level world, List<ItemEntity> itemEntities, int maxItemSmelt, SpellStats spellStats) {
      int numSmelted = 0;

      for (ItemEntity itemEntity : itemEntities) {
         if (numSmelted > maxItemSmelt) {
            break;
         }

         Optional<? extends AbstractCookingRecipe> optional;
         if (spellStats.hasBuff(AugmentDampen.INSTANCE)) {
            optional = world.m_7465_().m_44015_(RecipeType.f_44110_, new SimpleContainer(new ItemStack[]{itemEntity.m_32055_()}), world);
         } else if (spellStats.hasBuff(AugmentAmplify.INSTANCE)) {
            optional = world.m_7465_().m_44015_(RecipeType.f_44109_, new SimpleContainer(new ItemStack[]{itemEntity.m_32055_()}), world);
         } else {
            optional = world.m_7465_().m_44015_(RecipeType.f_44108_, new SimpleContainer(new ItemStack[]{itemEntity.m_32055_()}), world);
         }

         if (optional.isPresent()) {
            ItemStack result = optional.get().m_8043_().m_41777_();
            if (!result.m_41619_()) {
               while (numSmelted < maxItemSmelt && !itemEntity.m_32055_().m_41619_()) {
                  itemEntity.m_32055_().m_41774_(1);
                  world.m_7967_(new ItemEntity(world, itemEntity.m_20185_(), itemEntity.m_20186_(), itemEntity.m_20189_(), result.m_41777_()));
                  numSmelted++;
               }
            }
         }
      }
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentAOE.INSTANCE, AugmentPierce.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Smelts blocks and items in the world. AOE will increase the number of items and radius of blocks that can be smelted at once, while Amplify will allow Smelt to work on blocks of higher hardness.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @Override
   public int getDefaultManaCost() {
      return 100;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_FIRE});
   }
}
