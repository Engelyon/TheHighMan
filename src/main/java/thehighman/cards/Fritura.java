package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
import thehighman.util.CardStats;

public class Fritura extends BaseCard {
    public static final String ID = makeID("Fritura");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int TEMP_STRENGTH = 2;
    private static final int LARICA_LOSS = 1;

    public Fritura() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 2 de Força temporária
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, TEMP_STRENGTH), TEMP_STRENGTH));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, TEMP_STRENGTH), TEMP_STRENGTH));

        // Perde 1 de Larica se tiver
        if (p.hasPower(Larica.POWER_ID)) {
            int atual = p.getPower(Larica.POWER_ID).amount;
            if (atual > 0) {
                p.getPower(Larica.POWER_ID).amount -= LARICA_LOSS;
                if (p.getPower(Larica.POWER_ID).amount <= 0) {
                    p.getPower(Larica.POWER_ID).onRemove();
                    p.powers.remove(p.getPower(Larica.POWER_ID));
                }
            }
        }
    }
}