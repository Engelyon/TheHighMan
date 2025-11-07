package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Comido;
import thehighman.util.CardStats;

public class CuidadoAlimentar extends BaseCard {
    public static final String ID = makeID("CuidadoAlimentar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 12;
    private static final int HEAL = 2;
    private static final int COMIDO_THRESHOLD = 3;

    public CuidadoAlimentar() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(Comido.POWER_ID) && p.getPower(Comido.POWER_ID).amount >= COMIDO_THRESHOLD) {
            addToBot(new GainBlockAction(p, BLOCK));
            addToBot(new HealAction(p, p, HEAL));
        }
    }
}