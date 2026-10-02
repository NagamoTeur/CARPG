package com.hollingsworth.arsnouveau.common.items;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class AnimBlockItem extends ModBlockItem implements IAnimatable {
   AnimationFactory manager = GeckoLibUtil.createFactory(this);

   public AnimBlockItem(Block blockIn, Properties builder) {
      super(blockIn, builder);
   }

   @Override
   public void registerControllers(AnimationData animationData) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }
}
