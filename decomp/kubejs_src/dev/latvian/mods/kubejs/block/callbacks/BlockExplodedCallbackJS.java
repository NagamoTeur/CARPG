package dev.latvian.mods.kubejs.block.callbacks;

import dev.latvian.mods.kubejs.level.BlockContainerJS;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockExplodedCallbackJS {
   protected final Level level;
   protected final BlockContainerJS block;
   protected final BlockState state;
   protected final Explosion explosion;

   public BlockExplodedCallbackJS(Level level, BlockPos pos, Explosion explosion) {
      this.level = level;
      this.block = new BlockContainerJS(level, pos);
      this.state = level.m_8055_(pos);
      this.explosion = explosion;
   }

   public Level getLevel() {
      return this.level;
   }

   public BlockContainerJS getBlock() {
      return this.block;
   }

   public BlockState getBlockState() {
      return this.state;
   }

   public Explosion getExplosion() {
      return this.explosion;
   }

   public Entity getCause() {
      return this.explosion.f_46016_;
   }

   @Nullable
   public LivingEntity getIgniter() {
      return this.explosion.m_46079_();
   }

   public float getRadius() {
      return this.explosion.f_46017_;
   }

   public DamageSource getDamageSource() {
      return this.explosion.m_46077_();
   }

   public List<Player> getAffectedPlayers() {
      return this.explosion.m_46078_().keySet().stream().toList();
   }
}
