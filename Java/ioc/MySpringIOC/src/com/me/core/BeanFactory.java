package com.me.core;
/**
 * IOC��������ӿ�
 * @author Administrator
 *
 */
public interface BeanFactory {
	//����bean��id��ȡ����
	Object getBean(String id);
}
