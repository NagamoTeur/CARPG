package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.event.EffectResolveEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.perk.IEffectResolvePerk;
import com.hollingsworth.arsnouveau.api.perk.PerkInstance;
import com.hollingsworth.arsnouveau.api.util.CuriosUtil;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.NotEnoughManaPacket;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;

public class SpellResolver {
   public AbstractCastMethod castType;
   public Spell spell;
   public SpellContext spellContext;
   public boolean silent;
   private final ISpellValidator spellValidator;
   @Nullable
   public HitResult hitResult = null;

   public SpellResolver(SpellContext spellContext) {
      this.spell = spellContext.getSpell();
      this.castType = spellContext.getSpell().getCastMethod();
      this.spellContext = spellContext;
      this.spellValidator = ArsNouveauAPI.getInstance().getSpellCastingSpellValidator();
   }

   public SpellResolver withSilent(boolean isSilent) {
      this.silent = isSilent;
      return this;
   }

   public boolean canCast(LivingEntity entity) {
      List<SpellValidationError> validationErrors = this.spellValidator.validate(this.spell.recipe);
      if (validationErrors.isEmpty()) {
         return this.enoughMana(entity);
      } else {
         if (!this.silent && !entity.m_20193_().f_46443_) {
            PortUtil.sendMessageNoSpam(entity, validationErrors.get(0).makeTextComponentExisting());
         }

         return false;
      }
   }

   boolean enoughMana(LivingEntity entity) {
      int totalCost = this.getResolveCost();
      IManaCap manaCap = (IManaCap)CapabilityRegistry.getMana(entity).orElse(null);
      if (manaCap == null) {
         return false;
      } else {
         boolean var10000;
         label37: {
            label28:
            if (!((double)totalCost <= manaCap.getCurrentMana())) {
               if (entity instanceof Player player && player.m_7500_()) {
                  break label28;
               }

               var10000 = false;
               break label37;
            }

            var10000 = true;
         }

         boolean canCast = var10000;
         if (!canCast && !entity.m_20193_().f_46443_ && !this.silent) {
            PortUtil.sendMessageNoSpam(entity, Component.m_237115_("ars_nouveau.spell.no_mana"));
            if (entity instanceof ServerPlayer serverPlayer) {
               Networking.sendToPlayerClient(new NotEnoughManaPacket(totalCost), serverPlayer);
            }
         }

         return canCast;
      }
   }

   public boolean postEvent() {
      return SpellUtil.postEvent(new SpellCastEvent(this.spell, this.spellContext));
   }

   private SpellStats getCastStats() {
      LivingEntity caster = this.spellContext.getUnwrappedCaster();
      return new SpellStats.Builder()
         .setAugments(this.spell.getAugments(0, caster))
         .addItemsFromEntity(caster)
         .build(this.castType, this.hitResult, caster.f_19853_, caster, this.spellContext);
   }

   public boolean onCast(ItemStack stack, Level level) {
      if (this.canCast(this.spellContext.getUnwrappedCaster()) && !this.postEvent()) {
         this.hitResult = null;
         CastResolveType resolveType = this.castType.onCast(stack, this.spellContext.getUnwrappedCaster(), level, this.getCastStats(), this.spellContext, this);
         if (resolveType == CastResolveType.SUCCESS) {
            this.expendMana();
         }

         return resolveType.wasSuccess;
      } else {
         return false;
      }
   }

   public boolean onCastOnBlock(BlockHitResult blockRayTraceResult) {
      if (this.canCast(this.spellContext.getUnwrappedCaster()) && !this.postEvent()) {
         this.hitResult = blockRayTraceResult;
         CastResolveType resolveType = this.castType
            .onCastOnBlock(blockRayTraceResult, this.spellContext.getUnwrappedCaster(), this.getCastStats(), this.spellContext, this);
         if (resolveType == CastResolveType.SUCCESS) {
            this.expendMana();
         }

         return resolveType.wasSuccess;
      } else {
         return false;
      }
   }

   public boolean onCastOnBlock(UseOnContext context) {
      if (this.canCast(this.spellContext.getUnwrappedCaster()) && !this.postEvent()) {
         this.hitResult = context.f_43705_;
         CastResolveType resolveType = this.castType.onCastOnBlock(context, this.getCastStats(), this.spellContext, this);
         if (resolveType == CastResolveType.SUCCESS) {
            this.expendMana();
         }

         return resolveType.wasSuccess;
      } else {
         return false;
      }
   }

