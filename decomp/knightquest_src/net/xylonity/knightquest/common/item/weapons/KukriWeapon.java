package net.xylonity.knightquest.common.item.weapons;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.xylonity.knightquest.common.item.KQWeaponItem;
import net.xylonity.knightquest.config.values.KQConfigValues;

public class KukriWeapon extends KQWeaponItem {
   public KukriWeapon(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   @Override
   public void interaction(Level level, Player player, InteractionHand hand) {
      player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, KQConfigValues.SPEED_TICKS_KUKRI, 0, false, false));
   }

   @Override
   public int getCooldownTicks() {
      return KQConfigValues.COOLDOWN_KUKRI;
   }

   @Override
   public String getName() {
      return "kukri";
   }

   @Override
   protected boolean isEnabled() {
      return KQConfigValues.KUKRI;
   }
}
