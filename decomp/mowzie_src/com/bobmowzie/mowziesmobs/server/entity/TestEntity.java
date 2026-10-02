package com.bobmowzie.mowziesmobs.server.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class TestEntity extends Entity {
   public TestEntity(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   public TestEntity(EntityType<?> p_19870_, Level p_19871_, Vec3 position) {
      super(p_19870_, p_19871_);
      this.m_146884_(position);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ >= 20) {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag p_20052_) {
   }

   protected void m_7380_(CompoundTag p_20139_) {
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
