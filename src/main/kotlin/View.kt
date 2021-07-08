import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlin.system.exitProcess


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun mainWindow() {

    Window(onCloseRequest = {exitProcess(0)}) {

        MenuBar {
            Menu("View") {
                Item("Traders", true, {ApplicationState.state = ApplicationState.TRADERS})
                Item("Goods", true, {ApplicationState.state = ApplicationState.GOODS})
                Item("Trades", true, {ApplicationState.state = ApplicationState.TRADES})
            }
        }
        var height by remember { mutableStateOf(0) };
        MaterialTheme {
            Column(Modifier.background(Color.LightGray).fillMaxSize().onSizeChanged { height = it.height / 2 }) {
                var h = 500;

                if (height > 0) {
                    h = height - 70;
                    if (h < 300) h = 300;
                }
                Row(Modifier.height(h.dp).fillMaxWidth()) {
                    when (ApplicationState.state) {
                        ApplicationState.TRADERS -> dataListPanel("Traders");
                        ApplicationState.TRADES -> dataListPanel("Trades")
                        ApplicationState.GOODS -> dataListPanel("Goods")
                    }


                    when (ApplicationState.state) {
                        ApplicationState.TRADERS -> if (ApplicationState.chosenTrader != null) traderPanel(
                            Modifier.fillMaxSize().background(Color.Gray)
                        )
                        ApplicationState.TRADES -> if (ApplicationState.chosenTrade != null) detailsPanel(
                            Modifier.fillMaxSize().background(Color.Gray)
                        )
                        ApplicationState.GOODS -> if (ApplicationState.chosenGood != null) detailsPanel(
                            Modifier.fillMaxSize().background(Color.Gray)
                        )
                    }
                }
                Row(Modifier.requiredHeight(70.dp).fillMaxWidth().background(Color.DarkGray)) {
                    stateButton(
                        "Traders "  + DataModel.traders.size + " : " + DataModel.count2.value,
                        onClick = { ApplicationState.state = 0 },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    );
                    stateButton(
                        "Goods ",
                        onClick = { ApplicationState.state = 1 },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    );
                    stateButton(
                        "Trades ",
                        onClick = { ApplicationState.state = 2 },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    );
                    Spacer(Modifier.width(50.dp));

                    when (ApplicationState.state) {
                        ApplicationState.TRADERS -> if (ApplicationState.chosenTrader != null || true) {
                            detailsButton(
                                "Reset",
                                { ApplicationState.detailsState = ApplicationState.VIEW },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            );
                            detailsButton(
                                "Edit",
                                { ApplicationState.detailsState = ApplicationState.EDIT },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            );
                            detailsButton(
                                "Add",
                                { /*ataModel.count.value = DataModel.count.value + 1 ; */ DataModel.addTrader(); },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            );
                        }
                        ApplicationState.TRADES -> if (ApplicationState.chosenTrade != null) {
                            detailsButton("Trade Btn1", {}, modifier = Modifier.align(Alignment.CenterVertically));
                            detailsButton("Trade Btn2", {}, modifier = Modifier.align(Alignment.CenterVertically));
                            detailsButton("Trade Btn3", {}, modifier = Modifier.align(Alignment.CenterVertically));
                        }
                        ApplicationState.GOODS -> if (ApplicationState.chosenGood != null) {
                            detailsButton("Good Btn1", {}, modifier = Modifier.align(Alignment.CenterVertically));
                            detailsButton("Good Btn2", {}, modifier = Modifier.align(Alignment.CenterVertically));
                            detailsButton("Good Btn3", {}, modifier = Modifier.align(Alignment.CenterVertically));
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun stateButton(text: String, onClick: () -> Unit = {}, modifier: Modifier) {
    Button(modifier = modifier, onClick = onClick) {
        Text(text)
    }
    Spacer(modifier = Modifier.width(5.dp))
}

@Composable
fun detailsButton(text: String, onClick: () -> Unit = {}, modifier: Modifier) {
    Button(modifier = modifier, onClick = onClick) {
        Text(text)
    }
    Spacer(modifier = Modifier.width(5.dp))
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun dataListPanel(title: String) {
    LazyColumn {
        stickyHeader {
            Box (modifier = Modifier.width(300.dp).height(40.dp).background(Color.DarkGray)) {
                Text(title, modifier = Modifier.align(Alignment.Center))
            }
        }
        var dataList : List<BasicData> = mutableListOf();
        var chosen: BasicData? = null;
        var choose: ((BasicData?) -> Unit) = {};
        when (ApplicationState.state) {
            ApplicationState.TRADERS -> { dataList = DataModel.traders; chosen = ApplicationState.chosenTrader;
                                              choose = {if (it is Trader) ApplicationState.chosenTrader = it}}
            ApplicationState.GOODS -> { dataList = DataModel.goods; chosen = ApplicationState.chosenGood;
                                              choose = {if (it is Good) ApplicationState.chosenGood = it}}
            ApplicationState.TRADES -> { dataList = DataModel.trades; chosen = ApplicationState.chosenTrade;
                                              choose = {if (it is Trade) ApplicationState.chosenTrade = it}}

        }

        for (data in dataList) {
            item {
                dataListItem(data == chosen, choose, data);
            }
        }
    }
}

@Composable
fun dataListItem(chosen: Boolean, onClick2: (id2: BasicData?)-> Unit, data: BasicData?) {
    var modifier = Modifier.padding(2.dp).clip(RoundedCornerShape(10.dp)).width(296.dp).height(30.dp);
    if (chosen) {
        modifier = modifier.background(Color.Green)
    } else {
        modifier = modifier.background(Color.Gray)
    }
    Box (modifier = modifier) {
        Text("" + data?.name?.value, modifier = Modifier.align(Alignment.CenterStart).clickable { onClick2(data)});
    }
}

@Composable
fun traderPanel(modifier : Modifier) {
    val trader = ApplicationState.chosenTrader;
    if (trader == null) return
    Box(modifier) {
        Column {
            if (ApplicationState.detailsState == ApplicationState.VIEW) {
                Text("" + ApplicationState.chosenTrader?.name?.value);
            } else if (ApplicationState.detailsState == ApplicationState.EDIT) {
                Text("" + ApplicationState.chosenTrader?.name?.value);
                TextField(value = "" + ApplicationState.chosenTrader?.name?.value, onValueChange = {ApplicationState.chosenTrader?.name?.value = it});
            } else {
                Text("Bad state");
            }
        }
    }
}

@Composable
fun detailsPanel(modifier : Modifier) {
    Box(modifier) {
        Column {
            Text("Chosen Trader: " + ApplicationState.chosenTrader)
            Text("Chosen Good: " + ApplicationState.chosenGood)
            Text("Chosen Trade: " + ApplicationState.chosenTrade)
        }
    }
}