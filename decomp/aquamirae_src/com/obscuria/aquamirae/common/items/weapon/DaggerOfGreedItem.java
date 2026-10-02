package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Ability.Style;
import java.util.Random;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class DaggerOfGreedItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY_1 = Ability.create("aquamirae", "dagger_of_greed_1").build(DaggerOfGreedItem.class);
   @ClassAbility
   public final Ability ABILITY_2 = Ability.create("aquamirae", "dagger_of_greed_2").build(DaggerOfGreedItem.class);
   @ClassAbility
   public final Ability ABILITY_3 = Ability.create("aquamirae", "dagger_of_greed_3").style(Style.EPIC).build(DaggerOfGreedItem.class);

   public DaggerOfGreedItem() {
      super(AquamiraeTiers.DAGGER_OF_GREED, 3, -2.0F, new Properties().m_41486_().m_41497_(Rarity.UNCOMMON).m_41491_(Aquamirae.TAB));
   }

   public void m_6883_(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
      if (stack.m_41773_() != stack.m_41784_().m_128451_("DamageValue")) {
         stack.m_41721_(stack.m_41784_().m_128451_("DamageValue"));
      }
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
      boolean hurt = super.m_7579_(stack, entity, source);
      Level level = entity.m_9236_();
      if (level.m_5776_()) {
         return hurt;
      } else {
         stack.m_41784_().m_128405_("DamageValue", stack.m_41784_().m_128451_("DamageValue") + 1);
         if (entity instanceof AbstractVillager || entity instanceof AbstractIllager) {
            ItemEntity emerald = new ItemEntity(
               level, entity.m_20185_(), entity.m_20186_() + (double)entity.m_20206_() / 2.0, entity.m_20189_(), new ItemStack(Items.f_42616_)
            );
            emerald.m_32010_(10);
            level.m_7967_(emerald);
            if (!entity.m_6084_()) {
               ItemEntity emeralds = new ItemEntity(
                  level,
                  entity.m_20185_(),
                  entity.m_20186_() + (double)entity.m_20206_() / 2.0,
                  entity.m_20189_(),
                  new ItemStack(Items.f_42616_, new Random().nextInt(3, 8))
               );
               emeralds.m_32010_(10);
               level.m_7967_(emeralds);
            }

            if (entity instanceof AbstractVillager && new Random().nextInt(0, 20) == 20) {
               source.m_7292_(new MobEffectInstance(MobEffects.f_19594_, 24000, 0));
            }
         }

         return hurt;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(@NotNull ItemStack itemstack) {
      return true;
   }
}
