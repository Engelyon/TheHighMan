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
            2
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
        int comidoAmount = p.getPower(ComidoPower.POWER_ID).amount;
        addToBot(new ApplyPowerAction(p,p, new ComidoPower(p, this.magicNumber*comidoAmount)));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}