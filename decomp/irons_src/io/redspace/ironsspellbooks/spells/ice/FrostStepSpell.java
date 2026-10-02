package io.redspace.ironsspellbooks.spells.ice;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.network.spell.ClientboundFrostStepParticles;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.spells.ender.TeleportSpell;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class FrostStepSpell extends AbstractSpell {
   private final ResourceLocation spellId = new ResourceLocation("irons_spellbooks", "frost_step");
   private final DefaultConfig defaultConfig = new DefaultConfig()
      .setMinRarity(SpellRarity.RARE)
      .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
      .setMaxLevel(8)
      .setCooldownSeconds(10.0)
      .build();

   @Override
   public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
      return List.of(
         Component.m_237110_("ui.irons_spellbooks.distance", new Object[]{Utils.stringTruncation((double)this.getDistance(spellLevel, caster), 1)}),
         Component.m_237110_("ui.irons_spellbooks.shatter_damage", new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), 1)})
      );
   }

   public FrostStepSpell() {
      this.baseSpellPower = 14;
      this.spellPowerPerLevel = 3;
      this.baseManaCost = 15;
      this.manaCostPerLevel = 3;
      this.castTime = 0;
   }

   @Override
   public CastType getCastType() {
      return CastType.INSTANT;
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
      return Optional.empty();
   }

   @Override
   public Optional<SoundEvent> getCastFinishSound() {
      return Optional.of((SoundEvent)SoundRegistry.FROST_STEP.get());
   }

   @Override
   public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
      TeleportSpell.TeleportData teleportData = (TeleportSpell.TeleportData)playerMagicData.getAdditionalCastData();
      FrozenHumanoid shadow = new FrozenHumanoid(level, entity);
      shadow.setShatterDamage(this.getDamage(spellLevel, entity));
      shadow.setDeathTimer(60);
      level.m_7967_(shadow);
      Vec3 dest = null;
      if (teleportData != null) {
         Vec3 potentialTarget = teleportData.getTeleportTargetPosition();
         dest = potentialTarget;
      }

      if (dest == null) {
         dest = this.findTeleportLocation(spellLevel, level, entity);
      }

      Messages.sendToPlayersTrackingEntity(new ClientboundFrostStepParticles(entity.m_20182_(), dest), entity, true);
      if (entity.m_20159_()) {
         entity.m_8127_();
      }

      entity.m_6021_(dest.f_82479_, dest.f_82480_, dest.f_82481_);
      entity.m_183634_();
      level.m_6263_(null, dest.f_82479_, dest.f_82480_, dest.f_82481_, this.getCastFinishSound().get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
      playerMagicData.resetAdditionalCastData();
      super.onCast(level, spellLevel, entity, castSource, playerMagicData);
   }

   private Vec3 findTeleportLocation(int spellLevel, Level level, LivingEntity entity) {
      return TeleportSpell.findTeleportLocation(level, entity, this.getDistance(spellLevel, entity));
   }

   public static void particleCloud(Level level, Vec3 pos) {
      if (level.f_46443_) {
         double width = 0.5;
         float height = 1.0F;

         for (int i = 0; i < 25; i++) {
            double x = pos.f_82479_ + Utils.random.m_188500_() * width * 2.0 - width;
            double y = pos.f_82480_ + (double)height + Utils.random.m_188500_() * (double)height * 1.2 * 2.0 - (double)height * 1.2;
            double z = pos.f_82481_ + Utils.random.m_188500_() * width * 2.0 - width;
            double dx = Utils.random.m_188500_() * 0.1 * (double)(Utils.random.m_188499_() ? 1 : -1);
            double dy = Utils.random.m_188500_() * 0.1 * (double)(Utils.random.m_188499_() ? 1 : -1);
            double dz = Utils.random.m_188500_() * 0.1 * (double)(Utils.random.m_188499_() ? 1 : -1);
            level.m_6493_(ParticleHelper.SNOWFLAKE, true, x, y, z, dx, dy, dz);
            level.m_6493_(ParticleTypes.f_175821_, true, x, y, z, -dx, -dy, -dz);
         }
      }
   }

   private float getDistance(int spellLevel, LivingEntity sourceEntity) {
      return this.getSpellPower(spellLevel, sourceEntity) * 0.65F;
   }

   private float getDamage(int spellLevel, LivingEntity caster) {
      return this.getSpellPower(spellLevel, caster) / 3.0F;
   }

   @Override
   public AnimationHolder getCastStartAnimation() {
      return AnimationHolder.none();
   }
}
