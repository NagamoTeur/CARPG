package shadows.apotheosis.core.mobfx.potions;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.phys.AABB;

public class FlamingDetonationEffect extends MobEffect {
   public static final DamageSource DETONATION = new DamageSource("apotheosis.detonation").m_19389_().m_19380_();

   public FlamingDetonationEffect() {
      super(MobEffectCategory.HARMFUL, 16766976);
   }

   public void m_6386_(LivingEntity entity, AttributeMap map, int amp) {
      super.m_6386_(entity, map, amp);
      int ticks = entity.m_20094_();
      if (ticks > 0) {
         entity.m_7311_(0);
         entity.m_6469_(DETONATION, (float)((1 + amp) * ticks) / 14.0F);
         ServerLevel level = (ServerLevel)entity.f_19853_;
         AABB bb = entity.m_20191_();
         level.m_8767_(ParticleTypes.f_123744_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 100, bb.m_82362_(), bb.m_82376_(), bb.m_82385_(), 0.25);
         level.m_6269_(null, entity, SoundEvents.f_11892_, SoundSource.HOSTILE, 1.0F, 1.2F);
      }
   }
}
