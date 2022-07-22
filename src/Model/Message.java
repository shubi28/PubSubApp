package Model;

public class Message
{
	public String topic;
	public String payload;

	public Message(String topic, String payload)
	{
		this.topic = topic;
		this.payload = payload;
	}

	public String getTopic()
	{
		return topic;
	}

	public void setTopic(final String topic)
	{
		this.topic = topic;
	}

	public String getPayload()
	{
		return payload;
	}

	public void setPayload(final String payload)
	{
		this.payload = payload;
	}
}