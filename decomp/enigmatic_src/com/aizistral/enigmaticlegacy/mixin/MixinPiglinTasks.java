package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PiglinAi.class})
public class MixinPiglinTasks {
   private static final String AVARICE_SCROLL_TAG = "enigmaticlegacy:avarice_scroll_effect";

   private static void markPiglinWithCondition(Piglin piglin, Player player) {
      if (player != null
         && piglin != null
         && SuperpositionHandler.hasCurio(player, EnigmaticItems.AVARICE_SCROLL)
         && !piglin.m_19880_().contains("enigmaticlegacy:avarice_scroll_effect")) {
         piglin.m_20049_("enigmaticlegacy:avarice_scroll_effect");
      }
   }

   private static List<ItemStack> distributeExcess(ItemStack stack) {
      List<ItemStack> newStacks = new ArrayList<>();

      while (stack.m_41613_() > stack.m_41741_()) {
         newStacks.add(stack.m_41620_(stack.m_41741_()));
      }

      return newStacks;
   }

   @Inject(
      at = {@At("RETURN")},
      method = {"isWearingGold"},
      cancellable = true
   )
   private static void onWearingGoldCheck(LivingEntity entity, CallbackInfoReturnable<Boolean> info) {
      if (entity instanceof Player player
         && (SuperpositionHandler.hasCurio(player, EnigmaticItems.GOLDEN_RING) || SuperpositionHandler.hasCurio(player, EnigmaticItems.AVARICE_SCROLL))) {
         info.setReturnValue(true);
      }
   }

   @Inject(
      at = {@At("RETURN")},
      method = {"pickUpItem"}
   )
   private static void onPiglinItemPickup(Piglin piglin, ItemEntity itemEntity, CallbackInfo info) {
      UUID ownerID = itemEntity.m_32057_();
      if (!itemEntity.m_6084_() && itemEntity.f_19853_ instanceof ServerLevel && ownerID != null) {
         ServerLevel world = (ServerLevel)itemEntity.f_19853_;
         markPiglinWithCondition(piglin, world.m_46003_(ownerID));
      }
   }

   @Inject(
      at = {@At("RETURN")},
      method = {"mobInteract"}
   )
   private static void onBarterByHand(Piglin piglin, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
      if (info.getReturnValue() == InteractionResult.CONSUME) {
         markPiglinWithCondition(piglin, player);
      }
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"stopHoldingOffHandItem"},
      cancellable = true
   )
   private static void onPiglinBarter(Piglin piglin, boolean repay, CallbackInfo info) {
      ItemStack stack = piglin.m_21120_(InteractionHand.OFF_HAND);
      if (piglin.f_19853_ instanceof ServerLevel && piglin.m_19880_().contains("enigmaticlegacy:avarice_scroll_effect")) {
         piglin.m_20137_("enigmaticlegacy:avarice_scroll_effect");
         if (piglin.m_34667_() && repay && stack.isPiglinCurrency()) {
            info.cancel();
            piglin.m_21008_(InteractionHand.OFF_HAND, ItemStack.f_41583_);
            List<ItemStack> generatedLoot = PiglinAi.m_34996_(piglin);
            List<ItemStack> newStacks = new ArrayList<>();
            generatedLoot.forEach(lootStack -> {
               if (lootStack != null && !lootStack.m_41619_()) {
                  double multiplier = EnigmaticEventHandler.THEY_SEE_ME_ROLLIN.nextDouble() * 2.0;
                  int bonusAmount = (int)Math.round((double)lootStack.m_41613_() * multiplier);
                  int newCount = lootStack.m_41613_() + bonusAmount;
                  lootStack.m_41764_(newCount);
                  newStacks.addAll(distributeExcess(lootStack));
               }
            });
            generatedLoot.addAll(newStacks);
            PiglinAi.m_34860_(piglin, generatedLoot);
         }
      }
   }
}
