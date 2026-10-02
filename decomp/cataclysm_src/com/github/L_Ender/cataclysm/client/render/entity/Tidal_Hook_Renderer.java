package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Tidal_Hook_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Hook_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class Tidal_Hook_Renderer extends EntityRenderer<Tidal_Hook_Entity> {
   private final Tidal_Hook_Model model = new Tidal_Hook_Model();
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm:textures/entity/tidal_hook.png");
   private static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation("cataclysm:textures/entity/tidal_hook_chain.png");
   private static final RenderType CHAIN_LAYER = RenderType.m_110476_(CHAIN_TEXTURE);

   public Tidal_Hook_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(Tidal_Hook_Entity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource provider, int light) {
      matrices.m_85836_();
      matrices.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(tickDelta, entity.f_19859_, entity.m_146908_()) - 90.0F));
      matrices.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(tickDelta, entity.f_19860_, entity.m_146909_()) + 90.0F));
      VertexConsumer vertexConsumer = provider.m_6299_(this.model.m_103119_(this.getTextureLocation(entity)));
      this.model.m_7695_(matrices, vertexConsumer, light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrices.m_85849_();
      matrices.m_85836_();
      Entity fromEntity = entity.m_37282_();
      float x = (float)Mth.m_14139_((double)tickDelta, entity.f_19854_, entity.m_20185_());
      float y = (float)Mth.m_14139_((double)tickDelta, entity.f_19855_, entity.m_20186_());
      float z = (float)Mth.m_14139_((double)tickDelta, entity.f_19856_, entity.m_20189_());
      if (fromEntity != null) {
         Vec3 distVec = this.getPositionOfPriorMob(fromEntity, tickDelta).m_82492_((double)x, (double)y, (double)z);
         renderChainCube(distVec, tickDelta, entity.f_19797_, matrices, provider, light);
      }

      matrices.m_85849_();
   }

   private Vec3 getPositionOfPriorMob(Entity mob, float partialTicks) {
      double d4 = Mth.m_14139_((double)partialTicks, mob.f_19854_, mob.m_20185_());
      double d5 = Mth.m_14139_((double)partialTicks, mob.f_19855_, mob.m_20186_());
      double d6 = Mth.m_14139_((double)partialTicks, mob.f_19856_, mob.m_20189_());
      float f3 = 0.0F;
      if (mob instanceof Player player) {
         float f = player.m_21324_(partialTicks);
         float f1 = Mth.m_14031_(Mth.m_14116_(f) * (float) Math.PI);
         float f2 = Mth.m_14179_(partialTicks, player.f_20884_, player.f_20883_) * (float) (Math.PI / 180.0);
         int i = player.m_5737_() == HumanoidArm.RIGHT ? 1 : -1;
         ItemStack itemstack = player.m_21205_();
         if (!itemstack.m_150930_((Item)ModItems.TIDAL_CLAWS.get())) {
            i = -i;
         }

         double d0 = (double)Mth.m_14031_(f2);
         double d1 = (double)Mth.m_14089_(f2);
         double d2 = (double)i * 0.35;
         if ((this.f_114476_.f_114360_ == null || this.f_114476_.f_114360_.m_92176_().m_90612_()) && player == Minecraft.m_91087_().f_91074_) {
            double d7 = 960.0 / (double)((Integer)this.f_114476_.f_114360_.m_231837_().m_231551_()).intValue();
            Vec3 vec3 = this.f_114476_.f_114358_.m_167684_().m_167695_((float)i * 0.6F, -1.0F);
            vec3 = vec3.m_82490_(d7);
            vec3 = vec3.m_82524_(f1 * 0.25F);
            vec3 = vec3.m_82496_(-f1 * 0.35F);
            d4 = Mth.m_14139_((double)partialTicks, player.f_19854_, player.m_20185_()) + vec3.f_82479_;
            d5 = Mth.m_14139_((double)partialTicks, player.f_19855_, player.m_20186_()) + vec3.f_82480_;
            d6 = Mth.m_14139_((double)partialTicks, player.f_19856_, player.m_20189_()) + vec3.f_82481_;
            f3 = player.m_20192_() * 0.5F;
         } else {
            d4 = Mth.m_14139_((double)partialTicks, player.f_19854_, player.m_20185_()) - d1 * d2 - d0 * 0.2;
            d5 = player.f_19855_ + (double)player.m_20192_() + (player.m_20186_() - player.f_19855_) * (double)partialTicks - 0.45;
            d6 = Mth.m_14139_((double)partialTicks, player.f_19856_, player.m_20189_()) - d0 * d2 + d1 * 0.2;
            f3 = player.m_6047_() ? -0.1875F : 0.0F;
         }
      }

      return new Vec3(d4, d5 + (double)f3, d6);
   }

   public static void renderChainCube(Vec3 from, float tickDelta, int age, PoseStack stack, MultiBufferSource provider, int light) {
      float lengthXY = Mth.m_14116_((float)(from.f_82479_ * from.f_82479_ + from.f_82481_ * from.f_82481_));
      float squaredLength = (float)(from.f_82479_ * from.f_82479_ + from.f_82480_ * from.f_82480_ + from.f_82481_ * from.f_82481_);
      float length = Mth.m_14116_(squaredLength);
      stack.m_85836_();
      stack.m_85845_(Vector3f.f_122225_.m_122270_((float)(-Math.atan2(from.f_82481_, from.f_82479_)) - (float) (Math.PI / 2)));
      stack.m_85845_(Vector3f.f_122223_.m_122270_((float)(-Math.atan2((double)lengthXY, from.f_82480_)) - (float) (Math.PI / 2)));
      stack.m_85845_(Vector3f.f_122227_.m_122240_(25.0F));
      stack.m_85836_();
      stack.m_85837_(0.015, -0.2, 0.0);
      VertexConsumer vertexConsumer = provider.m_6299_(CHAIN_LAYER);
      float vertX1 = 0.0F;
      float vertY1 = 0.25F;
      float vertX2 = Mth.m_14031_((float) (Math.PI * 2)) * 0.125F;
      float vertY2 = Mth.m_14089_((float) (Math.PI * 2)) * 0.125F;
      float minU = 0.0F;
      float maxU = 0.1875F;
      float minV = 0.0F - ((float)age + tickDelta) * 0.01F;
      float maxV = Mth.m_14116_(squaredLength) / 8.0F - ((float)age + tickDelta) * 0.01F;
      Pose entry = stack.m_85850_();
      Matrix4f matrix4f = entry.m_85861_();
      Matrix3f matrix3f = entry.m_85864_();
      vertexConsumer.m_85982_(matrix4f, vertX1, vertY1, 0.0F)
         .m_6122_(0, 0, 0, 255)
         .m_7421_(minU, minV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      vertexConsumer.m_85982_(matrix4f, vertX1, vertY1, length)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(minU, maxV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      vertexConsumer.m_85982_(matrix4f, vertX2, vertY2, length)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(maxU, maxV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      vertexConsumer.m_85982_(matrix4f, vertX2, vertY2, 0.0F)
         .m_6122_(0, 0, 0, 255)
         .m_7421_(maxU, minV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      stack.m_85849_();
      stack.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
      stack.m_85837_(-0.015, -0.2, 0.0);
      entry = stack.m_85850_();
      matrix4f = entry.m_85861_();
      matrix3f = entry.m_85864_();
      vertexConsumer.m_85982_(matrix4f, vertX1, vertY1, 0.0F)
         .m_6122_(0, 0, 0, 255)
         .m_7421_(minU, minV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      vertexConsumer.m_85982_(matrix4f, vertX1, vertY1, length)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(minU, maxV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      vertexConsumer.m_85982_(matrix4f, vertX2, vertY2, length)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(maxU, maxV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      vertexConsumer.m_85982_(matrix4f, vertX2, vertY2, 0.0F)
         .m_6122_(0, 0, 0, 255)
         .m_7421_(maxU, minV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      stack.m_85849_();
   }

   public ResourceLocation getTextureLocation(Tidal_Hook_Entity entity) {
      return TEXTURE;
   }
}
