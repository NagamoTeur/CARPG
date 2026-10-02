package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleSparkleData;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntityRitualProjectile extends ColoredProjectile {
   public BlockPos tilePos;

   public EntityRitualProjectile(Level worldIn, double x, double y, double z) {
      super((EntityType<? extends ColoredProjectile>)ModEntities.ENTITY_RITUAL.get(), worldIn, x, y, z);
   }

   public EntityRitualProjectile(Level worldIn, BlockPos pos) {
      super(
         (EntityType<? extends ColoredProjectile>)ModEntities.ENTITY_RITUAL.get(),
         worldIn,
         (double)pos.m_123341_(),
         (double)pos.m_123342_(),
         (double)pos.m_123343_()
      );
   }

   public EntityRitualProjectile(EntityType<EntityRitualProjectile> entityAOEProjectileEntityType, Level world) {
      super(entityAOEProjectileEntityType, world);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.m_5776_() || this.tilePos != null && this.f_19853_.m_7702_(this.tilePos) instanceof RitualBrazierTile tile && tile.ritual != null) {
         this.f_19790_ = this.m_20185_();
         this.f_19791_ = this.m_20186_();
         this.f_19792_ = this.m_20189_();
         this.m_6034_(this.m_20185_(), this.m_20186_() + Math.sin((double)this.f_19853_.m_46467_() / 10.0) / 10.0, this.m_20189_());
         this.f_19854_ = this.m_20185_();
         this.f_19855_ = this.m_20186_();
         this.f_19856_ = this.m_20189_();
         if (this.f_19853_.f_46443_) {
            int counter = 0;

            for (double j = 0.0; j < 3.0; j++) {
               counter += this.f_19853_.f_46441_.m_188503_(3);
               if (counter
                     % (
                        ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_() == 0
                           ? 1
                           : 2 * ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_()
                     )
                  == 0) {
                  this.f_19853_
                     .m_7106_(
                        ParticleSparkleData.createData(this.getParticleColor()),
                        (double)((float)this.m_20182_().m_7096_()) + Math.sin((double)this.f_19853_.m_46467_() / 3.0),
                        (double)((float)this.m_20182_().m_7098_()),
                        (double)((float)this.m_20182_().m_7094_()) + Math.cos((double)this.f_19853_.m_46467_() / 3.0),
                        (double)(0.0225F * this.f_19796_.m_188501_()),
                        (double)(0.0225F * this.f_19796_.m_188501_()),
                        (double)(0.0225F * this.f_19796_.m_188501_())
                     );
               }
            }

            for (double jx = 0.0; jx < 3.0; jx++) {
               counter += this.f_19853_.f_46441_.m_188503_(3);
               if (counter
                     % (
                        ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_() == 0
                           ? 1
                           : 2 * ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_()
                     )
                  == 0) {
                  this.f_19853_
                     .m_7106_(
                        ParticleSparkleData.createData(new ParticleColor(2, 0, 144)),
                        (double)((float)this.m_20182_().m_7096_()) - Math.sin((double)this.f_19853_.m_46467_() / 3.0),
                        (double)((float)this.m_20182_().m_7098_()),
                        (double)((float)this.m_20182_().m_7094_()) - Math.cos((double)this.f_19853_.m_46467_() / 3.0),
                        (double)(0.0225F * this.f_19796_.m_188501_()),
                        (double)(0.0225F * this.f_19796_.m_188501_()),
                        (double)(0.0225F * this.f_19796_.m_188501_())
                     );
               }
            }
         }
      } else {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_RITUAL.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityRitualProjectile(SpawnEntity packet, Level world) {
      super((EntityType<? extends ColoredProjectile>)ModEntities.ENTITY_RITUAL.get(), world);
   }

   public boolean m_20223_(CompoundTag tag) {
      if (this.tilePos != null) {
         tag.m_128365_("ritpos", NbtUtils.m_129224_(this.tilePos));
      }

      return super.m_20223_(tag);
   }

   @Override
   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      if (compound.m_128441_("ritpos")) {
         this.tilePos = NbtUtils.m_129239_(compound.m_128469_("ritpos"));
      }
   }
}
