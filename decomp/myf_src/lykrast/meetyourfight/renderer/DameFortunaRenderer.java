package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DameFortunaRenderer extends HumanoidMobRenderer<DameFortunaEntity, DameFortunaModel> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/dame_fortuna.png");
   private static final ResourceLocation GLOW = MeetYourFight.rl("textures/entity/dame_fortuna_glow.png");

   public DameFortunaRenderer(Context context) {
      super(context, new DameFortunaModel(context.m_174023_(DameFortunaModel.MODEL)), 0.5F);
      this.m_115326_(new GenericGlowLayer(this, GLOW));
   }

   protected void setupRotations(DameFortunaEntity entity, PoseStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
      int rage = entity.getRage();
      if (rage >= 1) {
         rotationYaw += (float)(Math.cos((double)(ageInTicks + partialTicks) * 3.25) * Math.PI * (double)rage);
      }

      super.m_7523_(entity, stack, ageInTicks, rotationYaw, partialTicks);
   }

   public ResourceLocation getTextureLocation(DameFortunaEntity entity) {
      return TEXTURE;
   }
}
