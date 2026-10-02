package shadows.apotheosis.ench.enchantments.twisted;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class MinersFervorEnchant extends DiggingEnchantment {
   public MinersFervorEnchant() {
      super(Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6183_(int enchantmentLevel) {
      return 45 + (enchantmentLevel - 1) * 30;
   }

   public int m_6175_(int enchantmentLevel) {
      return this.m_6183_(enchantmentLevel) + 50;
   }

   public int m_6586_() {
      return 5;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_PURPLE);
   }

   protected boolean m_5975_(Enchantment e) {
      return super.m_5975_(e) && e != Enchantments.f_44984_;
   }

   public void breakSpeed(BreakSpeed e) {
      Player p = e.getEntity();
      ItemStack stack = p.m_21205_();
      if (!stack.m_41619_()) {
         int level = stack.getEnchantmentLevel(this);
         if (level > 0 && stack.m_41691_(e.getState()) > 1.0F) {
            float hardness = e.getState().m_60800_(p.f_19853_, e.getPosition().orElse(BlockPos.f_121853_));
            e.setNewSpeed(Math.min(29.9999F, 7.5F + 4.5F * (float)level) * hardness);
         }
      }
   }
}
