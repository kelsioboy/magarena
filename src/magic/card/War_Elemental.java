package magic.card;

import magic.model.MagicCounterType;
import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicPlayerState;
import magic.model.action.MagicChangeCountersAction;
import magic.model.action.MagicSacrificeAction;
import magic.model.event.MagicEvent;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;
import magic.model.trigger.MagicWhenDamageIsDealtTrigger;

public class War_Elemental {
    public static final MagicWhenComesIntoPlayTrigger T1 = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPlayer player) {
            final MagicPlayer opponent = player.getOpponent();
            return (!opponent.hasState(MagicPlayerState.WasDealtDamage)) ?
                new MagicEvent(
                    permanent,
                    permanent.getController(),
                    this,
                    "Sacrifice " + permanent + "."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            game.doAction(new MagicSacrificeAction(event.getPermanent()));
        }
    };
    
    public static final MagicWhenDamageIsDealtTrigger T2 = new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicDamage damage) {
            final MagicPlayer player = permanent.getController();
            final MagicPlayer opponent = player.getOpponent();
            final int amount = damage.getDealtAmount();
            return (damage.getTarget() == opponent) ?
                new MagicEvent(
                    permanent,
                    player,
                    new Object[]{amount},
                    this,
                    player + " puts " + amount + " +1/+1 counters on " + permanent + "."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            game.doAction(new MagicChangeCountersAction(
                    event.getPermanent(),
                    MagicCounterType.PlusOne,
                    (Integer)data[0],
                    true));
        }
    };
}
