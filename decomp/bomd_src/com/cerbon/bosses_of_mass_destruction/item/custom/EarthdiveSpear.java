package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EarthdiveSpear extends Item {
   private final Multimap<Attribute, AttributeModifier> attributeModifiers;

   public EarthdiveSpear(Properties properties) {
      super(properties);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 8.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.9F, Operation.ADDITION));
      this.attributeModifiers = builder.build();
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
      tooltipComponents.add(Component.m_237115_("item.bosses_of_mass_destruction.earthdive_spear.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
      super.m_7373_(stack, level, tooltipComponents, isAdvanced);
   }

   public void m_5551_(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user, int timeCharged) {
      if (user instanceof Player player
         && this.isCharged(stack, timeCharged)
         && !level.m_5776_()
         && level instanceof ServerLevel serverLevel
         && (
            new WallTeleport(serverLevel, player).tryTeleport(player.m_20154_(), player.m_146892_())
               || new WallTeleport(serverLevel, player).tryTeleport(player.m_20154_(), player.m_146892_().m_82520_(0.0, -1.0, 0.0))
         )) {
         stack.m_41622_(1, player, it -> it.m_21166_(EquipmentSlot.MAINHAND));
         player.m_36246_(Stats.f_12982_.m_12902_(this));
         serverLevel.m_6269_(
            null, player, (SoundEvent)BMDSounds.EARTHDIVE_SPEAR_THROW.get(), SoundSource.PLAYERS, 1.0F, BMDUtils.randomPitch(player.m_217043_())
         );
      }
   }

   public boolean m_7579_(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
      stack.m_41622_(1, attacker, it -> it.m_21166_(EquipmentSlot.MAINHAND));
      return true;
   }

   public boolean m_6813_(@NotNull ItemStack stack, @NotNull Level level, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity miningEntity) {
      if ((double)state.m_60800_(level, pos) != 0.0) {
         stack.m_41622_(2, miningEntity, miner -> miner.m_21166_(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, Player user, @NotNull InteractionHand usedHand) {
      ItemStack itemStack = user.m_21120_(usedHand);
      if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         user.m_6672_(usedHand);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int remainingUseDuration) {
      super.m_5929_(level, user, stack, remainingUseDuration);
      if (level instanceof ServerLevel serverLevel && this.isCharged(stack, remainingUseDuration)) {
         Consumer<BlockPos> teleportAction = pos -> this.spawnTeleportParticles(serverLevel, user);
         WallTeleport wallTeleport = new WallTeleport(serverLevel, user);
         if (wallTeleport.tryTeleport(user.m_20154_(), user.m_146892_(), teleportAction)) {
            wallTeleport.tryTeleport(user.m_20154_(), user.m_146892_().m_82520_(0.0, -1.0, 0.0), teleportAction);
         }
      }
   }

   private void spawnTeleportParticles(ServerLevel level, LivingEntity user) {
      Vec3 pos = user.m_146892_().m_82549_(user.m_20154_().m_82542_(0.15, 0.15, 0.15)).m_82549_(RandomUtils.randVec());
      Vec3 vel = user.m_146892_().m_82549_(user.m_20154_().m_82542_(4.0, 4.0, 4.0)).m_82546_(pos);
      BMDUtils.spawnParticle(level, (ParticleOptions)BMDParticles.EARTHDIVE_INDICATOR.get(), pos, vel, 0, 0.07);
   }

   private boolean isCharged(ItemStack stack, int remainingUseTicks) {
      return this.m_8105_(stack) - remainingUseTicks >= 10;
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot slot) {
      return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.m_7167_(slot);
   }

   public int m_8105_(@NotNull ItemStack stack) {
      return 72000;
   }

   @NotNull
   public UseAnim m_6164_(@NotNull ItemStack stack) {
      return UseAnim.SPEAR;
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 1;
   }
}
