package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class PassadaDeNivel extends BaseCard {
    public static final String ID = makeID("PassadaDeNivel");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static int DRAW = 2;
        public static int UPG_DRAW = 1;
        public static int C_ERVA = 2;
        public static int ENERGIA = 1;

    public PassadaDeNivel() {
        super(ID, info);
        setMagic(DRAW, UPG_DRAW);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        if (p.getPower(ErvaPower.POWER_ID).amount >= C_ERVA) {
            addToBot(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(ENERGIA));
            addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, C_ERVA));
        }
    }
    @Override
    public void upgrade() {
        super.upgrade();
    }
    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=2;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ErvaPower.POWER_ID)
                && AbstractDungeon.player.getPower(ErvaPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}