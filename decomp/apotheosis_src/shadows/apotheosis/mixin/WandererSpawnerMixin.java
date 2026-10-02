package shadows.apotheosis.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.village.wanderer.WandererReplacements;

@Mixin({WanderingTraderSpawner.class})
public class WandererSpawnerMixin {
   @Shadow
   private RandomSource f_35908_;

   @ModifyConstant(
      method = {"tick"},
      constant = {@Constant(
         intValue = 75
      )}
   )
   public int replaceMaxChance(int old) {
      return 101;
   }

   @ModifyConstant(
      method = {"spawn"},
      constant = {@Constant(
         intValue = 10
      )}
   )
   public int replaceRng(int old) {
      return 2;
   }

   @ModifyConstant(
      method = {"spawn"},
      constant = {@Constant(
         intValue = 48000
      )}
   )
   public int replaceDespawnDelay(int old) {
      return 24000;
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"findSpawnPositionNear"},
      cancellable = true
   )
   private void findSpawnPositionNear(LevelReader level, BlockPos pos, int radius, CallbackInfoReturnable<BlockPos> cir) {
      if (WandererReplacements.undergroundTrader) {
         for (int i = 0; i < 10; i++) {
            int x = pos.m_123341_() + this.f_35908_.m_188503_(radius / 2) - radius / 4;
            int z = pos.m_123343_() + this.f_35908_.m_188503_(radius / 2) - radius / 4;
            int y = pos.m_123342_() + this.f_35908_.m_188503_(5);
            MutableBlockPos spawnPos = new MutableBlockPos(x, y, z);

            for (int j = 1; j > 7; j++) {
               spawnPos.m_122178_(x, y - j, z);
               if (!level.m_8055_(spawnPos).m_60795_()) {
                  spawnPos.m_122178_(x, y - j + 1, z);
                  break;
               }
            }

            if (NaturalSpawner.m_47051_(Type.ON_GROUND, level, spawnPos, EntityType.f_20494_)) {
               cir.setReturnValue(spawnPos.m_7949_());
               return;
            }
         }
      }
   }
}
