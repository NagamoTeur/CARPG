package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.source.SourceManager;
import com.hollingsworth.arsnouveau.api.source.SourceProvider;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class SourceUtil {
   public static List<ISpecialSourceProvider> canGiveSource(BlockPos pos, Level world, int range) {
      List<ISpecialSourceProvider> posList = new ArrayList<>();
      BlockPos.m_121985_(pos, range, range, range).forEach(b -> {
         if (world.m_46749_(b) && world.m_7702_(b) instanceof SourceJarTile jar && jar.canAcceptSource()) {
            posList.add(new SourceProvider(jar, b.m_7949_()));
         }
      });

      for (ISpecialSourceProvider p : SourceManager.INSTANCE.canGiveSourceNearby(pos, world, range)) {
         posList.add(new SourceProvider(p));
      }

      return posList;
   }

   public static List<ISpecialSourceProvider> canTakeSource(BlockPos pos, Level world, int range) {
      List<ISpecialSourceProvider> posList = new ArrayList<>();
      BlockPos.m_121985_(pos, range, range, range).forEach(b -> {
         if (world.m_46749_(b) && world.m_7702_(b) instanceof SourceJarTile jar && jar.getSource() > 0) {
            posList.add(new SourceProvider(jar, b.m_7949_()));
         }
      });

      for (ISpecialSourceProvider p : SourceManager.INSTANCE.canTakeSourceNearby(pos, world, range)) {
         posList.add(new SourceProvider(p));
      }

      return posList;
   }

   @Nullable
   public static ISpecialSourceProvider takeSource(BlockPos pos, Level level, int range, int source) {
      for (ISpecialSourceProvider provider : canTakeSource(pos, level, range)) {
         if (provider.getSource().getSource() >= source) {
            provider.getSource().removeSource(source);
            return provider;
         }
      }

      return null;
   }

   @Nullable
   public static ISpecialSourceProvider takeSourceWithParticles(BlockPos pos, Level level, int range, int source) {
      ISpecialSourceProvider result = takeSource(pos, level, range, source);
      if (result != null) {
         EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(level, result.getCurrentPos(), pos);
         level.m_7967_(aoeProjectile);
      }

      return result;
   }

   public static boolean hasSourceNearby(BlockPos pos, Level world, int range, int source) {
      Optional<BlockPos> loc = BlockPos.m_121930_(pos, range, range, b -> {
         if (world.m_7702_(b) instanceof SourceJarTile jar && jar.getSource() >= source) {
            return true;
         }

         return false;
      });
      return loc.isPresent() ? true : SourceManager.INSTANCE.hasSourceNearby(pos, world, range, source) != null;
   }

   @Nullable
   @Deprecated(
      forRemoval = true
   )
   public static BlockPos takeSourceNearby(BlockPos pos, Level world, int range, int mana) {
      Optional<BlockPos> loc = BlockPos.m_121930_(
         pos, range, range, b -> world.m_7702_(b) instanceof SourceJarTile && ((SourceJarTile)world.m_7702_(b)).getSource() >= mana
      );
      if (loc.isEmpty()) {
         return null;
      } else {
         if (world.m_7702_(loc.get()) instanceof SourceJarTile tile) {
            tile.removeSource(mana);
         }

         return loc.get();
      }
   }

   @Deprecated(
      forRemoval = true
   )
   @Nullable
   public static BlockPos takeSourceNearbyWithParticles(BlockPos pos, Level world, int range, int mana) {
      BlockPos result = takeSourceNearby(pos, world, range, mana);
      if (result != null) {
         EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(world, result, pos);
         world.m_7967_(aoeProjectile);
      }

      return result;
   }

   @Nullable
   @Deprecated(
      forRemoval = true
   )
   public static BlockPos canGiveSourceClosest(BlockPos pos, Level world, int range) {
      Optional<BlockPos> loc = BlockPos.m_121930_(pos, range, range, b -> {
         if (world.m_7702_(b) instanceof SourceJarTile jar && jar.canAcceptSource()) {
            return true;
         }

         return false;
      });
      return loc.orElse(null);
   }

   @Deprecated(
      forRemoval = true
   )
   public static List<BlockPos> canGiveSourceAny(BlockPos pos, Level world, int range) {
      List<BlockPos> posList = new ArrayList<>();
      BlockPos.m_121985_(pos, range, range, range).forEach(b -> {
         if (world.m_7702_(b) instanceof SourceJarTile jar && jar.canAcceptSource()) {
            posList.add(b.m_7949_());
         }
      });
      return posList;
   }

   @Deprecated(
      forRemoval = true
   )
   public static List<BlockPos> canTakeSourceAny(BlockPos pos, Level world, int range) {
      List<BlockPos> posList = new ArrayList<>();
      BlockPos.m_121985_(pos, range, range, range).forEach(b -> {
         if (world.m_7702_(b) instanceof SourceJarTile jar && jar.getSource() > 0) {
            posList.add(b.m_7949_());
         }
      });
      return posList;
   }
}
