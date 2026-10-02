package shadows.apotheosis.spawn.modifiers;

import com.google.gson.JsonElement;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import shadows.apotheosis.spawn.spawner.ApothSpawnerTile;

public interface SpawnerStat<T> {
   String getId();

   T parseValue(JsonElement var1);

   boolean apply(T var1, T var2, T var3, ApothSpawnerTile var4);

   Class<T> getTypeClass();

   default MutableComponent name() {
      return Component.m_237115_("stat.apotheosis." + this.getId());
   }

   default MutableComponent desc() {
      return Component.m_237115_("stat.apotheosis." + this.getId() + ".desc");
   }
}
