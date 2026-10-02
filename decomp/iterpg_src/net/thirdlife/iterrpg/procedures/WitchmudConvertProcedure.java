package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.ItemHandlerHelper;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

@EventBusSubscriber
public class WitchmudConvertProcedure {
   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getHand() == event.getEntity().m_7655_()) {
         execute(
            event,
            event.getLevel(),
            (double)event.getPos().m_123341_(),
            (double)event.getPos().m_123342_(),
            (double)event.getPos().m_123343_(),
            event.getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21205_() : ItemStack.f_41583_).m_41720_() == Items.f_42589_
            && (
               (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21205_() : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128461_("Potion")
                     .equals("minecraft:awkward")
                  || (entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128461_("Potion")
                     .equals("minecraft:thick")
                  || (entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128461_("Potion")
                     .equals("minecraft:mundane")
            )
            && (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == Blocks.f_50493_ || world.m_8055_(new BlockPos(x, y, z)).m_60734_() == Blocks.f_220864_)) {
            world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.WITCHMUD.get()).m_49966_(), 3);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123811_, x + 0.5, y + 0.5, z + 0.5, 16, 0.25, 0.25, 0.25, 0.025);
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
               (entity instanceof LivingEntity _livEntxxxx ? _livEntxxxx.m_21205_() : ItemStack.f_41583_).m_41774_(1);
               if (entity instanceof Player _player) {
                  ItemStack _setstack = new ItemStack(Items.f_42590_);
                  _setstack.m_41764_(1);
                  ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
               }
            }
         }
      }
   }
}
