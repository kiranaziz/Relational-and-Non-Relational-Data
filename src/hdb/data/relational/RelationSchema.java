package hdb.data.relational;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

/**
 * A {@code RelationSchema} represents the schema of a relation.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class RelationSchema implements java.io.Serializable {

	/**
	 * An {@code InvalidAttributeIndexException} is thrown if an invalid attribute index is given to a
	 * {@code RelationSchema}.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class InvalidAttributeIndexException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = -912376463939840655L;

	}

	/**
	 * An {@code InvalidRelationSchemaDefinition} is thrown if a {@code RelationSchema} is defined inappropriately.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class InvalidRelationSchemaDefinitionException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = -5993611676487221609L;

	}

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -100208280521684870L;

	/**
	 * The names of the attributes of this {@code RelationSchema}.
	 */
	String[] attributeNames;

	/**
	 * The types of the attributes of this {@code RelationSchema}.
	 */
	Class<?>[] attributeTypes;

	/**
	 * Constructs a {@code RelationSchema}.
	 * 
	 * @param attributeNames
	 *            the names of the attributes of the {@code RelationSchema}
	 * @param attributeTypes
	 *            the types of the attributes of the {@code RelationSchema}
	 * @throws InvalidRelationSchemaDefinitionException
	 *             if the number of attribute names and the number of attribute types do not match
	 */
	public RelationSchema(String[] attributeNames, Class<?>[] attributeTypes)
			throws InvalidRelationSchemaDefinitionException {
		if (attributeNames.length != attributeTypes.length)
			throw new InvalidRelationSchemaDefinitionException();
		this.attributeNames = attributeNames;
		this.attributeTypes = attributeTypes;
	}

	/**
	 * Returns a string representation of this {@code RelationSchema}.
	 */
	@Override
	public String toString() {
		LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
		for (int i = 0; i < attributeNames.length; i++)
			m.put(attributeNames[i], attributeTypes[i].getName());
		return m.toString();
	}

	/**
	 * Returns the number of attributes in this {@code RelationSchema}.
	 * 
	 * @return the number of attributes in this {@code RelationSchema}
	 */
	public int size() {
		// TODO complete this method
		return (this.attributeNames.length);
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	public String attributeName(int attributeIndex) throws InvalidAttributeIndexException {
		// TODO complete this method
		//Valid attributeIndices return the name of the attribute at the specific index.
		//Otherwise an error is thrown out.
		if((attributeIndex >= this.attributeNames.length) || (attributeIndex < 0)) {
			throw new InvalidAttributeIndexException();
		}
		
		return this.attributeNames[attributeIndex];
	}

	/**
	 * Returns the type of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of the attribute
	 * @return the type of the specified attribute
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	public Class<?> attributeType(int attributeIndex) throws InvalidAttributeIndexException {
		// TODO complete this method
		//Valid attributeIndices return the type of that index.
		//Otherwise an error is thrown out.
		if((attributeIndex >= this.attributeTypes.length) || (attributeIndex < 0)) {
			throw new InvalidAttributeIndexException();
		}
		
		return this.attributeTypes[attributeIndex];
	}

	/**
	 * Returns the index of the specified attribute in this {@code RelationSchema} ({@code null} if no such attribute).
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @return the index of the specified attribute in this {@code RelationSchema}; {@code null} if no such attribute
	 */
	public Integer attributeIndex(String attributeName) {
		// TODO complete this method
		
		int arrayLength = this.attributeNames.length;
		int i = 0;
		
		while(i < arrayLength) {
			if(attributeNames[i].equals(attributeName)) {
				return i;
			}else {
				i++;
			}
		}
		
		return null;
	}

	/**
	 * Saves this {@code RelationSchema} in the specified file.
	 * 
	 * @param fileName
	 *            the name of the file to store this {@code RelationSchema}
	 * @throws FileNotFoundException
	 *             if the file cannot be opened
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void save(String fileName) throws FileNotFoundException, IOException {
		// TODO complete this method
		
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close(); 
	}

	/**
	 * Creates a {@code RelationSchema} from the specified file.
	 * 
	 * @param fileName
	 *            the name of the file from which a {@code RelationSchema} is created
	 * @return a {@code RelationSchema} created from the specified file
	 * @throws FileNotFoundException
	 *             if the specified file cannot be found
	 * @throws IOException
	 *             if an IO error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 */
	public static RelationSchema createRelationSchema(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		RelationSchema schema = (RelationSchema) in.readObject();
		in.close();
		return schema;
	}

}
