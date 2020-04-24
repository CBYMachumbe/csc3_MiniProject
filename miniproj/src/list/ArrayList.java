package list;
import java.util.Iterator;

public class ArrayList<T> implements List<T>, Iterable<T> {

	private T[] array;
	private Integer size;
	private Integer arrayLength;
	
	
	/*
	 * The overloaded constructor for creating an ArrayList 
	 */
	public ArrayList() {
		this.arrayLength = 3;
		this.array = createArray(this.arrayLength);
		this.size = 0;
	}
	
	/*
	 * A helper method for creating the underlying array
	 * ********** 5 marks ****************************
	 */
	@SuppressWarnings("unchecked")
	private T[] createArray(int size) 
	{
		T[] array = (T[]) new Object[size];
		return array;
	}
	
	/*
	 * The method for retrieving the element from the ArrayList
	 * @param the index to retrieve from
	 */
	@Override
	public T get(Integer i) {
		if (i >= size) {
			throw new ArrayListException("Index greater than size");
		}
		if (i < 0) {
			throw new ArrayListException("Index out of range");
		}
		return array[i];
	}

	/*
	 * The method for replacing an element in the ArrayList
	 * @param The index and he element 
	 */
	@Override
	public void set(Integer i, T e) {
		if (i >= (array.length)) {
			throw new ArrayListException("Index greater than size");
		}
		if (i < 0) {
			throw new ArrayListException("Index out of range");
		}
		
		array[i] = e;
	}

	/*
	 * The method for adding an element to the ArrayList
	 * @param the index for where the new element needs to be added and the element
	 * ********** 5 marks ****************************
	 */
	@Override
	public void add(Integer i, T e) 
	{
		if(i == 0)
		{
			set(i,e);
			return;
		}
		
		if(i >= array.length)
		{
			expandArray();
		}
		
		shiftElementsRight(i);
		set(i,e);
			
		size ++;
	}
	
	public void add(T e) 
	{
		if(size == array.length)
		{
			expandArray();
		}
		set(size++,e);
	}

	/*
	 * The method for removing an element from the arrayList
	 * @param the index of the element for removal
	 * ********** 5 marks ****************************
	 */
	@Override
	public T remove(Integer i) 
	{
		T e = array[i];
		shiftElementsLeft(i);
		return e;
	}

	/*
	 * The auxiliary method to determine the size of the ArrayList
	 */
	@Override
	public Integer size() {
		return size;
	}

	/*
	 * The auxiliary method to check if the list is empty 
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	/*
	 * The overridden toString method
	 */
	public String toString() {
		String str = "[";
		for (int i = 0; i < size-1; i++) {
			str += array[i].toString() + ",";
		}
		if (size > 0) {
			str += array[size-1];
		}
		str += "]";
		return str;
	}
	
	/*
	 * The expand array function that creates a new array that depends on the strategy 
	 * (1 for incremental and 2 for doubling) and copies the elements to the new array
	 * ********** 10 marks **************************** 
	 */
	private void expandArray() 
	{
		T[] newArray = createArray((size * 2));
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}
		array = newArray;
	}
	
	/*
	 * A method for shifting all the elements up by one to the right
	 * @param the index from where to shift
	 */
	private void shiftElementsRight(Integer pos) {
		for (int i = this.size; i >= pos; i--) {
			this.array[i+1] = this.array[i];
		}
	}
	
	/*
	 * A method for shifting all the elements up by one to the left
	 * @param the index from where to shift
	 */
	private void shiftElementsLeft(Integer pos) {
		for (int i = pos; i < size; i++) {
			this.array[i] = this.array[i+1];
		}
	}

	/*
	 * The overridden iterator method
	 * ********** 2 marks ****************************
	 */
	@Override
	public Iterator<T> iterator() 
	{		
		return new ArrayListIterator<>(this);
	}

}
