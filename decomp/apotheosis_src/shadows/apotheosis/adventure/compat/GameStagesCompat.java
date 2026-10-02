package shadows.apotheosis.adventure.compat;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.world.entity.player.Player;
import shadows.apotheosis.adventure.AdventureModule;

public class GameStagesCompat {
   public static boolean hasStage(Player player, Set<String> stages) {
      return !AdventureModule.STAGES_LOADED || stages == null || GameStagesCompat.Inner.hasStage(player, stages);
   }

   public interface IStaged {
      @Nullable
      Set<String> getStages();

      static <T extends GameStagesCompat.IStaged> Predicate<T> matches(Player player) {
         return obj -> GameStagesCompat.hasStage(player, obj.getStages());
      }
   }

   private static class Inner {
      private static boolean hasStage(Player player, Set<String> stages) {
         return GameStageHelper.hasAnyOf(player, stages);
      }
   }
}
