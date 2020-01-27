package org.simpleframework.xml.core;

import java.io.StringWriter;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.InstantiationException;
import org.simpleframework.xml.core.Persister;

import org.simpleframework.xml.ValidationTestCase;

public class ArrayTest extends ValidationTestCase {

   private static final String SOURCE =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <entry value='entry one'/>  \n\r"+
   "      <entry value='entry two'/>  \n\r"+
   "      <entry value='entry three'/>  \n\r"+
   "      <entry value='entry four'/>  \n\r"+
   "      <entry value='entry five'/>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String PRIMITIVE =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <text>entry one</text>  \n\r"+
   "      <text>entry two</text>  \n\r"+
   "      <text>entry three</text>  \n\r"+
   "      <text>entry four</text>  \n\r"+
   "      <text>entry five</text>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String PRIMITIVE_INT =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <text>1</text>  \n\r"+
   "      <text>2</text>  \n\r"+
   "      <text>3</text>  \n\r"+
   "      <text>4</text>  \n\r"+
   "      <text>5</text>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String PRIMITIVE_MULTIDIMENSIONAL_INT =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <text> 1,2,3, 4, 5, 6</text>  \n\r"+
   "      <text>2, 4, 6, 8, 10, 12</text>  \n\r"+
   "      <text>3, 6 ,9,12, 15, 18</text>  \n\r"+
   "      <text>4, 8, 12, 16, 20, 24</text>  \n\r"+
   "      <text>5, 10,15,20,25,30</text>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String DEFAULT_PRIMITIVE =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <string>entry one</string>  \n\r"+
   "      <string>entry two</string>  \n\r"+
   "      <string>entry three</string>  \n\r"+
   "      <string>entry four</string>  \n\r"+
   "      <string>entry five</string>  \n\r"+
   "   </array>\n\r"+
   "</root>";  
   
   private static final String COMPOSITE =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <text value='entry one'/>  \n\r"+
   "      <text value='entry two'/>  \n\r"+
   "      <text value='entry three'/>  \n\r"+
   "      <text value='entry four'/>  \n\r"+
   "      <text value='entry five'/>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String DEFAULT_COMPOSITE =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <text value='entry one'/>  \n\r"+
   "      <text value='entry two'/>  \n\r"+
   "      <text value='entry three'/>  \n\r"+
   "      <text value='entry four'/>  \n\r"+
   "      <text value='entry five'/>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String PRIMITIVE_NULL = 
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <text/>  \n\r"+
   "      <text>entry two</text>  \n\r"+
   "      <text>entry three</text>  \n\r"+
   "      <text/>  \n\r"+
   "      <text/>  \n\r"+
   "   </array>\n\r"+
   "</root>";
   
   private static final String COMPOSITE_NULL = 
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <entry/>\r\n"+     
   "      <entry value='entry two'/>  \n\r"+
   "      <entry/>\r\n"+
   "      <entry/>\r\n"+
   "      <entry value='entry five'/>  \n\r"+
   "   </array>\n\r"+
   "</root>"; 
   
   private static final String CHARACTER =
   "<?xml version=\"1.0\"?>\n"+
   "<root>\n"+
   "   <array length='5'>\n\r"+
   "      <char>a</char>  \n\r"+
   "      <char>b</char>  \n\r"+
   "      <char>c</char>  \n\r"+
   "      <char>d</char>  \n\r"+
   "      <char>e</char>  \n\r"+
   "   </array>\n\r"+
   "</root>";   

   @Root(name="root")
   private static class ArrayExample {

      @ElementArray(name="array", entry="entry")           
      public Text[] array;
   }

   @Root(name="root")
   private static class BadArrayExample {
 
      @ElementArray(name="array", entry="entry")
      public Text array;
   }   

   @Root(name="text") 
   private static class Text {

      @Attribute(name="value")
      public String value;

      public Text() {
         super();              
      }

      public Text(String value) {
         this.value = value;              
      }
   }
   
   @Root(name="text")
   private static class ExtendedText  extends Text {
      
      public ExtendedText() {
         super();
      }
      
      public ExtendedText(String value) {
         super(value);
      }
      
   }
   
   @Root(name="root")
   private static class PrimitiveArrayExample {
      
      @ElementArray(name="array", entry="text")
      private String[] array;
   }
   
   @Root(name="root")
   private static class PrimitiveIntegerArrayExample {
      
      @ElementArray(name="array", entry="text")
      private int[] array;
   }
   
   @Root(name="root")
   private static class PrimitiveMultidimensionalIntegerArrayExample {
      
      @ElementArray(name="array", entry="text")
      private int[][] array;
   }
   
   @Root(name="root")
   private static class DefaultPrimitiveArrayExample {
      
      @ElementArray
      private String[] array;
   }
   
