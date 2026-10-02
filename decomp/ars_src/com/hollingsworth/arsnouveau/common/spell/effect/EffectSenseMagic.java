package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.block.IPedestalMachine;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Set;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSenseMagic extends AbstractEffect implements IPotionEffect {
   public static EffectSenseMagic INSTANCE = new EffectSenseMagic(GlyphLib.EffectSenseMagicID, "Sense Magic");

   public EffectSenseMagic(String tag, String description) {
      super(tag, description);
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity living) {
         this.applyConfigPotion(living, (MobEffect)ModPotions.MAGIC_FIND_EFFECT.get(), spellStats);
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (world.m_7702_(rayTraceResult.m_82425_()) instanceof IPedestalMachine toHighlight) {
         toHighlight.lightPedestal(world);
      }
   }

   @Override
   public String getBookDescription() {
      return "Applies Magic Find to the target, causing magical mobs to glow within 75 blocks of them. Magic Find also reveals spells on Runes.";
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addPotionConfig(builder, 60);
      this.addExtendTimeConfig(builder, 15);
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.setOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE});
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ABJURATION});
   }

   @Override
   public int getBaseDuration() {
      return this.POTION_TIME == null ? 30 : (Integer)this.POTION_TIME.get();
   }

   @Override
   public int getExtendTimeDuration() {
      return this.EXTEND_TIME == null ? 8 : (Integer)this.EXTEND_TIME.get();
   }
}
