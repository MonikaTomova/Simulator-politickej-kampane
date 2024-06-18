package gui;

import java.util.LinkedList;
import java.util.Queue;

/** Manages the execution of multiple threads by maintaining a queue of threads */
public class ThreadHandler {
    private Queue<Thread> queue = new LinkedList<>();
    private boolean threadInProcess = false;

    /** 
     * Adds thread to the thread queue, starts the thread if no other threads are running
     * @param newThread thread to be added to the thread queue
     */
    protected void addThread(Thread newThread) {
        if (this.threadInProcess == true) {
            queue.add(newThread);
        }
        else {
            newThread.start();
            this.threadInProcess = true;
        }

        return;
    }

    
    /** 
     * Removes given thread from the thread queue and starts the next one (if exists)
     * @param finishedThread thread to be removed from the thread queue
     */
    protected void endThread(Thread finishedThread) {
        queue.remove(finishedThread);   
        if (!queue.isEmpty()) {
            Thread nextThread = queue.element();
            nextThread.start();
            this.threadInProcess = true;
        }
        else {
            this.threadInProcess = false;
        }
    }
    
}