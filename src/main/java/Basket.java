import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Basket {
    private int price[];
    private String[] products;
    private int cart[];
    private int total = 0;

    public Basket(int[] price, String[] products) {
        this.price = price;
        this.products = products;
        cart = new int[products.length];
    }

    public void cart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < cart.length; i++) {
            if (cart[i] != 0) {
                System.out.println(products[i] + " " + cart[i] + " шт " + price[i] + " руб/шт " + cart[i] * price[i] + " руб в сумме");
            }
        }
        System.out.println("Итого: " + total + " руб");
    }

    protected void addCart(int productCount, int productNumber) {
        cart[productNumber] += productCount;
        total += productCount * price[productNumber];
    }

    protected void saveTxt(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file.getName(), false)) {
            for (int priceTxt : price) {
                writer.write(priceTxt + " ");
            }
            writer.write("\n");
            for (String productsTxt : products) {
                writer.write(productsTxt + " ");
            }
            writer.write("\n");
            for (int cartTxt : cart) {
                writer.write(cartTxt + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected void saveJson(File file) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String basketJson = gson.toJson(this);

        try (FileWriter writer = new FileWriter(file.getName())) {
            writer.write(basketJson);
            writer.flush();
        }
    }

    protected static Basket loadFromJsonFile(File file) throws IOException, NullPointerException, ParseException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(file.getName()));
            JSONObject jsonObject = (JSONObject) obj;
            String jsonStr = jsonObject.toString();

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Basket basket = gson.fromJson(jsonStr, Basket.class);
            return basket;


        } catch (IOException | ParseException | NullPointerException e) {
            e.getMessage();
        }
        return null;
    }


    protected static Basket loadFromTxtFile(File file) throws IOException, NumberFormatException {
        try (BufferedReader br = new BufferedReader(new FileReader(file.getName()))) {
            String[] readStr1 = br.readLine().split(" ");
            int[] price = new int[readStr1.length];
            for (int i = 0; i < readStr1.length; i++) {
                price[i] = Integer.parseInt(readStr1[i]);
            }

            String[] readStr2 = br.readLine().split(" ");
            String[] products = new String[readStr2.length];
            for (int i = 0; i < readStr2.length; i++) {
                products[i] = readStr2[i];
            }

            String[] readStr3 = br.readLine().split(" ");
            int[] cart = new int[readStr3.length];
            for (int i = 0; i < readStr3.length; i++) {
                cart[i] = Integer.parseInt(readStr3[i]);
            }
            Basket basket = new Basket(price, products);
            for (int i = 0; i < products.length; i++) {
                basket.addCart(cart[i], i);
            }
            return basket;
        }


    }
}