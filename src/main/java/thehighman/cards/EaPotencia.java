package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.EaPotenciaPower;
import thehighman.util.CardStats;

public class EaPotencia extends BaseCard {
    public static final String ID = makeID("EaPotencia");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public EaPotencia() {
        super(ID, info);
        this.keywords.add("potência");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EaPotenciaPower(p,p)));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate=true;
            initializeDescription();
        }
    }
}