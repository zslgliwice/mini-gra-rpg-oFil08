import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import items.Armor;
import items.Item;
import items.Medicine;
import items.Weapon;

public class Game {
    private final Random random  = new Random();
    private Player player;
    private Entity enemy;
    private float difficultyMultiplier;
    private int liczbaTur;
    private List<String> monsterNames;
    private List<String> armorNames;
    private List<String> medicineNames;
    private List<String> weaponNames;

    public Game(int difficulty) throws IOException {
        float[] difficultyMultipliers = {0.75f, 1.0f, 1.25f};
        this.player = new Player();
        this.difficultyMultiplier = difficultyMultipliers[difficulty - 1];
        this.liczbaTur = 0;
        
        monsterNames = Files.readAllLines(Paths.get("data/monsters.txt"));
        armorNames = Files.readAllLines(Paths.get("data/armor.txt"));
        medicineNames = Files.readAllLines(Paths.get("data/medicine.txt"));
        weaponNames = Files.readAllLines(Paths.get("data/weapons.txt"));

        System.out.println("Witaj w Tajemniczym Lesie!");
        player.printStats();

        betweenEvents();
    }

    public void betweenEvents(){
        System.out.println("\n[==========================================================================]\n");

        while(true){
            System.out.println("Co chcesz zrobic?\n1. Idz dalej\n2. Zobacz statystyki\n3. Zobacz ekwipunek\n4. Zobacz portfel");
            int choice = getUserInput(1, 4);

            if(choice == 1){
                break;
            }
            else if(choice == 2){
                player.printStats();
                player.printXpBar();
            }
            else if(choice == 3){
                player.printWorn();
                player.inventory.printInventory(true);
                if(player.inventory.getSize() > 0){
                    System.out.println("Co chcesz zrobic?\n1. Zaloz przedmiot\n2. Uzyj przedmiot\n3. Wyrzuc przedmiot\n4. Nic");
                    choice = getUserInput(1, 4);
                    if(choice == 1){
                        System.out.println("Ktory?");
                        player.equipItem(getUserInput(1, player.inventory.getSize())-1);
                    }
                    else if(choice == 2){
                        System.out.println("Ktory?");
                        player.useConsumable(getUserInput(1, player.inventory.getSize())-1);
                    }
                    else if(choice == 3){
                            System.out.println("Ktory?");
                            System.out.println("Wyrzucono przedmiot");
                            player.inventory.removeItem(getUserInput(1, player.inventory.getSize())-1);
                    }
                }
            }
            else player.printWallet();
        }

        System.out.println("\n[==========================================================================]\n");

        randomEvent();
    }

    public void randomEvent() {
        liczbaTur++;
        int eventType = random.nextInt(10) + 1;
        
        if (eventType <= 3) fight();
        else if (eventType <= 5) treasure();
        else if (eventType <= 7) camp();
        else if (eventType <= 9) shop();
        else exit();
    }

    public Entity createRandomEnemy() {
        String enemyName = monsterNames.get(random.nextInt(monsterNames.size()));
        float multiplier = difficultyMultiplier * (1 + (float) liczbaTur / 50);
        int enemyHp = (int) ((random.nextInt(31) + 20) * multiplier);
        int enemyAttackPower = (int) ((random.nextInt(11) + 5) * multiplier);
        return new Entity(enemyName.replace("?", ""), enemyHp, enemyAttackPower);
    }

    public Item createRandomItem() {
        int itemPower = (int) ((random.nextInt(6) + 5) * (1 + (float) liczbaTur / 50));

        switch (random.nextInt(3)) {
            case 0:
                return new Armor(armorNames.get(random.nextInt(armorNames.size())).replace("?", ""), itemPower);
            case 1:
                return new Weapon(weaponNames.get(random.nextInt(weaponNames.size())).replace("?", ""), itemPower);
            default:
                return new Medicine(medicineNames.get(random.nextInt(medicineNames.size())).replace("?", ""), itemPower*2);
        }
    }

