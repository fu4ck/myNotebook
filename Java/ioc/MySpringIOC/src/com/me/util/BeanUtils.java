package com.me.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanUtils {
	/**
	 * ����bean�������������ȡ���Ե�set����
	 * @param beanObj
	 * @param name
	 * @return
	 */
	public static Method getWriteMethod(Object beanObj,String name){
		//ʹ����ʡ����ʵ�ָ÷���---->���ڷ��䣬��������bean��һ��api
		
		Method writeMethod = null;
		
			try {
				//����Bean����-->BeanInfo
				BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
				//�������������
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				//��������
				if(propertyDescriptors!=null){
					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						//�ж��Ƿ���������Ҫ�ķ���
						String pName =	propertyDescriptor.getName();//Ѱ������
						if(name.equals(pName)){
							//�ҵ���
							writeMethod = propertyDescriptor.getWriteMethod();//��Ӧ���Ե�set����
						}
						
					}
				}
				
				//�����ҵ���set����
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(writeMethod == null){
				//û�ҵ�
				throw new RuntimeException("û�ж�Ӧ��set������");
			}
		return writeMethod;
	}
}
