package ExceptionCustom;

/**
 * This exception happens when btnDisconnect is not closed after clicking it.
 * @author Admin
 *
 */
public class EC_DisconnectBtn extends Exception{
	public EC_DisconnectBtn() {}
	
	public EC_DisconnectBtn(String message) {
		super(message);
	}

}
