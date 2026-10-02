package com.hollingsworth.arsnouveau.common.entity.debug;

import com.hollingsworth.arsnouveau.setup.Config;
import java.io.PrintWriter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

public class EntityDebugger implements IDebugger {
   public FixedStack<EntityEvent> events = new FixedStack<>((Integer)Config.MAX_LOG_EVENTS.get());
   public final Entity entity;

   public EntityDebugger(Entity entity) {
      this.entity = entity;
   }

   @Override
   public void addEntityEvent(DebugEvent event, boolean storeDuplicate) {
      if (storeDuplicate || this.events.isEmpty() || !this.events.peek().id.equals(event.id)) {
         this.events.push(new EntityEvent(this.entity, event.id, event.message));
      }
   }

   @Override
   public void writeFile(PrintWriter writer) {
      writer.print("Entity:  (" + this.entity.getClass().getSimpleName() + ")");
      if (this.entity instanceof Mob mob) {
         for (WrappedGoal goal : mob.f_21345_.m_25386_().toList()) {
            writer.println("Running Goal: " + goal.m_26015_().getClass().getSimpleName());
         }
      }

      for (EntityEvent event : this.events) {
         writer.println(event.toString());
      }
   }
}
