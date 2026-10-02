package shadows.apotheosis.adventure.boss;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.block_entity.TickingBlockEntity;
import shadows.placebo.block_entity.TickingEntityBlock;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class BossSpawnerBlock extends Block implements TickingEntityBlock {
   private static final VoxelShape OCC_SHAPE = Shapes.m_83048_(0.0, 0.0, 0.0, 0.0, 15.99, 0.0);

   public BossSpawnerBlock(Properties properties) {
      super(properties);
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new BossSpawnerBlock.BossSpawnerTile(pPos, pState);
   }

   public VoxelShape m_7952_(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return OCC_SHAPE;
   }

   public static class BossSpawnerTile extends BlockEntity implements TickingBlockEntity {
      protected BossItem item;
      protected int ticks = 0;

      public BossSpawnerTile(BlockPos pos, BlockState state) {
         super((BlockEntityType)Apoth.Tiles.BOSS_SPAWNER.get(), pos, state);
      }

      public void serverTick(Level pLevel, BlockPos pPos, BlockState pState) {
         if (this.ticks++ % 40 == 0) {
            Optional<Player> opt = this.f_58857_
               .m_142425_(EntityType.f_20532_, new AABB(this.f_58858_).m_82377_(8.0, 8.0, 8.0), EntitySelector.f_20406_)
               .stream()
               .findFirst();
            opt.ifPresent(
               player -> {
                  this.f_58857_.m_46597_(this.f_58858_, Blocks.f_50016_.m_49966_());
                  BlockPos pos = this.f_58858_;
                  BossItem bossItem = this.item == null
                     ? (BossItem)BossItemManager.INSTANCE
                        .getRandomItem(
                           this.f_58857_.m_213780_(),
                           player.m_36336_(),
                           new Predicate[]{IDimensional.matches(this.f_58857_), GameStagesCompat.IStaged.matches(player)}
                        )
                     : this.item;
                  if (bossItem == null) {
                     AdventureModule.LOGGER
                        .error(
                           "A boss spawner attempted to spawn a boss at {} in {}, but no bosses were available!",
                           this.m_58899_(),
                           this.f_58857_.m_46472_().m_135782_()
                        );
                  } else {
                     Mob entity = bossItem.createBoss((ServerLevel)this.f_58857_, pos, this.f_58857_.m_213780_(), player.m_36336_());
                     entity.m_6710_(player);
                     entity.m_21530_();
                     ((ServerLevel)this.f_58857_).m_47205_(entity);
                  }
               }
            );
         }
      }

      public void setBossItem(BossItem item) {
         this.item = item;
      }

      public void m_183515_(CompoundTag tag) {
         if (this.item != null) {
            tag.m_128359_("boss_item", this.item.getId().toString());
         }

         super.m_183515_(tag);
      }

      public void m_142466_(CompoundTag tag) {
         this.item = (BossItem)BossItemManager.INSTANCE.getValue(new ResourceLocation(tag.m_128461_("boss_item")));
         super.m_142466_(tag);
      }
   }
}
