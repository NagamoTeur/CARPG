package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.gameevent.GameEvent;

public class The_Leviathan_Part extends Cm_Part_Entity<The_Leviathan_Entity> {
   private final EntityDimensions size;
   public float scale = 1.0F;

   public The_Leviathan_Part(The_Leviathan_Entity parent, float sizeX, float sizeY) {
      super(parent);
      this.size = EntityDimensions.m_20395_(sizeX, sizeY);
      this.m_6210_();
   }

   public The_Leviathan_Part(The_Leviathan_Entity entityCachalotWhale, float sizeX, float sizeY, EntityDimensions size) {
      super(entityCachalotWhale);
      this.size = size;
   }

   protected void m_8097_() {
   }

   public void m_7350_(EntityDataAccessor<?> accessor) {
   }

   public MobType getMobType() {
      return MobType.f_21644_;
   }

   public boolean m_5829_() {
      return false;
   }

   @Override
   public boolean m_6087_() {
      return ((The_Leviathan_Entity)this.getParent()).m_6084_();
   }

   @Override
   protected void setSize(EntityDimensions size) {
      super.setSize(size);
   }

   public boolean m_6469_(DamageSource source, float amount) {
      boolean flag = this.getParent() != null && ((The_Leviathan_Entity)this.getParent()).attackEntityFromPart(this, source, amount);
      if (flag) {
         this.m_146850_(GameEvent.f_223706_);
      }

      return flag;
   }

   protected void m_7378_(CompoundTag compound) {
   }

   protected void m_7380_(CompoundTag compound) {
   }

   public boolean m_7306_(Entity entity) {
      return this == entity || this.getParent() == entity;
   }

   protected void m_19915_(float yaw, float pitch) {
      this.m_146922_(yaw % 360.0F);
      this.m_146926_(pitch % 360.0F);
   }

   protected boolean m_7341_(Entity entityIn) {
      return false;
   }

   public boolean m_6072_() {
      return false;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      throw new UnsupportedOperationException();
   }

   @Override
   public EntityDimensions m_6972_(Pose poseIn) {
      return this.size;
   }
}
