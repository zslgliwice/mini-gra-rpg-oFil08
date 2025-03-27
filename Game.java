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
    private final Scanner scanner = new Scanner(System.in);
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
        float multiplier = difficultyMultiplier * (1 + (float) liczbaTur / 10);
        int enemyHp = (int) ((random.nextInt(31) + 20) * multiplier);
        int enemyAttackPower = (int) ((random.nextInt(11) + 5) * multiplier);
        return new Entity(enemyName, enemyHp, enemyAttackPower);
    }

    public Item createRandomItem() {
        int itemPower = (int) ((random.nextInt(6) + 5) * (1 + (float) liczbaTur / 10));

        switch (random.nextInt(3)) {
            case 0:
                return new Armor(armorNames.get(random.nextInt(armorNames.size())), itemPower);
            case 1:
                return new Weapon(weaponNames.get(random.nextInt(weaponNames.size())), itemPower);
            default:
                return new Medicine(medicineNames.get(random.nextInt(medicineNames.size())), itemPower);
        }
    }

    public void fight() {
        enemy = createRandomEnemy();
        System.out.println("Losowe wydarzenie: Spotkales przeciwnika: " + enemy.getName() + "!");
        enemy.printStats();
        
        while (true) {
            System.out.println("Co robisz?\n1. Atakuj\n2. Ulecz sie\n3. Uciekaj");
            int choice = getUserInput(1, 3);
            
            if (choice == 1 && player.attack(enemy)) {
                System.out.println("Pokonano przeciwnika!");
                player.battleRewards();
                break;
            } else if (choice == 2) {
                player.inventory.printConsumables();
                int itemIndex = scanner.nextInt() - 1;
                player.useConsumable(itemIndex);
            } else if (player.runAway()) {
                System.out.println("Udalo sie uciec!");
                break;
            }
            
            System.out.println(enemy.getName() + " atakuje!");
            if (enemy.attack(player)) {
                System.out.println("Zostales pokonany :(");
                return;
            }
        }
        randomEvent();
    }

    public void treasure() {
        System.out.println("Losowe wydarzenie: Znaleziono skarb!");
        player.inventory.addItem(createRandomItem());
        randomEvent();
    }

    public void camp() {
        System.out.println("Losowe wydarzenie: Znaleziono dobre miejsce na oboz!\nUleczyc 25% HP za 10 zlota?\n1. Tak\n2. Nie");
        if (getUserInput(1, 2) == 1) {
            player.addHp(player.getMaxHp() / 4);
            System.out.println("Odpoczales i odzyskales troche sil.");
            player.printStats();
        }
        randomEvent();
    }

    public void shop() {
        System.out.println("Losowe wydarzenie: Znaleziono podrozujacego handlarza!");
        
        Item[] items = {createRandomItem(), createRandomItem(), createRandomItem()};
        int[] prices = {random.nextInt(26) + 15, random.nextInt(26) + 15, random.nextInt(26) + 15};
        
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + items[i].getName() + " - Moc: " + items[i].getPower() + " Cena: " + prices[i]);
        }
        System.out.println("4. Nic nie kupuj");
        
        int choice = getUserInput(1, 4);
        if (choice != 4 && player.gold >= prices[choice - 1]) {
            System.out.println("Kupiono " + items[choice - 1].getName());
            player.gold -= prices[choice - 1];
            player.inventory.addItem(items[choice - 1]);
        } else if (choice != 4) {
            System.out.println("Brakuje zlota!");
        }
        randomEvent();
    }

    public void exit() {
        System.out.println("Losowe wydarzenie: Znaleziono wyjscie!\n1. Wyjdz\n2. Idz dalej");
        if (getUserInput(1, 2) == 1) {
            System.out.println("Wychodzisz z lasu i konczysz gre.");
            scanner.close();
            System.exit(0);
        }
        randomEvent();
    }

    private int getUserInput(int min, int max) {
        int input;
        while (true) {
            try {
                if (!scanner.hasNextInt()) {
                    if (!scanner.hasNext()) {
                        do{
                            scanner.next();
                        }while(!scanner.hasNext());
                    }
                }
                input = scanner.nextInt();
                if (input >= min && input <= max) return input;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Nieprawidlowy wybor, sproboj ponownie.");
            }
        }
    }
}