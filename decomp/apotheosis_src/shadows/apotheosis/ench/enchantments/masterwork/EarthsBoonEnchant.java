package shadows.apotheosis.ench.enchantments.masterwork;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.EnchModule;

public class EarthsBoonEnchant extends Enchantment {
   public EarthsBoonEnchant() {
      super(Rarity.VERY_RARE, EnchModule.PICKAXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6586_() {
      return 3;
   }

   public int m_6183_(int level) {
      return 60 + (level - 1) * 20;
   }

   public int m_6175_(int enchantmentLevel) {
      return 200;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_GREEN);
   }

   public void provideBenefits(BreakEvent e) {
      Player player = e.getPlayer();
      ItemStack stack = player.m_21205_();
      int level = stack.getEnchantmentLevel(this);
      if (!player.f_19853_.f_46443_) {
         if (e.getState().m_204336_(Blocks.STONE) && level > 0 && player.f_19796_.m_188501_() <= 0.01F * (float)level) {
            ItemStack newDrop = new ItemStack(
               (ItemLike)ForgeRegistries.ITEMS.tags().getTag(Apoth.Tags.BOON_DROPS).getRandomElement(player.f_19796_).orElse(Items.f_41852_)
            );
            Block.m_49840_(player.f_19853_, e.getPos(), newDrop);
         }
      }
   }
}
