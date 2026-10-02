package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.client.renderer.item.WandRenderer;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class Wand extends ModItem implements IAnimatable, ICasterTool {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public Wand(Properties properties) {
      super(properties);
   }

   public Wand() {
      super(new Properties().m_41487_(1).m_41491_(ArsNouveau.itemGroup));
   }

   private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("wand_gem_spin"));
      return PlayState.CONTINUE;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.m_21120_(handIn);
      ISpellCaster caster = this.getSpellCaster(stack);
      return caster.castSpell(worldIn, playerIn, handIn, Component.m_237115_("ars_nouveau.wand.invalid"));
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "controller", 20.0F, this::predicate));
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
   }

   @Override
   public void sendInvalidMessage(Player player) {
      PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.wand.invalid"));
   }

   @Override
   public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
      recipe.add(MethodProjectile.INSTANCE);
      recipe.add(AugmentAccelerate.INSTANCE);
      recipe.addAll(spell.recipe);
      spell.recipe = recipe;
      return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      this.getInformation(stack, worldIn, tooltip2, flagIn);
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new WandRenderer();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }
}
