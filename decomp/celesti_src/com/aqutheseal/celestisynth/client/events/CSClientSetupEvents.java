package com.aqutheseal.celestisynth.client.events;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.client.gui.celestialcrafting.CelestialCraftingScreen;
import com.aqutheseal.celestisynth.client.particles.BreezebrokenParticle;
import com.aqutheseal.celestisynth.client.particles.RainfallBeamParticle;
import com.aqutheseal.celestisynth.client.particles.RainfallEnergyParticle;
import com.aqutheseal.celestisynth.client.renderers.blockentity.CelestialCraftingTableBlockEntityRenderer;
import com.aqutheseal.celestisynth.client.renderers.entity.boss.TempestBossRenderer;
import com.aqutheseal.celestisynth.client.renderers.entity.projectile.RainfallArrowRenderer;
import com.aqutheseal.celestisynth.client.renderers.misc.CSEffectEntityRenderer;
import com.aqutheseal.celestisynth.client.renderers.misc.NullRenderer;
import com.aqutheseal.celestisynth.common.item.weapons.RainfallSerenityItem;
import com.aqutheseal.celestisynth.common.registry.CSBlockEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSMenuTypes;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CSClientSetupEvents {
   @SubscribeEvent
   public static void onRegisterRenderersEvent(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)CSEntityTypes.TEMPEST.get(), TempestBossRenderer::new);
      event.registerEntityRenderer((EntityType)CSEntityTypes.CS_EFFECT.get(), CSEffectEntityRenderer::new);
      event.registerEntityRenderer((EntityType)CSEntityTypes.CRESCENTIA_RANGED.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)CSEntityTypes.BREEZEBREAKER_TORNADO.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)CSEntityTypes.POLTERGEIST_WARD.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)CSEntityTypes.RAINFALL_RAIN.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)CSEntityTypes.RAINFALL_ARROW.get(), RainfallArrowRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)CSBlockEntityTypes.CELESTIAL_CRAFTING_TABLE_TILE.get(), CelestialCraftingTableBlockEntityRenderer::new);
   }

   @SubscribeEvent
   public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
      event.enqueueWork(
         () -> {
            ItemProperties.register(
               (Item)CSItems.SOLARIS.get(),
               Celestisynth.prefix("soul"),
               (stack, level, living, id) -> living != null
                        && stack.m_41782_()
                        && stack.m_41737_("csController") != null
                        && stack.m_41737_("csController").m_128471_("cs.hasStartedSoulDash")
                     ? 1.0F
                     : 0.0F
            );
            ItemProperties.register(
               (Item)CSItems.POLTERGEIST.get(),
               Celestisynth.prefix("haunted"),
               (stack, level, living, id) -> living != null
                        && stack.m_41782_()
                        && stack.m_41737_("csExtras") != null
                        && stack.m_41737_("csExtras").m_128471_("cs.isImpactLarge")
                     ? 1.0F
                     : 0.0F
            );
            ItemProperties.register(
               (Item)CSItems.AQUAFLORA.get(),
               Celestisynth.prefix("blooming"),
               (stack, level, living, id) -> living != null
                        && stack.m_41782_()
                        && stack.m_41737_("csController") != null
                        && stack.m_41737_("csController").m_128471_("cs.checkPassiveIfBlooming")
                     ? 1.0F
                     : 0.0F
            );
            ItemProperties.register(
               (Item)CSItems.RAINFALL_SERENITY.get(),
               new ResourceLocation("pull"),
               (stack, level, living, id) -> living != null && stack.m_41720_() instanceof RainfallSerenityItem
                     ? (
                        living.m_21211_() != stack
                           ? 0.0F
                           : (float)(stack.m_41779_() - living.m_21212_()) / ((RainfallSerenityItem)stack.m_41720_()).getDrawSpeed(stack)
                     )
                     : 0.0F
            );
            ItemProperties.register(
               (Item)CSItems.RAINFALL_SERENITY.get(),
               new ResourceLocation("pulling"),
               (stack, level, living, id) -> living != null && living.m_6117_() && living.m_21211_() == stack ? 1.0F : 0.0F
            );
            MenuScreens.m_96206_((MenuType)CSMenuTypes.CELESTIAL_CRAFTING.get(), CelestialCraftingScreen::new);
         }
      );
   }

   @SubscribeEvent
   public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
      event.register((ParticleType)CSParticleTypes.BREEZEBROKEN.get(), BreezebrokenParticle.Provider::new);
      event.register((ParticleType)CSParticleTypes.RAINFALL_BEAM.get(), RainfallBeamParticle.Provider::new);
      event.register((ParticleType)CSParticleTypes.RAINFALL_BEAM_QUASAR.get(), RainfallBeamParticle.Quasar.Provider::new);
      event.register((ParticleType)CSParticleTypes.RAINFALL_ENERGY.get(), RainfallEnergyParticle.Provider::new);
      event.register((ParticleType)CSParticleTypes.RAINFALL_ENERGY_SMALL.get(), RainfallEnergyParticle.Small.Provider::new);
   }
}
