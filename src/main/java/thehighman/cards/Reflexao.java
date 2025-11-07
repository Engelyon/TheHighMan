package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class Reflexao extends BaseCard {
    public static final String ID = makeID("Reflexao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int LARICA_AMOUNT = 2;

    public Reflexao() {
        super(ID, info);
        this.exhaust = true;
        setMagic(LARICA_AMOUNT, 1); // upgrade aumenta Larica de 2 → 3

        this.rawDescription = "Remova todos os efeitos de Chapado do inimigo. Aplique !M! de Larica.";
        this.keywords.add("chapado");
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Remove todos os stacks de Chapado do inimigo
        if (m.hasPower(ChapadoPower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(m, p, ChapadoPower.POWER_ID));
        }

        // Aplica 2 de Larica
        addToBot(new ApplyPowerAction(m, p, new LaricaPower(m, LARICA_AMOUNT), LARICA_AMOUNT));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // Larica: 2 → 3
            initializeDescription();
        }
    }
}