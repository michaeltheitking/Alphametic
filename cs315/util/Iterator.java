package cs315.util;


/**
 * Interface for iterating through all the permutations of a character array.
 * 
 * @author michael king and michael fradkin 
 * @version 1.0
 */

public interface Iterator
{

    /**
     * method to determine if loops can be incremented again. <BR>
     * post: returns true if iterator can be incremented at least once; otherwise false  <BR>
     * Complexity: ?? <BR>
     * Memory Usage: ?? <BR>
     * @return true if iterator can be incremented at least once; otherwise false
     */ 
	boolean hasNext();
	
    /**
     * method to increment through the object <BR>
     * pre: this.hasNext() == true <BR>
     * post: iterator is incremented once  <BR>
     * Complexity: ??<BR>
     * Memory Usage: ??<BR>
     * @return the next iteration Object
     */   	
	Object next();
}
