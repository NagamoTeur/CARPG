package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event.Result;

public class EntityUmvuthanaRaptor extends EntityUmvuthana implements LeaderSunstrikeImmune, Enemy {
   private final List<EntityUmvuthanaFollowerToRaptor> pack = new ArrayList<>();
   private final int packRadius = 3;

   public EntityUmvuthanaRaptor(EntityType<? extends EntityUmvuthanaRaptor> type, Level world) {
      super(type, world);
      this.setMask(MaskType.FURY);
      this.f_21364_ = 8;
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(3, new UmvuthanaHurtByTargetAI(this));
   }

   @Override
   protected void registerTargetGoals() {
      this.registerHuntingTargetGoals();
   }

   @Override
   protected boolean canHoldVaryingWeapons() {
      return false;
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22281_, 6.0).m_22268_(Attributes.f_22276_, 10.0);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      int i = 0;

      while (i < this.pack.size()) {
         this.pack.get(i).index = i++;
      }

      if (!this.f_19853_.f_46443_ && this.pack != null) {
         float theta = (float) (Math.PI * 2) / (float)this.pack.size();

         for (int ix = 0; ix < this.pack.size(); ix++) {
            EntityUmvuthanaFollowerToRaptor hunter = this.pack.get(ix);
            if (hunter.m_5448_() == null) {
               hunter.m_21573_()
                  .m_26519_(
                     this.m_20185_() + (double)(3.0F * Mth.m_14089_(theta * (float)ix)),
                     this.m_20186_(),
                     this.m_20189_() + (double)(3.0F * Mth.m_14031_(theta * (float)ix)),
                     0.45
                  );
               if (this.m_20270_(hunter) > 20.0F && this.f_19861_) {
                  hunter.m_6034_(
                     this.m_20185_() + (double)(3.0F * Mth.m_14089_(theta * (float)ix)),
                     this.m_20186_(),
                     this.m_20189_() + (double)(3.0F * Mth.m_14031_(theta * (float)ix))
                  );
               }
            }
         }
      }

      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
         this.m_146870_();
      }
   }

   public void m_142687_(RemovalReason reason) {
      if (this.f_19797_ == 0) {
         this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
      }

      this.pack.forEach(EntityUmvuthanaFollowerToRaptor::removeLeader);
      super.m_142687_(reason);
   }

   public boolean m_6914_(LevelReader worldReader) {
      return this.f_19797_ == 0
         ? !worldReader.m_46855_(this.m_20191_()) && worldReader.m_45786_(this)
         : !worldReader.m_46855_(this.m_20191_()) && worldReader.m_45786_(this) && this.f_19853_.m_45784_(this);
   }

   public void removePackMember(EntityUmvuthanaFollowerToRaptor tribeHunter) {
      this.pack.remove(tribeHunter);
      this.sortPackMembers();
   }

   public void addPackMember(EntityUmvuthanaFollowerToRaptor tribeHunter) {
      this.pack.add(tribeHunter);
      this.sortPackMembers();
   }

   private void sortPackMembers() {
      double theta = (Math.PI * 2) / (double)this.pack.size();

      for (int i = 0; i < this.pack.size(); i++) {
         int nearestIndex = -1;
         double smallestDiffSq = Double.MAX_VALUE;
         double targetTheta = theta * (double)i;
         double x = this.m_20185_() + 3.0 * Math.cos(targetTheta);
         double z = this.m_20189_() + 3.0 * Math.sin(targetTheta);

         for (int n = 0; n < this.pack.size(); n++) {
            EntityUmvuthanaFollowerToRaptor tribeHunter = this.pack.get(n);
            double diffSq = (x - tribeHunter.m_20185_()) * (x - tribeHunter.m_20185_()) + (z - tribeHunter.m_20189_()) * (z - tribeHunter.m_20189_());
            if (diffSq < smallestDiffSq) {
               smallestDiffSq = diffSq;
               nearestIndex = n;
            }
         }

         if (nearestIndex == -1) {
            throw new ArithmeticException("All pack members have NaN x and z?");
         }

         this.pack.add(i, this.pack.remove(nearestIndex));
      }
   }

   public int getPackSize() {
      return this.pack.size();
   }

   @Nullable
   @Override
   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData, @Nullable CompoundTag compound
   ) {
      int size = this.f_19796_.m_188503_(2) + 3;
      float theta = (float) (Math.PI * 2) / (float)size;

      for (int i = 0; i <= size; i++) {
         EntityUmvuthanaFollowerToRaptor tribeHunter = new EntityUmvuthanaFollowerToRaptor(
            (EntityType<? extends EntityUmvuthanaFollowerToRaptor>)EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), this.f_19853_, this
         );
         tribeHunter.m_6034_(
            this.m_20185_() + 0.1 * (double)Mth.m_14089_(theta * (float)i), this.m_20186_(), this.m_20189_() + 0.1 * (double)Mth.m_14031_(theta * (float)i)
         );
         int weapon = this.f_19796_.m_188503_(3) == 0 ? 1 : 0;
         tribeHunter.setWeapon(weapon);
         world.m_7967_(tribeHunter);
      }

      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   @Override
   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      this.pack.forEach(EntityUmvuthanaFollowerToRaptor::removeLeader);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.UMVUTHANA.spawnConfig;
   }

   public boolean m_5545_(LevelAccessor world, MobSpawnType reason) {
      for (LivingEntity nearbyEntity : this.getEntityLivingBaseNearby(30.0, 10.0, 30.0, 30.0)) {
         if (nearbyEntity instanceof EntityUmvuthanaRaptor
            || nearbyEntity instanceof Villager
            || nearbyEntity instanceof EntityUmvuthi
            || nearbyEntity instanceof Animal) {
            return false;
         }
      }

      return super.m_5545_(world, reason) && world.m_46791_() != Difficulty.PEACEFUL;
   }

   public int m_5792_() {
      return 1;
   }

   public void m_6043_() {
      super.m_6043_();
      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
         this.m_146870_();
      } else if (!this.m_21532_() && !this.m_8023_()) {
         Entity entity = this.f_19853_.m_45930_(this, -1.0);
         Result result = ForgeEventFactory.canEntityDespawn(this);
         if (result == Result.DENY) {
            this.f_20891_ = 0;
            entity = null;
         } else if (result == Result.ALLOW) {
            this.m_146870_();
            entity = null;
         }

         if (entity != null) {
            double d0 = entity.m_20280_(this);
            if (d0 > 16384.0 && this.m_6785_(d0) && this.pack != null) {
               this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
               this.m_146870_();
            }

            if (this.f_20891_ > 600 && this.f_19796_.m_188503_(800) == 0 && d0 > 1024.0 && this.m_6785_(d0) && this.pack != null) {
               this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
               this.m_146870_();
            } else if (d0 < 1024.0) {
               this.f_20891_ = 0;
            }
         }
      } else {
         this.f_20891_ = 0;
      }

      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
         this.m_146870_();
      } else if (!this.m_21532_() && !this.m_8023_()) {
         Entity entityx = this.f_19853_.m_45930_(this, -1.0);
         Result resultx = ForgeEventFactory.canEntityDespawn(this);
         if (resultx == Result.DENY) {
            this.f_20891_ = 0;
            entityx = null;
         } else if (resultx == Result.ALLOW) {
            if (this.pack != null) {
               this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
            }

            this.m_146870_();
            entityx = null;
         }

         if (entityx != null) {
            double d0x = entityx.m_20280_(this);
            int i = this.m_6095_().m_20674_().m_21611_();
            int j = i * i;
            if (d0x > (double)j && this.m_6785_(d0x)) {
               if (this.pack != null) {
                  this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
               }

               this.m_146870_();
            }

            int k = this.m_6095_().m_20674_().m_21612_();
            int l = k * k;
            if (this.f_20891_ > 600 && this.f_19796_.m_188503_(800) == 0 && d0x > (double)l && this.m_6785_(d0x)) {
               if (this.pack != null) {
                  this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
               }

               this.m_146870_();
            } else if (d0x < (double)l) {
               this.f_20891_ = 0;
            }
         }
      } else {
         this.f_20891_ = 0;
      }
   }
}
