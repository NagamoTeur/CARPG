package lykrast.meetyourfight.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

public class SpectresGrasp extends CurioBaseItem {
   public SpectresGrasp(Properties properties) {
      super(properties, false);
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
      map.put((Attribute)ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(uuid, "Reach bonus", 2.0, Operation.ADDITION));
      return map;
   }
}
