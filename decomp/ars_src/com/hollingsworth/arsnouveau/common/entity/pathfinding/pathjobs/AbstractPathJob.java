package com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.AbstractAdvancedPathNavigate;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.ChunkCache;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.ModNode;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.PathPointExtended;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.PathResult;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.PathingConstants;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.PathingOptions;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.SurfaceType;
import com.hollingsworth.arsnouveau.common.util.Log;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPathJob implements Callable<Path> {
   public static boolean DEBUG_DRAW = false;
   public static Set<ModNode> lastDebugNodesVisited;
   public static Set<ModNode> lastDebugNodesNotVisited;
   public static Set<ModNode> lastDebugNodesPath;
   public static final Map<Player, UUID> trackingMap = new HashMap<>();
   protected final BlockPos start;
   protected final LevelReader world;
   protected final PathResult result;
   protected final int maxRange;
   public Queue<ModNode> nodesOpen = new PriorityQueue<>(500);
   public Map<Integer, ModNode> nodesVisited = new HashMap<>();
   protected boolean debugDrawEnabled = false;
   @Nullable
   protected Set<ModNode> debugNodesVisited = new HashSet<>();
   @Nullable
   protected Set<ModNode> debugNodesNotVisited = new HashSet<>();
   @Nullable
   protected Set<ModNode> debugNodesPath = new HashSet<>();
   private final boolean allowJumpPointSearchTypeWalk;
   private int totalNodesAdded = 0;
   public int totalNodesVisited = 0;
   private final boolean xzRestricted;
   private final boolean hardXzRestriction;
   private PathingOptions pathingOptions = new PathingOptions();
   private int maxX;
   private int minX;
   private int maxZ;
   private int minZ;
   private int maxY;
   private int minY;
   private final AbstractAdvancedPathNavigate.RestrictionType restrictionType;
   protected WeakReference<LivingEntity> entity;

   public AbstractPathJob(Level world, BlockPos start, BlockPos end, int range, LivingEntity entity) {
      this(world, start, end, range, new PathResult(), entity);
   }

   public AbstractPathJob(Level world, BlockPos start, BlockPos end, int range, PathResult result, LivingEntity entity) {
      int minX = Math.min(start.m_123341_(), end.m_123341_()) - range / 2;
      int minZ = Math.min(start.m_123343_(), end.m_123343_()) - range / 2;
      int maxX = Math.max(start.m_123341_(), end.m_123341_()) + range / 2;
      int maxZ = Math.max(start.m_123343_(), end.m_123343_()) + range / 2;
      this.restrictionType = AbstractAdvancedPathNavigate.RestrictionType.NONE;
      this.xzRestricted = false;
      this.hardXzRestriction = false;
      this.world = new ChunkCache(world, new BlockPos(minX, world.m_141937_(), minZ), new BlockPos(maxX, world.m_151558_(), maxZ), range, world.m_6042_());
      this.start = new BlockPos(start);
      this.maxRange = range;
      this.result = result;
      result.setJob(this);
      this.allowJumpPointSearchTypeWalk = false;
      if (DEBUG_DRAW) {
         this.debugDrawEnabled = true;
         this.debugNodesVisited = new HashSet<>();
         this.debugNodesNotVisited = new HashSet<>();
         this.debugNodesPath = new HashSet<>();
      }

      this.entity = new WeakReference<>(entity);
   }

   public AbstractPathJob(
      Level world,
      BlockPos start,
      BlockPos startRestriction,
      BlockPos endRestriction,
      int range,
      boolean hardRestriction,
      PathResult<AbstractPathJob> result,
      LivingEntity entity,
      AbstractAdvancedPathNavigate.RestrictionType restrictionType
   ) {
      this(world, start, startRestriction, endRestriction, range, Vec3i.f_123288_, hardRestriction, result, entity, restrictionType);
   }

   public AbstractPathJob(
      Level world,
      BlockPos start,
      BlockPos startRestriction,
      BlockPos endRestriction,
      int range,
      Vec3i grow,
      boolean hardRestriction,
      PathResult<AbstractPathJob> result,
      LivingEntity entity,
      AbstractAdvancedPathNavigate.RestrictionType restrictionType
   ) {
      this.minX = Math.min(startRestriction.m_123341_(), endRestriction.m_123341_()) - grow.m_123341_();
      this.minZ = Math.min(startRestriction.m_123343_(), endRestriction.m_123343_()) - grow.m_123343_();
      this.maxX = Math.max(startRestriction.m_123341_(), endRestriction.m_123341_()) + grow.m_123341_();
      this.maxZ = Math.max(startRestriction.m_123343_(), endRestriction.m_123343_()) + grow.m_123343_();
      this.minY = Math.min(startRestriction.m_123342_(), endRestriction.m_123342_()) - grow.m_123342_();
      this.maxY = Math.max(startRestriction.m_123342_(), endRestriction.m_123342_()) + grow.m_123342_();
      this.xzRestricted = true;
      this.hardXzRestriction = hardRestriction;
      this.restrictionType = restrictionType;
      this.world = new ChunkCache(
         world, new BlockPos(this.minX, world.m_141937_(), this.minZ), new BlockPos(this.maxX, world.m_151558_(), this.maxZ), range, world.m_6042_()
      );
      this.start = start;
      this.maxRange = range;
      this.result = result;
      result.setJob(this);
      this.allowJumpPointSearchTypeWalk = false;
      if (DEBUG_DRAW) {
         this.debugDrawEnabled = true;
         this.debugNodesVisited = new HashSet<>();
         this.debugNodesNotVisited = new HashSet<>();
         this.debugNodesPath = new HashSet<>();
      }

      this.entity = new WeakReference<>(entity);
   }

   protected boolean onLadderGoingUp(ModNode currentNode, BlockPos dPos) {
      return currentNode.isLadder() && (dPos.m_123342_() >= 0 || dPos.m_123341_() != 0 || dPos.m_123343_() != 0);
   }

   public static BlockPos prepareStart(@NotNull LivingEntity entity) {
      MutableBlockPos pos = new MutableBlockPos(Mth.m_14107_(entity.m_20185_()), Mth.m_14107_(entity.m_20186_()), Mth.m_14107_(entity.m_20189_()));
      Level level = entity.f_19853_;
      BlockState bs = level.m_8055_(pos);
      VoxelShape collisionShape = bs.m_60812_(level, pos);
      boolean isFineToStandIn = canStandInSolidBlock(bs);
      if (bs.m_60767_().m_76334_() && !isFineToStandIn && collisionShape.m_83297_(Axis.Y) > 0.0) {
         double relPosX = Math.abs(entity.m_20185_() % 1.0);
         double relPosZ = Math.abs(entity.m_20189_() % 1.0);

         for (AABB box : collisionShape.m_83299_()) {
            if (relPosX >= box.f_82288_ && relPosX <= box.f_82291_ && relPosZ >= box.f_82290_ && relPosZ <= box.f_82293_ && box.f_82292_ > 0.0) {
               pos.m_122178_(pos.m_123341_(), pos.m_123342_() + 1, pos.m_123343_());
               bs = level.m_8055_(pos);
               break;
            }
         }
      }

      BlockState down = level.m_8055_(pos.m_7495_());

      while (
         canStandInSolidBlock(bs) && canStandInSolidBlock(down) && !down.m_60734_().isLadder(down, level, pos.m_7495_(), entity) && down.m_60819_().m_76178_()
      ) {
         pos.m_122175_(Direction.DOWN, 1);
         bs = down;
         down = level.m_8055_(pos.m_7495_());
         if (pos.m_123342_() < entity.m_20193_().m_141937_()) {
            return entity.m_20183_();
         }
      }

      Block b = bs.m_60734_();
      if (entity.m_20069_()) {
         while (!bs.m_60819_().m_76178_()) {
            pos.m_122178_(pos.m_123341_(), pos.m_123342_() + 1, pos.m_123343_());
            bs = level.m_8055_(pos);
         }
      } else if (b instanceof FenceBlock || b instanceof WallBlock || bs.m_60767_().m_76333_() && !canStandInSolidBlock(bs)) {
         double dX = entity.m_20185_() - Math.floor(entity.m_20185_());
         double dZ = entity.m_20189_() - Math.floor(entity.m_20189_());
         if (dX < 0.5 && dZ < 0.5) {
            if (dZ < dX) {
               pos.m_122178_(pos.m_123341_(), pos.m_123342_(), pos.m_123343_() - 1);
            } else {
               pos.m_122178_(pos.m_123341_() - 1, pos.m_123342_(), pos.m_123343_());
            }
         } else if (dZ > dX) {
            pos.m_122178_(pos.m_123341_(), pos.m_123342_(), pos.m_123343_() + 1);
         } else {
            pos.m_122178_(pos.m_123341_() + 1, pos.m_123342_(), pos.m_123343_());
         }
      }

      return pos.m_7949_();
   }

   private static void setLadderFacing(LevelReader world, BlockPos pos, PathPointExtended p) {
      BlockState state = world.m_8055_(pos);
      Block block = state.m_60734_();
      if (block instanceof VineBlock) {
         if ((Boolean)state.m_61143_(VineBlock.f_57836_)) {
            p.setLadderFacing(Direction.NORTH);
         } else if ((Boolean)state.m_61143_(VineBlock.f_57837_)) {
            p.setLadderFacing(Direction.EAST);
         } else if ((Boolean)state.m_61143_(VineBlock.f_57834_)) {
            p.setLadderFacing(Direction.SOUTH);
         } else if ((Boolean)state.m_61143_(VineBlock.f_57835_)) {
            p.setLadderFacing(Direction.WEST);
         }
      } else if (block instanceof LadderBlock) {
         p.setLadderFacing((Direction)state.m_61143_(LadderBlock.f_54337_));
      } else {
         p.setLadderFacing(Direction.UP);
      }
   }

   private static boolean canStandInSolidBlock(BlockState state) {
      return state.m_60734_() instanceof DoorBlock || state.m_60734_() instanceof TrapDoorBlock || !state.m_60734_().f_60439_.f_60884_;
   }

   private static boolean onALadder(ModNode node, ModNode nextInPath, BlockPos pos) {
      return nextInPath != null && node.isLadder() && nextInPath.pos.m_123341_() == pos.m_123341_() && nextInPath.pos.m_123343_() == pos.m_123343_();
   }

   private static int computeNodeKey(BlockPos pos) {
      return (pos.m_123341_() & 4095) << 20 | (pos.m_123342_() & 0xFF) << 12 | pos.m_123343_() & 4095;
   }

   protected double computeCost(
      @NotNull BlockPos dPos,
      boolean isSwimming,
      boolean onPath,
      boolean onRails,
      boolean railsExit,
      boolean swimStart,
      boolean corner,
      BlockState state,
      BlockPos blockPos
   ) {
      double cost = Math.sqrt((double)(dPos.m_123341_() * dPos.m_123341_() + dPos.m_123342_() * dPos.m_123342_() + dPos.m_123343_() * dPos.m_123343_()));
      if (dPos.m_123342_() != 0 && (Math.abs(dPos.m_123342_()) > 1 || !(this.world.m_8055_(blockPos).m_60734_() instanceof StairBlock))) {
         if (dPos.m_123342_() > 0) {
            cost *= this.pathingOptions.jumpCost * (double)Math.abs(dPos.m_123342_());
         } else {
            cost *= this.pathingOptions.dropCost * (double)Math.abs(dPos.m_123342_());
         }
      }

      if (this.world.m_8055_(blockPos).m_61138_(BlockStateProperties.f_61446_)) {
         cost *= this.pathingOptions.traverseToggleAbleCost;
      }

      if (onPath) {
         cost *= this.pathingOptions.onPathCost;
      }

      if (onRails) {
         cost *= this.pathingOptions.onRailCost;
      }

      if (railsExit) {
         cost *= this.pathingOptions.railsExitCost;
      }

      if (state.m_60734_() instanceof VineBlock) {
         cost *= this.pathingOptions.vineCost;
      }

      if (isSwimming) {
         if (swimStart) {
            cost *= this.pathingOptions.swimCostEnter;
         } else {
            cost *= this.pathingOptions.swimCost;
         }
      }

      return cost;
   }

   private static boolean nodeClosed(ModNode node) {
      return node != null && node.isClosed();
   }

   private static boolean calculateSwimming(LevelReader world, BlockPos pos, ModNode node) {
      return node == null ? SurfaceType.isWater(world, pos.m_7495_()) : node.isSwimming();
   }

   public PathResult getResult() {
      return this.result;
   }

   public final Path call() {
      try {
         return this.search();
      } catch (Exception var2) {
         Log.getLogger().warn("Pathfinding Exception", var2);
         return null;
      }
   }

   @Nullable
   protected Path search() {
      ModNode bestNode = this.getAndSetupStartNode();
      double bestNodeResultScore = Double.MAX_VALUE;

      while (!this.nodesOpen.isEmpty()) {
         if (Thread.currentThread().isInterrupted()) {
            return null;
         }

         ModNode currentNode = this.nodesOpen.poll();
         this.totalNodesVisited++;
         if (this.totalNodesVisited > this.maxRange * this.maxRange) {
            break;
         }

         currentNode.setCounterVisited(this.totalNodesVisited);
         this.handleDebugOptions(currentNode);
         currentNode.setClosed();
         boolean isViablePosition = this.isInRestrictedArea(currentNode.pos)
            && SurfaceType.getSurfaceType(this.world, this.world.m_8055_(currentNode.pos.m_7495_()), currentNode.pos.m_7495_()) == SurfaceType.WALKABLE;
         if (isViablePosition && this.isAtDestination(currentNode)) {
            bestNode = currentNode;
            this.result.setPathReachesDestination(true);
            break;
         }

         double nodeResultScore = this.getNodeResultScore(currentNode);
         if (isViablePosition && nodeResultScore < bestNodeResultScore && !currentNode.isCornerNode()) {
            bestNode = currentNode;
            bestNodeResultScore = nodeResultScore;
         }

         if (!this.hardXzRestriction || isViablePosition) {
            this.walkCurrentNode(currentNode);
         }
      }

      return this.finalizePath(bestNode);
   }

   private void handleDebugOptions(ModNode currentNode) {
      if (this.debugDrawEnabled && this.debugNodesNotVisited != null && this.debugNodesVisited != null && currentNode != null) {
         this.addNodeToDebug(currentNode);
      }
   }

   private void addNodeToDebug(ModNode currentNode) {
      this.debugNodesNotVisited.remove(currentNode);
      this.debugNodesVisited.add(currentNode);
   }

   private void addPathNodeToDebug(ModNode node) {
      this.debugNodesVisited.remove(node);
      this.debugNodesPath.add(node);
   }

   private void walkCurrentNode(@NotNull ModNode currentNode) {
      BlockPos dPos = PathingConstants.BLOCKPOS_IDENTITY;
      if (currentNode.parent != null) {
         dPos = currentNode.pos.m_121996_(currentNode.parent.pos);
      }

      if (this.onLadderGoingUp(currentNode, dPos)) {
         this.walk(currentNode, PathingConstants.BLOCKPOS_UP);
      }

      if (this.onLadderGoingDown(currentNode, dPos)) {
         this.walk(currentNode, PathingConstants.BLOCKPOS_DOWN);
      }

      if ((currentNode.parent == null || !currentNode.parent.pos.equals(currentNode.pos.m_7495_())) && currentNode.isCornerNode()) {
         this.walk(currentNode, PathingConstants.BLOCKPOS_DOWN);
      } else {
         if (this.isPassable(currentNode.pos.m_7495_(), false, currentNode.parent)
            && !currentNode.isSwimming()
            && this.isLiquid(this.world.m_8055_(currentNode.pos.m_7495_()))) {
            this.walk(currentNode, PathingConstants.BLOCKPOS_DOWN);
         }

         if (dPos.m_123343_() <= 0) {
            this.walk(currentNode, PathingConstants.BLOCKPOS_NORTH);
         }

         if (dPos.m_123341_() >= 0) {
            this.walk(currentNode, PathingConstants.BLOCKPOS_EAST);
         }

         if (dPos.m_123343_() >= 0) {
            this.walk(currentNode, PathingConstants.BLOCKPOS_SOUTH);
         }

         if (dPos.m_123341_() <= 0) {
            this.walk(currentNode, PathingConstants.BLOCKPOS_WEST);
         }
      }
   }

   protected boolean onLadderGoingDown(ModNode currentNode, BlockPos dPos) {
      return (dPos.m_123342_() <= 0 || dPos.m_123341_() != 0 || dPos.m_123343_() != 0) && this.isLadder(currentNode.pos.m_7495_());
   }

   private void handleDebugDraw() {
      if (this.debugDrawEnabled) {
         synchronized (PathingConstants.debugNodeMonitor) {
            lastDebugNodesNotVisited = this.debugNodesNotVisited;
            lastDebugNodesVisited = this.debugNodesVisited;
            lastDebugNodesPath = this.debugNodesPath;
         }
      }
   }

   private ModNode getAndSetupStartNode() {
      ModNode startNode = new ModNode(this.start, this.computeHeuristic(this.start));
      if (this.isLadder(this.start)) {
         startNode.setLadder();
      } else if (this.isLiquid(this.world.m_8055_(this.start.m_7495_()))) {
         startNode.setSwimming();
      }

      startNode.setOnRails(this.pathingOptions.canUseRails() && this.world.m_8055_(this.start).m_60734_() instanceof BaseRailBlock);
      this.nodesOpen.offer(startNode);
      this.nodesVisited.put(computeNodeKey(this.start), startNode);
      this.totalNodesAdded++;
      return startNode;
   }

   public boolean isLiquid(BlockState state) {
      return state.m_60767_().m_76332_() || !state.m_60767_().m_76334_() && !state.m_60819_().m_76178_();
   }

   private Path finalizePath(ModNode targetNode) {
      int pathLength = 1;
      int railsLength = 0;

      ModNode node;
      for (node = targetNode; node.parent != null; node = node.parent) {
         pathLength++;
         if (node.isOnRails()) {
            railsLength++;
         }
      }

      Node[] points = new Node[pathLength];
      points[0] = new PathPointExtended(node.pos);
      if (this.debugDrawEnabled) {
         this.addPathNodeToDebug(node);
      }

      ModNode nextInPath = null;
      Node next = null;

      for (ModNode var11 = targetNode; var11.parent != null; var11 = var11.parent) {
         if (this.debugDrawEnabled) {
            this.addPathNodeToDebug(var11);
         }

         pathLength--;
         BlockPos pos = var11.pos;
         if (var11.isSwimming()) {
            pos.m_121955_(PathingConstants.BLOCKPOS_DOWN);
         }

         PathPointExtended p = new PathPointExtended(pos);
         if (railsLength >= 8) {
            p.setOnRails(var11.isOnRails());
            if (!p.isOnRails() || var11.parent.isOnRails() && var11.parent.parent != null) {
               if (p.isOnRails() && points.length > pathLength + 1) {
                  PathPointExtended point = (PathPointExtended)points[pathLength + 1];
                  if (!point.isOnRails()) {
                     point.setRailsExit();
                  }
               }
            } else {
               p.setRailsEntry();
            }
         }

         if (nextInPath != null && onALadder(var11, nextInPath, pos)) {
            p.setOnLadder(true);
            if (nextInPath.pos.m_123342_() > pos.m_123342_()) {
               setLadderFacing(this.world, pos, p);
            }
         } else if (onALadder(var11.parent, var11.parent, pos)) {
            p.setOnLadder(true);
         }

         if (next != null) {
            next.f_77278_ = p;
         }

         next = p;
         points[pathLength] = p;
         nextInPath = var11;
      }

      this.doDebugPrinting(points);
      return new Path(Arrays.asList(points), this.getPathTargetPos(targetNode), this.isAtDestination(targetNode));
   }

   private void doDebugPrinting(Node[] points) {
   }

   protected BlockPos getPathTargetPos(ModNode finalNode) {
      return finalNode.pos;
   }

   protected abstract double computeHeuristic(BlockPos var1);

   protected abstract boolean isAtDestination(ModNode var1);

   protected abstract double getNodeResultScore(ModNode var1);

   protected final boolean walk(ModNode parent, BlockPos dPos) {
      BlockPos pos = parent.pos.m_121955_(dPos);
      int newY = this.getGroundHeight(parent, pos);
      if (newY < this.world.m_141937_()) {
         return false;
      } else {
         boolean corner = false;
         if (pos.m_123342_() != newY) {
            if (parent.isCornerNode() && (dPos.m_123341_() != 0 || dPos.m_123343_() != 0)) {
               return false;
            }

            if (parent.isCornerNode()
               || newY - parent.pos.m_123342_() <= 0
               || parent.parent != null && parent.parent.pos.equals(parent.pos.m_121955_(new BlockPos(0, newY - pos.m_123342_(), 0)))) {
               if (parent.isCornerNode()
                  || newY - parent.pos.m_123342_() >= 0
                  || dPos.m_123341_() == 0 && dPos.m_123343_() == 0
                  || parent.parent != null && parent.pos.m_7495_().equals(parent.parent.pos)) {
                  dPos = dPos.m_7918_(0, newY - pos.m_123342_(), 0);
                  pos = new BlockPos(pos.m_123341_(), newY, pos.m_123343_());
               } else {
                  dPos = new BlockPos(dPos.m_123341_(), 0, dPos.m_123343_());
                  pos = parent.pos.m_121955_(dPos);
                  corner = true;
               }
            } else {
               dPos = new BlockPos(0, newY - pos.m_123342_(), 0);
               pos = parent.pos.m_121955_(dPos);
               corner = true;
            }
         }

         int nodeKey = computeNodeKey(pos);
         ModNode node = this.nodesVisited.get(nodeKey);
         if (nodeClosed(node)) {
            return false;
         } else {
            boolean isSwimming = calculateSwimming(this.world, pos, node);
            if (isSwimming && !this.pathingOptions.canSwim()) {
               return false;
            } else {
               boolean swimStart = isSwimming && !parent.isSwimming();
               BlockState state = this.world.m_8055_(pos);
               boolean onRoad = this.pathingOptions.getIsRoad().apply(this.world.m_8055_(pos.m_7495_()));
               boolean onRails = this.pathingOptions.canUseRails() && this.world.m_8055_(corner ? pos.m_7495_() : pos).m_60734_() instanceof BaseRailBlock;
               boolean railsExit = !onRails && parent != null && parent.isOnRails();
               double stepCost = this.computeCost(dPos, isSwimming, onRoad, onRails, railsExit, swimStart, corner, state, pos);
               stepCost = this.calcAdditionalCost(stepCost, parent, pos, state);
               double heuristic = this.computeHeuristic(pos);
               double cost = parent.getCost() + stepCost;
               double score = cost + heuristic;
               if (node == null) {
                  node = this.createNode(parent, pos, nodeKey, isSwimming, heuristic, cost, score);
                  node.setOnRails(onRails);
                  node.setCornerNode(corner);
               } else if (this.updateCurrentNode(parent, node, heuristic, cost, score)) {
                  return false;
               }

               this.nodesOpen.offer(node);
               this.performJumpPointSearch(parent, dPos, node);
               return true;
            }
         }
      }
   }

   protected double calcAdditionalCost(double stepCost, ModNode parent, BlockPos pos, BlockState state) {
      return stepCost;
   }

   private void performJumpPointSearch(ModNode parent, BlockPos dPos, ModNode node) {
      if (this.allowJumpPointSearchTypeWalk && node.getHeuristic() <= parent.getHeuristic()) {
         this.walk(node, dPos);
      }
   }

   private ModNode createNode(ModNode parent, BlockPos pos, int nodeKey, boolean isSwimming, double heuristic, double cost, double score) {
      ModNode node = new ModNode(parent, pos, cost, heuristic, score);
      this.nodesVisited.put(nodeKey, node);
      if (this.debugDrawEnabled) {
         this.debugNodesNotVisited.add(node);
      }

      if (this.isLadder(pos)) {
         node.setLadder();
      }

      if (isSwimming) {
         node.setSwimming();
      }

      this.totalNodesAdded++;
      node.setCounterAdded(this.totalNodesAdded);
      return node;
   }

   private boolean updateCurrentNode(ModNode parent, ModNode node, double heuristic, double cost, double score) {
      if (score >= node.getScore()) {
         return true;
      } else if (!this.nodesOpen.remove(node)) {
         return true;
      } else {
         node.parent = parent;
         node.setSteps(parent.getSteps() + 1);
         node.setCost(cost);
         node.setHeuristic(heuristic);
         node.setScore(score);
         return false;
      }
   }

   protected int getGroundHeight(ModNode parent, BlockPos pos) {
      if (this.isLiquid(this.world.m_8055_(pos.m_7494_()))) {
         return -100;
      } else if (this.checkHeadBlock(parent, pos)) {
         return this.handleTargetNotPassable(parent, pos.m_7494_(), this.world.m_8055_(pos.m_7494_()));
      } else {
         BlockState target = this.world.m_8055_(pos);
         if (!this.isPassable(target, pos, parent, false)) {
            return this.handleTargetNotPassable(parent, pos, target);
         } else {
            BlockState below = this.world.m_8055_(pos.m_7495_());
            SurfaceType walkability = SurfaceType.getSurfaceType(this.world, below, pos);
            if (walkability == SurfaceType.WALKABLE) {
               return pos.m_123342_();
            } else {
               return walkability == SurfaceType.NOT_PASSABLE ? -100 : this.handleNotStanding(parent, pos, below);
            }
         }
      }
   }

   private int handleNotStanding(ModNode parent, BlockPos pos, BlockState below) {
      boolean isSwimming = parent != null && parent.isSwimming();
      if (this.isLiquid(below)) {
         return this.handleInLiquid(pos, below, isSwimming);
      } else {
         return this.isLadder(below.m_60734_(), pos.m_7495_()) ? pos.m_123342_() : this.checkDrop(parent, pos, isSwimming);
      }
   }

   private int checkDrop(ModNode parent, BlockPos pos, boolean isSwimming) {
      boolean canDrop = parent != null && !parent.isLadder();
      if (canDrop
         && (
            parent.pos.m_123341_() == pos.m_123341_() && parent.pos.m_123343_() == pos.m_123343_()
               || !this.isPassable(parent.pos.m_7495_(), false, parent)
               || SurfaceType.getSurfaceType(this.world, this.world.m_8055_(parent.pos.m_7495_()), parent.pos.m_7495_()) != SurfaceType.DROPABLE
         )) {
         for (int i = 2; i <= 10; i++) {
            BlockState below = this.world.m_8055_(pos.m_6625_(i));
            if (SurfaceType.getSurfaceType(this.world, below, pos) == SurfaceType.WALKABLE && i <= 3 || this.isLiquid(below)) {
               return pos.m_123342_() - i + 1;
            }

            if (below.m_60767_() != Material.f_76296_) {
               return -100;
            }
         }

         return -100;
      } else {
         return -100;
      }
   }

   private int handleInLiquid(BlockPos pos, BlockState below, boolean isSwimming) {
      if (isSwimming) {
         return pos.m_123342_();
      } else {
         return this.pathingOptions.canSwim() && SurfaceType.isWater(this.world, pos.m_7495_()) ? pos.m_123342_() : -100;
      }
   }

   private int handleTargetNotPassable(ModNode parent, BlockPos pos, BlockState target) {
      boolean canJump = parent != null && !parent.isLadder() && !parent.isSwimming();
      if (canJump && SurfaceType.getSurfaceType(this.world, target, pos) == SurfaceType.WALKABLE) {
         boolean isSmall = this.pathingOptions.canFitInOneCube();
         int headSpace = isSmall ? 1 : 2;
         if (!this.isPassable(pos.m_6630_(headSpace), false, parent)) {
            VoxelShape bb1 = this.world.m_8055_(pos).m_60812_(this.world, pos);
            VoxelShape bb2 = this.world.m_8055_(pos.m_6630_(headSpace)).m_60812_(this.world, pos.m_6630_(headSpace));
            if ((double)pos.m_6630_(headSpace).m_123342_() + this.getStartY(bb2, 1) - ((double)pos.m_123342_() + this.getEndY(bb1, 0)) < (double)headSpace) {
               return -100;
            }
         }

         if (!this.canLeaveBlock(pos.m_6630_(headSpace), parent, true)) {
            return -100;
         } else {
            if (!this.isPassable(parent.pos.m_6630_(headSpace), false, parent)) {
               VoxelShape bb1 = this.world.m_8055_(pos).m_60812_(this.world, pos);
               VoxelShape bb2 = this.world.m_8055_(parent.pos.m_6630_(headSpace)).m_60812_(this.world, parent.pos.m_6630_(headSpace));
               if ((double)parent.pos.m_6630_(headSpace).m_123342_() + this.getStartY(bb2, 1) - ((double)pos.m_123342_() + this.getEndY(bb1, 0))
                  < (double)headSpace) {
                  return -100;
               }
            }

            BlockState parentBelow = this.world.m_8055_(parent.pos.m_7495_());
            VoxelShape parentBB = parentBelow.m_60812_(this.world, parent.pos.m_7495_());
            double parentY = parentBB.m_83297_(Axis.Y);
            double parentMaxY = parentY + (double)parent.pos.m_7495_().m_123342_();
            double targetMaxY = target.m_60812_(this.world, pos).m_83297_(Axis.Y) + (double)pos.m_123342_();
            if (targetMaxY - parentMaxY < 1.3) {
               return pos.m_123342_() + (isSmall ? 0 : 1);
            } else {
               return target.m_60734_() instanceof StairBlock
                     && parentY - 0.5 < 1.3
                     && target.m_61143_(StairBlock.f_56842_) == Half.BOTTOM
                     && getXZFacing(parent.pos, pos) == target.m_61143_(StairBlock.f_56841_)
                  ? pos.m_123342_() + (isSmall ? 0 : 1)
                  : -100;
            }
         }
      } else {
         return -100;
      }
   }

   public static Direction getXZFacing(BlockPos pos, BlockPos neighbor) {
      BlockPos vector = neighbor.m_121996_(pos);
      return Direction.m_122372_((float)vector.m_123341_(), 0.0F, (float)vector.m_123343_());
   }

   private boolean checkHeadBlock(ModNode parent, BlockPos pos) {
      BlockPos localPos = pos;
      VoxelShape bb = this.world.m_8055_(pos).m_60812_(this.world, pos);
      if (bb.m_83297_(Axis.Y) < 1.0) {
         localPos = pos.m_7494_();
      }

      boolean isSmall = this.pathingOptions.canFitInOneCube();
      if (isSmall ? !this.isPassable(pos, true, parent) : !this.isPassable(pos.m_7494_(), true, parent)) {
         VoxelShape bb1 = this.world.m_8055_(pos.m_7495_()).m_60812_(this.world, pos.m_7495_());
         VoxelShape bb2 = this.world.m_8055_(pos.m_7494_()).m_60812_(this.world, pos.m_7494_());
         if ((double)pos.m_7494_().m_123342_() + this.getStartY(bb2, 1) - ((double)pos.m_7495_().m_123342_() + this.getEndY(bb1, 0)) < 2.0) {
            return true;
         }

         if (parent != null) {
            VoxelShape bb3 = this.world.m_8055_(parent.pos.m_7495_()).m_60812_(this.world, pos.m_7495_());
            if ((double)pos.m_7494_().m_123342_() + this.getStartY(bb2, 1) - ((double)parent.pos.m_7495_().m_123342_() + this.getEndY(bb3, 0)) < 1.75) {
               return true;
            }
         }
      }

      if (parent != null) {
         BlockPos posAbove = isSmall ? pos : pos.m_7494_();
         BlockState hereState = this.world.m_8055_(localPos.m_7495_());
         VoxelShape bb1x = this.world.m_8055_(pos).m_60812_(this.world, pos);
         VoxelShape bb2x = this.world.m_8055_(posAbove).m_60812_(this.world, posAbove);
         return (double)posAbove.m_123342_() + this.getStartY(bb2x, 1) - ((double)pos.m_123342_() + this.getEndY(bb1x, 0)) >= 2.0
            ? false
            : this.isLiquid(hereState) && !this.isPassable(pos, false, parent);
      } else {
         return false;
      }
   }

   private double getStartY(VoxelShape bb, int def) {
      return bb.m_83281_() ? (double)def : bb.m_83288_(Axis.Y);
   }

   private double getEndY(VoxelShape bb, int def) {
      return bb.m_83281_() ? (double)def : bb.m_83297_(Axis.Y);
   }

   protected boolean isPassable(BlockState block, BlockPos pos, ModNode parent, boolean head) {
      if (!this.canLeaveBlock(pos, parent, head)) {
         return false;
      } else if (block.m_60767_() == Material.f_76296_) {
         return true;
      } else {
         VoxelShape shape = block.m_60812_(this.world, pos);
         if (block.m_60767_().m_76334_() && !shape.m_83281_() && !(shape.m_83297_(Axis.Y) <= 0.1)) {
            if (block.m_60734_() instanceof TrapDoorBlock) {
               BlockPos parentPos = parent == null ? this.start : parent.pos;
               if (head) {
                  parentPos = parentPos.m_7494_();
               }

               BlockPos dir = pos.m_121996_(parentPos);
               if (dir.m_123342_() != 0 && dir.m_123341_() == 0 && dir.m_123343_() == 0) {
                  return true;
               } else {
                  Direction direction = getXZFacing(parentPos, pos);
                  Direction facing = (Direction)block.m_61143_(TrapDoorBlock.f_54117_);
                  return direction == facing.m_122424_() ? true : direction != facing;
               }
            } else {
               return this.pathingOptions.canEnterDoors() && (block.m_60734_() instanceof DoorBlock || block.m_60734_() instanceof FenceGateBlock)
                  || block.m_60734_() instanceof PressurePlateBlock
                  || block.m_60734_() instanceof SignBlock
                  || block.m_60734_() instanceof AbstractBannerBlock
                  || !block.m_60734_().f_60439_.f_60884_;
            }
         } else if (!(block.m_60734_() instanceof FireBlock)
            && !(block.m_60734_() instanceof SweetBerryBushBlock)
            && !(block.m_60734_() instanceof PowderSnowBlock)) {
            if (this.isLadder(block.m_60734_(), pos)) {
               return true;
            } else {
               if (shape.m_83281_()
                  || shape.m_83297_(Axis.Y) <= 0.1
                     && !this.isLiquid(block)
                     && (block.m_60734_() != Blocks.f_50125_ || (Integer)block.m_61143_(SnowLayerBlock.f_56581_) == 1)) {
                  BlockPathTypes pathType = block.getBlockPathType(this.world, pos, (Mob)this.entity.get());
                  if (pathType == null || pathType.getDanger() == null) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return false;
         }
      }
   }

   private boolean canLeaveBlock(BlockPos pos, ModNode parent, boolean head) {
      BlockPos parentPos = parent == null ? this.start : parent.pos;
      if (head) {
         parentPos = parentPos.m_7494_();
      }

      BlockState parentBlock = this.world.m_8055_(parentPos);
      if (parentBlock.m_60734_() instanceof TrapDoorBlock) {
         BlockPos dir = pos.m_121996_(parentPos);
         if (!(Boolean)parentBlock.m_61143_(TrapDoorBlock.f_57514_)) {
            if (dir.m_123342_() == 0) {
               return true;
            }

            return head && parentBlock.m_61143_(TrapDoorBlock.f_57515_) == Half.TOP && dir.m_123342_() < 0
               || !head && parentBlock.m_61143_(TrapDoorBlock.f_57515_) == Half.BOTTOM && dir.m_123342_() > 0;
         }

         if (dir.m_123341_() != 0 || dir.m_123343_() != 0) {
            Direction direction = getXZFacing(parentPos, pos);
            Direction facing = (Direction)parentBlock.m_61143_(TrapDoorBlock.f_54117_);
            if (direction == facing.m_122424_()) {
               return false;
            }
         }
      }

      return true;
   }

   protected boolean isPassable(BlockPos pos, boolean head, ModNode parent) {
      BlockState state = this.world.m_8055_(pos);
      VoxelShape shape = state.m_60812_(this.world, pos);
      return !shape.m_83281_() && !(shape.m_83297_(Axis.Y) <= 0.1)
         ? this.isPassable(state, pos, parent, head)
         : !head || !(state.m_60734_() instanceof WoolCarpetBlock) || this.isLadder(state.m_60734_(), pos);
   }

   protected boolean isLadder(Block block, BlockPos pos) {
      return block.isLadder(this.world.m_8055_(pos), this.world, pos, this.entity.get()) && (block != Blocks.f_50191_ || this.pathingOptions.canClimbVines());
   }

   protected boolean isLadder(BlockPos pos) {
      return this.isLadder(this.world.m_8055_(pos).m_60734_(), pos);
   }

   public void setPathingOptions(PathingOptions pathingOptions) {
      this.pathingOptions = pathingOptions;
   }

   public boolean isInRestrictedArea(BlockPos pos) {
      if (this.restrictionType == AbstractAdvancedPathNavigate.RestrictionType.NONE) {
         return true;
      } else {
         boolean isInXZ = pos.m_123341_() <= this.maxX && pos.m_123343_() <= this.maxZ && pos.m_123343_() >= this.minZ && pos.m_123341_() >= this.minX;
         if (!isInXZ) {
            return false;
         } else {
            return this.restrictionType == AbstractAdvancedPathNavigate.RestrictionType.XZ
               ? true
               : pos.m_123342_() <= this.maxY && pos.m_123342_() >= this.minY;
         }
      }
   }

   public void synchToClient(LivingEntity mob) {
   }

   public static void synchToClient(HashSet<BlockPos> reached, Mob mob) {
   }
}
