package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class Inspiracao extends BaseCard {
    public static final String ID = makeID("Inspiracao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    private static final int ERVA_GAIN = 2;
    private static final int UPG_ERVA = 2;
    private static final int BLOCK = 2;

    public Inspiracao() {
        super(ID, info);
        setMagic(ERVA_GAIN, UPG_ERVA);
        setBlock(BLOCK);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, BLOCK));
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p,this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SedaPower(p,p,1),1));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_ERVA);
            initializeDescription();
        }
    }
}