package ait.mediation;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BlkQueueImpl<T> implements BlkQueue<T> {

        private LinkedList<T> blkqueue;
        private int maxSize;

        public BlkQueueImpl(int maxSize) {
            this.maxSize = maxSize;
            blkqueue = new LinkedList<>();
        }

        public synchronized void push(T message) {
            while (blkqueue.size() >= maxSize) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            blkqueue.add(message);
            this.notify();
        }

        public synchronized T pop() {
            while (blkqueue.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T res = blkqueue.getFirst();
            blkqueue.removeLast();
            this.notify();
            return res;
        }








//    LinkedList<T> blkqueue;
//    int maxSize;
//    Lock mutex = new ReentrantLock();
//    Condition senderWaitingCondition = mutex.newCondition();
//    Condition receiverWaitingCondition = mutex.newCondition();
//
//    public BlkQueueImpl(int maxSize) {
//        this.maxSize = maxSize;
//        blkqueue = new LinkedList<>();
//    }
//
//    @Override
//    public void push(T message) {
//        mutex.lock();
//        try {
//            while (blkqueue.size() >= maxSize) {
//                try {
//                    senderWaitingCondition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            blkqueue.add(message);
//            receiverWaitingCondition.signal();
//        } finally {
//            mutex.unlock();
//        }
//    }
//
//    @Override
//    public T pop() {
//        mutex.lock();
//        try {
//            while (blkqueue.isEmpty()) {
//                try {
//                    receiverWaitingCondition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            T res = blkqueue.poll();
//            senderWaitingCondition.signal();
//            return res;
//        } finally {
//            mutex.unlock();
//        }
//    }
}