package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.SpellBook;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class SpellBookModel extends TransformAnimatedModel<SpellBook> {
   ResourceLocation OPEN = new ResourceLocation("ars_nouveau", "geo/spellbook_open.geo.json");
   ResourceLocation CLOSED = new ResourceLocation("ars_nouveau", "geo/spellbook_closed.geo.json");

   public ResourceLocation getModelResource(SpellBook book, @Nullable TransformType transformType) {
      return transformType != TransformType.GUI && transformType != TransformType.FIXED ? this.OPEN : this.CLOSED;
   }

   public void setCustomAnimations(SpellBook entity, int uniqueID, @org.jetbrains.annotations.Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      this.getBone("tier3").setHidden(entity.tier.value < 3);
      this.getBone("tier1").setHidden(entity.tier.value != 1);
      this.getBone("tier2").setHidden(entity.tier.value != 2);
   }

   public ResourceLocation getModelResource(SpellBook object) {
      return this.getModelResource(object, null);
   }

   public ResourceLocation getTextureResource(SpellBook object) {
      return new ResourceLocation("ars_nouveau", "textures/items/spellbook_purple.png");
   }

   public ResourceLocation getAnimationResource(SpellBook animatable) {
      return new ResourceLocation("ars_nouveau", "animations/empty.json");
   }
}
