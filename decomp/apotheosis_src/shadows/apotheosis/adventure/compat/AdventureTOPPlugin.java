package shadows.apotheosis.adventure.compat;

import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import shadows.apotheosis.util.CommonTooltipUtil;
import shadows.placebo.compat.TOPCompat;
import shadows.placebo.compat.TOPCompat.Provider;

public class AdventureTOPPlugin implements Provider {
   public static void register() {
      TOPCompat.registerProvider(new AdventureTOPPlugin());
   }

   public void addProbeEntityInfo(ProbeMode mode, IProbeInfo info, Player player, Level level, Entity entity, IProbeHitEntityData hitData) {
      if (entity instanceof LivingEntity living && living.getPersistentData().m_128471_("apoth.boss")) {
         CommonTooltipUtil.appendBossData(living.f_19853_, living, info::mcText);
      }
   }
}