   public boolean onCastOnEntity(ItemStack stack, Entity target, InteractionHand hand) {
      if (this.canCast(this.spellContext.getUnwrappedCaster()) && !this.postEvent()) {
         this.hitResult = new EntityHitResult(target);
         CastResolveType resolveType = this.castType
            .onCastOnEntity(stack, this.spellContext.getUnwrappedCaster(), target, hand, this.getCastStats(), this.spellContext, this);
         if (resolveType == CastResolveType.SUCCESS) {
            this.expendMana();
         }

         return resolveType.wasSuccess;
      } else {
         return false;
      }
   }

   public void onResolveEffect(Level world, HitResult result) {
      this.hitResult = result;
      this.resolveAllEffects(world);
   }

   protected void resolveAllEffects(Level world) {
      this.spellContext.resetCastCounter();
      LivingEntity shooter = this.spellContext.getUnwrappedCaster();
      SpellResolveEvent.Pre spellResolveEvent = new SpellResolveEvent.Pre(world, shooter, this.hitResult, this.spell, this.spellContext, this);
      MinecraftForge.EVENT_BUS.post(spellResolveEvent);
      if (!spellResolveEvent.isCanceled()) {
         List<PerkInstance> perkInstances = (List<PerkInstance>)(shooter instanceof Player player ? PerkUtil.getPerksFromPlayer(player) : new ArrayList<>());

         while (this.spellContext.hasNextPart()) {
            AbstractSpellPart part = this.spellContext.nextPart();
            if (part == null) {
               break;
            }

            if (!(part instanceof AbstractAugment)) {
               SpellStats.Builder builder = new SpellStats.Builder();
               List<AbstractAugment> augments = this.spell.getAugments(this.spellContext.getCurrentIndex() - 1, shooter);
               SpellStats stats = builder.setAugments(augments).addItemsFromEntity(shooter).build(part, this.hitResult, world, shooter, this.spellContext);
               if (part instanceof AbstractEffect effect) {
                  EffectResolveEvent.Pre preEvent = new EffectResolveEvent.Pre(
                     world, shooter, this.hitResult, this.spell, this.spellContext, effect, stats, this
                  );
                  if (!MinecraftForge.EVENT_BUS.post(preEvent)) {
                     for (PerkInstance perkInstance : perkInstances) {
                        if (perkInstance.getPerk() instanceof IEffectResolvePerk effectPerk) {
                           effectPerk.onPreResolve(this.hitResult, world, shooter, stats, this.spellContext, this, effect, perkInstance);
                        }
                     }

                     effect.onResolve(this.hitResult, world, shooter, stats, this.spellContext, this);

                     for (PerkInstance perkInstancex : perkInstances) {
                        if (perkInstancex.getPerk() instanceof IEffectResolvePerk effectPerk) {
                           effectPerk.onPostResolve(this.hitResult, world, shooter, stats, this.spellContext, this, effect, perkInstancex);
                        }
                     }

                     MinecraftForge.EVENT_BUS
                        .post(new EffectResolveEvent.Post(world, shooter, this.hitResult, this.spell, this.spellContext, effect, stats, this));
                  }
               }
            }
         }

         MinecraftForge.EVENT_BUS.post(new SpellResolveEvent.Post(world, shooter, this.hitResult, this.spell, this.spellContext, this));
      }
   }

   public void expendMana() {
      int totalCost = this.getResolveCostAndResetDiscounts();
      CapabilityRegistry.getMana(this.spellContext.getUnwrappedCaster()).ifPresent(mana -> mana.removeMana((double)totalCost));
   }

   public int getResolveCost() {
      int cost = this.spellContext.getSpell().getDiscountedCost() - ManaUtil.getPlayerDiscounts(this.spellContext.getUnwrappedCaster(), this.spell);
      return Math.max(cost, 0);
   }

   public int getResolveCostAndResetDiscounts() {
      int cost = this.spellContext.getSpell().getFinalCostAndReset() - ManaUtil.getPlayerDiscounts(this.spellContext.getUnwrappedCaster(), this.spell);
      return Math.max(cost, 0);
   }

   public SpellResolver getNewResolver(SpellContext context) {
      return new SpellResolver(context);
   }

   public boolean hasFocus(ItemStack stack) {
      return CuriosUtil.hasItem(this.spellContext.getUnwrappedCaster(), stack);
   }
}
