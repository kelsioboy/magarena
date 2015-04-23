def OPPONENT_WHO_CONTROLS_MORE_CREATURES = new MagicPlayerFilterImpl() {
    public boolean accept(final MagicSource source,final MagicPlayer player,final MagicPlayer target) {
        return target.isEnemy(player) &&
               target.getNrOfPermanents(MagicType.Creature) >= player.getNrOfPermanents(MagicType.Creature);
    } 
};

def TARGET_OPPONENT_WHO_CONTROLS_MORE_CREATURES = new MagicTargetChoice(
    OPPONENT_WHO_CONTROLS_MORE_CREATURES,
    "target opponent who controlls more creatures than you"
);

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Token),
        "Token"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source,"{G}"),
                new MagicTapEvent(source)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                TARGET_OPPONENT_WHO_CONTROLS_MORE_CREATURES,
                this,
                "PN puts a 2/2 green Beast creature token onto the battlefield."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.addEvent(MagicRuleEventAction.create(
                "Put a 2/2 green Beast creature token onto the battlefield.").getEvent(event.getSource())
            );
        }
    }
]