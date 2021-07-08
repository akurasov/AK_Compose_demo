import androidx.compose.runtime.*
import java.lang.RuntimeException

interface BasicData {
    var name: MutableState<String>;
    val id: Int;
}

data class Trader(var money: Int = 0, var wheat: Int = 0, var pork: Int = 0,
                  var wheat_prod: Int = 0, var pork_prod: Int = 0, override val id: Int) : BasicData {
                        override var name = mutableStateOf("")
                    }
data class Good(val price: MutableState<Int?> = mutableStateOf(null), override val id: Int) : BasicData {
    override var name = mutableStateOf("")
}
data class Trade(val count: Int = 0, val price: Int = 0, override val id: Int) : BasicData {
    override var name = mutableStateOf("")
}

object DataModel {
    val traders = mutableStateListOf<Trader>();
    val goods = mutableStateListOf<Good>();
    val trades = mutableStateListOf<Trade>();
    var count2 = mutableStateOf(0);
    fun addTrader() {
        try {
            throw RuntimeException();
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        val p = Trader(id = traders.size + 1);
        p.name.value = "Trader " + p.id;
        traders.add(p);
        count2.value++;
        try {
         throw RuntimeException();
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
    fun addGood() {
        val p = Good(id = goods.size + 1);
        p.name.value = "Good " + p.id;
        goods.add(p);
    }
    fun addTrade() {
        val p = Trade(id = trades.size + 1);
        p.name.value = "Trade " + p.id;
        trades.add(p);
    }
    fun init() {
        val p = Trader(id = traders.size + 1);
        p.name.value = "Trader " + p.id;
//        traders.add(p);
        for (i in 0..9) {

//            addTrader();
            addGood();
            addTrade();
        }
    }
}