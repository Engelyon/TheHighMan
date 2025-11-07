package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class SemPressa extends BaseCard {
    public static final String ID = makeID("SemPressa");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BASE_BLOCK = 3;
    private static final int BONUS_BLOCK = 2;
    private boolean foiRetidaEsteTurno = false;

    public SemPressa() {
        super(ID, info);
        setBlock(BASE_BLOCK);
        this.selfRetain = true;
        this.exhaust = true;
        this.rawDescription = "Retenha esta carta. Se for usada após ser retida, custa 0 e ganha +2 de Bloqueio.";
        initializeDescription();
    }

    @Override
    public void atTurnStart() {
        if (foiRetidaEsteTurno) {
            this.setCostForTurn(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalBlock = this.block + (foiRetidaEsteTurno ? BONUS_BLOCK : 0);
        addToBot(new GainBlockAction(p, totalBlock));
        foiRetidaEsteTurno = false;
    }
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        foiRetidaEsteTurno = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2); // 3 → 5 base
            initializeDescription();
        }
    }
}