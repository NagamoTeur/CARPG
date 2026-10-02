package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class EntitySunstrike extends Entity implements IEntityAdditionalSpawnData {
   public static final int STRIKE_EXPLOSION = 35;
   private static final int STRIKE_LENGTH = 43;
   private static final int STRIKE_LINGER = 1243;
   private int prevStrikeTime;
   private int strikeTime;
   private LivingEntity caster;
   private static final EntityDataAccessor<Integer> VARIANT_LEAST = SynchedEntityData.m_135353_(EntitySunstrike.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> VARIANT_MOST = SynchedEntityData.m_135353_(EntitySunstrike.class, EntityDataSerializers.f_135028_);

   public EntitySunstrike(EntityType<? extends EntitySunstrike> type, Level world) {
      super(type, world);
      this.f_19811_ = true;
   }

   public EntitySunstrike(EntityType<? extends EntitySunstrike> type, Level world, LivingEntity caster, int x, int y, int z) {
      this(type, world);
      this.caster = caster;
      this.m_6034_((double)((float)x + 0.5F), (double)((float)y + 1.0625F), (double)((float)z + 0.5F));
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(VARIANT_LEAST, 0);
      this.m_20088_().m_135372_(VARIANT_MOST, 0);
   }

   public float getStrikeTime(float delta) {
      return this.getActualStrikeTime(delta) / 43.0F;
   }

   public float getStrikeDrawTime(float delta) {
      return this.getActualStrikeTime(delta) / 35.0F;
   }

   public float getStrikeDamageTime(float delta) {
      return (this.getActualStrikeTime(delta) - 35.0F) / 8.0F;
   }

   public boolean isStrikeDrawing(float delta) {
      return this.getActualStrikeTime(delta) < 35.0F;
   }

   public boolean isLingering(float delta) {
      return this.getActualStrikeTime(delta) > 40.0F;
   }

   public boolean isStriking(float delta) {
      return this.getActualStrikeTime(delta) < 43.0F;
   }

   private float getActualStrikeTime(float delta) {
      return (float)this.prevStrikeTime + (float)(this.strikeTime - this.prevStrikeTime) * delta;
   }

   private void setStrikeTime(int strikeTime) {
      this.prevStrikeTime = this.strikeTime = strikeTime;
   }

   public boolean isStriking() {
      return this.isStriking(1.0F);
   }

   public long getVariant() {
      return (long)((Integer)this.m_20088_().m_135370_(VARIANT_MOST)).intValue() << 32
         | (long)((Integer)this.m_20088_().m_135370_(VARIANT_LEAST)).intValue() & 4294967295L;
   }

   private void setVariant(long variant) {
      this.m_20088_().m_135381_(VARIANT_MOST, (int)(variant >> 32));
      this.m_20088_().m_135381_(VARIANT_LEAST, (int)variant);
   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_6783_(double distance) {
      return distance < 1024.0;
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevStrikeTime = this.strikeTime;
      if (this.f_19853_.f_46443_) {
         if (this.strikeTime == 0) {
            MowziesMobs.PROXY.playSunstrikeSound(this);
         } else if (this.strikeTime < 25) {
            float time = this.getStrikeTime(1.0F);
            int timeBonus = (int)(time * 5.0F);
            int orbCount = this.f_19796_.m_188503_(4 + timeBonus) + timeBonus + 1;

            while (orbCount-- > 0) {
               float theta = this.f_19796_.m_188501_() * (float) (Math.PI * 2);
               float min = 0.2F;
               float max = 1.9F;
               float r = this.f_19796_.m_188501_() * 1.6999999F + 0.2F;
               float ox = r * Mth.m_14089_(theta);
               float oz = r * Mth.m_14031_(theta);
               float minY = 0.1F;
               float oy = this.f_19796_.m_188501_() * (time * 6.0F - 0.1F) + 0.1F;
               this.f_19853_
                  .m_7106_(
                     new ParticleOrb.OrbData((float)this.m_20185_(), (float)this.m_20189_()),
                     this.m_20185_() + (double)ox,
                     this.m_20186_() + (double)oy,
                     this.m_20189_() + (double)oz,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         } else if (this.strikeTime > 35) {
            this.smolder();
         } else if (this.strikeTime == 35) {
            this.spawnExplosionParticles(10);
         }
      } else {
         this.moveDownToGround();
         if (this.strikeTime >= 1243 || !this.f_19853_.m_46861_(this.m_20183_())) {
            this.m_146870_();
         } else if (this.strikeTime == 35) {
            this.damageEntityLivingBaseNearby(3.0);
         }
      }

      this.strikeTime++;
   }

   public void moveDownToGround() {
      HitResult rayTrace = this.rayTrace(this);
      if (rayTrace.m_6662_() == Type.BLOCK) {
         BlockHitResult hitResult = (BlockHitResult)rayTrace;
         if (hitResult.m_82434_() == Direction.UP) {
            BlockState hitBlock = this.f_19853_.m_8055_(hitResult.m_82425_());
            if (this.strikeTime > 43 && hitBlock != this.f_19853_.m_8055_(this.m_20183_().m_7495_())) {
               this.m_146870_();
            }

            if (hitBlock.m_60734_() instanceof SlabBlock && hitBlock.m_61143_(BlockStateProperties.f_61397_) == SlabType.BOTTOM) {
               this.m_6034_(this.m_20185_(), (double)((float)hitResult.m_82425_().m_123342_() + 1.0625F - 0.5F), this.m_20189_());
            } else {
               this.m_6034_(this.m_20185_(), (double)((float)hitResult.m_82425_().m_123342_() + 1.0625F), this.m_20189_());
            }

            if (this.f_19853_ instanceof ServerLevel) {
               ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundTeleportEntityPacket(this));
            }
         }
      }
   }

   public void damageEntityLivingBaseNearby(double radius) {
      AABB region = new AABB(
         this.m_20185_() - radius,
         this.m_20186_() - 0.5,
         this.m_20189_() - radius,
         this.m_20185_() + radius,
         (double)(this.f_19853_.m_151558_() + 20),
         this.m_20189_() + radius
      );
      List<Entity> entities = this.f_19853_.m_45933_(this, region);
      double radiusSq = radius * radius;

      for (Entity entity : entities) {
         if (entity instanceof LivingEntity
            && this.getDistanceSqXZToEntity(entity) < radiusSq
            && (!(this.caster instanceof EntityUmvuthi) || !(entity instanceof LeaderSunstrikeImmune))
            && (!(this.caster instanceof Player) || entity != this.caster)) {
            float damageFire = 2.0F;
            float damageMob = 2.0F;
            if (this.caster instanceof EntityUmvuthi) {
               damageFire = (float)((double)damageFire * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
               damageMob = (float)((double)damageMob * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
            }

            if (this.caster instanceof Player) {
               damageFire = (float)((double)damageFire * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get());
               damageMob = (float)((double)damageMob * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get());
            }

            if (entity.m_6469_(DamageSource.m_19340_(this, this.caster), damageMob)) {
               entity.f_19802_ = 0;
            }

            if (entity.m_6469_(DamageSource.f_19307_, damageFire)) {
               entity.m_20254_(3);
            }
         }
      }
   }

   public double getDistanceSqXZToEntity(Entity entityIn) {
      double d0 = this.m_20185_() - entityIn.m_20185_();
      double d2 = this.m_20189_() - entityIn.m_20189_();
      return d0 * d0 + d2 * d2;
   }

   private void smolder() {
      if (this.f_19796_.m_188501_() < 0.1F) {
         int amount = this.f_19796_.m_188503_(2) + 1;

         while (amount-- > 0) {
            float theta = this.f_19796_.m_188501_() * (float) (Math.PI * 2);
            float r = this.f_19796_.m_188501_() * 0.7F;
            float x = r * Mth.m_14089_(theta);
            float z = r * Mth.m_14031_(theta);
            this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20185_() + (double)x, this.m_20186_() + 0.1, this.m_20189_() + (double)z, 0.0, 0.0, 0.0);
         }
      }
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.1F;
         float yaw = (float)i * ((float) (Math.PI * 2) / (float)amount);
         float vy = this.f_19796_.m_188501_() * 0.08F;
         float vx = 0.1F * Mth.m_14089_(yaw);
         float vz = 0.1F * Mth.m_14031_(yaw);
         this.f_19853_.m_7106_(ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_() + 0.1, this.m_20189_(), (double)vx, (double)vy, (double)vz);
      }

      for (int i = 0; i < amount / 2; i++) {
         this.f_19853_.m_7106_(ParticleTypes.f_123756_, this.m_20185_(), this.m_20186_() + 0.1, this.m_20189_(), 0.0, 0.0, 0.0);
      }
   }

   public void onSummon() {
      this.setVariant(this.f_19796_.m_188505_());
   }

   private HitResult rayTrace(EntitySunstrike entity) {
      Vec3 startPos = new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      Vec3 endPos = new Vec3(entity.m_20185_(), (double)this.f_19853_.m_141937_(), entity.m_20189_());
      return entity.f_19853_.m_45547_(new ClipContext(startPos, endPos, Block.COLLIDER, Fluid.NONE, this));
   }

   public void m_7380_(CompoundTag compound) {
      compound.m_128405_("strikeTime", this.strikeTime);
      compound.m_128356_("variant", this.getVariant());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_7378_(CompoundTag compound) {
      this.setStrikeTime(compound.m_128451_("strikeTime"));
      this.setVariant(compound.m_128454_("variant"));
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.strikeTime);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.setStrikeTime(buffer.readInt());
   }
}
