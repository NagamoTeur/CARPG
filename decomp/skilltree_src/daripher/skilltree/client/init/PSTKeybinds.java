package daripher.skilltree.client.init;

import daripher.skilltree.client.screen.SkillTreeScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "skilltree",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class PSTKeybinds {
   private static final KeyMapping SKILL_TREE_KEY = new KeyMapping("key.display_skill_tree", 79, "key.categories.skilltree");

   @SubscribeEvent
   public static void registerKeybinds(RegisterKeyMappingsEvent event) {
      event.register(SKILL_TREE_KEY);
   }

   @EventBusSubscriber(
      modid = "skilltree",
      value = {Dist.CLIENT}
   )
   private static class KeyEvents {
      @SubscribeEvent
      public static void keyPressed(Key event) {
         Minecraft minecraft = Minecraft.m_91087_();
         if (minecraft.f_91074_ != null && minecraft.f_91080_ == null) {
            if (event.getKey() == PSTKeybinds.SKILL_TREE_KEY.getKey().m_84873_()) {
               minecraft.m_91152_(new SkillTreeScreen(new ResourceLocation("skilltree", "main_tree")));
            }
         }
      }
   }
}
