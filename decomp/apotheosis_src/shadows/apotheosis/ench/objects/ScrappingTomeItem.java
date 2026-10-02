package shadows.apotheosis.ench.objects;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
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
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;

public class ScrappingTomeItem extends BookItem {
   static Random rand = new Random();

   public ScrappingTomeItem() {
      super(new Properties().m_41491_(Apotheosis.APOTH_GROUP));
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
      if (!stack.m_41793_()) {
         tooltip.add(Component.m_237115_("info.apotheosis.scrap_tome").m_130940_(ChatFormatting.GRAY));
         tooltip.add(Component.m_237115_("info.apotheosis.scrap_tome2").m_130940_(ChatFormatting.GRAY));
      }
   }

   public Rarity m_41460_(ItemStack stack) {
      return Rarity.UNCOMMON;
   }

   public static boolean updateAnvil(AnvilUpdateEvent ev) {
      ItemStack weapon = ev.getLeft();
      ItemStack book = ev.getRight();
      if (book.m_41720_() instanceof ScrappingTomeItem && !book.m_41793_() && weapon.m_41793_()) {
         Map<Enchantment, Integer> wepEnch = EnchantmentHelper.m_44831_(weapon);
         int size = Mth.m_14165_((double)wepEnch.size() / 2.0);
         List<Enchantment> keys = Lists.newArrayList(wepEnch.keySet());
         long seed = 1831L;

         for (Enchantment e : keys) {
            seed ^= (long)ForgeRegistries.ENCHANTMENTS.getKey(e).hashCode();
         }

         seed ^= (long)ev.getPlayer().m_36322_();
         rand.setSeed(seed);

         while (wepEnch.size() > size) {
            Enchantment lost = keys.get(rand.nextInt(keys.size()));
            wepEnch.remove(lost);
            keys.remove(lost);
         }

         ItemStack out = new ItemStack(Items.f_42690_);
         EnchantmentHelper.m_44865_(wepEnch, out);
         ev.setMaterialCost(1);
         ev.setCost(wepEnch.size() * 6);
         ev.setOutput(out);
         return true;
      } else {
         return false;
      }
   }
}
