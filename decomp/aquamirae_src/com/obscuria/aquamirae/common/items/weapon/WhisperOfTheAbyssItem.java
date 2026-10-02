package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import java.util.UUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class WhisperOfTheAbyssItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "whisper_of_the_abyss")
      .action(
         (stack, entity, target, context, values) -> {
            if (target == null) {
               return false;
            } else {
               MobEffectInstance EFFECT = target.m_21124_((MobEffect)AquamiraeMobEffects.ARMOR_DECREASE.get());
               if (EFFECT != null) {
                  target.m_7292_(
                     new MobEffectInstance(
                        (MobEffect)AquamiraeMobEffects.ARMOR_DECREASE.get(), 20 * (Integer)values.get(0), Math.min(4, EFFECT.m_19564_() + 1), false, false
                     )
                  );
               } else {
                  target.m_7292_(new MobEffectInstance((MobEffect)AquamiraeMobEffects.ARMOR_DECREASE.get(), 20 * (Integer)values.get(0), 0, false, false));
               }

               return true;
            }
         }
      )
      .var(10, "s")
      .build(WhisperOfTheAbyssItem.class);

   public WhisperOfTheAbyssItem() {
      super(AquamiraeTiers.WHISPER_OF_tHE_ABYSS, 3, -3.2F, new Properties().m_41486_().m_41497_(Rarity.EPIC).m_41491_(Aquamirae.TAB));
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot slot) {
      Multimap<Attribute, AttributeModifier> multimap = super.m_7167_(slot);
      if (slot == EquipmentSlot.MAINHAND) {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.putAll(multimap);
         builder.put(
            (Attribute)ForgeMod.ATTACK_RANGE.get(),
            new AttributeModifier(UUID.fromString("AB3F54D3-645C-4F36-A497-9C11A33DB5CF"), "Weapon modifier", 0.25, Operation.MULTIPLY_BASE)
         );
         return builder.build();
      } else {
         return multimap;
      }
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
      boolean hurt = super.m_7579_(stack, entity, source);
      if (hurt) {
         this.ABILITY.use(stack, source, entity, null);
      }

      return hurt;
   }
}
