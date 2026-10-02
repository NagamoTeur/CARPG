package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.Base64Variant;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonLocation;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonStreamContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.util.JsonParserDelegate;

public class FilteringParserDelegate extends JsonParserDelegate {
   protected TokenFilter rootFilter;
   protected boolean _allowMultipleMatches;
   protected boolean _includePath;
   @Deprecated
   protected boolean _includeImmediateParent;
   protected JsonToken _currToken;
   protected JsonToken _lastClearedToken;
   protected TokenFilterContext _headContext;
   protected TokenFilterContext _exposedContext;
   protected TokenFilter _itemFilter;
   protected int _matchCount;

   public FilteringParserDelegate(JsonParser p, TokenFilter f, boolean includePath, boolean allowMultipleMatches) {
      super(p);
      this.rootFilter = f;
      this._itemFilter = f;
      this._headContext = TokenFilterContext.createRootContext(f);
      this._includePath = includePath;
      this._allowMultipleMatches = allowMultipleMatches;
   }

   public TokenFilter getFilter() {
      return this.rootFilter;
   }

   public int getMatchCount() {
      return this._matchCount;
   }

   @Override
   public JsonToken getCurrentToken() {
      return this._currToken;
   }

   @Override
   public JsonToken currentToken() {
      return this._currToken;
   }

   @Override
   public final int getCurrentTokenId() {
      JsonToken t = this._currToken;
      return t == null ? 0 : t.id();
   }

   @Override
   public final int currentTokenId() {
      JsonToken t = this._currToken;
      return t == null ? 0 : t.id();
   }

   @Override
   public boolean hasCurrentToken() {
      return this._currToken != null;
   }

   @Override
   public boolean hasTokenId(int id) {
      JsonToken t = this._currToken;
      return t == null ? 0 == id : t.id() == id;
   }

   @Override
   public final boolean hasToken(JsonToken t) {
      return this._currToken == t;
   }

   @Override
   public boolean isExpectedStartArrayToken() {
      return this._currToken == JsonToken.START_ARRAY;
   }

   @Override
   public boolean isExpectedStartObjectToken() {
      return this._currToken == JsonToken.START_OBJECT;
   }

   @Override
   public JsonLocation getCurrentLocation() {
      return this.delegate.getCurrentLocation();
   }

   @Override
   public JsonStreamContext getParsingContext() {
      return this._filterContext();
   }

