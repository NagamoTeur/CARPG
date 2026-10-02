package immersive_armors.config;

import immersive_armors.Items;
import immersive_armors.config.configEntries.FloatConfigEntry;
import immersive_armors.config.configEntries.IntegerConfigEntry;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Supplier;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen {
   public static Screen getScreen() {
      Config config = Config.getInstance();
      ConfigBuilder builder = ConfigBuilder.create()
         .setTitle(Component.m_237115_("itemGroup.immersive_armors.immersive_armors_tab"))
         .setSavingRunnable(config::save);
      ConfigCategory general = builder.getOrCreateCategory(Component.m_237115_("option.immersive_armors.general"));
      ConfigEntryBuilder entryBuilder = builder.entryBuilder();

      for (Field field : Config.class.getDeclaredFields()) {
         for (Annotation annotation : field.getAnnotations()) {
            try {
               String key = "option.immersive_armors." + field.getName();
               if (annotation instanceof IntegerConfigEntry entry) {
                  general.addEntry(
                     entryBuilder.startIntField(Component.m_237115_(key), field.getInt(config)).setDefaultValue(entry.value()).setSaveConsumer(v -> {
                        try {
                           field.setInt(config, v);
                        } catch (IllegalAccessException var4) {
                           throw new RuntimeException(var4);
                        }
                     }).setMin(entry.min()).setMax(entry.max()).build()
                  );
               } else if (annotation instanceof FloatConfigEntry entry) {
                  general.addEntry(
                     entryBuilder.startFloatField(Component.m_237115_(key), field.getFloat(config)).setDefaultValue(entry.value()).setSaveConsumer(v -> {
                        try {
                           field.setFloat(config, v);
                        } catch (IllegalAccessException var4) {
                           throw new RuntimeException(var4);
                        }
                     }).setMin(entry.min()).setMax(entry.max()).build()
                  );
               }
            } catch (IllegalAccessException var15) {
               throw new RuntimeException(var15);
            }
         }
      }

      ConfigCategory whitelist = builder.getOrCreateCategory(Component.m_237115_("option.immersive_armors.whitelist"));

      for (String material : Items.items
         .values()
         .stream()
         .map(Supplier::get)
         .map(i -> (ExtendedArmorItem)i)
         .map(ExtendedArmorItem::getMaterial)
         .map(ExtendedArmorMaterial::m_6082_)
         .distinct()
         .sorted()
         .toList()) {
         config.enabledArmors.putIfAbsent(material, true);
      }

      for (String material : config.enabledArmors.keySet()) {
         whitelist.addEntry(
            entryBuilder.startBooleanToggle(Component.m_237115_(material), config.enabledArmors.getOrDefault(material, true))
               .setDefaultValue(true)
               .setSaveConsumer(v -> config.enabledArmors.put(material, v))
               .requireRestart()
               .build()
         );
      }

      return builder.build();
   }
}
