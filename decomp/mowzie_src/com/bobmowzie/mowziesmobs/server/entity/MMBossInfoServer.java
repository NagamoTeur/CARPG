package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.message.MessageUpdateBossBar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraftforge.network.NetworkDirection;

public class MMBossInfoServer extends ServerBossEvent {
   private final MowzieEntity entity;
   private final Set<ServerPlayer> unseen = new HashSet<>();

   public MMBossInfoServer(MowzieEntity entity) {
      super(entity.m_5446_(), entity.bossBarColor(), BossBarOverlay.PROGRESS);
      this.m_8321_(entity.hasBossBar());
      this.entity = entity;
   }

   public void update() {
      this.m_142711_(this.entity.m_21223_() / this.entity.m_21233_());
      Iterator<ServerPlayer> it = this.unseen.iterator();

      while (it.hasNext()) {
         ServerPlayer player = it.next();
         if (this.entity.m_21574_().m_148306_(player)) {
            super.m_6543_(player);
            it.remove();
         }
      }
   }

   public void m_6543_(ServerPlayer player) {
      MowziesMobs.NETWORK.sendTo(new MessageUpdateBossBar(this.m_18860_(), this.entity), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
      if (this.entity.m_21574_().m_148306_(player)) {
         super.m_6543_(player);
      } else {
         this.unseen.add(player);
      }
   }

   public void m_6539_(ServerPlayer player) {
      MowziesMobs.NETWORK.sendTo(new MessageUpdateBossBar(this.m_18860_(), null), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
      super.m_6539_(player);
      this.unseen.remove(player);
   }
}
