package software.bernie.ars_nouveau.geckolib3.model.provider;

import net.minecraft.resources.ResourceLocation;

public interface IAnimatableModelProvider<E> {
   ResourceLocation getAnimationResource(E var1);
}
