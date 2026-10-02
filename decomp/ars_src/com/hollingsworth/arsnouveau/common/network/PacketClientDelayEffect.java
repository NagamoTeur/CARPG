package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.event.DelayedSpellEvent;
import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketClientDelayEffect {
   public Spell spell;
   public int duration;
   public int shooterID;
   @Nullable
   public BlockHitResult hitPos;
   public int hitEntityID;

   public PacketClientDelayEffect(FriendlyByteBuf buf) {
      this.duration = buf.readInt();
      this.spell = Spell.fromTag(buf.m_130260_());
      this.shooterID = buf.readInt();
      this.hitEntityID = buf.readInt();
      if (this.hitEntityID == -1) {
         this.hitPos = buf.m_130283_();
      }
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.duration);
      buf.m_130079_(this.spell.serialize());
      buf.writeInt(this.shooterID);
      buf.writeInt(this.hitEntityID);
      if (this.hitEntityID == -1) {
         buf.m_130062_(this.hitPos);
      }
   }

   public PacketClientDelayEffect(
      int duration, @Nullable LivingEntity shooter, Spell spell, SpellContext context, @Nullable BlockHitResult hitPos, @Nullable Entity hitEntity
   ) {
      this.duration = duration;
      this.shooterID = shooter == null ? -1 : shooter.m_19879_();
      this.spell = spell;
      this.hitPos = hitPos;
      this.hitEntityID = hitEntity == null ? -1 : hitEntity.m_19879_();
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(
            () -> {
               Level world = ArsNouveau.proxy.getClientWorld();
               Entity hitEntity = world.m_6815_(this.hitEntityID);
               HitResult result;
               if (this.hitEntityID == -1) {
                  result = this.hitPos;
               } else if (hitEntity == null) {
                  result = BlockHitResult.m_82426_(new Vec3(0.0, 0.0, 0.0), Direction.UP, BlockPos.f_121853_);
               } else {
                  result = new EntityHitResult(hitEntity);
               }

               EventQueue.getClientQueue()
                  .addEvent(
                     new DelayedSpellEvent(
                        this.duration,
                        this.spell,
                        result,
                        world,
                        (LivingEntity)world.m_6815_(this.shooterID),
                        new SpellContext(world, this.spell, (LivingEntity)world.m_6815_(this.shooterID))
                     )
                  );
            }
         );
      ctx.get().setPacketHandled(true);
   }
}
