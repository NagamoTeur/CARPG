package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonPointer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.type.WritableTypeId;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializationFeature;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.RawValue;

public class ObjectNode extends ContainerNode<ObjectNode> {
   protected final Map<String, JsonNode> _children;

   public ObjectNode(JsonNodeFactory nc) {
      super(nc);
      this._children = new LinkedHashMap<>();
   }

   public ObjectNode(JsonNodeFactory nc, Map<String, JsonNode> kids) {
      super(nc);
      this._children = kids;
   }

   @Override
   protected JsonNode _at(JsonPointer ptr) {
      return this.get(ptr.getMatchingProperty());
   }

   public ObjectNode deepCopy() {
      ObjectNode ret = new ObjectNode(this._nodeFactory);

      for (Entry<String, JsonNode> entry : this._children.entrySet()) {
         ret._children.put(entry.getKey(), entry.getValue().deepCopy());
      }

      return ret;
   }

   @Override
   public boolean isEmpty(SerializerProvider serializers) {
      return this._children.isEmpty();
   }

   @Override
   public JsonNodeType getNodeType() {
      return JsonNodeType.OBJECT;
   }

   @Override
   public JsonToken asToken() {
      return JsonToken.START_OBJECT;
   }

   @Override
   public int size() {
      return this._children.size();
   }

   @Override
   public Iterator<JsonNode> elements() {
      return this._children.values().iterator();
   }

   @Override
   public JsonNode get(int index) {
      return null;
   }

   @Override
   public JsonNode get(String fieldName) {
      return this._children.get(fieldName);
   }

   @Override
   public Iterator<String> fieldNames() {
      return this._children.keySet().iterator();
   }

   @Override
   public JsonNode path(int index) {
      return MissingNode.getInstance();
   }

   @Override
   public JsonNode path(String fieldName) {
      JsonNode n = this._children.get(fieldName);
      return (JsonNode)(n != null ? n : MissingNode.getInstance());
   }

   @Override
   public Iterator<Entry<String, JsonNode>> fields() {
      return this._children.entrySet().iterator();
   }

   public ObjectNode with(String propertyName) {
      JsonNode n = this._children.get(propertyName);
      if (n != null) {
         if (n instanceof ObjectNode) {
            return (ObjectNode)n;
         } else {
            throw new UnsupportedOperationException(
               "Property '" + propertyName + "' has value that is not of type ObjectNode (but " + n.getClass().getName() + ")"
            );
         }
      } else {
         ObjectNode result = this.objectNode();
         this._children.put(propertyName, result);
         return result;
      }
   }

   public ArrayNode withArray(String propertyName) {
      JsonNode n = this._children.get(propertyName);
      if (n != null) {
         if (n instanceof ArrayNode) {
            return (ArrayNode)n;
         } else {
            throw new UnsupportedOperationException(
               "Property '" + propertyName + "' has value that is not of type ArrayNode (but " + n.getClass().getName() + ")"
            );
         }
      } else {
         ArrayNode result = this.arrayNode();
         this._children.put(propertyName, result);
         return result;
      }
   }

   @Override
   public boolean equals(Comparator<JsonNode> comparator, JsonNode o) {
      if (!(o instanceof ObjectNode)) {
         return false;
      } else {
         ObjectNode other = (ObjectNode)o;
         Map<String, JsonNode> m1 = this._children;
         Map<String, JsonNode> m2 = other._children;
         int len = m1.size();
         if (m2.size() != len) {
            return false;
         } else {
            for (Entry<String, JsonNode> entry : m1.entrySet()) {
               JsonNode v2 = m2.get(entry.getKey());
               if (v2 == null || !entry.getValue().equals(comparator, v2)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public JsonNode findValue(String fieldName) {
      for (Entry<String, JsonNode> entry : this._children.entrySet()) {
         if (fieldName.equals(entry.getKey())) {
            return entry.getValue();
         }

         JsonNode value = entry.getValue().findValue(fieldName);
         if (value != null) {
            return value;
         }
      }

      return null;
   }

   @Override
   public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
      for (Entry<String, JsonNode> entry : this._children.entrySet()) {
         if (fieldName.equals(entry.getKey())) {
            if (foundSoFar == null) {
               foundSoFar = new ArrayList<>();
            }

            foundSoFar.add(entry.getValue());
         } else {
            foundSoFar = entry.getValue().findValues(fieldName, foundSoFar);
         }
      }

      return foundSoFar;
   }

   @Override
   public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
      for (Entry<String, JsonNode> entry : this._children.entrySet()) {
         if (fieldName.equals(entry.getKey())) {
            if (foundSoFar == null) {
               foundSoFar = new ArrayList<>();
            }

            foundSoFar.add(entry.getValue().asText());
         } else {
            foundSoFar = entry.getValue().findValuesAsText(fieldName, foundSoFar);
         }
      }

      return foundSoFar;
   }

   public ObjectNode findParent(String fieldName) {
      for (Entry<String, JsonNode> entry : this._children.entrySet()) {
         if (fieldName.equals(entry.getKey())) {
            return this;
         }

         JsonNode value = entry.getValue().findParent(fieldName);
         if (value != null) {
            return (ObjectNode)value;
         }
      }

      return null;
   }

   @Override
   public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
      for (Entry<String, JsonNode> entry : this._children.entrySet()) {
         if (fieldName.equals(entry.getKey())) {
            if (foundSoFar == null) {
               foundSoFar = new ArrayList<>();
            }

            foundSoFar.add(this);
         } else {
            foundSoFar = entry.getValue().findParents(fieldName, foundSoFar);
         }
      }

      return foundSoFar;
   }

   @Override
   public void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
      boolean trimEmptyArray = provider != null && !provider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
      g.writeStartObject(this);

      for (Entry<String, JsonNode> en : this._children.entrySet()) {
         BaseJsonNode value = (BaseJsonNode)en.getValue();
         if (!trimEmptyArray || !value.isArray() || !value.isEmpty(provider)) {
            g.writeFieldName(en.getKey());
            value.serialize(g, provider);
         }
      }

      g.writeEndObject();
   }

   @Override
   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
      boolean trimEmptyArray = provider != null && !provider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
      WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(this, JsonToken.START_OBJECT));

