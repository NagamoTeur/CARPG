package com.hollingsworth.arsnouveau.client.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau",
   bus = Bus.MOD
)
@OnlyIn(Dist.CLIENT)
public class TextureEvent {
   @SubscribeEvent
   public static void textEvent(Pre event) {
      if (event.getAtlas().m_118330_().toString().equals("minecraft:textures/atlas/chest.png")) {
         ResourceLocation resNormal = new ResourceLocation("ars_nouveau", "entity/archwood_chest");
         ResourceLocation resLeft = new ResourceLocation("ars_nouveau", "entity/archwood_chest_left");
         ResourceLocation resRight = new ResourceLocation("ars_nouveau", "entity/archwood_chest_right");
         event.addSprite(resNormal);
         event.addSprite(resLeft);
         event.addSprite(resRight);
      }
   }
}