    public void fight() {
        player.printStats();
        enemy = createRandomEnemy();
        System.out.println("Losowe wydarzenie: Spotkales przeciwnika: " + enemy.getName() + "!");
        enemy.printStats();
        
        while (true) {
            int max = 3;
            if(player.inventory.countConsumables() > 0){
                System.out.println("Co robisz?\n1. Atakuj\n2. Ulecz sie\n3. Uciekaj");
            }
            else{
                System.out.println("Co robisz?\n1. Atakuj\n2. Uciekaj"); max = 2;
            }
            int choice = getUserInput(1, max);

            if(choice == 2 && max == 2){
                choice = 3;
            } 
            
            if (choice == 1 && player.attack(enemy)) {
                System.out.println("Pokonano przeciwnika!");
                player.battleRewards();
                break;
            } else if (choice == 2) {
                int numOfConsumables = player.inventory.printConsumables();
                if(numOfConsumables > 0){
                    int itemIndex = getUserInput(1, numOfConsumables);
                    player.useConsumable(itemIndex-1);
                }
                else System.out.println("Nie masz zadnych przedmiotow do leczenia!");
            } else if (choice == 3){
                if(player.runAway()){
                    System.out.println("Udalo sie uciec!");
                    break;
                } else {
                    System.out.println("Nie udalo sie uciec...");
                }
            }
            
            System.out.println(enemy.getName() + " atakuje!");
            if (enemy.attack(player)) {
                System.out.println("Zostales pokonany :(");
                return;
            }
        }
        betweenEvents();
    }

    public void treasure() {
        System.out.println("Losowe wydarzenie: Znaleziono skarb!");
        player.inventory.addItem(createRandomItem());
        betweenEvents();
    }

    public void camp() {
        System.out.println("Losowe wydarzenie: Znaleziono dobre miejsce na oboz!\nUleczyc 25% HP za 10 zlota?\nTwoje zloto: " + player.getGold() + "\nTwoje hp: " + player.getHp() +"/" + player.getMaxHp()+ "\n1. Tak\n2. Nie");
        if (getUserInput(1, 2) == 1) {
            if(player.getGold() >= 10){
                player.addHp(player.getMaxHp() / 4);
                player.removeGold(10);
                System.out.println("Odpoczales i odzyskales troche sil.");
                player.printStats();
            }
            else{
                System.out.println("Niewystarczająco złota");
            }
        }
        betweenEvents();
    }

    public void shop() {
        System.out.println("Losowe wydarzenie: Znaleziono podrozujacego handlarza! Ilosc zlota: "+ player.getGold());
        
        Item[] items = {createRandomItem(), createRandomItem(), createRandomItem()};
        int[] prices = {random.nextInt(26) + 15, random.nextInt(26) + 15, random.nextInt(26) + 15};
        
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + items[i].getName() + " - Moc: " + items[i].getPower() + " Cena: " + prices[i]);
        }
        System.out.println("4. Nic nie kupuj");
        
        while (true) {
            int choice = getUserInput(1, 4);

            if (choice != 4 && player.getGold() >= prices[choice - 1]) {
                System.out.println("Kupiono " + items[choice - 1].getName());
                player.removeGold(prices[choice - 1]);
                player.inventory.addItem(items[choice - 1]);
                break;
            } else if (choice != 4) {
                System.out.println("Brakuje zlota!");
            } else if(choice == 4){
                break;
            }
        }
        betweenEvents();
    }

    public void exit() {
        System.out.println("Losowe wydarzenie: Znaleziono wyjscie!\n1. Wyjdz\n2. Idz dalej");
        if (getUserInput(1, 2) == 1) {
            System.out.println("Wychodzisz z lasu i konczysz gre.");
            System.exit(0);
        }
        betweenEvents();
    }

    private int getUserInput(int min, int max) {
        Scanner s = new Scanner(System.in);
        int input;

        while (true) {
            if (s.hasNextInt()) {
                input = s.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Liczba poza zakresem, sprobuj ponownie.");
                }
            } else {
                System.out.println("Niepoprawna wartosc, wpisz liczbe.");
                s.next(); 
            }
        }
    }
}