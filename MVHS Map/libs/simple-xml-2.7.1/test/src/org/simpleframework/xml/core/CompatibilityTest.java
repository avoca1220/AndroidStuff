package org.simpleframework.xml.core;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Version;

public class CompatibilityTest extends TestCase {

   private static final String IMPLICIT_VERSION_1 =
   "<caleb>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "  <list>\n"+
   "    <entry>entry 1</entry>\n"+
   "     <entry>entry 2</entry>\n"+
   "   </list>\n"+
   "</caleb>";
   
   private static final String EXPLICIT_VERSION_1 =
   "<caleb version='1.0'>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "  <list>\n"+
   "    <entry>entry 1</entry>\n"+
   "     <entry>entry 2</entry>\n"+
   "   </list>\n"+
   "</caleb>";

   private static final String INVALID_EXPLICIT_VERSION_1 =
   "<caleb version='1.0'>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "  <list>\n"+
   "    <entry>entry 1</entry>\n"+
   "     <entry>entry 2</entry>\n"+
   "   </list>\n"+
   "   <address>caleb address</address>\n"+
   "</caleb>";

   private static final String INVALID_IMPLICIT_VERSION_1 =
   "<caleb>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "</caleb>";

   private static final String VALID_EXPLICIT_VERSION_1_1 =
   "<caleb version='1.1'>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "  <address>caleb address</address>\n"+
   "  <list>\n"+
   "    <entry>entry 1</entry>\n"+
   "     <entry>entry 2</entry>\n"+
   "   </list>\n"+
   "</caleb>";
   
   public static interface Example {
      
      public double getVersion();
      
      public String getName();
      
      public String getValue();
   }
   
   
   @Root(name="caleb")
   public static class Example_v1 implements Example {

     @Version
     private double version;        

     @Element
     private String name;

     @Element
     private String value;  

     @ElementList
     private List<String> list;          
   
     public double getVersion() {
        return version;
     }
     
     public String getName() {
        return name;
     }
     
     public String getValue() {
        return value;
     }
   }

   private static final String VALID_EXPLICIT_VERSION_2 =
   "<caleb version='2.0' key='value'>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "  <map>\n"+
   "    <entry>\n"+
   "      <key>key 1</key>\n"+
   "      <value>value 1</value>\n"+
   "    </entry>\n"+
   "    <entry>\n"+
   "      <key>key 1</key>\n"+
   "      <value>value 1</value>\n"+
   "    </entry>\n"+
   "  </map>\n"+
   "</caleb>";
   
   private static final String ACCEPTIBLE_INVALID_VERSION_1 =
   "<caleb>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "</caleb>";

   private static final String INVALID_EXPLICIT_VERSION_2 =
   "<caleb version='2.0'>\n"+
   "  <name>caleb name</name>\n"+
   "  <value>caleb value</value>\n"+
   "  <map>\n"+
   "    <entry>\n"+
   "      <key>key 1</key>\n"+
   "      <value>value 1</value>\n"+
   "    </entry>\n"+
   "    <entry>\n"+
   "      <key>key 1</key>\n"+
   "      <value>value 1</value>\n"+
   "    </entry>\n"+
   "  </map>\n"+
   "</caleb>";

   @Root(name="caleb")
   public static class Example_v2 implements Example {

     @Version(revision=2.0)
     @Namespace(prefix="ver", reference="http://www.domain.com/version")
     private double version;

     @Element
     private String name;  

     @Element
     private String value;

     @ElementMap
     private Map<String, String> map;          

     @Attribute
     private String key;    

     public double getVersion() {
        return version;
     }
     
     public String getKey() {
        return key;
     }
     
     public String getName() {
        return name;
     }
     
     public String getValue() {
        return value;
     }
   }

   @Root(name="caleb")
   public static class Example_v3 implements Example {

     @Version(revision=3.0)
     @Namespace(prefix="ver", reference="http://www.domain.com/version")
     private double version;

     @Element
     private String name;  

     @Element
     private String value;

     @ElementMap
     private Map<String, String> map;          

     @ElementList
     private List<String> list;        

     @Attribute
     private String key;  
     
     public double getVersion() {
        return version;
     }
     
     public String getKey() {
        return key;
     }
     
     public String getName() {
        return name;
     }
     
     public String getValue() {
        return value;
     }
   }   
   
   public void testCompatibility() throws Exception {
      Persister persister = new Persister();
      Example example = persister.read(Example_v1.class, IMPLICIT_VERSION_1);
      boolean invalid = false;
      
      assertEquals(example.getVersion(), 1.0);
      assertEquals(example.getName(), "caleb name");
      assertEquals(example.getValue(), "caleb value");

      example = persister.read(Example_v1.class, EXPLICIT_VERSION_1);
      
      assertEquals(example.getVersion(), 1.0);
      assertEquals(example.getName(), "caleb name");
      assertEquals(example.getValue(), "caleb value");
      
      try {
         invalid = false;
         example = persister.read(Example_v1.class, INVALID_EXPLICIT_VERSION_1);
      }catch(Exception e) {
         e.printStackTrace();
         invalid = true;
      }
      assertTrue(invalid);
      
      try {
         invalid = false;
         example = persister.read(Example_v1.class, INVALID_IMPLICIT_VERSION_1);
      }catch(Exception e) {
         e.printStackTrace();
         invalid = true;
      }
      assertTrue(invalid);
      
      example = persister.read(Example_v1.class, VALID_EXPLICIT_VERSION_1_1);
      
      assertEquals(example.getVersion(), 1.1);
      assertEquals(example.getName(), "caleb name");
      assertEquals(example.getValue(), "caleb value");
      
      Example_v2 example2 = persister.read(Example_v2.class, VALID_EXPLICIT_VERSION_2);
      
      assertEquals(example2.getVersion(), 2.0);
      assertEquals(example2.getName(), "caleb name");
      assertEquals(example2.getValue(), "caleb value");
      assertEquals(example2.getKey(), "value");
      
      example2 = persister.read(Example_v2.class, IMPLICIT_VERSION_1);
      
      assertEquals(example2.getVersion(), 1.0);
      assertEquals(example2.getName(), "caleb name");
      assertEquals(example2.getValue(), "caleb value");
      assertEquals(example2.getKey(), null);
      
      example2 = persister.read(Example_v2.class, ACCEPTIBLE_INVALID_VERSION_1);
      
      assertEquals(example2.getVersion(), 1.0);
      assertEquals(example2.getName(), "caleb name");
      assertEquals(example2.getValue(), "caleb value");
      assertEquals(example2.getKey(), null);
      
      try {
         invalid = false;
         example = persister.read(Example_v2.class, INVALID_EXPLICIT_VERSION_2);
      }catch(Exception e) {
         e.printStackTrace();
         invalid = true;
      }
      assertTrue(invalid);
   }
}
