package com.github.L_Ender.cataclysm.items.Dungeon_Eye;

import com.github.L_Ender.cataclysm.entity.projectile.Eye_Of_Dungeon_Entity;
import com.github.L_Ender.cataclysm.init.ModTag;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

public class CursedEyeItem extends Item {
   public CursedEyeItem(Properties group) {
      super(group);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41184_, Player p_41185_, InteractionHand p_41186_) {
      ItemStack itemstack = p_41185_.m_21120_(p_41186_);
      p_41185_.m_6672_(p_41186_);
      if (p_41184_ instanceof ServerLevel serverlevel) {
         BlockPos blockpos = serverlevel.m_215011_(ModTag.EYE_OF_CURSE_LOCATED, p_41185_.m_20183_(), 100, false);
         if (blockpos != null) {
            Eye_Of_Dungeon_Entity eyeofender = new Eye_Of_Dungeon_Entity(p_41184_, p_41185_.m_20185_(), p_41185_.m_20227_(0.5), p_41185_.m_20189_());
            eyeofender.setItem(itemstack);
            eyeofender.signalTo(blockpos);
            eyeofender.setR(26);
            eyeofender.setG(107);
            eyeofender.setB(89);
            p_41184_.m_214171_(GameEvent.f_157778_, eyeofender.m_20182_(), Context.m_223717_(p_41185_));
            p_41184_.m_7967_(eyeofender);
            if (p_41185_ instanceof ServerPlayer) {
               CriteriaTriggers.f_10579_.m_73935_((ServerPlayer)p_41185_, blockpos);
            }

            p_41184_.m_6263_(
               (Player)null,
               p_41185_.m_20185_(),
               p_41185_.m_20186_(),
               p_41185_.m_20189_(),
               SoundEvents.f_11898_,
               SoundSource.NEUTRAL,
               0.5F,
               0.4F / (p_41184_.m_213780_().m_188501_() * 0.4F + 0.8F)
            );
            p_41184_.m_5898_((Player)null, 1003, p_41185_.m_20183_(), 0);
            if (!p_41185_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            p_41185_.m_36246_(Stats.f_12982_.m_12902_(this));
            p_41185_.m_21011_(p_41186_, true);
            return InteractionResultHolder.m_19090_(itemstack);
         }
      }

      return InteractionResultHolder.m_19096_(itemstack);
   }
}
