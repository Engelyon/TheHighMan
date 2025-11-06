package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static thehighman.InimigosDoSpire.makeID;

public class Erva extends BasePower{

    public static final String POWER_ID = makeID("Erva");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public Erva(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Você possui " + amount + " Ervas";
    }
}

