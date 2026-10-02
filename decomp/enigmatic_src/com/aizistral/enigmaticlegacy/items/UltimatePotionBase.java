package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.items.IAdvancedPotionItem;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.helpers.PotionHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.objects.AdvancedPotion;
import com.aizistral.enigmaticlegacy.registries.EnigmaticPotions;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UltimatePotionBase extends ItemBase implements IAdvancedPotionItem {
   public IAdvancedPotionItem.PotionType potionType;

   public UltimatePotionBase(Rarity rarity, IAdvancedPotionItem.PotionType type) {
      super(ItemBase.getDefaultProperties().m_41497_(rarity).m_41487_(1).m_41491_(EnigmaticLegacy.POTION_TAB));
      this.potionType = type;
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

   public String m_5671_(ItemStack stack) {
      return this.m_5524_() + ".effect." + PotionHelper.getAdvancedPotion(stack).getId();
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

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
      Player player = entityLiving instanceof Player ? (Player)entityLiving : null;
      List<MobEffectInstance> effectList = PotionHelper.getEffects(stack);
      if (player == null || !player.m_150110_().f_35937_) {
         stack.m_41774_(1);
      }

      if (player instanceof ServerPlayer) {
         CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)player, stack);
      }

      if (!worldIn.f_46443_) {
         for (MobEffectInstance effectinstance : effectList) {
            if (effectinstance.m_19544_().m_8093_()) {
               effectinstance.m_19544_().m_19461_(player, player, entityLiving, effectinstance.m_19564_(), 1.0);
            } else {
               entityLiving.m_7292_(new MobEffectInstance(effectinstance));
            }
         }
      }

      if (player != null) {
         player.m_36246_(Stats.f_12982_.m_12902_(this));
      }

      if (player == null || !player.m_150110_().f_35937_) {
         if (stack.m_41619_()) {
            return new ItemStack(Items.f_42590_);
         }

         if (player != null) {
            player.m_150109_().m_36054_(new ItemStack(Items.f_42590_));
         }
      }

      return stack;
   }

   public int m_8105_(ItemStack stack) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      playerIn.m_6672_(handIn);
      return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
   }

   @Override
   public IAdvancedPotionItem.PotionType getPotionType() {
      return this.potionType;
   }
}
