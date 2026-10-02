package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.perk.GlidingPerk;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Set;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectGlide extends AbstractEffect implements IPotionEffect {
   public static EffectGlide INSTANCE = new EffectGlide();

   private EffectGlide() {
      super(GlyphLib.EffectGlideID, "Glide");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity living) {
         this.applyConfigPotion(living, (MobEffect)ModPotions.GLIDE_EFFECT.get(), spellStats);
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addPotionConfig(builder, 180);
      this.addExtendTimeConfig(builder, 120);
   }

   @Override
   public int getDefaultManaCost() {
      return 100;
   }

   @Override
   public String getBookDescription() {
      return "Grants the Glide effect, allowing the wearer to fly as if they were wearing an Elytra. ";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE});
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
   }

   public static boolean canGlide(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModPotions.GLIDE_EFFECT.get())) {
         return true;
      } else {
         if (entity instanceof Player player && (double)PerkUtil.countForPerk(GlidingPerk.INSTANCE, player) > 0.0) {
            return true;
         }

         return false;
      }
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
