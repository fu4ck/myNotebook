package com.me.config.parse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.me.config.Bean;
import com.me.config.Property;
/**
 * ��xml�ж�ȡBean��Ϣ
 * @author Administrator
 *
 */
public class XMLSourceReader implements SourceReader{
	/**
	 * ��ȡxml�����ļ�
	 * @param path
	 * @return
	 */
	public Map<String, Bean> getConfig(String path){
		
		
		Map<String, Bean> map = new HashMap<>();
		//dom4j����xml
			//����������
			SAXReader reader = new SAXReader();
			//���������ļ�=>document����
			InputStream is = XMLSourceReader.class.getResourceAsStream(path);
			Document document = null;
			try {
				document = reader.read(is);
			} catch (DocumentException e) {
				throw new RuntimeException("������������ó������˰�~");
			}
			//����xpath���ʽ��ȥ������beanԪ��
			String xpath = "//bean";
			//��beanԪ�ؽ��б���
			List<Element> list = document.selectNodes(xpath);
			if(list!=null){
				for (Element bean : list) {
					Bean b = new Bean();
					//��bean��name,class���Է�װ��bean������
					String name = bean.attributeValue("id");
					String className = bean.attributeValue("class");
					String scope = bean.attributeValue("scope");
					b.setName(name);
					b.setClassName(className);
					if(scope != null){
						b.setScope(scope);
					}
					
					//��ȡBean�µ�����propertyԪ��
					@SuppressWarnings("unchecked")
					List<Element> children = bean.elements("property");
					List<Property> properties = new ArrayList<>();
					if(children!=null){
						for (Element child : children) {
							Property p = new Property();
							properties.add(p);
							String cname = child.attributeValue("name");
							String value =child.attributeValue("value");
							String ref = child.attributeValue("ref");
							p.setName(cname);
							p.setValue(value);
							p.setRef(ref);
						}
					}
					b.setProperties(properties);
					map.put(name, b);
				}
			}
		return map;
	}
}
