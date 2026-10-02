package net.xylonity.knightquest.common.item.weapons;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.common.item.KQWeaponItem;
import net.xylonity.knightquest.config.values.KQConfigValues;

public class NailWeapon extends KQWeaponItem {
   private static final Map<UUID, Boolean> doubleJumpStates = new HashMap<>();

   public NailWeapon(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   @Override
   public void interaction(Level level, Player player, InteractionHand hand) {
      boolean canDash = doubleJumpStates.getOrDefault(player.m_20148_(), true);
      if (canDash) {
         handleClientSideDoubleJump(player);
      }

      doubleJumpStates.put(player.m_20148_(), true);
   }

   @Override
   public int getCooldownTicks() {
      return KQConfigValues.COOLDOWN_NAIL;
   }

   @Override
   public String getName() {
      return "nail";
   }

   @Override
   protected boolean isEnabled() {
      return KQConfigValues.NAIL;
   }

   private static void handleClientSideDoubleJump(Player player) {
      boolean canDash = doubleJumpStates.getOrDefault(player.m_20148_(), true);
      if (canDash) {
         if (player.f_19853_.f_46443_) {
            doubleJumpStates.put(player.m_20148_(), false);
            double dashSpeed = KQConfigValues.DASH_POWER_NAIL;
            player.m_20256_(player.m_20154_().m_82490_(dashSpeed));
         }

         if (!player.f_19853_.f_46443_ && player.f_19853_ instanceof ServerLevel level) {
            Vec3 playerPos = player.m_20182_().m_82520_(0.0, 1.0, 0.0);
            Vec3 dashDirection = player.m_20154_().m_82490_(0.5);

            for (int i = 0; i < 20; i++) {
               double randomOffsetX = (Math.random() - 0.5) * 0.3;
               double randomOffsetY = (Math.random() - 0.5) * 0.1;
               double randomOffsetZ = (Math.random() - 0.5) * 0.3;
               Vec3 particlePos = playerPos.m_82520_(randomOffsetX, randomOffsetY, randomOffsetZ);
               level.m_8767_(
                  ParticleTypes.f_123796_,
                  particlePos.f_82479_,
                  particlePos.f_82480_,
                  particlePos.f_82481_,
                  1,
                  dashDirection.f_82479_,
                  dashDirection.f_82480_,
                  dashDirection.f_82481_,
                  0.1
               );
            }
         }
      }
   }
}
