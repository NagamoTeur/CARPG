package com.aizistral.enigmaticlegacy.objects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.Toast.Visibility;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;

public class RevelationTomeToast implements Toast {
   private final ItemStack tome;
   private final int xpPoints;
   private final int revelationPoints;

   public RevelationTomeToast(ItemStack tome, int xpPoints, int revelationPoints) {
      this.tome = tome;
      this.xpPoints = xpPoints;
      this.revelationPoints = revelationPoints;
   }

   @Nonnull
   public Visibility m_7172_(PoseStack ms, ToastComponent toastGui, long delta) {
      Minecraft mc = Minecraft.m_91087_();
      RenderSystem.m_157456_(0, f_94893_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      toastGui.m_93228_(ms, 0, 0, 0, 32, 160, 32);
      toastGui.m_94929_()
         .f_91062_
         .m_92883_(ms, I18n.m_118938_("enigmaticlegacy.toasts.revelationTome.title", new Object[]{this.xpPoints}), 30.0F, 7.0F, -11534256);
      toastGui.m_94929_()
         .f_91062_
         .m_92883_(ms, I18n.m_118938_("enigmaticlegacy.toasts.revelationTome.text", new Object[]{this.revelationPoints}), 30.0F, 17.0F, -16777216);
      toastGui.m_94929_().m_91291_().m_115203_(this.tome, 8, 8);
      return delta >= 5000L ? Visibility.HIDE : Visibility.SHOW;
   }
}
