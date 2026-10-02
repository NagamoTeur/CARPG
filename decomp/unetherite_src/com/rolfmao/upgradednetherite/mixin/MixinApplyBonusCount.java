package com.rolfmao.upgradednetherite.mixin;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.GoldUtil;
import java.util.Map;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({ApplyBonusCount.class})
public class MixinApplyBonusCount {
   @Shadow
   @Final
   Enchantment f_79899_;

   @ModifyVariable(
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "net/minecraft/world/item/enchantment/EnchantmentHelper.getItemEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/item/ItemStack;)I"
      ),
      method = {"run"}
   )
   private int upgradedFortune(int enchantLevel, ItemStack itemStack, LootContext lootContext) {
      if (this.f_79899_ == Enchantments.f_44987_) {
         ItemStack tool = (ItemStack)lootContext.m_78953_(LootContextParams.f_81463_);
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(tool);
         if (GoldUtil.isGoldTool(tool) && UpgradedNetheriteConfig.EnableFortuneBonus) {
            if (!enchantments.isEmpty() && enchantments.containsKey(Enchantments.f_44987_)) {
               int EnchantLevel = enchantments.get(Enchantments.f_44987_);
               return EnchantLevel >= 3
                  ? enchantLevel + UpgradedNetheriteConfig.FortuneBonus + UpgradedNetheriteConfig.FortuneEnchantBonus
                  : enchantLevel + UpgradedNetheriteConfig.FortuneBonus;
            } else {
               return enchantLevel + UpgradedNetheriteConfig.FortuneBonus;
            }
         } else {
            return CorruptUtil.isCorruptTool(tool)
                  && UpgradedNetheriteConfig.EnableFortuneBonusCorruptTool
                  && lootContext.m_78953_(LootContextParams.f_81455_) instanceof Player
               ? enchantLevel
                  + CorruptUtil.intWearingCorrupt((Player)lootContext.m_78953_(LootContextParams.f_81455_), true)
                     * UpgradedNetheriteConfig.FortuneBonusCorruptTool
               : enchantLevel;
         }
      } else {
         return enchantLevel;
      }
   }
}
