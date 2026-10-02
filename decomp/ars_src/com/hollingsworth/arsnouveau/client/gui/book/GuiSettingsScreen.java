package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.gui.buttons.GuiImageButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.SelectableButton;
import com.hollingsworth.arsnouveau.common.light.LightManager;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSummonLily;
import com.hollingsworth.arsnouveau.common.network.PacketUnsummonLily;
import com.hollingsworth.arsnouveau.setup.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiSettingsScreen extends BaseBook {
   public Screen parent;

   public GuiSettingsScreen(@Nullable Screen parent) {
      this.parent = parent;
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 71, this.bookBottom - 13, 0, 0, 41, 12, 41, 12, "textures/gui/clear_icon.png", e -> Minecraft.m_91087_().m_91152_(this.parent)
         )
      );
      SelectableButton dynamicButton = new SelectableButton(
         this.bookLeft + 20,
         this.bookTop + 34,
         0,
         0,
         16,
         16,
         16,
         16,
         new ResourceLocation("ars_nouveau", "textures/gui/settings_dynamic_light_off.png"),
         new ResourceLocation("ars_nouveau", "textures/gui/settings_dynamic_light_on.png"),
         b -> {
            SelectableButton button = (SelectableButton)b;
            button.isSelected = !button.isSelected;
            LightManager.toggleLightsAndConfig(!(Boolean)Config.DYNAMIC_LIGHTS_ENABLED.get());
            button.withTooltip(this, Component.m_237115_(button.isSelected ? "ars_nouveau.dynamic_lights.button_on" : "ars_nouveau.dynamic_lights.button_off"));
         }
      );
      dynamicButton.isSelected = (Boolean)Config.DYNAMIC_LIGHTS_ENABLED.get();
      dynamicButton.withTooltip(
         this, Component.m_237115_(dynamicButton.isSelected ? "ars_nouveau.dynamic_lights.button_on" : "ars_nouveau.dynamic_lights.button_off")
      );
      this.m_142416_(dynamicButton);
      if (ClientInfo.isSupporter) {
         GuiImageButton lilyButton = new GuiImageButton(
            this.bookLeft + 40,
            this.bookTop + 34,
            0,
            0,
            16,
            16,
            16,
            16,
            new ResourceLocation("ars_nouveau", "textures/gui/settings_summon_lily.png"),
            b -> Networking.sendToServer(new PacketSummonLily())
         );
         lilyButton.withTooltip(this, Component.m_237115_("ars_nouveau.settings.summon_lily"));
         GuiImageButton unsummonLily = new GuiImageButton(
            this.bookLeft + 60,
            this.bookTop + 34,
            0,
            0,
            16,
            16,
            16,
            16,
            new ResourceLocation("ars_nouveau", "textures/gui/settings_unsummon_lily.png"),
            b -> Networking.sendToServer(new PacketUnsummonLily())
         );
         unsummonLily.withTooltip(this, Component.m_237115_("ars_nouveau.settings.unsummon_lily"));
         this.m_142416_(lilyButton);
         this.m_142416_(unsummonLily);
      }
   }

   @Override
   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/create_paper.png"), 216, 179, 0, 0, 56, 15, 56, 15, stack);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.settings.title").getString(), 51.0F, 24.0F, -8355712);
      this.f_96541_.f_91062_.m_92889_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.close"), 238.0F, 183.0F, -8355712);
   }
}
