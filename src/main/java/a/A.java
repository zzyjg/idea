package a;

import java.io.File;

public class A {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	File[] a=	File.listRoots();
	for (File file : a) {
		System.out.println(file.getAbsolutePath());
	}
	}
}
