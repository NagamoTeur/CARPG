package io.redspace.ironsspellbooks.entity.mobs;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public interface MagicSummon extends AntiMagicSusceptible {
   LivingEntity getSummoner();

   void onUnSummon();

   @Override
   default void onAntiMagic(MagicData playerMagicData) {
      this.onUnSummon();
   }

   default boolean shouldIgnoreDamage(DamageSource damageSource) {
      return !damageSource.m_19378_() && damageSource instanceof EntityDamageSource && !ServerConfigs.CAN_ATTACK_OWN_SUMMONS.get()
         ? this.getSummoner() != null
            && damageSource.m_7639_() != null
            && (damageSource.m_7639_().equals(this.getSummoner()) || this.getSummoner().m_7307_(damageSource.m_7639_()))
         : false;
   }

   default boolean isAlliedHelper(Entity entity) {
      if (this.getSummoner() == null) {
         return false;
      } else {
         boolean isFellowSummon;
         boolean var10000;
         label28: {
            isFellowSummon = entity == this.getSummoner() || entity.m_7307_(this.getSummoner());
            if (entity instanceof OwnableEntity ownableEntity && ownableEntity.m_21826_() == this.getSummoner()) {
               var10000 = true;
               break label28;
            }

            var10000 = false;
         }

         boolean hasCommonOwner = var10000;
         return isFellowSummon || hasCommonOwner;
      }
   }

   default void onDeathHelper() {
      if (this instanceof LivingEntity entity) {
         Level level = entity.f_19853_;
         Component deathMessage = entity.m_21231_().m_19293_();
         if (!level.f_46443_ && level.m_46469_().m_46207_(GameRules.f_46142_) && this.getSummoner() instanceof ServerPlayer player) {
            player.m_213846_(deathMessage);
         }
      }
   }

   default void onRemovedHelper(Entity entity, SummonTimer timer) {
      RemovalReason reason = entity.m_146911_();
      if (reason != null && this.getSummoner() instanceof ServerPlayer player && reason.m_146965_()) {
         MobEffectInstance effect = player.m_21124_(timer);
         if (effect != null) {
            MobEffectInstance decrement = new MobEffectInstance(timer, effect.m_19557_(), effect.m_19564_() - 1, false, false, true);
            if (decrement.m_19564_() >= 0) {
               player.m_21221_().put(timer, decrement);
               player.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(player.m_19879_(), decrement));
            } else {
               player.m_21195_(timer);
            }
         }

         if (reason.equals(RemovalReason.DISCARDED)) {
            player.m_213846_(Component.m_237110_("ui.irons_spellbooks.summon_despawn_message", new Object[]{((Entity)this).m_5446_()}));
         }
      }
   }
}
