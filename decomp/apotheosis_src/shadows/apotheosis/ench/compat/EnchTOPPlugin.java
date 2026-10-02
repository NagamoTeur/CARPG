package shadows.apotheosis.ench.compat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import shadows.apotheosis.ench.anvil.AnvilTile;
import shadows.apotheosis.util.CommonTooltipUtil;
import shadows.placebo.compat.TOPCompat;
import shadows.placebo.compat.TOPCompat.Provider;

public class EnchTOPPlugin implements Provider {
   public static void register() {
      TOPCompat.registerProvider(new EnchTOPPlugin());
   }

   public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData hitData) {
      if (level.m_7702_(hitData.getPos()) instanceof AnvilTile anvil) {
         Object2IntMap<Enchantment> enchants = anvil.getEnchantments();
         ObjectIterator var9 = enchants.object2IntEntrySet().iterator();

         while (var9.hasNext()) {
            Entry<Enchantment> e = (Entry<Enchantment>)var9.next();
            info.text(((Enchantment)e.getKey()).m_44700_(e.getIntValue()));
         }
      }

      CommonTooltipUtil.appendBlockStats(level, state, info::mcText);
      if (state.m_60734_() == Blocks.f_50201_) {
         CommonTooltipUtil.appendTableStats(level, hitData.getPos(), info::mcText);
      }
   }
}
