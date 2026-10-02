package com.github.L_Ender.cataclysm.entity.partentity;

import com.github.L_Ender.cataclysm.message.MessageCMMultipart;
import java.util.Objects;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;

public abstract class Cm_Part_Entity<T extends Entity> extends PartEntity<T> {
   protected EntityDimensions realSize;
   protected int newPosRotationIncrements;
   protected double interpTargetX;
   protected double interpTargetY;
   protected double interpTargetZ;
   protected double interpTargetYaw;
   protected double interpTargetPitch;
   public float renderYawOffset;
   public float prevRenderYawOffset;
   public int deathTime;
   public int hurtTime;
   private EntityDimensions dimensions;

   public Cm_Part_Entity(T parent) {
      super(parent);
   }

   @OnlyIn(Dist.CLIENT)
   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements) {
      this.interpTargetX = x;
      this.interpTargetY = y;
      this.interpTargetZ = z;
      this.interpTargetYaw = (double)yaw;
      this.interpTargetPitch = (double)pitch;
      this.newPosRotationIncrements = posRotationIncrements;
   }

   public void m_8119_() {
      this.updateLastPos();
      super.m_8119_();
      if (this.newPosRotationIncrements > 0) {
         double d0 = this.m_20185_() + (this.interpTargetX - this.m_20185_()) / (double)this.newPosRotationIncrements;
         double d2 = this.m_20186_() + (this.interpTargetY - this.m_20186_()) / (double)this.newPosRotationIncrements;
         double d4 = this.m_20189_() + (this.interpTargetZ - this.m_20189_()) / (double)this.newPosRotationIncrements;
         double d6 = Mth.m_14175_(this.interpTargetYaw - (double)this.m_146908_());
         this.m_146922_((float)((double)this.m_146908_() + d6 / (double)this.newPosRotationIncrements));
         this.m_146926_((float)((double)this.m_146909_() + (this.interpTargetPitch - (double)this.m_146909_()) / (double)this.newPosRotationIncrements));
         this.newPosRotationIncrements--;
         this.m_6034_(d0, d2, d4);
         this.m_19915_(this.m_146908_(), this.m_146909_());
      }

      while (this.m_146908_() - this.f_19859_ < -180.0F) {
         this.f_19859_ -= 360.0F;
      }

      while (this.m_146908_() - this.f_19859_ >= 180.0F) {
         this.f_19859_ += 360.0F;
      }

      while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
         this.prevRenderYawOffset -= 360.0F;
      }

      while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
         this.prevRenderYawOffset += 360.0F;
      }

      while (this.m_146909_() - this.f_19860_ < -180.0F) {
         this.f_19860_ -= 360.0F;
      }

      while (this.m_146909_() - this.f_19860_ >= 180.0F) {
         this.f_19860_ += 360.0F;
      }
   }

   public final void updateLastPos() {
      this.m_6027_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      this.f_19859_ = this.m_146908_();
      this.f_19860_ = this.m_146909_();
      this.f_19797_++;
   }

   protected void setSize(EntityDimensions size) {
      this.realSize = size;
      this.m_6210_();
   }

   public boolean m_142038_() {
      return this.getParent().m_142038_();
   }

   public boolean m_20145_() {
      return this.getParent().m_20145_();
   }

   public EntityDimensions m_6972_(Pose pose) {
      return this.realSize;
   }

   public boolean m_6087_() {
      return true;
   }

   public void m_20234_(int id) {
      super.m_20234_(id + 1);
   }

   public MessageCMMultipart.PartDataHolder writeData() {
      return new MessageCMMultipart.PartDataHolder(
         this.m_20185_(),
         this.m_20186_(),
         this.m_20189_(),
         this.m_146908_(),
         this.m_146909_(),
         this.dimensions.f_20377_,
         this.dimensions.f_20378_,
         this.dimensions.f_20379_,
         this.m_20088_().m_135352_(),
         this.m_20088_().m_135352_() ? this.m_20088_().m_135378_() : null
      );
   }

   public void readData(MessageCMMultipart.PartDataHolder data) {
      Vec3 vec = new Vec3(data.x(), data.y(), data.z());
      this.setPositionAndRotationDirect(vec.m_7096_(), vec.m_7098_(), vec.m_7094_(), data.yRot(), data.xRot(), 3);
      float w = data.width();
      float h = data.height();
      this.setSize(data.fixed() ? EntityDimensions.m_20398_(w, h) : EntityDimensions.m_20395_(w, h));
      this.m_6210_();
      if (data.dirty()) {
         this.m_20088_().m_135356_(data.data());
      }
   }

   public static void assignPartIDs(Entity parent) {
      PartEntity<?>[] parts = parent.getParts();
      int i = 0;

      for (int partsLength = ((PartEntity[])Objects.<PartEntity<?>[]>requireNonNull(parts)).length; i < partsLength; i++) {
         PartEntity<?> part = parts[i];
         part.m_20234_(parent.m_19879_() + i);
      }
   }
}
