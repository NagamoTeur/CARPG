package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.thirdlife.iterrpg.entity.MudkinEntity;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

@EventBusSubscriber
public class WitchmudGolemSpawnProcedure {
   @SubscribeEvent
   public static void onBlockPlace(EntityPlaceEvent event) {
      execute(
         event,
         event.getLevel(),
         (double)event.getPos().m_123341_(),
         (double)event.getPos().m_123342_(),
         (double)event.getPos().m_123343_(),
         event.getState(),
         event.getEntity()
      );
   }

   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      execute(null, world, x, y, z, blockstate, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      if (entity != null) {
         if (blockstate.m_60734_() == IterRpgModBlocks.CATTAIL.get()
            && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.WITCHMUD.get()) {
            BlockPos _pos = new BlockPos(x, y, z);
            Block.m_49892_(world.m_8055_(_pos), world, new BlockPos(x, -69.0, z), null);
            world.m_46961_(_pos, false);
            world.m_46796_(2001, new BlockPos(x, y, z), Block.m_49956_(((Block)IterRpgModBlocks.CATTAIL.get()).m_49966_()));
            world.m_46796_(2001, new BlockPos(x, y + 1.0, z), Block.m_49956_(((Block)IterRpgModBlocks.CATTAIL.get()).m_49966_()));
            world.m_46961_(new BlockPos(x, y - 1.0, z), false);
            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new MudkinEntity((EntityType<MudkinEntity>)IterRpgModEntities.MUDKIN.get(), _serverLevelForEntitySpawn);
               _entityForSpawning.m_7678_(x + 0.5, y - 0.5, z + 0.5, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (_entityForSpawning instanceof TamableAnimal _toTame && entity instanceof Player _owner) {
                  _toTame.m_21828_(_owner);
               }

               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
            }
         }
      }
   }
}
