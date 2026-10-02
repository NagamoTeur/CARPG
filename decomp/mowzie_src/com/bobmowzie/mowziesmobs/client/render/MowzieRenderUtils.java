package com.bobmowzie.mowziesmobs.client.render;

import com.bobmowzie.mowziesmobs.client.model.tools.ModelPartMatrix;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;

public class MowzieRenderUtils {
   public static void matrixStackFromModel(PoseStack matrixStack, AdvancedModelRenderer modelRenderer) {
      AdvancedModelRenderer parent = modelRenderer.getParent();
      if (parent != null) {
         matrixStackFromModel(matrixStack, parent);
      }

      modelRenderer.translateRotate(matrixStack);
   }

   public static Vec3 getWorldPosFromModel(Entity entity, float entityYaw, AdvancedModelRenderer modelRenderer) {
      PoseStack matrixStack = new PoseStack();
      matrixStack.m_85837_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      matrixStack.m_85845_(new Quaternion(0.0F, -entityYaw + 180.0F, 0.0F, true));
      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStack.m_85837_(0.0, -1.5, 0.0);
      matrixStackFromModel(matrixStack, modelRenderer);
      Pose matrixEntry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixEntry.m_85861_();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix4f);
      return new Vec3((double)vec.m_123601_(), (double)vec.m_123615_(), (double)vec.m_123616_());
   }

   public static void translateRotateGeckolib(GeoBone bone, PoseStack matrixStackIn) {
      matrixStackIn.m_85837_((double)(bone.rotationPointX / 16.0F), (double)(bone.rotationPointY / 16.0F), (double)(bone.rotationPointZ / 16.0F));
      if (bone.getRotationZ() != 0.0F) {
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122270_(bone.getRotationZ()));
      }

      if (bone.getRotationY() != 0.0F) {
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122270_(bone.getRotationY()));
      }

      if (bone.getRotationX() != 0.0F) {
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122270_(bone.getRotationX()));
      }

      matrixStackIn.m_85841_(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
   }

   public static void matrixStackFromModel(PoseStack matrixStack, GeoBone geoBone) {
      GeoBone parent = geoBone.parent;
      if (parent != null) {
         matrixStackFromModel(matrixStack, parent);
      }

      translateRotateGeckolib(geoBone, matrixStack);
   }

   public static Vec3 getWorldPosFromModel(Entity entity, float entityYaw, GeoBone geoBone) {
      PoseStack matrixStack = new PoseStack();
      matrixStack.m_85837_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      matrixStack.m_85845_(new Quaternion(0.0F, -entityYaw + 180.0F, 0.0F, true));
      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStack.m_85837_(0.0, -1.5, 0.0);
      matrixStackFromModel(matrixStack, geoBone);
      Pose matrixEntry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixEntry.m_85861_();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix4f);
      return new Vec3((double)vec.m_123601_(), (double)vec.m_123615_(), (double)vec.m_123616_());
   }

   public static void moveToPivotMirror(GeoCube cube, PoseStack stack) {
      Vector3f pivot = cube.pivot;
      stack.m_85837_((double)(-pivot.m_122239_() / 16.0F), (double)(pivot.m_122260_() / 16.0F), (double)(pivot.m_122269_() / 16.0F));
   }

   public static void moveBackFromPivotMirror(GeoCube cube, PoseStack stack) {
      Vector3f pivot = cube.pivot;
      stack.m_85837_((double)(pivot.m_122239_() / 16.0F), (double)(-pivot.m_122260_() / 16.0F), (double)(-pivot.m_122269_() / 16.0F));
   }

   public static void moveToPivotMirror(GeoBone bone, PoseStack stack) {
      stack.m_85837_((double)(-bone.rotationPointX / 16.0F), (double)(bone.rotationPointY / 16.0F), (double)(bone.rotationPointZ / 16.0F));
   }

   public static void moveBackFromPivotMirror(GeoBone bone, PoseStack stack) {
      stack.m_85837_((double)(bone.rotationPointX / 16.0F), (double)(-bone.rotationPointY / 16.0F), (double)(-bone.rotationPointZ / 16.0F));
   }

   public static void translateMirror(GeoBone bone, PoseStack stack) {
      stack.m_85837_((double)(bone.getPositionX() / 16.0F), (double)(bone.getPositionY() / 16.0F), (double)(bone.getPositionZ() / 16.0F));
   }

   public static void rotateMirror(GeoBone bone, PoseStack stack) {
      if (bone.getRotationZ() != 0.0F) {
         stack.m_85845_(Vector3f.f_122227_.m_122270_(-bone.getRotationZ()));
      }

      if (bone.getRotationY() != 0.0F) {
         stack.m_85845_(Vector3f.f_122225_.m_122270_(-bone.getRotationY()));
      }

      if (bone.getRotationX() != 0.0F) {
         stack.m_85845_(Vector3f.f_122223_.m_122270_(bone.getRotationX()));
      }
   }

   public static void rotateMirror(GeoCube bone, PoseStack stack) {
      Vector3f rotation = bone.rotation;
      stack.m_85845_(new Quaternion(0.0F, 0.0F, -rotation.m_122269_(), false));
      stack.m_85845_(new Quaternion(0.0F, -rotation.m_122260_(), 0.0F, false));
      stack.m_85845_(new Quaternion(rotation.m_122239_(), 0.0F, 0.0F, false));
   }

   public static void transformStackToModelPart(PoseStack stack, ModelPartMatrix part) {
      stack.m_85850_().m_85861_().m_27624_();
      stack.m_85850_().m_85864_().m_8180_();
      stack.m_85836_();
      stack.m_85850_().m_85861_().m_27644_(part.getWorldXform());
      stack.m_85850_().m_85864_().m_8178_(part.getWorldNormal());
   }

   public static Matrix4f invertAndMultiplyMatrices(Matrix4f baseMatrix, Matrix4f inputMatrix) {
      inputMatrix = inputMatrix.m_27658_();
      invertFixed(inputMatrix);
      inputMatrix.m_27644_(baseMatrix);
      return inputMatrix;
   }

   public static boolean invertFixed(Matrix4f mat) {
      float f = mat.m_27652_();
      if (Math.abs(f) > 1.0E-6F) {
         mat.m_27630_(1.0F / f);
         return true;
      } else {
         return false;
      }
   }
}
