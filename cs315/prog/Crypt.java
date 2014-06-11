package cs315.prog;


/**
 * Crypt reads a given alphametic and attempts to find one solution for it.
 * 
 * @author michael king and michael fradkin 
 * @version 1.0
 */
public class Crypt implements Cloneable
{
    /**captures the number of attempts in trying to solve an alphametic arithmetically */
    private int attempts;
    /**holds the maximum length of an addend */
    private int maxWordSize;
    /**captures the number of unique characters in a given alphametic */
    private int numUniqueChars;
    /**the underlying storage container for solving the alphametic*/
    private cs315.util.Loops loops;
    /**the string representation of the alphametic, including addends and sum */
    private String crypt;
    /**the string representation of the alphametic converted to successful numbers */
    private String numbers;
    /**captures the truth of solving a given alphametic */
    private boolean matchFound;
    /**constant that makes sure all letters are single digits */
    private static final int MAX_VALUE = 9;
    /**An array that holds the characters that cannot be zero */
    private char[] nonZeroChars;
    /**Size of the above arrays - lets us know what index to add to next */
    private int nonZeroCharsSize;
    /** captures the first character of the summation word (used to increase speed)*/
    private char firstChar;
    /** captures the number of addends in the crypt */
    private int numAddends;
    /** captures the length of the summation word in the crypt (used to increase speed) */
    private int lastTokenLength;
    
    /**
     * Constructor for Crypt objects <BR>
     * pre: crypt != null <BR>
     * post: a crypt has been attempted to have been solved and either found a match or no solution<BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param crypt is the string representation of an alphametic
     * @throws BadInputException if input is not valid
     * @return nothing
     */ 
    public Crypt(String crypt) throws cs315.prog.BadInputException
    {   //initialize values
        attempts = numUniqueChars = maxWordSize = 0;
        String token = null;
        numbers = "";
        this.crypt = crypt;
        loops = new cs315.util.Loops(MAX_VALUE+1);
        java.util.StringTokenizer st = new java.util.StringTokenizer(crypt);     
        
        assert this.isValid();
        
        //number of addends is the number of tokens minus the summation word
        numAddends = st.countTokens() - 1;
        
        //there have to be at least 2 addends to make a sum; if not, throw an exception
        if ( st.countTokens() == 2 )
            throw new cs315.prog.BadInputException("There must be at least 3 words to solve an equation, the following line failed: " + crypt);
        else
            nonZeroChars = new char[st.countTokens()];           //Each token as one char (first char) that cannot be zero

        //while there are more words needing to be solved
        while(st.hasMoreTokens() )
        {   token = st.nextToken();  
        
            //While there are more tokens
            if ( st.hasMoreTokens() )
               maxWordSize = Math.max(token.length(), maxWordSize);        //See if the length of this token is longer than the others and save it;

            if(! st.hasMoreTokens() )
            {   lastTokenLength = token.length();
                firstChar = token.charAt(0);
			}
                
            //break down each individual letter in the word
            for (int i = 0; i < token.length(); i++)
            {   
                if (i == 0) {                   //If we are on the first character of this token
                    nonZeroChars[nonZeroCharsSize] = token.charAt(i);       //Add it to the array so it won't be zero
                    ++nonZeroCharsSize;                                     //Increment the size of the array
                }
                
                //only add if character is unique
                if( isUnique( token.charAt(i) ) )
                {   //increment number of unique characters
                    numUniqueChars++;
                    loops.add(token.charAt(i)); 
                        
                    //if there are more unique characters than slots, it cant be solved
                    if (numUniqueChars > (MAX_VALUE+1))
                        throw new cs315.prog.BadInputException("Not enough unique numbers for: " + crypt);
                }               
            }
                
        }          
        
        //sort the characters in ascending order for all possible permutations
        loops.sort();   

        //if only one char, theres no solution automatically
        if(numUniqueChars > 1)
        {   loops.next();
        
            //keep iterating if we dont find a match   
            while(loops.hasNext())
            {   loops.next();
            
                //if theres no leading zeros for first characters
                if(validPerm() )
                {   //if the last token length is > than the longest addend
                    if(lastTokenLength > maxWordSize)
                    {   //the first char of the summation word can only be as big as the number of addends minus 1
                        if(loops.elementAt(firstChar) <= numAddends - 1)
					    {
                            if(!isMatch())
                                numbers = ""; //reset the string to be empty
               
                            //if we find a match, we're done
                            else
                                break;
                        }
                    }
                    //check for a match like normal
                    else
                    {
                        if(!isMatch())
                            numbers = ""; //reset the string to be empty
               
                        //if we find a match, we're done
                        else
                            break;
					}                       
                }
            }
          
         //if we never find a match, theres no solution
         if(!matchFound)
           numbers = "No Solution";   
        }
        else
          numbers = "No Solution";

        //Judging by the length of the addends, it's impossible for the result to be obtained (the length is too long)
        if (token.length() > (maxWordSize + 1))                          
            throw new cs315.prog.BadInputException("Words cannot possibly add up to result word in: " + crypt);       
            
         assert this.isValid();
    }

