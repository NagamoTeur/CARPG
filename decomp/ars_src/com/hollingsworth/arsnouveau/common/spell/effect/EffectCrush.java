package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.crafting.recipes.CrushRecipe;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectCrush extends AbstractEffect implements IDamageEffect {
   public static EffectCrush INSTANCE = new EffectCrush();

   private EffectCrush() {
      super(GlyphLib.EffectCrushID, "Crush");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (spellStats.isSensitive()) {
         double aoeBuff = spellStats.getAoeMultiplier();
         int pierceBuff = spellStats.getBuffCount(AugmentPierce.INSTANCE);
         int maxItemCrush = (int)(4.0 + 4.0 * aoeBuff + (double)(4 * pierceBuff));
         List<ItemEntity> itemEntities = world.m_45976_(ItemEntity.class, new AABB(new BlockPos(rayTraceResult.m_82450_())).m_82400_(aoeBuff + 1.0));
         if (!itemEntities.isEmpty()) {
            crushItems(world, itemEntities, maxItemCrush);
         }
      } else {
         super.onResolve(rayTraceResult, world, shooter, spellStats, spellContext, resolver);
      }
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      float damage = (float)(
         (rayTraceResult.m_82443_().m_6069_() ? (Double)this.DAMAGE.get() * 3.0 : (Double)this.DAMAGE.get())
            + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier()
      );
      this.attemptDamage(world, shooter, spellStats, spellContext, resolver, rayTraceResult.m_82443_(), DamageSource.f_19311_, damage);
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      List<CrushRecipe> recipes = world.m_7465_().m_44013_((RecipeType)RecipeRegistry.CRUSH_TYPE.get());
      CrushRecipe lastHit = null;

      for (BlockPos p : SpellUtil.calcAOEBlocks(
         shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats.getAoeMultiplier(), spellStats.getBuffCount(AugmentPierce.INSTANCE)
      )) {
         BlockState state = world.m_8055_(p);
         Item item = state.m_60734_().m_5456_();
         if (lastHit == null || !lastHit.matches(item.m_7968_(), world)) {
            lastHit = null;

            for (CrushRecipe recipe : recipes) {
               if (recipe.matches(item.m_7968_(), world)) {
                  lastHit = recipe;
                  break;
               }
            }
         }

         if (lastHit != null) {
            List<ItemStack> outputs = lastHit.getRolledOutputs(world.f_46441_);
            boolean placedBlock = false;

            for (ItemStack i : outputs) {
               if (!placedBlock && i.m_41720_() instanceof BlockItem blockItem && !lastHit.shouldSkipBlockPlace()) {
                  world.m_46597_(p, blockItem.m_40614_().m_49966_());
                  placedBlock = true;
                  i.m_41774_(1);
                  ShapersFocus.tryPropagateBlockSpell(
                     new BlockHitResult(new Vec3((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_()), rayTraceResult.m_82434_(), p, false),
                     world,
                     shooter,
                     spellContext,
                     resolver
                  );
               }

               if (!i.m_41619_()) {
                  world.m_7967_(new ItemEntity(world, (double)p.m_123341_() + 0.5, (double)p.m_123342_(), (double)p.m_123343_() + 0.5, i));
               }
            }

            if (!placedBlock) {
               world.m_46597_(p, Blocks.f_50016_.m_49966_());
               ShapersFocus.tryPropagateBlockSpell(
                  new BlockHitResult(new Vec3((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_()), rayTraceResult.m_82434_(), p, false),
                  world,
                  shooter,
                  spellContext,
                  resolver
               );
            }
         }
      }
   }

   public static void crushItems(Level world, List<ItemEntity> itemEntities, int maxItemCrush) {
      List<CrushRecipe> recipes = world.m_7465_().m_44013_((RecipeType)RecipeRegistry.CRUSH_TYPE.get());
      CrushRecipe lastHit = null;
      int itemsCrushed = 0;

      for (ItemEntity IE : itemEntities) {
         if (itemsCrushed > maxItemCrush) {
            break;
         }

         ItemStack stack = IE.m_32055_();
         Item item = stack.m_41720_();
         if (lastHit == null || !lastHit.matches(item.m_7968_(), world)) {
            lastHit = recipes.stream().filter(recipe -> recipe.matches(item.m_7968_(), world)).findFirst().orElse(null);
         }

         if (lastHit != null) {
            while (!stack.m_41619_() && itemsCrushed <= maxItemCrush) {
               List<ItemStack> outputs = lastHit.getRolledOutputs(world.f_46441_);
               stack.m_41774_(1);
               itemsCrushed++;

               for (ItemStack result : outputs) {
                  world.m_7967_(new ItemEntity(world, IE.m_20185_(), IE.m_20186_(), IE.m_20189_(), result.m_41777_()));
               }
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 3.0);
      this.addAmpConfig(builder, 1.0);
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentFortune.INSTANCE, AugmentSensitive.INSTANCE
         }
      );
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   @Override
   public String getBookDescription() {
      return "Turns stone into gravel, and gravel into sand. Will also crush flowers into bonus dye. Need Sensitive to work on items. For full recipe support, see JEI. Will also harm entities and deals bonus damage to entities that are swimming.";
   }

   @Override
   public int getDefaultManaCost() {
      return 30;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_EARTH});
   }
}
