package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class CasterTome extends ModItem implements ICasterTool {
   public CasterTome(Properties properties) {
      super(properties);
   }

   public CasterTome() {
   }

   @Override
   public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack tableStack) {
      return player.m_7500_() && ICasterTool.super.onScribe(world, pos, player, handIn, tableStack);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.m_21120_(handIn);
      ISpellCaster caster = this.getSpellCaster(stack);
      Spell spell = caster.getSpell();
      if (spell.getDiscountedCost() > ManaUtil.getMaxMana(playerIn)) {
         spell.addDiscount(spell.getDiscountedCost() - ManaUtil.getMaxMana(playerIn));
      } else {
         spell.addDiscount(spell.getDiscountedCost() / 2);
      }

      return caster.castSpell(worldIn, playerIn, handIn, Component.m_237115_(""), spell);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (worldIn != null) {
         ISpellCaster caster = this.getSpellCaster(stack);
         Spell spell = caster.getSpell();
         this.getInformation(stack, worldIn, tooltip2, flagIn);
         tooltip2.add(Component.m_237115_("tooltip.ars_nouveau.caster_tome"));
         super.m_7373_(stack, worldIn, tooltip2, flagIn);
      }
   }
}
