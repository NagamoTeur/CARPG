package io.redspace.ironsspellbooks.spells.fire;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerRecasts;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.entity.spells.fireball.SmallMagicFireball;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class FlamingBarrageSpell extends AbstractSpell {
   private final ResourceLocation spellId = new ResourceLocation("irons_spellbooks", "flaming_barrage");
   private final DefaultConfig defaultConfig = new DefaultConfig()
      .setMinRarity(SpellRarity.RARE)
      .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
      .setMaxLevel(5)
      .setCooldownSeconds(15.0)
      .build();

   @Override
   public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
      return List.of(
         Component.m_237110_("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), 2)}),
         Component.m_237110_("ui.irons_spellbooks.projectile_count", new Object[]{this.getRecastCount(spellLevel, caster)})
      );
   }

   public FlamingBarrageSpell() {
      this.manaCostPerLevel = 5;
      this.baseSpellPower = 3;
      this.spellPowerPerLevel = 2;
      this.castTime = 0;
      this.baseManaCost = 80;
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
   public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
      return 5;
   }

   @Override
   public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
      return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 64, 0.15F);
   }

   @Override
   public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
      if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetEntityCastData) {
         PlayerRecasts recasts = playerMagicData.getPlayerRecasts();
         if (!recasts.hasRecastForSpell(this.getSpellId())) {
            recasts.addRecast(
               new RecastInstance(
                  this.getSpellId(),
                  spellLevel,
                  this.getRecastCount(spellLevel, entity),
                  80,
                  castSource,
                  new MultiTargetEntityCastData(targetEntityCastData.getTarget((ServerLevel)level))
               ),
               playerMagicData
            );
         } else {
            RecastInstance instance = recasts.getRecastInstance(this.getSpellId());
            if (instance != null && instance.getCastData() instanceof MultiTargetEntityCastData targetingData) {
               targetingData.addTarget(targetEntityCastData.getTargetUUID());
            }
         }
      }

      super.onCast(level, spellLevel, entity, castSource, playerMagicData);
   }

   @Override
   public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
      super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
      Level level = serverPlayer.f_19853_;
      Vec3 origin = serverPlayer.m_146892_().m_82549_(serverPlayer.m_20156_().m_82541_().m_82490_(0.2F));
      level.m_6263_(null, origin.f_82479_, origin.f_82480_, origin.f_82481_, SoundEvents.f_11705_, SoundSource.PLAYERS, 2.0F, 1.0F);
      if (castDataSerializable instanceof MultiTargetEntityCastData targetingData) {
         targetingData.getTargets().forEach(uuid -> {
            LivingEntity target = (LivingEntity)((ServerLevel)serverPlayer.f_19853_).m_8791_(uuid);
            if (target != null) {
               SmallMagicFireball fireball = new SmallMagicFireball(level, serverPlayer);
               fireball.m_146884_(origin.m_82492_(0.0, (double)fireball.m_20206_(), 0.0));
               Vec3 vec = target.m_20191_().m_82399_().m_82546_(serverPlayer.m_146892_()).m_82541_();
               float inaccuracy = (float)Mth.m_14085_(0.2F, 1.4F, target.m_20182_().m_82557_(serverPlayer.m_20182_()) / 1024.0);
               fireball.shoot(vec.m_82490_(0.75), inaccuracy);
               fireball.setDamage(this.getDamage(recastInstance.getSpellLevel(), serverPlayer));
               fireball.setHomingTarget(target);
               level.m_7967_(fireball);
            }
         });
      }
   }

   private float getDamage(int spellLevel, LivingEntity caster) {
      return this.getSpellPower(spellLevel, caster);
   }

   @Override
   public ICastDataSerializable getEmptyCastData() {
      return new MultiTargetEntityCastData();
   }
}
