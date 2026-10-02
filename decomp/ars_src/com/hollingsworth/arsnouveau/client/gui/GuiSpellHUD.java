package com.hollingsworth.arsnouveau.client.gui;

import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiSpellHUD {
   public static final IGuiOverlay OVERLAY = GuiSpellHUD::renderOverlay;
   private static final Minecraft minecraft = Minecraft.m_91087_();

   public static void renderOverlay(ForgeGui gui, PoseStack ms, float pt, int width, int height) {
      ItemStack stack = StackUtil.getHeldSpellbook(minecraft.f_91074_);
      if (stack != ItemStack.f_41583_ && stack.m_41720_() instanceof SpellBook && stack.m_41783_() != null) {
         int offsetLeft = 10;
         ISpellCaster caster = CasterUtil.getCaster(stack);
         String renderString = caster.getCurrentSlot() + 1 + " " + caster.getSpellName();
         minecraft.f_91062_.m_92750_(ms, renderString, (float)offsetLeft, (float)(minecraft.m_91268_().m_85446_() - 30), 16777215);
      }
   }
}
