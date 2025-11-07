package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class LimparAMente extends BaseCard {
    public static final String ID = makeID("LimparAMente");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int DISCARD_AMOUNT = 2;
    private static final int ERVA_GAIN = 2;

    public LimparAMente() {
        super(ID, info);
        setMagic(ERVA_GAIN, ERVA_GAIN + 1); // 2 → 3 de Erva com upgrade

        this.rawDescription = "Descarte 2 cartas. Ganhe !M! de Erva.";
        this.keywords.add("erva");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Descarta 2 cartas da mão
        addToBot(new DiscardAction(p, p, DISCARD_AMOUNT, false));

        // Ganha 2 de Erva
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // 2 → 3 de Erva
            initializeDescription();
        }
    }
}