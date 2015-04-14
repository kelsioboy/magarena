package magic.model.target;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicSource;

import java.util.ArrayList;
import java.util.List;

public abstract class MagicPermanentFilterImpl implements MagicTargetFilter<MagicPermanent> {
    public List<MagicPermanent> filter(final MagicGame game) {
        return filter(game, game.getTurnPlayer(), MagicTargetHint.None);
    }
    
    public List<MagicPermanent> filter(final MagicPlayer player) {
        return filter(player.getGame(), player, MagicTargetHint.None);
    }

    public boolean accept(final MagicGame game, final MagicSource source, final MagicPermanent target) {
        return accept(game, source.getController(), target);
    }
    
    public List<MagicPermanent> filter(final MagicGame game, final MagicSource source, final MagicTargetHint targetHint) {
        return filter(game, source.getController(), targetHint); 
    }

    public List<MagicPermanent> filter(final MagicGame game, final MagicPlayer player, final MagicTargetHint targetHint) {
        final List<MagicPermanent> targets=new ArrayList<MagicPermanent>();
        if (acceptType(MagicTargetType.Permanent)) {
            for (final MagicPlayer controller : game.getPlayers()) {
                for (final MagicPermanent targetPermanent : controller.getPermanents()) {
                    if (accept(game,player,targetPermanent) &&
                        targetHint.accept(player,targetPermanent)) {
                        targets.add(targetPermanent);
                    }
                }
            }
        }
        return targets;
    }
    
    public boolean acceptType(final MagicTargetType targetType) {
        return targetType==MagicTargetType.Permanent;
    }

    public MagicPermanentFilterImpl except(final MagicPermanent invalid) {
        return new MagicOtherPermanentTargetFilter(this, invalid);
    }
}
