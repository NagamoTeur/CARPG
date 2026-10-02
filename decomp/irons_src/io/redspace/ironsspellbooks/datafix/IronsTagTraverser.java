package io.redspace.ironsspellbooks.datafix;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagVisitor;

@ParametersAreNonnullByDefault
public class IronsTagTraverser implements TagVisitor {
   private final AtomicInteger changeCount;

   public IronsTagTraverser() {
      this.changeCount = new AtomicInteger(0);
   }

   private IronsTagTraverser(AtomicInteger changeCount) {
      this.changeCount = changeCount;
   }

   public boolean changesMade() {
      return this.changeCount.get() > 0;
   }

   public int totalChanges() {
      return this.changeCount.get();
   }

   public void visit(@Nullable Tag pTag) {
      if (pTag != null) {
         pTag.m_142327_(this);
      }
   }

   public void m_142614_(StringTag pTag) {
   }

   public void m_141946_(ByteTag pTag) {
   }

   public void m_142183_(ShortTag pTag) {
   }

   public void m_142045_(IntTag pTag) {
   }

   public void m_142046_(LongTag pTag) {
   }

   public void m_142181_(FloatTag pTag) {
   }

   public void m_142121_(DoubleTag pTag) {
   }

   public void m_142154_(ByteArrayTag pTag) {
   }

   public void m_142251_(IntArrayTag pTag) {
   }

   public void m_142309_(LongArrayTag pTag) {
   }

   public void m_142447_(ListTag pTag) {
      for (int i = 0; i < pTag.size(); i++) {
         new IronsTagTraverser(this.changeCount).visit(pTag.get(i));
      }
   }

   public void m_142303_(CompoundTag pTag) {
      if (DataFixerHelpers.doFixUps(pTag)) {
         this.changeCount.incrementAndGet();
      }

      List<String> list = Lists.newArrayList(pTag.m_128431_());
      Collections.sort(list);

      for (String s : list) {
         new IronsTagTraverser(this.changeCount).visit(pTag.m_128423_(s));
      }
   }

   public void m_142384_(EndTag pTag) {
   }
}
