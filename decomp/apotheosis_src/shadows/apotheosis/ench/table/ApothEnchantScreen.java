package shadows.apotheosis.ench.table;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.enchantments.InertEnchantment;
import shadows.apotheosis.util.ApothMiscUtil;
import shadows.placebo.util.EnchantmentUtils;

public class ApothEnchantScreen extends AbstractContainerScreen<ApothEnchantmentMenu> {
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("apotheosis", "textures/gui/enchanting_table.png");
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private BookModel bookModel;
   private final Random random = new Random();
   public int ticks;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   private ItemStack last = ItemStack.f_41583_;
   protected float eterna = 0.0F;
   protected float lastEterna = 0.0F;
   protected float quanta = 0.0F;
   protected float lastQuanta = 0.0F;
   protected float arcana = 0.0F;
   protected float lastArcana = 0.0F;
   protected final Int2ObjectMap<List<EnchantmentInstance>> clues = new Int2ObjectOpenHashMap();
   protected boolean[] hasAllClues = new boolean[]{false, false, false};

   public ApothEnchantScreen(ApothEnchantmentMenu container, Inventory inv, Component title) {
      super(container, inv, title);
      this.f_97727_ = 197;
      this.clues.defaultReturnValue(new ArrayList());
   }

   protected void m_7856_() {
      super.m_7856_();
      this.bookModel = new BookModel(this.f_96541_.m_167973_().m_171103_(ModelLayers.f_171271_));
   }

   protected void m_7027_(PoseStack stack, int mouseX, int mouseY) {
      this.f_96547_.m_92889_(stack, this.f_96539_, 12.0F, 5.0F, 4210752);
      this.f_96547_.m_92889_(stack, this.f_169604_, 7.0F, (float)(this.f_97727_ - 96) + 4.0F, 4210752);
      this.f_96547_.m_92883_(stack, I18n.m_118938_("gui.apotheosis.enchant.eterna", new Object[0]), 19.0F, 74.0F, 4044093);
      this.f_96547_.m_92883_(stack, I18n.m_118938_("gui.apotheosis.enchant.quanta", new Object[0]), 19.0F, 84.0F, 16536660);
      this.f_96547_.m_92883_(stack, I18n.m_118938_("gui.apotheosis.enchant.arcana", new Object[0]), 19.0F, 94.0F, 11010216);
   }

