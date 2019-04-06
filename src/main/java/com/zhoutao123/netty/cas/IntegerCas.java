package com.zhoutao123.netty.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/** 多线程并发CAS (自旋锁) */
public class IntegerCas {

  public static void main(String[] args) throws InterruptedException {
    Increment increment = new Increment(1);
    for (int i = 0; i < 50; i++) {
      new Thread(new MyRunnable(increment)).start();
    }
    // 等待所有线程执完成
    TimeUnit.SECONDS.sleep(4);
    System.out.println(increment.i);
  }

  static class MyRunnable implements Runnable {

    private Increment increment;

    public MyRunnable(Increment increment) {
      this.increment = increment;
    }

    @Override
    public void run() {
      increment.increment();
    }
  }

  static class Increment {

    // volatile 保证了线程可见性，但是没有保证按成安全性
    private volatile int i;

    // 声明CAS的抽象类
    private static AtomicIntegerFieldUpdater<Increment> updater;

    static {
      // 通过静态方法创建CAS的具体实例
      // 第一个参数表示维护的Integer所在的类的class
      // 第二个参数表示维护的Integer变量的名称
      updater = AtomicIntegerFieldUpdater.newUpdater(Increment.class, "i");
    }

    /**
     * 构造初始化函数
     *
     * @param i
     */
    public Increment(int i) {
      this.i = i;
    }

    /** 自增一个 */
    public void increment() {
      increment(1);
    }

    /**
     * 重载自增方法
     *
     * <p>该方法具体实现了CAS的代码逻辑，重在均在下面的for循环中
     *
     * @param increment
     */
    public void increment(int increment) {
      // 创建一个死循环，compareAndSet检查不通过的时候，重新获取新的值尝试更新
      for (; ; ) {
        // 保存当前线程更新的值的状态
        int oldI = this.i;
        final int newValue = oldI + increment;
        // 校验值是否更新完成
        if (newValue <= increment) {
          throw new IllegalArgumentException("非法的参数异常");
        }
        // 如果CAS更新完成，则退出循环，否则打印重试日志
        if (updater.compareAndSet(this, oldI, newValue)) {
          break;
        }
        System.out.println("发生多线程并发,CAS正常重新尝试");
      }
    }
  }
}
