import java.util.ArrayList;
import java.util.Scanner;

import items.*;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void printInventory(){
        for(int i = 0 ; i < items.size() ; i++){
            System.out.println((i+1) + ". " + items.get(i).getName());
        }
    }

    public void printInventory(boolean showFreeSlots){
        printInventory();
        if(showFreeSlots) System.out.println("Zajete jest: " + items.size() + "/10 miejsc w ekwipunku");
    }

    public int countConsumables(){
        int i = 0;
        for(Item item : items){
            if(item instanceof Medicine){
                i++;
            }
        }

        return i;
    }

    public int printConsumables(){
        int i = 0;

        for(Item item : items){
            if(item instanceof Medicine){
                i++;
                System.out.println(i + ". " + item.getName() + " moc: " + item.getPower());
            }
        }

        return i;
    }

    public void addItem(Item item){
        System.out.println("Otrzymujesz nowy przedmiot: " + item.getName() + " o mocy: " + item.getPower());

        if(getSize() < 10)items.add(item);
        else{
            System.out.println("Ekwipunek pelny, wyrzuc jakis przedmiot");
            System.out.println("0. " + item.getName());
            printInventory();

            Scanner s = new Scanner(System.in);
            int index;
            do{
                index = s.nextInt()-1;   
            }while(!removeItem(index) && index != -1);

            System.out.println("Wyrzucono przedmiot");
            if(index != -1) items.add(item);
        }

    }

    public boolean removeItem(int index){
        if(index > items.size()-1 || index < 0){
            System.out.println("Nieprawidlowy Index");
            return false;
        }

        items.remove(index);
        return true;
    }

    public Item getItem(int index){
        return items.get(index);
    }

    public int getSize(){
        return items.size();
    }
}
