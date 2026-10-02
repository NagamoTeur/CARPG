package net.cisco.init;

import net.cisco.client.renderer.AfterImageRenderer;
import net.cisco.client.renderer.CiscoRenderer;
import net.cisco.client.renderer.DescendedCiscoRenderer;
import net.cisco.client.renderer.DragonSeekerMissileRenderer;
import net.cisco.client.renderer.FellShieldRenderer;
import net.cisco.client.renderer.FellkingbossRenderer;
import net.cisco.client.renderer.LegionnaireJotunnRenderer;
import net.cisco.client.renderer.LegionnaireKingsguardRenderer;
import net.cisco.client.renderer.SupremeNightfallAegisModeRenderer;
import net.cisco.client.renderer.VengefulAfterImageRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class CiscoModModEntityRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)CiscoModModEntities.CISCO.get(), CiscoRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.AFTER_IMAGE.get(), AfterImageRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.FELLKINGBOSS.get(), FellkingbossRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.LEGIONNAIRE_KINGSGUARD.get(), LegionnaireKingsguardRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.FELL_SHIELD.get(), FellShieldRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.LEGIONNAIRE_JOTUNN.get(), LegionnaireJotunnRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.DRAGON_SEEKER_MISSILE.get(), DragonSeekerMissileRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.DESCENDED_CISCO.get(), DescendedCiscoRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.VENGEFUL_AFTER_IMAGE.get(), VengefulAfterImageRenderer::new);
      event.registerEntityRenderer((EntityType)CiscoModModEntities.SUPREME_NIGHTFALL_AEGIS_MODE.get(), SupremeNightfallAegisModeRenderer::new);
   }
}
