package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.BasicReductionCaster;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.client.renderer.item.SpellBowRenderer;
import com.hollingsworth.arsnouveau.common.entity.EntitySpellArrow;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class SpellBow extends BowItem implements IAnimatable, ICasterTool {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public SpellBow(Properties properties) {
      super(properties);
   }

   public SpellBow() {
      this(ItemsRegistry.defaultItemProperties().m_41487_(1));
   }

   public boolean canPlayerCastSpell(ItemStack bow, Player playerentity) {
      ISpellCaster caster = this.getSpellCaster(bow);
      return new SpellResolver(new SpellContext(playerentity.f_19853_, caster.getSpell(), playerentity, new PlayerCaster(playerentity), bow))
         .withSilent(true)
         .canCast(playerentity);
   }

   public ItemStack findAmmo(Player playerEntity, ItemStack shootable) {
      if (shootable.m_41720_() instanceof ProjectileWeaponItem projectileWeaponItem) {
         Predicate var8 = projectileWeaponItem.m_6442_()
            .and(ix -> !(ix.m_41720_() instanceof SpellArrow) || ix.m_41720_() instanceof SpellArrow && this.canPlayerCastSpell(shootable, playerEntity));
         ItemStack itemstack = ProjectileWeaponItem.m_43010_(playerEntity, var8);
         if (!itemstack.m_41619_()) {
            return ForgeHooks.getProjectile(playerEntity, shootable, itemstack);
         } else {
            var8 = projectileWeaponItem.m_6437_()
               .and(ix -> !(ix.m_41720_() instanceof SpellArrow) || ix.m_41720_() instanceof SpellArrow && this.canPlayerCastSpell(shootable, playerEntity));

            for (int i = 0; i < playerEntity.m_150109_().m_6643_(); i++) {
               ItemStack itemstack1 = playerEntity.f_36093_.m_8020_(i);
               if (var8.test(itemstack1)) {
                  return ForgeHooks.getProjectile(playerEntity, shootable, itemstack1);
               }
            }

            return ForgeHooks.getProjectile(playerEntity, shootable, playerEntity.f_36077_.f_35937_ ? new ItemStack(Items.f_42412_) : ItemStack.f_41583_);
         }
      } else {
         return ItemStack.f_41583_;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      ISpellCaster caster = this.getSpellCaster(playerIn.m_21120_(handIn));
      boolean hasAmmo = !this.findAmmo(playerIn, itemstack).m_41619_();
      InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, hasAmmo);
      if (ret != null) {
         return ret;
      } else if (!hasAmmo
         && (
            !caster.getSpell().isValid()
               || !new SpellResolver(new SpellContext(worldIn, caster.getSpell(), playerIn, new PlayerCaster(playerIn), itemstack))
                  .withSilent(true)
                  .canCast(playerIn)
         )) {
         if (!playerIn.f_36077_.f_35937_ && !hasAmmo) {
            return InteractionResultHolder.m_19100_(itemstack);
         } else {
            playerIn.m_6672_(handIn);
            return InteractionResultHolder.m_19096_(itemstack);
         }
      } else {
         playerIn.m_6672_(handIn);
         return InteractionResultHolder.m_19096_(itemstack);
      }
   }

   public EntitySpellArrow buildSpellArrow(Level worldIn, Player playerentity, ISpellCaster caster, boolean isSpellArrow, ItemStack bowStack) {
      EntitySpellArrow spellArrow = new EntitySpellArrow(worldIn, playerentity);
      spellArrow.spellResolver = new SpellResolver(new SpellContext(worldIn, caster.getSpell(), playerentity, new PlayerCaster(playerentity), bowStack))
         .withSilent(true);
      spellArrow.setColors(caster.getColor());
      if (isSpellArrow) {
         spellArrow.m_36781_(0.0);
      }

      return spellArrow;
   }

   public void m_5551_(ItemStack bowStack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player playerentity) {
         boolean isInfinity = playerentity.f_36077_.f_35937_ || bowStack.getEnchantmentLevel(Enchantments.f_44952_) > 0;
         ItemStack arrowStack = this.findAmmo(playerentity, bowStack);
         int useTime = this.m_8105_(bowStack) - timeLeft;
         useTime = ForgeEventFactory.onArrowLoose(bowStack, worldIn, playerentity, useTime, !arrowStack.m_41619_() || isInfinity);
         if (useTime >= 0) {
            boolean canFire = false;
            if (!arrowStack.m_41619_() || isInfinity) {
               if (arrowStack.m_41619_()) {
                  arrowStack = new ItemStack(Items.f_42412_);
               }

               canFire = true;
            }

            ISpellCaster caster = this.getSpellCaster(bowStack);
            boolean isSpellArrow = false;
            if (arrowStack.m_41619_()
               && caster.getSpell().isValid()
               && new SpellResolver(new SpellContext(worldIn, caster.getSpell(), playerentity, new PlayerCaster(playerentity), bowStack)).canCast(playerentity)
               )
             {
               canFire = true;
               isSpellArrow = true;
            }

            if (canFire) {
               float f = m_40661_(useTime);
               boolean didCastSpell = false;
               if ((double)f >= 0.1) {
                  boolean var10000;
                  label150: {
                     label111:
                     if (!playerentity.f_36077_.f_35937_) {
                        if (arrowStack.m_41720_() instanceof ArrowItem arrowItem && arrowItem.isInfinite(arrowStack, bowStack, playerentity)) {
                           break label111;
                        }

                        var10000 = false;
                        break label150;
                     }

                     var10000 = true;
                  }

                  boolean isArrowInfinite = var10000;
                  if (!worldIn.f_46443_) {
                     ArrowItem arrowitem = (ArrowItem)(arrowStack.m_41720_() instanceof ArrowItem ? arrowStack.m_41720_() : Items.f_42412_);
                     AbstractArrow abstractarrowentity = arrowitem.m_6394_(worldIn, arrowStack, playerentity);
                     abstractarrowentity = this.customArrow(abstractarrowentity);
                     List<AbstractArrow> arrows = new ArrayList<>();
                     SpellResolver resolver = new SpellResolver(
                        new SpellContext(
                           worldIn,
                           caster.modifySpellBeforeCasting(worldIn, entityLiving, InteractionHand.MAIN_HAND, caster.getSpell()),
                           playerentity,
                           new PlayerCaster(playerentity),
                           bowStack
                        )
                     );
                     if (arrowitem == Items.f_42412_ && resolver.withSilent(true).canCast(playerentity)) {
                        abstractarrowentity = this.buildSpellArrow(worldIn, playerentity, caster, isSpellArrow, bowStack);
                        resolver.expendMana();
                        didCastSpell = true;
                     } else if (arrowitem instanceof SpellArrow) {
                        if (!resolver.canCast(playerentity)) {
                           return;
                        }

                        if (resolver.canCast(playerentity)) {
                           resolver.expendMana();
                           didCastSpell = true;
                        }
                     }

                     arrows.add(abstractarrowentity);
                     if (caster.getSpell().isValid() && didCastSpell) {
                        int numSplits = caster.getSpell().getBuffsAtIndex(0, playerentity, AugmentSplit.INSTANCE);
                        if (abstractarrowentity instanceof EntitySpellArrow arrow) {
                           numSplits = arrow.spellResolver.spell.getBuffsAtIndex(0, playerentity, AugmentSplit.INSTANCE);
                        }

                        for (int i = 0; i < numSplits; i++) {
                           EntitySpellArrow spellArrow = this.buildSpellArrow(worldIn, playerentity, caster, isSpellArrow, bowStack);
                           arrows.add(spellArrow);
                        }
                     }

                     int opposite = -1;
                     int counter = 0;

                     for (AbstractArrow arr : arrows) {
                        arr.m_37251_(
                           playerentity,
                           playerentity.m_146909_(),
                           playerentity.m_146908_() + (float)(Math.round((double)counter / 2.0) * 10L * (long)opposite),
                           0.0F,
                           f * 3.0F,
                           1.0F
                        );
                        opposite *= -1;
                        counter++;
                        if (f >= 1.0F) {
                           arr.m_36762_(true);
                        }

                        this.addArrow(arr, bowStack, arrowStack, isArrowInfinite, playerentity);
                     }
                  }

                  worldIn.m_6263_(
                     null,
                     playerentity.m_20185_(),
                     playerentity.m_20186_(),
                     playerentity.m_20189_(),
                     SoundEvents.f_11687_,
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F / (worldIn.f_46441_.m_188501_() * 0.4F + 1.2F) + f * 0.5F
                  );
                  if (didCastSpell) {
                     caster.playSound(playerentity.m_20097_(), playerentity.f_19853_, playerentity, caster.getCurrentSound(), SoundSource.PLAYERS);
                  }

                  if (!isArrowInfinite && !playerentity.f_36077_.f_35937_) {
                     arrowStack.m_41774_(1);
                  }
               }
            }
         }
      }
   }

   public void addArrow(AbstractArrow abstractarrowentity, ItemStack bowStack, ItemStack arrowStack, boolean isArrowInfinite, Player playerentity) {
      int power = bowStack.getEnchantmentLevel(Enchantments.f_44988_);
      if (power > 0) {
         abstractarrowentity.m_36781_(abstractarrowentity.m_36789_() + (double)power * 0.5 + 0.5);
      }

      int punch = bowStack.getEnchantmentLevel(Enchantments.f_44989_);
      if (punch > 0) {
         abstractarrowentity.m_36735_(punch);
      }

      if (bowStack.getEnchantmentLevel(Enchantments.f_44990_) > 0) {
         abstractarrowentity.m_20254_(100);
      }

      if (isArrowInfinite || playerentity.f_36077_.f_35937_ && (arrowStack.m_41720_() == Items.f_42737_ || arrowStack.m_41720_() == Items.f_42738_)) {
         abstractarrowentity.f_36705_ = Pickup.CREATIVE_ONLY;
      }

      playerentity.f_19853_.m_7967_(abstractarrowentity);
   }

   public Predicate<ItemStack> m_6437_() {
      return super.m_6437_().or(i -> i.m_41720_() instanceof SpellArrow);
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   public AbstractArrow customArrow(AbstractArrow arrow) {
      return super.customArrow(arrow);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
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

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return true;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new SpellBowRenderer();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   @NotNull
   @Override
   public ISpellCaster getSpellCaster(ItemStack stack) {
      return new BasicReductionCaster(stack, spell -> {
         spell.addDiscount(MethodProjectile.INSTANCE.getCastingCost());
         return spell;
      });
   }
}
