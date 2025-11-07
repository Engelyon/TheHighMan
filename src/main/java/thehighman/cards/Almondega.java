package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Comido;
import thehighman.powers.Larica;
import thehighman.util.CardStats;

public class Almondega extends BaseCard {
    public static final String ID = makeID("Almondega");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_AMOUNT = 1;
    private static final int LARICA_AMOUNT = 1;

    public Almondega() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 1 de Comido
        addToBot(new ApplyPowerAction(p, p, new Comido(p, COMIDO_AMOUNT), COMIDO_AMOUNT));

        // Ganha 1 de Larica
        addToBot(new ApplyPowerAction(p, p, new Larica(p, LARICA_AMOUNT), LARICA_AMOUNT));
    }
}