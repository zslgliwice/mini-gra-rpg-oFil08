import java.util.Random;

public class Entity {
    protected String name;
    protected int maxHp;
    protected int hp;
    protected int attackPower;

    public Entity(String name, int hp, int attackPower) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    public void printStats(){
        if(name.equals("Gracz")) System.out.println("Twoje statystyki: HP = " + hp + " | atak = " + attackPower);
        else System.out.println(name + ": HP = " + hp + " | atak = " + attackPower);
    }

    public void removeHp(int hpToRemove){
        this.hp -= hpToRemove;
    }

    public void addHp(int hpToAdd){
        this.hp += hpToAdd;
    }

    public void attack(Entity entity){
        int attackHalved = attackPower/2;
        Random r = new Random();
        int hit = r.nextInt(attackPower + 1) + attackHalved;
        entity.removeHp(hit);
        
        if(name.equals("Gracz")) System.out.println("Zadałeś " + hit + " obrażeń");
        else System.out.println(name + " zadał " + hit + " obrażeń");
        entity.printStats();
    }
}
