package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class NodeBossBarRenderer {
   private final String entityTypeKey;
   private final List<Float> hpPercentages;
   private final ResourceLocation noteTexture;
   private final int textureSize;

   public NodeBossBarRenderer(String entityTypeKey, List<Float> hpPercentages, ResourceLocation noteTexture, int textureSize) {
      this.entityTypeKey = entityTypeKey;
      this.hpPercentages = hpPercentages;
      this.noteTexture = noteTexture;
      this.textureSize = textureSize;
   }

   public void renderBossBar(PoseStack poseStack, int x, int y, BossEvent bossEvent, CallbackInfo callbackInfo) {
      Component name = bossEvent.m_18861_();
      if (name.m_214077_() instanceof TranslatableContents translatableContents && translatableContents.m_237508_().equals(this.entityTypeKey)) {
         float colorLocation = (float)(bossEvent.m_18862_().ordinal() * 5) * 2.0F;
         GuiComponent.m_93133_(poseStack, x, y, 0.0F, colorLocation, 182, 5, this.textureSize, this.textureSize);
         int i = (int)(bossEvent.m_142717_() * 183.0F);
         if (i > 0) {
            float progressLocation = (float)(bossEvent.m_18862_().ordinal() * 5 * 2) + 5.0F;
            GuiComponent.m_93133_(poseStack, x, y, 0.0F, progressLocation, i, 5, this.textureSize, this.textureSize);
         }

         this.renderBossNodes(bossEvent, poseStack, x, y);
         callbackInfo.cancel();
      }
   }

   private void renderBossNodes(BossEvent bossEvent, PoseStack poseStack, int x, int y) {
      RenderSystem.m_157456_(0, this.noteTexture);
      int steppedPercentage = (int)(192.0F * MathUtils.roundedStep(bossEvent.m_142717_(), this.hpPercentages, true)) + 7;
      GuiComponent.m_93133_(poseStack, x - 3, y - 1, 0.0F, 0.0F, steppedPercentage, 7, this.textureSize, this.textureSize);
      int steppedPercentageReverse = 192 - steppedPercentage;
      GuiComponent.m_93133_(
         poseStack, x - 3 + steppedPercentage, y - 1, (float)steppedPercentage, 7.0F, steppedPercentageReverse, 7, this.textureSize, this.textureSize
      );
   }
}
