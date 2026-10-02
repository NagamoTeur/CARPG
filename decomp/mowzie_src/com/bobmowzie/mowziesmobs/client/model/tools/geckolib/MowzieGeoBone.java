package com.bobmowzie.mowziesmobs.client.model.tools.geckolib;

import com.mojang.math.Vector3d;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class MowzieGeoBone extends GeoBone {
   protected boolean forceMatrixTransform = false;

   public MowzieGeoBone getParent() {
      return (MowzieGeoBone)this.parent;
   }

   public void addPosition(Vec3 vec) {
      this.addPosition((float)vec.m_7096_(), (float)vec.m_7098_(), (float)vec.m_7094_());
   }

   public void addPosition(float x, float y, float z) {
      this.addPositionX(x);
      this.addPositionY(y);
      this.addPositionZ(z);
   }

   public void addPositionX(float x) {
      this.setPositionX(this.getPositionX() + x);
   }

   public void addPositionY(float y) {
      this.setPositionY(this.getPositionY() + y);
   }

   public void addPositionZ(float z) {
      this.setPositionZ(this.getPositionZ() + z);
   }

   public void setPosition(Vec3 vec) {
      this.setPosition((float)vec.m_7096_(), (float)vec.m_7098_(), (float)vec.m_7094_());
   }

   public void setPosition(float x, float y, float z) {
      this.setPositionX(x);
      this.setPositionY(y);
      this.setPositionZ(z);
   }

   public Vector3d getPosition() {
      return new Vector3d((double)this.getPositionX(), (double)this.getPositionY(), (double)this.getPositionZ());
   }

   public void addRotation(Vec3 vec) {
      this.addRotation((float)vec.m_7096_(), (float)vec.m_7098_(), (float)vec.m_7094_());
   }

   public void addRotation(float x, float y, float z) {
      this.addRotationX(x);
      this.addRotationY(y);
      this.addRotationZ(z);
   }

   public void addRotationX(float x) {
      this.setRotationX(this.getRotationX() + x);
   }

   public void addRotationY(float y) {
      this.setRotationY(this.getRotationY() + y);
   }

   public void addRotationZ(float z) {
      this.setRotationZ(this.getRotationZ() + z);
   }

   public void setRotation(Vec3 vec) {
      this.setRotation((float)vec.m_7096_(), (float)vec.m_7098_(), (float)vec.m_7094_());
   }

   public void setRotation(float x, float y, float z) {
      this.setRotationX(x);
      this.setRotationY(y);
      this.setRotationZ(z);
   }

   public Vector3d getRotation() {
      return new Vector3d((double)this.getRotationX(), (double)this.getRotationY(), (double)this.getRotationZ());
   }

   public void multiplyScale(Vec3 vec) {
      this.multiplyScale((float)vec.m_7096_(), (float)vec.m_7098_(), (float)vec.m_7094_());
   }

   public void multiplyScale(float x, float y, float z) {
      this.setScaleX(this.getScaleX() * x);
      this.setScaleY(this.getScaleY() * y);
      this.setScaleZ(this.getScaleZ() * z);
   }

   public void setScale(Vec3 vec) {
      this.setScale((float)vec.m_7096_(), (float)vec.m_7098_(), (float)vec.m_7094_());
   }

   public void setScale(float x, float y, float z) {
      this.setScaleX(x);
      this.setScaleY(y);
      this.setScaleZ(z);
   }

   public Vector3d getScale() {
      return new Vector3d((double)this.getScaleX(), (double)this.getScaleY(), (double)this.getScaleZ());
   }

   public void addRotationOffsetFromBone(MowzieGeoBone source) {
      this.setRotationX(this.getRotationX() + source.getRotationX() - source.getInitialSnapshot().rotationValueX);
      this.setRotationY(this.getRotationY() + source.getRotationY() - source.getInitialSnapshot().rotationValueY);
      this.setRotationZ(this.getRotationZ() + source.getRotationZ() - source.getInitialSnapshot().rotationValueZ);
   }

   public void setForceMatrixTransform(boolean forceMatrixTransform) {
      this.forceMatrixTransform = forceMatrixTransform;
   }

   public boolean isForceMatrixTransform() {
      return this.forceMatrixTransform;
   }
}
