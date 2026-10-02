package immersive_armors.client;

import immersive_armors.Main;
import immersive_armors.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class OverlayRenderer {
   private static final ItemStack clock = new ItemStack(Items.f_42524_);
   private static final ItemStack compass = new ItemStack(Items.f_42522_);

   public static void renderOverlay() {
      Minecraft client = Minecraft.m_91087_();
      if (!client.f_91066_.f_92062_ && client.f_91072_ != null && client.f_91074_ != null) {
         for (ItemStack item : client.f_91074_.m_6168_()) {
            ResourceLocation id = Registry.f_122827_.m_7981_(item.m_41720_());
            if (id.equals(Main.locate("steampunk_chestplate"))) {
               renderSteampunkHud();
            }
         }
      }
   }

   private static void renderSteampunkHud() {
      Minecraft client = Minecraft.m_91087_();
      HumanoidArm arm = null;
      Player playerEntity = !(client.m_91288_() instanceof Player) ? null : (Player)client.m_91288_();
      if (playerEntity != null) {
         ItemStack itemStack = playerEntity.m_21206_();
         if (!itemStack.m_41619_()) {
            arm = playerEntity.m_5737_().m_20828_();
         }
      }

      int scaledWidth = client.m_91268_().m_85445_();
      int scaledHeight = client.m_91268_().m_85446_();
      client.m_91291_()
         .m_115203_(
            clock,
            scaledWidth / 2 + (arm == HumanoidArm.LEFT ? Config.getInstance().hudClockXOffhand : Config.getInstance().hudClockX),
            scaledHeight + Config.getInstance().hudClockY
         );
      client.m_91291_()
         .m_115203_(
            compass,
            scaledWidth / 2 + (arm == HumanoidArm.RIGHT ? Config.getInstance().hudCompassXOffhand : Config.getInstance().hudCompassX),
            scaledHeight + Config.getInstance().hudCompassY
         );
   }
}
