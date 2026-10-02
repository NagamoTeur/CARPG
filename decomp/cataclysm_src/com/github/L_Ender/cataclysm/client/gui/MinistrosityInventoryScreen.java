package com.github.L_Ender.cataclysm.client.gui;

import com.github.L_Ender.cataclysm.entity.Pet.Netherite_Ministrosity_Entity;
import com.github.L_Ender.cataclysm.inventory.MinistrostiyMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinistrosityInventoryScreen extends AbstractContainerScreen<MinistrostiyMenu> {
   private static final ResourceLocation HORSE_INVENTORY_LOCATION = new ResourceLocation("cataclysm", "textures/gui/ministrosity2.png");
   private final Netherite_Ministrosity_Entity mini;
   private float xMouse;
   private float yMouse;

   public MinistrosityInventoryScreen(MinistrostiyMenu p_98817_, Inventory p_98818_, Netherite_Ministrosity_Entity p_98819_) {
      super(p_98817_, p_98818_, p_98819_.m_5446_());
      this.mini = p_98819_;
   }

   protected void m_7286_(PoseStack p_282553_, float p_282998_, int p_282929_, int p_283133_) {
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      RenderSystem.m_157456_(0, HORSE_INVENTORY_LOCATION);
      this.m_93228_(p_282553_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      RenderSystem.m_157456_(0, HORSE_INVENTORY_LOCATION);
      this.m_93228_(p_282553_, i + 70, j + 17, 0, this.f_97727_, this.mini.getInventoryColumns() * 18, 54);
      InventoryScreen.m_98850_(i + 35, j + 62, 40, (float)(i + 35) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.mini);
   }

   public void m_6305_(PoseStack p_281697_, int p_282103_, int p_283529_, float p_283079_) {
      this.m_7333_(p_281697_);
      this.xMouse = (float)p_282103_;
      this.yMouse = (float)p_283529_;
      super.m_6305_(p_281697_, p_282103_, p_283529_, p_283079_);
      this.m_7025_(p_281697_, p_282103_, p_283529_);
   }
}
