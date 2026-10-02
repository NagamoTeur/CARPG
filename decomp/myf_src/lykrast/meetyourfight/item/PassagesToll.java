package lykrast.meetyourfight.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PassagesToll extends Item {
   public PassagesToll(Properties properties) {
      super(properties);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Level world = context.m_43725_();
      if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockPos pos = context.m_8083_();
         Direction dir = context.m_43719_().m_122424_();
         Player player = context.m_43723_();
         player.m_36335_().m_41524_(this, 20);
         MutableBlockPos mut = new MutableBlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
         boolean found = false;

         label39:
         for (int i = 0; i < 16; i++) {
            mut.m_122173_(dir);
            if (mut.m_123342_() <= world.m_141937_()) {
               break;
            }

            if (!world.m_8055_(mut).m_60767_().m_76334_()) {
               found = true;
               double targetY = player.m_20186_();

               while ((double)mut.m_123342_() > targetY && mut.m_123342_() > 1) {
                  mut.m_122184_(0, -1, 0);
                  if (world.m_8055_(mut).m_60767_().m_76334_()) {
                     mut.m_122184_(0, 1, 0);
                     break label39;
                  }
               }
               break;
            }
         }

         if (!found) {
            return InteractionResult.FAIL;
         } else {
            player.m_6021_((double)mut.m_123341_() + 0.5, (double)mut.m_123342_() + 0.5, (double)mut.m_123343_() + 0.5);
            player.f_19789_ = 0.0F;
            world.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11757_, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.m_5496_(SoundEvents.f_11757_, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
         }
      }
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
   }
}
