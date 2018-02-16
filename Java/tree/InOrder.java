package interview.tree;

public class InOrder {
	
	/**
	 * ��ȡ�����������һ���ڵ�
	 * @param node
	 * @return
	 */
	public static TreeNode<Character> next(TreeNode<Character> node){
		
		if(node == null){
			return null;
		}
		
		if(node.right != null){ 
			return first(node.right);
		}
		
		// һֱ��parent�ߣ�ֱ���ýڵ���parent�����ӻ�parentΪnull��֮�󷵻�parent
		while(node.parent!=null && node.parent.left != node){
			node = node.parent;
		}
		
		return node.parent;
	}
	
	// ������������������ĵ�һ���ڵ㣬һֱ����...
	public static TreeNode<Character> first(TreeNode<Character> node){
		
		if(node == null){
			return null;
		}
		
		while(node.left != null){
			node = node.left;
		}
		return node;
	}

}
