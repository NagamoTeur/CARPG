package software.bernie.ars_nouveau.geckolib3.compat;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import vazkii.patchouli.client.book.gui.GuiBook;

public class PatchouliCompat {
   public static void patchouliLoaded(PoseStack poseStack) {
      if (Minecraft.m_91087_().f_91065_.equals(GuiBook.class)) {
         poseStack.m_85836_();
         Lighting.m_84930_();
         Minecraft.m_91087_().m_91269_().m_110104_().m_109911_();
         RenderSystem.m_69482_();
         Lighting.m_84931_();
         poseStack.m_85849_();
      }
   }
}
