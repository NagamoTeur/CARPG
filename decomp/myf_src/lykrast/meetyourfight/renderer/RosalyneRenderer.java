package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RosalyneRenderer extends MobRenderer<RosalyneEntity, RosalyneModel> {
   private static final ResourceLocation BASE = MeetYourFight.rl("textures/entity/rosalyne.png");
   private static final ResourceLocation COFFIN = MeetYourFight.rl("textures/entity/rosalyne_coffin.png");
   private static final ResourceLocation CRACKED = MeetYourFight.rl("textures/entity/rosalyne_cracked.png");

   public RosalyneRenderer(Context context) {
      super(context, new RosalyneModel(context.m_174023_(RosalyneModel.MODEL)), 0.5F);
      this.m_115326_(new RosalyneGlowLayer(this));
      this.m_115326_(new RosalyneArmorLayer(this, context.m_174027_()));
   }

   protected void setupRotations(RosalyneEntity entity, PoseStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
      int phase = entity.getPhase();
      if (phase == 1 || phase == 5) {
         rotationYaw += (float)(Math.cos((double)entity.f_19797_ * 3.25) * Math.PI * 0.8);
      }

      super.m_7523_(entity, stack, ageInTicks, rotationYaw, partialTicks);
   }

   public ResourceLocation getTextureLocation(RosalyneEntity entity) {
      int phase = entity.getPhase();
      if (phase == 0 || phase == 1) {
         return COFFIN;
      } else {
         return phase == 6 ? CRACKED : BASE;
      }
   }
}
