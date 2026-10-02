package lykrast.meetyourfight.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class SummonItem extends Item {
   private SummonItem.BossSpawner spawner;

   public SummonItem(Properties properties, SummonItem.BossSpawner spawner) {
      super(properties);
      this.spawner = spawner;
   }

   public ItemStack m_5922_(ItemStack stack, Level world, LivingEntity entityLiving) {
      if (!(entityLiving instanceof Player player)) {
         return stack;
      } else {
         if (!world.f_46443_) {
            if (!world.m_6443_(Mob.class, player.m_20191_().m_82400_(32.0), e -> !e.m_6072_() && e.m_6084_()).isEmpty()) {
               player.m_5661_(Component.m_237115_("status.meetyourfight.boss_nearby"), true);
               return stack;
            }

            this.spawner.spawn(player, world);
            if (!player.m_150110_().f_35937_) {
               stack.m_41774_(1);
               player.m_21190_(player.m_7655_());
            }

            player.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         return stack;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      player.m_6672_(hand);
      return InteractionResultHolder.m_19096_(player.m_21120_(hand));
   }

   public int m_8105_(ItemStack stack) {
      return 20;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
   }

   @FunctionalInterface
   public interface BossSpawner {
      void spawn(Player var1, Level var2);
   }
}
