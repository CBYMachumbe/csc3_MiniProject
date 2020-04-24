package list;
public class ArrayListException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    public ArrayListException() {
        super();
    }
    
    public ArrayListException(String message) {
        super(message);
    }
    
    public ArrayListException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ArrayListException(Throwable cause) {
        super(cause);
    } 
	
}
