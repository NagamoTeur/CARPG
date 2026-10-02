package io.redspace.ironsspellbooks.spells.lightning;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.ZapParticleOption;
import io.redspace.ironsspellbooks.spells.ender.TeleportSpell;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@AutoSpellConfig
public class ThunderStepSpell extends AbstractSpell {
   private final ResourceLocation spellId = new ResourceLocation("irons_spellbooks", "thunder_step");
   private final DefaultConfig defaultConfig = new DefaultConfig()
      .setMinRarity(SpellRarity.UNCOMMON)
      .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
      .setMaxLevel(5)
      .setCooldownSeconds(8.0)
      .build();

   @Override
   public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
      return List.of(Component.m_237110_("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)this.getSpellPower(spellLevel, caster), 1)}));
   }

   public ThunderStepSpell() {
      this.manaCostPerLevel = 15;
      this.baseSpellPower = 10;
      this.spellPowerPerLevel = 2;
      this.castTime = 0;
      this.baseManaCost = 75;
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
      return Optional.of(SoundEvents.f_12053_);
   }

   @Override
   public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
      TeleportSpell.TeleportData teleportData = (TeleportSpell.TeleportData)playerMagicData.getAdditionalCastData();
      Vec3 dest = null;
      if (teleportData != null) {
         Vec3 potentialTarget = teleportData.getTeleportTargetPosition();
         if (potentialTarget != null) {
            dest = potentialTarget;
         }
      }

      if (dest == null) {
         dest = TeleportSpell.findTeleportLocation(level, entity, this.getDistance(spellLevel, entity));
      }

      this.zapEntitiesBetween(entity, spellLevel, dest);
      Vec3 travel = dest.m_82546_(entity.m_20182_());

      for (int i = 0; i < 7; i++) {
         Vec3 random1 = Utils.getRandomVec3(0.5).m_82542_((double)entity.m_20205_(), (double)entity.m_20206_(), (double)entity.m_20205_());
         Vec3 random2 = Utils.getRandomVec3(0.8F).m_82542_((double)entity.m_20205_(), (double)entity.m_20206_(), (double)entity.m_20205_());
         float yOffset = (float)i / 7.0F * entity.m_20206_();
         Vec3 midpoint = entity.m_20182_().m_82549_(travel.m_82490_(0.5)).m_82549_(random2);
         ((ServerLevel)level)
            .m_8767_(
               new ZapParticleOption(random1.m_82520_(entity.m_20185_(), entity.m_20186_() + (double)yOffset, entity.m_20189_())),
               midpoint.f_82479_,
               midpoint.f_82480_,
               midpoint.f_82481_,
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         ((ServerLevel)level)
            .m_8767_(
               new ZapParticleOption(random1.m_82490_(-1.0).m_82520_(dest.f_82479_, dest.f_82480_ + (double)yOffset, dest.f_82481_)),
               midpoint.f_82479_,
               midpoint.f_82480_,
               midpoint.f_82481_,
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
      }

      if (entity.m_20159_()) {
         entity.m_8127_();
      }

      entity.m_6021_(dest.f_82479_, dest.f_82480_, dest.f_82481_);
      entity.m_183634_();
      playerMagicData.resetAdditionalCastData();
      entity.m_5496_(this.getCastFinishSound().get(), 2.0F, 1.0F);
      super.onCast(level, spellLevel, entity, castSource, playerMagicData);
   }

   private void zapEntitiesBetween(LivingEntity caster, int spellLevel, Vec3 blockEnd) {
      Vec3 start = caster.m_146892_();
      Vec3 end = blockEnd.m_82520_(0.0, (double)caster.m_20192_(), 0.0);
      AABB range = caster.m_20191_().m_82369_(end.m_82546_(start));

      for (Entity target : caster.f_19853_.m_45933_(caster, range)) {
         Vec3 height = new Vec3(0.0, (double)caster.m_20192_(), 0.0);
         if (Utils.checkEntityIntersecting(target, start, end, 1.0F).m_6662_() != Type.MISS
            || Utils.checkEntityIntersecting(target, start.m_82546_(height), end.m_82546_(height), 1.0F).m_6662_() != Type.MISS) {
            DamageSources.applyDamage(target, this.getDamage(spellLevel, caster), this.getDamageSource(caster));
         }
      }
   }

   private float getDistance(int spellLevel, LivingEntity sourceEntity) {
      return this.getSpellPower(spellLevel, sourceEntity);
   }

   private float getDamage(int spellLevel, LivingEntity sourceEntity) {
      return this.getSpellPower(spellLevel, sourceEntity);
   }
}
