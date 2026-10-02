package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateCaster {
   Spell spellRecipe;
   int cast_slot;
   String spellName;
   boolean mainHand;

   public PacketUpdateCaster() {
   }

   @Deprecated
   public PacketUpdateCaster(Spell spellRecipe, int cast_slot, String spellName) {
      this(spellRecipe, cast_slot, spellName, true);
   }

   public PacketUpdateCaster(Spell spellRecipe, int cast_slot, String spellName, boolean mainHand) {
      this.spellRecipe = spellRecipe;
      this.cast_slot = cast_slot;
      this.spellName = spellName;
      this.mainHand = mainHand;
   }

   public PacketUpdateCaster(FriendlyByteBuf buf) {
      this.spellRecipe = Spell.fromTag(buf.m_130260_());
      this.cast_slot = buf.readInt();
      this.spellName = buf.m_130136_(32767);
      this.mainHand = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130079_(this.spellRecipe.serialize());
      buf.writeInt(this.cast_slot);
      buf.m_130070_(this.spellName);
      buf.writeBoolean(this.mainHand);
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ctx.get().getSender() != null) {
            InteractionHand hand = this.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack stack = ctx.get().getSender().m_21120_(hand);
            if (!(stack.m_41720_() instanceof ICasterTool)) {
               return;
            }

            if (this.spellRecipe != null) {
               ISpellCaster caster = CasterUtil.getCaster(stack);
               caster.setCurrentSlot(this.cast_slot);
               Spell spell = caster.getSpell(this.cast_slot).setRecipe(this.spellRecipe.recipe);
               caster.setSpell(spell, this.cast_slot);
               caster.setSpellName(this.spellName, this.cast_slot);
               Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new PacketUpdateBookGUI(stack));
            }
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
