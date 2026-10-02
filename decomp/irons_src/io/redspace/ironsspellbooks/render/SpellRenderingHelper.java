package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.spells.blood.RayOfSiphoningSpell;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellRenderingHelper {
   public static final ResourceLocation SOLID = IronsSpellbooks.id("textures/entity/ray/solid.png");
   public static final ResourceLocation BEACON = IronsSpellbooks.id("textures/entity/ray/beacon_beam.png");
   public static final ResourceLocation STRAIGHT_GLOW = IronsSpellbooks.id("textures/entity/ray/ribbon_glow.png");
   public static final ResourceLocation TWISTING_GLOW = IronsSpellbooks.id("textures/entity/ray/twisting_glow.png");

   public static void renderSpellHelper(
      SyncedSpellData spellData, LivingEntity castingMob, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks
   ) {
      if (((AbstractSpell)SpellRegistry.RAY_OF_SIPHONING_SPELL.get()).getSpellId().equals(spellData.getCastingSpellId())) {
         renderRayOfSiphoning(castingMob, poseStack, bufferSource, partialTicks);
      }
   }

   public static void renderRayOfSiphoning(LivingEntity entity, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, (double)(entity.m_20192_() * 0.8F), 0.0);
      if (entity instanceof IMagicEntity mob) {
         Vec3 dir = entity.m_20154_().m_82541_();
         double pitch = Math.asin(dir.f_82480_);
         double yaw = Math.atan2(dir.f_82479_, dir.f_82481_);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_((float)(-pitch) * (180.0F / (float)Math.PI)));
      } else {
         float f = Mth.m_14201_(entity.f_19859_, entity.m_146908_(), partialTicks);
         float f1 = Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_());
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-f));
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(f1));
      }

      Pose pose = poseStack.m_85850_();
      Vec3 start = Vec3.f_82478_;
      Vec3 impact = Utils.raycastForEntity(entity.f_19853_, entity, RayOfSiphoningSpell.getRange(0), true).m_82450_();
      float distance = (float)entity.m_146892_().m_82554_(impact);
      float radius = 0.12F;
      int r = 178;
      int g = 0;
      int b = 0;
      int a = 255;
      float deltaTicks = (float)entity.f_19797_ + partialTicks;
      float deltaUV = -deltaTicks % 10.0F;
      float max = Mth.m_14187_(deltaUV * 0.2F - (float)Mth.m_14143_(deltaUV * 0.1F));
      float min = -1.0F + max;

      for (int j = 1; (float)j <= distance; j++) {
         Vec3 wiggle = new Vec3(
            (double)(Mth.m_14031_(deltaTicks * 0.8F) * 0.02F),
            (double)(Mth.m_14031_(deltaTicks * 0.8F + 100.0F) * 0.02F),
            (double)(Mth.m_14089_(deltaTicks * 0.8F) * 0.02F)
         );
         Vec3 end = new Vec3(0.0, 0.0, (double)Math.min((float)j, distance)).m_82549_(wiggle);
         VertexConsumer inner = bufferSource.m_6299_(RenderType.m_110454_(BEACON, true));
         drawHull(start, end, radius, radius, pose, inner, r, g, b, a, min, max);
         VertexConsumer outer = bufferSource.m_6299_(RenderType.m_110473_(TWISTING_GLOW));
         drawQuad(start, end, radius * 4.0F, 0.0F, pose, outer, r, g, b, a, min, max);
         drawQuad(start, end, 0.0F, radius * 4.0F, pose, outer, r, g, b, a, min, max);
         start = end;
      }

      poseStack.m_85849_();
   }

   private static void drawHull(
      Vec3 from, Vec3 to, float width, float height, Pose pose, VertexConsumer consumer, int r, int g, int b, int a, float uvMin, float uvMax
   ) {
      drawQuad(
         from.m_82492_(0.0, (double)(height * 0.5F), 0.0),
         to.m_82492_(0.0, (double)(height * 0.5F), 0.0),
         width,
         0.0F,
         pose,
         consumer,
         r,
         g,
         b,
         a,
         uvMin,
         uvMax
      );
      drawQuad(
         from.m_82520_(0.0, (double)(height * 0.5F), 0.0),
         to.m_82520_(0.0, (double)(height * 0.5F), 0.0),
         width,
         0.0F,
         pose,
         consumer,
         r,
         g,
         b,
         a,
         uvMin,
         uvMax
      );
      drawQuad(
         from.m_82492_((double)(width * 0.5F), 0.0, 0.0), to.m_82492_((double)(width * 0.5F), 0.0, 0.0), 0.0F, height, pose, consumer, r, g, b, a, uvMin, uvMax
      );
      drawQuad(
         from.m_82520_((double)(width * 0.5F), 0.0, 0.0), to.m_82520_((double)(width * 0.5F), 0.0, 0.0), 0.0F, height, pose, consumer, r, g, b, a, uvMin, uvMax
      );
   }

   private static void drawQuad(
      Vec3 from, Vec3 to, float width, float height, Pose pose, VertexConsumer consumer, int r, int g, int b, int a, float uvMin, float uvMax
   ) {
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      float halfWidth = width * 0.5F;
      float halfHeight = height * 0.5F;
      consumer.m_85982_(poseMatrix, (float)from.f_82479_ - halfWidth, (float)from.f_82480_ - halfHeight, (float)from.f_82481_)
         .m_6122_(r, g, b, a)
         .m_7421_(0.0F, uvMin)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(240)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, (float)from.f_82479_ + halfWidth, (float)from.f_82480_ + halfHeight, (float)from.f_82481_)
         .m_6122_(r, g, b, a)
         .m_7421_(1.0F, uvMin)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(240)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, (float)to.f_82479_ + halfWidth, (float)to.f_82480_ + halfHeight, (float)to.f_82481_)
         .m_6122_(r, g, b, a)
         .m_7421_(1.0F, uvMax)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(240)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, (float)to.f_82479_ - halfWidth, (float)to.f_82480_ - halfHeight, (float)to.f_82481_)
         .m_6122_(r, g, b, a)
         .m_7421_(0.0F, uvMax)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(240)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }
}
