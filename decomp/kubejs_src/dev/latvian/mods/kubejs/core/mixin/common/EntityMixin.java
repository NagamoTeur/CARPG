package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.core.EntityKJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RemapPrefixForJS("kjs$")
@Mixin({Entity.class})
public abstract class EntityMixin implements EntityKJS {
   private CompoundTag kjs$persistentData;
   @Shadow
   @RemapForJS("stepHeight")
   public float f_19793_;
   @Shadow
   @RemapForJS("age")
   public int f_19797_;

   @Shadow
   public abstract boolean m_20137_(String var1);

   @Override
   public CompoundTag kjs$getPersistentData() {
      if (this.kjs$persistentData == null) {
         this.kjs$persistentData = new CompoundTag();
      }

      return this.kjs$persistentData;
   }

   @Inject(
      method = {"saveWithoutId"},
      at = {@At("RETURN")}
   )
   private void saveKJS(CompoundTag tag, CallbackInfoReturnable<CompoundTag> ci) {
      if (this.kjs$persistentData != null && !this.kjs$persistentData.m_128456_()) {
         tag.m_128365_("KubeJSPersistentData", this.kjs$persistentData);
      }
   }

   @Inject(
      method = {"load"},
      at = {@At("RETURN")}
   )
   private void loadKJS(CompoundTag tag, CallbackInfo ci) {
      if (tag.m_128441_("KubeJSPersistentData")) {
         this.kjs$persistentData = tag.m_128469_("KubeJSPersistentData");
      } else {
         this.kjs$persistentData = null;
      }
   }

   @HideFromJS
   @Nullable
   @Override
   public CompoundTag kjs$getRawPersistentData() {
      return this.kjs$persistentData;
   }

   @HideFromJS
   @Override
   public void kjs$setRawPersistentData(@Nullable CompoundTag tag) {
      this.kjs$persistentData = tag;
   }

   @Shadow
   @RemapForJS("getUuid")
   public abstract UUID m_20148_();

   @Shadow
   @RemapForJS("getStringUuid")
   public abstract String m_20149_();

   @Shadow
   @RemapForJS("getUsername")
   public abstract String m_6302_();

   @Shadow
   @RemapForJS("isGlowing")
   public abstract boolean m_142038_();

   @Shadow
   @RemapForJS("setGlowing")
   public abstract void m_146915_(boolean var1);

   @Shadow
   @RemapForJS("getYaw")
   public abstract float m_146908_();

   @Shadow
   @RemapForJS("setYaw")
   public abstract void m_146922_(float var1);

   @Shadow
   @RemapForJS("getPitch")
   public abstract float m_146909_();

   @Shadow
   @RemapForJS("setPitch")
   public abstract void m_146926_(float var1);

   @Shadow
   @RemapForJS("setMotion")
   public abstract void m_20334_(double var1, double var3, double var5);

   @Shadow
   @RemapForJS("setPositionAndRotation")
   public abstract void m_7678_(double var1, double var3, double var5, float var7, float var8);

   @Shadow
   @RemapForJS("addMotion")
   public abstract void m_5997_(double var1, double var3, double var5);

   @Shadow
   @HideFromJS
   public abstract List<Entity> m_20197_();

   @Shadow
   @RemapForJS("isOnSameTeam")
   public abstract boolean m_7307_(Entity var1);

   @Shadow
   @RemapForJS("getHorizontalFacing")
   public abstract Direction m_6350_();

   @Shadow
   @RemapForJS("extinguish")
   public abstract void m_20095_();

   @Shadow
   @RemapForJS("attack")
   public abstract boolean m_6469_(DamageSource var1, float var2);

   @Shadow
   @RemapForJS("getDistanceSq")
   public abstract double m_20275_(double var1, double var3, double var5);

   @Shadow
   @RemapForJS("getEntityType")
   public abstract EntityType<?> m_6095_();

   @Shadow
   @RemapForJS("distanceToEntitySqr")
   public abstract double m_20280_(Entity var1);

   @Shadow
   @RemapForJS("distanceToEntity")
   public abstract float m_20270_(Entity var1);
}
