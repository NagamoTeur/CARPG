package com.github.alexthe666.alexsmobs.client.gui;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.inventory.MenuTransmutationTable;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GUITransmutationTable extends AbstractContainerScreen<MenuTransmutationTable> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("alexsmobs:textures/gui/transmutation_table.png");
   private int tickCount = 0;
   private ButtonTransmute transmuteBtn1;
   private ButtonTransmute transmuteBtn2;
   private ButtonTransmute transmuteBtn3;

   public GUITransmutationTable(MenuTransmutationTable menu, Inventory inventory, Component name) {
      super(menu, inventory, name);
      this.f_97727_ = 201;
   }

   protected void m_7856_() {
      super.m_7856_();
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_142416_(
         this.transmuteBtn1 = new ButtonTransmute(this, i + 30, j + 16, button -> ((MenuTransmutationTable)this.f_97732_).m_6366_(this.f_96541_.f_91074_, 0))
      );
      this.m_142416_(
         this.transmuteBtn2 = new ButtonTransmute(this, i + 30, j + 35, button -> ((MenuTransmutationTable)this.f_97732_).m_6366_(this.f_96541_.f_91074_, 1))
      );
      this.m_142416_(
         this.transmuteBtn3 = new ButtonTransmute(this, i + 30, j + 54, button -> ((MenuTransmutationTable)this.f_97732_).m_6366_(this.f_96541_.f_91074_, 2))
      );
      this.transmuteBtn1.f_93624_ = false;
      this.transmuteBtn2.f_93624_ = false;
      this.transmuteBtn3.f_93624_ = false;
   }

   public void m_6305_(PoseStack stack, int x, int y, float partialTick) {
      this.m_7333_(stack);
      this.m_7286_(stack, partialTick, x, y);
      super.m_6305_(stack, x, y, partialTick);
      this.renderItemsTransmute(stack, x, y);
      this.m_7025_(stack, x, y);
   }

   protected void m_7286_(PoseStack poseStack, float f, int x, int y) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_93228_(poseStack, i, j, 0, 0, this.f_97726_, this.f_97727_);
   }

   protected void m_181908_() {
      this.tickCount++;
      boolean thingIn = !((MenuTransmutationTable)this.f_97732_).m_38853_(0).m_7993_().m_41619_();
      this.transmuteBtn1.f_93624_ = !AlexsMobs.PROXY.getDisplayTransmuteResult(0).m_41619_() && thingIn;
      this.transmuteBtn2.f_93624_ = !AlexsMobs.PROXY.getDisplayTransmuteResult(1).m_41619_() && thingIn;
      this.transmuteBtn3.f_93624_ = !AlexsMobs.PROXY.getDisplayTransmuteResult(2).m_41619_() && thingIn;
   }

   protected void m_7027_(PoseStack poseStack, int x, int y) {
      this.f_97728_ = (this.f_97726_ - this.f_96547_.m_92852_(this.f_96539_)) / 2;
      this.f_96547_.m_92889_(poseStack, this.f_96539_, (float)this.f_97728_, (float)this.f_97729_, 5177121);
   }

   protected void renderItemsTransmute(PoseStack poseStack, int x, int y) {
      int i = this.f_97735_;
      int j = this.f_97736_;
      if (!((MenuTransmutationTable)this.f_97732_).m_38853_(0).m_7993_().m_41619_()) {
         this.f_96542_.m_115203_(AlexsMobs.PROXY.getDisplayTransmuteResult(0), i + 31, j + 17);
         this.f_96542_.m_115203_(AlexsMobs.PROXY.getDisplayTransmuteResult(1), i + 31, j + 36);
         this.f_96542_.m_115203_(AlexsMobs.PROXY.getDisplayTransmuteResult(2), i + 31, j + 55);
      }
   }
}
