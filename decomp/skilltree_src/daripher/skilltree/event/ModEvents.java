package daripher.skilltree.event;

import daripher.skilltree.config.Config;
import daripher.skilltree.init.PSTItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "skilltree"
)
public class ModEvents {
   @SubscribeEvent
   public static void dropAmnesiaScroll(LivingDropsEvent event) {
      if (Config.dragon_drops_amnesia_scroll) {
         LivingEntity entity = event.getEntity();
         if (entity.m_6095_() == EntityType.f_20565_) {
            ItemStack scroll = new ItemStack((ItemLike)PSTItems.AMNESIA_SCROLL.get());
            event.getDrops().add(new ItemEntity(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), scroll));
         }
      }
   }
}
