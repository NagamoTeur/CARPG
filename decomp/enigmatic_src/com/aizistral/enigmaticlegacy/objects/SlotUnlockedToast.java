package com.aizistral.enigmaticlegacy.objects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.Toast.Visibility;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlotUnlockedToast implements Toast {
   private long firstDrawTime;
   private ItemStack drawnStack;
   private String identifier;

   public SlotUnlockedToast(ItemStack stack, String id) {
      this.drawnStack = stack;
      this.identifier = id;
   }

   public Visibility m_7172_(PoseStack PoseStack, ToastComponent toastGui, long delta) {
      RenderSystem.m_157456_(0, new ResourceLocation("enigmaticlegacy", "textures/gui/enigmatic_toasts.png"));
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      toastGui.m_93228_(PoseStack, 0, 0, 0, 0, 160, 43);
      toastGui.m_94929_()
         .f_91062_
         .m_92883_(
            PoseStack,
            I18n.m_118938_(
               "enigmaticlegacy.toasts.slotUnlocked.title", new Object[]{I18n.m_118938_("enigmaticlegacy.curiotype." + this.identifier, new Object[0])}
            ),
            7.0F,
            7.0F,
            -11534256
         );
      toastGui.m_94929_().f_91062_.m_92883_(PoseStack, I18n.m_118938_("enigmaticlegacy.toasts.slotUnlocked.text1", new Object[0]), 30.0F, 18.0F, -16777216);
      toastGui.m_94929_().f_91062_.m_92883_(PoseStack, I18n.m_118938_("enigmaticlegacy.toasts.slotUnlocked.text2", new Object[0]), 30.0F, 28.0F, -16777216);
      toastGui.m_94929_().m_91291_().m_115203_(this.drawnStack, 8, 18);
      return delta - this.firstDrawTime >= 5000L ? Visibility.HIDE : Visibility.SHOW;
   }
}
