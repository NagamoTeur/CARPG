package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.BasicReductionCaster;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.client.renderer.item.SwordRenderer;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EnchantersSword extends SwordItem implements ICasterTool, IAnimatable {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public EnchantersSword(Tier iItemTier, int baseDamage, float baseAttackSpeed) {
      this(iItemTier, baseDamage, baseAttackSpeed, ItemsRegistry.defaultItemProperties().m_41487_(1));
   }

   public EnchantersSword(Tier iItemTier, int baseDamage, float baseAttackSpeed, Properties properties) {
      super(iItemTier, baseDamage, baseAttackSpeed, properties);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
      super.m_6883_(stack, world, entity, p_77663_4_, p_77663_5_);
      if (entity instanceof Player player) {
         RepairingPerk.attemptRepair(stack, player);
      }
   }

   @Override
   public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
   }

   @Override
   public void sendInvalidMessage(Player player) {
      PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.sword.invalid"));
   }

   @Override
   public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
      recipe.add(MethodTouch.INSTANCE);
      recipe.addAll(spell.recipe);
      recipe.add(AugmentAmplify.INSTANCE);
      spell.recipe = recipe;
      return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity entity) {
      ISpellCaster caster = this.getSpellCaster(stack);
      IWrappedCaster wrappedCaster = (IWrappedCaster)(entity instanceof Player player ? new PlayerCaster(player) : new LivingCaster(entity));
      SpellContext context = new SpellContext(
         entity.f_19853_, caster.modifySpellBeforeCasting(target.f_19853_, entity, InteractionHand.MAIN_HAND, caster.getSpell()), entity, wrappedCaster, stack
      );
      SpellResolver resolver = (SpellResolver)(entity instanceof Player ? new SpellResolver(context) : new EntitySpellResolver(context));
      EntityHitResult entityRes = new EntityHitResult(target);
      resolver.onCastOnEntity(stack, entityRes.m_82443_(), InteractionHand.MAIN_HAND);
      return super.m_7579_(stack, target, entity);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      this.getInformation(stack, worldIn, tooltip2, flagIn);
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
   }

   @Override
   public void registerControllers(AnimationData animationData) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new SwordRenderer();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   @NotNull
   @Override
   public ISpellCaster getSpellCaster(ItemStack stack) {
      return new BasicReductionCaster(stack, spell -> {
         spell.addDiscount(AugmentAmplify.INSTANCE.getCastingCost());
         return spell;
      });
   }
}
