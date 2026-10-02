package daripher.autoleveling.mixin.client;

import daripher.autoleveling.client.LeveledMobsTextures;
import daripher.autoleveling.event.MobsLevelingEvents;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntityRenderer.class})
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
   @Shadow
   protected M f_115290_;

   @Inject(
      method = {"getRenderType"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void injectGetRenderType(T entity, boolean visible, boolean invisibleToPlayer, boolean glowing, CallbackInfoReturnable<RenderType> callbackInfo) {
      int level = MobsLevelingEvents.getLevel(entity);
      ResourceLocation textureLocation = LeveledMobsTextures.get(entity.m_6095_(), level + 1);
      if (textureLocation != null) {
         if (invisibleToPlayer) {
            callbackInfo.setReturnValue(RenderType.m_110467_(textureLocation));
         } else if (visible) {
            callbackInfo.setReturnValue(this.f_115290_.m_103119_(textureLocation));
         } else {
            callbackInfo.setReturnValue(glowing ? RenderType.m_110491_(textureLocation) : null);
         }
      }
   }
}
