package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

import java.util.Objects;

public class Amnesia extends BaseCard {
    public static final String ID = makeID("Amnesia");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int ERVA_GAIN = 2;

    public Amnesia() {
        super(ID, info);
        this.exhaust = true;
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            int badtripsEX = 0;
            for (int i = 0; i < p.exhaustPile.size(); i++) {
                if (Objects.equals(p.exhaustPile.getNCardFromTop(i).getMetricID(), BadTrip.ID)) {
                    badtripsEX++;
                }
            }
            addToBot(new ExhaustAction(2, true, false, false));
            if (badtripsEX >= 1) {
                for (int i = 0; i < badtripsEX; i++) {
                    addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
                }
            }
        } else {
            int badtripsEX = 0;
            for (int i = 0; i < p.exhaustPile.size(); i++) {
                if (Objects.equals(p.exhaustPile.getNCardFromTop(i).getMetricID(), BadTrip.ID)) {
                    badtripsEX++;
                }
            }
            addToBot(new ExhaustAction(2, false, false, false));
            if (badtripsEX >= 1) {
                for (int i = 0; i < badtripsEX; i++) {
                    addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
                }
            }
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        super.canUse(p, m);
        if (p.hand.size() <= 2) {
            this.cantUseMessage = "Preciso de pelo menos 3 cartas na mão para usar isto.";
            return false;
        }
        return true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.exhaust = false;
    }
}