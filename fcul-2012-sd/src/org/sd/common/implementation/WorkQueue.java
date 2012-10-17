package org.sd.common.implementation;

import java.util.LinkedList;

public class WorkQueue
{
    private final PoolWorker[] threadArray;
    private final LinkedList<Runnable> workerQueue;

    public WorkQueue(int nThreads)
    {
        workerQueue = new LinkedList<Runnable>();
        threadArray = new PoolWorker[nThreads];
        
        //instantiate all the threads in the array
        for (int i=0; i<nThreads; i++) {
            threadArray[i] = new PoolWorker();
            threadArray[i].start();
        }
    }

    public void execute(Runnable r) {
        synchronized(workerQueue) {
        	//add a new worker to the bottom of the list
        	//and notify the queue it has a new worker
            workerQueue.addLast(r);
            workerQueue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable r;

            while (true) {
                synchronized(workerQueue) {
                    while (workerQueue.isEmpty()) {
                        try {
                        	//wait for workers
                            workerQueue.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    
                    //get a worker from the top of the queue
                    r = (Runnable) workerQueue.removeFirst();
                }

                // If we don't catch RuntimeException, 
                // the pool could leak threads
                try {
                	//start the worker
                    r.run();
                } catch (RuntimeException e) {
                    //TODO BM tratar erros
                }
            }
        }
    }
}
