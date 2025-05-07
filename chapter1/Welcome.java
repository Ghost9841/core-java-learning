package chapter1;
// Listing 1.1 Welcome.java

public class Welcome{
    public static void main (String[] args){
        String greeting = "Welcome to Core Java!";
        System.out.println(greeting);
        for (int i = 0; i < greeting.length(); i++) 
            System.out.print("=");
    }
}


/*
object.method(parameters)
You will be using this thing a lot so don't forget this and keep it in mind.
*/

// >> Second Listing is ImageViewer.java Listing 1.2