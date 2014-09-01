package message;

public class ClientInit extends Message{

    public ClientInit(String name) {
        this.Sender = name;
    }

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
