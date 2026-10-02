package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EnchantmentMenu.class})
public abstract class MixinEnchantmentContainer extends AbstractContainerMenu {
   protected MixinEnchantmentContainer(MenuType<?> type, int id) {
      super(type, id);
   }

   @Inject(
      at = {@At("INVOKE")},
      method = {"net.minecraft.world.inventory.EnchantmentMenu.clickMenuButton(Lnet/minecraft/world/entity/player/Player;I)Z"},
      cancellable = true
   )
   private void onEnchantedItem(Player player, int clickedID, CallbackInfoReturnable<Boolean> info) {
      if (EnchantmentMenu.class.isInstance(this)) {
         EnchantmentMenu container = (EnchantmentMenu)this;
         if (EnigmaticItems.ENCHANTER_PEARL.isPresent(player)) {
            ItemStack inputItem = container.f_39449_.m_8020_(0);
            int levelsRequired = clickedID + 1;
            if (container.f_39446_[clickedID] > 0
               && !inputItem.m_41619_()
               && (player.f_36078_ >= levelsRequired && player.f_36078_ >= container.f_39446_[clickedID] || player.m_150110_().f_35937_)) {
               container.f_39450_
                  .m_39292_(
                     (world, blockPos) -> {
                        ItemStack enchantedItem = inputItem;
                        List<EnchantmentInstance> rolledEnchantments = container.m_39471_(inputItem, clickedID, container.f_39446_[clickedID]);
                        if (!rolledEnchantments.isEmpty()) {
                           ItemStack doubleRoll = EnchantmentHelper.m_220292_(
                              player.m_217043_(), inputItem.m_41777_(), Math.min(container.f_39446_[clickedID] + 7, 40), true
                           );
                           player.m_7408_(inputItem, levelsRequired);
                           boolean isBookStack = inputItem.m_41720_() == Items.f_42517_;
                           if (isBookStack) {
                              enchantedItem = new ItemStack(Items.f_42690_);
                              CompoundTag compoundnbt = inputItem.m_41783_();
                              if (compoundnbt != null) {
                                 enchantedItem.m_41751_(compoundnbt.m_6426_());
                              }

                              container.f_39449_.m_6836_(0, enchantedItem);
                           }

                           for (EnchantmentInstance enchantmentdata : rolledEnchantments) {
                              if (isBookStack) {
                                 EnchantedBookItem.m_41153_(enchantedItem, enchantmentdata);
                              } else {
                                 enchantedItem.m_41663_(enchantmentdata.f_44947_, enchantmentdata.f_44948_);
                              }
                           }

                           enchantedItem = SuperpositionHandler.mergeEnchantments(enchantedItem, doubleRoll, false, false);
                           enchantedItem = SuperpositionHandler.maybeApplyEternalBinding(enchantedItem);
                           container.f_39449_.m_6836_(0, enchantedItem);
                           player.m_36220_(Stats.f_12964_);
                           if (player instanceof ServerPlayer) {
                              CriteriaTriggers.f_10575_.m_27668_((ServerPlayer)player, enchantedItem, levelsRequired);
                           }

                           container.f_39449_.m_6596_();
                           container.f_39452_.m_6422_(player.m_36322_());
                           container.m_6199_(container.f_39449_);
                           world.m_5594_((Player)null, blockPos, SoundEvents.f_11887_, SoundSource.BLOCKS, 1.0F, world.f_46441_.m_188501_() * 0.1F + 0.9F);
                        }
                     }
                  );
               info.setReturnValue(true);
               return;
            }

            info.setReturnValue(false);
            return;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Inject(
      at = {@At("HEAD")},
      method = {"net.minecraft.world.inventory.EnchantmentMenu.getGoldCount()I"},
      cancellable = true
   )
   public void onGetLapisAmount(CallbackInfoReturnable<Integer> info) {
      if (EnchantmentMenu.class.isInstance(this)) {
         EnchantmentMenu container = (EnchantmentMenu)this;
         Player containerUser = null;

         for (Slot slot : container.f_38839_) {
            if (slot.f_40218_ instanceof Inventory playerInv) {
               containerUser = playerInv.f_35978_;
               break;
            }
         }

         if (containerUser != null && EnigmaticItems.ENCHANTER_PEARL.isPresent(containerUser)) {
            info.setReturnValue(64);
            return;
         }
      }
   }
}
