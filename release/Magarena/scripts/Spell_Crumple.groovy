[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                NEG_TARGET_SPELL,
                this,
                "Counter target spell.\$ If that spell is countered this way, put it on the bottom of "+
                "its owner's library instead of into that player's graveyard. Put SN on the bottom of "+
                "its owner's library."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCardOnStack(game, {
                game.doAction(new CounterItemOnStackAction(it, MagicLocationType.BottomOfOwnersLibrary));
                game.doAction(new ChangeCardDestinationAction(event.getCardOnStack(), MagicLocationType.BottomOfOwnersLibrary));
            });
        }
    }
]
