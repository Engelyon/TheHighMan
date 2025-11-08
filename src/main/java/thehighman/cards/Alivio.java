package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

import static basemod.BaseMod.addKeyword;

public class Alivio extends BaseCard {
    public static final String ID = makeID("Alivio");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 3;
    private static final int UPG_CHAPADO = 1;

    public Alivio() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT, UPG_CHAPADO);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_CHAPADO); // Aumenta de 3 para 4
            initializeDescription();
        }
    }
}