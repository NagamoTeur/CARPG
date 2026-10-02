package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ItemSpear extends MowzieToolItem {
   public ItemSpear(Properties properties) {
      super(
         -2.0F + ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SPEAR.toolConfig.attackDamageValue,
         -4.0F + ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SPEAR.toolConfig.attackSpeedValue,
         Tiers.STONE,
         BlockTags.f_144281_,
         properties
      );
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ == EnchantmentCategory.WEAPON || enchantment.f_44672_ == EnchantmentCategory.BREAKABLE;
   }

   public static LivingEntity raytraceEntities(Level world, Player player, double range) {
      ItemSpear.SpearHitResult result = new ItemSpear.SpearHitResult();
      Vec3 pos = new Vec3(player.m_20185_(), player.m_20186_() + (double)player.m_20192_(), player.m_20189_());
      Vec3 segment = player.m_20154_();
      segment = pos.m_82520_(segment.f_82479_ * range, segment.f_82480_ * range, segment.f_82481_ * range);
      result.setBlockHit(world.m_45547_(new ClipContext(pos, segment, Block.COLLIDER, Fluid.NONE, player)));
      double collidePosX;
      double collidePosY;
      double collidePosZ;
      if (result.blockHit != null) {
         collidePosX = result.blockHit.m_82450_().f_82479_;
         collidePosY = result.blockHit.m_82450_().f_82480_;
         collidePosZ = result.blockHit.m_82450_().f_82481_;
      } else {
         Vec3 end = player.m_20154_().m_82490_(range).m_82549_(pos);
         collidePosX = end.f_82479_;
         collidePosY = end.f_82480_;
         collidePosZ = end.f_82481_;
      }

      List<LivingEntity> entities = world.m_45976_(
         LivingEntity.class,
         new AABB(
               Math.min(pos.f_82479_, collidePosX),
               Math.min(pos.f_82480_, collidePosY),
               Math.min(pos.f_82481_, collidePosZ),
               Math.max(pos.f_82479_, collidePosX),
               Math.max(pos.f_82480_, collidePosY),
               Math.max(pos.f_82481_, collidePosZ)
            )
            .m_82377_(1.0, 1.0, 1.0)
      );
      LivingEntity closest = null;

      for (LivingEntity entity : entities) {
         if (entity != player) {
            float pad = entity.m_6143_();
            AABB aabb = entity.m_20191_().m_82377_((double)pad, (double)pad, (double)pad);
            boolean hit = aabb.m_82335_(pos, segment);
            if (aabb.m_82390_(pos) || hit) {
               result.addEntityHit(entity);
               if (closest == null || player.m_20270_(closest) > player.m_20270_(entity)) {
                  closest = entity;
               }
            }
         }
      }

      return closest;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }

   @Override
   public ConfigHandler.ToolConfig getConfig() {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SPEAR.toolConfig;
   }

   public static class SpearHitResult {
      private HitResult blockHit;
      private final List<LivingEntity> entities = new ArrayList<>();

      public HitResult getBlockHit() {
         return this.blockHit;
      }

      public void setBlockHit(HitResult blockHit) {
         this.blockHit = blockHit;
      }

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }
}
