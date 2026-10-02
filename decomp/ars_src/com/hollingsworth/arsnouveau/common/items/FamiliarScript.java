package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FamiliarScript extends ModItem {
   public AbstractFamiliarHolder familiar;

   public FamiliarScript(AbstractFamiliarHolder familiar) {
      this.familiar = familiar;
   }

   public FamiliarScript(Properties properties) {
      super(properties);
   }

   public FamiliarScript() {
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      if (!worldIn.f_46443_ && handIn == InteractionHand.MAIN_HAND) {
         IPlayerCap familiarCap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(playerIn).orElse(null);
         if (familiarCap != null) {
            if (familiarCap.ownsFamiliar(this.familiar)) {
               playerIn.m_213846_(Component.m_237115_("ars_nouveau.familiar.owned"));
               return super.m_7203_(worldIn, playerIn, handIn);
            }

            familiarCap.unlockFamiliar(this.familiar);
            CapabilityRegistry.EventHandler.syncPlayerCap(playerIn);
            playerIn.m_213846_(Component.m_237115_("ars_nouveau.familiar.unlocked"));
            playerIn.m_21120_(handIn).m_41774_(1);
         }

         return super.m_7203_(worldIn, playerIn, handIn);
      } else {
         return super.m_7203_(worldIn, playerIn, handIn);
      }
   }

   public Component m_7626_(ItemStack pStack) {
      return Component.m_237110_("ars_nouveau.bound_script", new Object[]{this.familiar.getLangName().getString()});
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      tooltip2.add(Component.m_237115_("ars_nouveau.familiar.script"));
   }
}
