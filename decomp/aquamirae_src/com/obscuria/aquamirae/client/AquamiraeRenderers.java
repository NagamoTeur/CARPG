package com.obscuria.aquamirae.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.client.renderers.AnglerfishRenderer;
import com.obscuria.aquamirae.client.renderers.CaptainCorneliaRenderer;
import com.obscuria.aquamirae.client.renderers.EelRenderer;
import com.obscuria.aquamirae.client.renderers.GoldenMothRenderer;
import com.obscuria.aquamirae.client.renderers.LuminousJellyRenderer;
import com.obscuria.aquamirae.client.renderers.MawRenderer;
import com.obscuria.aquamirae.client.renderers.MazeMotherRenderer;
import com.obscuria.aquamirae.client.renderers.MazeRoseRenderer;
import com.obscuria.aquamirae.client.renderers.PoisonedChakraRenderer;
import com.obscuria.aquamirae.client.renderers.SpinefishRenderer;
import com.obscuria.aquamirae.client.renderers.TorturedSoulRenderer;
import com.obscuria.aquamirae.common.entities.PillagersPatrol;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class AquamiraeRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(@NotNull RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)AquamiraeEntities.GOLDEN_MOTH.get(), GoldenMothRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.MAW.get(), MawRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.ANGLERFISH.get(), AnglerfishRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.MAZE_MOTHER.get(), MazeMotherRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.CAPTAIN_CORNELIA.get(), CaptainCorneliaRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.TORTURED_SOUL.get(), TorturedSoulRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.EEL.get(), EelRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.MAZE_ROSE.get(), MazeRoseRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.POISONED_CHAKRA.get(), PoisonedChakraRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.SPINEFISH.get(), SpinefishRenderer::new);
      event.registerEntityRenderer((EntityType)AquamiraeEntities.LUMINOUS_JELLY.get(), LuminousJellyRenderer::new);
      event.registerEntityRenderer(
         (EntityType)AquamiraeEntities.PILLAGERS_PATROL.get(),
         provider -> new MobRenderer<PillagersPatrol, SlimeModel<PillagersPatrol>>(
               provider, new SlimeModel<PillagersPatrol>(provider.m_174023_(ModelLayers.f_171241_)) {
                  public void setupAnim(@NotNull PillagersPatrol entity, float f1, float f2, float f3, float f4, float f5) {
                  }

                  public void m_7695_(@NotNull PoseStack pose, @NotNull VertexConsumer consumer, int i1, int i2, float f1, float f2, float f3, float f4) {
                  }
               }, 0.0F
            ) {
               @NotNull
               public ResourceLocation getTextureLocation(@NotNull PillagersPatrol entity) {
                  return new ResourceLocation("aquamirae", "textures/entity/blank.png");
               }
            }
      );
   }
}
