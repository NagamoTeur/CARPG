package shadows.apotheosis.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.placebo.json.NBTAdapter;

public class SupportingEntity {
   public static Codec<SupportingEntity> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("entity").forGetter(t -> t.entity),
               NBTAdapter.EITHER_CODEC.optionalFieldOf("nbt").forGetter(t -> Optional.ofNullable(t.nbt)),
               Codec.DOUBLE.optionalFieldOf("x", 0.0).forGetter(t -> t.x),
               Codec.DOUBLE.optionalFieldOf("y", 0.0).forGetter(t -> t.y),
               Codec.DOUBLE.optionalFieldOf("z", 0.0).forGetter(t -> t.z)
            )
            .apply(inst, SupportingEntity::new)
   );
   public final EntityType<?> entity;
   protected final CompoundTag nbt;
   protected final double x;
   protected final double y;
   protected final double z;

   public SupportingEntity(EntityType<?> entity, Optional<CompoundTag> nbt, double x, double y, double z) {
      this.entity = entity;
      this.nbt = nbt.orElse(null);
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Mob create(Level level, double x, double y, double z) {
      Mob ent = (Mob)this.entity.m_20615_(level);
      if (this.nbt != null) {
         ent.deserializeNBT(this.nbt);
      }

      ent.m_6034_(this.x + x, this.y + y, this.z + z);
      return ent;
   }
}
