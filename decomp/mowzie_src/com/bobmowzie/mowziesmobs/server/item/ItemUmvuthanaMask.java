package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.item.RenderUmvuthanaMaskItem;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCraneToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ItemUmvuthanaMask extends MowzieArmorItem implements UmvuthanaMask, IAnimatable {
   private final MaskType type;
   private static final ItemUmvuthanaMask.UmvuthanaMaskMaterial UMVUTHANA_MASK_MATERIAL = new ItemUmvuthanaMask.UmvuthanaMaskMaterial();
   public String controllerName = "controller";
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public ItemUmvuthanaMask(MaskType type, Properties properties) {
      super(UMVUTHANA_MASK_MATERIAL, EquipmentSlot.HEAD, properties);
      this.type = type;
   }

   public MobEffect getPotion() {
      return this.type.potion;
   }

   public boolean m_6832_(ItemStack itemStack, ItemStack materialItemStack) {
      return false;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      ItemStack headStack = (ItemStack)player.m_150109_().f_35975_.get(3);
      if (headStack.m_41720_() instanceof ItemSolVisage) {
         if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() && !player.m_7500_()) {
            headStack.m_41622_(2, player, p -> p.m_21190_(hand));
         }

         boolean didSpawn = this.spawnUmvuthana(this.type, stack, player, (float)stack.m_41773_() / (float)stack.m_41776_());
         if (didSpawn) {
            if (!player.m_7500_()) {
               stack.m_41774_(1);
            }

            return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
         }
      }

      return super.m_7203_(world, player, hand);
   }

   private boolean spawnUmvuthana(MaskType mask, ItemStack stack, Player player, float durability) {
      PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
      if (playerCapability != null && playerCapability.getPackSize() < 10) {
         player.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_BELLY.get(), 1.5F, 1.0F);
         player.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_BLOWDART.get(), 1.5F, 0.5F);
         double angle = (double)player.m_6080_();
         if (angle < 0.0) {
            angle += 360.0;
         }

         EntityUmvuthanaFollowerToPlayer umvuthana;
         if (mask == MaskType.FAITH) {
            umvuthana = new EntityUmvuthanaCraneToPlayer(
               (EntityType<? extends EntityUmvuthanaCraneToPlayer>)EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(), player.f_19853_, player
            );
         } else {
            umvuthana = new EntityUmvuthanaFollowerToPlayer(
               (EntityType<? extends EntityUmvuthanaFollowerToPlayer>)EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(), player.f_19853_, player
            );
         }

         if (!player.f_19853_.f_46443_) {
            if (mask != MaskType.FAITH) {
               int weapon;
               if (mask != MaskType.FURY) {
                  weapon = umvuthana.randomizeWeapon();
               } else {
                  weapon = 0;
               }

               umvuthana.setWeapon(weapon);
            }

            umvuthana.m_19890_(
               player.m_20185_() + 1.0 * Math.sin(-angle * (Math.PI / 180.0)),
               player.m_20186_() + 1.5,
               player.m_20189_() + 1.0 * Math.cos(-angle * (Math.PI / 180.0)),
               (float)angle,
               0.0F
            );
            umvuthana.setActive(false);
            umvuthana.active = false;
            player.f_19853_.m_7967_(umvuthana);
            double vx = 0.5 * Math.sin(-angle * Math.PI / 180.0);
            double vy = 0.5;
            double vz = 0.5 * Math.cos(-angle * Math.PI / 180.0);
            umvuthana.m_20334_(vx, vy, vz);
            umvuthana.m_21153_((1.0F - durability) * umvuthana.m_21233_());
            umvuthana.setMask(mask);
            umvuthana.setStoredMask(stack.m_41777_());
            if (stack.m_41788_()) {
               umvuthana.m_6593_(stack.m_41786_());
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(
         new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new RenderUmvuthanaMaskItem();

            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
               return armorSlot != EquipmentSlot.HEAD
                  ? null
                  : GeoArmorRenderer.getRenderer(ItemUmvuthanaMask.this.getClass(), entityLiving)
                     .applyEntityStats(_default)
                     .setCurrentItem(entityLiving, itemStack, armorSlot)
                     .applySlot(armorSlot);
            }

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               return this.renderer;
            }
         }
      );
   }

   public MaskType getType() {
      return this.type;
   }

   @Nullable
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      String s = ChatFormatting.m_126649_(stack.m_41786_().getString());
      return new ResourceLocation("mowziesmobs", "textures/item/umvuthana_mask_" + this.type.name + ".png").toString();
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }

   @Override
   public ConfigHandler.ArmorConfig getConfig() {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.UMVUTHANA_MASK.armorConfig;
   }

   public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      List<LivingEntity> livingEntities = event.getExtraDataOfType(LivingEntity.class);
      if (livingEntities.size() > 0 && livingEntities.get(0) instanceof EntityUmvuthana) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("umvuthana", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("player", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, this.controllerName, 0.0F, this::predicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   private static class UmvuthanaMaskMaterial implements ArmorMaterial {
      public int m_7366_(EquipmentSlot equipmentSlotType) {
         return ArmorMaterials.LEATHER.m_7366_(equipmentSlotType);
      }

      public int m_7365_(EquipmentSlot equipmentSlotType) {
         return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.UMVUTHANA_MASK.armorConfig.damageReductionValue;
      }

      public int m_6646_() {
         return ArmorMaterials.LEATHER.m_6646_();
      }

      public SoundEvent m_7344_() {
         return ArmorMaterials.LEATHER.m_7344_();
      }

      public Ingredient m_6230_() {
         return null;
      }

      public String m_6082_() {
         return "umvuthana_mask";
      }

      public float m_6651_() {
         return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.UMVUTHANA_MASK.armorConfig.toughnessValue;
      }

      public float m_6649_() {
         return ArmorMaterials.LEATHER.m_6649_();
      }
   }
}
