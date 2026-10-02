package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.datafix.IronsTagTraverser;
import java.io.File;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerDataStorage.class})
public abstract class DataFixPlayerDataStorageMixin {
   @Shadow
   @Final
   private File f_78427_;
   @Unique
   private static final Object iron_sSpells_nSpellbooks$sync = new Object();

   @Inject(
      method = {"load"},
      at = {@At("HEAD")}
   )
   private void load(Player pPlayer, CallbackInfoReturnable<CompoundTag> cir) {
      if ((Boolean)ServerConfigs.RUN_WORLD_UPGRADER.get()) {
         File file1 = new File(this.f_78427_, pPlayer.m_20149_() + ".dat");
         if (file1.exists() && file1.isFile()) {
            try {
               synchronized (iron_sSpells_nSpellbooks$sync) {
                  CompoundTag compoundTag1 = NbtIo.m_128937_(file1);
                  IronsTagTraverser ironsTraverser = new IronsTagTraverser();
                  ironsTraverser.visit(compoundTag1);
                  if (ironsTraverser.changesMade()) {
                     NbtIo.m_128944_(compoundTag1, file1);
                     IronsSpellbooks.LOGGER.debug("DataFixPlayerDataStorageMixin: Player inventory updated: {} updates", ironsTraverser.totalChanges());
                  }
               }
            } catch (Exception var9) {
               IronsSpellbooks.LOGGER
                  .debug("DataFixPlayerDataStorageMixin: Failed to load player data for {} {}", pPlayer.m_7755_().getString(), var9.getMessage());
            }
         }
      }
   }
}
