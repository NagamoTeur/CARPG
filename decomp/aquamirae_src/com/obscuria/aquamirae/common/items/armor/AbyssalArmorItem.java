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
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "armor"
)
public abstract class AbyssalArmorItem extends ArmorItem {
   public final Ability ABILITY_HALFSET = Ability.create("aquamirae", "abyssal_armor_half").style(Style.ATTRIBUTE).build(AbyssalArmorItem.class);
   public final Ability ABILITY_FULLSET_1 = Ability.create("aquamirae", "abyssal_armor_full_1").var(90, "s").build(AbyssalArmorItem.class);
   public final Ability ABILITY_FULLSET_2 = Ability.create("aquamirae", "abyssal_armor_full_2").build(AbyssalArmorItem.class);
   public final Bonus BONUS_1 = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Type.POWER, Operation.AMOUNT).value(3).build();
   public final Bonus BONUS_2 = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Type.POWER, Operation.PERCENT).value(25).build();

   public AbyssalArmorItem(EquipmentSlot slot, @NotNull Properties properties) {
      super(AquamiraeTiers.ABYSSAL_ARMOR, slot, properties.m_41497_(Rarity.EPIC).m_41491_(Aquamirae.TAB));
   }

   public void onArmorTick(ItemStack itemstack, Level world, Player player) {
      if (ItemUtils.getArmorPieces(player, new Class[]{AbyssalArmorItem.class, AbyssalArmorExtraItem.class}) >= 2) {
         MobEffectInstance EFFECT = player.m_21124_((MobEffect)AquamiraeMobEffects.STRONG_ARMOR.get());
         if (EFFECT != null) {
            EFFECT.m_19558_(new MobEffectInstance((MobEffect)AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
         } else {
            player.m_7292_(new MobEffectInstance((MobEffect)AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
         }
      }
   }

   public static class Boots extends AbyssalArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET_1;
      @ClassAbility
      public final Ability ABILITY_FULLSET_2;
      @ClassBonus
      public final Bonus BONUS;

      public Boots() {
         super(EquipmentSlot.FEET, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
         this.ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
         this.BONUS = super.BONUS_1;
      }

      public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @OnlyIn(Dist.CLIENT)
               @NotNull
               public HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(
                  LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel
               ) {
                  HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "left_leg",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).left_boot,
                           "right_leg",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).right_boot,
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "body",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
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
         return "aquamirae:textures/entity/armor/abyssal_boots.png";
      }
   }

   public static class Brigantine extends AbyssalArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET_1;
      @ClassAbility
      public final Ability ABILITY_FULLSET_2;
      @ClassBonus
      public final Bonus BONUS;

      public Brigantine() {
         super(EquipmentSlot.CHEST, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
         this.ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
         this.BONUS = super.BONUS_2;
      }

      public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @OnlyIn(Dist.CLIENT)
               @NotNull
               public HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(
                  LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel
               ) {
                  HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "body",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).body,
                           "left_arm",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).left_arm,
                           "right_arm",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).right_arm,
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
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
         return "aquamirae:textures/entity/armor/abyssal_brigantine.png";
      }
   }

   public static class Heaume extends AbyssalArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET_1;
      @ClassAbility
      public final Ability ABILITY_FULLSET_2;
      @ClassBonus
      public final Bonus BONUS;

      public Heaume() {
         super(EquipmentSlot.HEAD, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
         this.ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
         this.BONUS = super.BONUS_1;
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
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).helmet,
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
         return "aquamirae:textures/entity/armor/abyssal_heaume.png";
      }
   }

   public static class Leggings extends AbyssalArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET_1;
      @ClassAbility
      public final Ability ABILITY_FULLSET_2;
      @ClassBonus
      public final Bonus BONUS;

      public Leggings() {
         super(EquipmentSlot.LEGS, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
         this.ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
         this.BONUS = super.BONUS_2;
      }

      public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @OnlyIn(Dist.CLIENT)
               @NotNull
               public HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(
                  LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel
               ) {
                  HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "left_leg",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).left_leg,
                           "right_leg",
                           (new ModelAbyssalArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.ABYSSAL_ARMOR))).right_leg,
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "body",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
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
         return "aquamirae:textures/entity/armor/abyssal_leggings.png";
      }
   }
}
