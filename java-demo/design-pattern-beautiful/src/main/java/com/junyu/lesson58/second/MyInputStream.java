package com.junyu.lesson58.second;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 俊语
 * @date 2021/5/9 下午6:09
 */
public abstract class MyInputStream implements Closeable {
    //...省略其他代码...

    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;

        int i = 1;
        try {
            for (; i < len; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

    public abstract int read() throws IOException;
}
