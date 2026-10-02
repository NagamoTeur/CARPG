package lykrast.meetyourfight.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.SlotContext;

public class WiltedIdeals extends CurioBaseItem {
   public WiltedIdeals(Properties properties) {
      super(properties, true);
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
      map.put(Attributes.f_22276_, new AttributeModifier(uuid, "Wilted Ideals", -0.5, Operation.MULTIPLY_TOTAL));
      return map;
   }

   public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
      if (prevStack != stack && slotContext.entity() != null) {
         slotContext.entity().m_6469_(DamageSource.f_19313_, 1.0F);
      }
   }
}
