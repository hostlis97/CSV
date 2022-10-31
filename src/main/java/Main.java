import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String products[] = {"Хлеб", "Яблоки", "Молоко"};
        int price[] = {100, 200, 300};
        Basket cart = new Basket(price, products);
        File file = new File("basket.bin");

        if (file.exists()) {
            System.out.println("Файл существует");
            try {
                cart = Basket.loadFromBinFile(file);
            } catch (ClassNotFoundException e) {
                e.getMessage();
            }
        } else {
            System.out.println("Файл не существует");
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл для сохранения корзины создан!");
                } else {
                    System.out.println("Файл для сохранения корзины не создан!");
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + price[i] + " руб/шт");
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = s.nextLine();
            if ("end".equals(input)) {
                cart.cart();
                break;
            }
            String parts[] = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Нужно указать 2 числа через пробел.\n Первое - номер торвара.\n Второе - кол-во штук.");
                continue;
            }
            try {
                int productNumber = Integer.parseInt(parts[0]) - 1;
                int productCount = Integer.parseInt(parts[1]);
                cart.addCart(productCount, productNumber);
                cart.saveBin(file, cart);
                if ((productNumber > products.length - 1) || (productNumber < 0)) {
                    System.out.println("Товара с таким номером нет в списке");
                    continue;
                }
                if (productCount < 0) {
                    System.out.println("Вы ввели отрицательное кол-во товара");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("неправильный формат двнных");
                continue;
            }
        }
    }
}
