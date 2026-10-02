package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
import com.hollingsworth.arsnouveau.api.item.ISpellHotkeyListener;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StackUtil {
   @NotNull
   public static ItemStack getHeldSpellbook(Player playerEntity) {
      ItemStack book = playerEntity.m_21205_().m_41720_() instanceof SpellBook ? playerEntity.m_21205_() : null;
      return book == null ? (playerEntity.m_21206_().m_41720_() instanceof SpellBook ? playerEntity.m_21206_() : ItemStack.f_41583_) : book;
   }

   @Nullable
   public static InteractionHand getBookHand(Player playerEntity) {
      ItemStack mainStack = playerEntity.m_21205_();
      ItemStack offStack = playerEntity.m_21206_();
      if (mainStack.m_41720_() instanceof SpellBook) {
         return InteractionHand.MAIN_HAND;
      } else {
         return offStack.m_41720_() instanceof SpellBook ? InteractionHand.OFF_HAND : null;
      }
   }

   @Nullable
   public static InteractionHand getHeldCasterTool(Player player) {
      InteractionHand casterTool = player.m_21205_().m_41720_() instanceof ICasterTool ? InteractionHand.MAIN_HAND : null;
      return casterTool == null ? (player.m_21206_().m_41720_() instanceof ICasterTool ? InteractionHand.OFF_HAND : null) : casterTool;
   }

   @Nullable
   public static InteractionHand getHeldCasterTool(Player player, Predicate<ICasterTool> filter) {
      if (player.m_21205_().m_41720_() instanceof ICasterTool casterTool && filter.test(casterTool)) {
         return InteractionHand.MAIN_HAND;
      }

      if (player.m_21206_().m_41720_() instanceof ICasterTool casterTool && filter.test(casterTool)) {
         return InteractionHand.OFF_HAND;
      }

      return null;
   }

   @Nullable
   public static InteractionHand getQuickCaster(Player player) {
      InteractionHand var10000;
      label24: {
         if (player.m_21205_().m_41720_() instanceof ISpellHotkeyListener listener && listener.canQuickCast()) {
            var10000 = InteractionHand.MAIN_HAND;
            break label24;
         }

         var10000 = null;
      }

      InteractionHand casterTool = var10000;
      if (casterTool == null) {
         if (player.m_21206_().m_41720_() instanceof ISpellHotkeyListener listener && listener.canQuickCast()) {
            return InteractionHand.OFF_HAND;
         }

         var10000 = null;
      } else {
         var10000 = casterTool;
      }

      return var10000;
   }

   @NotNull
   public static ItemStack getHeldRadial(Player playerEntity) {
      ItemStack book = playerEntity.m_21205_().m_41720_() instanceof IRadialProvider ? playerEntity.m_21205_() : ItemStack.f_41583_;
      return book.m_41619_() ? playerEntity.m_21206_() : book;
   }

   public static ItemStack getHeldCasterToolOrEmpty(Player player) {
      ItemStack stack = ItemStack.f_41583_;
      if (player.m_21120_(InteractionHand.MAIN_HAND).m_41720_() instanceof ICasterTool) {
         stack = player.m_21120_(InteractionHand.MAIN_HAND);
      } else if (player.m_21120_(InteractionHand.OFF_HAND).m_41720_() instanceof ICasterTool) {
         stack = player.m_21120_(InteractionHand.OFF_HAND);
      }

      return stack;
   }
}
