package io.redspace.ironsspellbooks.spells.evocation;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.entity.mobs.SummonedHorse;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class SummonHorseSpell extends AbstractSpell {
   private final ResourceLocation spellId = new ResourceLocation("irons_spellbooks", "summon_horse");
   private final DefaultConfig defaultConfig = new DefaultConfig()
      .setMinRarity(SpellRarity.COMMON)
      .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
      .setMaxLevel(5)
      .setCooldownSeconds(20.0)
      .build();

   public SummonHorseSpell() {
      this.manaCostPerLevel = 2;
      this.baseSpellPower = 2;
      this.spellPowerPerLevel = 1;
      this.castTime = 20;
      this.baseManaCost = 50;
   }

   @Override
   public CastType getCastType() {
      return CastType.LONG;
   }

   @Override
   public DefaultConfig getDefaultConfig() {
      return this.defaultConfig;
   }

   @Override
   public ResourceLocation getSpellResource() {
      return this.spellId;
   }

   @Override
   public Optional<SoundEvent> getCastStartSound() {
      return Optional.of(SoundEvents.f_12054_);
   }

   @Override
   public Optional<SoundEvent> getCastFinishSound() {
      return Optional.of(SoundEvents.f_12052_);
   }

   @Override
   public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
      int summonTime = 12000;
      Vec3 spawn = entity.m_20182_();
      Vec3 forward = entity.m_20156_().m_82541_().m_82490_(1.5);
      spawn.m_82520_(forward.f_82479_, 0.15F, forward.f_82481_);
      List<SummonedHorse> horses = world.m_6443_(
         SummonedHorse.class, entity.m_20191_().m_82400_(100.0), summonedHorse -> summonedHorse.getSummoner() == entity && !summonedHorse.m_21224_()
      );
      SummonedHorse horse = horses.size() > 0 ? horses.get(0) : new SummonedHorse(world, entity);
      horse.m_146884_(spawn);
      horse.m_6234_((MobEffect)MobEffectRegistry.SUMMON_HORSE_TIMER.get());
      horse.m_147215_(new MobEffectInstance((MobEffect)MobEffectRegistry.SUMMON_HORSE_TIMER.get(), summonTime, 0, false, false, false), null);
      this.setAttributes(horse, this.getSpellPower(spellLevel, entity));
      world.m_7967_(horse);
      entity.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.SUMMON_HORSE_TIMER.get(), summonTime, 0, false, false, true));
      super.onCast(world, spellLevel, entity, castSource, playerMagicData);
   }

   private void setAttributes(AbstractHorse horse, float power) {
      int maxPower = this.baseSpellPower + (ServerConfigs.getSpellConfig(this).maxLevel() - 1) * this.spellPowerPerLevel;
      float quality = power / (float)maxPower;
      float minSpeed = 0.2F;
      float maxSpeed = 0.45F;
      float minJump = 0.6F;
      float maxJump = 1.0F;
      float minHealth = 10.0F;
      float maxHealth = 40.0F;
      horse.m_21051_(Attributes.f_22279_).m_22100_((double)Mth.m_14179_(quality, minSpeed, maxSpeed));
      horse.m_21051_(Attributes.f_22288_).m_22100_((double)Mth.m_14179_(quality, minJump, maxJump));
      horse.m_21051_(Attributes.f_22276_).m_22100_((double)Mth.m_14179_(quality, minHealth, maxHealth));
      if (!horse.m_21224_()) {
         horse.m_21153_(horse.m_21233_());
      }
   }
}
