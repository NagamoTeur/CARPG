package com.hollingsworth.arsnouveau.common.items;

import com.google.common.collect.Lists;
import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.BasicReductionCaster;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.client.renderer.item.SpellCrossbowRenderer;
import com.hollingsworth.arsnouveau.common.entity.EntitySpellArrow;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class SpellCrossbow extends CrossbowItem implements IAnimatable, ICasterTool {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public SpellCrossbow(Properties pProperties) {
      super(pProperties);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pHand) {
      ItemStack itemstack = pPlayer.m_21120_(pHand);
      if (m_40932_(itemstack)) {
         this.shootStoredProjectiles(pLevel, pPlayer, pHand, itemstack, this.getShootingPower(itemstack), 1.0F);
         m_40884_(itemstack, false);
         return InteractionResultHolder.m_19096_(itemstack);
      } else if (!pPlayer.m_6298_(itemstack).m_41619_()) {
         if (!m_40932_(itemstack)) {
            this.f_40847_ = false;
            this.f_40848_ = false;
            pPlayer.m_6672_(pHand);
         }

         return InteractionResultHolder.m_19096_(itemstack);
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   private float getShootingPower(ItemStack pCrossbowStack) {
      return this.hasChargedProjectile(pCrossbowStack, Items.f_42688_) ? 1.6F : 3.15F;
   }

   public void m_5551_(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
      int i = this.m_8105_(pStack) - pTimeLeft;
      float f = m_40853_(i, pStack);
      if (f >= 1.0F && !m_40932_(pStack) && this.tryLoadProjectiles(pEntityLiving, pStack)) {
         m_40884_(pStack, true);
         SoundSource soundsource = pEntityLiving instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
         pLevel.m_6263_(
            null,
            pEntityLiving.m_20185_(),
            pEntityLiving.m_20186_(),
            pEntityLiving.m_20189_(),
            SoundEvents.f_11841_,
            soundsource,
            1.0F,
            1.0F / (pLevel.m_213780_().m_188501_() * 0.5F + 1.0F) + 0.2F
         );
      }
   }

   private boolean tryLoadProjectiles(LivingEntity pShooter, ItemStack pCrossbowStack) {
      int multishotLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44959_, pCrossbowStack);
      int numProjectiles = multishotLevel == 0 ? 1 : 3;
      boolean isCreative = pShooter instanceof Player && ((Player)pShooter).m_150110_().f_35937_;
      ItemStack ammoStack = pShooter.m_6298_(pCrossbowStack);
      ItemStack ammoCopy = ammoStack.m_41777_();
      ISpellCaster caster = this.getSpellCaster(pCrossbowStack);
      SpellResolver resolver = new SpellResolver(
         new SpellContext(
            pShooter.f_19853_,
            caster.modifySpellBeforeCasting(pShooter.f_19853_, pShooter, InteractionHand.MAIN_HAND, caster.getSpell()),
            pShooter,
            LivingCaster.from(pShooter),
            pCrossbowStack
         )
      );
      boolean consumedMana = false;
      if (!(pShooter instanceof Player) || resolver.withSilent(true).canCast(pShooter)) {
         resolver.expendMana();
         consumedMana = true;
         numProjectiles += resolver.spell.getBuffsAtIndex(0, pShooter, AugmentSplit.INSTANCE);
      }

      if (ammoStack.m_41720_() instanceof FormSpellArrow formSpellArrow && formSpellArrow.part == AugmentSplit.INSTANCE) {
         numProjectiles += formSpellArrow.numParts;
      }

      for (int k = 0; k < numProjectiles; k++) {
         if (k > 0) {
            ammoStack = ammoCopy.m_41777_();
         }

         if (ammoStack.m_41619_() && isCreative) {
            ammoStack = new ItemStack(Items.f_42412_);
            ammoCopy = ammoStack.m_41777_();
         }

         if (!this.loadProjectile(pShooter, pCrossbowStack, ammoStack, k > 0, isCreative, consumedMana)) {
            return false;
         }
      }

      return true;
   }

   private boolean loadProjectile(
      LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative, boolean consumedMana
   ) {
      if (pAmmoStack.m_41619_()) {
         return false;
      } else {
         boolean flag = pIsCreative && pAmmoStack.m_41720_() instanceof ArrowItem;
         ItemStack itemstack;
         if (!flag && !pIsCreative && !pHasAmmo) {
            itemstack = pAmmoStack.m_41620_(1);
            if (pAmmoStack.m_41619_() && pShooter instanceof Player) {
               ((Player)pShooter).m_150109_().m_36057_(pAmmoStack);
            }
         } else {
            itemstack = pAmmoStack.m_41777_();
         }

         this.addChargedProjectile(pCrossbowStack, itemstack, consumedMana);
         return true;
      }
   }

   private void addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean isSpell) {
      CompoundTag compoundtag = pCrossbowStack.m_41784_();
      ListTag listtag;
      if (compoundtag.m_128425_("ChargedProjectiles", 9)) {
         listtag = compoundtag.m_128437_("ChargedProjectiles", 10);
      } else {
         listtag = new ListTag();
      }

      CompoundTag compoundtag1 = new CompoundTag();
      pAmmoStack.m_41739_(compoundtag1);
      listtag.add(compoundtag1);
      compoundtag.m_128365_("ChargedProjectiles", listtag);
      compoundtag.m_128379_("isSpell", isSpell);
   }

   private List<ItemStack> getChargedProjectiles(ItemStack pCrossbowStack) {
      List<ItemStack> list = Lists.newArrayList();
      CompoundTag compoundtag = pCrossbowStack.m_41783_();
      if (compoundtag != null && compoundtag.m_128425_("ChargedProjectiles", 9)) {
         ListTag listtag = compoundtag.m_128437_("ChargedProjectiles", 10);
         if (listtag != null) {
            for (int i = 0; i < listtag.size(); i++) {
               CompoundTag compoundtag1 = listtag.m_128728_(i);
               list.add(ItemStack.m_41712_(compoundtag1));
            }
         }
      }

      return list;
   }

   private void clearChargedProjectiles(ItemStack pCrossbowStack) {
      CompoundTag compoundtag = pCrossbowStack.m_41783_();
      if (compoundtag != null) {
         ListTag listtag = compoundtag.m_128437_("ChargedProjectiles", 9);
         listtag.clear();
         compoundtag.m_128365_("ChargedProjectiles", listtag);
      }
   }

   public boolean hasChargedProjectile(ItemStack pCrossbowStack, Item pAmmoItem) {
      return this.getChargedProjectiles(pCrossbowStack).stream().anyMatch(p_40870_ -> p_40870_.m_150930_(pAmmoItem));
   }

   public void shootOne(
      Level worldIn,
      LivingEntity pShooter,
      InteractionHand pHand,
      ItemStack pCrossbowStack,
      ItemStack pAmmoStack,
      float pSoundPitch,
      boolean pIsCreativeMode,
      float pVelocity,
      float pInaccuracy,
      float pProjectileAngle,
      boolean isSpell
   ) {
      if (!worldIn.f_46443_) {
         boolean flag = pAmmoStack.m_150930_(Items.f_42688_);
         Projectile projectile;
         if (flag) {
            projectile = new FireworkRocketEntity(worldIn, pAmmoStack, pShooter, pShooter.m_20185_(), pShooter.m_20188_() - 0.15F, pShooter.m_20189_(), true);
         } else {
            projectile = this.getArrow(worldIn, pShooter, pCrossbowStack, pAmmoStack);
            if (pIsCreativeMode || pProjectileAngle != 0.0F) {
               ((AbstractArrow)projectile).f_36705_ = Pickup.CREATIVE_ONLY;
            }
         }

         LivingCaster livingCaster = (LivingCaster)(pShooter instanceof Player ? new PlayerCaster((Player)pShooter) : new LivingCaster(pShooter));
         ISpellCaster caster = this.getSpellCaster(pCrossbowStack);
         SpellResolver resolver = new SpellResolver(
            new SpellContext(
               worldIn,
               caster.modifySpellBeforeCasting(worldIn, pShooter, InteractionHand.MAIN_HAND, caster.getSpell()),
               pShooter,
               livingCaster,
               pCrossbowStack
            )
         );
         if (pAmmoStack.m_41720_() == Items.f_42412_ && isSpell) {
            projectile = this.buildSpellArrow(worldIn, pShooter, caster);
            ((EntitySpellArrow)projectile).pierceLeft = ((EntitySpellArrow)projectile).pierceLeft
               + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44961_, pCrossbowStack);
         } else if (pAmmoStack.m_41720_() instanceof SpellArrow && projectile instanceof EntitySpellArrow spellArrow) {
            spellArrow.pierceLeft = spellArrow.pierceLeft + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44961_, pCrossbowStack);
            spellArrow.setColors(resolver.spell.color);
         }

         if (pShooter instanceof CrossbowAttackMob crossbowattackmob) {
            crossbowattackmob.m_5811_(crossbowattackmob.m_5448_(), pCrossbowStack, projectile, pProjectileAngle);
         } else {
            Vec3 vec31 = pShooter.m_20289_(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(vec31), pProjectileAngle, true);
            Vec3 vec3 = pShooter.m_20252_(1.0F);
            Vector3f vector3f = new Vector3f(vec3);
            vector3f.m_122251_(quaternion);
            projectile.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), pVelocity, pInaccuracy);
         }

         pCrossbowStack.m_41622_(flag ? 3 : 1, pShooter, p_40858_ -> p_40858_.m_21190_(pHand));
         worldIn.m_7967_(projectile);
         worldIn.m_6263_(null, pShooter.m_20185_(), pShooter.m_20186_(), pShooter.m_20189_(), SoundEvents.f_11847_, SoundSource.PLAYERS, 1.0F, pSoundPitch);
      }
   }

   private AbstractArrow getArrow(Level pLevel, LivingEntity pLivingEntity, ItemStack pCrossbowStack, ItemStack pAmmoStack) {
      ArrowItem arrowitem = (ArrowItem)(pAmmoStack.m_41720_() instanceof ArrowItem ? pAmmoStack.m_41720_() : Items.f_42412_);
      AbstractArrow abstractarrow = arrowitem.m_6394_(pLevel, pAmmoStack, pLivingEntity);
      if (pLivingEntity instanceof Player) {
         abstractarrow.m_36762_(true);
      }

      abstractarrow.m_36740_(SoundEvents.f_11840_);
      abstractarrow.m_36793_(true);
      int i = pCrossbowStack.getEnchantmentLevel(Enchantments.f_44961_);
      if (i > 0) {
         abstractarrow.m_36767_((byte)i);
      }

      return abstractarrow;
   }

   public void shootStoredProjectiles(
      Level pLevel, LivingEntity pShooter, InteractionHand pUsedHand, ItemStack pCrossbowStack, float pVelocity, float pInaccuracy
   ) {
      if (pShooter instanceof Player player && ForgeEventFactory.onArrowLoose(pCrossbowStack, pShooter.f_19853_, player, 1, true) < 0) {
         return;
      }

      List<ItemStack> list = this.getChargedProjectiles(pCrossbowStack);
      float[] afloat = m_220023_(pShooter.m_217043_());
      boolean isSpell = pCrossbowStack.m_41782_() && pCrossbowStack.m_41783_().m_128471_("isSpell");

      for (int i = 0; i < list.size(); i++) {
         ItemStack itemstack = list.get(i);
         boolean flag = pShooter instanceof Player && ((Player)pShooter).m_150110_().f_35937_;
         if (!itemstack.m_41619_()) {
            float offset = 10.0F * (float)((i > 0 ? 1 + i : 0) / 2);
            boolean isOdd = i % 2 == 1;
            if (isOdd) {
               offset *= -1.0F;
            }

            this.shootOne(
               pLevel,
               pShooter,
               pUsedHand,
               pCrossbowStack,
               itemstack,
               i == 0 ? 1.0F : m_220025_(isOdd == pShooter.m_217043_().m_188499_(), pShooter.m_217043_()),
               flag,
               pVelocity,
               pInaccuracy,
               offset,
               isSpell
            );
         }
      }

      this.onCrossbowShot(pLevel, pShooter, pCrossbowStack);
   }

   private void onCrossbowShot(Level pLevel, LivingEntity pShooter, ItemStack pCrossbowStack) {
      if (pShooter instanceof ServerPlayer serverplayer) {
         if (!pLevel.f_46443_) {
            CriteriaTriggers.f_10555_.m_65462_(serverplayer, pCrossbowStack);
         }

         serverplayer.m_36246_(Stats.f_12982_.m_12902_(pCrossbowStack.m_41720_()));
      }

      this.clearChargedProjectiles(pCrossbowStack);
   }

   public Predicate<ItemStack> m_6437_() {
      return super.m_6437_().or(i -> i.m_41720_() instanceof SpellArrow);
   }

   public EntitySpellArrow buildSpellArrow(Level worldIn, LivingEntity playerentity, ISpellCaster caster) {
      EntitySpellArrow spellArrow = new EntitySpellArrow(worldIn, playerentity);
      spellArrow.spellResolver = new SpellResolver(new SpellContext(worldIn, caster.getSpell(), playerentity, LivingCaster.from(playerentity)))
         .withSilent(true);
      spellArrow.setColors(caster.getColor());
      return spellArrow;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      this.getInformation(stack, worldIn, tooltip2, flagIn);
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
   }

   @Override
   public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
   }

   @Override
   public void sendInvalidMessage(Player player) {
      PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.bow.invalid"));
   }

   @Override
   public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
      recipe.add(MethodProjectile.INSTANCE);
      recipe.addAll(spell.recipe);
      spell.recipe = recipe;
      return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
   }

   @NotNull
   @Override
   public ISpellCaster getSpellCaster(ItemStack stack) {
      return new BasicReductionCaster(stack, spell -> {
         spell.addDiscount(MethodProjectile.INSTANCE.getCastingCost());
         return spell;
      });
   }

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return true;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new SpellCrossbowRenderer();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }
}
