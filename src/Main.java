import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 280, 250, 150}; // Добавлен Medic с здоровьем 150
    public static int[] heroesDamage = {20, 10, 15, 0}; // У Medic нет урона
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Healer"}; // Добавлен тип атаки для Medic
    public static int roundNumber = 0;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void playRound() {
        roundNumber++;
        chooseDefence();
        bossAttacks();
        heroesAttack();
        medicHeal(); // Добавляем лечение Medic
        showStatistics();
    }

    public static void chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void medicHeal() {
        int medicIndex = heroesAttackType.length - 1; // Индекс Medic в массивах
        int lowestHealth = Integer.MAX_VALUE; // Наименьшее здоровье для проверки
        int heroToHeal = -1; // Индекс героя для лечения

        // Находим героя с наименьшим здоровьем < 100
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[i] < lowestHealth) {
                lowestHealth = heroesHealth[i];
                heroToHeal = i;
            }
        }

        // Лечим найденного героя, если Medic жив
        if (heroesHealth[medicIndex] > 0 && heroToHeal != -1) {
            int healAmount = 50; // Пример количества лечения (можно изменить)
            heroesHealth[heroToHeal] += healAmount;
            System.out.println("Medic healed hero " + heroToHeal + " for " + healAmount + " health points.");
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}
