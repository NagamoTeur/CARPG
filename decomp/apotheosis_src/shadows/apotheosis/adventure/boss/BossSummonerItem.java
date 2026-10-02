package shadows.apotheosis.adventure.boss;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class BossSummonerItem extends Item {
   public BossSummonerItem(Properties properties) {
      super(properties);
   }

   public InteractionResult m_6225_(UseOnContext ctx) {
      Level world = ctx.m_43725_();
      if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         Player player = ctx.m_43723_();
         BossItem item = (BossItem)BossItemManager.INSTANCE
            .getRandomItem(world.m_213780_(), ctx.m_43723_().m_36336_(), new Predicate[]{IDimensional.matches(world), GameStagesCompat.IStaged.matches(player)});
         if (item == null) {
            return InteractionResult.FAIL;
         } else {
            BlockPos pos = ctx.m_8083_().m_121945_(ctx.m_43719_());
            if (!world.m_45772_(item.getSize().m_82338_(pos))) {
               pos = pos.m_7494_();
               if (!world.m_45772_(item.getSize().m_82338_(pos))) {
                  return InteractionResult.FAIL;
               }
            }

            Mob boss = item.createBoss((ServerLevel)world, pos, world.m_213780_(), player.m_36336_());
            boss.m_6710_(player);
            ((ServerLevel)world).m_47205_(boss);
            ctx.m_43722_().m_41774_(1);
            return InteractionResult.SUCCESS;
         }
      }
   }
}
