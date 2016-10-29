package com.stardust.tool;

import android.os.Environment;

import java.util.Stack;

public class Recorder {
	public static boolean isPC = false;
	static StringBuffer rec = new StringBuffer();
    static String error="";
    static Stack<String> errors=new Stack<String>();
	public Recorder() {
	};

	public static void rec(String str) {
		rec.append(str + "\n");
		if (isPC) {
			System.out.println(str);
		}
	}

	public static void rec(int i) {
		rec(String.valueOf(i));
	}

	public static void writeRec() {
		if (isPC) {
			System.out.println(rec.toString());
		} else {
			try {
				File fc = new File(Environment.getExternalStorageDirectory().getPath()+"/runrec.txt");
				if (fc.exists()) {
					fc.delete();
				}
				fc.createNewFile();
				FileOutputStream dos = new FileOutputStream(fc);
				dos.write(rec.toString().getBytes());
				dos.close();
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
	}

	public static String rec() {
		return rec.toString();
	}
	public static String getError(){
		String tmp=error;
		error="";
		return tmp;
	}
	public static void setError(String str){
		if(error.equals(""))error=str;
	}
	public static void pushError(String str){
		errors.push(str);
	}
	public static String popError(){
		return (String)errors.pop();
	}
	public static String getErrors(){
		String r="";
		while(!errors.isEmpty())r+=popError()+"\n";
		return r;
	}
}