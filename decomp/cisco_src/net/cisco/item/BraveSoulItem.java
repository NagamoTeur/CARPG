package net.cisco.item;

import net.cisco.init.CiscoModModTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.ForgeRegistries;

public class BraveSoulItem extends RecordItem {
   public BraveSoulItem() {
      super(
         0,
         () -> (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:bravesoul")),
         new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(1).m_41497_(Rarity.RARE),
         2720
      );
   }
}
