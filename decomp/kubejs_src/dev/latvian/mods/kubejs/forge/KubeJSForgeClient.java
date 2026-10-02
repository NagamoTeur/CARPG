package dev.latvian.mods.kubejs.forge;

import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.bindings.event.ClientEvents;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.client.AtlasSpriteRegistryEventJS;
import dev.latvian.mods.kubejs.client.BlockTintFunctionWrapper;
import dev.latvian.mods.kubejs.client.ItemTintFunctionWrapper;
import dev.latvian.mods.kubejs.fluid.FluidBucketItemBuilder;
import dev.latvian.mods.kubejs.fluid.FluidBuilder;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Item;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class KubeJSForgeClient {
   public KubeJSForgeClient() {
      FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.LOW, this::setupClient);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::blockColors);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::itemColors);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::textureStitch);
   }

   private void setupClient(FMLClientSetupEvent event) {
      KubeJS.PROXY.clientSetup();

      for (BuilderBase<?> builder : RegistryInfo.BLOCK) {
         if (builder instanceof BlockBuilder b) {
            String var5 = b.renderType;
            switch (var5) {
               case "cutout":
                  ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.m_110463_());
                  break;
               case "cutout_mipped":
                  ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.m_110457_());
                  break;
               case "translucent":
                  ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.m_110466_());
            }
         }
      }

      for (BuilderBase<?> builderx : RegistryInfo.FLUID) {
         if (builderx instanceof FluidBuilder b) {
            String var10 = b.renderType;
            switch (var10) {
               case "cutout":
                  ItemBlockRenderTypes.setRenderLayer(b.get().m_5613_(), RenderType.m_110463_());
                  ItemBlockRenderTypes.setRenderLayer(b.get().m_5615_(), RenderType.m_110463_());
                  break;
               case "cutout_mipped":
                  ItemBlockRenderTypes.setRenderLayer(b.get().m_5613_(), RenderType.m_110457_());
                  ItemBlockRenderTypes.setRenderLayer(b.get().m_5615_(), RenderType.m_110457_());
                  break;
               case "translucent":
                  ItemBlockRenderTypes.setRenderLayer(b.get().m_5613_(), RenderType.m_110466_());
                  ItemBlockRenderTypes.setRenderLayer(b.get().m_5615_(), RenderType.m_110466_());
            }
         }
      }
   }

   private void blockColors(Block event) {
      for (BuilderBase<?> builder : RegistryInfo.BLOCK) {
         if (builder instanceof BlockBuilder) {
            BlockBuilder b = (BlockBuilder)builder;
            if (b.tint != null) {
               event.register(new BlockTintFunctionWrapper(b.tint), new net.minecraft.world.level.block.Block[]{b.get()});
            }
         }
      }
   }

   private void itemColors(Item event) {
      for (BuilderBase<?> builder : RegistryInfo.ITEM) {
         if (builder instanceof ItemBuilder b && b.tint != null) {
            event.register(new ItemTintFunctionWrapper(b.tint), new ItemLike[]{(ItemLike)b.get()});
         }

         if (builder instanceof FluidBucketItemBuilder b && b.fluidBuilder.bucketColor != -1) {
            event.register((stack, index) -> index == 1 ? b.fluidBuilder.bucketColor : -1, new ItemLike[]{(ItemLike)b.get()});
         }
      }
   }

   private void textureStitch(Pre event) {
      ClientEvents.ATLAS_SPRITE_REGISTRY.post(new AtlasSpriteRegistryEventJS(event::addSprite), event.getAtlas().m_118330_());
   }
}
