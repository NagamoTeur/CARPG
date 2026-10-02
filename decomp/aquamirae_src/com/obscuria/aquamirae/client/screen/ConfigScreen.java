package com.obscuria.aquamirae.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.AquamiraeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
   private final Screen PARENT;
   private CycleButton<Boolean> overlay;
   private CycleButton<Boolean> particles;
   private CycleButton<Boolean> ambientSounds;
   private CycleButton<Boolean> biomeMusic;
   private CycleButton<Boolean> bossMusic;
   private CycleButton<Boolean> stylizedBossbar;
   private CycleButton<Boolean> notifications;

   public ConfigScreen(Screen parent) {
      super(Component.m_237113_("Aquamirae Settings"));
      this.PARENT = parent;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.overlay = CycleButton.m_168916_((Boolean)AquamiraeConfig.Client.overlay.get())
         .m_168930_(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 24 - 6, 310, 20, Component.m_237113_("Helmet Overlay"));
      this.particles = CycleButton.m_168916_((Boolean)AquamiraeConfig.Client.particles.get())
         .m_168930_(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 48 - 6, 150, 20, Component.m_237113_("Biome Particles"));
      this.ambientSounds = CycleButton.m_168916_((Boolean)AquamiraeConfig.Client.ambientSounds.get())
         .m_168930_(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 48 - 6, 150, 20, Component.m_237113_("Ambient Sounds"));
      this.biomeMusic = CycleButton.m_168916_((Boolean)AquamiraeConfig.Client.biomeMusic.get())
         .m_168930_(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 72 - 6, 150, 20, Component.m_237113_("Biome Music"));
      this.bossMusic = CycleButton.m_168916_((Boolean)AquamiraeConfig.Client.bossMusic.get())
         .m_168930_(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 72 - 6, 150, 20, Component.m_237113_("Boss Music"));
      this.stylizedBossbar = CycleButton.m_168916_((Boolean)AquamiraeConfig.Client.stylizedBossbar.get())
         .m_168930_(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 108 - 6, 150, 20, Component.m_237113_("Stylized Boss Bar"));
      this.notifications = CycleButton.m_168916_((Boolean)AquamiraeConfig.Common.notifications.get())
         .m_168930_(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 108 - 6, 150, 20, Component.m_237113_("Chat Notifications"));
      this.m_142416_(this.overlay);
      this.m_142416_(this.particles);
      this.m_142416_(this.ambientSounds);
      this.m_142416_(this.biomeMusic);
      this.m_142416_(this.bossMusic);
      this.m_142416_(this.stylizedBossbar);
      this.m_142416_(this.notifications);
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 168 - 6, 100, 20, Component.m_237113_("Reset"), button -> {
         AquamiraeConfig.Client.overlay.set((Boolean)AquamiraeConfig.Client.overlay.getDefault());
         AquamiraeConfig.Client.particles.set((Boolean)AquamiraeConfig.Client.particles.getDefault());
         AquamiraeConfig.Client.ambientSounds.set((Boolean)AquamiraeConfig.Client.ambientSounds.getDefault());
         AquamiraeConfig.Client.biomeMusic.set((Boolean)AquamiraeConfig.Client.biomeMusic.getDefault());
         AquamiraeConfig.Client.bossMusic.set((Boolean)AquamiraeConfig.Client.bossMusic.getDefault());
         AquamiraeConfig.Client.stylizedBossbar.set((Boolean)AquamiraeConfig.Client.stylizedBossbar.getDefault());
         AquamiraeConfig.Common.notifications.set((Boolean)AquamiraeConfig.Common.notifications.getDefault());
         this.overlay.m_168892_((Boolean)AquamiraeConfig.Client.overlay.get());
         this.particles.m_168892_((Boolean)AquamiraeConfig.Client.particles.get());
         this.ambientSounds.m_168892_((Boolean)AquamiraeConfig.Client.ambientSounds.get());
         this.biomeMusic.m_168892_((Boolean)AquamiraeConfig.Client.biomeMusic.get());
         this.bossMusic.m_168892_((Boolean)AquamiraeConfig.Client.bossMusic.get());
         this.stylizedBossbar.m_168892_((Boolean)AquamiraeConfig.Client.stylizedBossbar.get());
         this.notifications.m_168892_((Boolean)AquamiraeConfig.Common.notifications.get());
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 45, this.f_96544_ / 6 + 168 - 6, 200, 20, CommonComponents.f_130655_, button -> {
         this.save();
         Minecraft.m_91087_().m_91152_(this.PARENT);
      }));
   }

   public void m_6305_(PoseStack pose, int mouseX, int mouseY, float partialTicks) {
      this.m_96626_(0);
      m_93215_(pose, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      super.m_6305_(pose, mouseX, mouseY, partialTicks);
   }

   public void save() {
      AquamiraeConfig.Client.overlay.set((Boolean)this.overlay.m_168883_());
      AquamiraeConfig.Client.particles.set((Boolean)this.particles.m_168883_());
      AquamiraeConfig.Client.ambientSounds.set((Boolean)this.ambientSounds.m_168883_());
      AquamiraeConfig.Client.biomeMusic.set((Boolean)this.biomeMusic.m_168883_());
      AquamiraeConfig.Client.bossMusic.set((Boolean)this.bossMusic.m_168883_());
      AquamiraeConfig.Client.stylizedBossbar.set((Boolean)this.stylizedBossbar.m_168883_());
      AquamiraeConfig.Common.notifications.set((Boolean)this.notifications.m_168883_());
   }

   public void m_7379_() {
      this.save();
      if (this.f_96541_ != null && this.PARENT != null) {
         this.f_96541_.m_91152_(this.PARENT);
      } else {
         super.m_7379_();
      }
   }
}
