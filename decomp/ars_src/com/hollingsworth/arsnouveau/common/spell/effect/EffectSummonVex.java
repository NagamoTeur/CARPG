package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.EntityAllyVex;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSummonVex extends AbstractEffect {
   public static EffectSummonVex INSTANCE = new EffectSummonVex();

   private EffectSummonVex() {
      super(GlyphLib.EffectSummonVexID, "Summon Vex");
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
            for (int i = 0; i < 3; i++) {
               BlockPos blockpos = pos.m_7918_(-2 + shooter.m_217043_().m_188503_(5), 2, -2 + shooter.m_217043_().m_188503_(5));
               EntityAllyVex vexentity = new EntityAllyVex(world, shooter);
               vexentity.m_20035_(blockpos, 0.0F, 0.0F);
               vexentity.m_6518_((ServerLevelAccessor)world, world.m_6436_(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
               vexentity.setOwner(shooter);
               vexentity.m_34033_(blockpos);
               vexentity.m_33987_(ticks);
               this.summonLivingEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver, vexentity);
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
      return this.getSummonAugments();
   }

   @Override
   public String getBookDescription() {
      return "Summons three Vex allies that will attack nearby hostile enemies. These Vex will last a short time until they begin to take damage, but time may be extended with the Extend Time augment.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
