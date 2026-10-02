package net.thirdlife.iterrpg.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.network.MobPlacerGUIButtonMessage;
import net.thirdlife.iterrpg.procedures.MobPlacerCoordinatesReturnProcedure;
import net.thirdlife.iterrpg.procedures.MobPlacerModelProviderProcedure;
import net.thirdlife.iterrpg.world.inventory.MobPlacerGUIMenu;

public class MobPlacerGUIScreen extends AbstractContainerScreen<MobPlacerGUIMenu> {
   private static final HashMap<String, Object> guistate = MobPlacerGUIMenu.guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   Button button_spawn;
   Button button_g;
   private static final ResourceLocation texture = new ResourceLocation("iter_rpg:textures/screens/mob_placer_gui.png");

   public MobPlacerGUIScreen(MobPlacerGUIMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.world = container.world;
      this.x = container.x;
      this.y = container.y;
      this.z = container.z;
      this.entity = container.entity;
      this.f_97726_ = 176;
      this.f_97727_ = 166;
   }

   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      this.m_7333_(ms);
      super.m_6305_(ms, mouseX, mouseY, partialTicks);
      this.m_7025_(ms, mouseX, mouseY);
      if (MobPlacerModelProviderProcedure.execute(this.world, this.entity) instanceof LivingEntity livingEntity) {
         InventoryScreen.renderEntityInInventoryRaw(
            this.f_97735_ + 85,
            this.f_97736_ + 133,
            30,
            0.0F + (float)Math.atan((double)(this.f_97735_ + 85 - mouseX) / 40.0),
            (float)Math.atan((double)(this.f_97736_ + 83 - mouseY) / 40.0),
            livingEntity
         );
      }
   }

   protected void m_7286_(PoseStack ms, float partialTicks, int gx, int gy) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157456_(0, texture);
      m_93133_(ms, this.f_97735_, this.f_97736_, 0.0F, 0.0F, this.f_97726_, this.f_97727_, this.f_97726_, this.f_97727_);
      RenderSystem.m_69461_();
   }

   public boolean m_7933_(int key, int b, int c) {
      if (key == 256) {
         this.f_96541_.f_91074_.m_6915_();
         return true;
      } else {
         return super.m_7933_(key, b, c);
      }
   }

   public void m_181908_() {
      super.m_181908_();
   }

   protected void m_7027_(PoseStack poseStack, int mouseX, int mouseY) {
      this.f_96547_.m_92883_(poseStack, MobPlacerCoordinatesReturnProcedure.execute(this.entity), 16.0F, 10.0F, -12829636);
   }

   public void m_7379_() {
      super.m_7379_();
      Minecraft.m_91087_().f_91068_.m_90926_(false);
   }

   public void m_7856_() {
      super.m_7856_();
      this.f_96541_.f_91068_.m_90926_(true);
      this.button_spawn = new Button(this.f_97735_ + 61, this.f_97736_ + 139, 51, 20, Component.m_237115_("gui.iter_rpg.mob_placer_gui.button_spawn"), e -> {
         IterRpgMod.PACKET_HANDLER.sendToServer(new MobPlacerGUIButtonMessage(0, this.x, this.y, this.z));
         MobPlacerGUIButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
      });
      guistate.put("button:button_spawn", this.button_spawn);
      this.m_142416_(this.button_spawn);
      this.button_g = new Button(this.f_97735_ + 7, this.f_97736_ + 29, 30, 20, Component.m_237115_("gui.iter_rpg.mob_placer_gui.button_g"), e -> {
         IterRpgMod.PACKET_HANDLER.sendToServer(new MobPlacerGUIButtonMessage(1, this.x, this.y, this.z));
         MobPlacerGUIButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z);
      });
      guistate.put("button:button_g", this.button_g);
      this.m_142416_(this.button_g);
   }
}
