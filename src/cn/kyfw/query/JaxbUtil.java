package cn.kyfw.query;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Jaxb2工具类
 * @author zhuc
 * @create 2013-3-29 下午2:40:14
 */
public class JaxbUtil {
	/**
	 * JavaBean转换成xml
	 * 默认编码UTF-8
	 * @param obj
	 * @param writer
	 * @return
	 */
	public static String bean2Xml(Object obj) {
		return bean2Xml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String bean2Xml(Object obj, String encoding) {
		String result = null;
		if(obj==null) return "";
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
		
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * xml转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xml2Bean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * json转成javabean
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T json2Bean(String json, Class<T> cls){
		ObjectMapper mapper = new ObjectMapper();
		T o = null;
		try {
			o = mapper.readValue(json, cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	/**
	 * javabean转成json字符串
	 * @param obj
	 * @return
	 */
	public static <T> String bean2Json(T obj){
		ObjectMapper mapper = new ObjectMapper();
		StringWriter wr = new StringWriter();
		try {
			mapper.writeValue(wr, obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wr.toString();
	}
}