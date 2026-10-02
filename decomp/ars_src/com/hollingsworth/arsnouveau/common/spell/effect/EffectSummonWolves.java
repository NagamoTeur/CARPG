package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.SummonWolf;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSummonWolves extends AbstractEffect {
   public static EffectSummonWolves INSTANCE = new EffectSummonWolves();

   private EffectSummonWolves() {
      super(GlyphLib.EffectSummonWolvesID, "Summon Wolves");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (this.canSummon(shooter)) {
         Vec3 hit = rayTraceResult.m_82450_();
         int ticks = (int)(
            20.0
               * (
                  (double)((Integer)this.GENERIC_INT.get()).intValue()
                     + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier()
               )
         );
         if (ticks > 0) {
            for (int i = 0; i < 2; i++) {
               SummonWolf wolf = new SummonWolf((EntityType<? extends Wolf>)ModEntities.SUMMON_WOLF.get(), world);
               wolf.ticksLeft = ticks;
               wolf.m_6034_(hit.m_7096_(), hit.m_7098_(), hit.m_7094_());
               wolf.m_6710_(shooter.m_21214_());
               wolf.m_21561_(true);
               wolf.m_7105_(true);
               wolf.m_21828_((Player)shooter);
               this.summonLivingEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver, wolf);
            }

            this.applySummoningSickness(shooter, ticks);
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericInt(builder, 60, "Base duration in seconds", "duration");
      this.addExtendTimeConfig(builder, 60);
   }

   @Override
   public int getDefaultManaCost() {
      return 100;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.getSummonAugments();
   }

   @Override
   public String getBookDescription() {
      return "Summons two wolves that will fight with you. Extend Time will increase the amount of time on the summons. Applies Summoning Sickness to the caster, preventing other summoning magic.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.ONE;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
