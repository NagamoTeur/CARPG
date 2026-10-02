package dev.latvian.mods.kubejs.level;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class ExplosionJS {
   private final LevelAccessor level;
   public final double x;
   public final double y;
   public final double z;
   public Entity exploder;
   public float strength;
   public boolean causesFire;
   public BlockInteraction explosionMode;

   public ExplosionJS(LevelAccessor l, double _x, double _y, double _z) {
      this.level = l;
      this.x = _x;
      this.y = _y;
      this.z = _z;
      this.exploder = null;
      this.strength = 3.0F;
      this.causesFire = false;
      this.explosionMode = BlockInteraction.BREAK;
   }

   public ExplosionJS exploder(Entity entity) {
      this.exploder = entity;
      return this;
   }

   public ExplosionJS strength(float f) {
      this.strength = f;
      return this;
   }

   public ExplosionJS causesFire(boolean b) {
      this.causesFire = b;
      return this;
   }

   public ExplosionJS damagesTerrain(boolean b) {
      this.explosionMode = b ? BlockInteraction.BREAK : BlockInteraction.NONE;
      return this;
   }

   public ExplosionJS destroysTerrain(boolean b) {
      this.explosionMode = b ? BlockInteraction.DESTROY : BlockInteraction.NONE;
      return this;
   }

   public void explode() {
      if (this.level instanceof Level level) {
         level.m_46518_(this.exploder, this.x, this.y, this.z, this.strength, this.causesFire, this.explosionMode);
      }
   }
}
