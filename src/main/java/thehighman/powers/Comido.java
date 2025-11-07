package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class Comido extends AbstractPower {
    public static final String POWER_ID = "thehighman:Comido";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public Comido(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Math.min(amount, 5); // máximo 5
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Evita efeitos negativos da Larica. Máximo de 5. Cada stack bloqueia 1 penalidade.";
    }

    // Método auxiliar para consumir Comido
    public static boolean consumirComido(AbstractCreature target) {
        AbstractPower comido = target.getPower(POWER_ID);
        if (comido != null && comido.amount > 0) {
            comido.amount--;
            comido.updateDescription();
            if (comido.amount == 0) {
                target.powers.remove(comido);
            }
            return true;
        }
        return false;
    }
}