package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class Ventilador extends BaseCard {
    public static final String ID = makeID("Ventilador");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 2;
    private static final int UPG_CHAPADO = 1;

    public Ventilador() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT, UPG_CHAPADO);
        this.rawDescription = "Aplique !M! de Chapado a todos os inimigos.";
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, this.magicNumber), this.magicNumber));
            }
        }
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