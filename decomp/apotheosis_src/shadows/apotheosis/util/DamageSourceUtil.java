package shadows.apotheosis.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;

public class DamageSourceUtil {
   public static EntityDamageSource copy(EntityDamageSource other) {
      EntityDamageSource nSrc = (EntityDamageSource)(other instanceof IndirectEntityDamageSource ind
         ? new IndirectEntityDamageSource(ind.m_19385_(), ind.m_7640_(), ind.m_7639_())
         : new EntityDamageSource(other.m_19385_(), other.m_7639_()));
      ((DamageSourceUtil.DmgSrcCopy)nSrc).copyFrom(other);
      return nSrc;
   }

   public static DamageSource copy(DamageSource other) {
      if (other instanceof EntityDamageSource eSrc) {
         return copy(eSrc);
      } else {
         DamageSource nSrc = new DamageSource(other.m_19385_());
         ((DamageSourceUtil.DmgSrcCopy)nSrc).copyFrom(other);
         return nSrc;
      }
   }

   public interface DmgSrcCopy {
      void copyFrom(DamageSource var1);
   }
}
