package machine
import java.util.Scanner

enum class State {
    CHOOSING_ACTION,
    BUYING_COFFEE,
    FILLING_WATER,
    FILLING_MILK,
    FILLING_COFFEE_BEANS,
    ADDING_CUPS
}

class CoffeeMachine {
    var waterSupply = 400
    var milkSupply = 540
    var coffeeBeansSupply = 120
    var disposableCups = 9
    var balance = 550

    var currentState = State.CHOOSING_ACTION

    private fun printCoffeeMachineState() {
        println("The coffee machine has:")
        println("$waterSupply of water")
        println("$milkSupply of milk")
        println("$coffeeBeansSupply of coffee beans")
        println("$disposableCups of disposable cups")
        println("$balance of money")
        println()
        initState()
    }

    private fun buyACoffee(typeOfCoffee: String) {
        when (typeOfCoffee) {
            "1" -> reduceSupplies(250, 0, 16, 4)      // Espresso
            "2" -> reduceSupplies(350, 75, 20, 7)     // Latte
            "3" -> reduceSupplies(200, 100, 12, 6)    // Cappuccino
            "back" -> println()
        }
        initState()
    }

    private fun reduceSupplies(water: Int, milk: Int, coffeeBeans: Int, amount: Int) {
        when {
            disposableCups == 0 -> println("Sorry, not enough cups!")
            waterSupply < water -> println("Sorry, not enough water!")
            milkSupply < milk -> println("Sorry, not enough milk!")
            coffeeBeansSupply < coffeeBeans -> println("Sorry, not enough coffee beans!")
            else -> {
                println("I have enough resources, making you a coffee!")
                disposableCups--
                waterSupply -= water
                milkSupply -= milk
                coffeeBeansSupply -= coffeeBeans
                balance += amount
            }
        }
        println()
    }

    private fun fillWaterSupply(quantity: Int) {
        waterSupply += quantity
        print("Write how many ml of milk do you want to add: ")
        currentState = State.FILLING_MILK
    }

    private fun fillMilkSupply(quantity: Int) {
        milkSupply += quantity
        print("Write how many grams of coffee beans do you want to add: ")
        currentState = State.FILLING_COFFEE_BEANS
    }

    private fun fillCoffeeBeansSupply(quantity: Int) {
        coffeeBeansSupply += quantity
        print("Write how many disposable cups of coffee do you want to add: ")
        currentState = State.ADDING_CUPS
    }

    private fun addDisposableCups(quantity: Int) {
        disposableCups += quantity
        println()
        initState()
    }


    private fun takeMoney() {
        println("I gave you $$balance")
        balance = 0
        println()
        initState()
    }

    private fun choseAction(action: String) {
        when (action) {
            "buy" -> {
                print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                currentState = State.BUYING_COFFEE
            }
            "fill" -> {
                print("Write how many ml of water do you want to add: ")
                currentState = State.FILLING_WATER
            }
            "take" -> takeMoney()
            "remaining" -> printCoffeeMachineState()
            "exit" -> println()
            else -> {
                println("Unknown action")
                println()
            }
        }
    }

    fun initState() {
        print("Write action (buy, fill, take, remaining, exit): ")
        currentState = State.CHOOSING_ACTION
    }

    fun setUserInput(input: String) {
        when (currentState) {
            State.CHOOSING_ACTION -> choseAction(input)
            State.BUYING_COFFEE -> buyACoffee(input)
            State.FILLING_WATER -> fillWaterSupply(input.toInt())
            State.FILLING_MILK -> fillMilkSupply(input.toInt())
            State.FILLING_COFFEE_BEANS -> fillCoffeeBeansSupply(input.toInt())
            State.ADDING_CUPS -> addDisposableCups(input.toInt())
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.initState()

    do {
        val input = scanner.next()
        coffeeMachine.setUserInput(input)
    } while (input != "exit")
}


