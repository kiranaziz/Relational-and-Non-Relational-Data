package hdb.data.nonrelational;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import hdb.data.nonrelational.CollectionSchema.InvalidAttributeIndexException;

/**
 * A {@code DataObject} represents an entity using a number of attributes organized possibly in a hierarchical fashion.
 * {@code DataObject}s in a non-relational collection can have different sets of attributes.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class DataObject implements java.io.Serializable {

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -5147919692261347227L;

	/**
	 * The {@code CollectionSchema} for this {@code DataObject}.
	 */
	CollectionSchema schema;

	/**
	 * A {@code HashMap} that associates each attribute index with a value.
	 */
	HashMap<Integer, Object> index2value = new HashMap<Integer, Object>();

	/**
	 * Constructs a {@code DataObject}.
	 * 
	 * @param schema
	 *            a {@code CollectionSchema}
	 */
	public DataObject(CollectionSchema schema) {
		this.schema = schema;
	}

	/**
	 * Returns the {@code CollectionSchema} associated this {@code DataObject}.
	 * 
	 * @return the {@code CollectionSchema} associated this {@code DataObject}
	 */
	public CollectionSchema schema() {
		return schema;
	}

	/**
	 * Returns a string representation of this {@code DataObject}.
	 */
	@Override
	public String toString() {
		String s = "{";
		for (Entry<Integer, Object> e : index2value.entrySet()) {
			s += (s.length() == 1 ? "" : ", ");
			s += (e.getKey() + ":" + schema.attributeName(e.getKey()) + "=" + e.getValue());
		}
		return s + "}";
	}

	/**
	 * Sets the value of the specified attribute.
	 * 
	 * @param attributeName
	 *            the name of an attribute
	 * @param o
	 *            the value of the attribute
	 */
	public void setAttribute(String attributeName, Object o) {
		// TODO complete this method
		//
		//Registers attributeName to the index2value and value2index hashmaps of the schema
		//Then, the object is registered to index2value HashMap.
		this.schema.attributeIndex(attributeName);
		this.index2value.put(index2value.size(), o);
	}

	/**
	 * Returns the value of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the value of the specified attribute
	 */
	public Object attributeValue(int[] attributeIndex) {
		// TODO complete this method
		//Finds value of attribute through the hashmap index2value, and returns that value as an object.
		Object result = this.index2value.get(attributeIndex[0]);
		return result;
	}

	/**
	 * Writes the attributes of this {@code DataObject} to the specified {@code ObjectOutputStream}.
	 * 
	 * @param out
	 *            an {@code ObjectOutputStream}.
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void writeAttributes(ObjectOutputStream out) throws IOException {
		// TODO complete this method
		
		//Creates an arraylist of arraylists.
		//Each arraylist in this list holds a pair: the index and its value.
		ArrayList<ArrayList<Object> > List = new ArrayList<ArrayList<Object>>();
		
		for(int i=0; i<this.index2value.size(); i++) {
			ArrayList<Object> attributes = new ArrayList<Object>();
			attributes.add(i);
			attributes.add(this.index2value.get(i));
			List.add(attributes);
		}
		out.writeObject(List); //The output stream is the arraylist of arraylists.
	}

	/**
	 * Constructs a {@code DataObject} from the specified {@code ObjectInputStream}.
	 * 
	 * @param schema
	 *            a {@code CollectionSchema}
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 * @throws InvalidAttributeIndexException
	 *             if an attribute not registered in the {@code CollectionSchema} is read
	 */
	public DataObject(CollectionSchema schema, ObjectInputStream in)
			throws IOException, ClassNotFoundException, InvalidAttributeIndexException {
		// TODO complete this method
		this.schema = schema;
		//The input stream is the arraylist of arraylists, as seen in the previous function.
		//The list is read until the end of the list, and forms a new data object.
		ArrayList<ArrayList<Object>> List = (ArrayList<ArrayList<Object>>)in.readObject();
//		The code below was for testing schemas that did have valid indices, and the expected error was thrown.
//		ArrayList<Object> List2 = new ArrayList<Object>(){
//            {
//                add(0);
//                add("123");
//            }
//        };
//        
//        ArrayList<Object> List3 = new ArrayList<Object>(){
//            {
//                add(1);
//                add("John");
//            }
//        };
//        
//        ArrayList<Object> List4 = new ArrayList<Object>(){
//            {
//                add(2);
//                add("Scully");
//            }
//        };
//        
//        ArrayList<ArrayList<Object> > List = new ArrayList<ArrayList<Object>>();
//        List.add(List2);List.add(List3);List.add(List4);
        
		int index;
		int i=0;
		
		while(i<List.size()){
			index = (int)List.get(i).get(0);
			if((index >= this.schema.index2name.size()) || (index < 0)) {
				throw new InvalidAttributeIndexException();
			}
			this.setAttribute(schema.attributeName(index), List.get(index).get(1));
            
            i++;
		}
	}

}
