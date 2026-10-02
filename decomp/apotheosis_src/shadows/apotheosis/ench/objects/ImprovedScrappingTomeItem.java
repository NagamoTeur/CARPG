package shadows.apotheosis.ench.objects;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AnvilUpdateEvent;
import shadows.apotheosis.Apotheosis;

public class ImprovedScrappingTomeItem extends BookItem {
   static Random rand = new Random();

   public ImprovedScrappingTomeItem() {
      super(new Properties().m_41491_(Apotheosis.APOTH_GROUP));
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
      if (!stack.m_41793_()) {
         tooltip.add(Component.m_237115_("info.apotheosis.improved_scrap_tome").m_130940_(ChatFormatting.GRAY));
         tooltip.add(Component.m_237115_("info.apotheosis.improved_scrap_tome2").m_130940_(ChatFormatting.GRAY));
      }
   }

   public Rarity m_41460_(ItemStack stack) {
      return Rarity.RARE;
   }

   public static boolean updateAnvil(AnvilUpdateEvent ev) {
      ItemStack weapon = ev.getLeft();
      ItemStack book = ev.getRight();
      if (book.m_41720_() instanceof ImprovedScrappingTomeItem && !book.m_41793_() && weapon.m_41793_()) {
         Map<Enchantment, Integer> wepEnch = EnchantmentHelper.m_44831_(weapon);
         ItemStack out = new ItemStack(Items.f_42690_);
         EnchantmentHelper.m_44865_(wepEnch, out);
         ev.setMaterialCost(1);
         ev.setCost(wepEnch.size() * 10);
         ev.setOutput(out);
         return true;
      } else {
         return false;
      }
   }
}
