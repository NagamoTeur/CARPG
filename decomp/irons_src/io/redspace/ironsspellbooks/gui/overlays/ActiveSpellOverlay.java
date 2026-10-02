package io.redspace.ironsspellbooks.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.item.CastingItem;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ActiveSpellOverlay extends GuiComponent {
   protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/gui/icons.png");

   public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         ItemStack stack = player.m_21205_();
         AbstractSpell spell;
         if (hasRightClickCasting(stack.m_41720_())) {
            if (ISpellContainer.isSpellContainer(stack)) {
               spell = ISpellContainer.get(stack).getSpellAtIndex(0).getSpell();
            } else {
               spell = ClientMagicData.getSpellSelectionManager().getSelectedSpellData().getSpell();
            }
         } else {
            stack = player.m_21206_();
            if (!hasRightClickCasting(stack.m_41720_())) {
               return;
            }

            if (ISpellContainer.isSpellContainer(stack)) {
               spell = ISpellContainer.get(stack).getSpellAtIndex(0).getSpell();
            } else {
               spell = ClientMagicData.getSpellSelectionManager().getSelectedSpellData().getSpell();
            }
         }

         if (!stack.m_41619_() && spell != SpellRegistry.none()) {
            int centerX = screenWidth / 2 + 91 + 9;
            int centerY = screenHeight - 23;
            setTranslucentTexture(WIDGETS_LOCATION);
            gui.m_93228_(poseStack, centerX, centerY, 24, 22, 29, 24);
            setOpaqueTexture(spell.getSpellIconResource());
            ForgeGui.m_93133_(poseStack, centerX + 3, centerY + 4, 0.0F, 0.0F, 16, 16, 16, 16);
            float f = ClientMagicData.getCooldownPercent(spell);
            if (f > 0.0F && !stack.m_41720_().equals(ItemRegistry.SCROLL.get())) {
               setTranslucentTexture(TEXTURE);
               int pixels = (int)(16.0F * f + 1.0F);
               gui.m_93228_(poseStack, centerX + 3, centerY + 20 - pixels, 47, 87, 16, pixels);
            }
         }
      }
   }

   private static boolean hasRightClickCasting(Item item) {
      return item instanceof Scroll || item instanceof CastingItem;
   }

   private static void setOpaqueTexture(ResourceLocation texture) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, texture);
   }

   private static void setTranslucentTexture(ResourceLocation texture) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172649_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, texture);
   }
}
