package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.core.ServerLevelKJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin({ServerLevel.class})
public abstract class ServerLevelMixin implements ServerLevelKJS {
   @Shadow
   @Final
   @HideFromJS
   List<ServerPlayer> f_8546_;
   @Unique
   private CompoundTag kjs$persistentData;

   @Override
   public CompoundTag kjs$getPersistentData() {
      if (this.kjs$persistentData == null) {
         String t = this.kjs$self().m_46472_().m_135782_().toString();
         this.kjs$persistentData = this.kjs$self().m_7654_().kjs$getPersistentData().m_128469_(t);
         this.kjs$self().m_7654_().kjs$getPersistentData().m_128365_(t, this.kjs$persistentData);
      }

      return this.kjs$persistentData;
   }

   @Shadow
   @HideFromJS
   public abstract List<ServerPlayer> m_6907_();

   @Shadow
   @HideFromJS
   protected abstract LevelEntityGetter<Entity> m_142646_();
}
