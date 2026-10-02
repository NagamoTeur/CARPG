package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonPointer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.type.WritableTypeId;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.RawValue;

public class ArrayNode extends ContainerNode<ArrayNode> {
   private final List<JsonNode> _children;

   public ArrayNode(JsonNodeFactory nf) {
      super(nf);
      this._children = new ArrayList<>();
   }

   public ArrayNode(JsonNodeFactory nf, int capacity) {
      super(nf);
      this._children = new ArrayList<>(capacity);
   }

   public ArrayNode(JsonNodeFactory nf, List<JsonNode> children) {
      super(nf);
      this._children = children;
   }

   @Override
   protected JsonNode _at(JsonPointer ptr) {
      return this.get(ptr.getMatchingIndex());
   }

   public ArrayNode deepCopy() {
      ArrayNode ret = new ArrayNode(this._nodeFactory);

      for (JsonNode element : this._children) {
         ret._children.add(element.deepCopy());
      }

      return ret;
   }

   @Override
   public boolean isEmpty(SerializerProvider serializers) {
      return this._children.isEmpty();
   }

   @Override
   public JsonNodeType getNodeType() {
      return JsonNodeType.ARRAY;
   }

   @Override
   public JsonToken asToken() {
      return JsonToken.START_ARRAY;
   }

   @Override
   public int size() {
      return this._children.size();
   }

   @Override
   public Iterator<JsonNode> elements() {
      return this._children.iterator();
   }

   @Override
   public JsonNode get(int index) {
      return index >= 0 && index < this._children.size() ? this._children.get(index) : null;
   }

   @Override
   public JsonNode get(String fieldName) {
      return null;
   }

   @Override
   public JsonNode path(String fieldName) {
      return MissingNode.getInstance();
   }

   @Override
   public JsonNode path(int index) {
      return (JsonNode)(index >= 0 && index < this._children.size() ? this._children.get(index) : MissingNode.getInstance());
   }

