package com.github.alexthe666.alexsmobs.client.gui;

import com.github.alexthe666.alexsmobs.client.render.RenderLaviathan;
import com.github.alexthe666.alexsmobs.client.render.RenderMurmurBody;
import com.github.alexthe666.alexsmobs.client.render.RenderUnderminer;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.citadel.client.gui.GuiBasicBook;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GUIAnimalDictionary extends GuiBasicBook {
   private static final ResourceLocation ROOT = new ResourceLocation("alexsmobs:book/animal_dictionary/root.json");

   public GUIAnimalDictionary(ItemStack bookStack) {
      super(bookStack, Component.m_237115_("animal_dictionary.title"));
   }

   public GUIAnimalDictionary(ItemStack bookStack, String page) {
      super(bookStack, Component.m_237115_("animal_dictionary.title"));
      this.currentPageJSON = new ResourceLocation(this.getTextFileDirectory() + page + ".json");
   }

   public void m_6305_(PoseStack matrixStack, int x, int y, float partialTicks) {
      if (this.currentPageJSON.equals(this.getRootPage()) && this.currentPageCounter == 0) {
         int k = (this.f_96543_ - this.xSize) / 2;
         int l = (this.f_96544_ - this.ySize + 128) / 2;
         RenderSystem.m_157182_();
         PoseStack stack = RenderSystem.m_157191_();
         stack.m_85836_();
         stack.m_85837_((double)k, (double)l, 0.0);
         stack.m_85841_(2.75F, 2.75F, 2.75F);
         this.f_96542_.m_115123_(new ItemStack((ItemLike)AMItemRegistry.TAB_ICON.get()), 25, 14);
         this.f_96542_.f_115093_ = 0.0F;
         stack.m_85849_();
         RenderSystem.m_157182_();
      }

      RenderLaviathan.renderWithoutShaking = true;
      RenderMurmurBody.renderWithHead = true;
      RenderUnderminer.renderWithPickaxe = true;
      super.m_6305_(matrixStack, x, y, partialTicks);
      RenderLaviathan.renderWithoutShaking = false;
      RenderMurmurBody.renderWithHead = false;
      RenderUnderminer.renderWithPickaxe = false;
   }

   protected int getBindingColor() {
      return 6318886;
   }

   public ResourceLocation getRootPage() {
      return ROOT;
   }

   public String getTextFileDirectory() {
      return "alexsmobs:book/animal_dictionary/";
   }
}