   @Override
   public String getCurrentName() throws IOException {
      JsonStreamContext ctxt = this._filterContext();
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return ctxt.getCurrentName();
      } else {
         JsonStreamContext parent = ctxt.getParent();
         return parent == null ? null : parent.getCurrentName();
      }
   }

   @Override
   public void clearCurrentToken() {
      if (this._currToken != null) {
         this._lastClearedToken = this._currToken;
         this._currToken = null;
      }
   }

   @Override
   public JsonToken getLastClearedToken() {
      return this._lastClearedToken;
   }

   @Override
   public void overrideCurrentName(String name) {
      throw new UnsupportedOperationException("Can not currently override name during filtering read");
   }

   @Override
   public JsonToken nextToken() throws IOException {
      if (!this._allowMultipleMatches
         && this._currToken != null
         && this._exposedContext == null
         && this._currToken.isScalarValue()
         && !this._headContext.isStartHandled()
         && !this._includePath
         && this._itemFilter == TokenFilter.INCLUDE_ALL) {
         return this._currToken = null;
      } else {
         TokenFilterContext ctxt = this._exposedContext;
         if (ctxt != null) {
            while (true) {
               JsonToken t = ctxt.nextTokenToRead();
               if (t != null) {
                  this._currToken = t;
                  return t;
               }

               if (ctxt == this._headContext) {
                  this._exposedContext = null;
                  if (ctxt.inArray()) {
                     t = this.delegate.getCurrentToken();
                     this._currToken = t;
                     return t;
                  }
                  break;
               }

               ctxt = this._headContext.findChildOf(ctxt);
               this._exposedContext = ctxt;
               if (ctxt == null) {
                  throw this._constructError("Unexpected problem: chain of filtered context broken");
               }
            }
         }

         JsonToken tx = this.delegate.nextToken();
         if (tx == null) {
            this._currToken = tx;
            return tx;
         } else {
            switch (tx.id()) {
               case 1:
                  TokenFilter var16 = this._itemFilter;
                  if (var16 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildObjectContext(var16, true);
                     return this._currToken = tx;
                  }

                  if (var16 == null) {
                     this.delegate.skipChildren();
                  } else {
                     var16 = this._headContext.checkValue(var16);
                     if (var16 == null) {
                        this.delegate.skipChildren();
                     } else {
                        if (var16 != TokenFilter.INCLUDE_ALL) {
                           var16 = var16.filterStartObject();
                        }

                        this._itemFilter = var16;
                        if (var16 == TokenFilter.INCLUDE_ALL) {
                           this._headContext = this._headContext.createChildObjectContext(var16, true);
                           return this._currToken = tx;
                        }

                        this._headContext = this._headContext.createChildObjectContext(var16, false);
                        if (this._includePath) {
                           tx = this._nextTokenWithBuffering(this._headContext);
                           if (tx != null) {
                              this._currToken = tx;
                              return tx;
                           }
                        }
                     }
                  }
                  break;
               case 2:
               case 4:
                  boolean returnEnd = this._headContext.isStartHandled();
                  TokenFilter var15 = this._headContext.getFilter();
                  if (var15 != null && var15 != TokenFilter.INCLUDE_ALL) {
                     var15.filterFinishArray();
                  }

                  this._headContext = this._headContext.getParent();
                  this._itemFilter = this._headContext.getFilter();
                  if (returnEnd) {
                     return this._currToken = tx;
                  }
                  break;
               case 3:
                  TokenFilter f = this._itemFilter;
                  if (f == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildArrayContext(f, true);
                     return this._currToken = tx;
                  }

                  if (f == null) {
                     this.delegate.skipChildren();
                  } else {
                     f = this._headContext.checkValue(f);
                     if (f == null) {
                        this.delegate.skipChildren();
                     } else {
                        if (f != TokenFilter.INCLUDE_ALL) {
                           f = f.filterStartArray();
                        }

                        this._itemFilter = f;
                        if (f == TokenFilter.INCLUDE_ALL) {
                           this._headContext = this._headContext.createChildArrayContext(f, true);
                           return this._currToken = tx;
                        }

                        this._headContext = this._headContext.createChildArrayContext(f, false);
                        if (this._includePath) {
                           tx = this._nextTokenWithBuffering(this._headContext);
                           if (tx != null) {
                              this._currToken = tx;
                              return tx;
                           }
                        }
                     }
                  }
                  break;
               case 5:
                  String name = this.delegate.getCurrentName();
                  TokenFilter var11 = this._headContext.setFieldName(name);
                  if (var11 == TokenFilter.INCLUDE_ALL) {
                     this._itemFilter = var11;
                     if (!this._includePath && this._includeImmediateParent && !this._headContext.isStartHandled()) {
                        tx = this._headContext.nextTokenToRead();
                        this._exposedContext = this._headContext;
                     }

                     return this._currToken = tx;
                  }

                  if (var11 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                  } else {
                     var11 = var11.includeProperty(name);
                     if (var11 == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                     } else {
                        this._itemFilter = var11;
                        if (var11 == TokenFilter.INCLUDE_ALL) {
                           if (this._verifyAllowedMatches()) {
                              if (this._includePath) {
                                 return this._currToken = tx;
                              }
                           } else {
                              this.delegate.nextToken();
                              this.delegate.skipChildren();
                           }
                        }

                        if (this._includePath) {
                           tx = this._nextTokenWithBuffering(this._headContext);
                           if (tx != null) {
                              this._currToken = tx;
                              return tx;
                           }
                        }
                     }
                  }
                  break;
               default:
                  TokenFilter fx = this._itemFilter;
                  if (fx == TokenFilter.INCLUDE_ALL) {
                     return this._currToken = tx;
                  }

                  if (fx != null) {
                     fx = this._headContext.checkValue(fx);
                     if ((fx == TokenFilter.INCLUDE_ALL || fx != null && fx.includeValue(this.delegate)) && this._verifyAllowedMatches()) {
                        return this._currToken = tx;
                     }
                  }
            }

            return this._nextToken2();
         }
      }
   }

   protected final JsonToken _nextToken2() throws IOException {
      while (true) {
         JsonToken t = this.delegate.nextToken();
         if (t == null) {
            this._currToken = t;
            return t;
         }

         switch (t.id()) {
            case 1:
               TokenFilter var13 = this._itemFilter;
               if (var13 == TokenFilter.INCLUDE_ALL) {
                  this._headContext = this._headContext.createChildObjectContext(var13, true);
                  return this._currToken = t;
               }

               if (var13 == null) {
                  this.delegate.skipChildren();
               } else {
                  var13 = this._headContext.checkValue(var13);
                  if (var13 == null) {
                     this.delegate.skipChildren();
                  } else {
                     if (var13 != TokenFilter.INCLUDE_ALL) {
                        var13 = var13.filterStartObject();
                     }

                     this._itemFilter = var13;
                     if (var13 == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(var13, true);
                        return this._currToken = t;
                     }

                     this._headContext = this._headContext.createChildObjectContext(var13, false);
                     if (!this._includePath) {
                        continue;
                     }

                     t = this._nextTokenWithBuffering(this._headContext);
                     if (t == null) {
                        continue;
                     }

                     this._currToken = t;
                     return t;
                  }
               }
               break;
            case 2:
            case 4:
               boolean returnEnd = this._headContext.isStartHandled();
               TokenFilter var12 = this._headContext.getFilter();
               if (var12 != null && var12 != TokenFilter.INCLUDE_ALL) {
                  var12.filterFinishArray();
               }

               this._headContext = this._headContext.getParent();
               this._itemFilter = this._headContext.getFilter();
               if (!returnEnd) {
                  break;
               }

               return this._currToken = t;
            case 3:
               TokenFilter f = this._itemFilter;
               if (f == TokenFilter.INCLUDE_ALL) {
                  this._headContext = this._headContext.createChildArrayContext(f, true);
                  return this._currToken = t;
               }

               if (f == null) {
                  this.delegate.skipChildren();
               } else {
                  f = this._headContext.checkValue(f);
                  if (f == null) {
                     this.delegate.skipChildren();
                  } else {
                     if (f != TokenFilter.INCLUDE_ALL) {
                        f = f.filterStartArray();
                     }

                     this._itemFilter = f;
                     if (f == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(f, true);
                        return this._currToken = t;
                     }

                     this._headContext = this._headContext.createChildArrayContext(f, false);
                     if (!this._includePath) {
                        continue;
                     }

                     t = this._nextTokenWithBuffering(this._headContext);
                     if (t == null) {
                        continue;
                     }

                     this._currToken = t;
                     return t;
                  }
               }
               break;
            case 5:
               String name = this.delegate.getCurrentName();
               TokenFilter var8 = this._headContext.setFieldName(name);
               if (var8 == TokenFilter.INCLUDE_ALL) {
                  this._itemFilter = var8;
                  return this._currToken = t;
               }

               if (var8 == null) {
                  this.delegate.nextToken();
                  this.delegate.skipChildren();
               } else {
                  var8 = var8.includeProperty(name);
                  if (var8 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                  } else {
                     this._itemFilter = var8;
                     if (var8 == TokenFilter.INCLUDE_ALL) {
                        if (!this._verifyAllowedMatches() || !this._includePath) {
                           continue;
                        }

                        return this._currToken = t;
                     } else {
                        if (!this._includePath) {
                           continue;
                        }

                        t = this._nextTokenWithBuffering(this._headContext);
                        if (t == null) {
                           continue;
                        }

                        this._currToken = t;
                        return t;
                     }
                  }
               }
               break;
            default:
               TokenFilter fx = this._itemFilter;
               if (fx == TokenFilter.INCLUDE_ALL) {
                  return this._currToken = t;
               }

               if (fx != null) {
                  fx = this._headContext.checkValue(fx);
                  if ((fx == TokenFilter.INCLUDE_ALL || fx != null && fx.includeValue(this.delegate)) && this._verifyAllowedMatches()) {
                     return this._currToken = t;
                  }
               }
         }
      }
   }

   protected final JsonToken _nextTokenWithBuffering(TokenFilterContext buffRoot) throws IOException {
      while (true) {
         JsonToken t = this.delegate.nextToken();
         if (t == null) {
            return t;
         }

         switch (t.id()) {
            case 1:
               TokenFilter var11 = this._itemFilter;
               if (var11 == TokenFilter.INCLUDE_ALL) {
                  this._headContext = this._headContext.createChildObjectContext(var11, true);
                  return t;
               }

               if (var11 == null) {
                  this.delegate.skipChildren();
               } else {
                  var11 = this._headContext.checkValue(var11);
                  if (var11 == null) {
                     this.delegate.skipChildren();
                     break;
                  }

                  if (var11 != TokenFilter.INCLUDE_ALL) {
                     var11 = var11.filterStartObject();
                  }

                  this._itemFilter = var11;
                  if (var11 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildObjectContext(var11, true);
                     return this._nextBuffered(buffRoot);
                  }

                  this._headContext = this._headContext.createChildObjectContext(var11, false);
               }
               break;
            case 2:
            case 4:
               TokenFilter var10 = this._headContext.getFilter();
               if (var10 != null && var10 != TokenFilter.INCLUDE_ALL) {
                  var10.filterFinishArray();
               }

               boolean gotEnd = this._headContext == buffRoot;
               boolean returnEnd = gotEnd && this._headContext.isStartHandled();
               this._headContext = this._headContext.getParent();
               this._itemFilter = this._headContext.getFilter();
               if (returnEnd) {
                  return t;
               }
               break;
            case 3:
               TokenFilter f = this._headContext.checkValue(this._itemFilter);
               if (f == null) {
                  this.delegate.skipChildren();
                  break;
               }

               if (f != TokenFilter.INCLUDE_ALL) {
                  f = f.filterStartArray();
               }

               this._itemFilter = f;
               if (f == TokenFilter.INCLUDE_ALL) {
                  this._headContext = this._headContext.createChildArrayContext(f, true);
                  return this._nextBuffered(buffRoot);
               }

               this._headContext = this._headContext.createChildArrayContext(f, false);
               break;
            case 5:
               String name = this.delegate.getCurrentName();
               TokenFilter var7 = this._headContext.setFieldName(name);
               if (var7 == TokenFilter.INCLUDE_ALL) {
                  this._itemFilter = var7;
                  return this._nextBuffered(buffRoot);
               }

               if (var7 == null) {
                  this.delegate.nextToken();
                  this.delegate.skipChildren();
               } else {
                  var7 = var7.includeProperty(name);
                  if (var7 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                  } else {
                     this._itemFilter = var7;
                     if (var7 != TokenFilter.INCLUDE_ALL) {
                        break;
                     }

                     if (this._verifyAllowedMatches()) {
                        return this._nextBuffered(buffRoot);
                     }

                     this._itemFilter = this._headContext.setFieldName(name);
                  }
               }
               break;
            default:
               TokenFilter fx = this._itemFilter;
               if (fx == TokenFilter.INCLUDE_ALL) {
                  return this._nextBuffered(buffRoot);
               }

               if (fx != null) {
                  fx = this._headContext.checkValue(fx);
                  if ((fx == TokenFilter.INCLUDE_ALL || fx != null && fx.includeValue(this.delegate)) && this._verifyAllowedMatches()) {
                     return this._nextBuffered(buffRoot);
                  }
               }
         }
      }
   }

   private JsonToken _nextBuffered(TokenFilterContext buffRoot) throws IOException {
      this._exposedContext = buffRoot;
      TokenFilterContext ctxt = buffRoot;
      JsonToken t = buffRoot.nextTokenToRead();
      if (t != null) {
         return t;
      } else {
         while (ctxt != this._headContext) {
            ctxt = this._exposedContext.findChildOf(ctxt);
            this._exposedContext = ctxt;
            if (ctxt == null) {
               throw this._constructError("Unexpected problem: chain of filtered context broken");
            }

            t = this._exposedContext.nextTokenToRead();
            if (t != null) {
               return t;
            }
         }

         throw this._constructError("Internal error: failed to locate expected buffered tokens");
      }
   }

   private final boolean _verifyAllowedMatches() throws IOException {
      if (this._matchCount != 0 && !this._allowMultipleMatches) {
         return false;
      } else {
         this._matchCount++;
         return true;
      }
   }

   @Override
   public JsonToken nextValue() throws IOException {
      JsonToken t = this.nextToken();
      if (t == JsonToken.FIELD_NAME) {
         t = this.nextToken();
      }

      return t;
   }

   @Override
   public JsonParser skipChildren() throws IOException {
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return this;
      } else {
         int open = 1;

         while (true) {
            JsonToken t = this.nextToken();
            if (t == null) {
               return this;
            }

            if (t.isStructStart()) {
               open++;
            } else if (t.isStructEnd()) {
               if (--open == 0) {
                  return this;
               }
            }
         }
      }
   }

   @Override
   public String getText() throws IOException {
      return this.delegate.getText();
   }

   @Override
   public boolean hasTextCharacters() {
      return this.delegate.hasTextCharacters();
   }

   @Override
   public char[] getTextCharacters() throws IOException {
      return this.delegate.getTextCharacters();
   }

   @Override
   public int getTextLength() throws IOException {
      return this.delegate.getTextLength();
   }

   @Override
   public int getTextOffset() throws IOException {
      return this.delegate.getTextOffset();
   }

   @Override
   public BigInteger getBigIntegerValue() throws IOException {
      return this.delegate.getBigIntegerValue();
   }

   @Override
   public boolean getBooleanValue() throws IOException {
      return this.delegate.getBooleanValue();
   }

   @Override
   public byte getByteValue() throws IOException {
      return this.delegate.getByteValue();
   }

   @Override
   public short getShortValue() throws IOException {
      return this.delegate.getShortValue();
   }

   @Override
   public BigDecimal getDecimalValue() throws IOException {
      return this.delegate.getDecimalValue();
   }

   @Override
   public double getDoubleValue() throws IOException {
      return this.delegate.getDoubleValue();
   }

   @Override
   public float getFloatValue() throws IOException {
      return this.delegate.getFloatValue();
   }

   @Override
   public int getIntValue() throws IOException {
      return this.delegate.getIntValue();
   }

   @Override
   public long getLongValue() throws IOException {
      return this.delegate.getLongValue();
   }

   @Override
   public JsonParser.NumberType getNumberType() throws IOException {
      return this.delegate.getNumberType();
   }

   @Override
   public Number getNumberValue() throws IOException {
      return this.delegate.getNumberValue();
   }

   @Override
   public int getValueAsInt() throws IOException {
      return this.delegate.getValueAsInt();
   }

   @Override
   public int getValueAsInt(int defaultValue) throws IOException {
      return this.delegate.getValueAsInt(defaultValue);
   }

   @Override
   public long getValueAsLong() throws IOException {
      return this.delegate.getValueAsLong();
   }

   @Override
   public long getValueAsLong(long defaultValue) throws IOException {
      return this.delegate.getValueAsLong(defaultValue);
   }

   @Override
   public double getValueAsDouble() throws IOException {
      return this.delegate.getValueAsDouble();
   }

   @Override
   public double getValueAsDouble(double defaultValue) throws IOException {
      return this.delegate.getValueAsDouble(defaultValue);
   }

   @Override
   public boolean getValueAsBoolean() throws IOException {
      return this.delegate.getValueAsBoolean();
   }

   @Override
   public boolean getValueAsBoolean(boolean defaultValue) throws IOException {
      return this.delegate.getValueAsBoolean(defaultValue);
   }

   @Override
   public String getValueAsString() throws IOException {
      return this.delegate.getValueAsString();
   }

   @Override
   public String getValueAsString(String defaultValue) throws IOException {
      return this.delegate.getValueAsString(defaultValue);
   }

   @Override
   public Object getEmbeddedObject() throws IOException {
      return this.delegate.getEmbeddedObject();
   }

   @Override
   public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
      return this.delegate.getBinaryValue(b64variant);
   }

   @Override
   public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
      return this.delegate.readBinaryValue(b64variant, out);
   }

   @Override
   public JsonLocation getTokenLocation() {
      return this.delegate.getTokenLocation();
   }

   protected JsonStreamContext _filterContext() {
      return this._exposedContext != null ? this._exposedContext : this._headContext;
   }
}
