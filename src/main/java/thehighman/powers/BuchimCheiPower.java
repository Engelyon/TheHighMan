package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BuchimCheiPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:BuchimCheiPower";

    public static int COMIDO_BASE_MAX = 10; // valor padrão do limite
    public static int COMIDO_MAX = COMIDO_BASE_MAX * 2;

    public BuchimCheiPower(AbstractCreature owner) {
        this.name = "Buchim Chei";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "O limite de Comido é dobrado neste combate.";
    }
}