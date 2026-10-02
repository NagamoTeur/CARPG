package com.hollingsworth.arsnouveau.common.items.curios;

import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.util.CuriosUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.mixin.LivingAccessor;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import top.theillusivec4.curios.api.SlotContext;

public class JumpingRing extends ArsNouveauCurio {
   public void curioTick(SlotContext slotContext, ItemStack stack) {
      super.curioTick(slotContext, stack);
   }

   public static void doJump(Player player) {
      if (CuriosUtil.hasItem(player, ItemsRegistry.JUMP_RING.get())) {
         IManaCap manaCap = (IManaCap)CapabilityRegistry.getMana(player).orElse(null);
         if (manaCap == null || manaCap.getCurrentMana() < (double)((Integer)Config.JUMP_RING_COST.get()).intValue() && !player.m_7500_()) {
            return;
         }

         manaCap.removeMana((double)((Integer)Config.JUMP_RING_COST.get()).intValue());
         LivingAccessor accessor = (LivingAccessor)player;
         double d0 = (double)accessor.callGetJumpPower() + player.m_182332_() + 0.1F;
         Vec3 lookVec = player.m_20154_();
         double lookScale = 0.7;
         player.m_20334_(lookVec.f_82479_ * lookScale, d0, lookVec.f_82481_ * lookScale);
         player.f_19812_ = true;
         player.f_19864_ = true;
         player.f_19789_ = 0.0F;
         ForgeHooks.onLivingJump(player);
      }
   }
}
