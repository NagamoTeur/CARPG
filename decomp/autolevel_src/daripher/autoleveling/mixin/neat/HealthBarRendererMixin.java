package daripher.autoleveling.mixin.neat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import daripher.autoleveling.event.MobsLevelingEvents;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.neat.HealthBarRenderer;
import vazkii.neat.NeatConfig;

@Mixin(
   value = {HealthBarRenderer.class},
   remap = false
)
public class HealthBarRendererMixin {
   @Inject(
      method = {"hookRender"},
      at = {@At("TAIL")}
   )
   private static void renderLevel(Entity entity, PoseStack poseStack, MultiBufferSource buffers, Quaternion cameraOrientation, CallbackInfo callback) {
      if (MobsLevelingEvents.hasLevel(entity)) {
         if (MobsLevelingEvents.shouldShowLevel(entity)) {
            Minecraft mc = Minecraft.m_91087_();
            int entityLevel = MobsLevelingEvents.getLevel((LivingEntity)entity) + 1;
            String name = entity.m_8077_() ? ChatFormatting.ITALIC + Objects.requireNonNull(entity.m_7770_()).getString() : entity.m_5446_().getString();
            float nameLen = (float)mc.f_91062_.m_92895_(name) * 0.5F;
            float halfSize = Math.max((float)NeatConfig.instance.plateSize(), nameLen / 2.0F + 10.0F);
            String levelText = Component.m_237110_("autoleveling.level", new Object[]{entityLevel}).getString();
            poseStack.m_85836_();
            poseStack.m_85837_(0.0, (double)entity.m_20206_() + NeatConfig.instance.heightAbove(), 0.0);
            poseStack.m_85845_(cameraOrientation);
            poseStack.m_85836_();
            poseStack.m_85841_(-0.0267F, -0.0267F, 0.0267F);
            poseStack.m_85837_((double)(halfSize - (float)mc.f_91062_.m_92895_(levelText) / 2.0F), -4.5, 0.0);
            poseStack.m_85841_(0.5F, 0.5F, 0.5F);
            mc.f_91062_.m_92811_(levelText, 0.0F, 0.0F, 16777215, false, poseStack.m_85850_().m_85861_(), buffers, false, 0, 15728880);
            poseStack.m_85849_();
            poseStack.m_85849_();
         }
      }
   }
}
