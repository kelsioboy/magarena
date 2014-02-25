[
    new MagicCardActivation(
        [MagicCondition.CARD_CONDITION],
        new MagicActivationHints(MagicTiming.Counter,true),
        "Pay 2 life"
    ) {
        public Iterable<MagicEvent> getCostEvent(final MagicCard source) {
            return [
                new MagicPayManaCostEvent(source, "{1}"),
                new MagicPayLifeEvent(source, 2)
            ];
        }
    }
]
