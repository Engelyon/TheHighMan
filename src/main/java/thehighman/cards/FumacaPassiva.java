package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.ErvaPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class FumacaPassiva extends BaseCard {
    public static final String ID = makeID("FumacaPassiva");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int CHAPADO = 1;

    public FumacaPassiva() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        isMultiDamage = true;
        setMagic(CHAPADO);
        this.keywords.add("chapado");
        this.keywords.add("seda");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.POISON));
        int ervas = p.getPower(ErvaPower.POWER_ID).amount;
        if (ervas>=1) {
            int sedaBonus = p.hasPower(SedaPower.POWER_ID) ? p.getPower(SedaPower.POWER_ID).amount : 0;
            int totalChapado = this.magicNumber + sedaBonus;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, totalChapado), totalChapado));
            }
        }
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;

        if (!p.hasPower(ErvaPower.POWER_ID) || p.getPower(ErvaPower.POWER_ID).amount < 1) {
            this.cantUseMessage = "Preciso de Erva para usar esta carta.";
            return false;
        }

        return true;
    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(SedaPower.POWER_ID)) {
            int seda = AbstractDungeon.player.getPower(SedaPower.POWER_ID).amount;
            int bonus = Math.max(1, seda);
            this.magicNumber = this.baseMagicNumber + bonus;
            isMagicNumberModified = true;
        } else {
            this.magicNumber = this.baseMagicNumber;
            isMagicNumberModified = false;
        }
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
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