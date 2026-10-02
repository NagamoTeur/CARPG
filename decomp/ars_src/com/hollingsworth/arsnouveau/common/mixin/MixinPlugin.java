package com.hollingsworth.arsnouveau.common.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import shadowed.llamalad7.mixinextras.MixinExtrasBootstrap;

public class MixinPlugin implements IMixinConfigPlugin {
   public boolean viveCraftLoaded;

   public void onLoad(String mixinPackage) {
      MixinExtrasBootstrap.init();

      try {
         Class.forName("org.vivecraft.tweaker.VivecraftTransformer");
         this.viveCraftLoaded = true;
      } catch (Throwable var3) {
      }
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      return !this.viveCraftLoaded || !mixinClassName.equals("com.hollingsworth.arsnouveau.common.mixin.elytra.ClientElytraMixin");
   }

   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }

   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }
}
