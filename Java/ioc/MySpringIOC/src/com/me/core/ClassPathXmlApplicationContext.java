package com.me.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.me.config.Bean;
import com.me.config.Property;
import com.me.config.parse.SourceReader;
import com.me.config.parse.XMLSourceReader;
import com.me.util.BeanUtils;

public class ClassPathXmlApplicationContext implements BeanFactory {

	private String configPath;
	protected SourceReader sourceReader;
	private Map<String, Bean> config;
	/*ʹ��hashmap��spring����*/
	private Map<String, Object> context = new HashMap<>();

	public ClassPathXmlApplicationContext(String path) {
		this.configPath = path;
		this.sourceReader = new XMLSourceReader();
		//��ȡbean������Ϣ
		config = sourceReader.getConfig(path);
		if(config!=null){
			for ( Entry< String, Bean> entry: config.entrySet()) {
				String name = entry.getKey();
				Bean bean = entry.getValue();
				
				Object existBean = context.get(name);
				if(existBean == null && bean.getScope().equals("singleton")){//���������ڸ�bean����scopeΪsingletonʱ�Ŵ���
					//����bean����bean����
					Object beanObj = createBean(bean);
					//��������
					context.put(name, beanObj);	
				}
				
			}
		}
	}
	private Object createBean(Bean bean) {
		String className = bean.getClassName();
		Class beanCls = null;
		Object beanObj = null;
		try {
			beanCls = Class.forName(className);
			//���bean����
			beanObj = beanCls.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(className+"���·�����԰ɣ�û�ҵ��������~");
		} catch (InstantiationException e) {
			throw new RuntimeException("�ղι��찡�����");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("������");
		}
		
		//��bean��������ע��
			if(bean.getProperties() != null){
				for (Property property : bean.getProperties()) {
					
					String name = property.getName();
					String value = property.getValue();
					String ref = property.getRef();
					
					//ʹ��Apache��BeanUtils������ɼ򵥵�����ע��
					if(value!=null){
						Map<String, String> paramMap = new HashMap<>();
						paramMap.put(name,value);
						try {
							//�ù�����������͵��Զ�ת��
							org.apache.commons.beanutils.BeanUtils.populate(beanObj, paramMap);
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
							throw new RuntimeException("���Բ����ˣ�");
						}
					}
					
//					
//					if(property.getValue()!=null){
//						//��ע�룬valueֱ��ע��
//						param =  property.getValue();
//					}
					
					if(property.getRef()!=null){
						//�鷳�����ö���ע��
						
						//��ȡset����
						Method setMethod = BeanUtils.getWriteMethod(beanObj,name);
						
						//�������в��ҵ�ǰҪע���Bean�Ƿ��Ѵ���
						Object existBean = context.get(property.getRef());
						if(existBean == null){//��singleton��bean����������
							//û����
							existBean = createBean(config.get(property.getRef()));//����bean
							//��bean�Ż�������
							if(config.get(property.getRef()).getScope().equals("singleton")){								
								context.put(property.getRef(),existBean);
							}
						}
						try {
							//����set�����������ע��
							setMethod.invoke(beanObj, existBean);	
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new RuntimeException("�Ǹ�...û�ж�Ӧ��set������");
						}
					}
				
				}
			}
		
		return beanObj;
	}

	@Override
	public Object getBean(String name) {
		
		Object bean = context.get(name);
		//���scopeΪprototype����context�ﻹû�����bean
		if(bean == null){//scopeΪprototype��bean����������������
			bean = createBean(config.get(name));
		}
		return bean;
	}

}
