package org.simpleframework.xml.core;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ValidationTestCase;

public class MixTest extends ValidationTestCase {
   
   @Root
   private static class MixExample {
      
     // @ElementList
     // private List<Object> list;
      
     // @ElementMap
     // private Map<Object, Object> map;
      
      @Element
      private Calendar calendar;
      
      public MixExample() {
      //   this.list = new ArrayList();
       //  this.map = new HashMap();
      }
      
      private void setTime(Date date) {
         calendar = new GregorianCalendar();
         calendar.setTime(date);
      }
      
     // public void put(Object key, Object value) {
     //    map.put(key, value);
     // }
      
     // public Object get(int index) {
      //   return list.get(index);
     // }
      
     // public void add(Object object) {
      //   list.add(object);
      //}
   }
   
   @Root
   private static class Entry {
      
      @Attribute
      private String id;
      
      @Text
      private String text;
      
      public Entry() {
         super();
      }
      
      public Entry(String id, String text) {
         this.id = id;
         this.text = text;
      }
   }
   
   public void testMix() throws Exception {
      Serializer serializer = new Persister();
      MixExample example = new MixExample();
      StringWriter source = new StringWriter();
      
      example.setTime(new Date());
     // caleb.add("text");
     // caleb.add(1);
     // caleb.add(true);
     // caleb.add(new Entry("1", "caleb 1"));
     // caleb.add(new Entry("2", "caleb 2"));
     // caleb.put(new Entry("1", "key 1"), new Entry("1", "value 1"));
     // caleb.put("key 2", "value 2");
     // caleb.put("key 3", 3);
     // caleb.put("key 4", new Entry("4", "value 4"));
      
      serializer.write(example, System.out);
      serializer.write(example, source);   
      serializer.validate(MixExample.class, source.toString());
      
      MixExample other = serializer.read(MixExample.class, source.toString());
      
      serializer.write(other, System.out);
      
     // assertEquals(caleb.get(0), "text");
     // assertEquals(caleb.get(1), 1);
     // assertEquals(caleb.get(2), true);
      
      validate(example, serializer);
   }

}
