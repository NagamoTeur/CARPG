package io.redspace.ironsspellbooks.item;

import io.redspace.ironsspellbooks.api.item.IScroll;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.SpellbookModCreativeTabs;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Scroll extends Item implements IScroll {
   public Scroll() {
      super(new Properties().m_41497_(Rarity.UNCOMMON));
   }

   private AbstractSpell getSpellFromStack(ItemStack itemStack) {
      return ISpellContainer.get(itemStack).getSpellAtIndex(0).getSpell();
   }

   private SpellData getSpellSlotFromStack(ItemStack itemStack) {
      return ISpellContainer.get(itemStack).getSpellAtIndex(0);
   }

   public void m_6787_(@NotNull CreativeModeTab category, @NotNull NonNullList<ItemStack> itemStackList) {
      if (category == CreativeModeTab.f_40754_) {
         SpellRegistry.REGISTRY.get().getValues().stream().filter(AbstractSpell::isEnabled).forEach(spell -> {
            int min = category == SpellbookModCreativeTabs.SPELL_EQUIPMENT_TAB ? spell.getMaxLevel() : spell.getMinLevel();

            for (int i = min; i <= spell.getMaxLevel(); i++) {
               ItemStack itemstack = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
               ISpellContainer spellList = ISpellContainer.createScrollContainer(spell, i, itemstack);
               spellList.save(itemstack);
               itemStackList.add(itemstack);
            }
         });
      }
   }

   protected void removeScrollAfterCast(ServerPlayer serverPlayer, ItemStack stack) {
      if (!serverPlayer.m_7500_()) {
         stack.m_41774_(1);
      }
   }

   public static void attemptRemoveScrollAfterCast(ServerPlayer serverPlayer) {
      ItemStack potentialScroll = MagicData.getPlayerMagicData(serverPlayer).getPlayerCastingItem();
      if (potentialScroll.m_41720_() instanceof Scroll scroll) {
         scroll.removeScrollAfterCast(serverPlayer, potentialScroll);
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      SpellData spellSlot = this.getSpellSlotFromStack(stack);
      AbstractSpell spell = spellSlot.getSpell();
      if (level.f_46443_) {
         if (ClientMagicData.isCasting()) {
            return InteractionResultHolder.m_19096_(stack);
         } else {
            return !ClientMagicData.getSyncedSpellData(player).isSpellLearned(spell)
               ? InteractionResultHolder.m_19098_(stack)
               : InteractionResultHolder.m_19096_(stack);
         }
      } else {
         String castingSlot = hand.ordinal() == 0 ? SpellSelectionManager.MAINHAND : SpellSelectionManager.OFFHAND;
         return spell.attemptInitiateCast(stack, spell.getLevelFor(spellSlot.getLevel(), player), level, player, CastSource.SCROLL, false, castingSlot)
            ? InteractionResultHolder.m_19096_(stack)
            : InteractionResultHolder.m_19100_(stack);
      }
   }

   public int m_8105_(@NotNull ItemStack itemStack) {
      return 7200;
   }

   @NotNull
   public UseAnim m_6164_(@NotNull ItemStack pStack) {
      return UseAnim.BOW;
   }

   public void m_5551_(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity, int ticksUsed) {
      if (this.getSpellFromStack(itemStack).getCastType() != CastType.CONTINUOUS || this.m_8105_(itemStack) - ticksUsed >= 4) {
         Utils.releaseUsingHelper(entity, itemStack, ticksUsed);
      }

      super.m_5551_(itemStack, level, entity, ticksUsed);
   }

   @NotNull
   public Component m_7626_(@NotNull ItemStack itemStack) {
      return this.getSpellSlotFromStack(itemStack).getDisplayName();
   }

   public void m_7373_(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
      if (MinecraftInstanceHelper.instance.player() instanceof LocalPlayer localPlayer) {
         lines.addAll(TooltipsUtils.formatScrollTooltip(itemStack, localPlayer));
      }

      super.m_7373_(itemStack, level, lines, flag);
   }
}
