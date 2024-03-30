import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * class customer that allows user to buy and checkout
 */
public class Customer {
  private String name;
  private int age;
  private ShoppingCart shoppingCart;

  /**
   *
   * @param name name of the customer
   * @param age age of the customer
   * @param shoppingCart class shopping cart that keep track
   */
  public Customer(String name, int age, ShoppingCart shoppingCart) {
    this.name = name;
    this.age = age;
    this.shoppingCart = shoppingCart;
  }

  /**
   *
   * @return the name of customer
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return the age of customer
   */
  public int getAge() {
    return age;
  }

  /**
   *
   * @return the shopping cart
   */
  public ShoppingCart getShoppingCart() {
    return shoppingCart;
  }

  /**
   *check stock inside grocery
   * @param product product that need to be checked
   * @param amount amount of quantity that need to be checked
   * @param inventory inventory that hold the product
   * @return true if there is enough
   * @throws Exception if there is not enough stock
   */
  public boolean checkGroceryQuantity(Product product, int amount, Inventory inventory) throws Exception{
    for(StockItem stockItem: inventory.getGroceryStock()){
      if(product.equals(stockItem)){
        if(stockItem.getQuantity() < amount){
          throw new Exception("Current stock is not enough!");
        }
      }
    }
    return true;
  }

  /**
   *check stock inside household
   * @param product product that need to be checked
   * @param amount amount of quantity that need to be checked
   * @param inventory inventory that hold the product
   * @return true if there is enough
   * @throws Exception if there is not enough stock
   */
  public boolean checkHouseholdQuantity(Product product, int amount, Inventory inventory) throws Exception{
    for(StockItem stockItem: inventory.getHouseholdStock()){
      if(product.equals(stockItem)){
        if(stockItem.getQuantity() < amount){
          throw new Exception("Current stock is not enough!");
        }
      }
    }
    return true;
  }

  /**
   * add products to the cart
   * @param product product that needed to be added
   * @param amount amount of products to be added
   * @param inventory check the inventory
   */
  public void addItemToCart(Product product, int amount, Inventory inventory){
    try{
      checkGroceryQuantity(product, amount, inventory);
      checkHouseholdQuantity(product, amount, inventory);
      shoppingCart.addItem(product, amount);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  /**
   * add one item to the shopping cart
   * @param product product that add to shopping cart
   * @param inventory inventory that check if exist
   */
  public void addItemToCart(Product product, Inventory inventory){
    this.addItemToCart(product,1, inventory);
  }

  /**
   * checkout the shopping cart
   * @param inventory of the store
   * @return the receipt that contain info about products and prices
   */
  public Receipt checkOut(Inventory inventory){

    ArrayList<Product> productReceived = new ArrayList<Product>();
    ArrayList<Product> productOutOfStock = new ArrayList<Product>();
    ArrayList<Product> productRemoved = new ArrayList<Product>();
    HashMap<Product, Integer> shoppingItems = shoppingCart.getCartMap();
    // check what items are removed in household
    for (Map.Entry<Product, Integer> entry: shoppingItems.entrySet()){
      Product product = entry.getKey();
      for(StockItem stockItem: inventory.getHouseholdStock()){
        if(stockItem.getProduct().equals(product)){
          if(stockItem.getQuantity() == 0){
            productOutOfStock.add(product);
            productRemoved.add(product);
            this.shoppingCart.cartMap.remove(product);
          }
        }
      }
      // check what items are removed in grocery
      for(StockItem stockItem: inventory.getGroceryStock()){
        if(stockItem.getProduct().equals(product)){
          if(stockItem.getQuantity() == 0){
            productOutOfStock.add(product);
            productRemoved.add(product);
            this.shoppingCart.cartMap.remove(product);
          }
        }
      }
      // removed item if min age is not required
      if(product.getMinimumAge() > this.getAge()){
        productRemoved.add(product);
        this.shoppingCart.cartMap.remove(product);
      }
    }
    // add left product into the receipt
    for (Map.Entry<Product, Integer> entry: shoppingItems.entrySet()){
      Product product = entry.getKey();
      int amount = entry.getValue();
      for(int i = 0; i < amount; i++){
        productReceived.add(product);
      }
    }
    // build the receipt
    double totalPrice = this.shoppingCart.calculatePrice();
    Receipt receipt = new Receipt(totalPrice, productReceived, productOutOfStock, productRemoved);
    return receipt;
  }
  /**
   * {@inheritDoc}
   */

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return age == customer.age && Objects.equals(name, customer.name)
        && Objects.equals(shoppingCart, customer.shoppingCart);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, age, shoppingCart);
  }
}
