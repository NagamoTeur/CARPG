package com.github.L_Ender.cataclysm.client.render;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class RenderUtils {
   public static void matrixStackFromCitadelModel(PoseStack matrixStack, AdvancedModelBox AdvancedModelBox) {
      AdvancedModelBox parent = AdvancedModelBox.getParent();
      if (parent != null) {
         matrixStackFromCitadelModel(matrixStack, parent);
      }

      AdvancedModelBox.translateAndRotate(matrixStack);
   }

   public static Vec3 matrixStackFromCitadelModel(Entity entity, float entityYaw, AdvancedModelBox modelRenderer) {
      PoseStack matrixStack = new PoseStack();
      matrixStack.m_85837_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      matrixStack.m_85845_(new Quaternion(0.0F, -entityYaw + 180.0F, 0.0F, true));
      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStack.m_85837_(0.0, -1.5, 0.0);
      matrixStackFromCitadelModel(matrixStack, modelRenderer);
      Pose matrixEntry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixEntry.m_85861_();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix4f);
      return new Vec3((double)vec.m_123601_(), (double)vec.m_123615_(), (double)vec.m_123616_());
   }
}
