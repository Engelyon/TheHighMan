package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.CompreensaoElevadaPower;
import thehighman.util.CardStats;

public class CompreensaoElevada extends BaseCard {
    public static final String ID = makeID("CompreensaoElevada");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public CompreensaoElevada() {
        super(ID, info);
        this.keywords.add("compreensão");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CompreensaoElevadaPower(p,p)));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1); // 2 → 1 de custo
            initializeDescription();
        }
    }
}