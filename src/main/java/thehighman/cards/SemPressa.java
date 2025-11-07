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

    private boolean foiRetida = false;

    public SemPressa() {
        super(ID, info);
        setBlock(BASE_BLOCK);
        this.selfRetain = true;
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void atTurnStart() {
        if (this.selfRetain && this.foiRetida) {
            this.setCostForTurn(0);
            this.upgradeBlock(BONUS_BLOCK);
        }
        this.foiRetida = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
    }
}