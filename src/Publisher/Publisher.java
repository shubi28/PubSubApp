package Publisher;

import Model.Message;

public interface Publisher {
	boolean addToMessageQueue(Message message);
}