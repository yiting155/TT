package com.s2;

import com.s1.Student1;
import com.s1.Student2;

public class B extends Student1 implements Student2
{
	private int eng;
	
	public B(String name,int eng) {
		super(name);
		this.eng=eng;
		// TODO Auto-generated constructor stub 系統自己給的
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	@Override
	public void skill()
	{
		System.out.println("B skill");
		// TODO Auto-generated method stub
		
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return super.show()+"\t英文:"+eng;
	}



	

}
