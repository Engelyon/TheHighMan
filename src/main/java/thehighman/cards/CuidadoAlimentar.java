package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class CuidadoAlimentar extends BaseCard {
    public static final String ID = makeID("CuidadoAlimentar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 12;
    private static final int HEAL = 2;

    public CuidadoAlimentar() {
        super(ID, info);
        setBlock(BLOCK, BLOCK + 4);
        setMagic(HEAL, HEAL + 1);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, BLOCK));
        addToBot(new HealAction(p, p, HEAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}