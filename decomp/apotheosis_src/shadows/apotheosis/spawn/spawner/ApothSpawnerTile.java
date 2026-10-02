package shadows.apotheosis.spawn.spawner;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.SpawnData.CustomSpawnRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event.Result;

public class ApothSpawnerTile extends SpawnerBlockEntity {
   public boolean ignoresPlayers = false;
   public boolean ignoresConditions = false;
   public boolean redstoneControl = false;
   public boolean ignoresLight = false;
   public boolean hasNoAI = false;
   public boolean silent = false;

   public ApothSpawnerTile(BlockPos pos, BlockState state) {
      super(pos, state);
      this.f_59788_ = new ApothSpawnerTile.SpawnerLogicExt();
   }

   public void m_183515_(CompoundTag tag) {
      tag.m_128379_("ignore_players", this.ignoresPlayers);
      tag.m_128379_("ignore_conditions", this.ignoresConditions);
      tag.m_128379_("redstone_control", this.redstoneControl);
      tag.m_128379_("ignore_light", this.ignoresLight);
      tag.m_128379_("no_ai", this.hasNoAI);
      tag.m_128379_("silent", this.silent);
      super.m_183515_(tag);
   }

   public void m_142466_(CompoundTag tag) {
      this.ignoresPlayers = tag.m_128471_("ignore_players");
      this.ignoresConditions = tag.m_128471_("ignore_conditions");
      this.redstoneControl = tag.m_128471_("redstone_control");
      this.ignoresLight = tag.m_128471_("ignore_light");
      this.hasNoAI = tag.m_128471_("no_ai");
      this.silent = tag.m_128471_("silent");
      super.m_142466_(tag);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      this.m_142466_(pkt.m_131708_());
   }

   public class SpawnerLogicExt extends BaseSpawner {
      public void m_45462_(EntityType<?> pType) {
         this.f_45444_ = new SpawnData();
         super.m_45462_(pType);
         this.f_45443_ = SimpleWeightedRandomList.m_185862_(this.f_45444_);
         if (ApothSpawnerTile.this.f_58857_ != null) {
            this.m_151350_(ApothSpawnerTile.this.f_58857_, ApothSpawnerTile.this.f_58858_);
         }
      }

      public void m_142523_(Level level, BlockPos pos, int id) {
         level.m_7696_(pos, Blocks.f_50085_, id, 0);
      }

      public void m_142667_(Level level, BlockPos pos, SpawnData nextSpawnData) {
         super.m_142667_(level, pos, nextSpawnData);
         if (level != null) {
            BlockState state = level.m_8055_(pos);
            level.m_7260_(pos, state, state, 4);
         }
      }

      @Nullable
      public BlockEntity getSpawnerBlockEntity() {
         return ApothSpawnerTile.this;
      }

      private boolean isActivated(Level level, BlockPos pos) {
         boolean flag = ApothSpawnerTile.this.ignoresPlayers
            || level.m_45914_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, (double)this.f_45452_);
         return flag && (!ApothSpawnerTile.this.redstoneControl || ApothSpawnerTile.this.f_58857_.m_46753_(pos));
      }

      private void m_151350_(Level pLevel, BlockPos pPos) {
         if (this.f_45448_ <= this.f_45447_) {
            this.f_45442_ = this.f_45447_;
         } else {
            this.f_45442_ = this.f_45447_ + pLevel.f_46441_.m_188503_(this.f_45448_ - this.f_45447_);
         }

         this.f_45443_.m_216829_(pLevel.f_46441_).ifPresent(potential -> this.m_142667_(pLevel, pPos, (SpawnData)potential.m_146310_()));
         this.m_142523_(pLevel, pPos, 1);
      }

