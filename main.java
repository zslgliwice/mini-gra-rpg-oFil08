import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String args[]) throws IOException{
        Game game;
        System.out.println("Jaki poziom trudnosci wybierasz?\n1. Latwy\n2. Normalny\n3. Trudny");
        Scanner s = new Scanner(System.in);

        do{
            int choice = s.nextInt();

            if(choice > 3 || choice < 0){
                System.out.println("Nieprawidlowy wybor");
            }
            else{
                game = new Game(choice);
                break;
            }
        
        }while(true);
    }
}
