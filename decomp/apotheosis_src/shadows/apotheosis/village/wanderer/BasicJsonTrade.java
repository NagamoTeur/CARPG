package shadows.apotheosis.village.wanderer;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.json.PSerializer;

public class BasicJsonTrade extends BasicItemListing implements JsonTrade {
   public static final PSerializer<BasicJsonTrade> SERIALIZER = PSerializer.basic("Basic Trade", BasicJsonTrade::fromJson);
   protected ResourceLocation id;
   protected final boolean rare;

   public BasicJsonTrade(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult, boolean rare) {
      super(price, price2, forSale, maxTrades, xp, priceMult);
      this.rare = rare;
   }

   public void setId(ResourceLocation id) {
      if (this.id != null) {
         throw new UnsupportedOperationException();
      } else {
         this.id = id;
      }
   }

   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public boolean isRare() {
      return this.rare;
   }

   public PSerializer<? extends JsonTrade> getSerializer() {
      return SERIALIZER;
   }

   public static BasicJsonTrade fromJson(JsonObject obj) {
      ItemStack price1 = (ItemStack)ItemAdapter.ITEM_READER.fromJson(obj.get("input_1"), ItemStack.class);
      ItemStack price2 = obj.has("input_2") ? (ItemStack)ItemAdapter.ITEM_READER.fromJson(obj.get("input_2"), ItemStack.class) : ItemStack.f_41583_;
      ItemStack output = (ItemStack)ItemAdapter.ITEM_READER.fromJson(obj.get("output"), ItemStack.class);
      int maxTrades = GsonHelper.m_13824_(obj, "max_trades", 1);
      int xp = GsonHelper.m_13824_(obj, "xp", 0);
      float priceMult = GsonHelper.m_13820_(obj, "price_mult", 1.0F);
      boolean rare = GsonHelper.m_13855_(obj, "rare", false);
      return new BasicJsonTrade(price1, price2, output, maxTrades, xp, priceMult, rare);
   }
}
