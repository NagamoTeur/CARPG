package io.redspace.ironsspellbooks.entity.spells.magic_missile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.spells.fireball.FireballRenderer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class MagicMissileRenderer extends EntityRenderer<MagicMissileProjectile> {
   private static final ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/magic_missile/magic_missile.png");
   private static final ResourceLocation[] FIRE_TEXTURES = new ResourceLocation[]{
      IronsSpellbooks.id("textures/entity/magic_missile/fire_1.png"),
      IronsSpellbooks.id("textures/entity/magic_missile/fire_2.png"),
      IronsSpellbooks.id("textures/entity/magic_missile/fire_3.png"),
      IronsSpellbooks.id("textures/entity/magic_missile/fire_4.png")
   };
   private final ModelPart body;
   protected final ModelPart outline;

   public MagicMissileRenderer(Context context) {
      super(context);
      ModelPart modelpart = context.m_174023_(FireballRenderer.MODEL_LAYER_LOCATION);
      this.body = modelpart.m_171324_("body");
      this.outline = modelpart.m_171324_("outline");
   }

   public void render(MagicMissileProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Vec3 motion = entity.m_20184_();
      float xRot = -((float)(Mth.m_14136_(motion.m_165924_(), motion.f_82480_) * 180.0F / (float)Math.PI) - 90.0F);
      float yRot = -((float)(Mth.m_14136_(motion.f_82481_, motion.f_82479_) * 180.0F / (float)Math.PI) + 90.0F);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(yRot));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(xRot));
      poseStack.m_85841_(0.35F, 0.35F, 0.45F);
      VertexConsumer consumer = bufferSource.m_6299_(this.renderType(this.getTextureLocation(entity)));
      this.body.m_104306_(poseStack, consumer, 15728880, OverlayTexture.f_118083_, 0.8F, 0.8F, 0.8F, 1.0F);
      poseStack.m_85841_(0.8F, 0.8F, 0.8F);
      poseStack.m_85837_(0.0, 0.0, 0.4F);
      consumer = bufferSource.m_6299_(this.renderType(this.getFireTextureLocation(entity)));
      this.outline.m_104306_(poseStack, consumer, 15728880, OverlayTexture.f_118083_, 0.8F, 0.8F, 0.8F, 1.0F);
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   public RenderType renderType(ResourceLocation TEXTURE) {
      return RenderType.m_110436_(TEXTURE, 0.0F, 0.0F);
   }

   public ResourceLocation getTextureLocation(MagicMissileProjectile entity) {
      return TEXTURE;
   }

   public ResourceLocation getFireTextureLocation(Projectile entity) {
      int frame = entity.f_19797_ % FIRE_TEXTURES.length;
      return FIRE_TEXTURES[frame];
   }
}
