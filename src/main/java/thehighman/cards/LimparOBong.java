package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class LimparOBong extends BaseCard {
    public static final String ID = makeID("LimparOBong");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int STRENGHT = 1;
    private static final int STRENGHT_UPG = 1;

    public LimparOBong() {
        super(ID, info);
        this.selfRetain = true;
        this.exhaust = true;
        setMagic(STRENGHT,STRENGHT_UPG);
        this.keywords.add("erva");
        this.keywords.add("força");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ErvaPower.POWER_ID)) {
            int erva = p.getPower(ErvaPower.POWER_ID).amount;
            if (erva > 0) {
                addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, erva));
                int strengthPerErva = magicNumber;
                int totalStrength = erva * strengthPerErva;
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, totalStrength), totalStrength));
                addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, totalStrength), totalStrength));
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (p == null || !p.hasPower(ErvaPower.POWER_ID) || p.getPower(ErvaPower.POWER_ID).amount <= 0) {
            this.cantUseMessage = "Preciso de pelo menos 1 de Erva.";
            return false;
        }
        return true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(STRENGHT_UPG);
            this.exhaust = false;
            initializeDescription();
        }
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