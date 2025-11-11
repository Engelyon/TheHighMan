package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
    private static final int BASE_BLOCK_UPG = 5;
    private static final int BONUS_PER_RETAIN = 2;
    private static final int BONUS_PER_RETAIN_UPG = 3;

    private int retainedTurns = 0;

    public SemPressa() {
        super(ID, info);
        setBlock(BASE_BLOCK);
        this.selfRetain = true;
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = upgraded ? BONUS_PER_RETAIN_UPG : BONUS_PER_RETAIN;
        int base = upgraded ? BASE_BLOCK_UPG : BASE_BLOCK;
        int totalBlock = base + retainedTurns * bonus;
        addToBot(new GainBlockAction(p, p, totalBlock));
        retainedTurns = 0;
        applyPowers();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        retainedTurns++;
        applyPowers();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        retainedTurns = 0;
        applyPowers();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player == null || AbstractDungeon.player.hand == null || !AbstractDungeon.player.hand.group.contains(this)) {
            if (retainedTurns != 0) {
                retainedTurns = 0;
            }
        }

        int bonus = upgraded ? BONUS_PER_RETAIN_UPG : BONUS_PER_RETAIN;
        int base = upgraded ? BASE_BLOCK_UPG : BASE_BLOCK;
        int newBlock = base + retainedTurns * bonus;

        this.baseBlock = newBlock;
        this.block = newBlock;
        this.isBlockModified = false;

        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BASE_BLOCK_UPG - BASE_BLOCK);
            initializeDescription();
        }
    }
}