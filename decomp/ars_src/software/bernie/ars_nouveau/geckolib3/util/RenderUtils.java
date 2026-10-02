package software.bernie.ars_nouveau.geckolib3.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoCube;

public final class RenderUtils {
   @Deprecated(
      forRemoval = true
   )
   public static void moveToPivot(GeoCube cube, PoseStack stack) {
      translateToPivotPoint(stack, cube);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void moveBackFromPivot(GeoCube cube, PoseStack stack) {
      translateAwayFromPivotPoint(stack, cube);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void moveToPivot(GeoBone bone, PoseStack stack) {
      translateToPivotPoint(stack, bone);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void moveBackFromPivot(GeoBone bone, PoseStack stack) {
      translateAwayFromPivotPoint(stack, bone);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void scale(GeoBone bone, PoseStack stack) {
      scaleMatrixForBone(stack, bone);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void translate(GeoBone bone, PoseStack stack) {
      translateMatrixToBone(stack, bone);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void rotate(GeoBone bone, PoseStack stack) {
      rotateMatrixAroundBone(stack, bone);
   }

   @Deprecated(
      forRemoval = true
   )
   public static void rotate(GeoCube bone, PoseStack stack) {
      rotateMatrixAroundCube(stack, bone);
   }

   public static void translateMatrixToBone(PoseStack poseStack, GeoBone bone) {
      poseStack.m_85837_((double)(-bone.getPositionX() / 16.0F), (double)(bone.getPositionY() / 16.0F), (double)(bone.getPositionZ() / 16.0F));
   }

   public static void rotateMatrixAroundBone(PoseStack poseStack, GeoBone bone) {
      if (bone.getRotationZ() != 0.0F) {
         poseStack.m_85845_(Vector3f.f_122227_.m_122270_(bone.getRotationZ()));
      }

      if (bone.getRotationY() != 0.0F) {
         poseStack.m_85845_(Vector3f.f_122225_.m_122270_(bone.getRotationY()));
      }

      if (bone.getRotationX() != 0.0F) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122270_(bone.getRotationX()));
      }
   }

   public static void rotateMatrixAroundCube(PoseStack poseStack, GeoCube cube) {
      Vector3f rotation = cube.rotation;
      poseStack.m_85845_(new Quaternion(0.0F, 0.0F, rotation.m_122269_(), false));
      poseStack.m_85845_(new Quaternion(0.0F, rotation.m_122260_(), 0.0F, false));
      poseStack.m_85845_(new Quaternion(rotation.m_122239_(), 0.0F, 0.0F, false));
   }

   public static void scaleMatrixForBone(PoseStack poseStack, GeoBone bone) {
      poseStack.m_85841_(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
   }

   public static void translateToPivotPoint(PoseStack poseStack, GeoCube cube) {
      Vector3f pivot = cube.pivot;
      poseStack.m_85837_((double)(pivot.m_122239_() / 16.0F), (double)(pivot.m_122260_() / 16.0F), (double)(pivot.m_122269_() / 16.0F));
   }

   public static void translateToPivotPoint(PoseStack poseStack, GeoBone bone) {
      poseStack.m_85837_((double)(bone.rotationPointX / 16.0F), (double)(bone.rotationPointY / 16.0F), (double)(bone.rotationPointZ / 16.0F));
   }

   public static void translateAwayFromPivotPoint(PoseStack poseStack, GeoCube cube) {
      Vector3f pivot = cube.pivot;
      poseStack.m_85837_((double)(-pivot.m_122239_() / 16.0F), (double)(-pivot.m_122260_() / 16.0F), (double)(-pivot.m_122269_() / 16.0F));
   }

   public static void translateAwayFromPivotPoint(PoseStack poseStack, GeoBone bone) {
      poseStack.m_85837_((double)(-bone.rotationPointX / 16.0F), (double)(-bone.rotationPointY / 16.0F), (double)(-bone.rotationPointZ / 16.0F));
   }

   public static void translateAndRotateMatrixForBone(PoseStack poseStack, GeoBone bone) {
      translateToPivotPoint(poseStack, bone);
      rotateMatrixAroundBone(poseStack, bone);
   }

   public static void prepMatrixForBone(PoseStack poseStack, GeoBone bone) {
      translateMatrixToBone(poseStack, bone);
      translateToPivotPoint(poseStack, bone);
      rotateMatrixAroundBone(poseStack, bone);
      scaleMatrixForBone(poseStack, bone);
      translateAwayFromPivotPoint(poseStack, bone);
   }

   @Nullable
   public static IntIntPair getTextureDimensions(ResourceLocation texture) {
      if (texture == null) {
         return null;
      } else {
         AbstractTexture originalTexture = null;
         Minecraft mc = Minecraft.m_91087_();

         try {
            originalTexture = (AbstractTexture)mc.m_18691_(() -> mc.m_91097_().m_118506_(texture)).get();
         } catch (Exception var6) {
            GeckoLib.LOGGER.warn("Failed to load image for id {}", texture);
            var6.printStackTrace();
         }

         if (originalTexture == null) {
            return null;
         } else {
            NativeImage image = null;

            try {
               image = originalTexture instanceof DynamicTexture dynamicTexture
                  ? dynamicTexture.m_117991_()
                  : NativeImage.m_85058_(((Resource)mc.m_91098_().m_213713_(texture).get()).m_215507_());
            } catch (Exception var5) {
               GeckoLib.LOGGER.error("Failed to read image for id {}", texture);
               var5.printStackTrace();
            }

            return image == null ? null : IntIntImmutablePair.of(image.m_84982_(), image.m_85084_());
         }
      }
   }

   public static Matrix4f invertAndMultiplyMatrices(Matrix4f baseMatrix, Matrix4f inputMatrix) {
      inputMatrix = inputMatrix.m_27658_();
      inputMatrix.m_27657_();
      inputMatrix.m_27644_(baseMatrix);
      return inputMatrix;
   }
}
