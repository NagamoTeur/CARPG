package net.mindoth.dreadsteel.item.weapon;

import com.google.common.collect.Multimap;
import com.mojang.math.Vector3f;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.mindoth.dreadsteel.Dreadsteel;
import net.mindoth.dreadsteel.config.DreadsteelCommonConfig;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileBlack;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileBronze;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileDefault;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileWhite;
import net.mindoth.dreadsteel.message.MessageSwingArm;
import net.mindoth.dreadsteel.registries.DreadsteelEntities;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.mindoth.shadowizardlib.event.CommonEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "dreadsteel"
)
public class DreadsteelScythe extends SwordItem {
   static final Map<Item, Map<Attribute, AttributeModifier>> WEAPON_ATTRIBUTE_MODIFIERS = new HashMap<>();
   private static final String ATTACK_DAMAGE_MODIFIER_NAME = new ResourceLocation("dreadsteel", "dreadsteel_attack").toString();
   private static final String ATTACK_SPEED_MODIFIER_NAME = new ResourceLocation("dreadsteel", "dreadsteel_speed").toString();

   public DreadsteelScythe(DreadsteelTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
      super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
   }

   @SubscribeEvent
   public static void dreadsteelScytheAttributeEvent(ItemAttributeModifierEvent event) {
      Item item = event.getItemStack().m_41720_();
      if (item == DreadsteelItems.DREADSTEEL_SCYTHE.get() && event.getSlotType() == EquipmentSlot.MAINHAND) {
         CommonEvents.findAndRemoveVanillaModifier(event, Attributes.f_22281_, f_41374_);
         event.addModifier(Attributes.f_22281_, getAttackDamage(item, (double)((Integer)DreadsteelCommonConfig.SCYTHE_DAMAGE.get()).intValue()));
         CommonEvents.findAndRemoveVanillaModifier(event, Attributes.f_22283_, f_41375_);
         event.addModifier(Attributes.f_22283_, getAttackSpeed(item, (double)((float)((Double)DreadsteelCommonConfig.SCYTHE_SPEED.get() - 4.0))));
      }
   }

   private static AttributeModifier getAttackDamage(Item item, double defaultValue) {
      return getModifier(item, Attributes.f_22281_, f_41374_, ATTACK_DAMAGE_MODIFIER_NAME, defaultValue, Operation.ADDITION);
   }

   private static AttributeModifier getAttackSpeed(Item item, double defaultValue) {
      return getModifier(item, Attributes.f_22283_, f_41375_, ATTACK_SPEED_MODIFIER_NAME, defaultValue, Operation.ADDITION);
   }

   private static AttributeModifier getModifier(Item item, Attribute attribute, UUID uuid, String modifierName, double defaultValue, Operation operation) {
      return WEAPON_ATTRIBUTE_MODIFIERS.computeIfAbsent(item, k -> new HashMap<>())
         .computeIfAbsent(attribute, k -> new AttributeModifier(uuid, modifierName, defaultValue, operation));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("tooltip.dreadsteel.dreadsteel_scythe"));
      super.m_7373_(stack, world, tooltip, flagIn);
   }

   @SubscribeEvent
   public static void onPlayerLeftClick(LeftClickEmpty event) {
      onLeftClick(event.getEntity(), event.getItemStack());
      if (event.getLevel().f_46443_) {
         Dreadsteel.sendMSGToServer(new MessageSwingArm());
      }
   }

   public static void onLeftClick(Player playerEntity, ItemStack stack) {
      if (stack.m_41720_() == DreadsteelItems.DREADSTEEL_SCYTHE.get()) {
         spawnProjectile(stack, playerEntity);
      }
   }

   public static void spawnProjectile(ItemStack stack, Player player) {
      CompoundTag tag = stack.m_41784_();
      if (!((float)player.f_20913_ > 0.5F)) {
         if (player.m_21120_(InteractionHand.MAIN_HAND) == stack) {
            Multimap<Attribute, AttributeModifier> dmg = stack.m_41638_(EquipmentSlot.MAINHAND);
            double totalDmg = 0.0;

            for (AttributeModifier modifier : dmg.get(Attributes.f_22281_)) {
               totalDmg += modifier.m_22218_();
            }

            if (tag.m_128441_("CustomModelData")) {
               if (tag.m_128451_("CustomModelData") == 1) {
                  EntityScytheProjectileWhite shot = new EntityScytheProjectileWhite(
                     (EntityType<? extends AbstractArrow>)DreadsteelEntities.SCYTHE_PROJECTILE_WHITE.get(), player.f_19853_, player, totalDmg
                  );
                  Vec3 vector3d = player.m_20154_();
                  Vector3f vector3f = new Vector3f(vector3d);
                  shot.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), 1.0F, 0.0F);
                  player.f_19853_.m_7967_(shot);
               }

               if (tag.m_128451_("CustomModelData") == 2) {
                  EntityScytheProjectileBlack shot = new EntityScytheProjectileBlack(
                     (EntityType<? extends AbstractArrow>)DreadsteelEntities.SCYTHE_PROJECTILE_BLACK.get(), player.f_19853_, player, totalDmg
                  );
                  Vec3 vector3d = player.m_20154_();
                  Vector3f vector3f = new Vector3f(vector3d);
                  shot.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), 1.0F, 0.0F);
                  player.f_19853_.m_7967_(shot);
               }

               if (tag.m_128451_("CustomModelData") == 3) {
                  EntityScytheProjectileBronze shot = new EntityScytheProjectileBronze(
                     (EntityType<? extends AbstractArrow>)DreadsteelEntities.SCYTHE_PROJECTILE_BRONZE.get(), player.f_19853_, player, totalDmg
                  );
                  Vec3 vector3d = player.m_20154_();
                  Vector3f vector3f = new Vector3f(vector3d);
                  shot.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), 1.0F, 0.0F);
                  player.f_19853_.m_7967_(shot);
               }
            } else {
               EntityScytheProjectileDefault shot = new EntityScytheProjectileDefault(
                  (EntityType<? extends AbstractArrow>)DreadsteelEntities.SCYTHE_PROJECTILE_DEFAULT.get(), player.f_19853_, player, totalDmg
               );
               Vec3 vector3d = player.m_20154_();
               Vector3f vector3f = new Vector3f(vector3d);
               shot.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), 1.0F, 0.0F);
               player.f_19853_.m_7967_(shot);
            }

            player.f_19853_.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12520_, SoundSource.PLAYERS, 0.75F, 0.75F);
         }
      }
   }

   public boolean m_8120_(ItemStack p_77616_1_) {
      return true;
   }
}
