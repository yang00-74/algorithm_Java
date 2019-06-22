package Others;

import java.util.LinkedList;

public class StackFromLinkList {

	/** 
	 * 集合类模拟数据结构栈/队列
	 * 
	 */
	private LinkedList link;
	public StackFromLinkList(){
		  link=new LinkedList();
	}
	public void add(Object obj){
		link.addFirst(obj);
		
	}
	
	public Object get(){
		
		//return link.removeFirst();//弹出头结点,模拟栈，先进后出
		return link.removeLast();//弹出尾部结点，模拟队列，先进先出
	}
	public boolean isEmpty(){
		return link.isEmpty();
	}
	public static void main(String[] args) {
            StackFromLinkList m=new StackFromLinkList();
            m.add("hello");
            m.add("you");  
            m.add("are");
            while(!m.isEmpty()){
            	System.out.println(m.get());
            }
          
	}

}
