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
import com.hollingsworth.arsnouveau.api.util.ANExplosion;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class EffectExplosion extends AbstractEffect implements IDamageEffect {
   public static EffectExplosion INSTANCE = new EffectExplosion();
   public DoubleValue BASE;
   public DoubleValue AOE_BONUS;
   public DoubleValue AMP_DAMAGE;

   private EffectExplosion() {
      super(GlyphLib.EffectExplosionID, "Explosion");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Vec3 vec = this.safelyGetHitPos(rayTraceResult);
      double intensity = (Double)this.BASE.get()
         + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier()
         + (Double)this.AOE_BONUS.get() * spellStats.getAoeMultiplier();
      int dampen = spellStats.getBuffCount(AugmentDampen.INSTANCE);
      intensity -= 0.5 * (double)dampen;
      BlockInteraction mode = dampen > 0 ? BlockInteraction.NONE : BlockInteraction.DESTROY;
      mode = spellStats.hasBuff(AugmentExtract.INSTANCE) ? BlockInteraction.BREAK : mode;
      this.explode(world, shooter, null, null, vec.f_82479_, vec.f_82480_, vec.f_82481_, (float)intensity, false, mode, spellStats.getAmpMultiplier());
   }

   public Explosion explode(
      Level world,
      @Nullable Entity e,
      @Nullable DamageSource source,
      @Nullable ExplosionDamageCalculator context,
      double x,
      double y,
      double z,
      float radius,
      boolean p_230546_11_,
      BlockInteraction p_230546_12_,
      double amp
   ) {
      ANExplosion explosion = new ANExplosion(world, e, source, context, x, y, z, radius, p_230546_11_, p_230546_12_, amp);
      explosion.baseDamage = (Double)this.DAMAGE.get();
      explosion.ampDamageScalar = (Double)this.AMP_DAMAGE.get();
      if (ForgeEventFactory.onExplosionStart(world, explosion)) {
         return explosion;
      } else {
         explosion.m_46061_();
         explosion.m_46075_(false);
         if (p_230546_12_ == BlockInteraction.NONE) {
            explosion.m_46080_();
         }

         for (Player serverplayerentity : world.m_6907_()) {
            if (serverplayerentity.m_20275_(x, y, z) < 4096.0) {
               ((ServerPlayer)serverplayerentity)
                  .f_8906_
                  .m_9829_(new ClientboundExplodePacket(x, y, z, radius, explosion.m_46081_(), (Vec3)explosion.m_46078_().get(serverplayerentity)));
            }
         }

         return explosion;
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addAmpConfig(builder, 0.5);
      this.BASE = builder.comment("Explosion base intensity").defineInRange("base", 0.75, 0.0, 100.0);
      this.AOE_BONUS = builder.comment("AOE intensity bonus").defineInRange("aoe_bonus", 1.5, 0.0, 100.0);
      this.addDamageConfig(builder, 6.0);
      this.AMP_DAMAGE = builder.comment("Additional damage per amplify").defineInRange("amp_damage", 2.5, 0.0, 2.147483647E9);
   }

   @Override
   public int getDefaultManaCost() {
      return 200;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentAOE.INSTANCE, AugmentExtract.INSTANCE});
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   @Override
   public String getBookDescription() {
      return "Causes an explosion at the location. Amplify increases the damage and size by a small amount, while AOE will increase the size of the explosion by a large amount, but not damage.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_FIRE});
   }
}
