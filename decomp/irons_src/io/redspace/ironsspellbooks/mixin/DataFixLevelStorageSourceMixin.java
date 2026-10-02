package io.redspace.ironsspellbooks.mixin;

import com.mojang.datafixers.DataFixer;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.datafix.IronsTagTraverser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelStorageSource.LevelDirectory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LevelStorageSource.class})
public abstract class DataFixLevelStorageSourceMixin {
   @Unique
   private static final Object iron_sSpells_nSpellbooks$sync = new Object();

   @Inject(
      method = {"readLevelData"},
      at = {@At("HEAD")}
   )
   private void readLevelData(LevelDirectory pLevelDirectory, BiFunction<Path, DataFixer, Object> pLevelDatReader, CallbackInfoReturnable<Object> cir) {
      if (Files.exists(pLevelDirectory.f_230850_())) {
         Path path = pLevelDirectory.m_230858_();

         try {
            synchronized (iron_sSpells_nSpellbooks$sync) {
               CompoundTag compoundTag1 = NbtIo.m_128937_(path.toFile());
               CompoundTag compoundTag2 = compoundTag1.m_128469_("Data");
               CompoundTag compoundTag3 = compoundTag2.m_128469_("Player");
               IronsTagTraverser ironsTraverser = new IronsTagTraverser();
               ironsTraverser.visit(compoundTag3);
               if (ironsTraverser.changesMade()) {
                  NbtIo.m_128944_(compoundTag1, path.toFile());
                  IronsSpellbooks.LOGGER.debug("DataFixLevelStorageSourceMixin: Single player inventory updated: {} updates", ironsTraverser.totalChanges());
               }
            }
         } catch (Exception var12) {
            IronsSpellbooks.LOGGER.warn("DataFixLevelStorageSourceMixin failed to load {}, {}", path, var12.getMessage());
         }
      }
   }
}
