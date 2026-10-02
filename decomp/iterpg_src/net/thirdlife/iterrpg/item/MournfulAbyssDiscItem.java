package net.thirdlife.iterrpg.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.ForgeRegistries;

public class MournfulAbyssDiscItem extends RecordItem {
   public MournfulAbyssDiscItem() {
      super(
         0,
         () -> (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:mournful_abyss_record")),
         new Properties().m_41491_(CreativeModeTab.f_40759_).m_41487_(1).m_41497_(Rarity.RARE),
         100
      );
   }
}
