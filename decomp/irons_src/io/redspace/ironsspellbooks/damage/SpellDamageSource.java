package io.redspace.ironsspellbooks.damage;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SpellDamageSource extends EntityDamageSource implements ISpellDamageSource {
   AbstractSpell spell;
   float lifesteal;
   int freezeTicks;
   int fireTime;

   public SpellDamageSource(@NotNull Entity causingEntity, AbstractSpell spell) {
      super(spell.getDeathMessageId(), causingEntity);
      this.spell = spell;
   }

   @NotNull
   public Component m_6157_(@NotNull LivingEntity pLivingEntity) {
      String s = "death.attack." + this.spell.getDeathMessageId();
      Component component = this.f_19391_.m_5446_();
      return Component.m_237110_(s, new Object[]{pLivingEntity.m_5446_(), component});
   }

   public SpellDamageSource setLifestealPercent(float lifesteal) {
      this.lifesteal = lifesteal;
      return this;
   }

   public SpellDamageSource setFireTime(int fireTime) {
      this.fireTime = fireTime;
      return this;
   }

   public SpellDamageSource setFreezeTicks(int freezeTicks) {
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
