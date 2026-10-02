package com.rolfmao.upgradednetherite.content;

import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.rolfmao.upgradedcore.helpers.TooltipHelper;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.init.ModItems;
import com.rolfmao.upgradednetherite.utils.ToolUtil;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.EchoUtil;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite.utils.check.FeatherUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.rolfmao.upgradednetherite.utils.check.GoldUtil;
import com.rolfmao.upgradednetherite.utils.check.PhantomUtil;
import com.rolfmao.upgradednetherite.utils.check.PoisonUtil;
import com.rolfmao.upgradednetherite.utils.check.WaterUtil;
import com.rolfmao.upgradednetherite.utils.check.WitherUtil;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UpgradedNetheriteCrossbow extends CrossbowItem {
   private boolean startSoundPlayed = false;
   private boolean midLoadSoundPlayed = false;

   public UpgradedNetheriteCrossbow(Properties properties) {
      super(properties);
   }

   private static boolean tryLoadProjectiles(LivingEntity livingEntity, ItemStack itemStack) {
      int i = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44959_, itemStack);
      int j = i == 0 ? 1 : 3;
      boolean flag = livingEntity instanceof Player && ((Player)livingEntity).m_150110_().f_35937_;
      ItemStack itemstack = livingEntity.m_6298_(itemStack);
      ItemStack itemstack1 = itemstack.m_41777_();

      for (int k = 0; k < j; k++) {
         if (k > 0) {
            itemstack = itemstack1.m_41777_();
         }

         if (itemstack.m_41619_() && flag) {
            itemstack = new ItemStack(Items.f_42412_);
            itemstack1 = itemstack.m_41777_();
         }

         if (!loadProjectile(livingEntity, itemStack, itemstack, k > 0, flag)) {
            return false;
         }
      }

      return true;
   }

   private static boolean loadProjectile(LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack1, boolean p_220023_3_, boolean p_220023_4_) {
      if (itemStack1.m_41619_()) {
         return false;
      } else {
         boolean flag = p_220023_4_ && itemStack1.m_41720_() instanceof ArrowItem;
         ItemStack itemstack;
         if (!flag && !p_220023_4_ && !p_220023_3_) {
            itemstack = itemStack1.m_41620_(1);
            if (itemStack1.m_41619_() && livingEntity instanceof Player) {
               ((Player)livingEntity).m_150109_().m_36057_(itemStack1);
            }
         } else {
            itemstack = itemStack1.m_41777_();
         }

         addChargedProjectile(itemStack, itemstack);
         return true;
      }
   }

   public static boolean isCharged(ItemStack itemStack) {
      CompoundTag compoundnbt = itemStack.m_41783_();
      return compoundnbt != null && compoundnbt.m_128471_("Charged");
   }

   public static void setCharged(ItemStack itemStack, boolean p_220011_1_) {
      CompoundTag compoundnbt = itemStack.m_41784_();
      compoundnbt.m_128379_("Charged", p_220011_1_);
   }

   private static void addChargedProjectile(ItemStack itemStack, ItemStack itemStack1) {
      CompoundTag compoundnbt = itemStack.m_41784_();
      ListTag listnbt;
      if (compoundnbt.m_128425_("ChargedProjectiles", 9)) {
         listnbt = compoundnbt.m_128437_("ChargedProjectiles", 10);
      } else {
         listnbt = new ListTag();
      }

      CompoundTag compoundnbt1 = new CompoundTag();
      itemStack1.m_41739_(compoundnbt1);
      listnbt.add(compoundnbt1);
      compoundnbt.m_128365_("ChargedProjectiles", listnbt);
   }

   private static List<ItemStack> getChargedProjectiles(ItemStack itemStack) {
      List<ItemStack> list = Lists.newArrayList();
      CompoundTag compoundnbt = itemStack.m_41783_();
      if (compoundnbt != null && compoundnbt.m_128425_("ChargedProjectiles", 9)) {
         ListTag listnbt = compoundnbt.m_128437_("ChargedProjectiles", 10);
         if (listnbt != null) {
            for (int i = 0; i < listnbt.size(); i++) {
               CompoundTag compoundnbt1 = listnbt.m_128728_(i);
               list.add(ItemStack.m_41712_(compoundnbt1));
            }
         }
      }

      return list;
   }

   private static void clearChargedProjectiles(ItemStack itemStack) {
      CompoundTag compoundnbt = itemStack.m_41783_();
      if (compoundnbt != null) {
         ListTag listnbt = compoundnbt.m_128437_("ChargedProjectiles", 9);
         listnbt.clear();
         compoundnbt.m_128365_("ChargedProjectiles", listnbt);
      }
   }

   private static void shootProjectile(
      Level level,
      LivingEntity livingEntity,
      InteractionHand interactionHand,
      ItemStack itemStack,
      ItemStack itemStack1,
      float p_220016_5_,
      boolean p_220016_6_,
      float p_220016_7_,
      float p_220016_8_,
      float p_220016_9_
   ) {
      if (livingEntity instanceof Player playerentity && !level.f_46443_) {
         boolean flag = itemStack1.m_41720_() == Items.f_42688_;
         Projectile projectileentity;
         if (flag) {
            projectileentity = new FireworkRocketEntity(
               level, itemStack1, livingEntity, livingEntity.m_20185_(), livingEntity.m_20188_() - 0.15F, livingEntity.m_20189_(), true
            );
         } else {
            projectileentity = getArrow(level, livingEntity, itemStack, itemStack1);
            if (p_220016_6_ || p_220016_9_ != 0.0F) {
               ((AbstractArrow)projectileentity).f_36705_ = Pickup.CREATIVE_ONLY;
            }
         }

         if (livingEntity instanceof CrossbowAttackMob icrossbowuser) {
            icrossbowuser.m_5811_(icrossbowuser.m_5448_(), itemStack, projectileentity, p_220016_9_);
         } else {
            Vec3 vec31 = livingEntity.m_20289_(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(vec31), p_220016_9_, true);
            Vec3 vec3 = livingEntity.m_20252_(1.0F);
            Vector3f vector3f = new Vector3f(vec3);
            vector3f.m_122251_(quaternion);
            projectileentity.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), p_220016_7_, p_220016_8_);
         }

         itemStack.m_41622_(flag ? 3 : 1, livingEntity, p_220017_1_ -> p_220017_1_.m_21190_(interactionHand));
         if (itemStack.m_41720_() == ModItems.GOLD_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("GoldUpgradedNetheriteBow");
            if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44982_, itemStack) > 0) {
               projectileentity.getPersistentData()
                  .m_128405_("LootingGoldUpgradedNetheriteBow", EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44982_, itemStack));
            }
         }

         if (itemStack.m_41720_() == ModItems.FIRE_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("FireUpgradedNetheriteBow");
            if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44990_, itemStack) > 0) {
               projectileentity.m_20049_("FlameFireUpgradedNetheriteBow");
               projectileentity.m_20254_(100);
            }
         }

         if (itemStack.m_41720_() == ModItems.ENDER_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("EnderUpgradedNetheriteBow");
            if (itemStack.m_41784_().m_128471_("UpgradedNetherite_Tagged")) {
               projectileentity.getPersistentData().m_128385_("UpgradedNetherite_Position", itemStack.m_41784_().m_128465_("UpgradedNetherite_Position"));
               projectileentity.getPersistentData().m_128359_("UpgradedNetherite_Dimension", itemStack.m_41784_().m_128461_("UpgradedNetherite_Dimension"));
               projectileentity.getPersistentData().m_128379_("UpgradedNetherite_Tagged", itemStack.m_41784_().m_128471_("UpgradedNetherite_Tagged"));
            }
         }

         if (itemStack.m_41720_() == ModItems.WATER_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("WaterUpgradedNetheriteBow");
         }

         if (itemStack.m_41720_() == ModItems.WITHER_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("WitherUpgradedNetheriteBow");
         }

         if (itemStack.m_41720_() == ModItems.POISON_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("PoisonUpgradedNetheriteBow");
         }

         if (itemStack.m_41720_() == ModItems.PHANTOM_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("PhantomUpgradedNetheriteBow");
         }

         if (itemStack.m_41720_() == ModItems.FEATHER_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("FeatherUpgradedNetheriteBow");
         }

         if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("CorruptUpgradedNetheriteBow");
            projectileentity.getPersistentData().m_128405_("LootingCorruptUpgradedNetheriteBow", CorruptUtil.intWearingCorrupt(playerentity, true));
         }

         if (itemStack.m_41720_() == ModItems.ECHO_UPGRADED_NETHERITE_CROSSBOW.get()) {
            projectileentity.m_20049_("EchoUpgradedNetheriteBow");
         }

         if (itemStack.m_41720_() != ModItems.NETHERITE_BOW.get()) {
            projectileentity.getPersistentData().m_128350_("getPowerForTime", 1.0F);
         }

         level.m_7967_(projectileentity);
         level.m_6263_(
            null, livingEntity.m_20185_(), livingEntity.m_20186_(), livingEntity.m_20189_(), SoundEvents.f_11847_, SoundSource.PLAYERS, 1.0F, p_220016_5_
         );
      }
   }

   private static AbstractArrow getArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack1) {
      ArrowItem arrowitem = (ArrowItem)(itemStack1.m_41720_() instanceof ArrowItem ? itemStack1.m_41720_() : Items.f_42412_);
      AbstractArrow abstractarrowentity = arrowitem.m_6394_(level, itemStack1, livingEntity);
      if (livingEntity instanceof Player) {
         abstractarrowentity.m_36762_(true);
      }

      abstractarrowentity.m_36740_(SoundEvents.f_11840_);
      abstractarrowentity.m_36793_(true);
      int i = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44961_, itemStack);
      if (i > 0) {
         abstractarrowentity.m_36767_((byte)i);
      }

      return abstractarrowentity;
   }

   public static void performShooting(
      Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack, float p_220014_4_, float p_220014_5_
   ) {
      List<ItemStack> list = getChargedProjectiles(itemStack);
      float[] afloat = getShotPitches(livingEntity.m_217043_());

      for (int i = 0; i < list.size(); i++) {
         ItemStack itemstack = list.get(i);
         boolean flag = livingEntity instanceof Player && ((Player)livingEntity).m_150110_().f_35937_;
         if (!itemstack.m_41619_()) {
            if (i == 0) {
               shootProjectile(level, livingEntity, interactionHand, itemStack, itemstack, afloat[i], flag, p_220014_4_, p_220014_5_, 0.0F);
            } else if (i == 1) {
               shootProjectile(level, livingEntity, interactionHand, itemStack, itemstack, afloat[i], flag, p_220014_4_, p_220014_5_, -10.0F);
            } else if (i == 2) {
               shootProjectile(level, livingEntity, interactionHand, itemStack, itemstack, afloat[i], flag, p_220014_4_, p_220014_5_, 10.0F);
            }
         }
      }

      onCrossbowShot(level, livingEntity, itemStack);
   }

   private static float[] getShotPitches(RandomSource random) {
      boolean flag = random.m_188499_();
      return new float[]{1.0F, getRandomShotPitch(flag, random), getRandomShotPitch(!flag, random)};
   }

   private static float getRandomShotPitch(boolean p_220032_0_, RandomSource random) {
      float f = p_220032_0_ ? 0.63F : 0.43F;
      return 1.0F / (random.m_188501_() * 0.5F + 1.8F) + f;
   }

   private static void onCrossbowShot(Level level, LivingEntity livingEntity, ItemStack itemStack) {
      if (livingEntity instanceof ServerPlayer serverplayerentity) {
         if (!level.f_46443_) {
            CriteriaTriggers.f_10555_.m_65462_(serverplayerentity, itemStack);
         }

         serverplayerentity.m_36246_(Stats.f_12982_.m_12902_(itemStack.m_41720_()));
      }

      clearChargedProjectiles(itemStack);
   }

   public static int getChargeDuration(ItemStack p_220026_0_) {
      int i = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44960_, p_220026_0_);
      return i == 0 ? 25 : 25 - 5 * i;
   }

   private static float getPowerForTime(int p_220031_0_, ItemStack itemStack) {
      float f = (float)p_220031_0_ / (float)getChargeDuration(itemStack);
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   private static float getShootingPower(ItemStack itemStack) {
      return itemStack.m_41720_() == Items.f_42717_ && m_40871_(itemStack, Items.f_42688_) ? 2.4F : 4.725F;
   }

   public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
      return Items.f_42418_.equals(repair.m_41720_());
   }

   public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
      if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get() && enchantment == Enchantments.f_44962_) {
         return false;
      } else if ((
            itemStack.m_41720_() == ModItems.GOLD_UPGRADED_NETHERITE_CROSSBOW.get()
               || itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get()
         )
         && enchantment == Enchantments.f_44982_) {
         return true;
      } else {
         return itemStack.m_41720_() == ModItems.FIRE_UPGRADED_NETHERITE_CROSSBOW.get() && enchantment == Enchantments.f_44990_
            ? true
            : enchantment.f_44672_.m_7454_(itemStack.m_41720_());
      }
   }

   public void m_6883_(ItemStack itemStack, Level level, Entity entity, int slot, boolean p_77663_5_) {
      if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get()
         && itemStack.m_41793_()
         && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44962_, itemStack) > 0) {
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(itemStack);
         if (enchantments.containsKey(Enchantments.f_44962_)) {
            enchantments.remove(Enchantments.f_44962_);
            EnchantmentHelper.m_44865_(enchantments, itemStack);
         }
      }
   }

   public InteractionResult m_6225_(UseOnContext context) {
      InteractionResult iResult = InteractionResult.PASS;
      if (context.m_43723_() != null
         && context.m_43723_().m_6047_()
         && EnderUtil.isEnderRangedWeapon(context.m_43722_())
         && !context.m_43723_().m_36335_().m_41519_(context.m_43722_().m_41720_())) {
         iResult = ToolUtil.EnderSetTag(context);
      }

      return iResult == InteractionResult.PASS ? super.m_6225_(context) : iResult;
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (itemStack.m_41720_() == ModItems.NETHERITE_CROSSBOW.get()) {
            list.add(itemStack);
         } else if (GoldUtil.isGoldRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (FireUtil.isFireRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (EnderUtil.isEnderRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (WaterUtil.isWaterRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (WitherUtil.isWitherRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (PoisonUtil.isPoisonRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (PhantomUtil.isPhantomRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (FeatherUtil.isFeatherRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (CorruptUtil.isCorruptRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (EchoUtil.isEchoRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }

   public Predicate<ItemStack> m_6442_() {
      return f_43006_;
   }

   public Predicate<ItemStack> m_6437_() {
      return f_43005_;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
      ItemStack itemstack = player.m_21120_(interactionHand);
      if (isCharged(itemstack)) {
         performShooting(level, player, interactionHand, itemstack, getShootingPower(itemstack), 1.0F);
         setCharged(itemstack, false);
         return InteractionResultHolder.m_19096_(itemstack);
      } else if (!player.m_6298_(itemstack).m_41619_()) {
         if (!isCharged(itemstack)) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            player.m_6672_(interactionHand);
         }

         return InteractionResultHolder.m_19096_(itemstack);
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   public void m_5551_(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeLeft) {
      int i = this.m_8105_(itemStack) - timeLeft;
      float f = getPowerForTime(i, itemStack);
      if (f >= 1.0F && !isCharged(itemStack) && tryLoadProjectiles(livingEntity, itemStack)) {
         setCharged(itemStack, true);
         SoundSource soundcategory = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
         level.m_6263_(
            null,
            livingEntity.m_20185_(),
            livingEntity.m_20186_(),
            livingEntity.m_20189_(),
            SoundEvents.f_11841_,
            soundcategory,
            1.0F,
            1.0F / (level.f_46441_.m_188501_() * 0.5F + 1.0F) + 0.2F
         );
      }
   }

   public void m_5929_(Level level, LivingEntity livingEntity, ItemStack itemStack, int p_219972_4_) {
      if (!level.f_46443_) {
         int i = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44960_, itemStack);
         SoundEvent soundevent = this.getStartSound(i);
         SoundEvent soundevent1 = i == 0 ? SoundEvents.f_11842_ : null;
         float f = (float)(itemStack.m_41779_() - p_219972_4_) / (float)getChargeDuration(itemStack);
         if (f < 0.2F) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
         }

         if (f >= 0.2F && !this.startSoundPlayed) {
            this.startSoundPlayed = true;
            level.m_6263_(null, livingEntity.m_20185_(), livingEntity.m_20186_(), livingEntity.m_20189_(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
         }

         if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
            this.midLoadSoundPlayed = true;
            level.m_6263_(null, livingEntity.m_20185_(), livingEntity.m_20186_(), livingEntity.m_20189_(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
         }
      }
   }

   public int m_8105_(ItemStack itemStack) {
      return getChargeDuration(itemStack) + 3;
   }

   public UseAnim m_6164_(ItemStack itemStack) {
      return UseAnim.CROSSBOW;
   }

   private SoundEvent getStartSound(int p_220025_1_) {
      switch (p_220025_1_) {
         case 1:
            return SoundEvents.f_11844_;
         case 2:
            return SoundEvents.f_11845_;
         case 3:
            return SoundEvents.f_11846_;
         default:
            return SoundEvents.f_11843_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
      List<ItemStack> list = getChargedProjectiles(itemStack);
      if (!UpgradedNetheriteConfig.DisableTooltips) {
         Item crossbow = itemStack.m_41720_();
         if (crossbow != ModItems.NETHERITE_CROSSBOW.get()) {
            if (Screen.m_96638_()) {
               if (!GoldUtil.isGoldRangedWeapon(itemStack)
                  || !UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon && !UpgradedNetheriteConfig.EnableLootingBonus) {
                  if (!FireUtil.isFireRangedWeapon(itemStack)
                     || !UpgradedNetheriteConfig.EnableDamageBonusFireWeapon && !UpgradedNetheriteConfig.EnableAutoSmelt) {
                     if (!EnderUtil.isEnderRangedWeapon(itemStack)
                        || !UpgradedNetheriteConfig.EnablePreventTeleport && !UpgradedNetheriteConfig.EnableTeleportChest) {
                        if (!WaterUtil.isWaterRangedWeapon(itemStack)
                           || !UpgradedNetheriteConfig.EnableDamageBonusWaterWeapon && !UpgradedNetheriteConfig.EnableMiningSpeedUnderwater) {
                           if (WitherUtil.isWitherRangedWeapon(itemStack)) {
                              if (UpgradedNetheriteConfig.EnableWitherEffect) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Wither_Weapon.TT"));
                              }

                              if (UpgradedNetheriteConfig.EnableDamageBonusWitherWeapon) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                                 TooltipHelper.addTWO(
                                    tooltip, "upgradednetherite.Wither_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusWitherWeapon + "%"}
                                 );
                              }
                           } else if (PoisonUtil.isPoisonRangedWeapon(itemStack)) {
                              if (UpgradedNetheriteConfig.EnablePoisonEffect) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Poison_Weapon.TT"));
                              }

                              if (UpgradedNetheriteConfig.EnableDamageBonusPoisonWeapon) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                                 TooltipHelper.addTWO(
                                    tooltip, "upgradednetherite.Poison_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusPoisonWeapon + "%"}
                                 );
                              }
                           } else if (!PhantomUtil.isPhantomRangedWeapon(itemStack)
                              || !UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon
                                 && !UpgradedNetheriteConfig.EnableGlowingEffect
                                 && !UpgradedNetheriteConfig.EnableReachEffect) {
                              if (FeatherUtil.isFeatherRangedWeapon(itemStack)) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bow.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                                 TooltipHelper.addTWO(tooltip, "upgradednetherite.Feather_Tool.TT", new Object[]{"§7• "});
                              } else if (CorruptUtil.isCorruptRangedWeapon(itemStack)) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Malus.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Bonus2.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Enchant.TT"));
                                 if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon || UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                    tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                                 }

                                 if (Minecraft.m_91087_().f_91074_ != null && CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true) > 0) {
                                    if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon) {
                                       TooltipHelper.addTWO(
                                          tooltip,
                                          "upgradednetherite.Corrupt_Weapon3.TT",
                                          new Object[]{
                                             "§6"
                                                + CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true)
                                                   * UpgradedNetheriteConfig.DamageBonusCorruptWeapon
                                                + "%"
                                          }
                                       );
                                    }

                                    if (UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
                                       TooltipHelper.addTWO(
                                          tooltip,
                                          "upgradednetherite.Corrupt_Weapon4.TT",
                                          new Object[]{
                                             "§6"
                                                + CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true)
                                                   * UpgradedNetheriteConfig.LootingBonusCorruptWeapon
                                          }
                                       );
                                    }
                                 }

                                 float EnchantBonus = 0.0F;
                                 Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(itemStack);
                                 if (!enchantments.isEmpty() && enchantments.containsKey(Enchantments.f_44982_)) {
                                    int EnchantLevel = enchantments.get(Enchantments.f_44982_);
                                    EnchantBonus = (float)EnchantLevel;
                                 }

                                 if (!(EnchantBonus > 0.0F)) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Gold_Bow.TT"));
                                 }

                                 if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon) {
                                    TooltipHelper.addTWO(
                                       tooltip,
                                       "upgradednetherite.Corrupt_Weapon2.TT",
                                       new Object[]{"§d" + UpgradedNetheriteConfig.DamageBonusCorruptWeapon + "%"}
                                    );
                                 }

                                 if (UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
                                    TooltipHelper.addTWO(
                                       tooltip, "upgradednetherite.Corrupt_Weapon.TT", new Object[]{"§d" + UpgradedNetheriteConfig.LootingBonusCorruptWeapon}
                                    );
                                 }
                              } else if (EchoUtil.isEchoRangedWeapon(itemStack)
                                 && (UpgradedNetheriteConfig.EnableDamageBonusEchoWeapon || UpgradedNetheriteConfig.EnableBonusExpEcho)) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                                 if (UpgradedNetheriteConfig.EnableDamageBonusEchoWeapon) {
                                    TooltipHelper.addTWO(
                                       tooltip, "upgradednetherite.Echo_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusEchoWeapon + "%"}
                                    );
                                 }

                                 if (UpgradedNetheriteConfig.EnableBonusExpEcho) {
                                    TooltipHelper.addTWO(tooltip, "upgradednetherite.Echo_Tool2.TT", new Object[0]);
                                 }
                              } else {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Disabled.TT"));
                              }
                           } else {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                              if (UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon) {
                                 TooltipHelper.addTWO(
                                    tooltip, "upgradednetherite.Phantom_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusPhantomWeapon + "%"}
                                 );
                              }

                              if (UpgradedNetheriteConfig.EnableGlowingEffect) {
                                 TooltipHelper.addTWO(tooltip, "upgradednetherite.Phantom_Tool.TT", new Object[]{"§7• "});
                              }

                              if (UpgradedNetheriteConfig.EnableReachEffect) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Phantom_Tool2.TT"));
                              }
                           }
                        } else {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           if (UpgradedNetheriteConfig.EnableDamageBonusWaterWeapon) {
                              TooltipHelper.addTWO(
                                 tooltip, "upgradednetherite.Water_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusWaterWeapon + "%"}
                              );
                           }

                           if (UpgradedNetheriteConfig.EnableMiningSpeedUnderwater) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Water_Tool.TT"));
                           }
                        }
                     } else {
                        if (UpgradedNetheriteConfig.EnablePreventTeleport) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Ender_Weapon.TT"));
                        }

                        if (UpgradedNetheriteConfig.EnableTeleportChest) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Ender_Tool.TT"));
                           if (itemStack.m_41783_() != null
                              && itemStack.m_41783_().m_128441_("UpgradedNetherite_Tagged")
                              && itemStack.m_41783_().m_128471_("UpgradedNetherite_Tagged")) {
                              String world = level.m_46472_().m_135782_().m_135815_();
                              if (!world.equals(itemStack.m_41783_().m_128461_("UpgradedNetherite_Dimension"))) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Target.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Ender_Dim.TT"));
                                 tooltip.add(
                                    Component.m_237113_(
                                       "§7• §c"
                                          + itemStack.m_41783_().m_128461_("UpgradedNetherite_Dimension")
                                          + "§7 : §3"
                                          + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[0]
                                          + "§7, §3"
                                          + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[1]
                                          + "§7, §3"
                                          + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[2]
                                          + "§7."
                                    )
                                 );
                              } else {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 tooltip.add(Component.m_237115_("upgradednetherite.Target.TT"));
                                 tooltip.add(
                                    Component.m_237113_(
                                       "§7• §9"
                                          + itemStack.m_41783_().m_128461_("UpgradedNetherite_Dimension")
                                          + "§7 : §3"
                                          + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[0]
                                          + "§7, §3"
                                          + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[1]
                                          + "§7, §3"
                                          + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[2]
                                          + "§7."
                                    )
                                 );
                              }
                           }
                        }
                     }
                  } else {
                     tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                     tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                     if (UpgradedNetheriteConfig.EnableDamageBonusFireWeapon) {
                        float EnchantBonusx = 0.0F;
                        Map<Enchantment, Integer> enchantmentsx = EnchantmentHelper.m_44831_(itemStack);
                        if (!enchantmentsx.isEmpty() && enchantmentsx.containsKey(Enchantments.f_44990_)) {
                           int EnchantLevel = enchantmentsx.get(Enchantments.f_44990_);
                           EnchantBonusx = (float)EnchantLevel;
                        }

                        if (EnchantBonusx >= 1.0F) {
                           TooltipHelper.addTWO(
                              tooltip,
                              "upgradednetherite.Fire_Weapon.TT",
                              new Object[]{"§6" + (UpgradedNetheriteConfig.DamageBonusFireWeapon + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon) + "%"}
                           );
                        } else {
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Fire_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusFireWeapon + "%"}
                           );
                           tooltip.add(Component.m_237115_("upgradednetherite.Fire_CrossBow.TT"));
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Fire_Bow.TT", new Object[]{"§d" + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon + "%"}
                           );
                        }
                     }

                     if (UpgradedNetheriteConfig.EnableAutoSmelt) {
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Fire_Tool.TT", new Object[]{"§7• "});
                     }
                  }
               } else {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon) {
                     TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusGoldWeapon + "%"});
                  }

                  if (UpgradedNetheriteConfig.EnableLootingBonus) {
                     float EnchantBonusxx = 0.0F;
                     Map<Enchantment, Integer> enchantmentsxx = EnchantmentHelper.m_44831_(itemStack);
                     if (!enchantmentsxx.isEmpty() && enchantmentsxx.containsKey(Enchantments.f_44982_)) {
                        int EnchantLevel = enchantmentsxx.get(Enchantments.f_44982_);
                        EnchantBonusxx = (float)EnchantLevel;
                     }

                     if (EnchantBonusxx >= 3.0F) {
                        TooltipHelper.addTWO(
                           tooltip,
                           "upgradednetherite.Gold_Weapon2.TT",
                           new Object[]{"§6" + (UpgradedNetheriteConfig.LootingBonus + UpgradedNetheriteConfig.LootingEnchantBonus)}
                        );
                     } else {
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.LootingBonus});
                        if (!(EnchantBonusxx > 0.0F)) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Gold_Bow.TT"));
                        }

                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon3.TT", new Object[]{"§d" + UpgradedNetheriteConfig.LootingEnchantBonus});
                     }
                  }
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
               if (EnderUtil.isEnderRangedWeapon(itemStack)) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  if (itemStack.m_41783_() != null
                     && itemStack.m_41783_().m_128441_("UpgradedNetherite_Tagged")
                     && itemStack.m_41783_().m_128471_("UpgradedNetherite_Tagged")) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Ender_ToolTar.TT"));
                  } else {
                     tooltip.add(Component.m_237115_("upgradednetherite.Ender_ToolReq.TT"));
                  }
               }
            }
         }
      }

      if (isCharged(itemStack) && !list.isEmpty()) {
         ItemStack itemstack = list.get(0);
         tooltip.add(Component.m_237115_("item.minecraft.crossbow.projectile").m_130946_(" ").m_7220_(itemstack.m_41611_()));
         if (tooltipFlag.m_7050_() && itemstack.m_41720_() == Items.f_42688_) {
            List<Component> list1 = Lists.newArrayList();
            Items.f_42688_.m_7373_(itemstack, level, list1, tooltipFlag);
            if (!list1.isEmpty()) {
               for (int i = 0; i < list1.size(); i++) {
                  list1.set(i, Component.m_237113_("  ").m_7220_(list1.get(i)).m_130940_(ChatFormatting.GRAY));
               }

               tooltip.addAll(list1);
            }
         }
      }
   }

   public int m_6615_() {
      return 8;
   }
}
