
/**
 * This class drives the Crypt program.
 * 
 * @author Michael Fradkin and Michael King
 * @version 10/04/02
 */
public class main
{
    public static void main( String[] args ) {
    
        cs315.prog.ReadFile file = null;                //ReadFile reference
        cs315.prog.Crypt cryptSolve = null;
        java.io.FileWriter fout = null;                 //FileWriter reference
        java.io.PrintWriter out = null;                 //Printwriter reference
        cs315.util.Timer t = new cs315.util.Timer();    //Construct Time object
        
        try {
            file = new cs315.prog.ReadFile("main.in");          //File to read from
                //Construct FileWriter object with file to write to
            fout = new java.io.FileWriter("main.out");
                //Contruct a PrintWriter to output with (takes FileWriter)
            out = new java.io.PrintWriter(fout);    
            
            t.start("Loops", out);  
            
            //while there are more crypts to be solved
            while( file.isReady() )
            {   //attempt to solve the crypt
                cryptSolve = new cs315.prog.Crypt(file.readLine());
                //print the results
                cryptSolve.printResults(out);
            }

            //Turn off timer
            t.finish();            
        }
        
        //Catches exceptions thrown and prints the errors
        catch(cs315.prog.BadInputException e)
        {
            System.out.println( e );
        }

        catch(java.io.IOException e)
        {
            System.out.println( "The file was not found: " + e );
        }

        catch(java.lang.ArrayIndexOutOfBoundsException e)
        {
            System.out.println( e );
            e.printStackTrace();
        }
        
        //In any case, try to close the file
        finally
        {
            try
            {
                //Makes sure references arent null and then close the streams
                if (file != null)
                    file.close();
                if(fout != null)
                    fout.close();
            }
            //Can't close the file
            catch (java.io.IOException e) {
                System.out.println( "File could not be closed: " + "\n" + e + "\n");
                e.printStackTrace();
            }
        }    
    }
}//end class
