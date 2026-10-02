package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.sound.SpellSound;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.book.SoundScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class SoundButton extends Button {
   SoundScreen parent;
   public SpellSound sound;

   public SoundButton(SoundScreen parent, int x, int y, SpellSound sound, OnPress onPress) {
      super(x, y, 16, 16, Component.m_130674_(""), onPress);
      this.parent = parent;
      this.f_93620_ = x;
      this.f_93621_ = y;
      this.f_93618_ = 16;
      this.f_93619_ = 16;
      this.sound = sound;
   }

   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      if (this.f_93624_ && this.sound != null) {
         GuiSpellBook.drawFromTexture(this.sound.getTexturePath(), this.f_93620_, this.f_93621_, 0, 0, 16, 16, 16, 16, ms);
         if (this.parent.isMouseInRelativeRange(mouseX, mouseY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)) {
            List<Component> tip = new ArrayList<>();
            tip.add(this.sound.getSoundName());
            this.parent.tooltip = tip;
         }
      }
   }
}
