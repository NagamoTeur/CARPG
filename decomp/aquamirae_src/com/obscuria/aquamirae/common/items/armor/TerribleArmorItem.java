package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.Bonus;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassBonus;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Ability.Cost.Type;
import com.obscuria.obscureapi.api.common.classes.Bonus.Operation;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "armor"
)
public abstract class TerribleArmorItem extends ArmorItem {
   public final Ability ABILITY_HALFSET = Ability.create("aquamirae", "terrible_armor_half")
      .cost(Type.COOLDOWN, 10)
      .var(120, "%")
      .var(6, "s")
      .build(TerribleArmorItem.class);
   public final Ability ABILITY_FULLSET = Ability.create("aquamirae", "terrible_armor_full").var(4, "s").build(TerribleArmorItem.class);

   public TerribleArmorItem(EquipmentSlot slot, @NotNull Properties properties) {
      super(AquamiraeTiers.TERRIBLE_ARMOR, slot, properties.m_41491_(Aquamirae.TAB));
   }

   public static class Boots extends TerribleArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET;
      @ClassBonus
      public final Bonus BONUS;

      public Boots() {
         super(EquipmentSlot.FEET, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET = super.ABILITY_FULLSET;
         this.BONUS = Bonus.create()
            .target(Aquamirae.SEA_WOLF, "weapon")
            .type(com.obscuria.obscureapi.api.common.classes.Bonus.Type.COOLDOWN, Operation.PERCENT)
            .value(-20)
            .build();
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
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).left_shoe,
                           "right_leg",
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).right_shoe,
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
         return "aquamirae:textures/entity/armor/terrible_boots.png";
      }
   }

   public static class Chestplate extends TerribleArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET;
      @ClassBonus
      public final Bonus BONUS;

      public Chestplate() {
         super(EquipmentSlot.CHEST, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET = super.ABILITY_FULLSET;
         this.BONUS = Bonus.create()
            .target(Aquamirae.SEA_WOLF, "weapon")
            .type(com.obscuria.obscureapi.api.common.classes.Bonus.Type.POWER, Operation.PERCENT)
            .value(10)
            .build();
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
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).body,
                           "left_arm",
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).left_arm,
                           "right_arm",
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).right_arm,
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
         return "aquamirae:textures/entity/armor/terrible_chestplate.png";
      }
   }

   public static class Helmet extends TerribleArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET;
      @ClassBonus
      public final Bonus BONUS;

      public Helmet() {
         super(EquipmentSlot.HEAD, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET = super.ABILITY_FULLSET;
         this.BONUS = Bonus.create()
            .target(Aquamirae.SEA_WOLF, "weapon")
            .type(com.obscuria.obscureapi.api.common.classes.Bonus.Type.POWER, Operation.PERCENT)
            .value(20)
            .build();
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
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).head,
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
         return "aquamirae:textures/entity/armor/terrible_helmet.png";
      }
   }

   public static class Leggings extends TerribleArmorItem {
      @ClassAbility
      public final Ability ABILITY_HALFSET;
      @ClassAbility
      public final Ability ABILITY_FULLSET;
      @ClassBonus
      public final Bonus BONUS;

      public Leggings() {
         super(EquipmentSlot.LEGS, new Properties());
         this.ABILITY_HALFSET = super.ABILITY_HALFSET;
         this.ABILITY_FULLSET = super.ABILITY_FULLSET;
         this.BONUS = Bonus.create()
            .target(Aquamirae.SEA_WOLF, "weapon")
            .type(com.obscuria.obscureapi.api.common.classes.Bonus.Type.COOLDOWN, Operation.PERCENT)
            .value(-10)
            .build();
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
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).left_shoe2,
                           "right_leg",
                           (new ModelTerribleArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.TERRIBLE_ARMOR))).right_shoe2,
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
         return "aquamirae:textures/entity/armor/terrible_leggings.png";
      }
   }
}
