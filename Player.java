import java.util.Random;
import java.util.Scanner;

import items.*;

public class Player extends Entity{

    Inventory inventory;
    int gold;
    Weapon weapon;
    Armor armor;
    int lvl;
    int xp;
    float goldMultiplier;


    public Player() {
        super("Gracz", 100, 5);
        this.inventory = new Inventory();
        this.gold = 10;
        this.armor = new Armor("Potargane Łachmany", 0);
        this.weapon = new Weapon("Patyk", 0);
        this.lvl = 1;
        this.xp = 0;
        this.goldMultiplier = 1;
    }

    public void printStats(){
        System.out.println("Twoje statystyki: lvl: " + lvl + " | HP = " + hp + " | atak = " + attackPower);
    }

    public void removeHp(int hpToRemove){
        this.hp -= hpToRemove;

        System.out.println("Otrzymujesz " + hpToRemove + " obrazen!");
    }

    public void addHp(int hpToAdd){
        this.hp += hpToAdd;

        if(this.hp > this.maxHp) this.hp = this.maxHp;

        System.out.println("Uleczono " + hpToAdd + " obrazen!");
    }


    public boolean runAway(){
        if(Math.random() < 0.5) return true;
        return false;
    }

    public void printWallet(){
        System.out.println("Twoja sakiewka:\nilosc zlota: "+gold+"\nMnoznik zlota: "+goldMultiplier);
    }
    
    public void printWorn(){
        System.out.println("Zbroja: " + this.armor.getName() + " moc: " + this.armor.getPower());
        System.out.println("Bron: " + this.armor.getName() + " moc: " + this.armor.getPower());
    }
    
    public void printInventory(){
        printWorn();
        System.out.println();
        inventory.printInventory(true);
    }

    public boolean equipItem(int index){
        if(!(index > inventory.getSize()-1)|| !(index < 0)){
            System.out.println("Nieprawidlowy Index");
            return false;
        }
        
        Item pom = inventory.getItem(index);
        if(pom instanceof Weapon){
            attackPower -= weapon.getPower();

            inventory.removeItem(index);
            inventory.addItem(weapon);
            weapon = ((Weapon)pom);

            attackPower += weapon.getPower();

            return true;
        }else if(pom instanceof Armor){
            maxHp -= armor.getPower();

            inventory.removeItem(index);
            inventory.addItem(armor);
            armor = ((Armor)pom);

            maxHp += armor.getPower();

            return true;
        }

        return false;
    }

    public boolean useConsumable(int index){
        if((index > inventory.getSize()-1) && (index < 0)){
            System.out.println("Nieprawidlowy Index");
            return false;
        }

        Item pom = inventory.getItem(index);
        if(pom instanceof Medicine){
            hp += pom.getPower();
            System.out.println("Uzyto przedmiot: " + pom.getName());
            printStats();
            inventory.removeItem(index);
            return true;
        }

        return false;
    }

    public void addXp(){
        Random r = new Random();
        this.xp += r.nextInt(11)+10;

        int pom = 100 + 20 * (lvl - 1);
        if(xp >= pom){
            xp-=pom;
            lvl++;
            levelUp();
        }
    }

    public void levelUp(){
        System.out.println("LEVEL UP!\nMasz teraz poziom: "+lvl+"\nCo zwiekszyc?\n1. Hp\n2. Atak\n3. Mnoznik zlota");
        Scanner s = new Scanner(System.in);
        Integer choice = null;

        do{
            if(choice != null) System.out.println("Nieprawidlowy Index!");
            choice = s.nextInt();
        }while(choice > 0 && choice <= 3);
        s.close();

        switch (choice) {
            case 1:
                System.out.println("Zwiekszono HP!");
                hp += 5;
                printStats();
                break;
            
            case 2:
                System.out.println("Zwiekszono atak!");
                hp += 2;
                printStats();
                break;

            case 3:
                System.out.println("Zwiekszono mnoznik zlota!");
                goldMultiplier += 0.05;
                System.out.println("Aktualny mnoznik to: "+goldMultiplier);

            default:
                break;

            }
    }

    public void battleRewards(){
        Random r = new Random();

        addXp();
        
        int goldReward = (int)((r.nextInt(6)+10) * goldMultiplier);
        System.out.println("Znaleziono " + goldReward + " złota!");
        gold += goldReward;
    }
}
