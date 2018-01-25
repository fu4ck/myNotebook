package com.me.map.hashmap;


public class MyHashMap<K, V> implements MyMap<K, V>{
	
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; //��ʼ����
	static final float DEFAULT_LOAD_FACTOR = 0.75f; //�������ӣ��������ﵽ0.75ʱ����
	
	private final float loadFactor;
	private final int initialCapacity;
	private int count = 0; 
	
	private Entry<K, V>[] table;
	
	public MyHashMap() {
		this(DEFAULT_LOAD_FACTOR, DEFAULT_INITIAL_CAPACITY);
	}
	
	
	
	public MyHashMap(float loadFactor, int initialCapacity) {
		this.loadFactor = loadFactor;
		this.initialCapacity = initialCapacity;
		this.table = new Entry[this.initialCapacity];
	}



	@Override
	public V get(K key) {
		
//		if(key == null){
//			//TODO nullҲ������Ϊkey..
//		}
		
		int index = hash(key);
		Entry<K, V> node = table[index];
		if(node != null){
			while( node!=null && !node.key.equals(key)){
				node = node.next;
			}
			if(node != null){
				return node.value;
			}
			
		}else{
			return null;
		}
		
		return null;
	}

	@Override
	public V put(K key, V value) {
		
//		if(key == null){
//			//TODO nullҲ������Ϊkey..
//		}
		
		int index = hash(key);
		if(table[index] == null){ //no conflict
			table[index] = new Entry<K,V>(key, value);
			count++;
		}else{//conflict
			Entry<K, V> node = table[index];
			
			//��������е�key�Ƿ��ظ�
			//TODO ����д������
			if(node.key.equals(key)){
				V oldValue = node.value;
				node.value = value;
				return oldValue;//���ؾ�ֵ
			}
			
			while(node.next != null){
				
				//��������е�key�Ƿ��ظ�
				if(node.key.equals(key)){
					V oldValue = node.value;
					node.value = value;
					return oldValue;//���ؾ�ֵ
				}
				
				node = node.next;//û��key�ظ�������һ���ڵ�
			}
			node.next = new Entry<K, V>(key, value);
			count++;
		}
		
		//����Ƿ���Ҫ����
		//TODO
		
		return null; //û��key�ظ�����null
	}
	
	private int hash(K key){
		int index = key.hashCode()%table.length; 
		return index = Math.abs(index);//������hashcode�����������ֵ�������п���Ϊ����
	}

	@Override
	public int size() {
		return count;
	}
	
	static class Entry<K,V> implements MyMap.Entry<K, V>{

		K key;
		V value;
		Entry<K, V> next;
		
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
	}
	
	public static void main(String[] args) {
		MyMap<String, String> map = new MyHashMap<>();
		map.put("bacon", "yummy");
		map.put("bacon", "disgusting");
		map.put("cheese", "great!");
		System.err.println(map.get("bacon"));
		System.err.println(map.get("cheese"));
	}
	
}
