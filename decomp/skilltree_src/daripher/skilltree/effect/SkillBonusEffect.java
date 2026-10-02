package daripher.skilltree.effect;

import daripher.skilltree.skill.bonus.SkillBonus;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

public abstract class SkillBonusEffect extends MobEffect {
   private final SkillBonus<?> bonus;

   public SkillBonusEffect(MobEffectCategory category, int color, SkillBonus<?> bonus) {
      super(category, color);
      this.bonus = bonus;
   }

   public void m_6386_(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      if (entity instanceof ServerPlayer player) {
         this.bonus.onSkillRemoved(player);
      }
   }

   public void m_6385_(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
      super.m_6385_(entity, attributeMap, amplifier);
      if (entity instanceof ServerPlayer player) {
         this.bonus.onSkillLearned(player, true);
      }
   }

   public boolean m_6584_(int duration, int amplifier) {
      return this.bonus instanceof SkillBonus.Ticking;
   }

   public void m_6742_(@NotNull LivingEntity entity, int amplifier) {
      if (entity instanceof ServerPlayer player && this.bonus instanceof SkillBonus.Ticking ticking) {
         ticking.tick(player);
      }
   }

   public SkillBonus<?> getBonus() {
      return this.bonus;
   }
}
