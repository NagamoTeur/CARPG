package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.util.FakePlayer;

public class SpellUtil {
   public static boolean postEvent(SpellCastEvent e) {
      return MinecraftForge.EVENT_BUS.post(e);
   }

   public static List<BlockPos> calcAOEBlocks(LivingEntity caster, BlockPos origin, BlockHitResult mop, int aoeBonus) {
      return calcAOEBlocks(caster, origin, mop, 1 + aoeBonus, 1 + aoeBonus, 1, -1);
   }

   public static List<BlockPos> calcAOEBlocks(LivingEntity caster, BlockPos origin, BlockHitResult mop, double aoeBonus) {
      return calcAOEBlocks(caster, origin, mop, (int)(1.0 + Math.floor(aoeBonus)), (int)(1.0 + Math.ceil(aoeBonus)), 1, -1);
   }

   public static List<BlockPos> calcAOEBlocks(LivingEntity caster, BlockPos origin, BlockHitResult mop, SpellStats stats) {
      double aoeBonus = stats.getAoeMultiplier();
      int pierceBonus = stats.getBuffCount(AugmentPierce.INSTANCE);
      return calcAOEBlocks(caster, origin, mop, aoeBonus, pierceBonus);
   }

   public static boolean isCorrectHarvestLevel(int strength, BlockState state) {
      Tier tier = switch (strength) {
         case 2 -> Tiers.STONE;
         case 3 -> Tiers.IRON;
         case 4 -> Tiers.DIAMOND;
         case 5 -> Tiers.NETHERITE;
         default -> Tiers.WOOD;
      };
      if (strength > 5) {
         tier = Tiers.NETHERITE;
      }

      return TierSortingRegistry.isCorrectTierForDrops(tier, state);
   }

   public static List<BlockPos> calcAOEBlocks(LivingEntity caster, BlockPos origin, BlockHitResult mop, int aoeBonus, int pierceBonus) {
      return calcAOEBlocks(caster, origin, mop, 1 + aoeBonus, 1 + aoeBonus, 1 + pierceBonus, -1);
   }

   public static List<BlockPos> calcAOEBlocks(LivingEntity caster, BlockPos origin, BlockHitResult mop, double aoeBonus, int pierceBonus) {
      return calcAOEBlocks(caster, origin, mop, (int)(1.0 + Math.floor(aoeBonus)), (int)(1.0 + Math.ceil(aoeBonus)), 1 + pierceBonus, -1);
   }

   public static List<BlockPos> calcAOEBlocks(LivingEntity caster, BlockPos origin, BlockHitResult mop, int width, int height, int depth, int distance) {
      Vec3i hitVec = caster.m_6350_().m_122436_();
      if (caster instanceof FakePlayer) {
         mop = new BlockHitResult(mop.m_82450_(), mop.m_82434_(), mop.m_82425_(), false);
      }

      return calcAOEBlocks(hitVec, origin, mop, width, height, depth, distance);
   }

   public static List<BlockPos> calcAOEBlocks(Vec3 hitVec, BlockPos origin, BlockHitResult mop, int width, int height, int depth, int distance) {
      return calcAOEBlocks(
         Direction.m_122366_(hitVec.f_82479_, hitVec.f_82480_, hitVec.f_82481_).m_122424_().m_122436_(), origin, mop, width, height, depth, distance
      );
   }

