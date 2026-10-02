package net.thirdlife.iterrpg.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.network.SetBonusToggleKeyMessage;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class IterRpgModKeyMappings {
   public static final KeyMapping SET_BONUS_TOGGLE_KEY = new KeyMapping("key.iter_rpg.set_bonus_toggle_key", 86, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            IterRpgMod.PACKET_HANDLER.sendToServer(new SetBonusToggleKeyMessage(0, 0));
            SetBonusToggleKeyMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };

   @SubscribeEvent
   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(SET_BONUS_TOGGLE_KEY);
   }

   @EventBusSubscriber({Dist.CLIENT})
   public static class KeyEventListener {
      @SubscribeEvent
      public static void onClientTick(ClientTickEvent event) {
         if (Minecraft.m_91087_().f_91080_ == null) {
            IterRpgModKeyMappings.SET_BONUS_TOGGLE_KEY.m_90859_();
         }
      }
   }
}
