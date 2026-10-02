package com.github.L_Ender.cataclysm.entity.etc;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.message.MessageMusic;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

public class Animation_Monsters extends Monster implements Enemy {
   protected boolean dropAfterDeathAnim = false;
   public int killDataRecentlyHit;
   public DamageSource killDataCause;
   public Player killDataAttackingPlayer;
   public int attackTicks;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] socketPosArray;

   public Animation_Monsters(EntityType entity, Level world) {
      super(entity, world);
      if (world.f_46443_) {
         this.socketPosArray = new Vec3[0];
      }
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.getBossMusic() != null) {
         if (this.canPlayMusic()) {
            Cataclysm.sendMSGToAll(new MessageMusic(this.m_19879_(), true));
         } else {
            Cataclysm.sendMSGToAll(new MessageMusic(this.m_19879_(), false));
         }
      }
   }

   public boolean canPlayerHearMusic(Player player) {
      return player != null && this.m_6779_(player) && this.m_20270_(player) < 2500.0F;
   }

   public static void setConfigattribute(LivingEntity entity, double hpconfig, double dmgconfig) {
      AttributeInstance maxHealthAttr = entity.m_21051_(Attributes.f_22276_);
      if (maxHealthAttr != null) {
         double difference = maxHealthAttr.m_22115_() * hpconfig - maxHealthAttr.m_22115_();
         maxHealthAttr.m_22118_(
            new AttributeModifier(UUID.fromString("9513569b-57b6-41f5-814e-bdc49b81799f"), "Health config multiplier", difference, Operation.ADDITION)
         );
         entity.m_21153_(entity.m_21233_());
      }

      AttributeInstance attackDamageAttr = entity.m_21051_(Attributes.f_22281_);
      if (attackDamageAttr != null) {
         double difference = attackDamageAttr.m_22115_() * dmgconfig - attackDamageAttr.m_22115_();
         attackDamageAttr.m_22118_(
            new AttributeModifier(UUID.fromString("5b17d7cb-294e-4379-88ab-136c372bec9b"), "Attack config multiplier", difference, Operation.ADDITION)
         );
      }
   }

   public double calculateRange(DamageSource damagesource) {
      return damagesource.m_7639_() != null ? this.m_20280_(damagesource.m_7639_()) : -1.0;
   }

   public double getAngleBetweenEntities(Entity first, Entity second) {
      return Math.atan2(second.m_20189_() - first.m_20189_(), second.m_20185_() - first.m_20185_()) * (180.0 / Math.PI) + 90.0;
   }

   public void disableShield(Player player, int ticks) {
      if (player.m_21254_() && !player.f_19853_.f_46443_) {
         player.m_36335_().m_41524_(player.m_21211_().m_41720_(), ticks);
         player.m_5810_();
         player.f_19853_.m_7605_(this, (byte)30);
      }
   }

   protected boolean canPlayMusic() {
      return !this.m_20067_() && this.m_5448_() instanceof Player && this.m_5448_() != null;
   }

   protected void m_6153_() {
      this.onDeathUpdate(this.deathtimer());
   }

   public SoundEvent getBossMusic() {
      return null;
   }

   public int deathtimer() {
      return 20;
   }

   protected void onDeathAIUpdate() {
   }

   public void onDeathUpdate(int deathDuration) {
      this.onDeathAIUpdate();
      this.f_20919_++;
      if (this.f_20919_ >= deathDuration && !this.f_19853_.m_5776_() && !this.m_213877_()) {
         this.f_19853_.m_7605_(this, (byte)60);
         this.m_142687_(RemovalReason.KILLED);
      }
   }

   public void m_6667_(DamageSource cause) {
      if (!ForgeHooks.onLivingDeath(this, cause)) {
         if (!this.f_20890_) {
            Entity entity = cause.m_7639_();
            LivingEntity livingentity = this.m_21232_();
            if (this.f_20897_ >= 0 && livingentity != null) {
               livingentity.m_5993_(this, this.f_20897_, cause);
            }

            if (this.m_5803_()) {
               this.m_5796_();
            }

            this.f_20890_ = true;
            this.m_21231_().m_19296_();
            if (this.f_19853_ instanceof ServerLevel && (entity == null || entity.m_214076_((ServerLevel)this.f_19853_, this))) {
               this.m_146850_(GameEvent.f_223707_);
               this.m_21268_(livingentity);
               this.AfterDefeatBoss(livingentity);
               if (!this.dropAfterDeathAnim) {
                  this.m_6668_(cause);
               }
            }

            this.killDataCause = cause;
            this.killDataRecentlyHit = this.f_20889_;
            this.killDataAttackingPlayer = this.f_20888_;
            this.f_19853_.m_7605_(this, (byte)3);
            this.m_20124_(Pose.DYING);
         }
      }
   }

   protected void AfterDefeatBoss(@Nullable LivingEntity p_21269_) {
   }

   public void circleEntity(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
      int directionInt = direction ? 1 : -1;
      double t = (double)(directionInt * circleFrame) * 0.5 * (double)speed / (double)radius + (double)offset;
      Vec3 movePos = target.m_20182_().m_82520_((double)radius * Math.cos(t), 0.0, (double)radius * Math.sin(t));
      this.m_21573_().m_26519_(movePos.f_82479_, movePos.f_82480_, movePos.f_82481_, (double)(speed * moveSpeedMultiplier));
   }

   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getEntityLivingBaseNearby((double)x, (double)y, (double)z, (double)radius)) {
         if (entity.m_6087_() && !entity.f_19794_) {
            double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
            entity.m_20334_(-0.1 * Math.cos(angle), entity.m_20184_().f_82480_, -0.1 * Math.sin(angle));
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void setSocketPosArray(int index, Vec3 pos) {
      if (this.socketPosArray != null && this.socketPosArray.length > index) {
         this.socketPosArray[index] = pos;
      }
   }

   public boolean canBePushedByEntity(Entity entity) {
      return true;
   }

   public void m_7334_(Entity entityIn) {
      if (!this.m_5803_() && !this.m_20365_(entityIn) && !entityIn.f_19794_ && !this.f_19794_) {
         double d0 = entityIn.m_20185_() - this.m_20185_();
         double d1 = entityIn.m_20189_() - this.m_20189_();
         double d2 = Mth.m_14005_(d0, d1);
         if (d2 >= 0.01F) {
            d2 = (double)Mth.m_14116_((float)d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05F;
            d1 *= 0.05F;
            if (!this.m_20160_() && this.canBePushedByEntity(entityIn)) {
               this.m_5997_(-d0, 0.0, -d1);
            }

            if (!entityIn.m_20160_()) {
               entityIn.m_5997_(d0, 0.0, d1);
            }
         }
      }
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.f_19853_
         .m_6443_(
            entityClass,
            this.m_20191_().m_82377_(dX, dY, dZ),
            e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F) && e.m_20186_() <= this.m_20186_() + dY
         );
   }
}
