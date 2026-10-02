package com.bobmowzie.mowziesmobs.server.entity.frostmaw;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;

public class EntityFrozenController extends Entity {
   public EntityFrozenController(EntityType<? extends EntityFrozenController> type, Level world) {
      super(type, world);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.f_19797_ >= 70 && !this.m_20160_()) {
         this.m_146870_();
      }
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag compound) {
   }

   protected void m_7380_(CompoundTag compound) {
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean canRiderInteract() {
      return false;
   }

   public double m_6048_() {
      return 0.0;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
      return true;
   }

   public void m_7332_(Entity passenger) {
      if (this.m_20363_(passenger)) {
         if (passenger instanceof Player) {
            passenger.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
         } else {
            passenger.m_19890_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
         }
      }
   }
}
