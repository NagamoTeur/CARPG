package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarBookwyrm extends FlyingFamiliarEntity implements ISpellCastListener {
   public FamiliarBookwyrm(EntityType<? extends PathfinderMob> ent, Level world) {
      super(ent, world);
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!this.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(hand);
         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null
               && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_())
               && Arrays.asList(EntityBookwyrm.COLORS).contains(color.m_41065_())) {
               this.setColor(color);
               return InteractionResult.SUCCESS;
            } else {
               return InteractionResult.SUCCESS;
            }
         } else {
            return super.m_6071_(player, hand);
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46467_() % 20L == 0L) {
         LivingEntity owner = this.getOwner();
         if (owner instanceof Player player) {
            final FilterableItemHandler filterableItemHandler = new FilterableItemHandler(new PlayerMainInvWrapper(player.f_36093_), new ArrayList<>());
            InventoryManager manager = new InventoryManager(new ArrayList<FilterableItemHandler>() {
               {
                  this.add(filterableItemHandler);
               }
            });

            for (Entity entity : this.f_19853_.m_45933_(owner, new AABB(owner.m_20097_()).m_82400_(5.0))) {
               if (entity instanceof ItemEntity i) {
                  ItemStack stack = i.m_32055_();
                  if (stack.m_41619_()
                     || MinecraftForge.EVENT_BUS.post(new EntityItemPickupEvent(player, i))
                     || this.getOwnerID().equals(i.m_32057_())
                     || i.m_32063_()
                     || i.getPersistentData().m_128471_("PreventRemoteMovement")
                     || !i.m_6084_()) {
                     continue;
                  }

                  stack = manager.insertStack(stack);
                  i.m_32045_(stack);
               }

               if (entity instanceof ExperienceOrb orb && !orb.m_213877_() && !MinecraftForge.EVENT_BUS.post(new PickupXp(player, orb))) {
                  player.m_6756_(orb.f_20770_);
                  orb.m_142687_(RemovalReason.DISCARDED);
               }
            }
         }
      }
   }

   @Override
   public PlayState walkPredicate(AnimationEvent<?> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
      return PlayState.CONTINUE;
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FAMILIAR_BOOKWYRM.get();
   }

   public ResourceLocation getTexture(FamiliarEntity entity) {
      String color = this.getColor().toLowerCase();
      if (color.isEmpty()) {
         color = "blue";
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/book_wyrm_" + color + ".png");
   }
}
