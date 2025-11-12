package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class Analisar extends BaseCard {
    public static final String ID = makeID("Analisar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int DRAW_AMOUNT = 2;
    private static final int CHAPADO_THRESHOLD = 5;

    public Analisar() {
        super(ID, info);
        setMagic(DRAW_AMOUNT);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, DRAW_AMOUNT));
        boolean chapadoDetectado = false;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo != null && !mo.isDeadOrEscaped()) {
                if (mo.hasPower(ChapadoPower.POWER_ID)){
                    if (mo.getPower(ChapadoPower.POWER_ID).amount >= CHAPADO_THRESHOLD){
                        chapadoDetectado = true;
                    }
                }
            }
        }
        if (chapadoDetectado) {
            new ApplyPowerAction(p, p, new SedaPower(p, p, 1), 1);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}