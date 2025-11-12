package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class Ponderar extends BaseCard {
    public static final String ID = makeID("Ponderar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int SCRY_AMOUNT = 3;
    private static final int DRAW_AMOUNT = 1;
    private static final int DRAW_AMOUNT_UPG = 2;
    private static final int ERVA_COST = 1;

    private static final String NOT_ENOUGH_ERVA_MESSAGE = "Não tenho Erva suficiente";

    public Ponderar() {
        super(ID, info);
        this.baseMagicNumber = SCRY_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (p == null) return false;
        if (!p.hasPower(ErvaPower.POWER_ID) || p.getPower(ErvaPower.POWER_ID).amount < ERVA_COST) {
            this.cantUseMessage = NOT_ENOUGH_ERVA_MESSAGE;
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, ERVA_COST));
        addToBot(new ScryAction(this.magicNumber));
        int draws = upgraded ? DRAW_AMOUNT_UPG : DRAW_AMOUNT;
        addToBot(new DrawCardAction(p, draws));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=1;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ErvaPower.POWER_ID)
                && AbstractDungeon.player.getPower(ErvaPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}