import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.*;

class ImplementingMultiThread implements Runnable {
    String inputURL;
    File wordListFile;
    String successCode;
    ImplementingMultiThread(String inputURL, File wordListFile, String successCode) { // constructor function
        this.inputURL = inputURL;
        this.wordListFile = wordListFile;
        this.successCode = successCode;
    }
    public void run() {
        StringBuilder outputString = new StringBuilder(""); //Builder function
        
        try{
            
            BufferedReader br2 = new BufferedReader(new FileReader(wordListFile));
            String word;
            while((word = br2.readLine()) != null) { // loop in .txt until next line becomes null
            
                URL url = new URL(inputURL+"/"+word); // appending words in wordslist to given url
                
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET"); // setting the type of connection to get(read)
                connection.connect(); // making a connection
                int code = connection.getResponseCode(); // getting the status code

                if(successCode.contains(Integer.toString(code))) {
                    outputString.append("url: " + url + " [code: " + code+ "]").append(System.lineSeparator());
                }
            }
            System.out.println(outputString);
            br2.close();

        }
        catch(Exception e) {
            System.out.print("Caught error");
        }
    }
}



public class App {

    public static void main(String[] args) throws IOException 
    {   
        // get the url
        System.out.println("Enter url(s), if multiple seperate them with space");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        
        String inputURLArray[] = br1.readLine().split(" "); // getting url
        
        
        
        //get the file wordlist filelocation
        System.out.println("Enter the wordlist file location. Eg: C:\\Users\\leo\\Documents\\Java\\Test\\test\\src\\wordlist.txt");
        String fileLocation = br1.readLine();
        
        File wordListFile = new File(fileLocation); //Giving file name as input

        System.out.println("Enter Sucess Status Code with space seperated");
        String successCode = br1.readLine(); // get success codes
        
        br1.close(); // closing br1
        
        for(int i=0; i< inputURLArray.length; i++) {
            Thread object = new Thread(new ImplementingMultiThread(inputURLArray[i], wordListFile, successCode)); // creating multiple threads
            object.start();
        }
    }
}