package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.render.entity.FrozenRenderHandler;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.SunblockLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.item.RenderSolVisageArmor;
import com.bobmowzie.mowziesmobs.client.render.item.RenderUmvuthanaMaskArmor;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.item.ItemSolVisage;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@OnlyIn(Dist.CLIENT)
public class ClientLayerRegistry {
   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public static void onAddLayers(AddLayers event) {
      List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
         ForgeRegistries.ENTITY_TYPES
            .getValues()
            .stream()
            .filter(DefaultAttributes::m_22301_)
            .map(entityType -> (EntityType)entityType)
            .collect(Collectors.toList())
      );
      entityTypes.forEach(entityType -> addLayerIfApplicable((EntityType<? extends LivingEntity>)entityType, event));

      for (String skinType : event.getSkins()) {
         event.getSkin(skinType).m_115326_(new FrozenRenderHandler.LayerFrozen(event.getSkin(skinType)));
         event.getSkin(skinType).m_115326_(new SunblockLayer(event.getSkin(skinType)));
      }

      GeoArmorRenderer.registerArmorRenderer(ItemUmvuthanaMask.class, () -> new RenderUmvuthanaMaskArmor());
      GeoArmorRenderer.registerArmorRenderer(ItemSolVisage.class, () -> new RenderSolVisageArmor());
      GeckoPlayer.GeckoPlayerThirdPerson.initRenderer();
   }

   private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, AddLayers event) {
      LivingEntityRenderer renderer = null;
      if (entityType != EntityType.f_20565_) {
         try {
            renderer = event.getRenderer(entityType);
         } catch (Exception var4) {
            if (!entityType.m_142225_().isAssignableFrom(MowzieEntity.class)) {
               MowziesMobs.LOGGER
                  .warn(
                     "Could not apply layer to " + ForgeRegistries.ENTITY_TYPES.getKey(entityType) + ", has custom renderer that is not LivingEntityRenderer."
                  );
            }
         }

         if (renderer != null) {
            renderer.m_115326_(new FrozenRenderHandler.LayerFrozen(renderer));
            renderer.m_115326_(new SunblockLayer(renderer));
         }
      }
   }
}
