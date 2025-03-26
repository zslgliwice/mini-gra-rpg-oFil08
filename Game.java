import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Player player;
    Entity enemy;
    float difficultyMultiplier;
    int liczbaTur;
    boolean shouldIEndTheGame;

    public Game(int difficulty){
        this.player = new Player();
        this.difficultyMultiplier = new float[]{(float) 0.75 , 1 , (float) 1.25}[difficulty-1];
        this.liczbaTur = 0;
        this.shouldIEndTheGame = false;

        System.out.println("Witaj w Tajemniczym Lesie!");
        player.printStats();
        //randomEvent();
    }

    public void endGame(){
        shouldIEndTheGame = true;
    }

    public void randomEvent(){
        liczbaTur++;

        Random r = new Random();
        int eventType = r.nextInt(10)+1;
        if(eventType >= 1 && eventType <= 3) fight();
        else if(eventType == 4 || eventType == 5) treasure();
        else if(eventType == 6 || eventType == 7) camp();
        else if(eventType == 8 || eventType == 9) shop();
        exit();
    }

    public Entity createRandomEnemy() throws IOException{
        Random r = new Random();

        String enemyName;
        try (RandomAccessFile raf = new RandomAccessFile(new File("data/monsters.txt"), "r")) {
                long randomPosition = r.nextInt(50);
                raf.seek(randomPosition);

                if (randomPosition != 0) {
                    raf.readLine();
                }

                enemyName = raf.readLine();
        }

        int enemyHp = (int) ((r.nextInt(31) + 20) * difficultyMultiplier * (1 + (float)liczbaTur/10));

        int enemyAttackPower = (int) ((r.nextInt(11) + 5) * difficultyMultiplier * (1 + (float)liczbaTur/10));

        return new Entity(enemyName, enemyHp, enemyAttackPower);
    }

    public void fight(){
        try {
            enemy = createRandomEnemy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Losowe wydarzenie: Spotkales przeciwnika: " + enemy.getName() + "!");
        enemy.printStats();

        do{
            System.out.println("Co robisz?\n1. Atakuj\n2. Uciekaj");
            Scanner s = new Scanner(System.in);
            int choice;

            do{
                choice = s.nextInt();
            }while(choice != 1 && choice != 2);

            if(choice == 1){
                    if(player.attack(enemy)){
                        System.out.println("Pokonano przeciwnika!");
                        randomEvent();
                        break;
                    }
            }else{
                if(player.runAway()){
                    System.out.println("Pomyslnie uciekles z walki!");
                    randomEvent();
                    break;
                }else{
                    System.out.println("Nie udalo sie uciec z walki...");
                }
            }

            System.out.println(enemy.getName() + " atakuje!");
            if(enemy.attack(player)){
                System.out.println("Pokonano Cię :(");
                endGame();
                break;
            }
        }while(true);
    }  

    public void treasure(){

    }

    public void camp(){

    }

    public void shop(){

    }

    public void exit(){

    }
}
