package com.s3;

import com.s1.Student1;
import com.s1.Student2;
import com.s2.A;
import com.s2.B;

public class Add {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 系統給的
		Student1 s1=new 	Student1("s1");
		System.out.println(s1.show());
		
		s1.setName("s1null");
		System.out.println(s1.getName());
		
		Student2 s2=new A("a",74);
		s2.skill();
		
		Student1 s2_2=new A("a2",70);
		System.out.println(s2_2.show());
		//另一個寫法
		
		A s2_3=new A("a3",100);
		System.out.println(s2_3.show());
		
System.out.println("-------------------------------------------------------------");
		
		Student2 s3=new B("b",77);
		s3.skill();
		
		Student1 s3_2=new B("b2",70);
		System.out.println(s3_2.show());
		
		B s3_3=new B("b3",77);
		System.out.println(s3_3.show());

	}

}
