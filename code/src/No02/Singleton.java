package No02;

/** ---------------------------------------------------单线程情况下--------------------------------------------------- **/

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;

/**
 * 实现 Singleton 模式
 */
public class Singleton {
    public static void main(String[] args) throws Exception{
        Constructor<?> declaredConstructor1 = Class.forName("No02.I").getDeclaredConstructor();
        declaredConstructor1.setAccessible(true);
        System.out.println(declaredConstructor1.newInstance());
        System.out.println(declaredConstructor1.newInstance());
        System.out.println(I.getInstance());
        System.out.println(I.getInstance());

        System.out.println("------");
        System.out.println(A.getInstance());
        System.out.println(A.getInstance());
        Class<?> classA = Class.forName("No02.A");
        Constructor<?>[] declaredConstructors = classA.getDeclaredConstructors();
        AccessibleObject.setAccessible(declaredConstructors,true);//禁用安全检查
        for (Constructor constructor:declaredConstructors){
            A o = (A) constructor.newInstance();
            System.out.println(o);
        }
        Constructor<?> declaredConstructor = classA.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);//禁用安全检查
        System.out.println(declaredConstructor.newInstance());
    }
}

/**
 * 饿汉模式 不管实例为不为null，先创建
 */
class A{
    private static A instance = new A();
    private A(){}
    public static A getInstance(){
        return instance;
    }
}
/**
 * 懒汉模式 实例为null时，才创建实例
 */
class B{
    private static B instance;
    private B(){}
    public static B getInstance(){
        if(instance == null){
            instance = new B();
        }
        return instance;
    }
}
/** ---------------------------------------------------多线程情况下--------------------------------------------------- **/

/**
 * 同步代码块 synchronized 相比同步方法颗粒度更细，减少加锁耗时
 */
class C{
    private static C instance;
    private C(){}
    public static C getInstance(){
        synchronized (C.class){
            if (instance == null){
                instance = new C();
            }
        }
        return instance;
    }
}

/**
 * 上一个代码中每次执行操作都加锁耗时，改为首次加载时需要加锁，后续不加锁
 * 双重检查锁模式
 * 指令重排可能导致空指针
 * 如果构造函数中操作比较多时，为了提升效率，JVM 会在构造函数里面的属性未全部完成实例化时，就返回对象。
 * 双重检测锁出现空指针问题的原因就是出现在这里，当某个线程获取锁进行实例化时，其他线程就直接获取实例使用，
 * 由于JVM指令重排序的原因，其他线程获取的对象也许不是一个完整的对象，
 * 所以在使用实例的时候就会出现空指针异常问题
 */
class D{
    private static D instance;
    private D(){
//        int x = 10;
//        int y = 20;
//        Object z = new Object();
    }
    public static D getInstance(){
        if (instance == null){
            synchronized (D.class){
                if (instance == null){
                    instance = new D();
                }
            }
        }
        return instance;
    }
}

/**
 * 双重检查的改进版
 * volatile关键字严格遵循happens-before原则，即在读操作前，写操作必须全部完成
 * 添加volatile关键字之后的双重检查锁模式是一种比较好的单例实现模式，能够保证在多线程的情况下线程安全也不会有性能问题
 */
class E{
    private static volatile E instance;
    private E(){}
    public static E getInstance(){
        if (instance == null){
            synchronized (D.class){
                if (instance == null){
                    instance = new E();
                }
            }
        }
        return instance;
    }
}

/**
 * 静态代码块只会在用到该类的时候（类加载，调用了静态方法等）被调用唯一的一次
 */
class F{
    private static F instance;
    private F(){}
    static {
        instance = new F();
    }
    public static F getInstance(){
        return instance;
    }
    public static void func(){}
}

/**
 * 上面的实现种，如果有其他的静态方法（比如上面的func()），我们调用它就会导致静态代码块被执行，实例也随之创建
 * 使用一个静态类来创建Singleton，其他静态方法只要没有调用Single.instance就不会创建Singleton
 * 实现了需要时才创建实例对象，避免过早创建
 */
class G{
    private G(){}
    private static class Single{
        private static G instance;
        static {
            instance = new G();
        }
    }
    public static G getInstance(){
        return Single.instance;
    }
}

/**
 * 枚举类（推荐）
 * 单元素的枚举类型已经成为实现Singleton的最佳方法
 */
class H{
    private H(){}
    static enum Singleton{
        INSTANCE;
        private H instance;
        private Singleton(){
            instance = new H();
        }
        public H getInstance(){
            return instance;
        }
    }
    public static H getInstance(){
        return Singleton.INSTANCE.getInstance();
    }
}

/**
 *  一般来说, 一个类实现了 Serializable接口, 我们就可以把它往内存地写再从内存里读出而"组装"成一个跟原来一模一样的对象.
 *  不过当序列化遇到单例时,这里边就有了个问题: 从内存读出而组装的对象破坏了单例的规则.
 *  单例是要求一个JVM中只有一个类对象的, 而现在通过反序列化,一个新的对象克隆了出来
 *  把 Singleton对象(通过getInstance方法获得的那个单例对象)序列化后再从内存中读出时, 就有一个全新但跟原来一样的Singleton对象存在了
 *
 *
 * 破坏单例模式的方法及解决办法
 * 1、除枚举方式外, 其他方法都会通过反射的方式破坏单例,反射是通过调用构造方法生成新的对象，
 * 所以如果我们想要阻止单例破坏，可以在构造方法中进行判断，若已有实例, 则阻止生成新的实例，解决办法如下:
 *
 * private SingletonObject1(){
 *     if (instance !=null){
 *         throw new RuntimeException("实例已经存在，请通过 getInstance()方法获取");
 *     }
 * }
 * 2、如果单例类实现了序列化接口Serializable, 就可以通过反序列化破坏单例，
 * 所以我们可以不实现序列化接口,如果非得实现序列化接口，可以重写反序列化方法readResolve(), 反序列化时直接返回相关单例对象。
 * 当JVM从内存中反序列化地"组装"一个新对象时,就会自动调用这个 readResolve方法来返回我们指定好的对象了, 单例规则也就得到了保证
 *
 * public Object readResolve() throws ObjectStreamException {
 *         return instance;
 *     }
 */
class I implements Serializable {
    private static volatile I instance;
    private I(){
        if (instance !=null){
            throw new RuntimeException("实例已经存在，请通过 getInstance()方法获取");
        }
    }
    public static I getInstance(){
        if (instance == null){
            synchronized (D.class){
                if (instance == null){
                    instance = new I();
                }
            }
        }
        return instance;
    }
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }
}