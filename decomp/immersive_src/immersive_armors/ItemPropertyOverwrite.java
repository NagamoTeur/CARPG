package immersive_armors;

import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class ItemPropertyOverwrite {
   public static Map<String, Float> applyItemOverwrite(Map<String, Float> map) {
      Map<String, Float> backup = new HashMap<>();

      for (Entry<String, Float> entry : map.entrySet()) {
         String[] split = entry.getKey().split(":");
         if (split.length == 2) {
            Optional<ExtendedArmorMaterial> found = Items.items
               .values()
               .stream()
               .map(Supplier::get)
               .filter(ExtendedArmorItem.class::isInstance)
               .map(i -> ((ExtendedArmorItem)i).getMaterial())
               .filter(i -> i.m_6082_().equals(split[0]))
               .findAny();
            if (found.isPresent()) {
               ExtendedArmorMaterial material = found.get();
               String var7 = split[1];
               switch (var7) {
                  case "helmetProtection":
                     backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[3]);
                     material.getProtectionAmounts()[3] = entry.getValue().intValue();
                     break;
                  case "chestplateProtection":
                     backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[2]);
                     material.getProtectionAmounts()[2] = entry.getValue().intValue();
                     break;
                  case "leggingsProtection":
                     backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[1]);
                     material.getProtectionAmounts()[1] = entry.getValue().intValue();
                     break;
                  case "bootsProtection":
                     backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[0]);
                     material.getProtectionAmounts()[0] = entry.getValue().intValue();
                     break;
                  case "weight":
                     backup.putIfAbsent(entry.getKey(), material.getWeight());
                     material.weight(entry.getValue());
                     break;
                  case "toughness":
                     backup.putIfAbsent(entry.getKey(), material.m_6651_());
                     material.toughness(entry.getValue());
                     break;
                  case "enchantability":
                     backup.putIfAbsent(entry.getKey(), (float)material.m_6646_());
                     material.enchantability(entry.getValue().intValue());
                     break;
                  default:
                     Config.LOGGER.error("Armor property " + split[1] + " for item " + split[0] + " does not exist!");
               }

               Items.items
                  .values()
                  .stream()
                  .map(Supplier::get)
                  .filter(i -> i instanceof ExtendedArmorItem && ((ExtendedArmorItem)i).getMaterial() == material)
                  .forEach(i -> ((ExtendedArmorItem)i).refreshAttributes());
            } else {
               Config.LOGGER.error("Item " + split[0] + " for armor property overwrite does not exist!");
            }
         } else {
            Config.LOGGER.error("Malformed armor property overwrite: " + entry.getKey());
         }
      }

      return backup;
   }
}
