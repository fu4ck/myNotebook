
public class CanReviveObj {
	public static CanReviveObj obj;

	// �÷���ֻ�ᱻ����һ��
	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalizing CanReviveObj....");
		super.finalize();
		obj = this;
	}
	
	public static void main(String[] args) throws Exception{
		obj = new CanReviveObj();
		obj = null;  //�ɸ���
		System.out.println("��һ��GC");
		System.gc();
		Thread.sleep(1000);
		System.out.println("obj is "+obj);
		System.out.println("�ڶ���GC");
		obj = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println("obj is "+obj);
	}
	
}

