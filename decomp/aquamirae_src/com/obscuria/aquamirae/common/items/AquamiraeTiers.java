package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public final class AquamiraeTiers {
   public static final Tier REMNANTS_SABER = new Tier() {
      public int m_6609_() {
         return 100;
      }

      public float m_6624_() {
         return 4.0F;
      }

      public float m_6631_() {
         return 1.0F;
      }

      public int m_6604_() {
         return 1;
      }

      public int m_6601_() {
         return 5;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHARP_BONES.get()), new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())}
         );
      }
   };
   public static final Tier CORAL_LANCE = new Tier() {
      public int m_6609_() {
         return 1400;
      }

      public float m_6624_() {
         return 6.0F;
      }

      public float m_6631_() {
         return 10.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 14;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())});
      }
   };
   public static final Tier DAGGER_OF_GREED = new Tier() {
      public int m_6609_() {
         return 100;
      }

      public float m_6624_() {
         return 8.0F;
      }

      public float m_6631_() {
         return 0.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 30;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.f_43901_;
      }
   };
   public static final Tier DIVIDER = new Tier() {
      public int m_6609_() {
         return 2200;
      }

      public float m_6624_() {
         return 6.0F;
      }

      public float m_6631_() {
         return 3.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 14;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()), new ItemStack((ItemLike)AquamiraeItems.ABYSSAL_AMETHYST.get())}
         );
      }
   };
   public static final Tier FIN_CUTTER = new Tier() {
      public int m_6609_() {
         return 1000;
      }

      public float m_6624_() {
         return 8.0F;
      }

      public float m_6631_() {
         return 2.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 20;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(new ItemStack[]{new ItemStack(Items.f_42415_), new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())});
      }
   };
   public static final Tier MAZE_ROSE = new Tier() {
      public int m_6609_() {
         return 1800;
      }

      public float m_6624_() {
         return 4.0F;
      }

      public float m_6631_() {
         return -1.0F;
      }

      public int m_6604_() {
         return 2;
      }

      public int m_6601_() {
         return 12;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()), new ItemStack((ItemLike)AquamiraeItems.ABYSSAL_AMETHYST.get())}
         );
      }
   };
   public static final Tier POISONED_BLADE = new Tier() {
      public int m_6609_() {
         return 500;
      }

      public float m_6624_() {
         return 4.0F;
      }

      public float m_6631_() {
         return 1.0F;
      }

      public int m_6604_() {
         return 2;
      }

      public int m_6601_() {
         return 6;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.ANGLERS_FANG.get()), new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())}
         );
      }
   };
   public static final Tier POISONED_CHAKRA = new Tier() {
      public int m_6609_() {
         return 1200;
      }

      public float m_6624_() {
         return 2.0F;
      }

      public float m_6631_() {
         return -3.0F;
      }

      public int m_6604_() {
         return 0;
      }

      public int m_6601_() {
         return 12;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.ANGLERS_FANG.get()), new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())}
         );
      }
   };
   public static final Tier SWEET_LANCE = new Tier() {
      public int m_6609_() {
         return 1400;
      }

      public float m_6624_() {
         return 6.0F;
      }

      public float m_6631_() {
         return 10.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 14;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(new ItemStack[]{((Item)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()).m_7968_()});
      }
   };
   public static final Tier TERRIBLE_SWORD = new Tier() {
      public int m_6609_() {
         return 750;
      }

      public float m_6624_() {
         return 4.0F;
      }

      public float m_6631_() {
         return 2.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 18;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.ANGLERS_FANG.get()), new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())}
         );
      }
   };
   public static final Tier WHISPER_OF_tHE_ABYSS = new Tier() {
      public int m_6609_() {
         return 1400;
      }

      public float m_6624_() {
         return 6.0F;
      }

      public float m_6631_() {
         return 10.0F;
      }

      public int m_6604_() {
         return 3;
      }

      public int m_6601_() {
         return 14;
      }

      @NotNull
      public Ingredient m_6282_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()), new ItemStack((ItemLike)AquamiraeItems.ABYSSAL_AMETHYST.get())}
         );
      }
   };
   public static final ArmorMaterial TERRIBLE_ARMOR = new ArmorMaterial() {
      public int m_7366_(@NotNull EquipmentSlot slot) {
         return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 25;
      }

      public int m_7365_(@NotNull EquipmentSlot slot) {
         return new int[]{2, 3, 7, 5}[slot.m_20749_()];
      }

      public int m_6646_() {
         return 12;
      }

      @NotNull
      public SoundEvent m_7344_() {
         return SoundEvents.f_11677_;
      }

      @NotNull
      public Ingredient m_6230_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()), new ItemStack((ItemLike)AquamiraeItems.ANGLERS_FANG.get())}
         );
      }

      @NotNull
      public String m_6082_() {
         return "terrible";
      }

      public float m_6651_() {
         return 0.0F;
      }

      public float m_6649_() {
         return 0.0F;
      }
   };
   public static final ArmorMaterial THREE_BOLT_ARMOR = new ArmorMaterial() {
      public int m_7366_(@NotNull EquipmentSlot slot) {
         return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 75;
      }

      public int m_7365_(@NotNull EquipmentSlot slot) {
         return new int[]{3, 5, 5, 7}[slot.m_20749_()];
      }

      public int m_6646_() {
         return 9;
      }

      @NotNull
      public SoundEvent m_7344_() {
         return SoundEvents.f_11677_;
      }

      @NotNull
      public Ingredient m_6230_() {
         return Ingredient.f_43901_;
      }

      @NotNull
      public String m_6082_() {
         return "three_bolt";
      }

      public float m_6651_() {
         return 2.0F;
      }

      public float m_6649_() {
         return 0.1F;
      }
   };
   public static final ArmorMaterial ABYSSAL_ARMOR = new ArmorMaterial() {
      public int m_7366_(@NotNull EquipmentSlot slot) {
         return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 40;
      }

      public int m_7365_(@NotNull EquipmentSlot slot) {
         return new int[]{2, 4, 4, 6}[slot.m_20749_()];
      }

      public int m_6646_() {
         return 12;
      }

      @NotNull
      public SoundEvent m_7344_() {
         return SoundEvents.f_11679_;
      }

      @NotNull
      public Ingredient m_6230_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()), new ItemStack((ItemLike)AquamiraeItems.ABYSSAL_AMETHYST.get())}
         );
      }

      @NotNull
      public String m_6082_() {
         return "abyssal";
      }

      public float m_6651_() {
         return 3.0F;
      }

      public float m_6649_() {
         return 0.1F;
      }
   };
   public static final ArmorMaterial ABYSSAL_ARMOR_EXTRA = new ArmorMaterial() {
      public int m_7366_(@NotNull EquipmentSlot slot) {
         return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 40;
      }

      public int m_7365_(@NotNull EquipmentSlot slot) {
         return new int[]{3, 6, 8, 2}[slot.m_20749_()];
      }

      public int m_6646_() {
         return 12;
      }

      @NotNull
      public SoundEvent m_7344_() {
         return SoundEvents.f_11679_;
      }

      @NotNull
      public Ingredient m_6230_() {
         return Ingredient.m_43927_(
            new ItemStack[]{new ItemStack((ItemLike)AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()), new ItemStack((ItemLike)AquamiraeItems.ABYSSAL_AMETHYST.get())}
         );
      }

      @NotNull
      public String m_6082_() {
         return "abyssal";
      }

      public float m_6651_() {
         return 0.0F;
      }

      public float m_6649_() {
         return 0.0F;
      }
   };
}
