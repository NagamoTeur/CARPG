package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.item.inv.ExtractedStack;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffectInfuse extends AbstractEffect {
   public static EffectInfuse INSTANCE = new EffectInfuse();

   public EffectInfuse() {
      super(GlyphLib.EffectInfuseID, "Infuse");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!(spellStats.getAoeMultiplier() > 0.0) && !(spellStats.getDurationMultiplier() > 0.0)) {
         super.onResolve(rayTraceResult, world, shooter, spellStats, spellContext, resolver);
      } else {
         this.spawnPotionEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver);
      }
   }

   public void spawnPotionEntity(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      PotionData potionData = this.getPotionData(world, shooter, spellContext);
      if (potionData != null) {
         Item potionItem = spellStats.getAoeMultiplier() > 0.0 ? Items.f_42736_ : Items.f_42739_;
         ThrownPotion potion = new ThrownPotion(world, shooter);
         potion.m_37446_(potionData.asPotionStack(potionItem));
         potion.m_146884_(rayTraceResult.m_82450_());
         world.m_7967_(potion);
      }
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity livingEntity) {
         PotionData potionData = this.getPotionData(world, shooter, spellContext);
         if (potionData != null) {
            potionData.applyEffects(shooter, shooter, livingEntity);
         }
      }
   }

   @Nullable
   public PotionData getPotionData(Level world, @NotNull LivingEntity shooter, SpellContext spellContext) {
      PotionData potionData = null;
      InventoryManager manager = spellContext.getCaster().getInvManager();
      ExtractedStack extractedFlask = manager.extractItem(i -> {
         PotionFlask.FlaskData data = new PotionFlask.FlaskData(i);
         return !data.getPotion().isEmpty() && data.getCount() > 0;
      }, 1);
      if (!extractedFlask.isEmpty()) {
         PotionFlask.FlaskData data = new PotionFlask.FlaskData(extractedFlask.getStack());
         potionData = data.getPotion().clone();
         data.setCount(data.getCount() - 1);
         extractedFlask.returnOrDrop(world, shooter.m_20097_());
      } else {
         ExtractedStack potion = manager.extractItem(i -> i.m_41720_() instanceof PotionItem, 1);
         if (!potion.isEmpty()) {
            ItemStack stack = potion.getStack();
            potionData = new PotionData(stack.m_41777_());
            stack.m_41774_(1);
            potion.replaceAndReturnOrDrop(new ItemStack(Items.f_42590_), world, shooter.m_20097_());
         }
      }

      if (potionData == null && spellContext.getCaster().getNearbyBlockEntity(i -> {
         if (i instanceof PotionJarTile jarx && jarx.getAmount() > 100) {
            return true;
         }

         return false;
      }) instanceof PotionJarTile jar) {
         potionData = jar.getData().clone();
         jar.remove(100);
      }

      return potionData;
   }

   @Override
   public int getDefaultManaCost() {
      return 30;
   }

   @NotNull
   @Override
   protected Set<AbstractAugment> getCompatibleAugments() {
      return this.setOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentExtendTime.INSTANCE});
   }

   @Override
   protected Map<ResourceLocation, Integer> getDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAOE.INSTANCE.getRegistryName(), 1);
      defaults.put(AugmentExtendTime.INSTANCE.getRegistryName(), 1);
      return super.getDefaultAugmentLimits(defaults);
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @Override
   public String getBookDescription() {
      return "Infuses a target with a potion or flask from your inventory. If augmented with AOE, a splash potion will spawn at the location. If augmented with Extend Time, a lingering potion will spawn at the location. Caster blocks may pull from adjacent Potion Jars to cast infuse.";
   }
}
