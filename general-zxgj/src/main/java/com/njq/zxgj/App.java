package com.njq.zxgj;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	int a=16;
    	System.out.println("6".hashCode()&(a-1));
    	System.out.println("6".hashCode()%16);
//        System.out.println( "Hello World!" );
    }
}
