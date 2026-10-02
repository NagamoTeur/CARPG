package lykrast.meetyourfight.item;

import java.util.List;
import lykrast.meetyourfight.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class DepthStar extends SwordItem {
   private static final Tier TIER = new CustomTier(2, 693, 6.0F, 2.0F, 14, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.mossyTooth.get()}));

   public DepthStar(Properties builderIn) {
      super(TIER, 7, -3.1F, builderIn);
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player player) {
         float strength = this.getShockwaveStrength(this.m_8105_(stack) - timeLeft);
         if ((double)strength >= 0.25) {
            if (!world.f_46443_) {
               Vec3 start = new Vec3(player.m_20185_(), player.m_20188_(), player.m_20189_());
               Vec3 end = player.m_20154_().m_82490_(2.0).m_82549_(start);
               BlockHitResult raytrace = world.m_45547_(new ClipContext(start, end, Block.OUTLINE, Fluid.NONE, player));
               if (raytrace.m_6662_() != Type.MISS) {
                  end = raytrace.m_82450_();
               }

               world.m_46511_(player, end.f_82479_, end.f_82480_, end.f_82481_, strength * 2.0F, BlockInteraction.NONE);
               stack.m_41622_(2, player, entity -> entity.m_21190_(player.m_7655_()));
            }

            player.m_36399_(strength * 2.0F);
            world.m_6263_(
               null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12317_, SoundSource.PLAYERS, 1.0F, 0.8F + strength * 0.5F
            );
         }
      }
   }

   private float getShockwaveStrength(int charge) {
      float str = (float)charge / 20.0F;
      if (str > 1.0F) {
         str = 1.0F;
      }

      return (str * str + str * 2.0F) / 3.0F;
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      playerIn.m_6672_(handIn);
      return InteractionResultHolder.m_19096_(playerIn.m_21120_(handIn));
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
   }
}
