package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityBabyFoliaath;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class ItemFoliaathSeed extends Item {
   public ItemFoliaathSeed(Properties properties) {
      super(properties);
   }

   public Entity spawnCreature(ServerLevelAccessor world, Mob entity, double x, double y, double z) {
      if (entity != null) {
         entity.m_7678_(x + 0.5, y, z + 0.5, world.m_6018_().f_46441_.m_188501_() * 360.0F - 180.0F, 0.0F);
         entity.f_20885_ = entity.m_146908_();
         entity.f_20883_ = entity.m_146908_();
         entity.m_6518_(world, world.m_6436_(entity.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
         if (!entity.m_5545_(world, MobSpawnType.MOB_SUMMONED)) {
            return null;
         }

         world.m_7967_(entity);
      }

      return entity;
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Player player = context.m_43723_();
      if (player == null) {
         return InteractionResult.FAIL;
      } else {
         InteractionHand hand = context.m_43724_();
         Direction facing = context.m_43719_();
         ItemStack stack = player.m_21120_(hand);
         BlockPos pos = context.m_8083_();
         Level world = context.m_43725_();
         if (world.f_46443_) {
            return InteractionResult.SUCCESS;
         } else if (!player.m_36204_(pos.m_121945_(facing), facing, stack)) {
            return InteractionResult.FAIL;
         } else {
            Entity entity = this.spawnCreature(
               (ServerLevel)world,
               new EntityBabyFoliaath((EntityType<? extends EntityBabyFoliaath>)EntityHandler.BABY_FOLIAATH.get(), world),
               (double)pos.m_123341_(),
               (double)(pos.m_123342_() + 1),
               (double)pos.m_123343_()
            );
            if (entity != null) {
               if (entity instanceof LivingEntity && stack.m_41788_()) {
                  entity.m_6593_(stack.m_41786_());
               }

               if (!player.m_7500_()) {
                  stack.m_41774_(1);
               }
            }

            return InteractionResult.SUCCESS;
         }
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.2").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.3").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }
}
