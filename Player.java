import items.*;

public class Player extends Entity{

    Inventory inventory;
    int gold;
    Weapon weapon;
    Armor armor;
    int lvl;
    int xp;


    public Player() {
        super("Gracz", 100, 5);
        this.inventory = new Inventory();
        this.gold = 10;
        this.armor = new Armor("Potargane Łachmany", 0);
        this.weapon = new Weapon("Patyk", 0);
    }
    
    public void printWorn(){
        System.out.println("Zbroja: " + this.armor.getName() + " moc: " + this.armor.getPower());
        System.out.println("Broń: " + this.armor.getName() + " moc: " + this.armor.getPower());
    }
    
    public void printInventory(){
        printWorn();
        System.out.println();
        inventory.printInventory(true);
    }

    public boolean equipItem(int index){
        if(!(index > inventory.getSize()-1)|| !(index < 0)){
            System.out.println("Nieprawidłowy Index");
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

    public void useConsumable(int index){
        if(!(index > inventory.getSize()-1)|| !(index < 0)){
            System.out.println("Nieprawidłowy Index");
            return;
        }

        Item pom = inventory.getItem(index);
        if(pom instanceof Medicine){
            hp += pom.getPower();
            System.out.println("Użyto przedmiot: " + pom.getName());
            printStats();
            inventory.removeItem(index);
            return;
        }

        return;
    }
}
