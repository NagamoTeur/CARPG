package net.xylonity.knightquest.common.item;

import dev.xylonity.knightlib.compat.block.ChaliceBlock;
import dev.xylonity.knightlib.compat.registry.KnightLibBlocks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.xylonity.knightquest.config.values.KQConfigValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class KQWeaponItem extends SwordItem {
   public KQWeaponItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   @NotNull
   public InteractionResult m_6225_(@NotNull UseOnContext ctx) {
      Level level = ctx.m_43725_();
      BlockPos pos = ctx.m_8083_();
      BlockState blockState = level.m_8055_(pos);
      ItemStack stack = ctx.m_43722_();
      CompoundTag tag = stack.m_41784_();
      if (level.f_46443_
         || !blockState.m_60713_((Block)KnightLibBlocks.GREAT_CHALICE.get())
         || !((Integer)blockState.m_61143_(ChaliceBlock.fill)).equals(10)
         || !this.isEnabled()) {
         return InteractionResult.PASS;
      } else if (!tag.m_128471_("Activated")) {
         tag.m_128379_("Activated", true);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      BlockHitResult blockHit = m_41435_(level, player, Fluid.NONE);
      if (blockHit.m_6662_() == Type.BLOCK) {
         BlockState blockState = level.m_8055_(blockHit.m_82425_());
         if (blockState.m_60713_((Block)KnightLibBlocks.GREAT_CHALICE.get())
            && (((Integer)blockState.m_61143_(ChaliceBlock.fill)).equals(10) || ((Integer)blockState.m_61143_(ChaliceBlock.fill)).equals(1))) {
            return InteractionResultHolder.m_19100_(stack);
         }
      }

      CompoundTag tag = stack.m_41784_();
      if (!tag.m_128471_("Activated")) {
         return InteractionResultHolder.m_19100_(stack);
      } else {
         long currentTime = level.m_46467_();
         long lastUsed = tag.m_128454_("LastUsed");
         int cooldownTicks = this.getCooldownTicks();
         if (currentTime - lastUsed >= (long)cooldownTicks) {
            this.interaction(level, player, hand);
            if (!level.f_46443_) {
               tag.m_128356_("LastUsed", currentTime);
               player.m_36335_().m_41524_(this, cooldownTicks);
            }

            return InteractionResultHolder.m_19092_(stack, level.f_46443_);
         } else {
            return InteractionResultHolder.m_19100_(stack);
         }
      }
   }

   public void m_7373_(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
      if (this.isEnabled()) {
         pTooltipComponents.add(Component.m_237115_("tooltip.weapon.knightquest." + this.getName()));
         boolean isActivated = pStack.m_41784_().m_128471_("Activated");
         if (!isActivated) {
            pTooltipComponents.add(Component.m_237115_("tooltip.weapon.knightquest.disabled_abilities"));
         }

         pTooltipComponents.add(
            Component.m_237110_(
               "tooltip.weapon.knightquest." + this.getName() + ".active",
               new Object[]{
                  (float)KQConfigValues.SPEED_TICKS_KUKRI / 20.0F,
                  (float)KQConfigValues.INV_TICKS_PALADIN / 20.0F,
                  (int)Math.floor(KQConfigValues.EXTRA_DAMAGE_UCHIGATANA * 100.0),
                  (float)KQConfigValues.REFLECTION_TIME_KHOPESH / 20.0F,
                  KQConfigValues.TICKS_CLEAVER / 20
               }
            )
         );
         pTooltipComponents.add(
            Component.m_237110_(
               "tooltip.weapon.knightquest." + this.getName() + ".passive",
               new Object[]{
                  (int)Math.floor(KQConfigValues.EXTRA_DAMAGE_PASSIVE_UCHIGATANA * 100.0),
                  (int)Math.floor(KQConfigValues.ENEMY_HEALTH_PASSIVE_UCHIGATANA * 100.0),
                  (int)Math.floor(KQConfigValues.CHANCE_BURN_KHOPESH * 100.0),
                  KQConfigValues.REGEN_HP_PALADIN,
                  KQConfigValues.REGEN_TICKS_PALADIN / 20,
                  (int)Math.floor(KQConfigValues.REGEN_MAX_PALADIN * 100.0),
                  (int)Math.floor(KQConfigValues.EXTRA_DAMAGE_PASSIVE_CLEAVER * 100.0),
                  (int)Math.floor(KQConfigValues.ENEMY_HEALTH_PASSIVE_CLEAVER * 100.0)
               }
            )
         );
         if (!isActivated) {
            pTooltipComponents.add(Component.m_237115_("tooltip.weapon.knightquest.use_chalice"));
         }
      }

      super.m_7373_(pStack, pLevel, pTooltipComponents, pIsAdvanced);
   }

   public abstract void interaction(Level var1, Player var2, InteractionHand var3);

   public abstract int getCooldownTicks();

   public abstract String getName();

   protected abstract boolean isEnabled();
}
