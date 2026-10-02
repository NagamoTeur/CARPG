package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.init.IterRpgModItems;

@EventBusSubscriber
public class CoinPilePutCoinProcedure {
   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getHand() == event.getEntity().m_7655_()) {
         execute(
            event,
            event.getLevel(),
            (double)event.getPos().m_123341_(),
            (double)event.getPos().m_123342_(),
            (double)event.getPos().m_123343_(),
            event.getLevel().m_8055_(event.getPos()),
            event.getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      execute(null, world, x, y, z, blockstate, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      if (entity != null) {
         if (blockstate.m_60734_() == IterRpgModBlocks.COIN_PILE.get()) {
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == IterRpgModItems.COIN.get()
               && (blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip5 ? (Integer)blockstate.m_61143_(_getip5) : -1) < 6) {
               int _value = (blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip7 ? (Integer)blockstate.m_61143_(_getip7) : -1)
                  + 1;
               BlockPos _pos = new BlockPos(x, y, z);
               BlockState _bs = world.m_8055_(_pos);
               if (_bs.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _integerProp && _integerProp.m_6908_().contains(_value)) {
                  world.m_7731_(_pos, (BlockState)_bs.m_61124_(_integerProp, _value), 3);
               }

               if (!(new Object() {
                     public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof ServerPlayer _serverPlayer) {
                           return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                        } else {
                           return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                              ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                 && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                              : false;
                        }
                     }
                  })
                  .checkGamemode(entity)) {
                  (entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41774_(1);
               }
            } else if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == ItemStack.f_41583_.m_41720_()
               && (blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip16 ? (Integer)blockstate.m_61143_(_getip16) : -1) > 0) {
               int _valuex = (
                     blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip18 ? (Integer)blockstate.m_61143_(_getip18) : -1
                  )
                  - 1;
               BlockPos _posx = new BlockPos(x, y, z);
               BlockState _bsx = world.m_8055_(_posx);
               if (_bsx.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _integerProp && _integerProp.m_6908_().contains(_valuex)) {
                  world.m_7731_(_posx, (BlockState)_bsx.m_61124_(_integerProp, _valuex), 3);
               }

               if (world instanceof Level _level && !_level.m_5776_()) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x + 0.5, y + 0.25, z + 0.5, new ItemStack((ItemLike)IterRpgModItems.COIN.get()));
                  entityToSpawn.m_32010_(5);
                  _level.m_7967_(entityToSpawn);
               }
            }
         }
      }
   }
}
