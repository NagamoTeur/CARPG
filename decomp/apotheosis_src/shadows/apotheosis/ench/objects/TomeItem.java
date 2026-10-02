package shadows.apotheosis.ench.objects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.EnchModule;
import shadows.apotheosis.ench.table.IEnchantableItem;

public class TomeItem extends BookItem implements IEnchantableItem {
   final ItemStack rep;
   final EnchantmentCategory type;

   public TomeItem(Item rep, EnchantmentCategory type) {
      super(new Properties().m_41491_(Apotheosis.APOTH_GROUP));
      this.type = type;
      this.rep = new ItemStack(rep);
      EnchModule.TYPED_BOOKS.add(this);
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return stack.m_41613_() == 1;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return this.type == null
         ? EnchModule.TYPED_BOOKS.stream().filter(b -> b != this).allMatch(b -> !enchantment.m_6081_(new ItemStack(b)))
         : enchantment.canApplyAtEnchantingTable(this.rep);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("info.apotheosis." + ForgeRegistries.ITEMS.getKey(this).m_135815_()).m_130940_(ChatFormatting.GRAY));
      if (stack.m_41793_()) {
         tooltip.add(Component.m_237115_("info.apotheosis.tome_error").m_130940_(ChatFormatting.RED));
      }
   }

   public Rarity m_41460_(ItemStack stack) {
      return !stack.m_41793_() ? super.m_41460_(stack) : Rarity.UNCOMMON;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (stack.m_41793_()) {
         ItemStack book = new ItemStack(Items.f_42690_, stack.m_41613_());
         EnchantmentHelper.m_44865_(EnchantmentHelper.m_44831_(stack), book);
         return InteractionResultHolder.m_19096_(book);
      } else {
         return InteractionResultHolder.m_19098_(stack);
      }
   }

   @Override
   public ItemStack onEnchantment(ItemStack stack, List<EnchantmentInstance> enchantments) {
      stack = new ItemStack(Items.f_42690_);

      for (EnchantmentInstance inst : enchantments) {
         EnchantedBookItem.m_41153_(stack, inst);
      }

      return stack;
   }

   @Override
   public boolean forciblyAllowsTableEnchantment(ItemStack stack, Enchantment enchantment) {
      return this.canApplyAtEnchantingTable(stack, enchantment);
   }
}
