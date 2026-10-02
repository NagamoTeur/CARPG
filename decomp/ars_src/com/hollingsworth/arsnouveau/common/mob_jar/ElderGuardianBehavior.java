package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import java.util.List;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class ElderGuardianBehavior extends JarBehavior<ElderGuardian> {
   @Override
   public void tick(MobJarTile tile) {
      if ((Boolean)tile.m_58900_().m_61143_(BlockStateProperties.f_61448_)) {
         if (tile.m_58904_().m_46467_() % 1200L == 0L) {
            this.applyMiningFatigue(tile);
         }
      }
   }

   @Override
   public void onRedstonePower(MobJarTile tile) {
      this.applyMiningFatigue(tile);
   }

   public void applyMiningFatigue(MobJarTile tile) {
      if (tile.m_58904_() instanceof ServerLevel serverLevel) {
         MobEffectInstance effect = new MobEffectInstance(MobEffects.f_19599_, 6000, 2);
         List<ServerPlayer> list = MobEffectUtil.m_216946_(serverLevel, this.entityFromJar(tile), Vec3.m_82512_(tile.m_58899_()), 50.0, effect, 1200);
         list.forEach(player -> player.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132163_, 1.0F)));
      }
   }
}
