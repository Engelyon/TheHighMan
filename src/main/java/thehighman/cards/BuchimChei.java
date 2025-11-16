package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class BuchimChei extends BaseCard {
    public static final String ID = makeID("BuchimChei");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    private static final int TANTO = 1;
    private static final int TANTO_UPG =1;

    public BuchimChei() {
        super(ID, info);
        this.exhaust=true;
        setMagic(TANTO, TANTO_UPG);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ComidoPower.POWER_ID)){
        addToBot(new ApplyPowerAction(p,p, new ComidoPower(p,p, magicNumber*2)));
        addToBot(new ApplyPowerAction(p,p, new ComidoPower(p, p.getPower(ComidoPower.POWER_ID).amount)));
    } else{
            addToBot(new ApplyPowerAction(p,p, new ComidoPower(p,p, magicNumber*2)));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.exhaust=false;
    }
}