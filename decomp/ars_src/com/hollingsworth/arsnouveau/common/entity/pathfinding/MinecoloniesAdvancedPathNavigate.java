package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs.AbstractPathJob;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs.PathJobMoveAwayFromLocation;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs.PathJobMoveToLocation;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs.PathJobMoveToPathable;
import com.hollingsworth.arsnouveau.common.util.Log;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MinecoloniesAdvancedPathNavigate extends AbstractAdvancedPathNavigate {
   private static final double ON_PATH_SPEED_MULTIPLIER = 1.3;
   public static final double MIN_Y_DISTANCE = 0.001;
   public static final int MAX_SPEED_ALLOWED = 2;
   public static final double MIN_SPEED_ALLOWED = 0.1;
   private PathResult<AbstractPathJob> pathResult;
   private long pathStartTime = 0L;
   private BlockPos spawnedPos = BlockPos.f_121853_;
   private BlockPos desiredPos;
   private int desiredPosTimeout = 0;
   private IStuckHandler stuckHandler;
   private boolean isSneaking = true;
   private double swimSpeedFactor = 1.0;

   public MinecoloniesAdvancedPathNavigate(Mob entity, Level world) {
      super(entity, world);
      entity.f_21342_ = new MovementHandler(entity);
      this.f_26508_ = new WalkNodeEvaluator();
      this.f_26508_.m_77351_(true);
      this.getPathingOptions().setEnterDoors(true);
      this.f_26508_.m_77355_(true);
      this.getPathingOptions().setCanOpenDoors(true);
      this.f_26508_.m_77358_(true);
      this.getPathingOptions().setCanSwim(true);
      this.stuckHandler = PathingStuckHandler.createStuckHandler().withTakeDamageOnStuck(0.0F).withTeleportSteps(6).withTeleportOnFullStuck();
   }

   @Override
   public BlockPos getDestination() {
      return this.destination;
   }

   @Nullable
   @Override
   public PathResult moveAwayFromXYZ(BlockPos avoid, double range, double speedFactor, boolean safeDestination) {
      BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
      return this.setPathJob(
         new PathJobMoveAwayFromLocation(
            this.ourEntity.f_19853_, start, avoid, (int)range, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity
         ),
         null,
         speedFactor
      );
   }

   public PathResult setPathJob(AbstractPathJob job, BlockPos dest, double speedFactor) {
      this.m_26573_();
      this.destination = dest;
      this.originalDestination = dest;
      if (dest != null) {
         this.desiredPos = dest;
         this.desiredPosTimeout = 1000;
      }

      this.walkSpeedFactor = speedFactor;
      if (!(speedFactor > 2.0) && !(speedFactor < 0.1)) {
         job.setPathingOptions(this.getPathingOptions());
         this.pathResult = job.getResult();
         this.pathResult.startJob(Pathfinding.getExecutor());
         return this.pathResult;
      } else {
         Log.getLogger().error("Tried to set a bad speed:" + speedFactor + " for entity:" + this.ourEntity, new Exception());
         return null;
      }
   }

   public boolean m_26571_() {
      return (this.pathResult == null || this.pathResult.isDone() && this.pathResult.getStatus() != PathFindingStatus.CALCULATION_COMPLETE) && super.m_26571_();
   }

   public void m_7638_() {
      if (this.desiredPosTimeout > 0 && --this.desiredPosTimeout <= 0) {
         this.desiredPos = null;
      }

      if (this.pathResult != null) {
         if (!this.pathResult.isDone()) {
            return;
         }

         if (this.pathResult.getStatus() == PathFindingStatus.CALCULATION_COMPLETE) {
            this.processCompletedCalculationResult();
         }
      }

      int oldIndex = this.m_26571_() ? 0 : this.m_26570_().m_77399_();
      if (this.isSneaking) {
         this.isSneaking = false;
         this.f_26494_.m_20260_(false);
      }

      this.ourEntity.m_21567_(0.0F);
      if (this.handleLadders(oldIndex)) {
         this.m_7636_();
         this.stuckHandler.checkStuck(this);
      } else if (this.handleRails()) {
         this.stuckHandler.checkStuck(this);
      } else {
         this.f_26498_++;
         if (this.f_26506_) {
            this.m_26569_();
         }

         if (!this.m_26571_()) {
            if (this.m_7632_()) {
               this.m_7636_();
            } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
               Vec3 vector3d = this.m_7475_();
               Vec3 vector3d1 = this.f_26496_.m_77380_(this.f_26494_);
               if (vector3d.f_82480_ > vector3d1.f_82480_
                  && !this.f_26494_.m_20096_()
                  && Mth.m_14107_(vector3d.f_82479_) == Mth.m_14107_(vector3d1.f_82479_)
                  && Mth.m_14107_(vector3d.f_82481_) == Mth.m_14107_(vector3d1.f_82481_)) {
                  this.f_26496_.m_77374_();
               }
            }

            DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
            if (!this.m_26571_()) {
               Vec3 vector3d2 = this.f_26496_.m_77380_(this.f_26494_);
               BlockPos blockpos = new BlockPos(vector3d2);
               if (this.f_26495_.m_46749_(blockpos)) {
                  this.f_26494_
                     .m_21566_()
                     .m_6849_(
                        vector3d2.f_82479_,
                        this.f_26495_.m_8055_(blockpos.m_7495_()).m_60795_() ? vector3d2.f_82480_ : getSmartGroundY(this.f_26495_, blockpos),
                        vector3d2.f_82481_,
                        this.f_26497_
                     );
               }
            }
         }

         if (this.pathResult != null && this.m_26571_()) {
            this.pathResult.setStatus(PathFindingStatus.COMPLETE);
            this.pathResult = null;
         }

         this.stuckHandler.checkStuck(this);
      }
   }

   public static double getSmartGroundY(BlockGetter world, BlockPos pos) {
      BlockPos blockpos = pos.m_7495_();
      VoxelShape voxelshape = world.m_8055_(blockpos).m_60812_(world, blockpos);
      return !voxelshape.m_83281_() && !(voxelshape.m_83297_(Axis.Y) < 1.0)
         ? (double)blockpos.m_123342_() + voxelshape.m_83297_(Axis.Y)
         : (double)pos.m_123342_();
   }

   @Override
   public PathResult moveToXYZ(double x, double y, double z, double speedFactor) {
      int newX = Mth.m_14107_(x);
      int newY = (int)y;
      int newZ = Mth.m_14107_(z);
      if (this.pathResult == null
         || !(this.pathResult.getJob() instanceof PathJobMoveToLocation)
         || !this.pathResult.isComputing()
            && (this.destination == null || !this.destination.equals(new BlockPos(newX, newY, newZ)))
            && (this.originalDestination == null || !this.originalDestination.equals(new BlockPos(newX, newY, newZ)))) {
         BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
         this.desiredPos = new BlockPos(newX, newY, newZ);
         return this.setPathJob(
            new PathJobMoveToLocation(
               this.ourEntity.f_19853_, start, this.desiredPos, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity
            ),
            this.desiredPos,
            speedFactor
         );
      } else {
         return this.pathResult;
      }
   }

   public PathResult moveToClosestPosition(List<BlockPos> positions, double speedFactor) {
      if ((
            this.pathResult == null
               || !(this.pathResult.getJob() instanceof PathJobMoveToLocation)
               || !this.pathResult.isComputing() && (this.destination == null || !positions.contains(this.destination))
         )
         && (this.originalDestination == null || !positions.contains(this.originalDestination))) {
         BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
         return this.setPathJob(
            new PathJobMoveToPathable(this.ourEntity.f_19853_, start, positions, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity),
            null,
            speedFactor
         );
      } else {
         return this.pathResult;
      }
   }

   @Override
   public boolean tryMoveToBlockPos(BlockPos pos, double speedFactor) {
      this.moveToXYZ((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), speedFactor);
      return true;
   }

   protected PathFinder m_5532_(int p_179679_1_) {
      return null;
   }

   protected boolean m_7632_() {
      return true;
   }

   protected Vec3 m_7475_() {
      return this.ourEntity.m_20182_();
   }

   public Path m_7864_(BlockPos pos, int p_179680_2_) {
      return null;
   }

   protected boolean m_183431_(Vec3 start, Vec3 end) {
      return super.m_183431_(start, end);
   }

   public double getSpeedFactor() {
      if (this.ourEntity.m_20069_()) {
         this.f_26497_ = this.walkSpeedFactor * this.swimSpeedFactor;
         return this.f_26497_;
      } else {
         this.f_26497_ = this.walkSpeedFactor;
         return this.walkSpeedFactor;
      }
   }

   public void m_26517_(double speedFactor) {
      if (!(speedFactor > 2.0) && !(speedFactor < 0.1)) {
         this.walkSpeedFactor = speedFactor;
      } else {
         Log.getLogger().error("Tried to set a bad speed:" + speedFactor + " for entity:" + this.ourEntity, new Exception());
      }
   }

   public boolean m_26519_(double x, double y, double z, double speedFactor) {
      if (x == 0.0 && y == 0.0 && z == 0.0) {
         return false;
      } else {
         this.moveToXYZ(x, y, z, speedFactor);
         return true;
      }
   }

   public boolean m_5624_(Entity entityIn, double speedFactor) {
      return this.tryMoveToBlockPos(entityIn.m_20183_(), speedFactor);
   }

   protected void m_6804_() {
   }

   public boolean m_26536_(Path path, double speedFactor) {
      if (path == null) {
         this.m_26573_();
         return false;
      } else {
         this.pathStartTime = this.f_26495_.m_46467_();
         return super.m_26536_(this.convertPath(path), speedFactor);
      }
   }

   private Path convertPath(Path path) {
      int pathLength = path.m_77398_();
      Path tempPath = null;
      if (pathLength > 0 && !(path.m_77375_(0) instanceof PathPointExtended)) {
         PathPointExtended[] newPoints = new PathPointExtended[pathLength];

         for (int i = 0; i < pathLength; i++) {
            Node point = path.m_77375_(i);
            if (!(point instanceof PathPointExtended)) {
               newPoints[i] = new PathPointExtended(new BlockPos(point.f_77271_, point.f_77272_, point.f_77273_));
            } else {
               newPoints[i] = (PathPointExtended)point;
            }
         }

         tempPath = new Path(Arrays.asList(newPoints), path.m_77406_(), path.m_77403_());
         PathPointExtended finalPoint = newPoints[pathLength - 1];
         this.destination = new BlockPos(finalPoint.f_77271_, finalPoint.f_77272_, finalPoint.f_77273_);
      }

      return tempPath == null ? path : tempPath;
   }

   private boolean processCompletedCalculationResult() {
      this.pathResult.getJob().synchToClient(this.f_26494_);
      this.m_26536_(this.pathResult.getPath(), this.getSpeedFactor());
      if (this.pathResult != null) {
         this.pathResult.setStatus(PathFindingStatus.IN_PROGRESS_FOLLOWING);
      }

      return false;
   }

   private boolean handleLadders(int oldIndex) {
      if (!this.m_26571_()) {
         PathPointExtended pEx = (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_());
         PathPointExtended pExNext = this.m_26570_().m_77398_() > this.m_26570_().m_77399_() + 1
            ? (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_() + 1)
            : null;
         BlockPos pos = new BlockPos(pEx.f_77271_, pEx.f_77272_, pEx.f_77273_);
         if (pEx.isOnLadder()
            && pExNext != null
            && (pEx.f_77272_ != pExNext.f_77272_ || this.f_26494_.m_20186_() > (double)pEx.f_77272_)
            && this.f_26495_.m_8055_(pos).isLadder(this.f_26495_, pos, this.ourEntity)) {
            return this.handlePathPointOnLadder(pEx);
         }

         if (this.ourEntity.m_20069_()) {
            return this.handleEntityInWater(oldIndex, pEx);
         }

         if (this.f_26495_.f_46441_.m_188503_(20) == 0) {
            if (!pEx.isOnLadder() && pExNext != null && pExNext.isOnLadder()) {
               this.f_26497_ = this.getSpeedFactor() / 4.0;
            } else {
               this.f_26497_ = this.getSpeedFactor();
            }
         }
      }

      return false;
   }

   private BlockPos findBlockUnderEntity(Entity parEntity) {
      int blockX = (int)Math.round(parEntity.m_20185_());
      int blockY = Mth.m_14107_(parEntity.m_20186_() - 0.2);
      int blockZ = (int)Math.round(parEntity.m_20189_());
      return new BlockPos(blockX, blockY, blockZ);
   }

   private boolean handleRails() {
      if (!this.m_26571_()) {
         PathPointExtended pEx = (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_());
         PathPointExtended pExNext = this.m_26570_().m_77398_() > this.m_26570_().m_77399_() + 1
            ? (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_() + 1)
            : null;
         if (pExNext != null && pEx.f_77271_ == pExNext.f_77271_ && pEx.f_77273_ == pExNext.f_77273_) {
            pExNext = this.m_26570_().m_77398_() > this.m_26570_().m_77399_() + 2
               ? (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_() + 2)
               : null;
         }

         if (pEx.isOnRails() || pEx.isRailsExit()) {
            return this.handlePathOnRails(pEx, pExNext);
         }
      }

      return false;
   }

   private boolean handlePathOnRails(PathPointExtended pEx, PathPointExtended pExNext) {
      return false;
   }

   private boolean handlePathPointOnLadder(PathPointExtended pEx) {
      Vec3 vec3 = this.m_26570_().m_77380_(this.ourEntity);
      BlockPos entityPos = new BlockPos(this.ourEntity.m_20182_());
      if (vec3.m_82531_(this.ourEntity.m_20185_(), vec3.f_82480_, this.ourEntity.m_20189_()) < Math.random() * 0.1) {
         double newSpeed = 0.3;
         switch (pEx.getLadderFacing()) {
            case NORTH:
               vec3 = vec3.m_82520_(0.0, 0.0, 1.0);
               break;
            case SOUTH:
               vec3 = vec3.m_82520_(0.0, 0.0, -1.0);
               break;
            case WEST:
               vec3 = vec3.m_82520_(1.0, 0.0, 0.0);
               break;
            case EAST:
               vec3 = vec3.m_82520_(-1.0, 0.0, 0.0);
               break;
            case UP:
               vec3 = vec3.m_82520_(0.0, 1.0, 0.0);
               break;
            default:
               newSpeed = 0.0;
               this.f_26494_.m_20260_(true);
               this.isSneaking = true;
         }

         if (!(newSpeed > 0.0)) {
            if (this.f_26495_.m_8055_(entityPos.m_7495_()).isLadder(this.f_26495_, entityPos.m_7495_(), this.ourEntity)) {
               this.ourEntity.m_21567_(-0.5F);
               return true;
            }

            return false;
         }

         if (!(this.f_26495_.m_8055_(this.ourEntity.m_20183_()).m_60734_() instanceof LadderBlock)) {
            this.ourEntity.m_20256_(this.ourEntity.m_20184_().m_82520_(0.0, 0.1, 0.0));
         }

         this.ourEntity.m_21566_().m_6849_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, newSpeed);
      }

      return false;
   }

   private boolean handleEntityInWater(int oldIndex, PathPointExtended pEx) {
      int curIndex = this.m_26570_().m_77399_();
      if (curIndex > 0 && curIndex + 1 < this.m_26570_().m_77398_() && this.m_26570_().m_77375_(curIndex - 1).f_77272_ != pEx.f_77272_) {
         oldIndex = curIndex + 1;
      }

      this.m_26570_().m_77393_(oldIndex);
      Vec3 Vector3d = this.m_26570_().m_77380_(this.ourEntity);
      if (Vector3d.m_82557_(new Vec3(this.ourEntity.m_20185_(), Vector3d.f_82480_, this.ourEntity.m_20189_())) < 0.1
         && Math.abs(this.ourEntity.m_20186_() - Vector3d.f_82480_) < 0.5) {
         this.m_26570_().m_77374_();
         if (this.m_26571_()) {
            return true;
         }

         Vector3d = this.m_26570_().m_77380_(this.ourEntity);
      }

      this.ourEntity.m_21566_().m_6849_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, this.getSpeedFactor());
      return false;
   }

   protected void m_7636_() {
      this.getSpeedFactor();
      int curNode = this.f_26496_.m_77399_();
      int curNodeNext = curNode + 1;
      if (curNodeNext < this.f_26496_.m_77398_()) {
         if (!(this.f_26496_.m_77375_(curNode) instanceof PathPointExtended)) {
            this.f_26496_ = this.convertPath(this.f_26496_);
         }

         PathPointExtended pEx = (PathPointExtended)this.f_26496_.m_77375_(curNode);
         PathPointExtended pExNext = (PathPointExtended)this.f_26496_.m_77375_(curNodeNext);
         if (pEx.isOnLadder() && pEx.getLadderFacing() == Direction.DOWN && !pExNext.isOnLadder()) {
            Vec3 vec3 = this.m_7475_();
            if (vec3.f_82480_ - (double)pEx.f_77272_ < 0.001) {
               this.f_26496_.m_77393_(curNodeNext);
            }

            return;
         }
      }

      this.f_26505_ = 0.5F;
      boolean wentAhead = false;
      boolean isTracking = AbstractPathJob.DEBUG_DRAW;
      HashSet<BlockPos> reached = new HashSet<>();

      for (int i = this.f_26496_.m_77399_(); i < Math.min(this.f_26496_.m_77398_(), this.f_26496_.m_77399_() + 4); i++) {
         Vec3 next = this.f_26496_.m_77382_(this.f_26494_, i);
         if (Math.abs(this.f_26494_.m_20185_() - next.f_82479_) < (double)this.f_26505_ - Math.abs(this.f_26494_.m_20186_() - next.f_82480_) * 0.1
            && Math.abs(this.f_26494_.m_20189_() - next.f_82481_) < (double)this.f_26505_ - Math.abs(this.f_26494_.m_20186_() - next.f_82480_) * 0.1
            && Math.abs(this.f_26494_.m_20186_() - next.f_82480_) <= 1.0) {
            this.f_26496_.m_77374_();
            wentAhead = true;
            if (isTracking) {
               Node point = this.f_26496_.m_77375_(i);
               reached.add(new BlockPos(point.f_77271_, point.f_77272_, point.f_77273_));
            }
         }
      }

      if (isTracking) {
         AbstractPathJob.synchToClient(reached, this.ourEntity);
         reached.clear();
      }

      if (this.f_26496_.m_77392_()) {
         this.onPathFinish();
      } else if (!wentAhead) {
         if (curNode < this.f_26496_.m_77398_() && curNode > 1) {
            Vec3 curr = this.f_26496_.m_77382_(this.f_26494_, curNode - 1);
            Vec3 next = this.f_26496_.m_77382_(this.f_26494_, curNode);
            if (this.f_26494_.m_20182_().m_82554_(curr) >= 2.0 && this.f_26494_.m_20182_().m_82554_(next) >= 2.0) {
               for (int currentIndex = curNode - 1; currentIndex > 0; currentIndex--) {
                  Vec3 tempoPos = this.f_26496_.m_77382_(this.f_26494_, currentIndex);
                  if (this.f_26494_.m_20182_().m_82554_(tempoPos) <= 1.0) {
                     this.f_26496_.m_77393_(currentIndex);
                  } else if (isTracking) {
                     reached.add(new BlockPos(tempoPos.f_82479_, tempoPos.f_82480_, tempoPos.f_82481_));
                  }
               }
            }

            if (isTracking) {
               AbstractPathJob.synchToClient(reached, this.ourEntity);
               reached.clear();
            }
         }
      }
   }

   private void onPathFinish() {
      this.m_26573_();
   }

   public void m_26569_() {
   }

   protected void m_6481_(Vec3 positionVec3) {
   }

   public void m_26573_() {
      if (this.pathResult != null) {
         this.pathResult.cancel();
         this.pathResult.setStatus(PathFindingStatus.CANCELLED);
         this.pathResult = null;
      }

      this.destination = null;
      super.m_26573_();
   }

   public void m_7008_(boolean canSwim) {
      super.m_7008_(canSwim);
      this.getPathingOptions().setCanSwim(canSwim);
   }

   @Override
   public BlockPos getDesiredPos() {
      return this.desiredPos;
   }

   @Override
   public void setStuckHandler(IStuckHandler stuckHandler) {
      this.stuckHandler = stuckHandler;
   }

   @Override
   public void setSwimSpeedFactor(double factor) {
      this.swimSpeedFactor = factor;
   }
}
