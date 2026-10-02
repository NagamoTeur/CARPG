package net.cisco.init;

import net.cisco.CiscoModMod;
import net.cisco.network.AzureAscendantArmorAbilityMessage;
import net.cisco.network.CiscoArmorAbilityMessage;
import net.cisco.network.CrimsonAscendantMessage;
import net.cisco.network.DescendedHeroAbilityMessage;
import net.cisco.network.EagleArmorAbilityMessage;
import net.cisco.network.FallenArmorAbilityMessage;
import net.cisco.network.SovereignAbilityMessage;
import net.cisco.network.VioletAscendantAbilityMessage;
import net.cisco.network.WindwalkerPassiveMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class CiscoModModKeyMappings {
   public static final KeyMapping FALLEN_ARMOR_ABILITY = new KeyMapping("key.cisco_mod.fallen_armor_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new FallenArmorAbilityMessage(0, 0));
            FallenArmorAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping CISCO_ARMOR_ABILITY = new KeyMapping("key.cisco_mod.cisco_armor_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new CiscoArmorAbilityMessage(0, 0));
            CiscoArmorAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping WINDWALKER_PASSIVE = new KeyMapping("key.cisco_mod.windwalker_passive", 32, "key.categories.misc") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new WindwalkerPassiveMessage(0, 0));
            WindwalkerPassiveMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping EAGLE_ARMOR_ABILITY = new KeyMapping("key.cisco_mod.eagle_armor_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new EagleArmorAbilityMessage(0, 0));
            EagleArmorAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping AZURE_ASCENDANT_ARMOR_ABILITY = new KeyMapping("key.cisco_mod.azure_ascendant_armor_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new AzureAscendantArmorAbilityMessage(0, 0));
            AzureAscendantArmorAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping VIOLET_ASCENDANT_ABILITY = new KeyMapping("key.cisco_mod.violet_ascendant_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new VioletAscendantAbilityMessage(0, 0));
            VioletAscendantAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping CRIMSON_ASCENDANT = new KeyMapping("key.cisco_mod.crimson_ascendant", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new CrimsonAscendantMessage(0, 0));
            CrimsonAscendantMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping DESCENDED_HERO_ABILITY = new KeyMapping("key.cisco_mod.descended_hero_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new DescendedHeroAbilityMessage(0, 0));
            DescendedHeroAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };
   public static final KeyMapping SOVEREIGN_ABILITY = new KeyMapping("key.cisco_mod.sovereign_ability", 88, "key.categories.gameplay") {
      private boolean isDownOld = false;

      public void m_7249_(boolean isDown) {
         super.m_7249_(isDown);
         if (this.isDownOld != isDown && isDown) {
            CiscoModMod.PACKET_HANDLER.sendToServer(new SovereignAbilityMessage(0, 0));
            SovereignAbilityMessage.pressAction(Minecraft.m_91087_().f_91074_, 0, 0);
         }

         this.isDownOld = isDown;
      }
   };

   @SubscribeEvent
   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(FALLEN_ARMOR_ABILITY);
      event.register(CISCO_ARMOR_ABILITY);
      event.register(WINDWALKER_PASSIVE);
      event.register(EAGLE_ARMOR_ABILITY);
      event.register(AZURE_ASCENDANT_ARMOR_ABILITY);
      event.register(VIOLET_ASCENDANT_ABILITY);
      event.register(CRIMSON_ASCENDANT);
      event.register(DESCENDED_HERO_ABILITY);
      event.register(SOVEREIGN_ABILITY);
   }

   @EventBusSubscriber({Dist.CLIENT})
   public static class KeyEventListener {
      @SubscribeEvent
      public static void onClientTick(ClientTickEvent event) {
         if (Minecraft.m_91087_().f_91080_ == null) {
            CiscoModModKeyMappings.FALLEN_ARMOR_ABILITY.m_90859_();
            CiscoModModKeyMappings.CISCO_ARMOR_ABILITY.m_90859_();
            CiscoModModKeyMappings.WINDWALKER_PASSIVE.m_90859_();
            CiscoModModKeyMappings.EAGLE_ARMOR_ABILITY.m_90859_();
            CiscoModModKeyMappings.AZURE_ASCENDANT_ARMOR_ABILITY.m_90859_();
            CiscoModModKeyMappings.VIOLET_ASCENDANT_ABILITY.m_90859_();
            CiscoModModKeyMappings.CRIMSON_ASCENDANT.m_90859_();
            CiscoModModKeyMappings.DESCENDED_HERO_ABILITY.m_90859_();
            CiscoModModKeyMappings.SOVEREIGN_ABILITY.m_90859_();
         }
      }
   }
}
