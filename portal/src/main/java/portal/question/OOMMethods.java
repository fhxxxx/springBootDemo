package portal.question;

import javassist.CannotCompileException;
import javassist.ClassPool;

import java.util.ArrayList;
import java.util.List;

/**
 * 会引起OOM的方法
 */
public class OOMMethods {

    public static void main(String[] args) {
        heapOOM();
    }

    //堆溢出，往数组里加数据
    public static void heapOOM() {
        List<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object());
        }
    }

    //栈溢出，递归调用
    public static void stackOOM() {
        stackOOM();
    }

    //元空间溢出，动态创建类
    public static void metaSpaceOOM() throws CannotCompileException {
        ClassPool classPool = new ClassPool();
        int i = 1;
        while (true){
            classPool.makeClass("classMetaSpace" + i).toClass();
        }

    }
}
