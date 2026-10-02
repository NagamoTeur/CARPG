package daripher.skilltree.mixin.apotheosis;

import daripher.skilltree.container.ContainerHelper;
import daripher.skilltree.container.menu.EnchantmentMenuExtension;
import daripher.skilltree.skill.bonus.SkillBonusHandler;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.apotheosis.ench.table.ApothEnchantmentMenu;

@Mixin(
   value = {ApothEnchantmentMenu.class},
   remap = false
)
public class ApothEnchantContainerMixin {
   @Redirect(
      method = {"lambda$slotsChanged$1"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraftforge/event/ForgeEventFactory;onEnchantmentLevelSet(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;IILnet/minecraft/world/item/ItemStack;I)I"
      )
   )
   private int reduceLevelRequirements(Level level, BlockPos pos, int slot, int power, ItemStack itemStack, int enchantmentLevel) {
      ApothEnchantmentMenu menu = (ApothEnchantmentMenu)this;
      Player player = ContainerHelper.getViewingPlayer(menu);
      int[] costs = menu.f_39446_;
      int cost = ForgeEventFactory.onEnchantmentLevelSet(level, pos, slot, power, itemStack, costs[slot]);
      if (player == null) {
         return cost;
      } else {
         EnchantmentMenuExtension extension = (EnchantmentMenuExtension)this;
         extension.getCostsBeforeReduction()[slot] = cost;
         return SkillBonusHandler.adjustEnchantmentCost(cost, player);
      }
   }

   @Redirect(
      method = {"lambda$slotsChanged$1"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/ench/table/ApothEnchantmentMenu;getEnchantmentList(Lnet/minecraft/world/item/ItemStack;II)Ljava/util/List;",
         remap = true
      )
   )
   private List<EnchantmentInstance> amplifyEnchantmentsVisually(ApothEnchantmentMenu menu, ItemStack itemStack, int slot, int cost) {
      return this.amplifyEnchantments(itemStack, slot);
   }

   @Redirect(
      method = {"lambda$clickMenuButton$0"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/ench/table/ApothEnchantmentMenu;getEnchantmentList(Lnet/minecraft/world/item/ItemStack;II)Ljava/util/List;",
         remap = true
      )
   )
   private List<EnchantmentInstance> amplifyEnchantmentsOnButtonClick(ApothEnchantmentMenu menu, ItemStack itemStack, int slot, int cost) {
      return this.amplifyEnchantments(itemStack, slot);
   }

   protected List<EnchantmentInstance> amplifyEnchantments(ItemStack itemStack, int slot) {
      EnchantmentMenuExtension enchantmentMenu = (EnchantmentMenuExtension)this;
      int[] costsBeforeReduction = enchantmentMenu.getCostsBeforeReduction();
      List<EnchantmentInstance> enchantments = this.m_39471_(itemStack, slot, costsBeforeReduction[slot]);
      int enchantmentSeed = enchantmentMenu.getEnchantmentSeed();
      RandomSource random = RandomSource.m_216335_((long)enchantmentSeed);
      ApothEnchantmentMenu menu = (ApothEnchantmentMenu)this;
      Player player = ContainerHelper.getViewingPlayer(menu);
      if (player == null) {
         return enchantments;
      } else {
         Objects.requireNonNull(enchantments);
         SkillBonusHandler.amplifyEnchantments(enchantments, random, player);
         return enchantments;
      }
   }

   @Shadow(
      remap = true
   )
   private List<EnchantmentInstance> m_39471_(ItemStack stack, int enchantSlot, int level) {
      return null;
   }
}
