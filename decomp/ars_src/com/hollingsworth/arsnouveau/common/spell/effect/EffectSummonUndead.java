package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.SummonSkeleton;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSummonUndead extends AbstractEffect {
   public static EffectSummonUndead INSTANCE = new EffectSummonUndead();

   private EffectSummonUndead() {
      super(GlyphLib.EffectSummonUndeadID, "Summon Undead");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (this.canSummon(shooter)) {
         Vec3 vector3d = this.safelyGetHitPos(rayTraceResult);
         int ticks = (int)(
            20.0
               * (
                  (double)((Integer)this.GENERIC_INT.get()).intValue()
                     + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier()
               )
         );
         BlockPos pos = new BlockPos(vector3d);
         if (ticks > 0) {
            int count = 3 + spellStats.getBuffCount(AugmentSplit.INSTANCE);

            for (int i = 0; i < count; i++) {
               BlockPos blockpos = pos.m_7918_(-2 + shooter.m_217043_().m_188503_(5), 2, -2 + shooter.m_217043_().m_188503_(5));
               ItemStack weapon = Items.f_42383_.m_7968_();
               if (spellStats.hasBuff(AugmentPierce.INSTANCE)) {
                  weapon = Items.f_42411_.m_7968_();
                  if (spellStats.getAmpMultiplier() > 0.0) {
                     weapon.m_41663_(Enchantments.f_44988_, Math.max(4, (int)spellStats.getAmpMultiplier()) - 1);
                  }
               } else if (spellStats.getAmpMultiplier() >= 3.0) {
                  weapon = Items.f_42396_.m_7968_();
               } else if (spellStats.getAmpMultiplier() > 2.0) {
                  weapon = Items.f_42393_.m_7968_();
               } else if (spellStats.getAmpMultiplier() > 1.0) {
                  weapon = Items.f_42388_.m_7968_();
               }

               SummonSkeleton undeadentity = new SummonSkeleton(world, shooter, weapon);
               undeadentity.m_20035_(blockpos, 0.0F, 0.0F);
               undeadentity.m_6518_((ServerLevelAccessor)world, world.m_6436_(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
               undeadentity.setOwner(shooter);
               undeadentity.setLimitedLife(ticks);
               this.summonLivingEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver, undeadentity);
            }

            shooter.m_7292_(new MobEffectInstance((MobEffect)ModPotions.SUMMONING_SICKNESS_EFFECT.get(), ticks));
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericInt(builder, 15, "Base duration in seconds", "duration");
      this.addExtendTimeConfig(builder, 10);
   }

   @Override
   public int getDefaultManaCost() {
      return 150;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.setOf(
         new AbstractAugment[]{
            AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentAmplify.INSTANCE, AugmentSplit.INSTANCE, AugmentPierce.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Summons a number of Skeleton allies that will attack nearby hostile enemies. These Skeletons will last a short time until they begin to take damage, but time may be extended with the Extend Time augment.  Additionally, their summoned weapons are changed using augments, use Amplify to give it a better sword, or Pierce to give it a bow.  Adding Split after the effect will add to the number of summoned skeletons.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
