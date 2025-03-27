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

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public void printStats(){
        System.out.println(name + ": HP = " + hp + " | atak = " + attackPower);
    }

    public void removeHp(int hpToRemove){
        this.hp -= hpToRemove;

        if(this.hp > this.maxHp) this.hp = this.maxHp;

        System.out.println("Zadales " + hpToRemove + " obrazen!");
    }

    public void addHp(int hpToAdd){
        this.hp += hpToAdd;

        if(this.hp > this.maxHp) this.hp = this.maxHp;

        System.out.println(this.name + "uleczyl " + hpToAdd + " obrazen!");
    }

    public boolean attack(Entity entity){
        int attackHalved = attackPower/2;
        Random r = new Random();

        int hit = r.nextInt(attackPower + 1) + attackHalved;
        entity.removeHp(hit);

        if(entity.getHp() <= 0){
            return true;
        }

        entity.printStats();
        return false;
    }
}
