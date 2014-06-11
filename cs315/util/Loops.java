package cs315.util;


/**
 * Loops is a general class that encapsulates an idea of building a set of nested loops based on characters.
 * 
 * @author Michael Fradkin and Michael King
 * @version 10/04/02
 */
public class Loops implements cs315.util.Iterator, Cloneable
{
    /**storage container for loops */
    private char[] letters;
    /**captures current position of storage container */
    private int position;
    /**captures truth value of whether the 'loop' can continue or not */
    private boolean hasNext;

    /**
     * Constructor for class Loops <BR>
     * pre: howMany >= 0 <BR>
     * post: variables are initialized; hasNext = true <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param the number of 'loops' to be built, each nested inside each the others
     * @throws nothing
     * @return nothing
     */    
    public Loops(int howMany)
    {   letters = new char[howMany];
        //initialize the array to all spaces for padding
        for(int x = 0; x < howMany; x++)
        {   letters[x] = ' ';
            assert(letters[x] == ' ');
        }
           
        position = 0;
        hasNext = true;
    }

    /**
     * method to build a new nested loop based on the inputed character <BR>
     * pre: position < letters.length <BR>
     * post:  letter is added to the loops<BR>
     * Complexity: Big O(1)<BR>
     * Memory Usage: Big O(1)<BR>
     * @throws nothing
     * @param letter is the letter to be created within the other nested loops, if any
     * @return nothing
     */ 
    public void add(char letter) 
    {   
        letters[position] = letter;
        ++position;
        assert (this.isValid());
    }
    
    /**
     * method to sort the loops based in alphabetical order of inputted characters. <BR>
     * pre: none
     * post: variables are initialized; hasNext = true <BR>
     * Complexity: Big O(1) + Big O(N)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param none
     * @throws nothing
     * @return nothing
     */     
    public void sort()
    {   java.util.Arrays.sort(letters); }

    /**
     * method to determine if loops can be incremented again. <BR>
     * pre: none
     * post: returns true if all loops can be incremented at least once; otherwise false  <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param the number of 'loops' to be built, each nested inside each other
     * @throws nothing
     * @return true if all loops can be incremented at least once; otherwise false
     */ 
    public boolean hasNext() //const
    {   
        return hasNext;
    }

    /**
     * method to increment the loops <BR>
     * pre: this.hasNext() == true <BR>
     * post: all loops are incremented  <BR>
     * Complexity: Big O(1) + Big O(N)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param none
     * @throws nothing
     * @return an array of all the current values of each letter
     */   
    public Object next()
    {   hasNext = nextPermutation(letters);
        return letters;
    }

    /**
     * method that performs all permutations of a character sequence <BR>
     * pre: a != null <BR>
     * post: returns true if all loops can be incremented at least once; otherwise false  <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param an array with indices of the current values of each letter
     * @throws nothing
     * @return true if all loops can be incremented at least once; otherwise false
     */     
    private boolean nextPermutation(char[] a)
    {   //cant permutate at all if theres not at least 2 characters
        if ((a.length == 0) || (a.length == 1))
            return false;

        int i = a.length - 1;
        while (true)
        {
            int j = i--;

            if (a[i] < a[j])
            {
                int k = a.length;
                while (!(a[i] < a[--k])) {}
                swap(a, i, k);
                reverse(a, j);
                return true;
            }

            if (i == 0)
            {   reverse(a, 0); //reverse all values to ascending order for reuse later, if necessary
                return false;
            }
        }
    }
    
    /**
     * method to swap two characters in the loops. <BR>
     * pre: swap1, swap2 < c.length
     * post: the characters switch positions  <BR>
     * Complexity: Big O(1)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param c is an array of characters with indices as current values
     * @param swap1 is the first position
     * @param swap2 is the position to be swapped with swap1
     * @throws nothing
     * @return nothing
     */     
    private void swap(char[] c, int swap1, int swap2)
    {  char temp = c[swap1];
       c[swap1] = c[swap2];
       c[swap2] = temp;
       assert(this.isValid());
    }

    /**
     * method to reverse items in underlying character array <BR>
     * pre: reverseFrom < c.length
     * post: all characters after but including reverseFrom are reversed.  <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param char[] c is an the underlying array for the Loops class
     * @param int reverseFrom is the index to be reversed from until c.length
     * @throws nothing
     * @return nothing
     */     
    private void reverse(char[] c, int reverseFrom)
    {   int i = c.length - 1;
        while(reverseFrom < i)
            swap(c, reverseFrom++, i--);
    }

    /**
     * method to determine if loops contains a certain character <BR>
     * pre: none <BR>
     * post: returns true if character is found, otherwise false  <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param letter is the letter to be searched for
     * @throws nothing
     * @return true if character is found, otherwise false
     */ 
    public boolean contains(char letter)
    {
        for(int x = 0; x < letters.length; x++)
            if(letters[x] == letter)
                return true;
        return false;
    }

    /**
     * method to get the value of a character at the elements current loop position <BR>
     * pre: none <BR>
     * post: if found, the int value of the character is returned, otherwise -1  <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param c is the letter to be searched for
     * @throws nothing
     * @return the int value of the character
     */     
    public int elementAt(char c)
    {   for(int x = 0; x < letters.length; x++)
        {   if(letters[x] == c)
                return x;
        }
        assert(!contains(c) );
        return -1;
    }

    /**
     * method to determine the value of the invariant. <BR>
     * pre: none <BR>
     * post: returns true if invariant is true, otherwise false.  <BR>
     * Complexity: Big O(1)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param none
     * @throws nothing
     * @return true if invariant is true, otherwise false.
     */       
    public boolean isValid()
    {   return (position >= 0 && letters.length >= 0); }


    /**
     * method to clone a Loops object. <BR>
     * pre: none <BR>
     * post: returns a new Loops object independent of its copy.  <BR>
     * Complexity: Big O(1)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param none
     * @throws nothing
     * @return the new Loops object.
     */     
    public Object clone()
    {
        try
        {   Loops temp = (Loops)super.clone();
            temp.letters = new char[this.letters.length];
            for(int x = 0; x < this.letters.length; x++)
                temp.letters[x] = this.letters[x];
			return temp;
        }
        catch(CloneNotSupportedException e)
        {   assert false;
            return null;
		}
	}
    
}//end class
