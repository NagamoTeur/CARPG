package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class ScryersOculusTile extends ModdedTile implements IAnimatable, ITickable {
   public int time;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   public float rot;
   public float oRot;
   public float tRot;
   public boolean playerNear;
   private static final Random RANDOM = new Random();
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public ScryersOculusTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public ScryersOculusTile(BlockPos pos, BlockState state) {
      this(BlockRegistry.SCRYERS_OCULUS_TILE, pos, state);
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void tick() {
      if (this.f_58857_.f_46443_) {
         bookAnimationTick(this.f_58857_, this.m_58899_(), this.m_58900_(), this);
      }
   }

   public static void bookAnimationTick(Level pLevel, BlockPos pPos, BlockState pState, ScryersOculusTile pBlockEntity) {
      pBlockEntity.oOpen = pBlockEntity.open;
      pBlockEntity.oRot = pBlockEntity.rot;
      Player player = pLevel.m_45924_((double)pPos.m_123341_() + 0.5, (double)pPos.m_123342_() + 0.5, (double)pPos.m_123343_() + 0.5, 5.0, false);
      if (player != null) {
         double d0 = player.m_20185_() - ((double)pPos.m_123341_() + 0.5);
         double d1 = player.m_20189_() - ((double)pPos.m_123343_() + 0.5);
         pBlockEntity.tRot = (float)Mth.m_14136_(d1, d0);
         pBlockEntity.open += 0.1F;
         if (pBlockEntity.open < 0.5F || RANDOM.nextInt(40) == 0) {
            float f1 = pBlockEntity.flipT;

            do {
               pBlockEntity.flipT = pBlockEntity.flipT + (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
            } while (f1 == pBlockEntity.flipT);
         }

         pBlockEntity.playerNear = true;
      } else {
         pBlockEntity.tRot += 0.02F;
         pBlockEntity.open -= 0.1F;
         pBlockEntity.playerNear = false;
      }

      while (pBlockEntity.rot >= (float) Math.PI) {
         pBlockEntity.rot -= (float) (Math.PI * 2);
      }

      while (pBlockEntity.rot < (float) -Math.PI) {
         pBlockEntity.rot += (float) (Math.PI * 2);
      }

      while (pBlockEntity.tRot >= (float) Math.PI) {
         pBlockEntity.tRot -= (float) (Math.PI * 2);
      }

      while (pBlockEntity.tRot < (float) -Math.PI) {
         pBlockEntity.tRot += (float) (Math.PI * 2);
      }

      float f2 = pBlockEntity.tRot - pBlockEntity.rot;

      while (f2 >= (float) Math.PI) {
         f2 -= (float) (Math.PI * 2);
      }

      while (f2 < (float) -Math.PI) {
         f2 += (float) (Math.PI * 2);
      }

      pBlockEntity.rot += f2 * 0.4F;
      pBlockEntity.open = Mth.m_14036_(pBlockEntity.open, 0.0F, 1.0F);
      pBlockEntity.time++;
      pBlockEntity.oFlip = pBlockEntity.flip;
      float f = (pBlockEntity.flipT - pBlockEntity.flip) * 0.4F;
      float f3 = 0.2F;
      f = Mth.m_14036_(f, -0.2F, 0.2F);
      pBlockEntity.flipA = pBlockEntity.flipA + (f - pBlockEntity.flipA) * 0.9F;
      pBlockEntity.flip = pBlockEntity.flip + pBlockEntity.flipA;
   }
}
