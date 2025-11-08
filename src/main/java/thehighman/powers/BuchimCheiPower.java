package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import static thehighman.InimigosDoSpire.makeID;

public class BuchimCheiPower extends BasePower {
    public static final String POWER_ID = makeID("BuchimCheiPower");
    public static int COMIDO_BASE_MAX = 10;
    public static int COMIDO_MAX = COMIDO_BASE_MAX * 2;

    public BuchimCheiPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public BuchimCheiPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }

    @Override
    public void updateDescription() {
        this.description = "Dobre seu limite de Comido.";
    }
}