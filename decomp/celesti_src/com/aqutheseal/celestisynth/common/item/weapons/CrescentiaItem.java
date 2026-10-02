package com.aqutheseal.celestisynth.common.item.weapons;

import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.attack.cresentia.CrescentiaBarrageAttack;
import com.aqutheseal.celestisynth.common.attack.cresentia.CrescentiaDragonAttack;
import com.aqutheseal.celestisynth.common.item.base.SkilledSwordItem;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.FireworkRocketItem.Shape;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class CrescentiaItem extends SkilledSwordItem {
   public CrescentiaItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   @Override
   public ImmutableList<WeaponAttackInstance> getPossibleAttacks(Player player, ItemStack stack, int dur) {
      return ImmutableList.of(new CrescentiaBarrageAttack(player, stack), new CrescentiaDragonAttack(player, stack));
   }

   @Override
   public int getSkillsAmount() {
      return 2;
   }

   @Override
   public boolean hasPassive() {
      return true;
   }

   @Override
   public int getPassiveAmount() {
      return 1;
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity entity, LivingEntity source) {
      entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 40, 2));
      return super.m_7579_(itemStack, entity, source);
   }

   @Override
   public void forceTick(ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof Player player && (isSelected || player.m_21206_().m_41720_() instanceof CrescentiaItem)) {
         player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 2, 0));
      }

      super.forceTick(itemStack, level, entity, itemSlot, isSelected);
   }

   public static void createCrescentiaFirework(ItemStack itemStack, Level level, Player player, double x, double y, double z, boolean isBig) {
      Random random = new Random();
      ItemStack star = new ItemStack(Items.f_42689_);
      CompoundTag compoundtag = star.m_41698_("Explosion");
      List<Integer> list = Lists.newArrayList();
      DyeColor[] allowedColors = new DyeColor[]{DyeColor.LIGHT_BLUE, DyeColor.WHITE, DyeColor.BLUE, DyeColor.MAGENTA, DyeColor.YELLOW, DyeColor.ORANGE};
      list.add(allowedColors[random.nextInt(allowedColors.length)].m_41070_());
      compoundtag.m_128408_("Colors", list);
      compoundtag.m_128344_("Type", (byte)(isBig ? Shape.LARGE_BALL.m_41236_() : Shape.SMALL_BALL.m_41236_()));
      CompoundTag itemCompound = itemStack.m_41698_("Fireworks");
      ListTag listtag = new ListTag();
      CompoundTag starCompound = star.m_41737_("Explosion");
      if (starCompound != null) {
         listtag.add(starCompound);
      }

      itemCompound.m_128344_("Flight", (byte)3);
      if (!listtag.isEmpty()) {
         itemCompound.m_128365_("Explosions", listtag);
      }

      level.m_7228_(x, y, z, 0.01, 0.01, 0.01, itemCompound);
      player.m_5496_(SoundEvents.f_11930_, 1.0F, 0.5F + random.nextFloat());
   }

   @Override
   public void onPlayerHurt(LivingHurtEvent event, ItemStack mainHandItem, ItemStack offHandItem) {
      LivingEntity entity = event.getEntity();
      CompoundTag tagR = mainHandItem.m_41720_().getShareTag(entity.m_21205_());
      CompoundTag tagL = offHandItem.m_41720_().getShareTag(entity.m_21206_());
      if (tagR != null && tagR.m_128471_("cs.hasAnimationBegun") || tagL != null && tagL.m_128471_("cs.hasAnimationBegun")) {
         event.setAmount(event.getAmount() * 0.7F);
      }
   }
}
