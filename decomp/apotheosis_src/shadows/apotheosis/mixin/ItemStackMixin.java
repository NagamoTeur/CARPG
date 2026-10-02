package shadows.apotheosis.mixin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.DoubleStream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.ench.asm.EnchHooks;

@Mixin({ItemStack.class})
public class ItemStackMixin {
   @Unique
   private static final MutableComponent apotheosis$SPACE = Component.m_237113_(" ");

   @Inject(
      method = {"getHoverName"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void apoth_affixItemName(CallbackInfoReturnable<Component> ci) {
      ItemStack ths = (ItemStack)this;
      CompoundTag afxData = ths.m_41737_("affix_data");
      if (afxData != null && afxData.m_128425_("name", 8)) {
         try {
            Component component = AffixHelper.getName(ths);
            if (component.m_214077_() instanceof TranslatableContents tContents) {
               int idx = "misc.apotheosis.affix_name.four".equals(tContents.m_237508_()) ? 2 : 1;
               tContents.m_237523_()[idx] = ci.getReturnValue();
               ci.setReturnValue(component);
            } else {
               afxData.m_128473_("name");
            }
         } catch (Exception var7) {
            afxData.m_128473_("name");
         }
      }
   }

   @Inject(
      method = {"getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;"},
      at = {@At(
         value = "INVOKE",
         ordinal = 3,
         target = "net/minecraft/world/item/ItemStack.shouldShowInTooltip(ILnet/minecraft/world/item/ItemStack$TooltipPart;)Z"
      )},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   public void apoth_tooltipMarker(@Nullable Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
      list.add(Component.m_237113_("APOTH_REMOVE_MARKER"));
   }

   @Inject(
      method = {"getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;"},
      at = {@At(
         value = "INVOKE",
         ordinal = 1,
         target = "net/minecraft/world/item/ItemStack.hasTag()Z"
      )},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   public void apoth_tooltipMarker2(@Nullable Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
      list.add(Component.m_237113_("APOTH_REMOVE_MARKER_2"));
   }

   @ModifyVariable(
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/world/item/ItemStack.getDamageValue()I"
      ),
      method = {"hurt"},
      argsOnly = true,
      ordinal = 0
   )
   public int swapDura(int amount, int amountCopy, RandomSource pRandom, @Nullable ServerPlayer pUser) {
      int blocked = 0;
      DoubleStream chances = AffixHelper.getAffixes((ItemStack)this).values().stream().mapToDouble(inst -> (double)inst.getDurabilityBonusPercentage(pUser));
      double chance = chances.reduce(0.0, (res, ele) -> res + (1.0 - res) * ele);
      int delta = 1;
      if (chance < 0.0) {
         delta = -1;
         chance = -chance;
      }

      if (chance > 0.0) {
         for (int i = 0; i < amount; i++) {
            if ((double)pRandom.m_188501_() <= chance) {
               blocked += delta;
            }
         }
      }

      return amount - blocked;
   }

   @Unique
   private static void appendModifiedEnchTooltip(List<Component> tooltip, Enchantment ench, int realLevel, int nbtLevel) {
      MutableComponent mc = ench.m_44700_(realLevel).m_6881_();
      mc.m_7360_().clear();
      Component nbtLevelComp = Component.m_237115_("enchantment.level." + nbtLevel);
      Component realLevelComp = Component.m_237115_("enchantment.level." + realLevel);
      if (realLevel != 1 || EnchHooks.getMaxLevel(ench) != 1) {
         mc.m_7220_(apotheosis$SPACE).m_7220_(realLevelComp);
      }

      int diff = realLevel - nbtLevel;
      char sign = (char)(diff > 0 ? 43 : 45);
      Component diffComp = Component.m_237110_("(%s " + sign + " %s)", new Object[]{nbtLevelComp, Component.m_237115_("enchantment.level." + Math.abs(diff))})
         .m_130940_(ChatFormatting.DARK_GRAY);
      mc.m_7220_(apotheosis$SPACE).m_7220_(diffComp);
      if (realLevel == 0) {
         mc.m_130940_(ChatFormatting.DARK_GRAY);
      }

      tooltip.add(mc);
   }

   @Redirect(
      method = {"getTooltipLines"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;appendEnchantmentNames(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V"
      )
   )
   public void apoth_enchTooltipRewrite(List<Component> tooltip, ListTag tagEnchants) {
      ItemStack ths = (ItemStack)this;
      Map<Enchantment, Integer> realLevels = new HashMap<>(ths.getAllEnchantments());
      List<Component> enchTooltips = new ArrayList<>();

      for (int i = tagEnchants.size() - 1; i >= 0; i--) {
         CompoundTag compoundtag = tagEnchants.m_128728_(i);
         Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.m_182446_(compoundtag));
         if (ench != null && realLevels.containsKey(ench)) {
            int nbtLevel = EnchantmentHelper.m_182438_(compoundtag);
            int realLevel = realLevels.remove(ench);
            if (nbtLevel == realLevel) {
               enchTooltips.add(ench.m_44700_(EnchantmentHelper.m_182438_(compoundtag)));
            } else {
               appendModifiedEnchTooltip(enchTooltips, ench, realLevel, nbtLevel);
            }
         }
      }

      Collections.reverse(enchTooltips);
      tooltip.addAll(enchTooltips);

      for (Entry<Enchantment, Integer> real : realLevels.entrySet()) {
         if (real.getValue() > 0) {
            appendModifiedEnchTooltip(tooltip, real.getKey(), real.getValue(), 0);
         }
      }
   }
}
