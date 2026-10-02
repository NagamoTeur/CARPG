package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.block.IPedestalMachine;
import com.hollingsworth.arsnouveau.api.scrying.SingleBlockScryer;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.ritual.RitualScrying;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DowsingRod extends ModItem {
   public DowsingRod(Properties properties) {
      super(properties);
   }

   public DowsingRod() {
      this(ItemsRegistry.defaultItemProperties().m_41503_(4));
   }

   public InteractionResult m_6225_(UseOnContext pContext) {
      if (pContext.m_43725_() instanceof ServerLevel && pContext.m_43725_().m_7702_(pContext.m_8083_()) instanceof IPedestalMachine ipm) {
         ipm.lightPedestal(pContext.m_43725_());
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6225_(pContext);
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
      ItemStack heldStack = pPlayer.m_21120_(pUsedHand);
      heldStack.m_41721_(pPlayer.m_21120_(pUsedHand).m_41773_() + 1);
      if (heldStack.m_41773_() >= this.getMaxDamage(heldStack)) {
         heldStack.m_41774_(1);
      }

      if (!pLevel.f_46443_) {
         pPlayer.m_7292_(new MobEffectInstance((MobEffect)ModPotions.MAGIC_FIND_EFFECT.get(), 1200));
         SingleBlockScryer singleBlockScryer = new SingleBlockScryer(Blocks.f_152491_);
         RitualScrying.grantScrying((ServerPlayer)pPlayer, 1200, singleBlockScryer);
      }

      return super.m_7203_(pLevel, pPlayer, pUsedHand);
   }
}
