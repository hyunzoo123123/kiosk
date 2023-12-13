import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    println("어서오세요! 명품떡볶이입니다! 메뉴를 선택하세요!")
    start()
}

fun start() {

    println("1 1인분")
    println("2 닭강정")
    println("3 음료")
    println("4 결제")

    try {
        when (readLine()!!.toInt()) {
            1 -> {
                println("1 떡볶이")
                println("2 순대")
                println("3 어묵(3꼬치)")
                val collectedSingle = singleMenu(readLine()!!.toInt())
                addTOCart(collectedSingle)
            }

            2 -> {
                println("1 순한맛")
                println("2 매운맛")
                println("3 간장맛")
                val collectedChicken = chickenMenu(readLine()!!.toInt())
                addTOCart(collectedChicken)
            }

            3 -> {
                println("1 콜라")
                println("2 사이다")
                println("3 환타(파인)")
                val collectedDrink = drinkMenu(readLine()!!.toInt())
                addTOCart(collectedDrink)
            }

            4 -> {
                println("장바구니에 담긴 메뉴를 확인합니다.")
                displayCart()
                println("위와 같이 주문하시겠습니까?")
                println("1 주문 2 메뉴판으로 이동 3 장바구니 비우기")
                order(readLine()!!.toInt())
            }

            0 -> println("키오스크를 종료합니다.")

            else -> {
                println("잘못된 번호를 입력했어요. 다시 입력해주세요.")
                start()
            }
        }
    } catch (e: NumberFormatException) {
        println("숫자를 입력해주세요. 다시 시도하세요.")
        start()
    }
}

fun singleMenu(x: Int): mpFood {
    return when (x) {
        1 -> SingleMenu("떡볶이", 3000)
        2 -> SingleMenu("순대", 3500)
        3 -> SingleMenu("어묵", 2000)
        else -> SingleMenu("잘못된 입력입니다.", 0)
    }
}

fun chickenMenu(y: Int): mpFood {
    return when (y) {
        1 -> ChickenMenu("순한맛", 3000)
        2 -> ChickenMenu("매운맛", 3000)
        3 -> ChickenMenu("간장맛", 3000)
        else -> ChickenMenu("잘못된 입력입니다.", 0)
    }
}

fun drinkMenu(z: Int): mpFood {
    return when (z) {
        1 -> DrinkMenu("콜라", 2000)
        2 -> DrinkMenu("사이다", 2000)
        3 -> DrinkMenu("환타(파인)", 2000)
        else -> DrinkMenu("잘못된 입력입니다.", 0)
    }
}

abstract class mpFood() {
    var name = ""
    var price = 0
    abstract fun displayInfo()
}

class SingleMenu() : mpFood() {
    override fun displayInfo() {
        println("${name} 입니다. 가격은 ${price} 입니다.")
    }

    constructor(name: String, price: Int) : this() {
        super.name = name
        super.price = price
    }
}

class ChickenMenu() : mpFood() {
    override fun displayInfo() {
        println("${name} 입니다. 가격은 ${price} 입니다.")
    }

    constructor(name: String, price: Int) : this() {
        super.name = name
        super.price = price
    }
}

class DrinkMenu() : mpFood() {
    override fun displayInfo() {
        println("${name} 입니다. 가격은 ${price} 입니다.")
    }

    constructor(name: String, price: Int) : this() {
        super.name = name
        super.price = price
    }
}

val shoppingCart = mutableListOf<mpFood>()

fun addTOCart(itemCart: mpFood) {
    shoppingCart.add(itemCart)
    println("${itemCart.name}을 장바구니에 담았습니다. 가격은${itemCart.price}원 입니다.")
    start()
}

fun displayCart() {
    println("장바구니 내역:")
    for (item in shoppingCart) {
        println("${item.name} ${item.price}원")
    }
    var totalPrice = 0
    for (item in shoppingCart) {
        totalPrice += item.price
    }
    println("총 가격: ${totalPrice} 원")
}

fun order(a: Int) {
    when (a) {
        1 -> {
            println("결제를 진행하겠습니다. 금액을 넣어 주십시요.")
            pay(readLine()!!.toInt())
        }

        2 -> {
            println("메뉴판으로 돌아갑니다.")
            start()
        }

        3 -> {
            println("장바구니를 비웁니다.")
            removeFromCart()
            start()
        }
    }
}

fun removeFromCart() {
    println("장바구니에서 삭제할 항목을 선택해주세요:")
    displayCart()

    try {
        val itemIndex = readLine()!!.toInt()

        if (itemIndex in 1..shoppingCart.size) {
            val removedItem = shoppingCart.removeAt(itemIndex - 1)
            println("${removedItem.name}을 장바구니에서 삭제했습니다.")
        } else {
            println("유효하지 않은 항목 번호입니다. 다시 시도하세요.")
        }
    } catch (e: NumberFormatException) {
        println("숫자를 입력해주세요. 다시 시도하세요.")
    } finally {
        start()
    }
}

fun pay(putMoney: Int) {
    var totalPrice = 0
    for (item in shoppingCart) {
        totalPrice += item.price
    }
    val count = putMoney - totalPrice

    if (count >= 0) { // 구매)

        val now = LocalDateTime.now() // java.time 패키지를 사용해 LocalDateTime.now() 사용하여 현재 시간을 얻어오고
        val formattedNow =
            now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) // 시간정보를 원하는 형식으로 출력하도록 지정한다.

        println("결제가 완료되었습니다. 거스름돈은 ${count}원 입니다.")
        println("명품떡볶이를 이용해주셔서 감사합니다! :) (${formattedNow})")
        shoppingCart.clear() // 결제 완료 후 장바구니 비우기
    } else {
        println("넣은 금액은 ${putMoney}원으로 ${Math.abs(count)}원이 부족해서 주문할 수 없습니다.")
        println("1 결제를 재시도 합니다. 2 메뉴판으로 돌아갑니다.")
        when (readLine()!!.toInt()) {
            1 -> order(1) // 결제를 다시 시도
            2 -> order(2) // 메뉴판으로 이동 //
        }
    }
}


//class dduckBokee{
//    val name:String = "떡볶이"
//    val price:Int = 3000
//}
//class soonDea{
//    val name:String = "순대"
//    val price:Int = 3500
//}
//class fishCake{
//    val name:String = "어묵(3꼬치)"
//    val price:Int = 2000
//}
//class mildTaste{
//    val name:String = "순한맛"
//    val price:Int = 3000
//}
//class hotTaste{
//    val name:String = "매운맛"
//    val price:Int = 3000
//}
//class soyTaste{
//    val name:String = "간장맛"
//    val price:Int = 3000
//}
//class coke{
//    val name:String = "콜라"
//    val price:Int = 2000
//}
//class cyder{
//    val name:String = "사이다"
//    val price:Int = 2000
//}
//class fanta{
//    val name:String = "환타(파인)"
//    val price:Int = 2000
//

