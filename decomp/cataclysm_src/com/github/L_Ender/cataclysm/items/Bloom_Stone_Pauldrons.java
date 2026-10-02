package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Bloom_Stone_Pauldrons extends ArmorItem implements KeybindUsingArmor {
   public Bloom_Stone_Pauldrons(Armortier material, EquipmentSlot slot, Properties properties) {
      super(material, slot, properties);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getArmorRenderProperties());
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "cataclysm:textures/armor/bloom_stone_pauldrons.png";
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(
         Component.m_237110_("item.cataclysm.bloom_stone_pauldrons.desc", new Object[]{ModKeybind.CHESTPLATE_KEY_ABILITY.m_90863_()})
            .m_130940_(ChatFormatting.DARK_GREEN)
      );
   }

   public void m_6883_(ItemStack stack, Level level, Entity entity, int i, boolean held) {
      super.m_6883_(stack, level, entity, i, held);
      if (entity instanceof Player living
         && living.m_6844_(EquipmentSlot.CHEST).m_41720_() == ModItems.BLOOM_STONE_PAULDRONS.get()
         && level.f_46443_
         && Cataclysm.PROXY.getClientSidePlayer() == entity
         && Cataclysm.PROXY.isKeyDown(6)) {
         Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.CHEST.ordinal(), living.m_19879_(), 6));
         this.onKeyPacket(living, stack, 6);
      }
   }

   @Override
   public void onKeyPacket(Player player, ItemStack itemStack, int Type) {
      if (player != null && !player.m_36335_().m_41519_(this)) {
         for (int i = 0; i < 8; i++) {
            float throwAngle = (float)i * (float) Math.PI / 4.0F;
            double sx = player.m_20185_() + (double)(Mth.m_14089_(throwAngle) * 1.0F);
            double sy = player.m_20186_() + (double)player.m_20206_() * 0.5;
            double sz = player.m_20189_() + (double)(Mth.m_14031_(throwAngle) * 1.0F);
            double vx = (double)Mth.m_14089_(throwAngle);
            double vy = (double)(0.0F + player.m_217043_().m_188501_() * 0.3F);
            double vz = (double)Mth.m_14031_(throwAngle);
            double v3 = (double)Mth.m_14116_((float)(vx * vx + vz * vz));
            Amethyst_Cluster_Projectile_Entity projectile = new Amethyst_Cluster_Projectile_Entity(
               (EntityType<Amethyst_Cluster_Projectile_Entity>)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(),
               player.f_19853_,
               player,
               (float)CMConfig.AmethystClusterdamage
            );
            projectile.m_7678_(sx, sy, sz, (float)i * 11.25F, player.m_146909_());
            float speed = 0.8F;
            projectile.m_6686_(vx, vy + v3 * 0.2F, vz, speed, 1.0F);
            player.f_19853_.m_7967_(projectile);
         }

         player.m_36335_().m_41524_((Item)ModItems.BLOOM_STONE_PAULDRONS.get(), 240);
      }
   }
}