   public void m_181908_() {
      this.tickBook();
      float current = ((ApothEnchantmentMenu)this.f_97732_).eterna.get();
      if (current != this.eterna) {
         if (current > this.eterna) {
            this.eterna = this.eterna + Math.min(current - this.eterna, Math.max(0.16F, (current - this.eterna) * 0.1F));
         } else {
            this.eterna = Math.max(this.eterna - this.lastEterna * 0.075F, current);
         }
      }

      if (current > 0.0F) {
         this.lastEterna = current;
      }

      current = ((ApothEnchantmentMenu)this.f_97732_).quanta.get();
      if (current != this.quanta) {
         if (current > this.quanta) {
            this.quanta = this.quanta + Math.min(current - this.quanta, Math.max(0.04F, (current - this.quanta) * 0.1F));
         } else {
            this.quanta = Math.max(this.quanta - this.lastQuanta * 0.075F, current);
         }
      }

      if (current > 0.0F) {
         this.lastQuanta = current;
      }

      current = ((ApothEnchantmentMenu)this.f_97732_).arcana.get();
      if (current != this.arcana) {
         if (current > this.arcana) {
            this.arcana = this.arcana + Math.min(current - this.arcana, Math.max(0.04F, (current - this.arcana) * 0.1F));
         } else {
            this.arcana = Math.max(this.arcana - this.lastArcana * 0.075F, current);
         }
      }

      if (current > 0.0F) {
         this.lastArcana = current;
      }
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;

      for (int k = 0; k < 3; k++) {
         double d0 = pMouseX - (double)(i + 60);
         double d1 = pMouseY - (double)(j + 14 + 19 * k);
         if (d0 >= 0.0 && d1 >= 0.0 && d0 < 108.0 && d1 < 19.0 && ((ApothEnchantmentMenu)this.f_97732_).m_6366_(this.f_96541_.f_91074_, k)) {
            this.f_96541_.f_91072_.m_105208_(((ApothEnchantmentMenu)this.f_97732_).f_38840_, k);
            return true;
         }
      }

      if (((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_6657_()
         && this.m_6774_(145, -15, 27, 15, pMouseX, pMouseY)
         && Arrays.stream(((ApothEnchantmentMenu)this.f_97732_).f_39447_).boxed().map(Enchantment::m_44697_).allMatch(Predicates.notNull())) {
         Minecraft.m_91087_().pushGuiLayer(new EnchantingInfoScreen(this));
      }

      return super.m_6375_(pMouseX, pMouseY, pButton);
   }

   protected void m_7286_(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
      Lighting.m_84930_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, ENCHANTMENT_TABLE_GUI_TEXTURE);
      int xCenter = (this.f_96543_ - this.f_97726_) / 2;
      int yCenter = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(stack, xCenter, yCenter, 0, 0, this.f_97726_, this.f_97727_);
      int guiScale = (int)this.f_96541_.m_91268_().m_85449_();
      RenderSystem.m_69949_((this.f_96543_ - 320) / 2 * guiScale, (this.f_96544_ - 240) / 2 * guiScale, 320 * guiScale, 240 * guiScale);
      Matrix4f matrix4f = Matrix4f.m_27653_(-0.34F, 0.23F, 0.0F);
      matrix4f.m_27644_(Matrix4f.m_27625_(90.0, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.m_157183_();
      RenderSystem.m_157425_(matrix4f);
      stack.m_85836_();
      Pose posestack$pose = stack.m_85850_();
      posestack$pose.m_85861_().m_27624_();
      posestack$pose.m_85864_().m_8180_();
      stack.m_85837_(0.0, 5.3F, 1984.0);
      stack.m_85841_(5.0F, 5.0F, 5.0F);
      stack.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
      stack.m_85845_(Vector3f.f_122223_.m_122240_(20.0F));
      float f1 = Mth.m_14179_(partialTicks, this.oOpen, this.open);
      stack.m_85837_((double)((1.0F - f1) * 0.2F), (double)((1.0F - f1) * 0.1F), (double)((1.0F - f1) * 0.25F));
      float f2 = -(1.0F - f1) * 90.0F - 90.0F;
      stack.m_85845_(Vector3f.f_122225_.m_122240_(f2));
      stack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
      float f3 = Mth.m_14179_(partialTicks, this.oFlip, this.flip) + 0.25F;
      float f4 = Mth.m_14179_(partialTicks, this.oFlip, this.flip) + 0.75F;
      f3 = (f3 - (float)Mth.m_14080_((double)f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)Mth.m_14080_((double)f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      this.bookModel.m_102292_(0.0F, f3, f4, f1);
      BufferSource buf = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
      VertexConsumer vertexconsumer = buf.m_6299_(this.bookModel.m_103119_(ENCHANTMENT_TABLE_BOOK_TEXTURE));
      this.bookModel.m_7695_(stack, vertexconsumer, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      buf.m_109911_();
      stack.m_85849_();
      RenderSystem.m_69949_(0, 0, this.f_96541_.m_91268_().m_85441_(), this.f_96541_.m_91268_().m_85442_());
      RenderSystem.m_157424_();
      Lighting.m_84931_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNames.m_98734_().m_98735_((long)((ApothEnchantmentMenu)this.f_97732_).m_39493_());
      int lapis = ((ApothEnchantmentMenu)this.f_97732_).m_39492_();

      for (int slot = 0; slot < 3; slot++) {
         int j1 = xCenter + 60;
         int k1 = j1 + 20;
         this.m_93250_(0);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, ENCHANTMENT_TABLE_GUI_TEXTURE);
         int level = ((ApothEnchantmentMenu)this.f_97732_).f_39446_[slot];
         if (level == 0) {
            this.m_93228_(stack, j1, yCenter + 14 + 19 * slot, 148, 218, 108, 19);
         } else {
            String s = level + "";
            int width = 86 - this.f_96547_.m_92895_(s);
            FormattedText itextproperties = EnchantmentNames.m_98734_().m_98737_(this.f_96547_, width);
            int color = 6839882;
            if ((lapis >= slot + 1 && this.f_96541_.f_91074_.f_36078_ >= level || this.f_96541_.f_91074_.m_150110_().f_35937_)
               && ((ApothEnchantmentMenu)this.f_97732_).f_39447_[slot] != -1) {
               int k2 = mouseX - (xCenter + 60);
               int l2 = mouseY - (yCenter + 14 + 19 * slot);
               if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                  this.m_93228_(stack, j1, yCenter + 14 + 19 * slot, 148, 237, 108, 19);
                  color = 16777088;
               } else {
                  this.m_93228_(stack, j1, yCenter + 14 + 19 * slot, 148, 199, 108, 19);
               }

               this.m_93228_(stack, j1 + 1, yCenter + 15 + 19 * slot, 16 * slot, 223, 16, 16);
               this.f_96547_.m_92857_(itextproperties, k1, yCenter + 16 + 19 * slot, width, color);
               color = 8453920;
            } else {
               this.m_93228_(stack, j1, yCenter + 14 + 19 * slot, 148, 218, 108, 19);
               this.m_93228_(stack, j1 + 1, yCenter + 15 + 19 * slot, 16 * slot, 239, 16, 16);
               this.f_96547_.m_92857_(itextproperties, k1, yCenter + 16 + 19 * slot, width, (color & 16711422) >> 1);
               color = 4226832;
            }

            this.f_96547_.m_92750_(stack, s, (float)(k1 + 86 - this.f_96547_.m_92895_(s)), (float)(yCenter + 16 + 19 * slot + 7), color);
         }
      }

      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, ENCHANTMENT_TABLE_GUI_TEXTURE);
      if (this.eterna > 0.0F) {
         this.m_93228_(stack, xCenter + 59, yCenter + 75, 0, 197, (int)(this.eterna / ((ApothEnchantmentMenu)this.f_97732_).eterna.getMax() * 110.0F), 5);
      }

      if (this.quanta > 0.0F) {
         this.m_93228_(stack, xCenter + 59, yCenter + 85, 0, 202, (int)(this.quanta / 100.0F * 110.0F), 5);
      }

      if (this.arcana > 0.0F) {
         this.m_93228_(stack, xCenter + 59, yCenter + 95, 0, 207, (int)(this.arcana / 100.0F * 110.0F), 5);
      }

      if (((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_6657_()
         && Arrays.stream(((ApothEnchantmentMenu)this.f_97732_).f_39447_).boxed().map(Enchantment::m_44697_).allMatch(Predicates.notNull())) {
         int u = this.m_6774_(145, -15, 27, 15, (double)mouseX, (double)mouseY) ? 15 : 0;
         this.m_93228_(stack, xCenter + 145, yCenter - 15, this.f_97726_, u, 27, 15);
      }
   }

   public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      partialTicks = this.f_96541_.m_91296_();
      this.m_7333_(stack);
      super.m_6305_(stack, mouseX, mouseY, partialTicks);
      this.m_7025_(stack, mouseX, mouseY);
      boolean creative = this.f_96541_.f_91074_.m_150110_().f_35937_;
      int lapis = ((ApothEnchantmentMenu)this.f_97732_).m_39492_();

      for (int slot = 0; slot < 3; slot++) {
         int level = ((ApothEnchantmentMenu)this.f_97732_).f_39446_[slot];
         Enchantment enchantment = Enchantment.m_44697_(((ApothEnchantmentMenu)this.f_97732_).f_39447_[slot]);
         int cost = slot + 1;
         if (this.m_6774_(60, 14 + 19 * slot, 108, 17, (double)mouseX, (double)mouseY) && level > 0) {
            List<Component> list = Lists.newArrayList();
            boolean isFailedInfusion = slot == 2
               && enchantment == null
               && EnchantingRecipe.findItemMatch(this.f_96541_.f_91073_, ((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_7993_()) != null;
            if (enchantment != null) {
               if (!((List)this.clues.get(slot)).isEmpty()) {
                  list.add(
                     Component.m_237115_("info.apotheosis.runes" + (this.hasAllClues[slot] ? "_all" : ""))
                        .m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.UNDERLINE})
                  );

                  for (EnchantmentInstance i : (List)this.clues.get(slot)) {
                     list.add(i.f_44947_.m_44700_(i.f_44948_));
                  }
               } else {
                  list.add(Component.m_237115_("info.apotheosis.no_clue").m_130944_(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.UNDERLINE}));
               }
            } else if (isFailedInfusion) {
               list.add(((InertEnchantment)Apoth.Enchantments.INFUSION.get()).m_44700_(1).m_6881_().m_130940_(ChatFormatting.ITALIC));
               Collections.addAll(list, Component.m_237113_(""), Component.m_237115_("info.apotheosis.infusion_failed").m_130940_(ChatFormatting.RED));
            } else {
               list.add(
                  Component.m_237110_("container.enchant.clue", new Object[]{""}).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC})
               );
               Collections.addAll(
                  list, Component.m_237113_(""), Component.m_237115_("forge.container.enchant.limitedEnchantability").m_130940_(ChatFormatting.RED)
               );
            }

            if (enchantment != null && !creative) {
               list.add(Component.m_237113_(""));
               if (this.f_96541_.f_91074_.f_36078_ < level) {
                  list.add(
                     Component.m_237110_("container.enchant.level.requirement", new Object[]{((ApothEnchantmentMenu)this.f_97732_).f_39446_[slot]})
                        .m_130940_(ChatFormatting.RED)
                  );
               } else {
                  String s;
                  if (cost == 1) {
                     s = I18n.m_118938_("container.enchant.lapis.one", new Object[0]);
                  } else {
                     s = I18n.m_118938_("container.enchant.lapis.many", new Object[]{cost});
                  }

                  ChatFormatting textformatting = lapis >= cost ? ChatFormatting.GRAY : ChatFormatting.RED;
                  list.add(Component.m_237113_(s).m_130940_(textformatting));
                  if (cost == 1) {
                     s = I18n.m_118938_("container.enchant.level.one", new Object[0]);
                  } else {
                     s = I18n.m_118938_("container.enchant.level.many", new Object[]{cost});
                  }

                  list.add(Component.m_237113_(s).m_130940_(ChatFormatting.GRAY));
               }
            }

            this.m_96597_(stack, list, mouseX, mouseY);
            break;
         }
      }

      if (this.m_6774_(60, 76, 110, 5, (double)mouseX, (double)mouseY)) {
         List<Component> listx = Lists.newArrayList();
         listx.add(Component.m_237113_(eterna() + I18n.m_118938_("gui.apotheosis.enchant.eterna.desc", new Object[0])));
         listx.add(Component.m_237115_("gui.apotheosis.enchant.eterna.desc2").m_130940_(ChatFormatting.GRAY));
         if (((ApothEnchantmentMenu)this.f_97732_).eterna.get() > 0.0F) {
            listx.add(Component.m_237113_(""));
            listx.add(
               Component.m_237113_(
                     I18n.m_118938_(
                        "gui.apotheosis.enchant.eterna.desc3",
                        new Object[]{f(((ApothEnchantmentMenu)this.f_97732_).eterna.get()), ((ApothEnchantmentMenu)this.f_97732_).eterna.getMax()}
                     )
                  )
                  .m_130940_(ChatFormatting.GRAY)
            );
         }

         this.m_96597_(stack, listx, mouseX, mouseY);
      } else if (this.m_6774_(60, 86, 110, 5, (double)mouseX, (double)mouseY)) {
         List<Component> listx = Lists.newArrayList();
         listx.add(Component.m_237113_(quanta() + I18n.m_118938_("gui.apotheosis.enchant.quanta.desc", new Object[0])));
         listx.add(Component.m_237115_("gui.apotheosis.enchant.quanta.desc2").m_130940_(ChatFormatting.GRAY));
         listx.add(Component.m_237113_(rectification() + I18n.m_118938_("gui.apotheosis.enchant.quanta.desc3", new Object[0])).m_130940_(ChatFormatting.GRAY));
         if (((ApothEnchantmentMenu)this.f_97732_).quanta.get() > 0.0F) {
            listx.add(Component.m_237113_(""));
            listx.add(
               Component.m_237113_(I18n.m_118938_("gui.apotheosis.enchant.quanta.desc4", new Object[]{f(((ApothEnchantmentMenu)this.f_97732_).quanta.get())}))
                  .m_130940_(ChatFormatting.GRAY)
            );
            listx.add(
               Component.m_237113_(
                     I18n.m_118938_("info.apotheosis.gui_rectification", new Object[]{f(((ApothEnchantmentMenu)this.f_97732_).rectification.get())})
                  )
                  .m_130940_(ChatFormatting.YELLOW)
            );
         }

         this.m_96597_(stack, listx, mouseX, mouseY);
         float quanta = ((ApothEnchantmentMenu)this.f_97732_).quanta.get();
         float rectification = ((ApothEnchantmentMenu)this.f_97732_).rectification.get();
         if (quanta > 0.0F) {
            listx.clear();
            listx.add(Component.m_237115_("info.apotheosis.quanta_buff").m_130944_(new ChatFormatting[]{ChatFormatting.UNDERLINE, ChatFormatting.RED}));
            listx.add(
               Component.m_237110_("info.apotheosis.quanta_reduc", new Object[]{f(-quanta + quanta * rectification / 100.0F)})
                  .m_130940_(ChatFormatting.DARK_RED)
            );
            listx.add(Component.m_237110_("info.apotheosis.quanta_growth", new Object[]{f(quanta)}).m_130940_(ChatFormatting.BLUE));
            this.drawOnLeft(stack, listx, this.getGuiTop() + 29);
         }
      } else if (this.m_6774_(60, 96, 110, 5, (double)mouseX, (double)mouseY)) {
         List<Component> listxx = Lists.newArrayList();
         stack.m_85836_();
         stack.m_85837_(0.0, 0.0, 4.0);
         listxx.add(Component.m_237113_(arcana() + I18n.m_118938_("gui.apotheosis.enchant.arcana.desc", new Object[0])));
         listxx.add(Component.m_237115_("gui.apotheosis.enchant.arcana.desc2").m_130940_(ChatFormatting.GRAY));
         listxx.add(Component.m_237115_("gui.apotheosis.enchant.arcana.desc3").m_130940_(ChatFormatting.GRAY));
         if (((ApothEnchantmentMenu)this.f_97732_).arcana.get() > 0.0F) {
            listxx.add(Component.m_237113_(""));
            float ench = (float)((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_7993_().getEnchantmentValue() / 2.0F;
            listxx.add(
               Component.m_237113_(
                     I18n.m_118938_("gui.apotheosis.enchant.arcana.desc4", new Object[]{f(((ApothEnchantmentMenu)this.f_97732_).arcana.get() - ench)})
                  )
                  .m_130940_(ChatFormatting.GRAY)
            );
            listxx.add(Component.m_237110_("info.apotheosis.ench_bonus", new Object[]{f(ench)}).m_130940_(ChatFormatting.YELLOW));
            listxx.add(
               Component.m_237113_(I18n.m_118938_("gui.apotheosis.enchant.arcana.desc5", new Object[]{f(((ApothEnchantmentMenu)this.f_97732_).arcana.get())}))
                  .m_130940_(ChatFormatting.GOLD)
            );
         }

         this.m_96597_(stack, listxx, mouseX, mouseY);
         stack.m_85849_();
         if (((ApothEnchantmentMenu)this.f_97732_).arcana.get() > 0.0F) {
            listxx.clear();
            ApothEnchantmentMenu.Arcana a = ApothEnchantmentMenu.Arcana.getForThreshold(((ApothEnchantmentMenu)this.f_97732_).arcana.get());
            listxx.add(
               Component.m_237115_("info.apotheosis.arcana_bonus").m_130944_(new ChatFormatting[]{ChatFormatting.UNDERLINE, ChatFormatting.DARK_PURPLE})
            );
            if (a != ApothEnchantmentMenu.Arcana.EMPTY) {
               listxx.add(Component.m_237115_("info.apotheosis.weights_changed").m_130940_(ChatFormatting.BLUE));
            }

            int minEnchants = ((ApothEnchantmentMenu)this.f_97732_).arcana.get() > 75.0F
               ? 3
               : (((ApothEnchantmentMenu)this.f_97732_).arcana.get() > 25.0F ? 2 : 0);
            if (minEnchants > 0) {
               listxx.add(Component.m_237110_("info.apotheosis.min_enchants", new Object[]{minEnchants}).m_130940_(ChatFormatting.BLUE));
            }

            this.drawOnLeft(stack, listxx, this.getGuiTop() + 29);
            int offset = 20 + listxx.size() * 9;
            listxx.clear();
            listxx.add(Component.m_237115_("info.apotheosis.rel_weights").m_130944_(new ChatFormatting[]{ChatFormatting.UNDERLINE, ChatFormatting.YELLOW}));
            listxx.add(
               Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.common", new Object[0]), a.rarities[0]})
                  .m_130940_(ChatFormatting.GRAY)
            );
            listxx.add(
               Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.uncommon", new Object[0]), a.rarities[1]})
                  .m_130940_(ChatFormatting.GREEN)
            );
            listxx.add(
               Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.rare", new Object[0]), a.rarities[2]})
                  .m_130940_(ChatFormatting.BLUE)
            );
            listxx.add(
               Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.very_rare", new Object[0]), a.rarities[3]})
                  .m_130940_(ChatFormatting.GOLD)
            );
            this.drawOnLeft(stack, listxx, this.getGuiTop() + 29 + offset);
         }
      } else if (((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_6657_()
         && this.m_6774_(145, -15, 27, 15, (double)mouseX, (double)mouseY)
         && Arrays.stream(((ApothEnchantmentMenu)this.f_97732_).f_39447_).boxed().map(Enchantment::m_44697_).allMatch(Predicates.notNull())) {
         List<Component> listxxx = Lists.newArrayList();
         listxxx.add(Component.m_237115_("info.apotheosis.all_available").m_130940_(ChatFormatting.BLUE));
         this.m_96597_(stack, listxxx, mouseX, mouseY);
      }

      ItemStack enchanting = ((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_7993_();
      if (!enchanting.m_41619_() && ((ApothEnchantmentMenu)this.f_97732_).f_39446_[2] > 0) {
         for (int slotx = 0; slotx < 3; slotx++) {
            if (this.m_6774_(60, 14 + 19 * slotx, 108, 17, (double)mouseX, (double)mouseY)) {
               List<Component> listxxx = new ArrayList<>();
               int level = ((ApothEnchantmentMenu)this.f_97732_).f_39446_[slotx];
               listxxx.add(
                  Component.m_237113_(I18n.m_118938_("info.apotheosis.ench_at", new Object[]{level}))
                     .m_130944_(new ChatFormatting[]{ChatFormatting.UNDERLINE, ChatFormatting.GREEN})
               );
               listxxx.add(Component.m_237113_(""));
               int expCost = ApothMiscUtil.getExpCostForSlot(level, slotx);
               listxxx.add(
                  Component.m_237110_(
                     "info.apotheosis.xp_cost",
                     new Object[]{
                        Component.m_237113_(expCost + "").m_130940_(ChatFormatting.GREEN),
                        Component.m_237113_(EnchantmentUtils.getLevelForExperience(expCost) + "").m_130940_(ChatFormatting.GREEN)
                     }
                  )
               );
               float quanta = ((ApothEnchantmentMenu)this.f_97732_).quanta.get() / 100.0F;
               float rectification = ((ApothEnchantmentMenu)this.f_97732_).rectification.get() / 100.0F;
               int minPow = Math.round(
                  Mth.m_14036_((float)level - (float)level * (quanta - quanta * rectification), 1.0F, EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F)
               );
               int maxPow = Math.round(Mth.m_14036_((float)level + (float)level * quanta, 1.0F, EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F));
               listxxx.add(
                  Component.m_237110_(
                     "info.apotheosis.power_range",
                     new Object[]{
                        Component.m_237113_(minPow + "").m_130940_(ChatFormatting.DARK_RED), Component.m_237113_(maxPow + "").m_130940_(ChatFormatting.BLUE)
                     }
                  )
               );
               listxxx.add(
                  Component.m_237110_(
                     "info.apotheosis.item_ench", new Object[]{Component.m_237113_(enchanting.getEnchantmentValue() + "").m_130940_(ChatFormatting.GREEN)}
                  )
               );
               listxxx.add(
                  Component.m_237110_(
                     "info.apotheosis.num_clues",
                     new Object[]{Component.m_237113_(1 + ((ApothEnchantmentMenu)this.f_97732_).clues.m_6501_() + "").m_130940_(ChatFormatting.DARK_AQUA)}
                  )
               );
               this.drawOnLeft(stack, listxxx, this.getGuiTop() + 29);
               break;
            }
         }
      }
   }

   public void drawOnLeft(PoseStack stack, List<Component> list, int y) {
      if (!list.isEmpty()) {
         int xPos = this.getGuiLeft() - 16 - list.stream().<Integer>map(this.f_96547_::m_92852_).max(Integer::compare).get();
         int maxWidth = 9999;
         if (xPos < 0) {
            maxWidth = this.getGuiLeft() - 6;
            xPos = -8;
         }

         List<FormattedText> split = new ArrayList<>();
         int lambdastupid = maxWidth;
         list.forEach(comp -> split.addAll(this.f_96547_.m_92865_().m_92414_(comp, lambdastupid, comp.m_7383_())));
         this.renderComponentTooltip(stack, split, xPos, y, this.f_96547_);
      }
   }

   public void tickBook() {
      ItemStack itemstack = ((ApothEnchantmentMenu)this.f_97732_).m_38853_(0).m_7993_();
      if (!ItemStack.m_41728_(itemstack, this.last)) {
         this.last = itemstack;

         do {
            this.flipT = this.flipT + (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while (this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
      }

      this.oFlip = this.flip;
      this.oOpen = this.open;
      boolean flag = false;

      for (int i = 0; i < 3; i++) {
         if (((ApothEnchantmentMenu)this.f_97732_).f_39446_[i] != 0) {
            flag = true;
         }
      }

      if (flag) {
         this.open += 0.2F;
      } else {
         this.open -= 0.2F;
      }

      this.open = Mth.m_14036_(this.open, 0.0F, 1.0F);
      float f1 = (this.flipT - this.flip) * 0.4F;
      f1 = Mth.m_14036_(f1, -0.2F, 0.2F);
      this.flipA = this.flipA + (f1 - this.flipA) * 0.9F;
      this.flip = this.flip + this.flipA;
   }

   public void acceptClues(int slot, List<EnchantmentInstance> clues, boolean all) {
      this.clues.put(slot, clues);
      this.hasAllClues[slot] = all;
   }

   private static String eterna() {
      return ChatFormatting.GREEN + I18n.m_118938_("gui.apotheosis.enchant.eterna", new Object[0]) + ChatFormatting.RESET;
   }

   private static String quanta() {
      return ChatFormatting.RED + I18n.m_118938_("gui.apotheosis.enchant.quanta", new Object[0]) + ChatFormatting.RESET;
   }

   private static String arcana() {
      return ChatFormatting.DARK_PURPLE + I18n.m_118938_("gui.apotheosis.enchant.arcana", new Object[0]) + ChatFormatting.RESET;
   }

   private static String rectification() {
      return ChatFormatting.YELLOW + I18n.m_118938_("gui.apotheosis.enchant.rectification", new Object[0]) + ChatFormatting.RESET;
   }

   private static String f(float f) {
      return String.format("%.2f", f);
   }
}
