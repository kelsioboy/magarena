[
    new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent creature) {
            return (permanent.isFriend(creature) &&
                    creature.getPower() <= 2) ?
                new MagicEvent(
                    permanent,
                    game.getDefendingPlayer(),
                    this,
                    "SN deals 1 damage to defending player."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicDealDamageAction(event.getSource(),game.getDefendingPlayer(),1));
        }
    }
]
