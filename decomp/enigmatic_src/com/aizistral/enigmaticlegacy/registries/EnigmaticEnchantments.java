package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.api.generic.ConfigurableItem;
import com.aizistral.enigmaticlegacy.enchantments.CeaselessEnchantment;
import com.aizistral.enigmaticlegacy.enchantments.EternalBindingCurse;
import com.aizistral.enigmaticlegacy.enchantments.NemesisCurse;
import com.aizistral.enigmaticlegacy.enchantments.SharpshooterEnchantment;
import com.aizistral.enigmaticlegacy.enchantments.SlayerEnchantment;
import com.aizistral.enigmaticlegacy.enchantments.SorrowCurse;
import com.aizistral.enigmaticlegacy.enchantments.TorrentEnchantment;
import com.aizistral.enigmaticlegacy.enchantments.WrathEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class EnigmaticEnchantments extends AbstractRegistry<Enchantment> {
   private static final EnigmaticEnchantments INSTANCE = new EnigmaticEnchantments();
   @ConfigurableItem("Sharpshooter Enchantment")
   @ObjectHolder(
      value = "enigmaticlegacy:sharpshooter",
      registryName = "enchantment"
   )
   public static final SharpshooterEnchantment SHARPSHOOTER = null;
   @ConfigurableItem("Ceaseless Enchantment")
   @ObjectHolder(
      value = "enigmaticlegacy:ceaseless",
      registryName = "enchantment"
   )
   public static final CeaselessEnchantment CEASELESS = null;
   @ConfigurableItem("Torrent Enchantment")
   @ObjectHolder(
      value = "enigmaticlegacy:torrent",
      registryName = "enchantment"
   )
   public static final TorrentEnchantment TORRENT = null;
   @ConfigurableItem("Wrath Enchantment")
   @ObjectHolder(
      value = "enigmaticlegacy:wrath",
      registryName = "enchantment"
   )
   public static final WrathEnchantment WRATH = null;
   @ConfigurableItem("Slayer Enchantment")
   @ObjectHolder(
      value = "enigmaticlegacy:slayer",
      registryName = "enchantment"
   )
   public static final SlayerEnchantment SLAYER = null;
   @ConfigurableItem("Curse of Nemesis")
   @ObjectHolder(
      value = "enigmaticlegacy:nemesis",
      registryName = "enchantment"
   )
   public static final NemesisCurse NEMESIS = null;
   @ConfigurableItem("Curse of Eternal Binding")
   @ObjectHolder(
      value = "enigmaticlegacy:eternal_binding",
      registryName = "enchantment"
   )
   public static final EternalBindingCurse ETERNAL_BINDING = null;
   @ConfigurableItem("Curse of Sorrow")
   @ObjectHolder(
      value = "enigmaticlegacy:sorrow",
      registryName = "enchantment"
   )
   public static final SorrowCurse SORROW = null;

   private EnigmaticEnchantments() {
      super(ForgeRegistries.ENCHANTMENTS);
      this.register("sharpshooter", () -> new SharpshooterEnchantment(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
      this.register("ceaseless", () -> new CeaselessEnchantment(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
      this.register("nemesis", () -> new NemesisCurse(EquipmentSlot.MAINHAND));
      this.register("torrent", () -> new TorrentEnchantment(EquipmentSlot.MAINHAND));
      this.register("wrath", () -> new WrathEnchantment(EquipmentSlot.MAINHAND));
      this.register("slayer", () -> new SlayerEnchantment(EquipmentSlot.MAINHAND));
      this.register("eternal_binding", () -> new EternalBindingCurse(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
      this.register("sorrow", () -> new SorrowCurse(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
   }
}
