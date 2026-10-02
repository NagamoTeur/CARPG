package io.redspace.ironsspellbooks.spells.evocation;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.SummonedVex;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class SummonVexSpell extends AbstractSpell {
   private final ResourceLocation spellId = new ResourceLocation("irons_spellbooks", "summon_vex");
   private final DefaultConfig defaultConfig = new DefaultConfig()
      .setMinRarity(SpellRarity.RARE)
      .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
      .setMaxLevel(5)
      .setCooldownSeconds(150.0)
      .build();

   @Override
   public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
      return List.of(Component.m_237110_("ui.irons_spellbooks.summon_count", new Object[]{spellLevel}));
   }

   public SummonVexSpell() {
      this.manaCostPerLevel = 10;
      this.baseSpellPower = 1;
      this.spellPowerPerLevel = 0;
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
      return Optional.of(SoundEvents.f_11868_);
   }

   @Override
   public Optional<SoundEvent> getCastFinishSound() {
      return Optional.of(SoundEvents.f_11862_);
   }

   @Override
   public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
      int summonTime = 12000;

      for (int i = 0; i < spellLevel; i++) {
         SummonedVex vex = new SummonedVex(world, entity);
         vex.m_20219_(entity.m_146892_().m_82549_(new Vec3(Utils.getRandomScaled(2.0), 1.0, Utils.getRandomScaled(2.0))));
         vex.m_6518_((ServerLevel)world, world.m_6436_(vex.m_20097_()), MobSpawnType.MOB_SUMMONED, null, null);
         vex.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.VEX_TIMER.get(), summonTime, 0, false, false, false));
         world.m_7967_(vex);
      }

      int effectAmplifier = spellLevel - 1;
      if (entity.m_21023_((MobEffect)MobEffectRegistry.VEX_TIMER.get())) {
         effectAmplifier += entity.m_21124_((MobEffect)MobEffectRegistry.VEX_TIMER.get()).m_19564_() + 1;
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.VEX_TIMER.get(), summonTime, effectAmplifier, false, false, true));
      super.onCast(world, spellLevel, entity, castSource, playerMagicData);
   }
}
