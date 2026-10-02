package com.min01.archaeology.misc;

import com.min01.archaeology.init.ArchaeologyItems;
import com.min01.archaeology.init.ArchaeologyRegistryKey;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DecoratedPotPatterns {
   public static final String BASE_NAME = "decorated_pot_base";
   public static final String BRICK_NAME = "decorated_pot_side";
   public static final String ANGLER_NAME = "angler_pottery_pattern";
   public static final String ARCHER_NAME = "archer_pottery_pattern";
   public static final String ARMS_UP_NAME = "arms_up_pottery_pattern";
   public static final String BLADE_NAME = "blade_pottery_pattern";
   public static final String BREWER_NAME = "brewer_pottery_pattern";
   public static final String BURN_NAME = "burn_pottery_pattern";
   public static final String DANGER_NAME = "danger_pottery_pattern";
   public static final String EXPLORER_NAME = "explorer_pottery_pattern";
   public static final String FRIEND_NAME = "friend_pottery_pattern";
   public static final String HEART_NAME = "heart_pottery_pattern";
   public static final String HEARTBREAK_NAME = "heartbreak_pottery_pattern";
   public static final String HOWL_NAME = "howl_pottery_pattern";
   public static final String MINER_NAME = "miner_pottery_pattern";
   public static final String MOURNER_NAME = "mourner_pottery_pattern";
   public static final String PLENTY_NAME = "plenty_pottery_pattern";
   public static final String PRIZE_NAME = "prize_pottery_pattern";
   public static final String SHEAF_NAME = "sheaf_pottery_pattern";
   public static final String SHELTER_NAME = "shelter_pottery_pattern";
   public static final String SKULL_NAME = "skull_pottery_pattern";
   public static final String SNORT_NAME = "snort_pottery_pattern";
   public static final ResourceKey<String> BASE = create("decorated_pot_base");
   public static final ResourceKey<String> BRICK = create("decorated_pot_side");
   public static final ResourceKey<String> ANGLER = create("angler_pottery_pattern");
   public static final ResourceKey<String> ARCHER = create("archer_pottery_pattern");
   public static final ResourceKey<String> ARMS_UP = create("arms_up_pottery_pattern");
   public static final ResourceKey<String> BLADE = create("blade_pottery_pattern");
   public static final ResourceKey<String> BREWER = create("brewer_pottery_pattern");
   public static final ResourceKey<String> BURN = create("burn_pottery_pattern");
   public static final ResourceKey<String> DANGER = create("danger_pottery_pattern");
   public static final ResourceKey<String> EXPLORER = create("explorer_pottery_pattern");
   public static final ResourceKey<String> FRIEND = create("friend_pottery_pattern");
   public static final ResourceKey<String> HEART = create("heart_pottery_pattern");
   public static final ResourceKey<String> HEARTBREAK = create("heartbreak_pottery_pattern");
   public static final ResourceKey<String> HOWL = create("howl_pottery_pattern");
   public static final ResourceKey<String> MINER = create("miner_pottery_pattern");
   public static final ResourceKey<String> MOURNER = create("mourner_pottery_pattern");
   public static final ResourceKey<String> PLENTY = create("plenty_pottery_pattern");
   public static final ResourceKey<String> PRIZE = create("prize_pottery_pattern");
   public static final ResourceKey<String> SHEAF = create("sheaf_pottery_pattern");
   public static final ResourceKey<String> SHELTER = create("shelter_pottery_pattern");
   public static final ResourceKey<String> SKULL = create("skull_pottery_pattern");
   public static final ResourceKey<String> SNORT = create("snort_pottery_pattern");
   public static Map<Item, ResourceKey<String>> ITEM_TO_POT_TEXTURE = Map.ofEntries(
      Map.entry(Items.f_42460_, BRICK),
      Map.entry((Item)ArchaeologyItems.ANGLER_POTTERY_SHERD.get(), ANGLER),
      Map.entry((Item)ArchaeologyItems.ARCHER_POTTERY_SHERD.get(), ARCHER),
      Map.entry((Item)ArchaeologyItems.ARMS_UP_POTTERY_SHERD.get(), ARMS_UP),
      Map.entry((Item)ArchaeologyItems.BLADE_POTTERY_SHERD.get(), BLADE),
      Map.entry((Item)ArchaeologyItems.BREWER_POTTERY_SHERD.get(), BREWER),
      Map.entry((Item)ArchaeologyItems.BURN_POTTERY_SHERD.get(), BURN),
      Map.entry((Item)ArchaeologyItems.DANGER_POTTERY_SHERD.get(), DANGER),
      Map.entry((Item)ArchaeologyItems.EXPLORER_POTTERY_SHERD.get(), EXPLORER),
      Map.entry((Item)ArchaeologyItems.FRIEND_POTTERY_SHERD.get(), FRIEND),
      Map.entry((Item)ArchaeologyItems.HEART_POTTERY_SHERD.get(), HEART),
      Map.entry((Item)ArchaeologyItems.HEARTBREAK_POTTERY_SHERD.get(), HEARTBREAK),
      Map.entry((Item)ArchaeologyItems.HOWL_POTTERY_SHERD.get(), HOWL),
      Map.entry((Item)ArchaeologyItems.MINER_POTTERY_SHERD.get(), MINER),
      Map.entry((Item)ArchaeologyItems.MOURNER_POTTERY_SHERD.get(), MOURNER),
      Map.entry((Item)ArchaeologyItems.PLENTY_POTTERY_SHERD.get(), PLENTY),
      Map.entry((Item)ArchaeologyItems.PRIZE_POTTERY_SHERD.get(), PRIZE),
      Map.entry((Item)ArchaeologyItems.SHEAF_POTTERY_SHERD.get(), SHEAF),
      Map.entry((Item)ArchaeologyItems.SHELTER_POTTERY_SHERD.get(), SHELTER),
      Map.entry((Item)ArchaeologyItems.SKULL_POTTERY_SHERD.get(), SKULL),
      Map.entry((Item)ArchaeologyItems.SNORT_POTTERY_SHERD.get(), SNORT)
   );

   private static ResourceKey<String> create(String path) {
      return ResourceKey.m_135785_(ArchaeologyRegistryKey.DECORATED_POT_PATTERNS, new ResourceLocation(path));
   }

   public static ResourceLocation location(String modId, ResourceKey<String> key) {
      return new ResourceLocation(modId, "entity/decorated_pot/" + key.m_135782_().m_135815_());
   }

   public static ResourceLocation location(ResourceKey<String> key) {
      return new ResourceLocation("entity/decorated_pot/" + key.m_135782_().m_135815_());
   }

   @Nullable
   public static ResourceKey<String> getResourceKey(Item item) {
      return ITEM_TO_POT_TEXTURE.get(item);
   }

   public static Set<ResourceKey<String>> bootstrap() {
      Set<ResourceKey<String>> set = new HashSet<>();
      set.add(BRICK);
      set.add(ANGLER);
      set.add(ARCHER);
      set.add(ARMS_UP);
      set.add(BLADE);
      set.add(BREWER);
      set.add(BURN);
      set.add(DANGER);
      set.add(EXPLORER);
      set.add(FRIEND);
      set.add(HEART);
      set.add(HEARTBREAK);
      set.add(HOWL);
      set.add(MINER);
      set.add(MOURNER);
      set.add(PLENTY);
      set.add(PRIZE);
      set.add(SHEAF);
      set.add(SHELTER);
      set.add(SKULL);
      set.add(SNORT);
      set.add(BASE);
      return set;
   }
}
