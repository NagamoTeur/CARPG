package shadows.apotheosis.adventure.affix.socket.gem;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import shadows.apotheosis.adventure.loot.LootCategory;

public record GemClass(String key, Set<LootCategory> types) {
   public static Codec<GemClass> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(Codec.STRING.fieldOf("key").forGetter(GemClass::key), LootCategory.SET_CODEC.fieldOf("types").forGetter(GemClass::types))
            .apply(inst, GemClass::new)
   );

   public GemClass(String key, Set<LootCategory> types) {
      this.key = key;
      this.types = types;
      Preconditions.checkArgument(!Strings.isNullOrEmpty(this.key), "Invalid GemClass with null key");
      Preconditions.checkArgument(this.types != null && !this.types.isEmpty(), "Invalid GemClass with null or empty types");
   }

   public void write(FriendlyByteBuf buf) {
      buf.m_130070_(this.key);
      buf.writeByte(this.types.size());
      this.types.forEach(c -> buf.m_130070_(c.getName()));
   }

   public static GemClass read(FriendlyByteBuf buf) {
      String key = buf.m_130277_();
      int size = buf.readByte();
      List<LootCategory> list = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         list.add(LootCategory.byId(buf.m_130277_()));
      }

      return new GemClass(key, ImmutableSet.copyOf(list));
   }
}
