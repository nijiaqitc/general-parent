package com.njq.junit;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * @author: nijiaqi
 * @date: 2019/12/12
 */
public class Test1 {
    public static void main(String[] args) {
        char[] charArray = new char[]{'a','b','c','d','e'};
        CharBuffer buffer = CharBuffer.wrap(charArray);
//        buffer.reset();
        System.out.println("a capacity()="+buffer.capacity()+" limit()="+buffer.limit());
//        buffer.limit(2);
        System.out.println("a capacity()="+buffer.capacity()+" limit()="+buffer.limit());
        buffer.put(0,'o');
        buffer.put(1,'p');
        buffer.put(2,'q');
        buffer.put(3,'r');
        buffer.put(4,'s');
        System.out.println("--------:"+buffer.arrayOffset());
        buffer.put(5,'u');
        buffer.put(6,'v');
        System.out.println(buffer.get(0));
        Arrays.asList(buffer.array()).stream().forEach(n-> System.out.println(n));

        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        ByteBuffer.allocateDirect(100).slice()
//        byteBuffer1.put
    }
}
