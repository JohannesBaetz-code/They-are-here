package weapons;

public class Magazine {
    private int currentBullets;
    private final int maxBullets;

    public Magazine(int maxBullets){
        this.maxBullets = currentBullets = maxBullets;
    }

    public boolean isEmpty(){
        return currentBullets == 0;
    }

    public void removeBullet(){
        currentBullets--;
    }

    public void refill(){
        currentBullets = maxBullets;
    }

    public double getFillStatus() {
        return Double.valueOf(currentBullets) / Double.valueOf(maxBullets);
    }
}
