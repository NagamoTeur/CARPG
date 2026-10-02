package com.obscuria.aquamirae.registry;

import com.obscuria.obscureapi.registry.ObscureAPIEnchantments;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class AquamiraeCreativeTab {
   public static List<ItemStack> poisonedChakra() {
      List<ItemStack> list = new ArrayList<>();
      ItemStack stack1 = ((Item)AquamiraeItems.POISONED_CHAKRA.get()).m_7968_();
      ItemStack stack2 = ((Item)AquamiraeItems.POISONED_CHAKRA.get()).m_7968_();
      stack1.m_41663_((Enchantment)ObscureAPIEnchantments.DISTANCE.get(), ((Enchantment)ObscureAPIEnchantments.DISTANCE.get()).m_6586_());
      stack1.m_41663_((Enchantment)ObscureAPIEnchantments.FAST_SPIN.get(), ((Enchantment)ObscureAPIEnchantments.FAST_SPIN.get()).m_6586_());
      stack2.m_41663_((Enchantment)ObscureAPIEnchantments.MIRROR.get(), ((Enchantment)ObscureAPIEnchantments.MIRROR.get()).m_6586_());
      list.add(stack1);
      list.add(stack2);
      return list;
   }

   public static List<ItemStack> mazeRose() {
      List<ItemStack> list = new ArrayList<>();
      ItemStack stack1 = ((Item)AquamiraeItems.MAZE_ROSE.get()).m_7968_();
      ItemStack stack2 = ((Item)AquamiraeItems.MAZE_ROSE.get()).m_7968_();
      stack1.m_41663_((Enchantment)ObscureAPIEnchantments.DISTANCE.get(), ((Enchantment)ObscureAPIEnchantments.DISTANCE.get()).m_6586_());
      stack1.m_41663_((Enchantment)ObscureAPIEnchantments.FAST_SPIN.get(), ((Enchantment)ObscureAPIEnchantments.FAST_SPIN.get()).m_6586_());
      stack2.m_41663_((Enchantment)ObscureAPIEnchantments.MIRROR.get(), ((Enchantment)ObscureAPIEnchantments.MIRROR.get()).m_6586_());
      list.add(stack1);
      list.add(stack2);
      return list;
   }

   public static List<ItemStack> logBooks() {
      List<ItemStack> list = new ArrayList<>();
      ItemStack book1 = Items.f_42615_.m_7968_();
      ItemStack book2 = Items.f_42615_.m_7968_();
      ItemStack book3 = Items.f_42615_.m_7968_();
      ListTag pages1 = new ListTag();
      ListTag pages2 = new ListTag();
      ListTag pages3 = new ListTag();
      pages1.add(
         StringTag.m_129297_(
            "{\"text\":\"Entry #12\n\nLast afternoon, Captain Cornelia went to the seabed in search of any additional resources. There are rumors among the crew that the real subject of her expedition is something else.\"}"
         )
      );
      pages1.add(
         StringTag.m_129297_(
            "{\"text\":\"More than a day has passed since the captain's dive. She was wearing the only diving suit we had, so we couldn't help her in any way. Worst of all, she had the master key with her. As our hopes of rescue dwindle, the threat of rebellion grows.\"}"
         )
      );
      book1.m_41784_().m_128359_("title", "Pillagers Ship Logbook");
      book1.m_41784_().m_128359_("author", "Unknown");
      book1.m_41784_().m_128405_("generation", 3);
      book1.m_41784_().m_128379_("resolved", true);
      book1.m_41784_().m_128365_("pages", pages1);
      pages2.add(
         StringTag.m_129297_(
            "{\"text\":\"Entry #34\n\nThese beasts from the depths prowl for someone to devour... One of those critters has already eaten our midshipman... Swallowed him whole along with his crossbow.\"}"
         )
      );
      pages2.add(
         StringTag.m_129297_(
            "{\"text\":\"Entry #35\n\nWe are waiting in vain for rescue... No one will come to help us, we have to survive on our own. Today the construction of the fort from parts of the ship was completed. In any case, she could no longer plow the seas ever again.\"}"
         )
      );
      pages2.add(
         StringTag.m_129297_(
            "{\"extra\":[{\"text\":\"Entry #36\n\nWe are no longer able to heat the fort, resources are running out. \"},{\"color\":\"dark_green\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"extra\":[{\"color\":\"green\",\"text\":\"Underground shelter\"},{\"text\":\"\n  There are more structures\n  underneath the ice... Looks\n  like I have to go down to\n  these terrible fish...\"}],\"text\":\"\"}},\"text\":\"Part of the crew began to dig a shelter underground\"},{\"text\":\". It is the only way we have a chance not to freeze to death.\"}],\"text\":\"\"}"
         )
      );
      pages2.add(
         StringTag.m_129297_(
            "{\"text\":\"Entry #37\n\nWe hid our supplies and valuable cargo underground. But Poseidon cursed us, and during the storm the lieutenant washed away into the sea... Along with master keys.\"}"
         )
      );
      pages2.add(
         StringTag.m_129297_(
            "{\"extra\":[{\"text\":\"The hope remains that we could possibly find \"},{\"color\":\"dark_green\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"extra\":[{\"color\":\"green\",\"text\":\"Cargo keys\"},{\"text\":\"\n  Without the keys, I will not\n  be able to receive the\n  most valuable cargo. I need\n  to find other ships nearby.\"}],\"text\":\"\"}},\"text\":\"similar keys on other ships of our fleet\"},{\"text\":\", which have suffered the same fate as us.\"}],\"text\":\"\"}"
         )
      );
      book2.m_41784_().m_128359_("title", "Pillagers Outpost Logbook");
      book2.m_41784_().m_128359_("author", "Unknown");
      book2.m_41784_().m_128405_("generation", 3);
      book2.m_41784_().m_128379_("resolved", true);
      book2.m_41784_().m_128365_("pages", pages2);
      pages3.add(
         StringTag.m_129297_(
            "{\"text\":\"Entry #41\n\nIn these cursed lands there you can't find shelter even under the ice and the thickness of the earth... Some creatures that look like eels crawled out of the depths of the underworld, depriving us of the opportunity \"}"
         )
      );
      pages3.add(
         StringTag.m_129297_(
            "{\"text\":\"to return back to the surface.\n\nOnly the mistress of the moon knows how long shall we last... At all events, our hopes of seeing sunlight had all but vanished. This place will become our grave, our eternal tomb. For us and that damned rune...\"}"
         )
      );
      pages3.add(
         StringTag.m_129297_(
            "{\"text\":\"Last Entry\n\nIt whispers to me, whispers inside my head the secrets of ice and snow... Ice... Now we are chained in ice forever...\n\nI suppose a similar fate awaits the crew that set out in search of the fire rune... I wonder if they made it\"}"
         )
      );
      pages3.add(StringTag.m_129297_("{\"text\":\"to the Great Dark Forest valley. Though it doesn't really matter now...\"}"));
      book3.m_41784_().m_128359_("title", "Pillagers Shelter Logbook");
      book3.m_41784_().m_128359_("author", "Unknown");
      book3.m_41784_().m_128405_("generation", 3);
      book3.m_41784_().m_128379_("resolved", true);
      book3.m_41784_().m_128365_("pages", pages3);
      list.add(book1);
      list.add(book2);
      list.add(book3);
      return list;
   }
}
