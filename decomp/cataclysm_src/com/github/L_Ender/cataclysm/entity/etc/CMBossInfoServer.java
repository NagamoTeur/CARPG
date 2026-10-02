package com.github.L_Ender.cataclysm.entity.etc;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.message.MessageUpdateBossBar;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;

public class CMBossInfoServer extends ServerBossEvent {
   private int renderType;

   public CMBossInfoServer(Component component, BossBarColor bossBarColor, boolean dark, int renderType) {
      super(component, bossBarColor, BossBarOverlay.PROGRESS);
      this.m_7003_(dark);
      this.renderType = renderType;
   }

   public void setRenderType(int renderType) {
      if (renderType != this.renderType) {
         this.renderType = renderType;
         Cataclysm.sendMSGToAll(new MessageUpdateBossBar(this.m_18860_(), renderType));
      }
   }

   public int getRenderType() {
      return this.renderType;
   }

   public void m_6543_(ServerPlayer serverPlayer) {
      Cataclysm.sendNonLocal(new MessageUpdateBossBar(this.m_18860_(), this.renderType), serverPlayer);
      super.m_6543_(serverPlayer);
   }

   public void m_6539_(ServerPlayer serverPlayer) {
      Cataclysm.sendNonLocal(new MessageUpdateBossBar(this.m_18860_(), -1), serverPlayer);
      super.m_6539_(serverPlayer);
   }
}
