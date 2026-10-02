package com.hollingsworth.arsnouveau.common.items;

import net.minecraft.world.item.Item.Properties;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class AnimModItem extends ModItem implements IAnimatable {
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public AnimModItem(Properties properties) {
      super(properties);
   }

   public AnimModItem() {
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }
}
