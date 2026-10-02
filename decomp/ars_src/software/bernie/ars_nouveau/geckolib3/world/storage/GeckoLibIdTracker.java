package software.bernie.ars_nouveau.geckolib3.world.storage;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class GeckoLibIdTracker extends SavedData {
   private static final String NAME = "geckolib_ids";
   private final Object2IntMap<String> usedIds = new Object2IntOpenHashMap();

   public GeckoLibIdTracker() {
      this.usedIds.defaultReturnValue(-1);
   }

   public static GeckoLibIdTracker from(ServerLevel world) {
      return (GeckoLibIdTracker)world.m_7654_().m_129783_().m_8895_().m_164861_(GeckoLibIdTracker::load, GeckoLibIdTracker::new, "geckolib_ids");
   }

   public static GeckoLibIdTracker load(CompoundTag tag) {
      GeckoLibIdTracker tracker = new GeckoLibIdTracker();
      tracker.usedIds.clear();

      for (String key : tag.m_128431_()) {
         if (tag.m_128425_(key, 99)) {
            tracker.usedIds.put(key, tag.m_128451_(key));
         }
      }

      return tracker;
   }

   public CompoundTag m_7176_(CompoundTag tag) {
      ObjectIterator var2 = this.usedIds.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<String> id = (Entry<String>)var2.next();
         tag.m_128405_((String)id.getKey(), id.getIntValue());
      }

      return tag;
   }

   public int getNextId(GeckoLibIdTracker.Type type) {
      int id = this.usedIds.getInt(type.key) + 1;
      this.usedIds.put(type.key, id);
      this.m_77762_();
      return id;
   }

   public static enum Type {
      ITEM("Item");

      private final String key;

      private Type(String key) {
         this.key = key;
      }
   }
}
