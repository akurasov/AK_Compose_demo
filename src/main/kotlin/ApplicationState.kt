import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ApplicationState {
    val TRADERS = 0;
    val GOODS = 1;
    val TRADES = 2;

    val VIEW = 0;
    val EDIT = 1;

    var state by mutableStateOf(TRADERS);
    var chosenTrader by mutableStateOf(null as Trader?)
    var chosenTrade by mutableStateOf(null as Trade?)
    var chosenGood by mutableStateOf(null as Good?)
    var detailsState by mutableStateOf(VIEW);
}