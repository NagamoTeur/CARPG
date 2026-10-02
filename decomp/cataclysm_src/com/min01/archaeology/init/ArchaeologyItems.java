package com.min01.archaeology.init;

import com.min01.archaeology.client.renderer.DecoratedPotItemRenderer;
import com.min01.archaeology.item.BrushItem;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ArchaeologyItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
   public static final RegistryObject<Item> BRUSH = ITEMS.register(
      "brush", () -> new BrushItem(new Properties().m_41491_(CreativeModeTab.f_40756_).m_41503_(64))
   );
   public static final RegistryObject<Item> SUSPICIOUS_SAND = ITEMS.register(
      "suspicious_sand", () -> new BlockItem((Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get(), new Properties().m_41491_(CreativeModeTab.f_40750_))
   );
   public static final RegistryObject<Item> SUSPICIOUS_GRAVEL = ITEMS.register(
      "suspicious_gravel", () -> new BlockItem((Block)ArchaeologyBlocks.SUSPICIOUS_GRAVEL.get(), new Properties().m_41491_(CreativeModeTab.f_40750_))
   );
   public static final RegistryObject<Item> DECORATED_POT = ITEMS.register(
      "decorated_pot", () -> new BlockItem((Block)ArchaeologyBlocks.DECORATED_POT.get(), new Properties().m_41491_(CreativeModeTab.f_40750_)) {
            public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
               consumer.accept(new IClientItemExtensions() {
                  public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                     return new DecoratedPotItemRenderer(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
                  }
               });
            }
         }
   );
   public static final RegistryObject<Item> MUSIC_DISC_RELIC = ITEMS.register(
      "music_disc_relic",
      () -> new RecordItem(0, ArchaeologySounds.MUSIC_DISC_RELIC, new Properties().m_41491_(CreativeModeTab.f_40753_).m_41487_(1).m_41497_(Rarity.RARE), 128)
   );
   public static final RegistryObject<Item> ANGLER_POTTERY_SHERD = ITEMS.register(
      "angler_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> ARCHER_POTTERY_SHERD = ITEMS.register(
      "archer_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> ARMS_UP_POTTERY_SHERD = ITEMS.register(
      "arms_up_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> BLADE_POTTERY_SHERD = ITEMS.register(
      "blade_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> BREWER_POTTERY_SHERD = ITEMS.register(
      "brewer_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> BURN_POTTERY_SHERD = ITEMS.register(
      "burn_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> DANGER_POTTERY_SHERD = ITEMS.register(
      "danger_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> EXPLORER_POTTERY_SHERD = ITEMS.register(
      "explorer_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> FRIEND_POTTERY_SHERD = ITEMS.register(
      "friend_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> HEART_POTTERY_SHERD = ITEMS.register(
      "heart_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> HEARTBREAK_POTTERY_SHERD = ITEMS.register(
      "heartbreak_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> HOWL_POTTERY_SHERD = ITEMS.register(
      "howl_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> MINER_POTTERY_SHERD = ITEMS.register(
      "miner_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> MOURNER_POTTERY_SHERD = ITEMS.register(
      "mourner_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> PLENTY_POTTERY_SHERD = ITEMS.register(
      "plenty_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> PRIZE_POTTERY_SHERD = ITEMS.register(
      "prize_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SHEAF_POTTERY_SHERD = ITEMS.register(
      "sheaf_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SHELTER_POTTERY_SHERD = ITEMS.register(
      "shelter_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SKULL_POTTERY_SHERD = ITEMS.register(
      "skull_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SNORT_POTTERY_SHERD = ITEMS.register(
      "snort_pottery_sherd", () -> new Item(new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
}
