package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.block.IPrismaticBlock;
import com.hollingsworth.arsnouveau.api.event.SpellProjectileHitEvent;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntityProjectileSpell extends ColoredProjectile {
   public int age;
   public SpellResolver spellResolver;
   public int pierceLeft;
   public int numSensitive;
   public boolean isNoGravity = true;
   public boolean canTraversePortals = true;
   public int prismRedirect;
   @Deprecated
   public int expireTime = 1200;
   public Set<BlockPos> hitList = new HashSet<>();

   public EntityProjectileSpell(EntityType<? extends EntityProjectileSpell> entityType, Level world) {
      super(entityType, world);
   }

   public EntityProjectileSpell(EntityType<? extends EntityProjectileSpell> entityType, Level world, double x, double y, double z) {
      super(entityType, world, x, y, z);
   }

   public EntityProjectileSpell(EntityType<? extends EntityProjectileSpell> type, Level worldIn, LivingEntity shooter) {
      super(type, worldIn, shooter);
      this.m_6034_(shooter.m_20185_(), shooter.m_20188_() - 0.1F, shooter.m_20189_());
   }

   public EntityProjectileSpell(Level world, double x, double y, double z) {
      this((EntityType<? extends EntityProjectileSpell>)ModEntities.SPELL_PROJ.get(), world, x, y, z);
   }

   public EntityProjectileSpell(EntityType<? extends EntityProjectileSpell> entityType, Level world, SpellResolver resolver) {
      this(entityType, world, resolver.spellContext.getUnwrappedCaster());
      this.spellResolver = resolver;
      this.pierceLeft = resolver.spell.getBuffsAtIndex(0, resolver.spellContext.getUnwrappedCaster(), AugmentPierce.INSTANCE);
      this.numSensitive = resolver.spell.getBuffsAtIndex(0, resolver.spellContext.getUnwrappedCaster(), AugmentSensitive.INSTANCE);
      this.setColor(resolver.spellContext.getColors());
   }

   public EntityProjectileSpell(Level world, SpellResolver resolver) {
      this((EntityType<? extends EntityProjectileSpell>)ModEntities.SPELL_PROJ.get(), world, resolver);
   }

   public EntityProjectileSpell(Level world, LivingEntity shooter) {
      this((EntityType<? extends EntityProjectileSpell>)ModEntities.SPELL_PROJ.get(), world, shooter);
   }

   public void m_8119_() {
      super.m_8119_();
      this.age++;
      if ((this.f_19853_.f_46443_ || this.age <= this.getExpirationTime()) && (this.f_19853_.f_46443_ || this.spellResolver != null)) {
         this.f_19790_ = this.m_20185_();
         this.f_19791_ = this.m_20186_();
         this.f_19792_ = this.m_20189_();
         Vec3 thisPosition = this.m_20182_();
         Vec3 nextPosition = this.getNextHitPosition();
         this.traceAnyHit(this.getHitResult(), thisPosition, nextPosition);
         this.tickNextPosition();
         if (this.f_19853_.f_46443_ && this.age > this.getParticleDelay()) {
            this.playParticles();
         }
      } else {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public HitResult getHitResult() {
      Vec3 thisPosition = this.m_20182_();
      Vec3 nextPosition = this.getNextHitPosition();
      return this.f_19853_.m_45547_(new ClipContext(thisPosition, nextPosition, this.numSensitive > 0 ? Block.OUTLINE : Block.COLLIDER, Fluid.NONE, this));
   }

   public Vec3 getNextHitPosition() {
      return this.m_20182_().m_82549_(this.m_20184_());
   }

   public void traceAnyHit(@Nullable HitResult raytraceresult, Vec3 thisPosition, Vec3 nextPosition) {
      if (raytraceresult != null && raytraceresult.m_6662_() != Type.MISS) {
         nextPosition = raytraceresult.m_82450_();
      }

      EntityHitResult entityraytraceresult = this.findHitEntity(thisPosition, nextPosition);
      if (entityraytraceresult != null) {
         raytraceresult = entityraytraceresult;
      }

      if (raytraceresult != null && raytraceresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
         this.m_6532_(raytraceresult);
         this.f_19812_ = true;
      }

      if (raytraceresult != null
         && raytraceresult.m_6662_() == Type.MISS
         && raytraceresult instanceof BlockHitResult blockHitResult
         && this.canTraversePortals()) {
         BlockRegistry.PORTAL_BLOCK.m_5581_(this.f_19853_, this.f_19853_.m_8055_(new BlockPos(raytraceresult.m_82450_())), blockHitResult, this);
      }
   }

   @Nullable
   public HitResult transformHitResult(@Nullable HitResult hitResult) {
      return (HitResult)(hitResult instanceof BlockHitResult hitResult1
         ? new BlockHitResult(hitResult1.m_82450_(), hitResult1.m_82434_(), hitResult1.m_82425_(), false)
         : hitResult);
   }

   public boolean canTraversePortals() {
      return this.canTraversePortals;
   }

   public int getExpirationTime() {
      return MethodProjectile.INSTANCE.getProjectileLifespan() * 20;
   }

   public void tickNextPosition() {
      Vec3 vec3d = this.m_20184_();
      double x = this.m_20185_() + vec3d.f_82479_;
      double y = this.m_20186_() + vec3d.f_82480_;
      double z = this.m_20189_() + vec3d.f_82481_;
      if (!this.m_20068_()) {
         Vec3 vec3d1 = this.m_20184_();
         this.m_20334_(vec3d1.f_82479_, vec3d1.f_82480_, vec3d1.f_82481_);
      }

      this.m_6034_(x, y, z);
   }

   public int getParticleDelay() {
      return 2;
   }

   public void playParticles() {
      double deltaX = this.m_20185_() - this.f_19790_;
      double deltaY = this.m_20186_() - this.f_19791_;
      double deltaZ = this.m_20189_() - this.f_19792_;
      double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 6.0);

      for (double j = 0.0; j < dist; j++) {
         double coeff = j / dist;
         this.f_19853_
            .m_7106_(
               GlowParticleData.createData(this.getParticleColor()),
               (double)((float)(this.f_19854_ + deltaX * coeff)),
               (double)((float)(this.f_19855_ + deltaY * coeff)) + 0.1,
               (double)((float)(this.f_19856_ + deltaZ * coeff)),
               (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
               (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
               (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F))
            );
      }
   }

   @Nullable
   @Override
   protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
      return ProjectileUtil.m_37304_(this.f_19853_, this, pStartVec, pEndVec, this.m_20191_().m_82369_(this.m_20184_()).m_82400_(1.0), this::m_5603_);
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -Mth.m_14031_(rotationYawIn * (float) (Math.PI / 180.0)) * Mth.m_14089_(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -Mth.m_14031_((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = Mth.m_14089_(rotationYawIn * (float) (Math.PI / 180.0)) * Mth.m_14089_(rotationPitchIn * (float) (Math.PI / 180.0));
      this.m_6686_((double)f, (double)f1, (double)f2, velocity, inaccuracy);
   }

   public void m_6686_(double x, double y, double z, float velocity, float inaccuracy) {
      Vec3 vec3d = new Vec3(x, y, z)
         .m_82541_()
         .m_82520_(
            this.f_19796_.m_188583_() * 0.0075F * (double)inaccuracy,
            this.f_19796_.m_188583_() * 0.0075F * (double)inaccuracy,
            this.f_19796_.m_188583_() * 0.0075F * (double)inaccuracy
         )
         .m_82490_((double)velocity);
      this.m_20256_(vec3d);
      float f = Mth.m_14116_((float)vec3d.m_165925_());
      this.f_19857_ = (float)(Mth.m_14136_(vec3d.f_82479_, vec3d.f_82481_) * 180.0F / (float)Math.PI);
      this.f_19858_ = (float)(Mth.m_14136_(vec3d.f_82480_, (double)f) * 180.0F / (float)Math.PI);
      this.f_19859_ = this.f_19857_;
      this.f_19860_ = this.f_19858_;
   }

   public void m_142467_(RemovalReason reason) {
      if (reason == RemovalReason.UNLOADED_TO_CHUNK) {
         reason = RemovalReason.DISCARDED;
      }

      super.m_142467_(reason);
   }

   public boolean m_20068_() {
      return this.isNoGravity;
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.SPELL_PROJ.get();
   }

   protected void attemptRemoval() {
      this.pierceLeft--;
      if (this.pierceLeft < 0) {
         this.f_19853_.m_7605_(this, (byte)3);
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   protected void m_6532_(HitResult result) {
      result = this.transformHitResult(result);
      if (!this.f_19853_.f_46443_) {
         SpellProjectileHitEvent event = new SpellProjectileHitEvent(this, result);
         MinecraftForge.EVENT_BUS.post(event);
         if (event.isCanceled()) {
            return;
         }

         if (result instanceof EntityHitResult entityHitResult) {
            if (entityHitResult.m_82443_().equals(this.m_37282_())) {
               return;
            }

            if (this.spellResolver != null) {
               this.spellResolver.onResolveEffect(this.f_19853_, result);
               Networking.sendToNearby(
                  this.f_19853_,
                  new BlockPos(result.m_82450_()),
                  new PacketANEffect(PacketANEffect.EffectType.BURST, new BlockPos(result.m_82450_()), this.getParticleColorWrapper())
               );
               this.attemptRemoval();
            }
         }

         if (result instanceof BlockHitResult blockraytraceresult && !this.m_213877_() && !this.hitList.contains(blockraytraceresult.m_82425_())) {
            BlockState state = this.f_19853_.m_8055_(blockraytraceresult.m_82425_());
            if (state.m_60734_() instanceof IPrismaticBlock prismaticBlock) {
               prismaticBlock.onHit((ServerLevel)this.f_19853_, blockraytraceresult.m_82425_(), this);
               return;
            }

            if (state.m_60767_() == Material.f_76298_) {
               state.m_60734_().m_7892_(state, this.f_19853_, blockraytraceresult.m_82425_(), this);
               return;
            }

            if (state.m_60734_() instanceof TargetBlock) {
               this.m_8060_(blockraytraceresult);
            }

            if (this.spellResolver != null) {
               this.hitList.add(blockraytraceresult.m_82425_());
               this.spellResolver.onResolveEffect(this.f_19853_, blockraytraceresult);
            }

            Networking.sendToNearby(
               this.f_19853_,
               ((BlockHitResult)result).m_82425_(),
               new PacketANEffect(PacketANEffect.EffectType.BURST, new BlockPos(result.m_82450_()).m_7495_(), this.getParticleColorWrapper())
            );
            this.attemptRemoval();
         }
      }
   }

   protected boolean m_5603_(Entity entity) {
      return super.m_5603_(entity) || entity.m_6095_().m_204039_(EntityTags.SPELL_CAN_HIT);
   }

   public EntityProjectileSpell(SpawnEntity packet, Level world) {
      super((EntityType<? extends ColoredProjectile>)ModEntities.SPELL_PROJ.get(), world);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_141965_(ClientboundAddEntityPacket pPacket) {
      super.m_141965_(pPacket);
      Entity entity = this.f_19853_.m_6815_(pPacket.m_131509_());
      if (entity != null) {
         this.m_5602_(entity);
      }
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128441_("pierce")) {
         this.pierceLeft = tag.m_128451_("pierce");
      }

      this.isNoGravity = tag.m_128471_("gravity");
   }

   @Override
   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("pierce", this.pierceLeft);
      tag.m_128379_("gravity", this.isNoGravity);
   }
}