      for (Entry<String, JsonNode> en : this._children.entrySet()) {
         BaseJsonNode value = (BaseJsonNode)en.getValue();
         if (!trimEmptyArray || !value.isArray() || !value.isEmpty(provider)) {
            g.writeFieldName(en.getKey());
            value.serialize(g, provider);
         }
      }

      typeSer.writeTypeSuffix(g, typeIdDef);
   }

   public JsonNode set(String fieldName, JsonNode value) {
      if (value == null) {
         value = this.nullNode();
      }

      this._children.put(fieldName, value);
      return this;
   }

   public JsonNode setAll(Map<String, ? extends JsonNode> properties) {
      for (Entry<String, ? extends JsonNode> en : properties.entrySet()) {
         JsonNode n = en.getValue();
         if (n == null) {
            n = this.nullNode();
         }

         this._children.put(en.getKey(), n);
      }

      return this;
   }

   public JsonNode setAll(ObjectNode other) {
      this._children.putAll(other._children);
      return this;
   }

   public JsonNode replace(String fieldName, JsonNode value) {
      if (value == null) {
         value = this.nullNode();
      }

      return this._children.put(fieldName, value);
   }

   public JsonNode without(String fieldName) {
      this._children.remove(fieldName);
      return this;
   }

   public ObjectNode without(Collection<String> fieldNames) {
      this._children.keySet().removeAll(fieldNames);
      return this;
   }

   @Deprecated
   public JsonNode put(String fieldName, JsonNode value) {
      if (value == null) {
         value = this.nullNode();
      }

      return this._children.put(fieldName, value);
   }

   public JsonNode remove(String fieldName) {
      return this._children.remove(fieldName);
   }

   public ObjectNode remove(Collection<String> fieldNames) {
      this._children.keySet().removeAll(fieldNames);
      return this;
   }

   public ObjectNode removeAll() {
      this._children.clear();
      return this;
   }

   @Deprecated
   public JsonNode putAll(Map<String, ? extends JsonNode> properties) {
      return this.setAll(properties);
   }

   @Deprecated
   public JsonNode putAll(ObjectNode other) {
      return this.setAll(other);
   }

   public ObjectNode retain(Collection<String> fieldNames) {
      this._children.keySet().retainAll(fieldNames);
      return this;
   }

   public ObjectNode retain(String... fieldNames) {
      return this.retain(Arrays.asList(fieldNames));
   }

   public ArrayNode putArray(String fieldName) {
      ArrayNode n = this.arrayNode();
      this._put(fieldName, n);
      return n;
   }

   public ObjectNode putObject(String fieldName) {
      ObjectNode n = this.objectNode();
      this._put(fieldName, n);
      return n;
   }

   public ObjectNode putPOJO(String fieldName, Object pojo) {
      return this._put(fieldName, this.pojoNode(pojo));
   }

   public ObjectNode putRawValue(String fieldName, RawValue raw) {
      return this._put(fieldName, this.rawValueNode(raw));
   }

   public ObjectNode putNull(String fieldName) {
      this._children.put(fieldName, this.nullNode());
      return this;
   }

   public ObjectNode put(String fieldName, short v) {
      return this._put(fieldName, this.numberNode(v));
   }

   public ObjectNode put(String fieldName, Short v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, int v) {
      return this._put(fieldName, this.numberNode(v));
   }

   public ObjectNode put(String fieldName, Integer v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, long v) {
      return this._put(fieldName, this.numberNode(v));
   }

   public ObjectNode put(String fieldName, Long v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, float v) {
      return this._put(fieldName, this.numberNode(v));
   }

   public ObjectNode put(String fieldName, Float v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, double v) {
      return this._put(fieldName, this.numberNode(v));
   }

   public ObjectNode put(String fieldName, Double v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, BigDecimal v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, BigInteger v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.numberNode(v)));
   }

   public ObjectNode put(String fieldName, String v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.textNode(v)));
   }

   public ObjectNode put(String fieldName, boolean v) {
      return this._put(fieldName, this.booleanNode(v));
   }

   public ObjectNode put(String fieldName, Boolean v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.booleanNode(v)));
   }

   public ObjectNode put(String fieldName, byte[] v) {
      return this._put(fieldName, (JsonNode)(v == null ? this.nullNode() : this.binaryNode(v)));
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else {
         return o instanceof ObjectNode ? this._childrenEqual((ObjectNode)o) : false;
      }
   }

   protected boolean _childrenEqual(ObjectNode other) {
      return this._children.equals(other._children);
   }

   @Override
   public int hashCode() {
      return this._children.hashCode();
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(32 + (this.size() << 4));
      sb.append("{");
      int count = 0;

      for (Entry<String, JsonNode> en : this._children.entrySet()) {
         if (count > 0) {
            sb.append(",");
         }

         count++;
         TextNode.appendQuoted(sb, en.getKey());
         sb.append(':');
         sb.append(en.getValue().toString());
      }

      sb.append("}");
      return sb.toString();
   }

   protected ObjectNode _put(String fieldName, JsonNode value) {
      this._children.put(fieldName, value);
      return this;
   }
}
