package com.example.pizzeria;

import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.entity.Product;

import org.junit.Before;
import org.junit.Test;

public class BasketUnitTest {

    @Before
    public void cleanup() {
        Basket.getInstance().emptyBasket();
    }

    @Test
    public void testAddingToBasket() {
        Basket basket = Basket.getInstance();
        Product product = new Product("Test product", 1f, 1, "Ing 1,Ing2", 0);
        basket.addItem(new BasketData(product, 1));

        assert basket.getBasketItems().length == 1;
        assert basket.getBasketItems()[0].getProduct().staticId == 1;
    }

    @Test
    public void testGettingBasketItems() {
        Basket basket = Basket.getInstance();
        Product product = new Product("Test product", 1f, 1, "Ing 1,Ing2", 0);
        basket.addItem(new BasketData(product, 1));

        BasketData[] items = basket.getBasketItems();

        assert items.length == 1;
        assert items[0].getQuantity() == 1;
    }

    @Test
    public void testUpdateBasketItemCount() {
        Basket basket = Basket.getInstance();
        Product product = new Product("Test product", 1f, 1, "Ing 1,Ing2", 0);
        basket.addItem(new BasketData(product, 1));

        basket.updateItemCount(0, 5);

        BasketData[] items = basket.getBasketItems();

        assert items.length == 1;
        assert items[0].getQuantity() == 5;
    }

    @Test
    public void testRemoveBasketItem() throws InterruptedException {
        Basket basket = Basket.getInstance();
        Product product = new Product("Test product", 1f, 1, "Ing 1,Ing2", 0);
        BasketData basketItem = new BasketData(product, 1);
        basket.addItem(basketItem);

        assert basket.getBasketItems().length == 1;

        basket.removeItem(basketItem);
        assert basket.getBasketItems().length == 0;
    }

    @Test
    public void testEmptyingBasket() {
        Basket basket = Basket.getInstance();
        basket.emptyBasket();

        assert basket.getBasketItems().length == 0;
    }

}
