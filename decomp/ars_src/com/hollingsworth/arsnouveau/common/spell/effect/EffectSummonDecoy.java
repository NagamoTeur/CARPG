package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.EntityDummy;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSummonDecoy extends AbstractEffect {
   public static EffectSummonDecoy INSTANCE = new EffectSummonDecoy();

   private EffectSummonDecoy() {
      super(GlyphLib.EffectDecoyID, "Summon Decoy");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (this.canSummon(shooter)) {
         Vec3 pos = this.safelyGetHitPos(rayTraceResult);
         EntityDummy dummy = new EntityDummy(world);
         dummy.ticksLeft = (int)(
            20.0
               * (
                  (double)((Integer)this.GENERIC_INT.get()).intValue()
                     + spellStats.getDurationMultiplier() * (double)((Integer)this.EXTEND_TIME.get()).intValue()
               )
         );
         dummy.m_6034_(pos.f_82479_, pos.f_82480_ + 1.0, pos.f_82481_);
         dummy.setOwnerID(shooter.m_20148_());
         this.summonLivingEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver, dummy);
         world.m_45976_(Mob.class, dummy.m_20191_().m_82377_(20.0, 10.0, 20.0)).forEach(l -> l.m_6710_(dummy));
         this.applySummoningSickness(shooter, 1);
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addExtendTimeConfig(builder, 15);
      this.addGenericInt(builder, 30, "Base duration in seconds", "duration");
   }

   @Override
   public int getDefaultManaCost() {
      return 200;
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
      return "Summons a decoy of yourself. Upon summoning, the decoy will attract any nearby mobs to attack it.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
