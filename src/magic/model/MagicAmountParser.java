package magic.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import magic.data.EnglishToInt;
import magic.model.target.MagicTargetFilterFactory;

public enum MagicAmountParser {

    YourLife("your life total") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.LifeTotal;
        }
    },
    Domain("basic land type(s)? among lands you control") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.Domain;
        }
    },
    Devotion("your devotion to " + ARG.COLOR) {
        public MagicAmount toAmount(final Matcher arg) {
            final MagicColor color = MagicColor.getColor(ARG.color(arg));
            return MagicAmountFactory.Devotion(color);
        }
    },
    Equipment("Equipment attached to (it|SN)") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.Equipment;
        }
    },
    Aura("Aura attached to (it|SN)") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.Aura;
        }
    },
    ItsPower("(its|SN's) power") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.SN_Power;
        }
    },
    AllCounterOnSN("counter(s)? on (it|SN)") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.AllCountersOnSource;
        }
    },
    CounterOnSN(ARG.WORD1 + " counter(s)? on (it|SN)") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.CounterOnSource(
                MagicCounterType.getCounterRaw(ARG.word1(arg))
            );
        }
    },
    ColorOnPerms("color among permanents you control") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.ColorsOnPerms;
        }
    },
    GreatestPower("the greatest power among creatures you control") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.GreatestPower;
        }
    },
    XCost("x") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.XCost;
        }
    },
    NegXCost("-x") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.NegXCost;
        }
    },
    Player("player") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.Constant(2);
        }
    },
    Number("[^ ]+") {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.Constant(EnglishToInt.convert(arg.group()));
        }
    },
    FromFilter(ARG.ANY) {
        public MagicAmount toAmount(final Matcher arg) {
            return MagicAmountFactory.FromFilter(
                MagicTargetFilterFactory.Target(ARG.any(arg))
            );
        }
    };

    private final Pattern pattern;

    private MagicAmountParser(final String regex) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Matcher matcher(final String rule) {
        return pattern.matcher(rule);
    }

    public abstract MagicAmount toAmount(final Matcher arg);

    public static final int getX(final String text, final int X) {
        if (text.equalsIgnoreCase("x")) {
            return X;
        } else if (text.equalsIgnoreCase("-x")) {
            return -X;
        } else {
            return EnglishToInt.convert(text);
        }
    }

    public static final MagicAmount build(final String text) {
        if (text == null || text.isEmpty()) {
            return MagicAmountFactory.One;
        }
        final String cleaned = text.replaceAll("^the number of ", "");
        for (final MagicAmountParser rule : values()) {
            final Matcher matcher = rule.matcher(cleaned);
            if (matcher.matches()) {
                return rule.toAmount(matcher);
            }
        }
        throw new RuntimeException("unknown amount \"" + text + "\"");
    }
}
