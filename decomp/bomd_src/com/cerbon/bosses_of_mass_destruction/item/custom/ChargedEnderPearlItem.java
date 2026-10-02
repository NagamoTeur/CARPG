package com.cerbon.bosses_of_mass_destruction.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChargedEnderPearlItem extends Item {
   public ChargedEnderPearlItem(Properties properties) {
      super(properties);
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, @NotNull InteractionHand usedHand) {
      ItemStack itemStack = player.m_21120_(usedHand);
      level.m_6263_(
         null,
         player.m_20185_(),
         player.m_20186_(),
         player.m_20189_(),
         SoundEvents.f_11857_,
         SoundSource.NEUTRAL,
         0.5F,
         0.4F / (level.m_213780_().m_188501_() * 0.4F + 0.8F)
      );
      int itemCooldown = 180;
      player.m_36335_().m_41524_(this, itemCooldown);
      if (!level.m_5776_()) {
         ThrowableItemProjectile projectile = new ChargedEnderPearlEntity(level, player);
         projectile.m_37446_(itemStack);
         projectile.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, 1.5F, 1.0F);
         level.m_7967_(projectile);
      }

      player.m_36246_(Stats.f_12982_.m_12902_(this));
      return InteractionResultHolder.m_19092_(itemStack, level.m_5776_());
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
      tooltipComponents.add(Component.m_237115_("item.bosses_of_mass_destruction.charged_ender_pearl.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
      tooltipComponents.add(Component.m_237115_("item.bosses_of_mass_destruction.charged_ender_pearl.tooltip2").m_130940_(ChatFormatting.DARK_GRAY));
      super.m_7373_(stack, level, tooltipComponents, isAdvanced);
   }
}
