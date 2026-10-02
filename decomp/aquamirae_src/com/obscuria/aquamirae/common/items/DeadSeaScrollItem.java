package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.ScrollEffects;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class DeadSeaScrollItem extends Item {
   public DeadSeaScrollItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(8).m_41497_(Rarity.UNCOMMON));
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(@NotNull ItemStack stack) {
      return Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.m_21205_().m_41720_() == AquamiraeItems.DEAD_SEA_SCROLL.get();
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
      InteractionResultHolder<ItemStack> resultHolder = super.m_7203_(level, player, hand);
      if (level.f_46443_) {
         Minecraft.m_91087_().f_91063_.m_109113_(((ItemStack)resultHolder.m_19095_()).m_41777_());
      }

      level.m_7785_(
         (double)player.m_146903_(),
         (double)player.m_146904_(),
         (double)player.m_146907_(),
         (SoundEvent)AquamiraeSounds.ITEM_SCROLL_USE.get(),
         SoundSource.PLAYERS,
         1.0F,
         1.0F,
         false
      );
      player.m_36335_().m_41524_(((ItemStack)resultHolder.m_19095_()).m_41720_(), 100);
      ((ItemStack)resultHolder.m_19095_()).m_41774_(1);
      player.m_6674_(hand);
      ScrollEffects.create(player);
      return resultHolder;
   }
}
