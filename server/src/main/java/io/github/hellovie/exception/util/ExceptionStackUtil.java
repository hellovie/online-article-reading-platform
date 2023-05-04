package io.github.hellovie.exception.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 打印异常堆栈信息工具类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class ExceptionStackUtil {
    private ExceptionStackUtil() {
    }

    /**
     * 获取完整堆栈信息.
     *
     * @param throwable throwable.
     * @return 完整堆栈信息字符串.
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