   public static List<BlockPos> calcAOEBlocks(Vec3i facingVec, BlockPos origin, BlockHitResult mop, int width, int height, int depth, int distance) {
      BlockPos start = origin;
      int x;
      int y;
      int z;
      switch (mop.m_82436_() ? Direction.DOWN : mop.m_82434_()) {
         case DOWN:
         case UP:
            x = facingVec.m_123341_() * height + facingVec.m_123343_() * width;
            y = mop.m_82434_().m_122421_().m_122540_() * -depth;
            z = facingVec.m_123341_() * width + facingVec.m_123343_() * height;
            start = origin.m_7918_(-x / 2, 0, -z / 2);
            if (x % 2 == 0) {
               if (x > 0 && mop.m_82450_().f_82479_ - (double)mop.m_82425_().m_123341_() > 0.5) {
                  start = start.m_7918_(1, 0, 0);
               } else if (x < 0 && mop.m_82450_().f_82479_ - (double)mop.m_82425_().m_123341_() < 0.5) {
                  start = start.m_7918_(-1, 0, 0);
               }
            }

            if (z % 2 == 0) {
               if (z > 0 && mop.m_82450_().f_82481_ - (double)mop.m_82425_().m_123343_() > 0.5) {
                  start = start.m_7918_(0, 0, 1);
               } else if (z < 0 && mop.m_82450_().f_82481_ - (double)mop.m_82425_().m_123343_() < 0.5) {
                  start = start.m_7918_(0, 0, -1);
               }
            }
            break;
         case NORTH:
         case SOUTH:
            x = width;
            y = height;
            z = mop.m_82434_().m_122421_().m_122540_() * -depth;
            start = origin.m_7918_(-width / 2, -height / 2, 0);
            if (width % 2 == 0 && mop.m_82450_().f_82479_ - (double)mop.m_82425_().m_123341_() > 0.5) {
               start = start.m_7918_(1, 0, 0);
            }

            if (height % 2 == 0 && mop.m_82450_().f_82480_ - (double)mop.m_82425_().m_123342_() > 0.5) {
               start = start.m_7918_(0, 1, 0);
            }
            break;
         case WEST:
         case EAST:
            x = mop.m_82434_().m_122421_().m_122540_() * -depth;
            y = height;
            z = width;
            start = origin.m_7918_(0, -height / 2, -width / 2);
            if (height % 2 == 0 && mop.m_82450_().f_82480_ - (double)mop.m_82425_().m_123342_() > 0.5) {
               start = start.m_7918_(0, 1, 0);
            }

            if (width % 2 == 0 && mop.m_82450_().f_82481_ - (double)mop.m_82425_().m_123343_() > 0.5) {
               start = start.m_7918_(0, 0, 1);
            }
            break;
         default:
            z = 0;
            y = 0;
            x = 0;
      }

      ArrayList<BlockPos> builder = new ArrayList<>();

      for (int xp = start.m_123341_(); xp != start.m_123341_() + x; xp += x / Mth.m_14040_(x)) {
         for (int yp = start.m_123342_(); yp != start.m_123342_() + y; yp += y / Mth.m_14040_(y)) {
            for (int zp = start.m_123343_(); zp != start.m_123343_() + z; zp += z / Mth.m_14040_(z)) {
               if ((xp != origin.m_123341_() || yp != origin.m_123342_() || zp != origin.m_123343_())
                  && (
                     distance <= 0
                        || Mth.m_14040_(xp - origin.m_123341_()) + Mth.m_14040_(yp - origin.m_123342_()) + Mth.m_14040_(zp - origin.m_123343_()) <= distance
                  )) {
                  BlockPos pos = new BlockPos(xp, yp, zp);
                  builder.add(pos);
               }
            }
         }
      }

      builder.add(origin);
      return builder;
   }

   public static Set<BlockPos> DFSBlockstates(Level world, BlockPos start, int maxBlocks, Predicate<BlockState> isMatch) {
      return DFSBlockstates(world, Collections.singleton(start), maxBlocks, isMatch);
   }

   private static Set<BlockPos> DFSBlockstates(Level world, Collection<BlockPos> start, int maxBlocks, Predicate<BlockState> isMatch) {
      LinkedList<BlockPos> searchQueue = new LinkedList<>(start);
      HashSet<BlockPos> searched = new HashSet<>(start);
      HashSet<BlockPos> found = new HashSet<>();

      while (!searchQueue.isEmpty() && found.size() < maxBlocks) {
         BlockPos current = searchQueue.removeFirst();
         BlockState state = world.m_8055_(current);
         if (isMatch.test(state)) {
            found.add(current);
            BlockPos.m_121990_(current.m_7918_(1, 1, 1), current.m_7918_(-1, -1, -1)).forEach(neighborMutable -> {
               if (!searched.contains(neighborMutable)) {
                  BlockPos neighbor = neighborMutable.m_7949_();
                  searched.add(neighbor);
                  searchQueue.add(neighbor);
               }
            });
         }
      }

      return found;
   }

   public static HitResult rayTrace(Entity entity, double length, float lookOffset, boolean hitLiquids) {
      HitResult result = entity.m_19907_(length, lookOffset, hitLiquids);
      EntityHitResult entityLookedAt = MathUtil.getLookedAtEntity(entity, 25);
      return (HitResult)(entityLookedAt == null ? result : entityLookedAt);
   }
}
