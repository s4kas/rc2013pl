package common.error;

import common.protocol.Message;

public interface IErrorTreatingStrategy {
	
	public void dealWithException(Message message, String exceptionType);
	
}
