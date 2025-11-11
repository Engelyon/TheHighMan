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

    public BuchimChei() {
        super(ID, info);
        this.exhaust=true;
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int comidoAmount = p.getPower(ComidoPower.POWER_ID).amount;
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, comidoAmount)));
        if (upgraded){
            addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, comidoAmount)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}