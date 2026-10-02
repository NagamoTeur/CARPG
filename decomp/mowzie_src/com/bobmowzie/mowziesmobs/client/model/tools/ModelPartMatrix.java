package com.bobmowzie.mowziesmobs.client.model.tools;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.model.geom.ModelPart;

public class ModelPartMatrix extends ModelPart {
   private Matrix4f worldXform;
   private Matrix3f worldNormal;
   private boolean resetUseMatrixMode;
   private boolean useMatrixMode;

   public ModelPartMatrix(ModelPart original) {
      this(original, true);
   }

   public ModelPartMatrix(ModelPart original, boolean resetUseMatrixMode) {
      super(original.f_104212_, original.f_104213_);
      this.m_104315_(original);
      this.worldNormal = new Matrix3f();
      this.worldNormal.m_8180_();
      this.worldXform = new Matrix4f();
      this.worldXform.m_27624_();
      this.useMatrixMode = true;
      this.resetUseMatrixMode = resetUseMatrixMode;
   }

   public void m_104299_(PoseStack matrixStackIn) {
      if (this.useMatrixMode && this.getWorldNormal() != null && this.getWorldXform() != null) {
         Pose last = matrixStackIn.m_85850_();
         last.m_85861_().m_27624_();
         last.m_85864_().m_8180_();
         last.m_85861_().m_27644_(this.getWorldXform());
         last.m_85864_().m_8178_(this.getWorldNormal());
      } else {
         super.m_104299_(matrixStackIn);
      }

      if (this.resetUseMatrixMode) {
         this.useMatrixMode = false;
      }
   }

   public void m_104315_(ModelPart modelRendererIn) {
      if (modelRendererIn instanceof ModelPartMatrix other) {
         this.setWorldNormal(other.getWorldNormal());
         this.setWorldXform(other.getWorldXform());
      }

      super.m_104315_(modelRendererIn);
   }

   public Matrix3f getWorldNormal() {
      return this.worldNormal;
   }

   public void setWorldNormal(Matrix3f worldNormal) {
      this.worldNormal = worldNormal;
   }

   public Matrix4f getWorldXform() {
      return this.worldXform;
   }

   public void setWorldXform(Matrix4f worldXform) {
      this.worldXform = worldXform;
   }

   public void setUseMatrixMode(boolean useMatrixMode) {
      this.useMatrixMode = useMatrixMode;
   }

   public boolean isUseMatrixMode() {
      return this.useMatrixMode;
   }
}
