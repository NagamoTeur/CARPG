package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.gui.book.BaseBook;
import com.hollingsworth.arsnouveau.client.gui.book.GlyphUnlockMenu;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.ForgeHooksClient;

public class ItemButton extends GuiImageButton {
   public String spellTag;
   public Ingredient ingredient = Ingredient.m_151265_();

   public ItemButton(BaseBook parent, int x, int y) {
      super(x, y, 0, 0, 22, 20, 22, 20, "textures/gui/spell_glyph_slot.png", b -> {
      });
      this.spellTag = "";
      this.resourceIcon = "";
      this.parent = parent;
   }

   public void m_7428_(PoseStack pPoseStack, int pMouseX, int pMouseY) {
      super.m_7428_(pPoseStack, pMouseX, pMouseY);
   }

   @Override
   public void m_6305_(PoseStack ms, int parX, int parY, float partialTicks) {
      if (this.f_93624_) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         if (!this.resourceIcon.equals("")) {
            GuiSpellBook.drawFromTexture(
               new ResourceLocation("ars_nouveau", "textures/items/" + this.resourceIcon),
               this.f_93620_ + 3,
               this.f_93621_ + 2,
               this.u,
               this.v,
               16,
               16,
               16,
               16,
               ms
            );
         }

         if (this.ingredient != null && this.ingredient.m_43908_().length != 0) {
            ItemStack stack = this.ingredient.m_43908_()[ClientInfo.ticksInGame / 20 % this.ingredient.m_43908_().length];
            if (this.parent.isMouseInRelativeRange(parX, parY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)
               && this.parent instanceof GlyphUnlockMenu menu) {
               Font font = Minecraft.m_91087_().f_91062_;
               List<ClientTooltipComponent> components = new ArrayList<>(
                  ForgeHooksClient.gatherTooltipComponents(ItemStack.f_41583_, this.parent.m_96555_(stack), parX, this.f_93618_, this.f_93619_, font, font)
               );
               menu.m_169383_(ms, components, parX, parY);
            }

            RenderUtils.drawItemAsIcon(stack.m_41720_(), ms, this.f_93620_ + 3, this.f_93621_ + 2, 16, false);
         }
      }

      super.m_6305_(ms, parX, parY, partialTicks);
   }
}
