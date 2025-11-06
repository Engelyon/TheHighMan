package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static thehighman.InimigosDoSpire.makeID;

public class Erva extends BasePower{
    public static final String POWER_ID = makeID("Erva");

    private static final int DEFAULT_AMOUNT = 1;

    public Erva(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public Erva(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, DEFAULT_AMOUNT);
    }
}
