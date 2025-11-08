package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class FocoFino extends BaseCard {
    public static final String ID = makeID("FocoFino");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 2;
    private static final int UPG_CHAPADO = 1;
    private static final int DRAW_AMOUNT = 1;

    public FocoFino() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT, UPG_CHAPADO);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Aplica Chapado ao inimigo
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));

        // Compra 1 carta
        addToBot(new DrawCardAction(p, DRAW_AMOUNT));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_CHAPADO); // 2 → 3 de Chapado
            initializeDescription();
        }
    }
}