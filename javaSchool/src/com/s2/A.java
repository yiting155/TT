package com.s2;

import com.s1.Student1;
import com.s1.Student2;
/**
 * <h3>學員管理系統</h3>
 * A班<br>
 * @version 1.0
 * @author teacher
 * 
 */
//封裝後，寫備註，/**--->打右邊那個，會自動帶入註解，<br>--->表示換行/ <h3>註標題</h3>
public class A extends Student1 implements Student2
{
	private int chi;
	/**
	 * 
	 * @param name 輸入名稱
	 * @param chi 輸入國文分數<br>
	  * Ex:<br>
	 * A a=new A("aa",74);
	 */
	public A(String name,int chi) 
	{
		super(name);
		this.chi=chi;
		// TODO Auto-generated constructor stub 系統自己給的
	}
	
	/**
	 * 
	 * @return 讀取國文分數
	 */
	public int getChi() {
		return chi;
	}
	
	/**
	 * 
	 * @param chi 輸入修改國文的分數<br>
	 * Ex:<br>
	 * a.setChi(100);
	 */
	public void setChi(int chi) {
		this.chi = chi;
	}
	
	
	@Override
	/**
	 *  test
	 */
	public void skill() 
	{
		System.out.println("A skill");
		// TODO Auto-generated method stub 系統自己給的
		
	}
	@Override
	/**
	 * @return 顯示內容
	 */
	public String show()
	{
		// TODO Auto-generated method stub 系統自己給的
		return super.show()+"\t國文:"+chi;
	}
	
	
	
}
