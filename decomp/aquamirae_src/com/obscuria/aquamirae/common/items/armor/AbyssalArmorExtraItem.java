package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.Bonus;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassBonus;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Ability.Style;
import com.obscuria.obscureapi.api.common.classes.Bonus.Operation;
import com.obscuria.obscureapi.api.common.classes.Bonus.Type;
import com.obscuria.obscureapi.util.ItemUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public abstract class AbyssalArmorExtraItem extends ArmorItem {
   public final Ability ABILITY_HALFSET = Ability.create("aquamirae", "abyssal_armor_half").style(Style.ATTRIBUTE).build(AbyssalArmorExtraItem.class);
   public final Ability ABILITY_FULLSET_1 = Ability.create("aquamirae", "abyssal_armor_full_1").var(90, "s").build(AbyssalArmorExtraItem.class);
   public final Ability ABILITY_FULLSET_2 = Ability.create("aquamirae", "abyssal_armor_full_2").build(AbyssalArmorExtraItem.class);
   public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Type.POWER, Operation.AMOUNT).value(3).build();

   public AbyssalArmorExtraItem(EquipmentSlot slot, @NotNull Properties properties) {
      super(AquamiraeTiers.ABYSSAL_ARMOR_EXTRA, slot, properties.m_41497_(Rarity.EPIC).m_41491_(Aquamirae.TAB));
   }

   public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
      if (ItemUtils.getArmorPieces(entity, new Class[]{AbyssalArmorItem.class}) == 3) {
         Vec3 center = entity.m_20182_().m_82520_(0.0, 1.0, 0.0);

         for (Monster monster : world.m_6443_(Monster.class, new AABB(center, center).m_82400_(4.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(entities -> entities.m_20238_(center)))
            .toList()) {
            monster.m_7292_(new MobEffectInstance((MobEffect)AquamiraeMobEffects.ABYSS_BLINDNESS.get(), 10, 0, false, false));
         }

         double radius = 4.0;
         world.m_7106_(
            ParticleTypes.f_123799_,
            entity.m_20185_() + Math.cos((double)entity.f_19797_ * 0.05) * 4.0,
            entity.m_20186_() + 0.5,
            entity.m_20189_() + Math.sin((double)entity.f_19797_ * 0.05) * 4.0,
            0.0,
            0.0,
            0.0
         );
         world.m_7106_(
            ParticleTypes.f_123799_,
            entity.m_20185_() + Math.cos((double)entity.f_19797_ * 0.05 + 3.12) * 4.0,
            entity.m_20186_() + 0.5,
            entity.m_20189_() + Math.sin((double)entity.f_19797_ * 0.05 + 3.12) * 4.0,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @ClassItem(
      clazz = "aquamirae:sea_wolf",
      type = "armor"
   )
   public static class Tiara extends AbyssalArmorExtraItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET_1;
      @ClassAbility
      public final Ability ABILITY_FULLSET_2;
      @ClassBonus
      public final Bonus BONUS;

      public Tiara() {
         super(EquipmentSlot.HEAD, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
         this.ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
         this.BONUS = super.BONUS;
      }

      public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @NotNull
               public HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(
                  LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel
               ) {
                  HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "head",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).tiara,
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "body",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_leg",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_leg",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        )
                     )
                  );
                  armorModel.f_102817_ = living.m_6144_();
                  armorModel.f_102609_ = defaultModel.f_102609_;
                  armorModel.f_102610_ = living.m_6162_();
                  return armorModel;
               }
            }
         );
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "aquamirae:textures/entity/armor/abyssal_tiara.png";
      }
   }
}
