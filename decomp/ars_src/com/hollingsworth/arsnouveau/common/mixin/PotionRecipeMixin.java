package com.hollingsworth.arsnouveau.common.mixin;

import java.util.List;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionBrewing.Mix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PotionBrewing.class})
public interface PotionRecipeMixin {
   @Accessor("POTION_MIXES")
   static List<Mix<Potion>> mixList() {
      throw new AssertionError();
   }
}
