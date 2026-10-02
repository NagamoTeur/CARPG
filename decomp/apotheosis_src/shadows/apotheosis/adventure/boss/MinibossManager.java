package shadows.apotheosis.adventure.boss;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.placebo.json.WeightedJsonReloadListener;

public class MinibossManager extends WeightedJsonReloadListener<MinibossItem> {
   public static final MinibossManager INSTANCE = new MinibossManager();

   public MinibossManager() {
      super(AdventureModule.LOGGER, "minibosses", false, false);
   }

   protected void validateItem(MinibossItem item) {
      super.validateItem(item);
      item.validate();
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, MinibossItem.SERIALIZER);
   }

   public interface IEntityMatch {
      @Nullable
      Set<EntityType<?>> getEntities();

      static <T extends MinibossManager.IEntityMatch> Predicate<T> matches(EntityType<?> type) {
         return obj -> {
            Set<EntityType<?>> types = obj.getEntities();
            return types == null || types.isEmpty() || types.contains(type);
         };
      }

      static <T extends MinibossManager.IEntityMatch> Predicate<T> matches(Entity entity) {
         return matches(entity.m_6095_());
      }
   }
}
