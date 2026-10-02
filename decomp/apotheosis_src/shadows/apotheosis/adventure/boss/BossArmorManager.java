package shadows.apotheosis.adventure.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.util.GearSet;
import shadows.placebo.json.WeightedJsonReloadListener;

public class BossArmorManager extends WeightedJsonReloadListener<GearSet> {
   public static final BossArmorManager INSTANCE = new BossArmorManager();

   public BossArmorManager() {
      super(AdventureModule.LOGGER, "boss_gear", false, false);
   }

   public <T extends Predicate<GearSet>> GearSet getRandomSet(RandomSource rand, float luck, @Nullable List<GearSet.SetPredicate> armorSets) {
      if (armorSets != null && !armorSets.isEmpty()) {
         List<GearSet> valid = this.registry.values().stream().filter(e -> {
            for (Predicate<GearSet> f : armorSets) {
               if (f.test(e)) {
                  return true;
               }
            }

            return false;
         }).collect(Collectors.toList());
         if (valid.isEmpty()) {
            AdventureModule.LOGGER.error("Failed to locate any gear sets matching the following predicates: ");
            armorSets.forEach(s -> AdventureModule.LOGGER.error(s.toString()));
            return (GearSet)this.getRandomItem(rand, luck);
         } else {
            List<Wrapper<GearSet>> list = new ArrayList<>(valid.size());
            valid.stream().map(l -> l.wrap(luck)).forEach(list::add);
            return WeightedRandom.m_216822_(rand, list).<GearSet>map(Wrapper::m_146310_).orElse(null);
         }
      } else {
         return (GearSet)this.getRandomItem(rand, luck);
      }
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, GearSet.SERIALIZER);
   }
}
