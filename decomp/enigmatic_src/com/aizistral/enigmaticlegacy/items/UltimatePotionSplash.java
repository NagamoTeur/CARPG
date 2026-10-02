package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.items.IAdvancedPotionItem;
import com.aizistral.enigmaticlegacy.entities.EnigmaticPotionEntity;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.helpers.PotionHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.objects.AdvancedPotion;
import com.aizistral.enigmaticlegacy.registries.EnigmaticPotions;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UltimatePotionSplash extends ItemBase implements IAdvancedPotionItem {
   public IAdvancedPotionItem.PotionType potionType;

   public UltimatePotionSplash(Rarity rarity, IAdvancedPotionItem.PotionType type) {
      super(ItemBase.getDefaultProperties().m_41497_(rarity).m_41487_(1).m_41491_(EnigmaticLegacy.POTION_TAB));
      this.potionType = type;
   }

   public String m_5671_(ItemStack stack) {
      return this.m_5524_() + ".effect." + PotionHelper.getAdvancedPotion(stack).getId();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(ItemStack stack) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack m_7968_() {
      ItemStack stack = super.m_7968_().m_41777_();
      PotionHelper.setAdvancedPotion(stack, EnigmaticPotions.EMPTY_POTION);
      return stack.m_41777_();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      SuperpositionHandler.addPotionTooltip(PotionHelper.getEffects(stack), stack, list, 1.0F);
   }

   public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
      if (this.m_220152_(group)) {
         if (this.potionType == IAdvancedPotionItem.PotionType.COMMON) {
            for (AdvancedPotion potion : EnigmaticPotions.COMMON_POTIONS) {
               ItemStack stack = new ItemStack(this);
               ItemNBTHelper.setString(stack, "EnigmaticPotion", potion.getId());
               items.add(stack);
            }
         } else {
            for (AdvancedPotion potion : EnigmaticPotions.ULTIMATE_POTIONS) {
               ItemStack stack = new ItemStack(this);
               ItemNBTHelper.setString(stack, "EnigmaticPotion", potion.getId());
               items.add(stack);
            }
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      ItemStack throwed = playerIn.m_150110_().f_35937_ ? itemstack.m_41777_() : itemstack.m_41620_(1);
      worldIn.m_6263_(
         (Player)null,
         playerIn.m_20185_(),
         playerIn.m_20186_(),
         playerIn.m_20189_(),
         SoundEvents.f_12091_,
         SoundSource.PLAYERS,
         0.5F,
         0.4F / (random.nextFloat() * 0.4F + 0.8F)
      );
      if (!worldIn.f_46443_) {
         EnigmaticPotionEntity potionEntity = new EnigmaticPotionEntity(worldIn, playerIn);
         potionEntity.m_37446_(throwed);
         potionEntity.m_37251_(playerIn, playerIn.m_146909_(), playerIn.m_146908_(), -20.0F, 0.5F, 1.0F);
         potionEntity.m_5602_(playerIn);
         worldIn.m_7967_(potionEntity);
      }

      playerIn.m_36246_(Stats.f_12982_.m_12902_(this));
      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }

   @Override
   public IAdvancedPotionItem.PotionType getPotionType() {
      return this.potionType;
   }
}
