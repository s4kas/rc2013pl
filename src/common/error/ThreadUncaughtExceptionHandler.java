/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.error;

import common.protocol.Message;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 *
 * @author Emanuel
 */
public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private Message message;
    private IErrorTreatingStrategy currentStrategy;


	public void setCurrentStrategy(IErrorTreatingStrategy currentStrategy) {
		this.currentStrategy = currentStrategy;
	}

	public ThreadUncaughtExceptionHandler() {
    }

    public ThreadUncaughtExceptionHandler(Message msg) {
        this.message = msg;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Thread: " + t.getName() + " | Erro: " + e.getMessage());
        solveException(t.getName());
    }

    private void solveException(String type) {
        currentStrategy.dealWithException(message, type);
    }
}
