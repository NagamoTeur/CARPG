package com.aizistral.enigmaticlegacy.gui.containers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.packets.server.PacketInkwellField;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LoreInscriberScreen extends AbstractContainerScreen<LoreInscriberContainer> implements ContainerListener {
   private ResourceLocation guiTexture;
   private static final ResourceLocation ANVIL_RESOURCE = new ResourceLocation("enigmaticlegacy", "textures/gui/inkwell_gui.png");
   private EditBox nameField;

   public LoreInscriberScreen(LoreInscriberContainer container, Inventory Inventory, Component title) {
      this(container, Inventory, title, ANVIL_RESOURCE);
      this.f_97728_ = 60;
   }

   private LoreInscriberScreen(LoreInscriberContainer container, Inventory Inventory, Component title, ResourceLocation guiTexture) {
      super(container, Inventory, title);
      this.guiTexture = guiTexture;
   }

   protected void initFields() {
      this.f_96541_.f_91068_.m_90926_(true);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.nameField = new EditBox(this.f_96547_, i + 55, j + 30, 95, 12, Component.m_237115_("container.repair"));
      this.nameField.m_94190_(false);
      this.nameField.m_94202_(-1);
      this.nameField.m_94205_(-1);
      this.nameField.m_94182_(false);
      this.nameField.m_94199_(128);
      this.nameField.m_94151_(this::renameResponder);
      this.m_7787_(this.nameField);
      this.m_94718_(this.nameField);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.initFields();
      ((LoreInscriberContainer)this.f_97732_).m_38893_(this);
   }

   public void m_7861_() {
      super.m_7861_();
      ((LoreInscriberContainer)this.f_97732_).m_38943_(this);
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public void m_6305_(PoseStack PoseStack, int x, int y, float partialTicksIGuess) {
      this.m_7333_(PoseStack);
      super.m_6305_(PoseStack, x, y, partialTicksIGuess);
      RenderSystem.m_69461_();
      this.renderNameField(PoseStack, x, y, partialTicksIGuess);
      this.m_7025_(PoseStack, x, y);
   }

   protected void m_7286_(PoseStack PoseStack, float partialTicks, int x, int y) {
      RenderSystem.m_69424_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, this.guiTexture);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(PoseStack, i, j, 0, 0, this.f_97726_, this.f_97727_);
      this.m_93228_(PoseStack, i + 52, j + 26, 0, this.f_97727_ + (((LoreInscriberContainer)this.f_97732_).m_38853_(0).m_6657_() ? 0 : 16), 102, 16);
      if (((LoreInscriberContainer)this.f_97732_).m_38853_(0).m_6657_() && !((LoreInscriberContainer)this.f_97732_).m_38853_(1).m_6657_()) {
         this.m_93228_(PoseStack, i + 71, j + 49, this.f_97726_, 0, 28, 21);
      }
   }

   public void m_6574_(Minecraft minecraft, int width, int height) {
      String s = this.nameField.m_94155_();
      this.m_6575_(minecraft, width, height);
      this.nameField.m_94144_(s);
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.f_96541_.f_91074_.m_6915_();
      }

      return !this.nameField.m_7933_(keyCode, scanCode, modifiers) && !this.nameField.m_94204_() ? super.m_7933_(keyCode, scanCode, modifiers) : true;
   }

   private void renameResponder(String input) {
      if (!input.isEmpty()) {
         String s = input;
         Slot slot = ((LoreInscriberContainer)this.f_97732_).m_38853_(0);
         if (slot != null && slot.m_6657_() && !slot.m_7993_().m_41788_() && input.equals(slot.m_7993_().m_41786_().getString())) {
            s = "";
         }

         ((LoreInscriberContainer)this.f_97732_).updateItemName(s);
         EnigmaticLegacy.packetInstance.sendToServer(new PacketInkwellField(s));
      }
   }

   protected void m_7027_(PoseStack PoseStack, int x, int y) {
      this.f_96547_.m_92889_(PoseStack, this.f_96539_, 52.0F, 13.0F, 4210752);
      RenderSystem.m_69461_();
   }

   public void renderNameField(PoseStack PoseStack, int mouseX, int mouseY, float partialTicks) {
      this.nameField.m_6305_(PoseStack, mouseX, mouseY, partialTicks);
   }

   public void m_7934_(AbstractContainerMenu containerToSend, int slotInd, ItemStack stack) {
      if (slotInd == 0) {
         this.nameField.m_94144_(stack.m_41619_() ? "" : stack.m_41786_().getString());
         this.nameField.m_94186_(!stack.m_41619_());
         this.m_7522_(this.nameField);
      }
   }

   public void m_142153_(AbstractContainerMenu p_150524_, int p_150525_, int p_150526_) {
   }
}
