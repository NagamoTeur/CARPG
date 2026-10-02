package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import shadows.apotheosis.ench.EnchModule;

public class NaturesBlessingEnchant extends Enchantment {
   public NaturesBlessingEnchant() {
      super(Rarity.RARE, EnchModule.HOE, new EquipmentSlot[0]);
   }

   public boolean m_6081_(ItemStack stack) {
      return stack.m_41720_() instanceof HoeItem;
   }

   public int m_6586_() {
      return 3;
   }

   public int m_6183_(int level) {
      return 25 + level * 10;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public void rightClick(RightClickBlock e) {
      ItemStack s = e.getItemStack();
      int nbLevel = s.getEnchantmentLevel(this);
      if (!e.getEntity().m_6144_() && nbLevel > 0 && BoneMealItem.applyBonemeal(s.m_41777_(), e.getLevel(), e.getPos(), e.getEntity())) {
         s.m_41622_(Math.max(1, 6 - nbLevel), e.getEntity(), ent -> ent.m_21190_(e.getHand()));
         e.setCanceled(true);
         e.setCancellationResult(InteractionResult.SUCCESS);
      }
   }
}