    /**
     * method to test if a permutation is a valid one that can be used. <BR>
     * pre: none <BR>
     * post: returns true if permutation has no zero values for leading characters in crypt, false otherwise<BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param none
     * @throws nothing
     * @return trut if permutation has no zero val for leading chars in crypt string, otherwise false
     */ 
    private boolean validPerm()
    {
        for(int x = 0; x < nonZeroChars.length; x++)
        {   if(loops.elementAt(nonZeroChars[x]) == 0)
                return false;
        }
        return true;
	}
    /**
     * method to determine if a character is unique or not. <BR>
     * pre: none <BR>
     * post: returns true if character has not been added before, otherwise false  <BR>
     * Complexity: Big O(1) + Big O(N)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param c is the letter to be checked for uniqueness
     * @throws nothing
     * @return true if character has not been added before, otherwise false
     */     
    private boolean isUnique(char c)
    {
        if(loops.contains(c))
            return false;
        return true;
    }

    /**
     * method to convert a word into its current number value <BR>
     * pre: s != null <BR>
     * post: the actual number the word may be representing is returned <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param s is the string representation of the word to be converted
     * @throws nothing
     * @return the current long value of the string
     */     
    private long numberConvert(String s)
    {   String temp = "";
    
        for(int x = 0; x < s.length(); x++)
        {   //loops must have this string element in it to convert to a number
            assert (loops.contains(s.charAt(x)));

            //get the index of the corresponding character in the array and add to a 'running total' string
            temp += loops.elementAt(s.charAt(x));   

        }
        
        //store all numbers together for printing later
        numbers += (temp + " ");    
        
        //convert the 'running total' string into an int
        return Long.parseLong(temp);
    }

    /**
     * method to determine if the int values of all words add up to the int value of the last word <BR>
     * pre: none <BR>
     * post: returns true if it has been successfully solved, otherwise false  <BR>
     * Complexity: Big O(N)<BR>
     * Memory Usage: Big O(N)<BR>
     * @param nothing
     * @throws nothing
     * @return true if match is found, otherwise false
     */     
    private boolean isMatch()
    {   boolean lastToken = false;
        long add = 0;
        long sum = 0;
        String temp = null;
        java.util.StringTokenizer st = new java.util.StringTokenizer(crypt);
        
        assert (st.countTokens() != 0);
            
        //while there are more words to check
        while(st.hasMoreTokens())
        {              
            temp = st.nextToken();
            
            //if there are no more tokens after this one, its the last token            
            if(! st.hasMoreTokens() )
                lastToken = true;
                
            //if its not the last token, convert the token to a number and add it to the running total of addends
            if(!lastToken)
                add += numberConvert(temp);                         //Add it to the total

            //if it is the last token, convert the token to a number and store as the sum
            else 
                sum = numberConvert(temp);                          //It's the sum
        }
        
        //we're attempting to solve once
        attempts++;
        if(add == sum)
        {   matchFound = true;
            return matchFound;
        }
        return false;
    }

    /**
     * method to print the results to a given PrintWriter object <BR>
     * pre: out != null <BR>
     * post: results are printed.  <BR>
     * Complexity: Big O(1)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param out is the PrintWriter object to be printed to
     * @throws nothing
     * @return nothing
     */     
    public void printResults(java.io.PrintWriter out)
    {   assert (out != null);
        out.println("words    = " + crypt);
        out.println("attempts = " + attempts);
        out.println("numbers  = " + numbers);
        out.println();
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
    {   return (attempts >= 0 && numUniqueChars >= 0 && maxWordSize >= 0);
    }

    /**
     * method to clone a Crypt object. <BR>
     * pre: none <BR>
     * post: returns a new Crypt object independent of its copy.  <BR>
     * Complexity: Big O(1)<BR>
     * Memory Usage: Big O(1)<BR>
     * @param none
     * @throws nothing
     * @return the new Crypt object.
     */     
    public Object clone()
    {  
       Crypt temp;
       
       try
       {   temp = (Crypt)super.clone(); //clone primitives
           temp.loops = (cs315.util.Loops)loops.clone(); //clone reference
       }
       catch(CloneNotSupportedException e)
       {   assert (false);
           return null;
       }
       
       return temp;
    }
            
} //end class
