package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class NuvemToxica extends BaseCard {
    public static final String ID = makeID("NuvemToxica");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 5;
    private static final int VULNERABLE_AMOUNT = 1;

    public NuvemToxica() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT, 2); // 5 → 7 de Chapado com upgrade
        this.keywords.add("chapado");
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Aplica 5 de Chapado
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, CHAPADO_AMOUNT), CHAPADO_AMOUNT));

        // Se o inimigo tiver Larica, aplica 1 de Vulnerável
        if (m.hasPower(LaricaPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, VULNERABLE_AMOUNT, false), VULNERABLE_AMOUNT));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2); // 5 → 7 de Chapado
            initializeDescription();
        }
    }
}