package daripher.skilltree.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.item.gem.GemItem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SocketTooltipRenderer implements ClientTooltipComponent {
   public static final ResourceLocation SOCKET = new ResourceLocation("skilltree", "textures/screen/socket.png");
   private final SocketTooltipRenderer.SocketComponent component;
   private final int spacing = 9 + 2;

   public SocketTooltipRenderer(SocketTooltipRenderer.SocketComponent comp) {
      this.component = comp;
   }

   public static Component getSocketDesc(ItemStack stack, int socket) {
      return !GemItem.hasGem(stack, socket) ? Component.m_237115_("gem.socket") : GemItem.getGemBonuses(stack).get(socket).getTooltip();
   }

   public int m_142103_() {
      return this.spacing * this.component.sockets;
   }

   public int m_142069_(@NotNull Font font) {
      int width = 0;

      for (int i = 0; i < this.component.sockets; i++) {
         width = Math.max(width, font.m_92852_(getSocketDesc(this.component.stack, i)) + 12);
      }

      return width;
   }

   public void m_183452_(@NotNull Font pFont, int x, int y, @NotNull PoseStack stack, @NotNull ItemRenderer itemRenderer, int pBlitOffset) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, SOCKET);

      for (int i = 0; i < this.component.sockets; i++) {
         GuiComponent.m_93143_(stack, x, y + this.spacing * i, pBlitOffset, 0.0F, 0.0F, 9, 9, 9, 9);
      }

      for (ItemStack gem : this.component.gems) {
         if (!gem.m_41619_()) {
            PoseStack mvStack = RenderSystem.m_157191_();
            mvStack.m_85836_();
            mvStack.m_85841_(0.5F, 0.5F, 1.0F);
            itemRenderer.m_115218_(gem, 2 * x + 1, 2 * y + 1);
            mvStack.m_85849_();
            RenderSystem.m_157182_();
         }

         y += this.spacing;
      }
   }

   public void m_142440_(@NotNull Font font, int x, int y, @NotNull Matrix4f matrix, @NotNull BufferSource buffer) {
      for (int i = 0; i < this.component.sockets; i++) {
         font.m_92841_(
            getSocketDesc(this.component.stack, i), (float)(x + 12), (float)(y + 1 + this.spacing * i), 11189196, true, matrix, buffer, false, 0, 15728880
         );
      }
   }

   public static class SocketComponent implements TooltipComponent {
      private final ItemStack stack;
      private final List<ItemStack> gems;
      private int sockets;

      public SocketComponent(ItemStack stack) {
         this.stack = stack;
         this.gems = GemItem.getGems(stack);
         this.sockets = ItemHelper.getMaximumSockets(stack, Minecraft.m_91087_().f_91074_);
         if (this.sockets < this.gems.size()) {
            this.sockets = this.gems.size();
         }
      }
   }
}
