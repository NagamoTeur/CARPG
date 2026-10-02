package io.redspace.ironsspellbooks.damage;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class IndirectSpellDamageSource extends IndirectEntityDamageSource implements ISpellDamageSource {
   AbstractSpell spell;
   float lifesteal;
   int freezeTicks;
   int fireTime;

   public IndirectSpellDamageSource(@NotNull Entity directEntity, @NotNull Entity causingEntity, AbstractSpell spell) {
      super(spell.getDeathMessageId(), directEntity, causingEntity);
      this.spell = spell;
   }

   @NotNull
   public Component m_6157_(@NotNull LivingEntity pLivingEntity) {
      String s = "death.attack." + this.spell.getDeathMessageId();
      Component component = this.f_19391_ != null ? this.m_7639_().m_5446_() : this.m_7640_().m_5446_();
      return Component.m_237110_(s, new Object[]{pLivingEntity.m_5446_(), component});
   }

   public IndirectSpellDamageSource setLifestealPercent(float lifesteal) {
      this.lifesteal = lifesteal;
      return this;
   }

   public IndirectSpellDamageSource setFireTime(int fireTime) {
      this.fireTime = fireTime;
      return this;
   }

   public IndirectSpellDamageSource setFreezeTicks(int freezeTicks) {
      this.freezeTicks = freezeTicks;
      return this;
   }

   @Override
   public DamageSource get() {
      return this;
   }

   @Override
   public AbstractSpell spell() {
      return this.spell;
   }

   @Override
   public float getLifestealPercent() {
      return this.lifesteal;
   }

   @Override
   public int getFireTime() {
      return this.fireTime;
   }

   @Override
   public int getFreezeTicks() {
      return this.freezeTicks;
   }
}
