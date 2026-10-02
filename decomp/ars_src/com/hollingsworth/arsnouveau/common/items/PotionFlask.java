package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.api.potion.IPotionProvider;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class PotionFlask extends ModItem implements IPotionProvider {
   public PotionFlask() {
      this(ItemsRegistry.defaultItemProperties().m_41487_(1).m_41503_(8));
   }

   public PotionFlask(Properties props) {
      super(props);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      if (!context.m_43725_().f_46443_ && context.m_43725_().m_7702_(context.m_8083_()) instanceof PotionJarTile jarTile) {
         ItemStack var6 = context.m_43722_();
         PotionFlask.FlaskData data = new PotionFlask.FlaskData(var6);
         Player playerEntity = context.m_43723_();
         if (playerEntity.m_6144_() && data.getCount() > 0 && jarTile.getMaxFill() - jarTile.getAmount() >= 0 && jarTile.canAccept(data.getPotion(), 100)) {
            jarTile.add(data.getPotion(), 100);
            data.setCount(data.getCount() - 1);
         }

         if (!playerEntity.m_6144_() && !this.isMax(var6) && jarTile.getAmount() >= 100) {
            if (data.potionData.areSameEffects(jarTile.getData())) {
               data.setCount(data.getCount() + 1);
               jarTile.remove(100);
            } else if (data.getCount() == 0) {
               data.setPotion(jarTile.getData());
               data.setCount(data.getCount() + 1);
               jarTile.remove(100);
            }
         }

         return super.m_6225_(context);
      } else {
         return super.m_6225_(context);
      }
   }

   public boolean isMax(ItemStack stack) {
      PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
      return data.getCount() >= this.getMaxCapacity();
   }

   public int getMaxCapacity() {
      return 8;
   }

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
      Player playerentity = entityLiving instanceof Player player ? player : null;
      if (!worldIn.f_46443_) {
         PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);

         for (MobEffectInstance effectinstance : data.getPotion().fullEffects()) {
            effectinstance = this.getEffectInstance(effectinstance);
            if (effectinstance.m_19544_().m_8093_()) {
               effectinstance.m_19544_().m_19461_(playerentity, playerentity, entityLiving, effectinstance.m_19564_(), 1.0);
            } else {
               entityLiving.m_7292_(new MobEffectInstance(effectinstance));
            }
         }

         data.setCount(data.getCount() - 1);
      }

      return stack;
   }

   @NotNull
   public abstract MobEffectInstance getEffectInstance(MobEffectInstance var1);

   public int getDamage(ItemStack stack) {
      PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
      return this.getMaxDamage(stack) - data.getCount();
   }

   public int getMaxDamage(ItemStack stack) {
      return this.getMaxCapacity();
   }

   public boolean isDamaged(ItemStack stack) {
      return false;
   }

   public boolean m_142522_(ItemStack pStack) {
      return true;
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 0.0F;
   }

   public boolean m_41465_() {
      return super.m_41465_();
   }

   public int m_8105_(ItemStack stack) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.m_21120_(handIn);
      PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
      return data.getCount() > 0 ? ItemUtils.m_150959_(worldIn, playerIn, handIn) : InteractionResultHolder.m_19098_(playerIn.m_21120_(handIn));
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
      tooltip.add(Component.m_237110_("ars_nouveau.flask.charges", new Object[]{data.getCount()}).m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
      data.potionData.appendHoverText(tooltip);
   }

   @Override
   public PotionData getPotionData(ItemStack stack) {
      PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
      return new PotionFlask.EnchantedPotionData(data.potionData, data.stack.m_41720_());
   }

   public static class EnchantedPotionData extends PotionData {
      public Item flaskPotion;

      public EnchantedPotionData(ItemStack stack) {
         super(stack);
         this.flaskPotion = stack.m_41720_();
      }

      public EnchantedPotionData() {
      }

      public EnchantedPotionData(PotionData potionData, Item item) {
         super(potionData);
         this.flaskPotion = item;
      }

      @Override
      public void applyEffects(Entity source, Entity inDirectSource, LivingEntity target) {
         if (this.flaskPotion instanceof PotionFlask potionFlask) {
            for (MobEffectInstance effectinstance : this.fullEffects()) {
               effectinstance = potionFlask.getEffectInstance(effectinstance);
               if (effectinstance.m_19544_().m_8093_()) {
                  effectinstance.m_19544_().m_19461_(source, inDirectSource, target, effectinstance.m_19564_(), 1.0);
               } else {
                  target.m_147207_(new MobEffectInstance(effectinstance), source);
               }
            }
         } else {
            super.applyEffects(source, inDirectSource, target);
         }
      }
   }

   public static class FlaskData extends ItemstackData {
      private PotionFlask.EnchantedPotionData potionData;
      private int count;

      public FlaskData(ItemStack stack) {
         super(stack);
         CompoundTag tag = this.getItemTag(stack);
         this.potionData = new PotionFlask.EnchantedPotionData();
         if (tag != null) {
            this.potionData = new PotionFlask.EnchantedPotionData(PotionData.fromTag(tag.m_128469_("PotionData")), stack.m_41720_());
            this.count = tag.m_128451_("count");
         }
      }

      public void setCount(int count) {
         this.count = Math.max(count, 0);
         if (count <= 0) {
            this.potionData = new PotionFlask.EnchantedPotionData();
         }

         this.writeItem();
      }

      public int getCount() {
         return this.count;
      }

      public void setPotion(PotionData potion) {
         this.potionData = new PotionFlask.EnchantedPotionData(potion, this.stack.m_41720_());
         this.writeItem();
      }

      public PotionData getPotion() {
         return (PotionData)(this.getCount() <= 0 ? new PotionData() : new PotionFlask.EnchantedPotionData(this.potionData, this.stack.m_41720_()));
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         tag.m_128365_("PotionData", this.potionData.toTag());
         tag.m_128405_("count", this.count);
      }

      @Override
      public String getTagString() {
         return "an_potion_flask";
      }
   }
}
