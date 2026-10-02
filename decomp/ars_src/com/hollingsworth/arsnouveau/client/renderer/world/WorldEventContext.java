package com.hollingsworth.arsnouveau.client.renderer.world;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class WorldEventContext {
   public static final WorldEventContext INSTANCE = new WorldEventContext();
   public static BufferSource bufferSource = MultiBufferSource.m_109898_(new BufferBuilder(256));
   PoseStack poseStack;
   float partialTicks;
   ClientLevel clientLevel;
   LocalPlayer clientPlayer;
   ItemStack mainHandItem;
   int clientRenderDist;

   private WorldEventContext() {
   }

   public void renderWorldLastEvent(RenderLevelStageEvent event) {
      this.poseStack = event.getPoseStack();
   }
}
