package lykrast.meetyourfight.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.UUID;
import lykrast.meetyourfight.registry.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeMod;

public class TwilightsThorn extends SwordItem {
   private static final Tier TIER = new CustomTier(3, 3873, 8.0F, 6.0F, 16, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.violetBloom.get()}));
   private final Multimap<Attribute, AttributeModifier> defaultModifiers;
   public static final UUID RANGE = UUID.fromString("3080eef9-4ab7-4f8b-9a90-774208857fc9");

   public TwilightsThorn(Properties builderIn) {
      super(TIER, 3, -2.2F, builderIn);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Weapon modifier", (double)this.m_43299_(), Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Weapon modifier", -2.2, Operation.ADDITION));
      builder.put((Attribute)ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(RANGE, "Weapon modifier", 1.0, Operation.ADDITION));
      this.defaultModifiers = builder.build();
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot slot) {
      return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.m_7167_(slot);
   }
}
