[
    new EntersBattlefieldTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return (game.getCreatureDiedThisTurn()) ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN returns a creature card at random from his or her graveyard to his or her hand."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            new MagicCardList(CREATURE_CARD_FROM_GRAVEYARD.filter(event)).getRandomCards(1) each {
                game.doAction(new ShiftCardAction(it, MagicLocationType.Graveyard,MagicLocationType.OwnersHand));
            }
        }
    }
]
