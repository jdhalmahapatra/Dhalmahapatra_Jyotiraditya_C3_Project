import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void BeforeEach(){
        LocalTime openingTime = getParse("22:30:00");
        LocalTime closingTime = getParse("23:00:00");
        restaurant =createRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }


    //REFACTOR ALL THE REPEATED LINES OF CODE
    private LocalTime getParse(String s) {
        return LocalTime.parse(s);
    }
    private Restaurant createRestaurant(String name, String city, LocalTime openingTime, LocalTime closingTime) {
        return new Restaurant(name,city,openingTime,closingTime);
    }
    private void addToMenu(String itemName, int price) {
        restaurant.addToMenu(itemName,price);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Boolean isOpen = restaurant.isRestaurantOpen();
        assertThat(isOpen, equalTo(true));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Boolean isClose = restaurant.isRestaurantOpen();
        assertThat(isClose, equalTo(false));

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        addToMenu("Sweet corn soup",119);
        addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        addToMenu("Sweet corn soup",119);
        addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        addToMenu("Sweet corn soup",119);
        addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void if_menu_details_available_should_return_list_of_item_on_calling_get_menu_method(){
        addToMenu("Sweet corn soup",119);
        addToMenu("Vegetable lasagne", 269);
        List<Item> menu = restaurant.getMenu();
        assertNotNull(menu);
    }
    @Test
    public void if_menu_details_not_available_should_return_null_on_calling_get_menu_method(){
        List<Item> menu = restaurant.getMenu();
        assertNull(menu);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void total_price_of_butter_chicken_and_Chilly_Chicken_is_600_rupees(){
        addToMenu("Butter Chicken", 200);
        addToMenu("Chilly Chicken", 400);
        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Butter Chicken");
        selectedItems.add("Chilly Chicken");
        Double price = restaurant.getOrderValue(selectedItems);
        assertEquals(600, price);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}