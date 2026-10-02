package com.obscuria.aquamirae.common.items.armor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.Bonus;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassBonus;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Bonus.Operation;
import com.obscuria.obscureapi.api.common.classes.Bonus.Type;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
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
public abstract class ThreeBoltArmorItem extends ArmorItem {
   public final Ability ABILITY_HALFSET = Ability.create("aquamirae", "three_bolt_armor_half").build(ThreeBoltArmorItem.class);
   public final Ability ABILITY_FULLSET = Ability.create("aquamirae", "three_bolt_armor_full").build(ThreeBoltArmorItem.class);
   public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor").type(Type.POWER, Operation.PERCENT).value(30).build();

   public ThreeBoltArmorItem(EquipmentSlot slot, @NotNull Properties properties) {
      super(AquamiraeTiers.THREE_BOLT_ARMOR, slot, properties.m_41491_(Aquamirae.TAB));
   }

   public static class Boots extends ThreeBoltArmorItem {
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
         this.BONUS = super.BONUS;
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
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_leg",
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).right_boot,
                           "left_leg",
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).left_boot
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
         return "aquamirae:textures/entity/armor/three_bolt_boots.png";
      }
   }

   public static class Chestplate extends ThreeBoltArmorItem {
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
         this.BONUS = super.BONUS;
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
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).body,
                           "left_arm",
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).left_arm,
                           "right_arm",
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).right_arm,
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
         return "aquamirae:textures/entity/armor/three_bolt_chestplate.png";
      }

      public void onArmorTick(ItemStack stack, Level world, @NotNull Player entity) {
         if (entity.m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof ThreeBoltArmorItem && entity.m_20146_() <= 0) {
            entity.m_20301_(280);
            if (stack.m_220157_(10, RandomSource.m_216327_(), null)) {
               stack.m_41774_(1);
               stack.m_41721_(0);
            }

            if (!entity.m_9236_().m_5776_()) {
               entity.m_9236_()
                  .m_5594_(
                     null,
                     new BlockPos(entity.m_20185_(), entity.m_20186_(), entity.m_20189_()),
                     (SoundEvent)AquamiraeSounds.EFFECT_OXYGEN.get(),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
            }
         }
      }
   }

   public static class Helmet extends ThreeBoltArmorItem {
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
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).head,
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

               public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
                  if ((Boolean)AquamiraeConfig.Client.overlay.get()) {
                     RenderSystem.m_69465_();
                     RenderSystem.m_69458_(false);
                     RenderSystem.m_69453_();
                     RenderSystem.m_157427_(GameRenderer::m_172817_);
                     RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
                     RenderSystem.m_157456_(0, new ResourceLocation("aquamirae", "textures/screens/three_bolt_overlay.png"));
                     Tesselator tesselator = Tesselator.m_85913_();
                     BufferBuilder bufferbuilder = tesselator.m_85915_();
                     bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85817_);
                     bufferbuilder.m_5483_(0.0, (double)height, -90.0).m_7421_(0.0F, 1.0F).m_5752_();
                     bufferbuilder.m_5483_((double)width, (double)height, -90.0).m_7421_(1.0F, 1.0F).m_5752_();
                     bufferbuilder.m_5483_((double)width, 0.0, -90.0).m_7421_(1.0F, 0.0F).m_5752_();
                     bufferbuilder.m_5483_(0.0, 0.0, -90.0).m_7421_(0.0F, 0.0F).m_5752_();
                     tesselator.m_85914_();
                     RenderSystem.m_69458_(true);
                     RenderSystem.m_69482_();
                     RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
                  }
               }
            }
         );
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "aquamirae:textures/entity/armor/three_bolt_helmet.png";
      }
   }

   public static class Leggings extends ThreeBoltArmorItem {
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
         this.BONUS = super.BONUS;
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
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).leggings_body,
                           "left_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_leg",
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).right_leg,
                           "left_leg",
                           (new ModelThreeBoltArmor(Minecraft.m_91087_().m_167973_().m_171103_(AquamiraeLayers.THREE_BOLT_ARMOR))).left_leg
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
         return "aquamirae:textures/entity/armor/three_bolt_leggings.png";
      }
   }
}