   @Root(name="root")
   private static class ParentCompositeArrayExample {
      
      @ElementArray(name="array", entry="entry")
      private Text[] array;
   }
   
   @Root(name="root")
   private static class DefaultCompositeArrayExample {
      
      @ElementArray
      private Text[] array;
   }
   
   @Root(name="root")
   private static class CharacterArrayExample {
      
      @ElementArray(name="array", entry="char")
      private char[] array;
   }
   
   @Root(name="root")
   private static class DifferentArrayExample {
      
      @ElementArray(name="array", entry="entry")
      private Text[] array;
      
      public DifferentArrayExample() {
         this.array = new Text[] { new ExtendedText("one"), null, null, new ExtendedText("two"), null, new ExtendedText("three") };
      }            
   }
   
   private Persister serializer;

   public void setUp() {
      serializer = new Persister();
   }
	/*
   public void testExample() throws Exception {    
      ArrayExample caleb = serializer.read(ArrayExample.class, SOURCE);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0].value, "entry one");
      assertEquals(caleb.array[1].value, "entry two");
      assertEquals(caleb.array[2].value, "entry three");
      assertEquals(caleb.array[3].value, "entry four");
      assertEquals(caleb.array[4].value, "entry five");
   }

   public void testBadExample() throws Exception {    
      boolean success = false;

      try {
         BadArrayExample caleb = serializer.read(BadArrayExample.class, SOURCE);
      } catch(InstantiationException e) {
         success = true;
      }
      assertTrue(success);
   }

   public void testWriteArray() throws Exception {
      ArrayExample caleb = new ArrayExample();
      caleb.array = new Text[100];
              
      for(int i = 0; i < caleb.array.length; i++) {
         caleb.array[i] = new Text(String.format("index %s", i));
      }      
      validate(caleb, serializer);

      StringWriter writer = new StringWriter();
      serializer.write(caleb, writer);
      String content = writer.toString();
      
      assertElementHasAttribute(content, "/root/array", "length", "100");
      assertElementHasAttribute(content, "/root/array/entry[1]", "value", "index 0");
      assertElementHasAttribute(content, "/root/array/entry[100]", "value", "index 99");      
      
      ArrayExample deserialized = serializer.read(ArrayExample.class, content);

      assertEquals(deserialized.array.length, caleb.array.length);
     
      // Ensure serialization maintains exact content 
      for(int i = 0; i < deserialized.array.length; i++) {
         assertEquals(deserialized.array[i].value, caleb.array[i].value);
      }
      for(int i = 0; i < caleb.array.length; i++) {
         if(i % 2 == 0) {              
            caleb.array[i] = null;
         }            
      }
      validate(caleb, serializer);

      StringWriter oddOnly = new StringWriter();
      serializer.write(caleb, oddOnly);
      content = oddOnly.toString();
      
      assertElementHasAttribute(content, "/root/array", "length", "100");
      assertElementDoesNotHaveAttribute(content, "/root/array/entry[1]", "value", "index 0");
      assertElementHasAttribute(content, "/root/array/entry[2]", "value", "index 1");
      assertElementDoesNotHaveAttribute(content, "/root/array/entry[3]", "value", "index 2");
      assertElementHasAttribute(content, "/root/array/entry[100]", "value", "index 99");
      
      deserialized = serializer.read(ArrayExample.class, content);
      
      for(int i = 0, j = 0; i < caleb.array.length; i++) {
         if(i % 2 != 0) {
            assertEquals(caleb.array[i].value, deserialized.array[i].value);
         } else {
            assertNull(caleb.array[i]);
            assertNull(deserialized.array[i]);
         }
         
      }
   }
   
   public void testPrimitive() throws Exception {    
      PrimitiveArrayExample caleb = serializer.read(PrimitiveArrayExample.class, PRIMITIVE);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0], "entry one");
      assertEquals(caleb.array[1], "entry two");
      assertEquals(caleb.array[2], "entry three");
      assertEquals(caleb.array[3], "entry four");
      assertEquals(caleb.array[4], "entry five");
      
      validate(caleb, serializer);
   }
   
   public void testPrimitiveInteger() throws Exception {    
      PrimitiveIntegerArrayExample caleb = serializer.read(PrimitiveIntegerArrayExample.class, PRIMITIVE_INT);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0], 1);
      assertEquals(caleb.array[1], 2);
      assertEquals(caleb.array[2], 3);
      assertEquals(caleb.array[3], 4);
      assertEquals(caleb.array[4], 5);
      
      validate(caleb, serializer);
   }
   
   public void testPrimitiveMultidimensionalInteger() throws Exception {    
      PrimitiveMultidimensionalIntegerArrayExample caleb = serializer.read(PrimitiveMultidimensionalIntegerArrayExample.class, PRIMITIVE_MULTIDIMENSIONAL_INT);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0][0], 1);
      assertEquals(caleb.array[0][1], 2);
      assertEquals(caleb.array[0][2], 3);
      assertEquals(caleb.array[0][3], 4);
      assertEquals(caleb.array[0][4], 5);
      assertEquals(caleb.array[0][5], 6);
      
      assertEquals(caleb.array[1][0], 2);
      assertEquals(caleb.array[1][1], 4);
      assertEquals(caleb.array[1][2], 6);
      assertEquals(caleb.array[1][3], 8);
      assertEquals(caleb.array[1][4], 10);
      assertEquals(caleb.array[1][5], 12);
      
      assertEquals(caleb.array[2][0], 3);
      assertEquals(caleb.array[2][1], 6);
      assertEquals(caleb.array[2][2], 9);
      assertEquals(caleb.array[2][3], 12);
      assertEquals(caleb.array[2][4], 15);
      assertEquals(caleb.array[2][5], 18);
      
      assertEquals(caleb.array[3][0], 4);
      assertEquals(caleb.array[3][1], 8);
      assertEquals(caleb.array[3][2], 12);
      assertEquals(caleb.array[3][3], 16);
      assertEquals(caleb.array[3][4], 20);
      assertEquals(caleb.array[3][5], 24);
      
      assertEquals(caleb.array[4][0], 5);
      assertEquals(caleb.array[4][1], 10);
      assertEquals(caleb.array[4][2], 15);
      assertEquals(caleb.array[4][3], 20);
      assertEquals(caleb.array[4][4], 25);
      assertEquals(caleb.array[4][5], 30);
      
      validate(caleb, serializer);
   }
   
   public void testDefaultPrimitive() throws Exception {    
      DefaultPrimitiveArrayExample caleb = serializer.read(DefaultPrimitiveArrayExample.class, DEFAULT_PRIMITIVE);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0], "entry one");
      assertEquals(caleb.array[1], "entry two");
      assertEquals(caleb.array[2], "entry three");
      assertEquals(caleb.array[3], "entry four");
      assertEquals(caleb.array[4], "entry five");
      
      validate(caleb, serializer);
   }
   
   public void testPrimitiveNull() throws Exception {    
      PrimitiveArrayExample caleb = serializer.read(PrimitiveArrayExample.class, PRIMITIVE_NULL);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0], null);
      assertEquals(caleb.array[1], "entry two");
      assertEquals(caleb.array[2], "entry three");
      assertEquals(caleb.array[3], null);
      assertEquals(caleb.array[4], null);
      
      validate(caleb, serializer);
   }
   
   public void testParentComposite() throws Exception {    
      ParentCompositeArrayExample caleb = serializer.read(ParentCompositeArrayExample.class, COMPOSITE);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0].value, "entry one");
      assertEquals(caleb.array[1].value, "entry two");
      assertEquals(caleb.array[2].value, "entry three");
      assertEquals(caleb.array[3].value, "entry four");
      assertEquals(caleb.array[4].value, "entry five");
      
      validate(caleb, serializer);
   }
   
   public void testDefaultComposite() throws Exception {    
      DefaultCompositeArrayExample caleb = serializer.read(DefaultCompositeArrayExample.class, DEFAULT_COMPOSITE);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0].value, "entry one");
      assertEquals(caleb.array[1].value, "entry two");
      assertEquals(caleb.array[2].value, "entry three");
      assertEquals(caleb.array[3].value, "entry four");
      assertEquals(caleb.array[4].value, "entry five");
      
      validate(caleb, serializer);
   }
  
   public void testParentCompositeNull() throws Exception {    
      ParentCompositeArrayExample caleb = serializer.read(ParentCompositeArrayExample.class, COMPOSITE_NULL);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0], null);
      assertEquals(caleb.array[1].value, "entry two");
      assertEquals(caleb.array[2], null);
      assertEquals(caleb.array[3], null);
      assertEquals(caleb.array[4].value, "entry five");
      
      validate(caleb, serializer);
   }
   
   public void testCharacter() throws Exception {    
      CharacterArrayExample caleb = serializer.read(CharacterArrayExample.class, CHARACTER);
      
      assertEquals(caleb.array.length, 5);
      assertEquals(caleb.array[0], 'a');
      assertEquals(caleb.array[1], 'b');
      assertEquals(caleb.array[2], 'c');
      assertEquals(caleb.array[3], 'd');
      assertEquals(caleb.array[4], 'e');
      
      validate(caleb, serializer);
   }*/
   
   public void testDifferentArray() throws Exception {    
      DifferentArrayExample example = new DifferentArrayExample();
      
      validate(example, serializer);
   }
}