   @Override
   public boolean equals(Comparator<JsonNode> comparator, JsonNode o) {
      if (!(o instanceof ArrayNode)) {
         return false;
      } else {
         ArrayNode other = (ArrayNode)o;
         int len = this._children.size();
         if (other.size() != len) {
            return false;
         } else {
            List<JsonNode> l1 = this._children;
            List<JsonNode> l2 = other._children;

            for (int i = 0; i < len; i++) {
               if (!l1.get(i).equals(comparator, l2.get(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public void serialize(JsonGenerator f, SerializerProvider provider) throws IOException {
      List<JsonNode> c = this._children;
      int size = c.size();
      f.writeStartArray(size);

      for (int i = 0; i < size; i++) {
         JsonNode n = c.get(i);
         ((BaseJsonNode)n).serialize(f, provider);
      }

      f.writeEndArray();
   }

   @Override
   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
      WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(this, JsonToken.START_ARRAY));

      for (JsonNode n : this._children) {
         ((BaseJsonNode)n).serialize(g, provider);
      }

      typeSer.writeTypeSuffix(g, typeIdDef);
   }

   @Override
   public JsonNode findValue(String fieldName) {
      for (JsonNode node : this._children) {
         JsonNode value = node.findValue(fieldName);
         if (value != null) {
            return value;
         }
      }

      return null;
   }

   @Override
   public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
      for (JsonNode node : this._children) {
         foundSoFar = node.findValues(fieldName, foundSoFar);
      }

      return foundSoFar;
   }

   @Override
   public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
      for (JsonNode node : this._children) {
         foundSoFar = node.findValuesAsText(fieldName, foundSoFar);
      }

      return foundSoFar;
   }

   public ObjectNode findParent(String fieldName) {
      for (JsonNode node : this._children) {
         JsonNode parent = node.findParent(fieldName);
         if (parent != null) {
            return (ObjectNode)parent;
         }
      }

      return null;
   }

   @Override
   public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
      for (JsonNode node : this._children) {
         foundSoFar = node.findParents(fieldName, foundSoFar);
      }

      return foundSoFar;
   }

   public JsonNode set(int index, JsonNode value) {
      if (value == null) {
         value = this.nullNode();
      }

      if (index >= 0 && index < this._children.size()) {
         return this._children.set(index, value);
      } else {
         throw new IndexOutOfBoundsException("Illegal index " + index + ", array size " + this.size());
      }
   }

   public ArrayNode add(JsonNode value) {
      if (value == null) {
         value = this.nullNode();
      }

      this._add(value);
      return this;
   }

   public ArrayNode addAll(ArrayNode other) {
      this._children.addAll(other._children);
      return this;
   }

   public ArrayNode addAll(Collection<? extends JsonNode> nodes) {
      this._children.addAll(nodes);
      return this;
   }

   public ArrayNode insert(int index, JsonNode value) {
      if (value == null) {
         value = this.nullNode();
      }

      this._insert(index, value);
      return this;
   }

   public JsonNode remove(int index) {
      return index >= 0 && index < this._children.size() ? this._children.remove(index) : null;
   }

   public ArrayNode removeAll() {
      this._children.clear();
      return this;
   }

   public ArrayNode addArray() {
      ArrayNode n = this.arrayNode();
      this._add(n);
      return n;
   }

   public ObjectNode addObject() {
      ObjectNode n = this.objectNode();
      this._add(n);
      return n;
   }

   public ArrayNode addPOJO(Object value) {
      if (value == null) {
         this.addNull();
      } else {
         this._add(this.pojoNode(value));
      }

      return this;
   }

   public ArrayNode addRawValue(RawValue raw) {
      if (raw == null) {
         this.addNull();
      } else {
         this._add(this.rawValueNode(raw));
      }

      return this;
   }

   public ArrayNode addNull() {
      this._add(this.nullNode());
      return this;
   }

   public ArrayNode add(int v) {
      this._add(this.numberNode(v));
      return this;
   }

   public ArrayNode add(Integer value) {
      return value == null ? this.addNull() : this._add(this.numberNode(value));
   }

   public ArrayNode add(long v) {
      return this._add(this.numberNode(v));
   }

   public ArrayNode add(Long value) {
      return value == null ? this.addNull() : this._add(this.numberNode(value));
   }

   public ArrayNode add(float v) {
      return this._add(this.numberNode(v));
   }

   public ArrayNode add(Float value) {
      return value == null ? this.addNull() : this._add(this.numberNode(value));
   }

   public ArrayNode add(double v) {
      return this._add(this.numberNode(v));
   }

   public ArrayNode add(Double value) {
      return value == null ? this.addNull() : this._add(this.numberNode(value));
   }

   public ArrayNode add(BigDecimal v) {
      return v == null ? this.addNull() : this._add(this.numberNode(v));
   }

   public ArrayNode add(BigInteger v) {
      return v == null ? this.addNull() : this._add(this.numberNode(v));
   }

   public ArrayNode add(String v) {
      return v == null ? this.addNull() : this._add(this.textNode(v));
   }

   public ArrayNode add(boolean v) {
      return this._add(this.booleanNode(v));
   }

   public ArrayNode add(Boolean value) {
      return value == null ? this.addNull() : this._add(this.booleanNode(value));
   }

   public ArrayNode add(byte[] v) {
      return v == null ? this.addNull() : this._add(this.binaryNode(v));
   }

   public ArrayNode insertArray(int index) {
      ArrayNode n = this.arrayNode();
      this._insert(index, n);
      return n;
   }

   public ObjectNode insertObject(int index) {
      ObjectNode n = this.objectNode();
      this._insert(index, n);
      return n;
   }

   public ArrayNode insertPOJO(int index, Object value) {
      return value == null ? this.insertNull(index) : this._insert(index, this.pojoNode(value));
   }

   public ArrayNode insertNull(int index) {
      this._insert(index, this.nullNode());
      return this;
   }

   public ArrayNode insert(int index, int v) {
      this._insert(index, this.numberNode(v));
      return this;
   }

   public ArrayNode insert(int index, Integer value) {
      if (value == null) {
         this.insertNull(index);
      } else {
         this._insert(index, this.numberNode(value));
      }

      return this;
   }

   public ArrayNode insert(int index, long v) {
      return this._insert(index, this.numberNode(v));
   }

   public ArrayNode insert(int index, Long value) {
      return value == null ? this.insertNull(index) : this._insert(index, this.numberNode(value));
   }

   public ArrayNode insert(int index, float v) {
      return this._insert(index, this.numberNode(v));
   }

   public ArrayNode insert(int index, Float value) {
      return value == null ? this.insertNull(index) : this._insert(index, this.numberNode(value));
   }

   public ArrayNode insert(int index, double v) {
      return this._insert(index, this.numberNode(v));
   }

   public ArrayNode insert(int index, Double value) {
      return value == null ? this.insertNull(index) : this._insert(index, this.numberNode(value));
   }

   public ArrayNode insert(int index, BigDecimal v) {
      return v == null ? this.insertNull(index) : this._insert(index, this.numberNode(v));
   }

   public ArrayNode insert(int index, BigInteger v) {
      return v == null ? this.insertNull(index) : this._insert(index, this.numberNode(v));
   }

   public ArrayNode insert(int index, String v) {
      return v == null ? this.insertNull(index) : this._insert(index, this.textNode(v));
   }

   public ArrayNode insert(int index, boolean v) {
      return this._insert(index, this.booleanNode(v));
   }

   public ArrayNode insert(int index, Boolean value) {
      return value == null ? this.insertNull(index) : this._insert(index, this.booleanNode(value));
   }

   public ArrayNode insert(int index, byte[] v) {
      return v == null ? this.insertNull(index) : this._insert(index, this.binaryNode(v));
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else {
         return o instanceof ArrayNode ? this._children.equals(((ArrayNode)o)._children) : false;
      }
   }

   protected boolean _childrenEqual(ArrayNode other) {
      return this._children.equals(other._children);
   }

   @Override
   public int hashCode() {
      return this._children.hashCode();
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(16 + (this.size() << 4));
      sb.append('[');
      int i = 0;

      for (int len = this._children.size(); i < len; i++) {
         if (i > 0) {
            sb.append(',');
         }

         sb.append(this._children.get(i).toString());
      }

      sb.append(']');
      return sb.toString();
   }

   protected ArrayNode _add(JsonNode node) {
      this._children.add(node);
      return this;
   }

   protected ArrayNode _insert(int index, JsonNode node) {
      if (index < 0) {
         this._children.add(0, node);
      } else if (index >= this._children.size()) {
         this._children.add(node);
      } else {
         this._children.add(index, node);
      }

      return this;
   }
}
