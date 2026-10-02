package shadows.apotheosis.mixin;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import shadows.apotheosis.util.INBTSensitiveFallingBlock;

@Mixin({FallingBlockEntity.class})
public abstract class FallingBlockEntityMixin extends Entity {
   public FallingBlockEntityMixin(EntityType<?> pType, Level pLevel) {
      super(pType, pLevel);
   }

   @Nullable
   public ItemEntity m_19998_(ItemLike pItem) {
      return pItem instanceof INBTSensitiveFallingBlock
         ? this.ths().m_5552_(((INBTSensitiveFallingBlock)pItem).toStack(this.ths().m_31980_(), this.ths().f_31944_), 0.0F)
         : this.ths().m_20000_(pItem, 0);
   }

   private FallingBlockEntity ths() {
      return (FallingBlockEntity)this;
   }
}