      public void m_151319_(Level pLevel, BlockPos pPos) {
         if (!this.isActivated(pLevel, pPos)) {
            this.f_45446_ = this.f_45445_;
         } else {
            double d0 = (double)pPos.m_123341_() + pLevel.f_46441_.m_188500_();
            double d1 = (double)pPos.m_123342_() + pLevel.f_46441_.m_188500_();
            double d2 = (double)pPos.m_123343_() + pLevel.f_46441_.m_188500_();
            pLevel.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0, 0.0, 0.0);
            pLevel.m_7106_(ParticleTypes.f_123744_, d0, d1, d2, 0.0, 0.0, 0.0);
            if (this.f_45442_ > 0) {
               this.f_45442_--;
            }

            this.f_45446_ = this.f_45445_;
            this.f_45445_ = (this.f_45445_ + (double)(1000.0F / ((float)this.f_45442_ + 200.0F))) % 360.0;
         }
      }

      public void m_151311_(ServerLevel pServerLevel, BlockPos pPos) {
         if (this.isActivated(pServerLevel, pPos)) {
            if (this.f_45442_ == -1) {
               this.m_151350_(pServerLevel, pPos);
            }

            if (this.f_45442_ > 0) {
               this.f_45442_--;
            } else {
               boolean flag = false;

               for (int i = 0; i < this.f_45449_; i++) {
                  CompoundTag tag = this.f_45444_.m_186567_();
                  Optional<EntityType<?>> optional = EntityType.m_20637_(tag);
                  if (optional.isEmpty()) {
                     this.m_151350_(pServerLevel, pPos);
                     return;
                  }

                  ListTag listtag = tag.m_128437_("Pos", 6);
                  int j = listtag.size();
                  double d0 = j >= 1
                     ? listtag.m_128772_(0)
                     : (double)pPos.m_123341_() + (pServerLevel.f_46441_.m_188500_() - pServerLevel.f_46441_.m_188500_()) * (double)this.f_45453_ + 0.5;
                  double d1 = j >= 2 ? listtag.m_128772_(1) : (double)(pPos.m_123342_() + pServerLevel.f_46441_.m_188503_(3) - 1);
                  double d2 = j >= 3
                     ? listtag.m_128772_(2)
                     : (double)pPos.m_123343_() + (pServerLevel.f_46441_.m_188500_() - pServerLevel.f_46441_.m_188500_()) * (double)this.f_45453_ + 0.5;
                  if (pServerLevel.m_45772_(optional.get().m_20585_(d0, d1, d2))) {
                     BlockPos blockpos = new BlockPos(d0, d1, d2);
                     LyingLevel liar = new LyingLevel(pServerLevel);
                     boolean useLiar = false;
                     if (!ApothSpawnerTile.this.ignoresConditions) {
                        if (ApothSpawnerTile.this.ignoresLight) {
                           boolean pass = false;

                           for (int light = 0; light < 16; light++) {
                              liar.setFakeLightLevel(light);
                              if (this.checkSpawnRules(optional, liar, blockpos)) {
                                 pass = true;
                                 break;
                              }
                           }

                           if (!pass) {
                              continue;
                           }

                           useLiar = true;
                        } else if (!this.checkSpawnRules(optional, pServerLevel, blockpos)) {
                           continue;
                        }
                     }

                     Entity entity = EntityType.m_20645_(tag, pServerLevel, p_151310_ -> {
                        p_151310_.m_7678_(d0, d1, d2, p_151310_.m_146908_(), p_151310_.m_146909_());
                        return p_151310_;
                     });
                     if (entity == null) {
                        this.m_151350_(pServerLevel, pPos);
                        return;
                     }

                     if (ApothSpawnerTile.this.hasNoAI) {
                        entity.m_20199_().filter(t -> t instanceof Mob).map(Mob.class::cast).forEach(mobx -> {
                           mobx.m_21557_(true);
                           mobx.getPersistentData().m_128379_("apotheosis:movable", true);
                        });
                     }

                     if (ApothSpawnerTile.this.silent) {
                        entity.m_20225_(true);
                     }

                     int k = pServerLevel.m_45976_(
                           entity.getClass(),
                           new AABB(
                                 (double)pPos.m_123341_(),
                                 (double)pPos.m_123342_(),
                                 (double)pPos.m_123343_(),
                                 (double)(pPos.m_123341_() + 1),
                                 (double)(pPos.m_123342_() + 1),
                                 (double)(pPos.m_123343_() + 1)
                              )
                              .m_82400_((double)this.f_45453_)
                        )
                        .size();
                     if (k >= this.f_45451_) {
                        this.m_151350_(pServerLevel, pPos);
                        return;
                     }

                     entity.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), pServerLevel.f_46441_.m_188501_() * 360.0F, 0.0F);
                     if (entity instanceof Mob mob) {
                        Result res = ForgeEventFactory.canEntitySpawn(
                           mob,
                           (LevelAccessor)(useLiar ? liar : pServerLevel),
                           (double)((float)entity.m_20185_()),
                           (double)((float)entity.m_20186_()),
                           (double)((float)entity.m_20189_()),
                           this,
                           MobSpawnType.SPAWNER
                        );
                        if (res == Result.DENY
                           || res == Result.DEFAULT
                              && !ApothSpawnerTile.this.ignoresConditions
                              && (
                                 this.f_45444_.m_186574_().isEmpty() && !mob.m_5545_((LevelAccessor)(useLiar ? liar : pServerLevel), MobSpawnType.SPAWNER)
                                    || !mob.m_6914_((LevelReader)(useLiar ? liar : pServerLevel))
                              )) {
                           continue;
                        }

                        if (this.f_45444_.m_186567_().m_128440_() == 1
                           && this.f_45444_.m_186567_().m_128425_("id", 8)
                           && !ForgeEventFactory.doSpecialSpawn(
                              mob,
                              (LevelAccessor)(useLiar ? liar : pServerLevel),
                              (float)entity.m_20185_(),
                              (float)entity.m_20186_(),
                              (float)entity.m_20189_(),
                              this,
                              MobSpawnType.SPAWNER
                           )) {
                           ((Mob)entity)
                              .m_6518_(pServerLevel, pServerLevel.m_6436_(entity.m_20183_()), MobSpawnType.SPAWNER, (SpawnGroupData)null, (CompoundTag)null);
                        }
                     }

                     if (!pServerLevel.m_8860_(entity)) {
                        this.m_151350_(pServerLevel, pPos);
                        return;
                     }

                     pServerLevel.m_46796_(2004, pPos, 0);
                     if (entity instanceof Mob) {
                        ((Mob)entity).m_21373_();
                     }

                     flag = true;
                  }
               }

               if (flag) {
                  this.m_151350_(pServerLevel, pPos);
               }
            }
         }
      }

      private boolean checkSpawnRules(Optional<EntityType<?>> optional, ServerLevelAccessor pServerLevel, BlockPos blockpos) {
         if (this.f_45444_.m_186574_().isPresent()) {
            if (!optional.get().m_20674_().m_21609_() && pServerLevel.m_46791_() == Difficulty.PEACEFUL) {
               return false;
            }

            CustomSpawnRules spawndata$customspawnrules = (CustomSpawnRules)this.f_45444_.m_186574_().get();
            if (ApothSpawnerTile.this.ignoresLight) {
               return true;
            }

            if (!spawndata$customspawnrules.f_186584_().m_184578_(pServerLevel.m_45517_(LightLayer.BLOCK, blockpos))
               || !spawndata$customspawnrules.f_186585_().m_184578_(pServerLevel.m_45517_(LightLayer.SKY, blockpos))) {
               return false;
            }
         } else if (!SpawnPlacements.m_217074_(optional.get(), pServerLevel, MobSpawnType.SPAWNER, blockpos, pServerLevel.m_213780_())) {
            return false;
         }

         return true;
      }
   }
}
