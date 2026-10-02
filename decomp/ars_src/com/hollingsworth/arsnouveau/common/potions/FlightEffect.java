package com.hollingsworth.arsnouveau.common.potions;

import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketUpdateFlight;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

public class FlightEffect extends MobEffect {
   protected FlightEffect() {
      super(MobEffectCategory.BENEFICIAL, 2039587);
   }

   public boolean m_6584_(int p_76397_1_, int p_76397_2_) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int p_76394_2_) {
      if (entity instanceof Player player) {
         player.f_36077_.f_35936_ = player.m_7500_() || entity.m_5833_() || entity.m_21124_((MobEffect)ModPotions.FLIGHT_EFFECT.get()).m_19557_() > 2;
      }
   }

   public void m_6386_(LivingEntity entity, AttributeMap p_111187_2_, int p_111187_3_) {
      super.m_6386_(entity, p_111187_2_, p_111187_3_);
      if (entity instanceof Player player) {
         boolean canFly = player.m_7500_() || player.m_5833_();
         player.f_36077_.f_35936_ = canFly;
         player.f_36077_.f_35935_ = canFly;
         Networking.sendToPlayerClient(new PacketUpdateFlight(canFly, canFly), (ServerPlayer)player);
      }
   }
}
