package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.candies.*;

public sealed interface Candy permits EmptyCandy, MultiCandy, RareCandy, RowSnapper, TurnMaster, normalCandy {
}
